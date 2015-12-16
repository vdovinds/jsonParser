package ru.vdovin.rest.bitcoin;

import com.google.common.base.Preconditions;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RestEasyProxyInvocationHandler implements InvocationHandler {
    private String uri;
    private static final RestTemplate rt = new RestTemplate();

    public RestEasyProxyInvocationHandler(String uri) {
        this.uri = uri;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Preconditions.checkArgument(
                (method.isAnnotationPresent(GET.class) || (method.isAnnotationPresent(POST.class)))
                , "Method must have @GET or @POST annotation");

        UriComponentsBuilder uriBuilder  = UriComponentsBuilder.fromHttpUrl(this.uri);
        if (method.isAnnotationPresent(Path.class)) {
            uriBuilder.path(method.getAnnotation(Path.class).value());
        }

        String uri = uriBuilder.build().toUriString();

        Map<String, String> param = new HashMap<>();
        for ( int i=0 ; i < method.getParameters().length; i++ ){
            if (method.getParameters()[i].isAnnotationPresent(PathParam.class)){
                param.put( method.getParameters()[i].getAnnotation(PathParam.class).value(), (String)args[i]);
            }
        }

        if (method.isAnnotationPresent(POST.class)) {
            //FIXME: temporarily for test
            String request = "";
            return rt.postForObject(uri, request, method.getReturnType(), param);
        }
        else {
            return rt.getForObject(uri, method.getReturnType(), param);
        }

    }
}
