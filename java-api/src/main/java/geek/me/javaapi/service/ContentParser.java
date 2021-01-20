package geek.me.javaapi.service;

import geek.me.javaapi.baidu.PcsConst;
import geek.me.javaapi.util.FileUtil;
import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Data
public class ContentParser {

    private String body;
    private String comment;
    private String thumb;

    private boolean success;

    private static String rootPath = "Z:\\pan\\zhuanlan\\zhuanlan\\";

    private String mediaPath;

    private String fullMediaPath;

    public ContentParser(String path,String mediaPath) {
        this.mediaPath = mediaPath;
        process(path);
    }

    private void process(String path) {
        String realPath = getFilePath(path);
        if(StringUtils.isEmpty(realPath)){
            success = false;
            return;
        }
        String conx = FileUtil.readFileContent(realPath);
        Document doc = Jsoup.parse(conx);


        Elements els = doc.getElementsByTag("img");
        els.forEach(x -> {
            String imgLink = x.attr("data-savepage-src");
            x.attr("src", imgLink);
            x.removeAttr("data-savepage-src");
        });

        String con = "";// 内容
        if (doc.getElementsByClass("_29HP61GA_0").size() > 0) {
            con = doc.getElementsByClass("_29HP61GA_0").get(0).html();
        } else if (doc.getElementsByClass("_2c4hPkl9").size() > 0) {
            con = doc.getElementsByClass("_2c4hPkl9").get(0).html();
        } else if (doc.getElementsByClass("_1kh1ihh6_0").size() > 0) {
            con = con = doc.getElementsByClass("_1kh1ihh6_0").get(0).html();
        }
        body = con;

        thumb = getThumByDivClassName(doc, "_3Jbcj4Iu_0");//封面图片
        if (StringUtils.isEmpty(thumb)) {
            thumb = getThumByDivClassName(doc, "_3-9A2Wmt_0");

        }


        comment = "";//评论内容

        if (doc.getElementsByClass("_1qhD3bdE_0").size() > 0) {
            comment = doc.getElementsByClass("_1qhD3bdE_0").get(0).getElementsByTag("ul").html();
        }



        boolean suc = !StringUtils.isEmpty(con) && !StringUtils.isEmpty(comment) && !StringUtils.isEmpty(thumb);
        this.fullMediaPath = getFilePath(mediaPath);
        success = true;

    }

    private String getFilePath(String path) {
        List<String> pathsSearch = new ArrayList<>();
        pathsSearch.add("00-更新中的专栏\\");
        pathsSearch.add("01-专栏课\\01-50\\");
        pathsSearch.add("01-专栏课\\051-99\\");
        pathsSearch.add("01-专栏课\\100-\\");


        for (String p : pathsSearch) {
            String realP = rootPath + p + path;
            File pf = new File(realP);
            if (pf.exists()) {
                return pf.getAbsolutePath();
            }
        }
        return "";
    }


    private String getThumByDivClassName(Document doc, String className) {
        if (doc.getElementsByClass(className).size() > 0 && doc.getElementsByClass(className).get(0).getElementsByTag("img").size() > 0) {
            return doc.getElementsByClass(className).get(0).getElementsByTag("img").get(0).attr("src");
        }
        return "";
    }

}
