package com.bzz.cloud.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@RestController
public class ClientController {

    @Autowired
    private DataSource dataSource;

    @Autowired ClientDetailsService clientDetailsService;


    @GetMapping("/api/getClientsById")
    public ClientDetails getClient(String clientId){
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        return clientDetails;
    }
}
