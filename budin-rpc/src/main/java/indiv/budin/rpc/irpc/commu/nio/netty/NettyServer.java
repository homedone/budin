package indiv.budin.rpc.irpc.commu.nio.netty;


import indiv.budin.ioc.annotations.IocComponent;
import indiv.budin.rpc.irpc.server.base.Server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

@IocComponent
public class NettyServer implements Server {
    /**
     * 绑定一个端口，并启动一个netty服务,采用主从Reactor,boss负责连接channel,worker负责处理事件
     *
     * @param port
     */
    private final static String DEFAULT_HOST = "127.0.0.1";
    private final static int PORT = 9443;

    private String host;
    private Integer port;

    public NettyServer() {
        host=DEFAULT_HOST;
        port=PORT;
    }

    public NettyServer(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public String getAddress() {
        return host + "::" + port;
    }

    @Override
    public void run() {
        if (port == 0) port = PORT;
        run(port);
    }

    @Override
    public void run(int port) {
        if (host == null) host = DEFAULT_HOST;
        try {
            String localHost = InetAddress.getLocalHost().getHostAddress();
//            if (!localHost.equals(host)) host = localHost;
        } catch (Exception e) {
            e.printStackTrace();
        }
        run(host, port);
    }

    @Override
    public void run(String host, int port) {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    // 设置 socket 的参数选项
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline()
                                    .addLast(new IdleStateHandler(20, 0, 0, TimeUnit.SECONDS))
                                    .addLast(new Encoder())
                                    .addLast(new Decoder())
                                    .addLast(new SimpleNettyServerHandler());
                        }
                    });
            ChannelFuture f = b.bind(host, port).sync();
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
