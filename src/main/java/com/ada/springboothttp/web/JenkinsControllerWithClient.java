package com.ada.springboothttp.web;

import com.ada.springboothttp.service.JenkinsServiceWithClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping( "/client" )
public class JenkinsControllerWithClient {
    private String jobName="test002";
    @Autowired
    private JenkinsServiceWithClient service;

    @RequestMapping( "/createJob" )
    public void create() throws IOException {
        service.createJob ( jobName );
    }

    @RequestMapping( "/buildTest" )
    public void build() throws IOException {
        service.buildJob ( jobName );
    }

    @RequestMapping( "/getBuild" )
    public void get() throws IOException {
        service.getBuild ( jobName );
    }
}
