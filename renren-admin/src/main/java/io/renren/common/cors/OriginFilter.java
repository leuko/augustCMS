package io.renren.common.cors;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by smileMAC on 18/8/6.
 */
@Component
public class OriginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE,PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        chain.doFilter(req, response);
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

}
