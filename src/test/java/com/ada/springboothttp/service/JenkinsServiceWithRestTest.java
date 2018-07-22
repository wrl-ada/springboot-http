package com.ada.springboothttp.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith ( SpringRunner.class )
@SpringBootTest
public class JenkinsServiceWithRestTest {
    @Autowired
    private JenkinsServiceWithRest rest;
    private String name = "rest001";

    @Test
    public void createJob() {
        rest.createJob ( name );
    }

    @Test
    public void buildJob() {
        rest.buildJob ( name,null );
    }

    @Test
    public void getBuild() {
        rest.getBuild ( name );
    }

    @Test
    public void deleteJob() {
        rest.deleteJob ( name );
    }
}