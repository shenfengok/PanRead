package geek.me.javaapi.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import geek.me.javaapi.baidu.PcsConst;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Component
public class DrupalHttpClient {
    HttpClient httpClient;

    @PostConstruct
    void init()  {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("shenfeng", "ipqmtd123");
        provider.setCredentials(AuthScope.ANY, credentials);
        httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
    }

    public JSONObject get(String url) {
        String result = "{}";
        try {
            HttpGet request = new HttpGet(url);
            request.setHeader("Accept","application/vnd.api+json");
            request.setHeader("Content-Type","application/vnd.api+json");
            HttpResponse response = httpClient.execute(request);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), "utf-8");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return JSON.parseObject(result);
    }


}
