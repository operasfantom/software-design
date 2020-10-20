package ru.akirakozov.sd.refactoring;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;

/**
 * @author akirakozov
 */
public class Server {
    private final org.eclipse.jetty.server.Server server;
    private final DatabaseRequest databaseRequest;
    private final ServletContextHandler context;

    public Server(int port) {
        this.databaseRequest = new DatabaseRequest();
        this.server = new org.eclipse.jetty.server.Server(port);
        this.context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    }

    public static void main(String[] args) throws Exception {
        assert args != null : "args are null";
        assert args.length == 1 : "args length is wrong:" + args.length;
        int port = Integer.parseInt(args[0]);
        Server server = new Server(port);
        server.start();
        server.join();
    }

    public void start() throws Exception {
        databaseRequest.createDatabase();
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(new ServletHolder(new AddProductServlet(databaseRequest)), "/add-product");
        context.addServlet(new ServletHolder(new GetProductsServlet(databaseRequest)), "/get-products");
        context.addServlet(new ServletHolder(new QueryServlet(databaseRequest)), "/query");
        server.start();
    }

    public void join() throws InterruptedException {
        server.join();
    }
}
