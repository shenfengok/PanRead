package geek.me.javaapi.baidu;

import geek.me.javaapi.baidu.dto.PcsItem;
import geek.me.javaapi.baidu.service.PcsItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PcsApi {
    @Autowired
    private PcsItemService pcsItemService;


    @Cacheable(key = "#fsid", value = "pcsItems")
    public List<PcsItem> getChildItem(String fsid) throws InterruptedException {
        return pcsItemService.getChildItem(fsid);
    }





}
