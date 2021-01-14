package geek.me.javaapi.baidu.service;

import com.alibaba.fastjson.JSONObject;
import geek.me.javaapi.baidu.PcsUrlHelper;
import geek.me.javaapi.baidu.js.JsFunc;
import geek.me.javaapi.util.BaiduHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.List;

@Service
public class PcsDownService {

    @Autowired
    private BaiduHttpClient myHttpClient;

    /**
     * 获取"/apps/Cloud Sync/"下的下载连接
     * @param fsids
     * @return
     * @throws InterruptedException
     */
    public String dlink(List<String> fsids) throws Exception {

        JSONObject so = getSignObject();
        String sign = JsFunc.getSign4Down(so.getString("sign1"),so.getString("sign2"),so.getString("sign3"));
        String url = PcsUrlHelper.getAppDownUrl(fsids,sign,so.getString("timestamp"));
        JSONObject response = myHttpClient.get(url);
        if(response.getInteger("errno") == 0){
            JSONObject dlinkObject = response.getJSONArray("dlink").getJSONObject(0);
            String dlink = dlinkObject.getString("dlink");
            return dlink;
        }
        throw new Exception("getCapture");
    }


    /**
     * 通过网盘user-agent获取"/apps/Cloud Sync/"下的下载连接
     * https://greasyfork.org/zh-CN/scripts/403991-%E7%99%BE%E5%BA%A6%E7%BD%91%E7%9B%98%E7%9B%B4%E9%93%BE%E6%8F%90%E5%8F%96-%E5%A4%9A%E9%80%89/code
     * @param path
     * @return
     * @throws InterruptedException
     */
//    @Cacheable(key = "#path",value = "netLink")
    public String netdiskLink(String path,String fileName) throws Exception {
        if(path.contains("null")){
            int a = 0;
        }

        System.out.println(fileName);

        String url = PcsUrlHelper.getnetdiskDownUrl(path);
        JSONObject response = myHttpClient.get(url);
        if(response.containsKey("client_ip")){
            JSONObject dlinkObject = response.getJSONArray("urls").getJSONObject(0);
            String dlink = dlinkObject.getString("url");
            dlink+= "&filename=" + URLEncoder.encode(fileName,"utf-8");
            return dlink;
        }
        throw new Exception("获取下载链接失败"+ path);
    }

    public String netContent(String url,String name,String contentPath) throws Exception {

        return myHttpClient.httpGet(url,name,contentPath,90000);
    }


    private JSONObject getSignObject (){
        String signUrl = PcsUrlHelper.getSignUrl();
        JSONObject sj = myHttpClient.get(signUrl);
        return sj.getJSONObject("result");
    }

}
