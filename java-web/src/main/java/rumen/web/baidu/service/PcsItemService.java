package rumen.web.baidu.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import geek.me.javaapi.baidu.dto.PcsItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static geek.me.javaapi.baidu.PcsUrlHelper.getShareUrl2;

@Service
public class PcsItemService {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 获取fsid下所有项目
     *
     * @param fsid
     * @return
     * @throws InterruptedException
     */
    public List<PcsItem> getChildItem(String fsid) throws InterruptedException {
        boolean hasMore = true;
        int page = 1;
        List<PcsItem> result = new ArrayList<>();
        while (hasMore) {
            String url = getShareUrl2(fsid, page);
            String response = restTemplate.getForEntity(url, String.class).getBody();
            JSONObject o = JSON.parseObject(response);
            result.addAll(getFromJsonObject(o));
            hasMore = ifHasMore(o);
            page++;
            Thread.sleep(500L);
        }

        return result;
    }

    /**
     * 判断是否有更多
     *
     * @param response
     * @return
     */
    private boolean ifHasMore(JSONObject response) {
        return response.getInteger("has_more") == 1;
    }


    private List<PcsItem> getFromJsonObject(JSONObject response) {
        List<PcsItem> list = new ArrayList<>();
        for (Object o : response.getJSONArray("records")) {
            PcsItem i = ((JSONObject) o).toJavaObject(PcsItem.class);
            list.add(i);
        }
        return list;
    }
}
