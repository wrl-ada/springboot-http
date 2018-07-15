package com.ada.springboothttp.web;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import net.sf.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URI;

@RestController
@RequestMapping("/rest")
public class JenkinsControllerWithRestTemplate {


    @RequestMapping("/buildJob")
    public void build() throws IOException {
  //      buildJob ( "test",false,null );
    }

//    public void buildJob(String jobName ,Boolean isNeedParams ,String buildJobParams) throws ClientProtocolException, IOException{
//        //jenkins登录账号
//        String username = "admin";
//        //jenkins登录密码
//        String password = "admin";
//        //jenkins登录url
//        String jenkinsUrl = "http://localhost:8080";
//        String buildToken = "buildToken";
//        //jenkins构建job的url
//        String jenkinsBuildUrl = jenkinsUrl + "/job/" + jobName + "/build";
//        if(isNeedParams == true){
//            jenkinsBuildUrl = jenkinsUrl + "/job/" + jobName + "/buildWithParameters" + "?" + buildJobParams;
//        }
//        URI uri = URI.create(jenkinsBuildUrl);
//        HttpHost host = new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());
//        System.out.print ( uri.getHost () );
//        System.out.print ( uri.getPort () );
//        System.out.print ( uri.getScheme () );
//        CredentialsProvider credsProvider = new BasicCredentialsProvider ();
//        credsProvider.setCredentials(new AuthScope(uri.getHost(), uri.getPort()), new UsernamePasswordCredentials(username, password));
//        // Create AuthCache instance
//        AuthCache authCache = new BasicAuthCache ();
//        // Generate BASIC scheme object and add it to the local auth cache
//        BasicScheme basicAuth = new BasicScheme();
//        authCache.put(host, basicAuth);
//        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
//        HttpPost httpPost = new HttpPost(uri);
//        // Add AuthCache to the execution context
//        HttpClientContext localContext = HttpClientContext.create();
//        localContext.setAuthCache(authCache);
//        HttpResponse response = httpClient.execute(host, httpPost, localContext);
//        System.out.print ( response.getEntity () );
//        EntityUtils.toString(response.getEntity());
//    }


    @RequestMapping( "/create" )
    public void job() {
        RestTemplateBuilder builder = new RestTemplateBuilder (  );
        //带身份认证的RestTemplate
        RestTemplate template = builder.basicAuthorization ( "admin","admin" ).build ();
        //不需要身份认证的RestTemplate
        RestTemplate restTemplate = new RestTemplate ();
        HttpHeaders headers = new HttpHeaders ();
        MediaType type = MediaType.parseMediaType ( "text/xml; charset=UTF-8" );
        headers.setContentType ( type );
        String url;
        url="http://localhost:8080/createItem?name=test002";
          /*File fileInput = new File ( getXml () );
          FileSystemResource resource = new FileSystemResource ( fileInput );
          MultiValueMap<String,Object> parm = new LinkedMultiValueMap ( );
          List<Object> list = new ArrayList<Object> (  );
          list.add ( resource );
          parm.put ( "file", list );
          HttpEntity<MultiValueMap<String,Object>> entity = new HttpEntity<> ( parm,headers );
          ResponseEntity<String> result = template.postForEntity ( url,entity,String.class );*/

        HttpEntity<String> formEntity = new HttpEntity ( getXml (), headers );

        //String result = restTemplate.postForObject ( url, formEntity, String.class );
        String result1 = template.postForObject ( url, formEntity, String.class );
        System.out.print ( result1 );
    }


    private String getXml(){
        StringBuilder builder = new StringBuilder ( );
        try{
            //String file = "/Users/adawang/IdeaProjects/demo.xml";
            String file = this.getClass ().getClassLoader ().getResource ( "demo.xml" ).getPath ();
            InputStream in = new FileInputStream ( file );
            InputStreamReader read = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                builder.append ( lineTxt );
            }
            bufferedReader.close ();
            read.close ();
            in.close ();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.print ( builder.toString () );
        return builder.toString ();
    }

    /*@RequestMapping("/createTest")
    public void createJob() throws ClientProtocolException, IOException{
        //jenkins登录账号
        String username = "admin";
        //jenkins登录密码
        String password = "admin";
        String jobName = "create";
        //jenkins登录url
        String jenkinsUrl = "http://localhost:8080";
        String buildToken = "buildToken";
        //jenkins创建job的url
        String jenkinsBuildUrl = jenkinsUrl + "/createItem？name=" + jobName;

        URI uri = URI.create(jenkinsBuildUrl);
        HttpHost host = new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());
        System.out.print ( uri.getHost () );
        System.out.print ( uri.getPort () );
        System.out.print ( uri.getScheme () );
        CredentialsProvider credsProvider = new BasicCredentialsProvider ();
        credsProvider.setCredentials(new AuthScope(uri.getHost(), uri.getPort()), new UsernamePasswordCredentials(username, password));
        // Create AuthCache instance
        AuthCache authCache = new BasicAuthCache ();
        // Generate BASIC scheme object and add it to the local auth cache
        BasicScheme basicAuth = new BasicScheme();
        authCache.put(host, basicAuth);
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
        HttpPost httpPost = new HttpPost(uri);
        // Add AuthCache to the execution context
        HttpClientContext localContext = HttpClientContext.create();
        localContext.setAuthCache(authCache);
        HttpResponse response = httpClient.execute(host, httpPost, localContext);
        System.out.print ( response.getEntity () );
        EntityUtils.toString(response.getEntity());
    }*/

    @RequestMapping( "/getBuild" )
    public void getDetail() {
        RestTemplate restTemplate = new RestTemplate ();
        HttpHeaders headers = new HttpHeaders ();
        MediaType type = MediaType.parseMediaType ( "text/xml; charset=UTF-8" );
        headers.setContentType ( type );
//        headers.add ( "Accept", MediaType.APPLICATION_JSON.toString () );

        //JSONObject jsonObj = JSONObject.fromObject ( params );
        String url;
        url="http://localhost:8080/job/test002/api/json";

        //HttpEntity<String> formEntity = new HttpEntity<String> ( getXml (), headers );
        //restTemplate.put ( url,formEntity );

        //String result = restTemplate.postForObject ( url, formEntity, String.class );
        // System.out.print ( result );
        JSONObject result = restTemplate.getForObject ( url,JSONObject.class );
        System.out.print ( result.toString ());

    }
}
