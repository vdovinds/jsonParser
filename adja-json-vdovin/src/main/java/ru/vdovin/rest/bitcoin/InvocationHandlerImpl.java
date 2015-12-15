package ru.vdovin.rest.bitcoin;

import com.google.common.base.Preconditions;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class InvocationHandlerImpl implements InvocationHandler {
    private String uri;

    public InvocationHandlerImpl(String uri) {
        this.uri = uri;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Preconditions.checkArgument(method.isAnnotationPresent(Path.class), "Method must have @Path annotation");
        String uri = this.uri + method.getAnnotation(Path.class).value();

        for ( int i=0 ; i < method.getParameters().length; i++ ){
            if (method.getParameters()[i].isAnnotationPresent(PathParam.class)){
                uri = uri.replace("{" + method.getParameters()[i].getAnnotation(PathParam.class).value() + "}",(String)args[i]);
            }
        }

        RestTemplate rt = new RestTemplate();
        return rt.getForObject(uri, method.getReturnType());

    }
}
