package indiv.budin.rpc.irpc.commu.nio.netty;

import indiv.budin.rpc.irpc.carrier.RpcMessage;
import indiv.budin.rpc.irpc.carrier.RpcRequest;
import indiv.budin.rpc.irpc.carrier.RpcResponse;
import indiv.budin.rpc.irpc.center.base.RegistryCenter;
import indiv.budin.rpc.irpc.center.nacos.NacosRegistryCenter;
import indiv.budin.rpc.irpc.common.concurent.FutureMap;
import indiv.budin.rpc.irpc.common.concurent.FuturePool;
import indiv.budin.rpc.irpc.common.concurent.SyncFuturePool;
import indiv.budin.rpc.irpc.common.constants.MessageType;
import indiv.budin.rpc.irpc.common.constants.SerializerType;
import indiv.budin.rpc.irpc.common.concurent.SyncFuture;
import indiv.budin.rpc.irpc.common.utils.FactoryUtil;
import indiv.budin.rpc.irpc.commu.nio.Client;
import indiv.budin.rpc.irpc.exception.NettyConnectException;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class NettyClient implements Client {

    Logger logger = LoggerFactory.getLogger(NettyClient.class);

    private final FutureMap futureMap;
    private final AtomicInteger atomicInteger = new AtomicInteger();
    private final static int RECONNECT_DELAY = 20;
    private final Bootstrap bootstrap;

    private final Map<String, Channel> channelMap;
    private final EventLoopGroup eventLoopGroup;

    private final RegistryCenter registryCenter;

    public NettyClient() {
        futureMap= (FutureMap) FactoryUtil.getSingletonInstance(FutureMap.class);
        registryCenter = NacosRegistryCenter.getInstance();
        eventLoopGroup = new NioEventLoopGroup();
        channelMap = new ConcurrentHashMap<>();
        bootstrap = new Bootstrap();
        try {
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new IdleStateHandler(0,15,0,TimeUnit.SECONDS))
                                    .addLast(new Encoder())
                                    .addLast(new Decoder())
                                    .addLast(new SimpleNettyClientHandler());
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Channel connect(InetSocketAddress inetSocketAddress) {
        String net = inetSocketAddress.toString();
        if (channelMap.containsKey(net)) return channelMap.get(net);
        try {
            ChannelFuture future = bootstrap.connect(inetSocketAddress);
            SyncFuture<Boolean> syncFuture = new SyncFuture<>();
            future.addListener((ChannelFutureListener) fu -> {
                if (!fu.isSuccess()) {
                    logger.info("connect problem: " + fu.cause());
                    logger.error("connect fail, try to reconnect within " + RECONNECT_DELAY + " seconds");
//                    fu.channel().eventLoop().schedule(() -> connect(inetSocketAddress), RECONNECT_DELAY, TimeUnit.SECONDS);
                    syncFuture.doneAndPut(Boolean.FALSE);
                } else {
                    logger.info("connect success");
                    syncFuture.doneAndPut(Boolean.TRUE);
                }
            });
            syncFuture.get(10, TimeUnit.SECONDS);
            if (!syncFuture.getResponse()) {
                throw new NettyConnectException("netty connect fail");
            }
            Channel socketChannel = future.channel();
            channelMap.put(net, socketChannel);
            return socketChannel;
        } catch (Exception e) {
            e.printStackTrace();
            throw new NettyConnectException("netty channel connect fail");
        }
    }

    public Object sendObject(Object message) {
        return sendObject(message, SerializerType.KRYO_POOL_SERIALIZER.getType());
    }

    public Object sendObject(Object message, byte serializerType) {
        if (message instanceof RpcRequest) {
            RpcRequest request = (RpcRequest) message;
            logger.info(request.toString());
            InetSocketAddress inetSocketAddress = registryCenter.discovery(request.getServiceName(),request.getMessageId());
            logger.info(inetSocketAddress.getHostName() + "::" + inetSocketAddress.getPort());
            RpcMessage rpcMessage = new RpcMessage();
            rpcMessage.setData(request);
            rpcMessage.setMessageType(MessageType.REQUEST.getType());
            rpcMessage.setMessageId(atomicInteger.incrementAndGet());
            rpcMessage.setSerializerType(serializerType);
            logger.info(rpcMessage.toString());
            Channel channel = connect(inetSocketAddress);
            SyncFuture<Object> syncFuture = new SyncFuture<>();
            futureMap.put(request.getMessageId(),syncFuture);
            channel.writeAndFlush(rpcMessage).addListener((ChannelFutureListener) fu -> {
                if (!fu.isSuccess()) {
                    logger.error("rpc call fail as " + fu.cause());
                    fu.channel().close();
                } else {
                    logger.info("rpc call success");
                }
            });
            try {
                return syncFuture.get(10, TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void release() {
        if (eventLoopGroup != null) {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
