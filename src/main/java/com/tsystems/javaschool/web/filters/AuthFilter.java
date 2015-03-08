package com.tsystems.javaschool.web.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/AuthenticationFilter")
public class AuthFilter implements Filter {
    private final static Logger logger = Logger.getLogger(AuthFilter.class);

    private ServletContext context;

    public void init(FilterConfig fConfig) throws ServletException {
        this.context = fConfig.getServletContext();
        logger.info("AuthenticationFilter initialized");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        req.setCharacterEncoding("UTF-8");
        res.setContentType("text/html;charset=UTF-8");

        HttpSession session = req.getSession(false);
        Object roles;
        try {
            roles = session.getAttribute("roles");
        } catch (NullPointerException e) {
            roles = null;
        }

        String uri = req.getRequestURI();
        logger.debug("Requested Resource: " + uri);


        if (roles == null) {
            if (!uri.contains("index.jsp") && !uri.contains("login")
                    && !uri.contains(".css") && !uri.contains(".js") && !uri.contains("loginError.jsp")) {
                logger.debug("Unauthorized access request");
                res.sendRedirect(req.getContextPath() + "/index.jsp");
                return;
            }
        }
        logger.debug("Request accessed successfully");
        chain.doFilter(request, response);
    }

    public void destroy() {
        //close any resources here
    }
}
