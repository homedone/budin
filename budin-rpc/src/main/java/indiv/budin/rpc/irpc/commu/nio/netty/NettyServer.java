package indiv.budin.rpc.irpc.commu.nio.netty;


import indiv.budin.ioc.annotations.IocComponent;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetAddress;

@IocComponent
public class NettyServer {
    /**
     * 绑定一个端口，并启动一个netty服务,采用主从Reactor,boss负责连接channel,worker负责处理事件
     * @param port
     */
    private final static String DEFAULT_HOST="127.0.0.1";
    private final static int PORT=9443;

    public void run(){
        run(PORT);
    }
    public void run(int port){
        String host=DEFAULT_HOST;
        try {
            host= InetAddress.getLocalHost().getHostAddress();
        }catch (Exception e){
            e.printStackTrace();
        }
        run(host,port);
    }
    public void run(String host,int port){

        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workerGroup=new NioEventLoopGroup();
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
                                    .addLast(new Encoder())
                                    .addLast(new Decoder())
                                    .addLast(new SimpleNettyServerHandler());
                        }
                    });
            ChannelFuture f = b.bind(host,port).sync();
            f.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
