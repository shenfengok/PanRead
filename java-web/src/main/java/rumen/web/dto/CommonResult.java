package rumen.web.dto;

import lombok.Data;

@Data
public class CommonResult<T> {

    private String status;
    private String msg;
    private T data;

    public static <T> CommonResult success(T data) {
        CommonResult result = new CommonResult();
        result.setData(data);
        result.setStatus("ok");
        return result;
    }

    public static <T> CommonResult success() {
        CommonResult result = new CommonResult();
        result.setData("");
        result.setStatus("ok");
        return result;
    }

    public static CommonResult fail(String msg) {
        CommonResult result = new CommonResult();
        result.setData(msg);
        result.setStatus("fail");
        return result;
    }
}
