package indiv.budin.gateway.filter;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import indiv.budin.common.constants.GatewaySingleton;
import indiv.budin.common.utils.UuidUtil;
import indiv.budin.gateway.utils.PassWebUtil;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
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
        String url = "http://localhost:3030/center/token/check";
        logger.info(token);
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        try {
            get.addHeader("Authorization", token);
            HttpResponse httpResponse = httpClient.execute(get);
            JSONObject jsonObject = JSON.parseObject(EntityUtils.toString(httpResponse.getEntity()));
            boolean status = (Boolean) jsonObject.get("status");
            logger.info("过滤 " + status);
            if (!status) {
                logger.warn("无权限");
                response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                DataBuffer wrap = response.bufferFactory().wrap(jsonObject.toJSONString().getBytes(StandardCharsets.UTF_8));

                return response.writeWith(Mono.just(wrap));
            }
            return chain.filter(exchange);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean shouldFilter(ServerHttpRequest request) {
        //将login放行
        return !PassWebUtil.isPass(request.getPath().value());
    }

    /**
     * 这里本想给传输加个密，禁止直接访问服务
     *
     * @param secrete
     * @return
     * @throws NoSuchAlgorithmException
     */
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
