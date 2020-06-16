package com.chaolifang.result;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BaseResult implements Serializable {
    public static final String RESULT_OK = "ok";
    public static final String RESULT_NOT_OK = "not_ok";
    public static final String MESSAGE = "成功操作";

    private String result;
    private Object data;
    private String message;

    public static BaseResult ok(Object data) {
        return createResult(RESULT_OK, data, MESSAGE);
    }

    public static BaseResult ok() {
        return createResult(RESULT_OK, null, MESSAGE);
    }

    public static BaseResult notOk(String errorReason) {
        return createResult(RESULT_NOT_OK, null, errorReason);
    }

    /**
     * @param result
     * @param data
     * @param message
     * @return
     */
    private static BaseResult createResult(String result, Object data, String message) {
        BaseResult baseResult = new BaseResult();
        baseResult.setResult(result);
        baseResult.setData(data);
        baseResult.setMessage(message);

        return baseResult;
    }
}
