package com.lzzy.project.backend.entity;

import lombok.Data;

/**
 * @author :lhy
 * @description : 返回工具类
 * @date :2023/05/19 下午 04:57
 */
@Data
public class RestBean<T> {
    private int status;
    private boolean success;
    private T message;

    public RestBean(int status, boolean success, T message) {
        this.status = status;
        this.success = success;
        this.message = message;
    }

    public static <T> RestBean<T> success(){
        return new RestBean(200, true, null);
    }

    public static <T> RestBean<T> success(T data){
        return new RestBean(200, true, data);
    }

    public static <T> RestBean<T> failure(int status){
        return new RestBean(status, false, null);
    }

    public static <T> RestBean<T> failure(int status,T data){
        return new RestBean(status, false, data);
    }
}
