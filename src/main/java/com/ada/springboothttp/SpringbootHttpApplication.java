package com.ada.springboothttp;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SpringbootHttpApplication {

    @Value( "${jenkins.user}" )
    private String user;
    @Value( "${jenkins.pass}" )
    private String pass;

    public static void main(String[] args) {
        SpringApplication.run ( SpringbootHttpApplication.class, args );
    }

    /**
     * Get RestTemplate
     *
     * @return
     */
    @Bean( name="jenkinsRestTemplate" )
    public RestTemplate getTemplate() {
        RestTemplate template=null;
        if (user == null || pass == null) {
            //带身份认证的RestTemplate
            template=new RestTemplate ();
        } else {
            //带身份认证的RestTemplate
            RestTemplateBuilder builder=new RestTemplateBuilder ();
            template=builder.basicAuthorization ( user, pass ).build ();
        }
        return template;
    }

    /**
     * Get client
     */
    @Bean( name="jenkinsClient" )
    public HttpClient getClient() {
        HttpClient client=new HttpClient ();
        client.getState ().setCredentials (
                new AuthScope ( AuthScope.ANY_HOST, AuthScope.ANY_PORT ),
                new UsernamePasswordCredentials ( user, pass )
        );
        client.getParams ().setAuthenticationPreemptive ( true );
        return client;
    }
}
