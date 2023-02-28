package indiv.budin.demo.client.controller;

import indiv.budin.demo.server.api.MongoDBService;
import indiv.budin.ioc.annotations.IocController;
import indiv.budin.ioc.annotations.IocRequestMapping;
import indiv.budin.ioc.annotations.IocRequestParam;
import indiv.budin.rpc.irpc.annotation.RpcAutowire;
import indiv.budin.rpc.irpc.common.concurent.BudinSubscriber;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author
 * @date 2023/2/27 19 13
 * discription
 */
@IocController
public class MongoDBController {
    Logger logger=LoggerFactory.getLogger(MongoDBController.class);
    @RpcAutowire(serviceName = "indiv.budin.demo.server.api.MongoDBService")
    private MongoDBService mongoDBService;

    @IocRequestMapping(url = "/insert")
    public void insertMany() {
        for (int i = 1000; i < 1100; i++) {
            Document document = new Document().append("goodsId", String.valueOf(i + 20)).append("type", "book").append("name", "book" + (i + 20));
            logger.info("------------ 第 "+(i-1000)+" 条rpc请求--------------");
            mongoDBService.insert(document);
        }
    }
    @IocRequestMapping(url = "/find")
    public void find(@IocRequestParam("key") String key,@IocRequestParam("val") String val){
        List<Object> objects = mongoDBService.find(new Document().append(key, val));
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
