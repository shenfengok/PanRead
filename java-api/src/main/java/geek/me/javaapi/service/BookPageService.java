package geek.me.javaapi.service;

import geek.me.javaapi.baidu.PcsApi;
import geek.me.javaapi.baidu.dto.PcsItem;
import geek.me.javaapi.baidu.dto.PcsItemView;
import geek.me.javaapi.util.BaiduHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookPageService {

    @Autowired
    private PcsApi pcsApi;

    public List<PcsItem> findChild(String fsid) throws Exception {
        List<PcsItem> items = pcsApi.getChildItem(fsid);
        return items;
    }
}
