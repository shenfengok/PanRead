package geek.me.javaapi.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import geek.me.javaapi.baidu.PcsConst;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.juli.logging.Log;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Component
public class BaiduHttpClient {
    HttpClient httpClient;

    @PostConstruct
    void init() throws UnsupportedEncodingException {
//        CookieStore store = getCookieStore();

        httpClient = HttpClientFactory.createHttpClient();
//        httpClient =  HttpClientBuilder.create().build();
    }

    public JSONObject get(String url) {
        String result = "{}";

        try {
            HttpGet request = new HttpGet(url);
            request.setHeader("Cookie", PcsConst.cookie);
            request.setHeader("User-Agent", "netdisk;P2SP;2.2.60.26");
            HttpResponse response = httpClient.execute(request);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), "utf-8");
            } else {
                System.out.println(response);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return JSON.parseObject(result);
    }

    public String httpGet(String url, int time) throws InterruptedException, IOException {
        // get请求返回结果
        String strResult = "";
        HttpGet request = null;
        HttpResponse response = null;
        try {
            // 发送get请求
            request = new HttpGet(url);
            request.setHeader("Cookie", PcsConst.cookie);
            request.setHeader("User-Agent", "netdisk;P2SP;2.2.60.26");
//            RequestConfig requestConfig = RequestConfig.custom()
//                    .setConnectTimeout(time).setConnectionRequestTimeout(time)
//                    .setSocketTimeout(time).build();
//            request.setConfig(requestConfig);
//            request.setProtocolVersion(HttpVersion.HTTP_1_0);//org.apache.http.ConnectionClosedException: Premature end of Content-Length delimited message body
//            request.addHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
            response = httpClient.execute(request);

            /** 请求发送成功，并得到响应 **/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /** 读取服务器返回过来的json字符串数据 **/
                strResult = readContent(response.getEntity());
            }
        }
//        catch (Exception e) {
//            System.out.println("ohno:"+e);
//            System.out.println("get请求提交失败:" + url);
//            Thread.sleep(1000);
//        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (null != request) {
                request.releaseConnection();
            }

            try {
                if (response != null) {
                    EntityUtils.consume(response.getEntity());
                }
//                if (url.startsWith("https") && httpClient != null && httpClient instanceof CloseableHttpClient) {
//                    ((CloseableHttpClient) httpClient).close();
//                }
            } catch (Exception e) {

            }


        }
        return strResult;
    }

    /**
     * https://issues.apache.org/jira/browse/HTTPCLIENT-1352
     * @param entity
     * @return
     * @throws IOException
     */
    private String readContent(HttpEntity entity) throws IOException {
        final InputStream instream = entity.getContent();
        try {
            final ContentType contentType = ContentType.getOrDefault(entity);
            Charset charset = contentType.getCharset();
            if (charset == null)

            {
                charset = HTTP.DEF_CONTENT_CHARSET;
            }
            final StringBuilder b = new StringBuilder();
            final char[] tmp = new char[1024];
            final Reader reader = new InputStreamReader(instream, charset);
            try {
                int l;
                while ((l = reader.read(tmp)) != -1)

                {
                    b.append(tmp, 0, l);
                }
            } catch (final ConnectionClosedException ignore) {
                int a =0;
            }
            return b.toString();
        } finally {
            instream.close();
        }
    }

    public CookieStore getCookieStore() throws UnsupportedEncodingException {
        CookieStore result = new BasicCookieStore();
        String[] cookiestrs = PcsConst.cookie.split(";");
        Cookie[] cookies = new Cookie[cookiestrs.length];
        for (int i = 0; i < cookies.length; i++) {
            String[] onecookie = cookiestrs[i].split("=");
            result.addCookie(new BasicClientCookie(onecookie[0], URLDecoder.decode(onecookie[1], "utf-8")));
        }
        return result;
    }
}
