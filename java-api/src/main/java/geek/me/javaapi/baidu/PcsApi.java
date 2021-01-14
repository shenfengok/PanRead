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

    public List<PcsItemView> getAllChild(String fsid) throws Exception {
        return getChildItemView(fsid,"");
    }

    public List<PcsItemView> getChildItemView(String fsid,String basePath) throws Exception {
        List<PcsItem> list = getChildItem(fsid);
        Map<String,PcsItem> listMap = list.stream().collect(Collectors.toMap(x->compressName(x.getServer_filename()),x->x));
        List<PcsItemView> result = new ArrayList<>();
        for(PcsItem item :list){
            if(item.getIsdir() == 1){
                PcsItemView view = new PcsItemView();
                view.setTitle(item.getServer_filename());
                view.setFsid(String.valueOf(item.getFs_id()));
                view.setIsdir(1);
                view.setFullPath(item.getPath());
                view.setParentPath(item.getPath().replaceAll(item.getServer_filename(),""));
                result.add(view);
            }else{
                if("mp3".equals(FileNameUtil.getExtensionName(item.getServer_filename())) || "m4a".equals(FileNameUtil.getExtensionName(item.getServer_filename()))){
                    String title = FileNameUtil.getFileNameNoEx(item.getServer_filename());
                    PcsItemView view = new PcsItemView();
                    view.setTitle(title);
                    view.setFsid(String.valueOf(item.getFs_id()));
                    view.setIsdir(0);
                    view.setMediaPath(getPath(item.getPath(),basePath));
                    view.setMediaTitle(item.getServer_filename());
                    PcsItem contentItem = listMap.get(compressName(title)+".html");
                    view.setFullPath(item.getPath());
                    view.setParentPath(item.getPath().replaceAll(item.getServer_filename(),""));
                    String contentPath = null;
                    if(null != contentItem){
                        contentPath = getPath(contentItem.getPath(),basePath);
                    }else{
                        //hack

                    }

                    if(contentPath == null ){
                        throw new Exception("无法获取网页内容");
                    }
                    view.setContentPath(contentPath);


                    result.add(view);
                }

            }

        }

        return result;
    }

    private String compressName(String name){
        //"12丨多线程之锁优化（上）：深入了解Synchronized同步锁的优化方法.pdf" -> "PcsItem(category=4, fs_id=686810862764976, isdir=0, local_ctime=1567056356, local_mtime=1567056358, path=/00-资源文件/14-极客时间/01-专栏课/01-50/47-Java性能调优实战/04-模块三· 多线程性能调优 (1讲)/12丨多线程之锁优化（上）：深入了解Synchronized同步锁的优化方法.pdf, server_ctime=1567182414, server_filename=12丨多线程之锁优化（上）：深入了解Synchronized同步锁的优化方法.pdf, server_mtime=1582452345, size=4196635, parentType=null)"
        return name.replaceAll(" ","").replaceAll("丨","");
    }

    private String getPath(String path,String base){
        return path.substring(path.indexOf(base) + base.length());
    }



}
