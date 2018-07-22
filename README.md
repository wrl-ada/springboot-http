# springboot-http
通过调用Jenkins API创建job，构建job及获取构建结果

#### 身份认证：
######  HttpClient：
1. HttpClient client = new HttpClient ();
2. client.getState().setCredentials(
3.     new AuthScope(AuthScope.ANY_HOST,AuthScope.ANY_PORT),
4.     new UsernamePasswordCredentials(username,apiToken)
5. ); 
6. client.getParams ().setAuthenticationPreemptive ( true );
######  RestTemplate:
1. RestTemplateBuilder builder = new RestTemplateBuilder (  );
2. //带身份认证的RestTemplate
3. RestTemplate template = builder.basicAuthorization ( "admin","admin" ).build ();
        
