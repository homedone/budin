# budin ![logo](budin-mini.png)

本项目是一个基于 Netty 的简易 RPC 框架项目(Netty + Nacos + Kyro / Hession)，作者是个技术菜鸟，本项目是作者学习IOC、AOP与RPC过程中，从别的地方东拼西凑，然后改出来的，供有兴趣的朋友参考。  
如果想学习RPC的话，还是推荐直接看
[guide-rpc-framework](https://github.com/Snailclimb/guide-rpc-framework) 和 [Dubbo](https://github.com/apache/dubbo)


核心内容：[budin-rpc](https://github.com/homedone/budin/tree/fileserver/budin-rpc)
实现了RPC Server 与 Client，[budin-framework-ioc](https://github.com/homedone/budin/tree/fileserver/budin-framework-ioc)
实习了简易的 IOC 控制，简易AOP，以及基于 Tomcat 的快速启动，两个模块都有Demo示例

PS: 除此之外，其他部分是一个简易云盘的雏形，OSS用的Minio，用gateway做了一个简易JWT登陆鉴权，可以进行简单的上传文件，其实最初想做个云盘来着，不知不觉走向了简易框架的实现
### budin-rpc

这部分参考的 [guide-rpc-framework](https://github.com/Snailclimb/guide-rpc-framework) (建议)

#### 较小的不同点

1. 注册中心：guide-rpc用 zookeeper (curator)，这里为了简单化，直接用了 Nacos SDK
2. 简单整合了IOC+AOP，基于注解配置的快速启动( @RpcApplication )，实际上也可以借用 Spring 容器 (guide-rpc)
3. 简单内容可配置化，如 Ip 和Port，也可以用 @IocConfiguration 自动装配

### budin-framework-ioc

该部分实现了简易的容器管理，并能够进行简易的代理增强，内嵌Tomcat，使用方式基本类似于Springboot (
注意这里只是简易实现！！！内部结构天差地别！！！)

#### 快速启动

**实现一个服务**：类Spring方式，在 @IocController 注解的类下，实现 @IocRequestMapping(url = " ") 注解的方法。

**启动**：类Springboot启动，配置application.yml文件，然后添加 @IocApplication 注解后，通过main方法中调用
BudinIocApplication.run(xxx.class) 方法 快速启动。

### 使用
类SpringBoot的启动方式，可以参考 Demo，(Nacos需要手动启动)

*配置文件*
~~~ yaml
# application.yml 文件
budin:
  server:
    host: xxx
    port: xxx
  center:
    host: xxx
    port: 8848 # Nacos默认是8848
~~~

*启动类*
~~~java
/**
 *  @RpcClientApplication 该注解作用下 服务作为 Tomcat Server，也作为 Netty Client
 * @RpcApplication 该注解作用下 服务作为 Netty Server，也是 Netty Client
 */

/**
 * 服务启动类
 */
@RpcApplication
public class Application {
    public static void main(String[] args) {
        BudinRpcApplication.run(Application.class);
    }
}

/**
 * 客户端启动类
 */
@RpcClientApplication
public class ClientApplication {
    public static void main(String[] args) {
        BudinRpcClientApplication.run(ClientApplication.class);
    }
}

~~~
*服务与消费类*
~~~java
/**
 * 服务类与服务方法，这里使用了MongoDb的SDK，Client
 */

@IocService
@RpcService(serviceName = "indiv.budin.demo.server.api.MongoDBService")
public class MongoDBServiceImpl implements MongoDBService {
    BudinSubscriber<Object> budinSubscriber = new BudinSubscriber<>(100);

    private final MongoDbOperator mongoDbOperator = new MongoDbOperator(MongoDBClient.connect()).changeDatabase("budin").changeCollection("goods");

    @Override
    public void insert(Document document) {
        mongoDbOperator.getCollection().insertOne(document).subscribe(budinSubscriber);
    }
}


/**
 * 消费类与消费方法，向mongoDb中插入数据
 */
@IocController
public class MongoDBController {
    @RpcAutowire(serviceName = "indiv.budin.demo.server.api.MongoDBService")
    Logger logger=LoggerFactory.getLogger(MongoDBController.class);
    private MongoDBService mongoDBService;

    @IocRequestMapping(url = "/insert")
    public void insertMany() {
        for (int i = 1; i < 100; i++) {
            Document document = new Document().append("goodsId", String.valueOf(i)).append("type", "book").append("name", "book" + i);
            logger.info("------------ 第 "+(i)+" 条rpc请求--------------");
            mongoDBService.insert(document);
        }
    }
}


~~~
