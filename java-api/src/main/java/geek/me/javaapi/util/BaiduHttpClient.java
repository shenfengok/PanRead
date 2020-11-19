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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;

@Component
public class BaiduHttpClient {
    HttpClient httpClient;

    @PostConstruct
    void init() throws UnsupportedEncodingException {
//        CookieStore store = getCookieStore();

        httpClient = HttpClientBuilder.create().build();
    }

    public JSONObject get(String url) {
        String result = "{}";
        try {
            HttpGet request = new HttpGet(url);
            request.setHeader("Cookie",PcsConst.cookie);
            request.setHeader("User-Agent","netdisk;P2SP;2.2.60.26");
            HttpResponse response = httpClient.execute(request);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), "utf-8");
            }else{
                System.out.println(response);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return JSON.parseObject(result);
    }

    public String httpGet(String url) {
        // get请求返回结果
        String strResult = "";
        try {
            // 发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = httpClient.execute(request);

            /** 请求发送成功，并得到响应 **/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /** 读取服务器返回过来的json字符串数据 **/
                strResult = EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));
            }
        } catch (Exception e) {
            System.out.println("get请求提交失败:" + url);
        }
        return strResult;
    }

    public CookieStore getCookieStore() throws UnsupportedEncodingException {
        CookieStore result = new BasicCookieStore();
        String[] cookiestrs = PcsConst.cookie.split(";");
        Cookie[] cookies = new Cookie[cookiestrs.length];
        for (int i = 0; i < cookies.length; i++) {
            String[] onecookie = cookiestrs[i].split("=");
            result.addCookie(new BasicClientCookie(onecookie[0], URLDecoder.decode(onecookie[1],"utf-8")));
        }
        return result;
    }
}
