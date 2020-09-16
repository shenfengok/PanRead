package geek.me.javaapi.baidu.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import geek.me.javaapi.baidu.PcsConst;
import geek.me.javaapi.baidu.PcsUrlHelper;
import geek.me.javaapi.baidu.js.JsFunc;
import geek.me.javaapi.util.MyHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.script.ScriptException;
import java.util.List;

@Service
public class PcsDownService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MyHttpClient myHttpClient;

    /**
     * 获取"/apps/Cloud Sync/"下的下载连接
     * @param fsids
     * @return
     * @throws InterruptedException
     */
    public String dlink(List<String> fsids) throws InterruptedException, ScriptException, NoSuchMethodException {

        String signUrl = PcsUrlHelper.getSignUrl();
        JSONObject sj = myHttpClient.get(signUrl);
        JSONObject so = sj.getJSONObject("result");
        String sign = JsFunc.getSign4Down(so.getString("sign1"),so.getString("sign2"),so.getString("sign3"));
        String url = PcsUrlHelper.getAppDownUrl(fsids,sign,so.getString("timestamp"));
        JSONObject response = myHttpClient.get(url);
        return response.getString("dlink");
    }

    public String testDlink2(){
        String url = "BAIDUID=3A5606DA0B031D4A3247F8A6CF3CA139:FG=1; BDUSS=FvTmU3SGlhYWxBMERwQWVuUTg2MjNGS1puekFXR2pQfnNpQm9ZWFM4Q2Rsb0ZmRVFBQUFBJCQAAAAAAAAAAAEAAABr357z3bHD0sLSysAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAJ0JWl-dCVpfT1; BDUSS_BFESS=FvTmU3SGlhYWxBMERwQWVuUTg2MjNGS1puekFXR2pQfnNpQm9ZWFM4Q2Rsb0ZmRVFBQUFBJCQAAAAAAAAAAAEAAABr357z3bHD0sLSysAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAJ0JWl-dCVpfT1; pan_login_way=1; PANWEB=1; STOKEN=db6335f42b3a3f3e20bca1b8eaadab3ddfdb218d851d8d42dcd4624b08c01a3b; SCRC=f22e02210ee7e614de2b427a4a7ecab3; BIDUPSID=3A5606DA0B031D4A3247F8A6CF3CA139; PSTM=1599737263; BDCLND=H7yKQYFhm4GLQ3ay8sXnNL42GSOlYNMECW8C%2FihLVLw%3D; recommendTime=android2020-09-11%2020%3A26%3A00%20; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; Hm_lvt_7a3960b6f067eb0085b7f96ff5e660b0=1599736223,1599807582,1600072456,1600149840; Hm_lpvt_7a3960b6f067eb0085b7f96ff5e660b0=1600149844; PANPSC=17726692248997980610%3ADJI9ZdfpjgKV2doCoUnx9Ey7txDAjOFZAvuw0%2Bn1dPHZlPIhre87bdvIifvnJmpQ1HTbxvuL7YeaV1QHy3lx2ysL9B6RlQ7rI1%2BbtH%2BxEkIu%2FGN14ZZ7XoMUFw37y0KsoHpXTuaU%2F4ljy4bwAz5jQiHq5mg%2FcPBDsGdcW9T0tiRm65hzsZIwfCLkIoG3iFZjMKjxtxPUcwo%3D";
        String response = restTemplate.getForEntity(url, String.class).getBody();
        JSONObject o = JSON.parseObject(response);
        return "";
    }
}
