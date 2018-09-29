package cn.sibat.iceflow.image.server.controller.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 配置ajax跨域访问
 * @author iceflow
 */
@Component
public class CorsFilter implements Filter {
    private static final String[] origins={"http://localhost:9502","http://192.168.40.126:9502","http://58.251.157.179:60400"};

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        String myOrigin = request.getHeader("origin");

        String setOrigin = "";
        for (String origin : origins) {
            if (origin.equals(myOrigin)) {
                setOrigin = origin;
            }
        }
        if (!StringUtils.isBlank(setOrigin)) {
            response.setHeader("Access-Control-Allow-Origin", setOrigin);
        }

        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Credentials");
        response.setHeader("Access-Control-Expose-Headers", "Set-Cookie");
        //response.setHeader("Content-Type", Constants.APPLICATION_JSON_UTF8);
        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}