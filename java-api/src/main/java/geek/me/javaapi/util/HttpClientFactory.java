package geek.me.javaapi.util;



import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;


public class HttpClientFactory {

    private static final Integer MAX_TOTAL = 300;             //连接池最大连接数
    private static final Integer MAX_PER_ROUTE = 100;          //单个路由默认最大连接数
    private static final Integer REQ_TIMEOUT =  300 * 1000;     //请求超时时间ms
    private static final Integer CONN_TIMEOUT = 30 * 1000;     //连接超时时间ms
    private static final Integer SOCK_TIMEOUT = 300 * 1000;    //读取超时时间ms
    private static HttpClientConnectionMonitorThread thread;  //HTTP链接管理器线程

    public static HttpClientConnectionMonitorThread getThread() {
        return thread;
    }
    public static void setThread(HttpClientConnectionMonitorThread thread) {
        HttpClientFactory.thread = thread;
    }

    public static HttpClient createSimpleHttpClient(){
        SSLConnectionSocketFactory sf = SSLConnectionSocketFactory.getSocketFactory();
        return HttpClientBuilder.create()
                .setSSLSocketFactory(sf)
                .build();

    }

    public static HttpClient createHttpClient() {
        ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                HeaderElementIterator it = new BasicHeaderElementIterator
                        (response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase
                            ("timeout")) {
                        return Long.parseLong(value) * 1000;
                    }
                }
                return 60 * 1000;//如果没有约定，则默认定义时长为60s
            }
        };
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        poolingHttpClientConnectionManager.setMaxTotal(MAX_TOTAL);
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(MAX_PER_ROUTE);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(REQ_TIMEOUT)
                .setConnectTimeout(CONN_TIMEOUT).setSocketTimeout(SOCK_TIMEOUT)
                .build();
        HttpClientFactory.thread=new HttpClientConnectionMonitorThread(poolingHttpClientConnectionManager); //管理 http连接池
        return HttpClients.custom().setKeepAliveStrategy(myStrategy).setConnectionManager(poolingHttpClientConnectionManager)
                .setDefaultRequestConfig(requestConfig)
                .build();
    }
}