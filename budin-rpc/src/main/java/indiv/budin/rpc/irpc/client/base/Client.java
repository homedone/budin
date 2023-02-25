package indiv.budin.rpc.irpc.client.base;

import io.netty.channel.Channel;

import java.net.InetSocketAddress;

public interface Client {
     Channel connect(InetSocketAddress inetSocketAddress);

     Object sendObject(Object obj);

     void release();
}
