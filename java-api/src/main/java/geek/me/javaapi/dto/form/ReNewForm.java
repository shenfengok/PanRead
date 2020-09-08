package geek.me.javaapi.dto.form;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class ReNewForm {
    public static String PCS_TOKEN;
    public static String PCS_COOKIE;
    public static String PCS_LOGID;
    public static String FROM_UK;

    private String yunDataTxt;
    private String cookie;
    private String logId;

    public synchronized void refresh(){
        JSONObject yunData = JSONObject.parseObject(yunDataTxt);
        PCS_COOKIE = cookie;
        PCS_TOKEN = yunData.getString("MYBDSTOKEN");
        FROM_UK = yunData.getString("SHARE_UK");
        PCS_LOGID = logId;
    }
}
