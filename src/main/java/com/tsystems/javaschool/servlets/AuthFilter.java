package com.tsystems.javaschool.servlets;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class AuthFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(AuthFilter.class);
    private String pathToBeIgnored;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        pathToBeIgnored = filterConfig.getInitParameter("pathToBeIgnored");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String uri = req.getRequestURI();
        LOGGER.debug("Request URI = " + uri);
        HttpSession session = req.getSession(false);
        LOGGER.debug("Session = " + session);

        if (session == null) {
            if (!uri.contains(pathToBeIgnored)) {
                LOGGER.debug("Unauthorized access request");
                res.sendRedirect("index.jsp");
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        this.pathToBeIgnored = null;
    }
}
