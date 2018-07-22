package com.ada.springboothttp.service;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class JenkinsServiceWithRest {

    private static final Logger LOGGER=LoggerFactory.getLogger ( JenkinsServiceWithRest.class );

    @Value( "${jenkins.url}" )
    private String jenkinsAPI;

    @Resource( name="jenkinsRestTemplate" )
    private RestTemplate template;


    private ResponseEntity<JSONObject> result=null;

    /**
     * read config.xml of job to create job
     *
     * @return
     */
    private String getXml() {
        StringBuilder builder=new StringBuilder ();
        try {
            //String file = "/Users/adawang/IdeaProjects/demo.xml";
            String file=this.getClass ().getClassLoader ().getResource ( "demo.xml" ).getPath ();
            InputStream in=new FileInputStream ( file );
            InputStreamReader read=new InputStreamReader ( in );
            BufferedReader bufferedReader=new BufferedReader ( read );
            String lineTxt=null;
            while ((lineTxt=bufferedReader.readLine ()) != null) {
                builder.append ( lineTxt );
            }
            bufferedReader.close ();
            read.close ();
            in.close ();
        } catch (Exception e) {
            e.printStackTrace ();
        }
        System.out.print ( builder.toString () );
        return builder.toString ();
    }


    /**
     * Create jenkins job
     *
     * @return
     */
    public int createJob(String jobName) {
        HttpHeaders headers=new HttpHeaders ();
        MediaType type=MediaType.parseMediaType ( "text/xml; charset=UTF-8" );
        headers.setContentType ( type );
          /*File fileInput = new File ( getXml () );
          FileSystemResource resource = new FileSystemResource ( fileInput );
          MultiValueMap<String,Object> parm = new LinkedMultiValueMap ( );
          List<Object> list = new ArrayList<Object> (  );
          list.add ( resource );
          parm.put ( "file", list );
          HttpEntity<MultiValueMap<String,Object>> entity = new HttpEntity<> ( parm,headers );
          ResponseEntity<String> result = template.postForEntity ( url,entity,String.class );*/
        /**
         * 以字符串写pipline
         */
        HttpEntity<String> formEntity=new HttpEntity ( getXml (), headers );
        result=template.postForEntity ( jenkinsAPI + "createItem?name=" + jobName,
                formEntity, JSONObject.class );
        LOGGER.info ( "Create job result: {}", result.getBody () );
        System.out.print ( result.getStatusCode () );
        return result.getStatusCodeValue ();
    }

    /**
     * Build job with params
     *
     * @param jobName
     * @param params
     * @return
     */
    public int buildJob(String jobName, Map<String, String> params) {
        String parameters="";
        if (params != null) {
            parameters=params.toString ().replace ( ",", "&" ).
                    replaceAll ( "[{}|\\s]?", "" );
            result=template.postForEntity ( jenkinsAPI + "job/" + jobName + "/buildWithParameters?" + parameters,
                    "", JSONObject.class );
        } else {
            result=template.postForEntity ( jenkinsAPI + "job/" + jobName + "/build",
                    "", JSONObject.class );
        }

        LOGGER.info ( "Build job result: {}", result.getBody ());
        return result.getStatusCodeValue ();
    }

    /**
     * Get build result
     *
     * @param jobName
     * @return
     */
    public boolean getBuild(String jobName) {
        boolean flag=false;
        do {
            result=template.postForEntity ( jenkinsAPI + "job/" + jobName + "/lastBuild/api/json",
                    "", JSONObject.class );
            // wait build complete
            try {
                TimeUnit.SECONDS.sleep ( 10 );
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
            LOGGER.info ( "Building status: {}", result.getBody ().getBooleanValue ( "building" ) );
        } while (result.getBody ().getBooleanValue ( "building" ));
        if (result.getBody ().getBooleanValue ( "building" )) {
            flag=true;
        }
        return flag;
    }

    /**
     * Delete job
     *
     * @param jobName
     */
    public void deleteJob(String jobName) {
        String result=template.postForObject ( jenkinsAPI + "job/" + jobName + "/doDelete", "",String.class );
        LOGGER.info ( result );
    }
}
