package com.ada.springboothttp.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.*;

@RunWith ( SpringRunner.class )
@SpringBootTest
public class JenkinsServiceWithClientTest {
    @Autowired
    private JenkinsServiceWithClient client;
    private String name = "client001";

    @Test
    public void createJob() {
        try {
            client.createJob(name);
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    @Test
    public void buildJob() {
        try {
            client.buildJob ( name );
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    @Test
    public void getBuild() throws IOException {
        client.buildJob ( name );
    }
}