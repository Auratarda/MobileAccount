package com.tsystems.javaschool.web.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/AuthenticationFilter")
public class AuthFilter implements Filter {
    private final static Logger logger = Logger.getLogger(AuthFilter.class);

    private ServletContext context;

    public void init(FilterConfig fConfig) throws ServletException {
        this.context = fConfig.getServletContext();
        this.context.log("AuthenticationFilter initialized");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        req.setCharacterEncoding("UTF-8");
        res.setContentType("text/html;charset=UTF-8");

        String uri = req.getRequestURI();
        logger.debug("Requested Resource: " + uri);

        String userId = null;
        Cookie[] cookies = req.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("uid")) {
                    userId = cookie.getValue();
                }
            }
        }
        logger.debug("UserId: " + userId);

        if (userId == null) {
            if (!uri.contains("index.jsp") && !uri.contains("login")
                    && !uri.contains("style.css") && !uri.contains("loginError.jsp")) {
                logger.debug("Unauthorized access request");
                res.sendRedirect("index.jsp");
                return;
            }
        }
        logger.debug("Successful access request");
        chain.doFilter(request, response);
    }

    public void destroy() {
        //close any resources here
    }
}
