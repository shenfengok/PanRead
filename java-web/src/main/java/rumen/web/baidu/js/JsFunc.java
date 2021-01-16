package rumen.web.baidu.js;

import geek.me.javaapi.baidu.PcsConst;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JsFunc {

    private static ScriptEngine nashorn;

    static {
        nashorn = new ScriptEngineManager().getEngineByName("nashorn");
        try {
            nashorn.eval("load('src/main/java/geek/me/javaapi/baidu/js/util.js')");
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取logId
     * @return
     * @throws ScriptException
     * @throws NoSuchMethodException
     */
    public static String getLogId() throws ScriptException, NoSuchMethodException {
        Invocable jsInvoke = (Invocable) nashorn;
        Object result = jsInvoke.invokeFunction("getLogID", new Object[] {PcsConst.cookie});
        return result.toString();
    }

    /**
     * 获取下载的sign
     * @param sign1
     * @param sign2
     * @param sign3
     * @return
     * @throws ScriptException
     * @throws NoSuchMethodException
     */
    public static String getSign4Down(String sign1,String sign2,String sign3) throws ScriptException, NoSuchMethodException {
        Invocable jsInvoke = (Invocable) nashorn;
        Object result = jsInvoke.invokeFunction("getSign", new Object[] {sign1,sign2,sign3});
        return result.toString();
    }
}
