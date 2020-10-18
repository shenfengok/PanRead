package geek.me.javaapi.baidu;

import geek.me.javaapi.baidu.dto.PcsItem;
import geek.me.javaapi.baidu.dto.PcsItemView;
import geek.me.javaapi.baidu.service.PcsItemService;
import geek.me.javaapi.util.FileNameUtil;
import javassist.bytecode.LineNumberAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class PcsApi {
    @Autowired
    private PcsItemService pcsItemService;


    @Cacheable(key = "#fsid", value = "pcsItems")
    public List<PcsItem> getChildItem(String fsid) throws InterruptedException {
        return pcsItemService.getChildItem(fsid);
    }


    public List<PcsItemView> getChildItemView(String fsid,String basePath) throws InterruptedException {
        List<PcsItem> list = getChildItem(fsid);
        Map<String,PcsItem> listMap = list.stream().collect(Collectors.toMap(x->x.getServer_filename(),x->x));
        List<PcsItemView> result = new ArrayList<>();
        for(PcsItem item :list){
            if(item.getIsdir() == 1){
                PcsItemView view = new PcsItemView();
                view.setTitle(item.getServer_filename());
                view.setFsid(String.valueOf(item.getFs_id()));
                view.setIsdir(1);
                result.add(view);
            }else{
                if("mp3".equals(FileNameUtil.getExtensionName(item.getServer_filename()))){
                    String title = FileNameUtil.getFileNameNoEx(item.getServer_filename());
                    PcsItemView view = new PcsItemView();
                    view.setTitle(title);
                    view.setFsid(String.valueOf(item.getFs_id()));
                    view.setIsdir(0);
                    PcsItem contentItem = listMap.get(title+".html");
                    if(null != contentItem){
                        view.setContentPath(contentItem.getPath().substring(basePath.length()));
                    }

                    result.add(view);
                }
            }

        }

        return result;
    }



}
