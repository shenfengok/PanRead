package rumen.web.baidu;

import com.alibaba.fastjson.JSON;
import geek.me.javaapi.baidu.js.JsFunc;
import geek.me.javaapi.util.URLParser;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import static geek.me.javaapi.dto.form.ReNewForm.PCS_TOKEN;

public class PcsUrlHelper {


    /**
     * 迁移文件url
     * @return
     */
    public static String getTransferUrl() {
        StringBuilder builder = new StringBuilder();
        try {
            URLParser u = URLParser.fromURL(PcsConst.transferUlr);
            if (!StringUtils.isEmpty(PCS_TOKEN)) {
                u.setParameter("bdstoken", PCS_TOKEN);
            }
            String url = u.toUrlEnc();
            return url;
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return builder.toString();
    }

    /**
     * 迁移文件form
     * @param fsids
     * @param path
     * @return
     */
    public static String getTransferForm(List<String> fsids, String path) {
        StringBuilder builder = new StringBuilder();
        try {
            URLParser u = URLParser.fromURL(PcsConst.transferForm);
            if (!StringUtils.isEmpty(path)) {
                u.setParameter("path", PcsConst.basePath + path);
            } else {
                u.setParameter("path", PcsConst.basePath);
            }
            u.setParameter("fs_ids", JSON.toJSONString(fsids));
            String url = u.toUrlEnc();
            return url;
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return builder.toString();
    }

    /**
     * 下载链接
     * @param fidList
     * @return
     */
    public static String getAppDownUrl(List<String> fidList,String sign,String timestamp) {
        StringBuilder builder = new StringBuilder();
        try {
            URLParser u = URLParser.fromURL(PcsConst.appDownUlr);

            u.setParameter("fidlist",URLEncoder.encode(getFidListStr(fidList),"utf-8"));
            u.setParameter("sign", sign);
            u.setParameter("timestamp", timestamp);
            String url = u.toUrlRaw();
            return url;
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return builder.toString();
    }
    /**
     * netdisk下载链接
     * @param path
     * @return
     */
    public static String getnetdiskDownUrl(String path) throws UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder();
        builder.append(PcsConst.netdiskDownUrl);
        builder.append(URLEncoder.encode(path,"utf-8"));
        return builder.toString();
    }

    private static String getFidListStr(List<String> fidList){
        return "["+ String.join(",",fidList) + "]";
    }

    /**
     * 现在连接签名url
     * @return
     */
    public static String getSignUrl() {
        StringBuilder builder = new StringBuilder();
        try {
            URLParser u = URLParser.fromURL(PcsConst.getSignUlr);
            if (!StringUtils.isEmpty(PCS_TOKEN)) {
                u.setParameter("bdstoken", PCS_TOKEN);
            }
            u.setParameter("logid",JsFunc.getLogId());
            String url = u.toUrlEnc();
            return url;
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return builder.toString();
    }

    public static String getDelUrl() {
        StringBuilder builder = new StringBuilder();
        try {
            URLParser u = URLParser.fromURL(PcsConst.delFolderUrl);
            if (!StringUtils.isEmpty(PCS_TOKEN)) {
                u.setParameter("bdstoken", PCS_TOKEN);
            }
            u.setParameter("logid",JsFunc.getLogId());
            String url = u.toUrlEnc();
            return url;
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return builder.toString();
    }

    private void timestatmpIt(URLParser u) throws UnsupportedEncodingException {
        u.setParameter("timestamp",String.valueOf(new Date().getTime()/1000));
    }


    public static String getShareUrl2(String fsid, Integer page) {
        StringBuilder builder = new StringBuilder();
        try {
            URLParser u = URLParser.fromURL(PcsConst.shareUrl);
            u.setParameter("fs_id", fsid);
            if (!StringUtils.isEmpty(JsFunc.getLogId())) {
                u.setParameter("logid", JsFunc.getLogId());
            }
            if (!StringUtils.isEmpty(PCS_TOKEN)) {
                u.setParameter("bdstoken", PCS_TOKEN);
            }
            u.setParameter("page", page.toString());
            String url = u.toUrlEnc();
            return url;
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return builder.toString();
    }

}
