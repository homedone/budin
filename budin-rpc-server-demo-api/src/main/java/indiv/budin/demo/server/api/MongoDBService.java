package indiv.budin.demo.server.api;

import indiv.budin.rpc.irpc.common.concurent.BudinSubscriber;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;

public interface MongoDBService {
    public void insert(Document document);
    public void insert(List<Document> documents);
    public List<Object> find(Document document);
    public void delete(Bson bson);
}
