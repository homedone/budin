//package indiv.budin.gateway.filter;
//
//import com.netflix.zuul.ZuulFilter;
//import com.netflix.zuul.context.RequestContext;
//import com.netflix.zuul.exception.ZuulException;
//import indiv.budin.gateway.utils.PassWebUtil;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.HttpClients;
//import org.springframework.stereotype.Component;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//;
//
///**
// * @author
// * @date 2022/11/3 17 20
// * discription
// */
////@Component
//public class SSOFilter extends ZuulFilter {
//    Logger logger = LoggerFactory.getLogger(SSOFilter.class);
//
//    @Override
//    public String filterType() {
//        return "pre";
//    }
//
//    @Override
//    public int filterOrder() {
//        return 0;
//    }
//
//    @Override
//    public boolean shouldFilter() {
//        //将login放行
//        RequestContext requestContext = RequestContext.getCurrentContext();
//        HttpServletRequest request = requestContext.getRequest();
//        String servletPath = request.getServletPath();
//        if (PassWebUtil.isPass(servletPath)) {
//            return false;
//        } else logger.info("过滤");
//        return true;
//    }
//
//    @Override
//    public Object run() throws ZuulException {
//        RequestContext currentContext = RequestContext.getCurrentContext();
//        HttpServletRequest request = currentContext.getRequest();
//        HttpServletResponse response = currentContext.getResponse();
//        String token = request.getHeader("Authorization");
//        String url = "http://localhost:3030/center/center/token/check";
//        HttpClient httpClient = HttpClients.createDefault();
//        HttpGet get = new HttpGet(url);
//        try {
//            get.addHeader("Authorization", token);
//            HttpResponse httpResponse = httpClient.execute(get);
//            int statusCode = httpResponse.getStatusLine().getStatusCode();
//            logger.error("error");
//            if (statusCode == 2030) {
//                logger.warn("未登陆");
//            }
//            response.sendRedirect("http://localhost:3010/login");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return null;
//    }
//}
