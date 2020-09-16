package geek.me.javaapi.baidu.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import geek.me.javaapi.baidu.PcsConst;
import geek.me.javaapi.baidu.PcsUrlHelper;
import geek.me.javaapi.baidu.dto.PcsItem;
import javassist.bytecode.LineNumberAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static geek.me.javaapi.baidu.PcsUrlHelper.getShareUrl2;

@Service
public class PcsTransferService {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 获取fsid下所有项目
     *
     * @param fsid
     * @return
     * @throws InterruptedException
     */
    public List<PcsItem> transfer(List<String> fsids, String path) throws InterruptedException {

        List<PcsItem> result = new ArrayList<>();
        String url = PcsUrlHelper.getTransferUrl();
        String body = PcsUrlHelper.getTransferForm(fsids,path);

        return result;
    }
}
