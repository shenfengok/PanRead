package geek.me.javaapi.baidu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import geek.me.javaapi.baidu.dto.PcsItem;
import geek.me.javaapi.util.URLParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static geek.me.javaapi.dto.form.ReNewForm.PCS_LOGID;
import static geek.me.javaapi.dto.form.ReNewForm.PCS_TOKEN;


@Service
public class PcsApi {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 获取fsid下所有项目
     *
     * @param fsid
     * @return
     * @throws InterruptedException
     */
    @Cacheable(key = "#fsid", value = "pcsItems")
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

    /**
     * https://pan.baidu.com/mbox/msg/shareinfo?msg_id=7557878178459586572&page=1&from_uk=228435709
     * &gid=658103785633267975&type=2&fs_id=915290417531226&num=50
     * &bdstoken=6d7f483cf5848d4e022ce7d051bc3316&channel=chunlei&web=1
     * &app_id=250528&logid=MTU5OTU2OTI3MzQwNjAuMTkxMDI1NTQ0NzY0Nzk4MTY=
     * &clienttype=0
     *
     * @param fsid
     * @param page
     * @return
     */
    private String getShareUrl(String fsid, Integer page) {
        StringBuilder builder = new StringBuilder();
        builder.append("https://pan.baidu.com/mbox/msg/shareinfo?msg_id=7557878178459586572&gid=658103785633267975");
        builder.append("&num=50&channel=chunlei&web=1&app_id=250528&clienttype=0&type=2");
        builder.append("&page=").append(page);
        builder.append("&logid=").append(PCS_LOGID);
        builder.append("&from_uk=").append(PCS_LOGID);
        builder.append("&fs_id=").append(fsid);
        builder.append("&bdstoken=").append(PCS_TOKEN);
        return builder.toString();
    }

    private String getShareUrl2(String fsid, Integer page) {
        StringBuilder builder = new StringBuilder();
        try {
            URLParser u = URLParser.fromURL(PcsConst.shareUrl);
            u.setParameter("fs_id", fsid);
            if(!StringUtils.isEmpty(PCS_LOGID)){
                u.setParameter("logid", PCS_LOGID);
            }
            if(!StringUtils.isEmpty(PCS_TOKEN)){
                u.setParameter("bdstoken", PCS_TOKEN);
            }
            u.setParameter("page", page.toString());
            String url = u.toURL();
            return url;
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return builder.toString();
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
