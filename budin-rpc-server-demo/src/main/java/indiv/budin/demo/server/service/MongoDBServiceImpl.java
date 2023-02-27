package indiv.budin.demo.server.service;

import indiv.budin.demo.server.api.MongoDBService;
import indiv.budin.demo.server.component.MongoDBClient;
import indiv.budin.demo.server.component.MongoDbOperator;
import indiv.budin.ioc.annotations.IocService;
import indiv.budin.rpc.irpc.annotation.RpcService;
import indiv.budin.rpc.irpc.common.concurent.BudinSubscriber;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;

/**
 * @author
 * @date 2023/2/27 18 59
 * discription
 */
@IocService
@RpcService(serviceName = "indiv.budin.demo.server.api.MongoDBService")
public class MongoDBServiceImpl implements MongoDBService {
    BudinSubscriber<Object> budinSubscriber= new BudinSubscriber<>(100);
    private final MongoDbOperator mongoDbOperator=new MongoDbOperator(MongoDBClient.connect()).changeDatabase("budin").changeCollection("goods");
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
        mongoDbOperator.getCollection().find(document).subscribe(budinSubscriber);
        try{
            return budinSubscriber.getReceives();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(Bson bson) {
        mongoDbOperator.getCollection().deleteOne(bson).subscribe(budinSubscriber);
    }
}
