package com.sesh.demo.filters;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@Log4j2
@Order(1)
public class CORSFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        Enumeration<String> headerNames = req.getHeaderNames();

        String headerName;
        StringBuilder sb = new StringBuilder();
        String origin = req.getHeader("Origin");
        if (origin != null) {
            //   boolean matches = Pattern.matches(configuration.getCrossdomain(), origin);

            HttpServletResponse resp = (HttpServletResponse) response;
            resp.setHeader("Access-Control-Allow-Headers", "appname, content-type, token, x-auth-token, transactionId, authorization, clientId");
            resp.setHeader("Access-Control-Expose-Headers", "authorization, clientId, transactionId");
            resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE");
            resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
            resp.setHeader("Access-Control-Max-Age", "1800");

            sb.append("passsed CORS " + " " + req.getHeader("Origin") + " " + req.getMethod() + " " + req.getRequestURI());
            sb.append("\n");
            while (headerNames.hasMoreElements()) {
                headerName = headerNames.nextElement();
                sb.append("\t" + headerName);
                sb.append("=");
                sb.append(req.getHeader(headerName));
                sb.append("\n");
            }
            log.debug(sb.toString());
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
