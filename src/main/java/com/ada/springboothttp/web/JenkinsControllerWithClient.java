package com.ada.springboothttp.web;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.FileRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/client")
public class JenkinsControllerWithClient {

    @RequestMapping( "/createJob" )
    public void test() throws IOException {
        String server = "127.0.0.1:8080";
        String jenkinsHost = "http://" + server + "/";
        String projectName = "test001";
        String xml = this.getClass ().getClassLoader ().getResource ( "demo.xml" ).getPath ();
        String username = "admin";
        String apiToken = "admin";
        HttpClient client = new HttpClient ();
        client.getState().setCredentials(
                new AuthScope (AuthScope.ANY_HOST,AuthScope.ANY_PORT),
                new UsernamePasswordCredentials(username,apiToken)
        );
        client.getParams ().setAuthenticationPreemptive ( true );
        PostMethod post = new PostMethod ( jenkinsHost + "/createItem?name=" + projectName );
        post.setDoAuthentication ( true );
        /**
         * 使用File传递参数，能保证jenkins的pipline格式
         * 若读取jenkinsfile，可以用string传参
         */
        File fileInput = new File ( xml );
        RequestEntity requestEntity = new FileRequestEntity ( fileInput, "text/xml; charset=UTF-8" );
        post.setRequestEntity ( requestEntity );
        int code = client.executeMethod ( post );
        System.out.print ( "code----->" );
        System.out.print ( code );

        System.out.print ( post.getResponseBodyAsString () );
    }

    @RequestMapping("/buildTest")
    public void test2() throws IOException {
        String server = "127.0.0.1:8080";
        String jenkinsHost = "http://" + server + "/";
        String projectName = "test001";

        String username = "admin";
        String apiToken = "admin";
        HttpClient client = new HttpClient ();
        client.getState().setCredentials(
                new AuthScope (AuthScope.ANY_HOST,AuthScope.ANY_PORT),
                new UsernamePasswordCredentials (username,apiToken)
        );
        client.getParams ().setAuthenticationPreemptive ( true );
        PostMethod post = new PostMethod ( jenkinsHost + "/job/" + projectName+"/build" );
        post.setDoAuthentication ( true );
        int code = client.executeMethod ( post );
        System.out.print ( "code----->" );
        System.out.print ( code );
        System.out.print ( post.getResponseBodyAsString () );
    }

    @RequestMapping("/getBuild")
    public void test3() throws IOException {
        String server = "127.0.0.1:8080";
        String jenkinsHost = "http://" + server + "/";
        String projectName = "test001";

        String username = "admin";
        String apiToken = "be21e4c07f8969754cf9e26c014e0b95";
        HttpClient client = new HttpClient ();
//        client.getState().setCredentials(
//                new AuthScope(),
//                new UsernamePasswordCredentials(username,apiToken)
//        );
        //  client.getParams ().setAuthenticationPreemptive ( true );
        GetMethod get = new GetMethod ( jenkinsHost+"/job/"+projectName+"/api/json" );
        // getMethod get = new PostMethod ( jenkinsHost + "/job/" + projectName+"/api/json" );
        //  post.setDoAuthentication ( true );
        //File fileInput = new File ( configurationFile );
        //RequestEntity requestEntity = new FileRequestEntity ( fileInput, "text/xml; charset=UTF-8" );
        //post.setRequestEntity ( requestEntity );
        //post.setRequestHeader("","");
        int code = client.executeMethod ( get );
        System.out.print ( "code----->" );
        System.out.print ( code );
        System.out.print ( get.getResponseBody () );
    }
}
