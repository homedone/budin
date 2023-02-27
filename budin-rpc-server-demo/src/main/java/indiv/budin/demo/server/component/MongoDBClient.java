package indiv.budin.demo.server.component;


import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import indiv.budin.rpc.irpc.common.concurent.BudinSubscriber;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.Document;
import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author
 * @date 2023/2/26 22 28
 * discription
 * <p>
 * -------------MongoDb Demo------------------------
 * 分片副本集群
 * shards
 * [
 * {
 * _id: 'shard1',
 * host: 'shard1/127.0.0.1:27010,127.0.0.1:27020',
 * state: 1,
 * topologyTime: Timestamp({ t: 1677416126, i: 5 })
 * },
 * {
 * _id: 'shard2',
 * host: 'shard2/127.0.0.1:27030,127.0.0.1:27040',
 * state: 1,
 * topologyTime: Timestamp({ t: 1677419293, i: 1 })
 * },
 * {
 * _id: 'shard3',
 * host: 'shard3/127.0.0.1:27050,127.0.0.1:27060',
 * state: 1,
 * topologyTime: Timestamp({ t: 1677418571, i: 6 })
 * }
 * ]
 * <p>
 * MongoDb database
 * db.createCollection("goods")
 * db.device.ensureIndex({createTime:1})
 * sh.shardCollection("budin.goods", {goodsId:"hashed"}, false, { numInitialChunks: 6} )
 */
public class MongoDBClient {

    private final static String host = "10.112.49.187";
    private final static int port = 20000;

    public MongoDBClient() {

    }

    public static MongoClient connect() {
        List<ServerAddress> servers=new ArrayList<>();
        servers.add(new ServerAddress(host,port));
        return connect(servers);
    }

    public static MongoClient connect(List<ServerAddress> servers) {
        MongoClientSettings.Builder builder = MongoClientSettings.builder();
        builder.applyToClusterSettings(
                build -> build.hosts(servers));
        return MongoClients.create(builder.build());
    }

    public static void main(String[] args) {
//        MongoClient mongoClient = MongoDBClient.connect();
        MongoClient mongoClient=MongoClients.create("mongodb://"+host+":"+"20000");
        MongoDatabase budin = mongoClient.getDatabase("budin");
        MongoCollection<Document> connect = budin.getCollection("goods");
        List<Document> documents=new ArrayList<>();
        BudinSubscriber<Object> subscriber = new BudinSubscriber<>();
        documents.add(new Document().append("goodsId","15").append("type","book").append("publish","bupt").append("name","txyl"));
        connect.find(documents.get(0)).subscribe(subscriber);
        connect.find(documents.get(0)).subscribe(subscriber);
        try {
            List<Object> objects = subscriber.get();
            System.out.println(objects);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
