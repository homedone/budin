package indiv.budin.demo.server.component;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import org.bson.Document;

import java.util.List;

/**
 * @author
 * @date 2023/2/27 18 20
 * discription
 */
public class MongoDbOperator {
    private final MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> collection;

    public MongoDbOperator(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public MongoDbOperator changeDatabase(String database) {
        mongoDatabase = mongoClient.getDatabase(database);
        return this;
    }

    public MongoDbOperator changeCollection(String s) {
        collection=mongoDatabase.getCollection(s);
        return this;
    }

    public MongoCollection<Document> getCollection() {
        return collection;
    }
}
