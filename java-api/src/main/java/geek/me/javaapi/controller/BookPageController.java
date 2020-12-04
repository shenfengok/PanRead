package geek.me.javaapi.controller;

import geek.me.javaapi.baidu.dto.PcsItem;
import geek.me.javaapi.baidu.dto.PcsItemView;
import geek.me.javaapi.service.BookPageService;
import geek.me.javaapi.view.PcsPageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class BookPageController {

    @Autowired
    private BookPageService bookPageService;

    @RequestMapping("zhuanlan/{fsid}")
    public String zhuanlanById(ModelMap map, @PathVariable(name = "fsid") String fsid) throws Exception {
        return zhuanlanHandle(map,fsid);
    }

    @RequestMapping("zhuanlan")
    public String zhuanlan(ModelMap map) throws Exception {
        return zhuanlanHandle(map,"294603226355310");
    }
    @RequestMapping("zhuanlangx")
    public String zhuanlangx(ModelMap map) throws Exception {
        return zhuanlanHandle(map,"1039355554886088");
    }

    private String zhuanlanHandle(ModelMap map,String fsid) throws Exception {
        map.addAttribute("title", "极客专栏");
        map.addAttribute("type", 1);
        map.addAttribute("url", "zhuanlan");
        if (StringUtils.isEmpty(fsid)) {
            fsid = "294603226355310";
        }
        List<PcsItem> items = bookPageService.findChild(fsid);

        List<PcsPageView> views = convert(items);
        map.addAttribute("itemList",views);


        return "home";
    }

    private List<PcsPageView> convert(List<PcsItem> items) {
        String basePath = getBasePath(items);
        List<PcsPageView> res = new ArrayList<>();
        if (null != items && items.size() > 0 ) {
            for(PcsItem i :items){
                PcsPageView v = new PcsPageView();
                v.setName(i.getServer_filename());
                v.setFsid(String.valueOf(i.getFs_id()));
                v.setBasePath(basePath);
                res.add(v);
            }
        }
        return  res.stream().sorted(Comparator.comparing(PcsPageView::getName)).collect(Collectors.toList());
    }

    private String getBasePath(List<PcsItem> items) {
        if (null != items && items.size() > 0) {
            String path = items.get(0).getPath();
            String name = items.get(0).getServer_filename();

            return path.replaceAll(name, "");
        }
        return "";
    }


}
