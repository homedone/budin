package indiv.budin.ioc.containers;

import indiv.budin.ioc.annotations.IocController;
import indiv.budin.ioc.annotations.IocRequestMapping;
import indiv.budin.ioc.annotations.IocRequestParam;
import indiv.budin.ioc.constants.WebMessage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class WebContainer extends HttpServlet {
    private AnnotationContainer annotationContainer;

    private Map<String, Method> methodHandler;
    private Map<String, Object> urlController;

    private String containerName;


    public WebContainer() {
        annotationContainer = (AnnotationContainer) AnnotationContainer.getInstance();
        methodHandler = new HashMap<>();
        urlController = new HashMap<>();
    }

    public static WebContainer create() {
        return new WebContainer();
    }

    public WebContainer build(){
        init();
        return this;
    }

    public void init() {
        Set<Class<?>> classesByAnnotation = annotationContainer.getClassesByAnnotation(IocController.class);
        //映射url-method
        for (Class<?> clazz : classesByAnnotation) {
            for (Method method : clazz.getDeclaredMethods()) {
                method.setAccessible(true);
                if (!method.isAnnotationPresent(IocRequestMapping.class)) continue;
                IocRequestMapping annotation = method.getAnnotation(IocRequestMapping.class);
                String url = annotation.url();
//                System.out.println(url);
                methodHandler.put(url, method);
                urlController.put(url, annotationContainer.getBean(clazz.getName()));
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Object res = doDispatcher(req, resp);
            if (res==null) return;
            resp.getWriter().write(res.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object doDispatcher(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
//        System.out.println(requestURI);
        if (!methodHandler.containsKey(requestURI)) {
            response.setStatus(404);
            response.getWriter().write(WebMessage.URL_NOT_FOUND);
            return null;
        }
        Method method = methodHandler.get(requestURI);
        Class<?>[] parameterTypes = method.getParameterTypes();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        Map<String, String[]> parameterMap = request.getParameterMap();
        List<Object> parameterValues = new ArrayList<>();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (int j = 0; j < parameterAnnotations[i].length; j++) {
                if (parameterAnnotations[i][j].annotationType().equals(IocRequestParam.class)) {
                    IocRequestParam iocRequestParam = (IocRequestParam) parameterAnnotations[i][j];
                    if (!parameterMap.containsKey(iocRequestParam.value())) {
                        throw new RuntimeException();
                    }
                    parameterValues.add(parameterMap.get(iocRequestParam.value())[0]);
                    break;
                }
            }
        }
        Object[] objects = parameterValues.toArray();

        Object obj = urlController.get(requestURI);
        try {
            return method.invoke(obj, objects);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }
}
