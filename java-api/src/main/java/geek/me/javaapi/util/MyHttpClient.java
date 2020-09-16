package geek.me.javaapi.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import geek.me.javaapi.baidu.PcsConst;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;

@Component
public class MyHttpClient {
    HttpClient httpClient;

    @PostConstruct
    void init() {
        CookieStore store = getCookieStore();

        httpClient = HttpClientBuilder.create().setDefaultCookieStore(store).build();
    }

    public JSONObject get(String url) {
        String result = "{}";
        try {
            HttpGet request = new HttpGet(url);

            HttpResponse response = httpClient.execute(request);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), "utf-8");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return JSON.parseObject(result);
    }

    public CookieStore getCookieStore() {
        CookieStore result = new BasicCookieStore();
        String[] cookiestrs = PcsConst.cookie.split(";");
        Cookie[] cookies = new Cookie[cookiestrs.length];
        for (int i = 0; i < cookies.length; i++) {
            String[] onecookie = cookiestrs[i].split("=");
            result.addCookie(new BasicClientCookie(onecookie[0],onecookie[1]));
        }
        return result;
    }
}
