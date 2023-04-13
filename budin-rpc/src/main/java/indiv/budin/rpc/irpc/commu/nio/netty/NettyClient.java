package indiv.budin.rpc.irpc.commu.nio.netty;

import indiv.budin.rpc.irpc.carrier.RpcMessage;
import indiv.budin.rpc.irpc.carrier.RpcRequest;
import indiv.budin.rpc.irpc.center.base.RegistryCenter;
import indiv.budin.rpc.irpc.center.nacos.NacosRegistryCenter;
import indiv.budin.rpc.irpc.common.concurent.BlockingFuturePool;
import indiv.budin.rpc.irpc.common.concurent.FutureMap;
import indiv.budin.rpc.irpc.common.concurent.ReuseFuture;
import indiv.budin.rpc.irpc.common.constants.MessageType;
import indiv.budin.rpc.irpc.common.constants.SerializerType;
import indiv.budin.rpc.irpc.common.concurent.SyncFuture;
import indiv.budin.rpc.irpc.common.utils.FactoryUtil;
import indiv.budin.rpc.irpc.client.base.Client;
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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class NettyClient implements Client {

    Logger logger = LoggerFactory.getLogger(NettyClient.class);

    private final FutureMap futureMap;
    private final BlockingFuturePool<Object> requestFuturePool;
    private final AtomicInteger atomicInteger = new AtomicInteger();
    private final static int RECONNECT_DELAY = 20;
    private final static int requestPoolSize = 100;
    private final Bootstrap bootstrap;

    private final Map<String, Channel> channelMap;
    private final EventLoopGroup eventLoopGroup;

    private final RegistryCenter registryCenter;

    public NettyClient() {
        requestFuturePool = (BlockingFuturePool<Object>) FactoryUtil.getSingletonInstance(BlockingFuturePool.class, new Object[]{requestPoolSize});
        futureMap = (FutureMap) FactoryUtil.getSingletonInstance(FutureMap.class);
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
                                    .addLast(new IdleStateHandler(0, 15, 0, TimeUnit.SECONDS))
                                    .addLast(new Encoder())
                                    .addLast(new Decoder())
                                    .addLast(new SimpleNettyClientHandler());
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Channel connect(InetSocketAddress inetSocketAddress) {
        String net = inetSocketAddress.toString();
        if (channelMap.containsKey(net)) return channelMap.get(net);
        try {
            ChannelFuture future = bootstrap.connect(inetSocketAddress);
            //每次都重新创建一个线程，开销太大，预改进策略，改成线程池,每次获取一个future
            ReuseFuture<Object> syncFuture = requestFuturePool.obtain();
            future.addListener((ChannelFutureListener) fu -> {
                if (!fu.isSuccess()) {
                    logger.info("connect problem: " + fu.cause());
                    logger.error("connect fail, try to reconnect within " + RECONNECT_DELAY + " seconds");
//                    fu.channel().eventLoop().schedule(() -> connect(inetSocketAddress), RECONNECT_DELAY, TimeUnit.SECONDS);
                    syncFuture.doneAndPut(null);
                } else {
                    logger.info("connect success");
                    syncFuture.doneAndPut(1);
                }
            });
            syncFuture.get(10, TimeUnit.SECONDS);
            if (syncFuture.getResponse() == null) {
                // 还回future
                requestFuturePool.free(syncFuture);
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

    @Override
    public Object sendObject(Object message) {
        return sendObject(message, SerializerType.KRYO_THREAD_SERIALIZER.getType());
    }

    public Object sendObject(Object message, byte serializerType) {
        if (message instanceof RpcRequest) {
            RpcRequest request = (RpcRequest) message;
            logger.info(request.toString());
            InetSocketAddress inetSocketAddress = registryCenter.discovery(request.getServiceName(), request.getMessageId());
            logger.info(inetSocketAddress.getHostName() + "::" + inetSocketAddress.getPort());
            RpcMessage rpcMessage = new RpcMessage();
            rpcMessage.setData(request);
            rpcMessage.setMessageType(MessageType.REQUEST.getType());
            rpcMessage.setMessageId(atomicInteger.incrementAndGet());
            rpcMessage.setSerializerType(serializerType);
            logger.info(rpcMessage.toString());
            Channel channel = connect(inetSocketAddress);
            ReuseFuture<Object> syncFuture = requestFuturePool.obtain();
            futureMap.put(request.getMessageId(), syncFuture);
            channel.writeAndFlush(rpcMessage).addListener((ChannelFutureListener) fu -> {
                if (!fu.isSuccess()) {
                    logger.error("rpc call fail as " + fu.cause());
                    fu.channel().close();
                } else {
                    logger.info("rpc call success");
                }
            });
            return syncFuture;
        }
        return null;
    }

    @Override
    public void release() {
        if (eventLoopGroup != null) {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
