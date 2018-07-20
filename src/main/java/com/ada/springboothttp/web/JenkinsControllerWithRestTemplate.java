package com.ada.springboothttp.web;


import com.ada.springboothttp.service.JenkinsServiceWithRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/rest" )
public class JenkinsControllerWithRestTemplate {

    @Autowired
    private JenkinsServiceWithRest service;

    private String jobName="test001";

    /**
     * Create jenkins job
     */
    @RequestMapping( "/create" )
    public void createJob() {
        service.createJob ( jobName );
    }

    /**
     * Build job
     */
    @RequestMapping( "/buildJob" )
    public void buildJob() {
        service.buildJob ( jobName, null );
    }

    /**
     * Get build result
     */
    @RequestMapping( "/getBuild" )
    public void getDetail() {
        service.getBuild ( jobName );
    }

    /**
     * delete job
     */
    @RequestMapping( "/deleteJob" )
    public void deleteJob() {
        service.deleteJob ( jobName );
    }
}
