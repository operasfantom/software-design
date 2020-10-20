package ru.akirakozov.sd.refactoring;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseHandler {
    private HttpServletResponse response;
    private StringBuilder builder = new StringBuilder();

    public void reset(HttpServletResponse response) {
        this.response = response;
        builder = new StringBuilder();
    }

    public void writeln(String string) {
        builder.append(string);
    }

    public void flush(int statusCode, boolean isHtml) throws IOException {
        if (isHtml) {
            response.getWriter().println("<html><body>");
        }
        response.getWriter().println(builder.toString());
        if (isHtml) {
            response.getWriter().println("</body></html>");
        }
        response.setContentType("text/html");
        response.setStatus(statusCode);
    }
}
