package rumen.web.baidu;

import org.springframework.util.ResourceUtils;

import java.io.*;

public class PcsConst {
    public static String cookie = "";
    public static String shareUrl= "";
    public static String transferUlr = "";
    public static String appDownUlr = "";
    public static String getSignUlr = "";
    public static String transferForm = "";
    public static String delFolderUrl = "";
    public static String signJson = "";
    public static final String basePath = "/apps/Cloud Sync/";
    public static final String netdiskDownUrl = "http://pcs.baidu.com/rest/2.0/pcs/file?app_id=778750&ver=2.0&method=locatedownload&path=";

    static{
        cookie = readTxt("pcs/cookie.txt");
        shareUrl = readTxt("pcs/share-url.txt");
        transferUlr = readTxt("pcs/transfer-url.txt");
        appDownUlr = readTxt("pcs/app-down-url.txt");
        getSignUlr = readTxt("pcs/get-sign-url.txt");
        transferForm = readTxt("pcs/transfer-form.txt");
        signJson = readTxt("pcs/sign-json.txt");
        delFolderUrl = readTxt("pcs/del-folder-url.txt");

    }

    private static String readTxt(String txtLoc){
        File file = null;
        StringBuilder sb = new StringBuilder();
        try {
            file = ResourceUtils.getFile("classpath:"+ txtLoc);
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String data = null;
            while((data = br.readLine()) != null) {
                sb.append(data);
            }

            br.close();
            isr.close();
            fis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
