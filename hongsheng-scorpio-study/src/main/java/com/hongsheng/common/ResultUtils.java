package com.hongsheng.common;

public class ResultUtils {

    public static ResultDTO success(Object data) {
        return new ResultDTO(data);
    }

    public static ResultDTO success() {
        return new ResultDTO();
    }

    public static ResultDTO fail(int code, String msg, Object data) {
        return new ResultDTO(code, msg, data);
    }

    public static ResultDTO fail(int code, String msg) {
        return fail(code, msg, null);
    }

}
