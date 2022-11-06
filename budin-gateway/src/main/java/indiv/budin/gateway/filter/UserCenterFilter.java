package indiv.budin.gateway.filter;


import indiv.budin.common.constants.GatewaySingleton;
import indiv.budin.common.utils.UuidUtil;
import indiv.budin.gateway.utils.PassWebUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * @author
 * @date 2022/11/6 18 16
 * discription
 */
@Order(value = 0)
@Component
public class UserCenterFilter implements GlobalFilter {
    Logger logger = LoggerFactory.getLogger(UserCenterFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        if (!shouldFilter(request)) {

            return chain.filter(exchange);
        }


        String token = request.getHeaders().getFirst("Authorization");
        String url = "http://localhost:3030/center/center/token/check";
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        try {
            get.addHeader("Authorization", token);
            HttpResponse httpResponse = httpClient.execute(get);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            logger.error("error");
            if (statusCode == 2030) {
                logger.warn("未登陆");
                String redirectUrl = "http://localhost:3010/login";
                response.getHeaders().set(HttpHeaders.LOCATION, redirectUrl);
                //303状态码表示由于请求对应的资源存在着另一个URI，应使用GET方法定向获取请求的资源
                response.setStatusCode(HttpStatus.SEE_OTHER);
                response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
                return response.setComplete();
            }
            return chain.filter(exchange);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean shouldFilter(ServerHttpRequest request) {
        //将login放行
        String servletPath = request.getPath().value();
        logger.info(servletPath);
        if (PassWebUtil.isPass(servletPath)) {
            return false;
        } else logger.info("过滤");
        return true;
    }

    private String gatewayEncryption(String secrete) throws NoSuchAlgorithmException {
        byte[] md5s = MessageDigest.getInstance("md5").digest(secrete.getBytes());
        String md5code = new BigInteger(1, md5s).toString(32);
        return md5code;
    }

    public void setSecreteHead(ServerHttpRequest request) throws NoSuchAlgorithmException {
        String uuid = UuidUtil.makeUuid();
        String secreteMd5 = gatewayEncryption(uuid);
        GatewaySingleton.getGateway().setSecrete(secreteMd5);

    }
}
