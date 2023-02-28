package indiv.budin.demo.server.service;

import ch.qos.logback.classic.Level;
import indiv.budin.demo.server.api.MongoDBService;
import indiv.budin.demo.server.component.MongoDBClient;
import indiv.budin.demo.server.component.MongoDbOperator;
import indiv.budin.ioc.annotations.IocService;
import indiv.budin.rpc.irpc.annotation.RpcService;
import indiv.budin.rpc.irpc.common.concurent.BudinSubscriber;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;

/**
 * @author
 * @date 2023/2/27 18 59
 * discription
 */
@Slf4j
@IocService
@RpcService(serviceName = "indiv.budin.demo.server.api.MongoDBService")
public class MongoDBServiceImpl implements MongoDBService {
    BudinSubscriber<Object> budinSubscriber = new BudinSubscriber<>(100);

    static {
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory
                .getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.INFO);
    }

    private final MongoDbOperator mongoDbOperator = new MongoDbOperator(MongoDBClient.connect()).changeDatabase("budin").changeCollection("goods");

    @Override
    public void insert(Document document) {
        mongoDbOperator.getCollection().insertOne(document).subscribe(budinSubscriber);
    }

    @Override
    public void insert(List<Document> documents) {
        mongoDbOperator.getCollection().insertMany(documents).subscribe(budinSubscriber);
    }

    @Override
    public List<Object> find(Document document) {
        BudinSubscriber<Object> subscriber = new BudinSubscriber<>();
        mongoDbOperator.getCollection().find(document).subscribe(subscriber);
        try {
            log.info(String.valueOf(budinSubscriber.getReceives().size()));
            List<Object> objects = subscriber.get();
            return objects;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(Bson bson) {
        mongoDbOperator.getCollection().deleteOne(bson).subscribe(budinSubscriber);
    }
}
