package geek.me.javaapi.baidu.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import geek.me.javaapi.baidu.PcsUrlHelper;
import geek.me.javaapi.baidu.dto.PcsItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
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

        List<PcsItem> result = new ArrayList<>();
        String url = PcsUrlHelper.getTransferUrl();
        String body = PcsUrlHelper.getTransferForm(fsids,path);
        String response = restTemplate.postForEntity(url,body, String.class).getBody();
        JSONObject o = JSON.parseObject(response);
        return o.getInteger("errno");
    }
}
