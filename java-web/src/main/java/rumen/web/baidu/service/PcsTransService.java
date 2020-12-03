package rumen.web.baidu.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import geek.me.javaapi.baidu.PcsConst;
import geek.me.javaapi.baidu.PcsUrlHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.util.List;

@Service
public class PcsTransService {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 移动到"/apps/Cloud+Sync/"下
     * @param fsids
     * @param path
     * @return
     * @throws InterruptedException
     */
    public int transfer(List<String> fsids, String path) throws InterruptedException {
        String url = PcsUrlHelper.getTransferUrl();
        String body = PcsUrlHelper.getTransferForm(fsids,path);
        String response = restTemplate.postForEntity(url,body, String.class).getBody();
        JSONObject o = JSON.parseObject(response);
        return o.getInteger("errno");
    }

    /**
     * 删除"/apps/Cloud+Sync/"下的目录
     * @param path
     * @return
     * @throws InterruptedException
     */
    public int del(String path) throws InterruptedException {
        String url = PcsUrlHelper.getDelUrl();
        String body = "filelist=%5B%22"+URLEncoder.encode(PcsConst.basePath +path) +"%22%5D";
        String response = restTemplate.postForEntity(url,body, String.class).getBody();
        JSONObject o = JSON.parseObject(response);
        return o.getInteger("errno");
    }
}
