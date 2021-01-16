package rumen.web.util;


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
    private static final Integer REQ_TIMEOUT =  90 * 1000;     //请求超时时间ms
    private static final Integer CONN_TIMEOUT = 30 * 1000;     //连接超时时间ms
    private static final Integer SOCK_TIMEOUT = 90 * 1000;    //读取超时时间ms
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
//
//        SSLContext ctx = SSLContext.getInstance("TLSv1.2");
////        X509TrustManager tm = new X509TrustManager() {
////            @Override
////            public void checkClientTrusted(X509Certi<a target=_blank target="_blank" href="http://superuser.com/questions/747377/enable-tls-1-1-and-1-2-for-clients-on-java-7">http://superuser.com/questions/747377/enable-tls-1-1-and-1-2-for-clients-on-java-7</a>ficate[] chain, String authType) throws CertificateException {
////        }
////
////        @Override
////        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
////        }
////
////        @Override
////        public X509Certificate[] getAcceptedIssuers() {
////            return null;
////        }
////    };
//			ctx.init(null, new TrustManager[] { tm }, null);
//    org.apache.http.conn.ssl.SSLSocketFactory ssf = new org.apache.http.conn.ssl.SSLSocketFactory(ctx,
//            org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//    ClientConnectionManager ccm = this.getConnectionManager();
//    SchemeRegistry sr = ccm.getSchemeRegistry();
//			sr.register(new Scheme("https", 443, ssf));

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