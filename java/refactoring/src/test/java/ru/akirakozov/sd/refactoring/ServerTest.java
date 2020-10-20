package ru.akirakozov.sd.refactoring;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static javax.servlet.http.HttpServletResponse.SC_OK;

public class ServerTest {
    public static final int PORT = 8081;
    private Server server;
    private WebTarget target;

    @BeforeEach
    public void initServer() throws Exception {
        DatabaseRequest databaseRequest = new DatabaseRequest();
        databaseRequest.dropDatabase();
        server = new Server(PORT, databaseRequest);
        server.start();
        target = ResteasyClientBuilder.newClient()
                .target("http://localhost:" + PORT);
    }

    @AfterEach
    public void stopServer() throws Exception {
        server.stop();
    }

    private void assertCreated(String a, int i) {
        try (Response response = createProduct(a, i)) {
            Assertions.assertEquals(SC_CREATED, response.getStatus());
            String body = response.readEntity(String.class);
            Assertions.assertEquals("OK", body.trim());
        }
    }

    private void assertGet(String name, String count, boolean shouldContains) {
        try (Response response = getProducts()) {
            Assertions.assertEquals(SC_OK, response.getStatus());
            String body = response.readEntity(String.class);
            Assertions.assertEquals(shouldContains, body.contains(name + "\t" + count));
        }
    }

    private void assertQuery(String query, String bodyPart) {
        try (Response response = getStatistics(query)) {
            Assertions.assertEquals(SC_OK, response.getStatus());
            String body = response.readEntity(String.class);
            Assertions.assertTrue(body.contains(bodyPart));
        }
    }

    @Test
    public void createTest() {
        assertCreated("a", 1);
        assertCreated("b", 2);
        assertCreated("a", 1);
    }

    @Test
    public void getTest() {
        assertCreated("a", 1);
        assertCreated("b", 2);
        assertCreated("a", 2);
        assertCreated("a", 1);
        assertGet("a", "1", true);
        assertGet("a", "2", true);
        assertGet("b", "2", true);
        assertGet("c", "3", false);
    }

    @Test
    public void queryTest() {
        assertCreated("a", 1);
        assertCreated("b", 2);
        assertQuery("max", "b\t2");
        assertQuery("min", "a\t1");
        assertQuery("sum", "3");
        assertQuery("count", "2");
    }

    private Response createProduct(String name, long price) {
        return target.path("/add-product")
                .queryParam("name", name)
                .queryParam("price", price)
                .request().get();
    }

    private Response getProducts() {
        return target.path("/get-products")
                .request().get();
    }

    private Response getStatistics(String stat) {
        return target.path("/query")
                .queryParam("command", stat)
                .request().get();
    }
}
