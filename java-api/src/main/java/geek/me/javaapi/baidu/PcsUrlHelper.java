package geek.me.javaapi.baidu;

import com.alibaba.fastjson.JSON;
import geek.me.javaapi.util.URLParser;
import org.springframework.util.StringUtils;

import java.util.List;

import static geek.me.javaapi.dto.form.ReNewForm.PCS_LOGID;
import static geek.me.javaapi.dto.form.ReNewForm.PCS_TOKEN;

public class PcsUrlHelper {


    public static String getTransferUrl() {
        StringBuilder builder = new StringBuilder();
        try {
            URLParser u = URLParser.fromURL(PcsConst.transferUlr);
            if(!StringUtils.isEmpty(PCS_TOKEN)){
                u.setParameter("bdstoken", PCS_TOKEN);
            }
            String url = u.toURL();
            return url;
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return builder.toString();
    }

    public static String getTransferForm(List<String> fsids, String path) {
        StringBuilder builder = new StringBuilder();
        try {
            URLParser u = URLParser.fromURL(PcsConst.transferForm);
            if(!StringUtils.isEmpty(path)){
                u.setParameter("path", path);
            }
            u.setParameter("fs_ids", JSON.toJSONString(fsids));
            String url = u.toURL();
            return url;
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return builder.toString();
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

    public static String getShareUrl2(String fsid, Integer page) {
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

}
