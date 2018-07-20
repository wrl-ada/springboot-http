package com.ada.springboothttp.service;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.FileRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

@Service
public class JenkinsServiceWithClient {

    private static final Logger LOGGER=LoggerFactory.getLogger ( JenkinsServiceWithClient.class );

    @Value( "${jenkins.url}" )
    private String jenkinsAPI;

    @Resource( name="jenkinsClient" )
    private HttpClient client;

    /**
     * Create job
     *
     * @param jobName
     * @return
     * @throws IOException
     */
    public int createJob(String jobName) throws IOException {
        String xml=this.getClass ().getClassLoader ().getResource ( "demo.xml" ).getPath ();
        PostMethod post=new PostMethod ( jenkinsAPI + "/createItem?name=" + jobName );
        post.setDoAuthentication ( true );
        /**
         * 使用File传递参数，能保证jenkins的pipline格式
         * 若读取jenkinsfile，可以用string传参
         */
        File fileInput=new File ( xml );
        RequestEntity requestEntity=new FileRequestEntity ( fileInput, "text/xml; charset=UTF-8" );
        post.setRequestEntity ( requestEntity );
        int code=client.executeMethod ( post );
        LOGGER.info ( "code: {}", code );
        LOGGER.info ( "Client response body: {}", post.getResponseBodyAsString () );
        return code;
    }

    /**
     * Build job
     *
     * @param jobName
     * @return
     * @throws IOException
     */
    public int buildJob(String jobName) throws IOException {
        PostMethod post=new PostMethod ( jenkinsAPI + "/job/" + jobName + "/build" );
        post.setDoAuthentication ( true );
        int code=client.executeMethod ( post );
        LOGGER.info ( "code: {}", code );
        LOGGER.info ( "Client response body: {}", post.getResponseBodyAsString () );
        return code;
    }

    /**
     * Get build result
     *
     * @param jobName
     * @return
     * @throws IOException
     */
    public int getBuild(String jobName) throws IOException {
        GetMethod get=new GetMethod ( jenkinsAPI + "/job/" + jobName + "/api/json" );
        int code=client.executeMethod ( get );
        LOGGER.info ( "code: {}", code );
        LOGGER.info ( "Client response body: {}", get.getResponseBody () );
        return code;
    }
}
