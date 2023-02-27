package indiv.budin.demo.client.controller;

import indiv.budin.demo.server.api.MongoDBService;
import indiv.budin.ioc.annotations.IocController;
import indiv.budin.ioc.annotations.IocRequestMapping;
import indiv.budin.rpc.irpc.annotation.RpcAutowire;
import indiv.budin.rpc.irpc.common.concurent.BudinSubscriber;
import org.bson.Document;

import java.util.List;

/**
 * @author
 * @date 2023/2/27 19 13
 * discription
 */
@IocController
public class MongoDBController {
    @RpcAutowire(serviceName = "indiv.budin.demo.server.api.MongoDBService")
    private MongoDBService mongoDBService;

    @IocRequestMapping(url = "/insert")
    public void insertMany() {
        for (int i = 0; i < 1000; i++) {
            Document document = new Document().append("goodsId", String.valueOf(i + 20)).append("type", "book").append("name", "book" + (i + 20));
            mongoDBService.insert(document);
        }
        List<Object> objects = mongoDBService.find(new Document().append("goodsId", "1"));
        try {
            System.out.println(objects.size());
            for (Object o : objects) {
                System.out.println(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
