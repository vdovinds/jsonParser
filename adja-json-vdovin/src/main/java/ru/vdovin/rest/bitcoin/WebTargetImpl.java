package ru.vdovin.rest.bitcoin;

import ru.nojs.rest.bitcoin.WebTarget;

import java.lang.reflect.Proxy;

public class WebTargetImpl implements WebTarget {

    public String uri;

    public WebTargetImpl(String uri){
        this.uri = uri;
    }

    @Override
    public <T> T proxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandlerImpl(uri));
    }
}
