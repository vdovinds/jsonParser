package ru.vdovin.rest.bitcoin;

import com.google.common.base.Preconditions;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.ws.rs.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class RestEasyProxyInvocationHandler implements InvocationHandler {
    private String uri;
    private RestTemplate rt;

    public RestEasyProxyInvocationHandler(String uri) {
        this.uri = uri;

        rt = new RestTemplate();
        rt.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
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

        final int[] idx = {0};
        List<String> pathParam = new ArrayList<>();
        Stream.of(method.getParameters())
                .forEach((parameter) -> {
                    if (parameter.isAnnotationPresent(QueryParam.class))
                        uriBuilder.queryParam(parameter.getAnnotation(QueryParam.class).value(), args[idx[0]]);
                    if (parameter.isAnnotationPresent(PathParam.class)) {
                        //uriBuilder.build().expand((String)args[idx[0]]); //TODO: почему-то не взлетело :(
                        pathParam.add((String)args[idx[0]]);
                    }
                    idx[0]++;
                });

        String uri = uriBuilder
                        .build()
                        .expand(pathParam.toArray())
                        .toUriString();

        try {
            if (method.isAnnotationPresent(POST.class)) {
                //FIXME: temporarily for test
                String request = "";
                return rt.postForObject(uri, request, method.getReturnType());
            } else {
                return rt.getForObject(uri, method.getReturnType());
            }
        }
        catch (HttpClientErrorException e) {
            throw new IllegalArgumentException("Cant't get " + uri);
        }

    }
}
