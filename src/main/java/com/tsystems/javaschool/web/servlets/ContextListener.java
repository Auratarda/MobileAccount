package com.tsystems.javaschool.web.servlets;

import com.tsystems.javaschool.persistence.PersistenceUtil;
import com.tsystems.javaschool.services.ClientService;
import com.tsystems.javaschool.services.ClientServiceImpl;
import com.tsystems.javaschool.services.OperatorService;
import com.tsystems.javaschool.services.OperatorServiceImpl;

import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent event) {

        ServletContext sc = event.getServletContext();
        EntityManager em = PersistenceUtil.getEntityManager();
        OperatorService operatorService = new OperatorServiceImpl(em);
        ClientService clientService = new ClientServiceImpl(em);
        sc.setAttribute("operatorService", operatorService);
        sc.setAttribute("clientService", clientService);

    }

    /**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
    }
}
