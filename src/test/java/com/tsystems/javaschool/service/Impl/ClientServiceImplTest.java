package com.tsystems.javaschool.service.Impl;

import com.tsystems.javaschool.domain.dao.ClientDAO;
import com.tsystems.javaschool.domain.entities.Client;
import com.tsystems.javaschool.service.exceptions.ClientNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ClientServiceImplTest {
    @InjectMocks
    ClientServiceImpl clientService;
    @Mock
    private ClientDAO mockClientDAO;


    @Before
    public void init() throws ClientNotFoundException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLogin() throws Exception {
        String email = "ivan@ya.ru";
        String password = "ivan";
        Client client = new Client();
        client.setEmail(email);
        Mockito.when(mockClientDAO.login(email, password)).thenReturn(client);

        client = clientService.login(email, password);

        Assert.assertEquals(email, client.getEmail());
    }
}