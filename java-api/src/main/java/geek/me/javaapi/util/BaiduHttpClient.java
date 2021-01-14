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
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.rmi.activation.Activator;
import java.util.Date;
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
        HttpGet request = new HttpGet(url);
        HttpResponse response = null;
        try {

            request.setHeader("Cookie", PcsConst.cookie);
            request.setHeader("User-Agent", "netdisk;P2SP;2.2.60.26");
             response = httpClient.execute(request);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), "utf-8");
            } else {
                System.out.println(response);
            }
        } catch (Exception e) {
            System.out.println(e);
        }finally {
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
        return JSON.parseObject(result);
    }

    public String httpGet2(String url,String name,String contentPath,int time) throws InterruptedException, IOException {
        long start = new Date().getTime();



        File file = new File("C:\\Users\\Administrator\\Downloads\\"+ name+".html");
        if(!file.exists()){
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+url);
        }
        while(true){

            if(file.exists()){
                String content =  readFileContent(file);
                if(!StringUtils.isEmpty(content)){
                    file.delete();
                    return content;
                }
            }

            long end = new Date().getTime();
            if(end - start > 80000){

                //加入网盘采集，todo
                return "";
            }else{
                Thread.sleep(3000);
            }

        }
    }

    public static String readFileContent(File file) {
        BufferedReader  reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
            reader = new BufferedReader(isr);
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }


    public String httpGet(String url,String name, String contentPath,int time) throws InterruptedException, IOException {

        try{
            File filez = new File("Z:\\pan\\zhuanlan\\"+ contentPath);
            if(filez.exists()){
                return readFileContent(filez);
            }
            else{
                return "";
            }
        }catch (Exception e){
            e.printStackTrace();
        }

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
            request.setProtocolVersion(HttpVersion.HTTP_1_0);//org.apache.http.ConnectionClosedException: Premature end of Content-Length delimited message body
            request.addHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
            response = httpClient.execute(request);

            /** 请求发送成功，并得到响应 **/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /** 读取服务器返回过来的json字符串数据 **/
//                strResult = EntityUtils.toString(response.getEntity(),Charset.defaultCharset());
                strResult = readContent(response.getEntity());
            }else if(response.getStatusLine().getStatusCode() == HttpStatus.SC_FORBIDDEN){
//                return "";
                return httpGet2(url,name,contentPath,time);
            }else{
                System.out.println("error"+ response);
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
            if("Remote host closed connection during handshake".equals(e.getMessage())){

            }

            e.printStackTrace();
            return httpGet2(url,name,contentPath,time);
//            throw e;
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
            final ContentType contentType =  ContentType.getOrDefault(entity);
            Charset charset = StandardCharsets.UTF_8;//contentType.getCharset();
//            if (charset == null)
//
//            {
//                charset = HTTP.DEF_CONTENT_CHARSET;
//            }
            final StringBuilder b = new StringBuilder();
            final char[] tmp = new char[1024];
            final Reader reader = new InputStreamReader(instream, charset);
            try {
                int l;
                while ((l = reader.read(tmp)) != -1)

                {
                    b.append(tmp, 0, l);
                }
            } catch (final Exception ignore) {//忽略超时，关闭等，读取字符串，一直等待，其实内容已经到了，没有标注的那么多内容
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
