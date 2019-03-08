package com.wulis.jsoup.common.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by dell on 2018/12/29.
 */
public class JSONResult<T> {
    private int code;

    private String msg;

    private T data;

    private boolean status;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("msg", msg);
        jsonObject.put("data", data);
        jsonObject.put("status", status);
        return jsonObject;
    }

    @Override
    public String toString() {
        return toJSONObject().toString();
    }
}
