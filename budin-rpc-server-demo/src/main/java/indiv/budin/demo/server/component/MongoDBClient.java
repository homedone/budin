package indiv.budin.demo.server.component;


import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoDatabase;

import java.util.ArrayList;

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
    private final MongoDBClient mongoDBClient;

    public MongoDBClient() {
        mongoDBClient = new MongoDBClient();
    }

    public MongoDatabase connect() {
        // 使用一个字符串
        MongoClientSettings.Builder builder = MongoClientSettings.builder();
        return null;
    }


}
