package com.chaolifang.result;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BaseResult implements Serializable {
    public static final String RESULT_OK = "ok";
    public static final String RESULT_NOT_OK = "not_ok";
    public static final String SUCCESS = "成功操作";

    private String result;
    private Object data;
    private String success;

    public static BaseResult ok(Object data) {
        return createResult(RESULT_OK, data, SUCCESS);
    }

    public static BaseResult notOk(String errorReason) {
        return createResult(RESULT_NOT_OK, null, errorReason);
    }

    /**
     * @param result
     * @param data
     * @param success
     * @return
     */
    private static BaseResult createResult(String result, Object data, String success) {
        BaseResult baseResult = new BaseResult();
        baseResult.setResult(result);
        baseResult.setData(data);
        baseResult.setSuccess(success);

        return baseResult;
    }
}
