package ru.nojs.jetty;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import ru.vdovin.jetty.HelloHandler;
import ru.vdovin.jetty.HelloServlet;

public class JettyTest {

    private final static Server server = new Server(8080);

    @BeforeClass
    public static void init() throws Exception {

        ServletContextHandler contextHelloServlet = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHelloServlet.setContextPath("/hello");
        contextHelloServlet.addServlet(new ServletHolder(new HelloServlet()), "/*");

        /*ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{contextHelloServlet, new HelloHandler()});
        server.setHandler(contexts);*/

        server.setHandler(contextHelloServlet);
        server.start();
        server.join();
    }

    @Ignore
    @Test
    public void testHelloHandler() throws Exception {
        Server server = new Server(8080);
        server.setHandler(new HelloHandler());
        server.start();
        server.join();
    }

    @Test
    public void testStart() throws Exception {

    }


}
