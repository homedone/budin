package indiv.budin.rpc.irpc.commu.nio.netty;

import indiv.budin.rpc.irpc.carrier.RpcMessage;
import indiv.budin.rpc.irpc.carrier.RpcRequest;
import indiv.budin.rpc.irpc.center.base.RegistryCenter;
import indiv.budin.rpc.irpc.center.nacos.NacosRegistryCenter;
import indiv.budin.rpc.irpc.common.constants.MessageType;
import indiv.budin.rpc.irpc.common.constants.SerializerType;
import indiv.budin.rpc.irpc.commu.nio.Client;
import indiv.budin.rpc.irpc.exception.NettyConnectException;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class NettyClient implements Client {

    Logger logger = LoggerFactory.getLogger(NettyClient.class);

    private AtomicInteger atomicInteger=new AtomicInteger();
    private final static int RECONNECT_DELAY = 20;
    private final Bootstrap bootstrap;

    private final Map<String, SocketChannel> channelMap;
    private final EventLoopGroup eventLoopGroup;

    private final RegistryCenter registryCenter;

    public NettyClient() {
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
                                    .addLast(new Encoder())
                                    .addLast(new Decoder())
                                    .addLast(new SimpleNettyClientHandler());
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SocketChannel connect(InetSocketAddress inetSocketAddress) {
        String net = inetSocketAddress.toString();
        if (channelMap.containsKey(net)) return channelMap.get(net);
        try {
            ChannelFuture future = bootstrap.connect(inetSocketAddress);
            future.addListener((ChannelFutureListener) fu -> {
                if (!fu.isSuccess()) {
                    logger.error("connect fail, try to reconnect within " + RECONNECT_DELAY + " seconds");
                    fu.channel().eventLoop().schedule(() -> connect(inetSocketAddress), RECONNECT_DELAY, TimeUnit.SECONDS);
                }
            });
            SocketChannel socketChannel = (SocketChannel) future.channel();
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
            InetSocketAddress inetSocketAddress = registryCenter.discovery(request.getServiceNameWithNode());
            logger.info(inetSocketAddress.getHostName()+"."+inetSocketAddress.getPort());
            RpcMessage rpcMessage = new RpcMessage();
            rpcMessage.setData(request);
            rpcMessage.setMessageType(MessageType.REQUEST.getType());
            rpcMessage.setMessageId(atomicInteger.incrementAndGet());
            rpcMessage.setSerializerType(serializerType);
            logger.info(rpcMessage.toString());
            Channel channel = connect(inetSocketAddress);
            channel.writeAndFlush(rpcMessage).addListener((ChannelFutureListener) fu -> {
                if (!fu.isSuccess()) {
                    logger.error("send message fail as "+fu.cause());
                    fu.channel().close();
                }else {
                    logger.info("send message success");
                }
            });
        }
        return null;
    }

    public void release() {
        if (eventLoopGroup != null) {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
