package com.yuan.domain;
import lombok.Data;


@Data
public class ResultData<T> {

        public static final Integer SUCCESS_CODE = 2000;
        public static final Integer FAIL_CODE = 4000;
        public static final String SUCCESS_MESSAGE = "success";
        public static final String FAIL_MESSAGE = "fail";
        /**
         * 返回状态码
         */
        private Integer code;
        /**
         * 返回信息
         */
        private String message;
        /**
         * 返回数据
         */
        private T data;

        private ResultData() {
        }

        public static <T> ResultData<T> success() {
            ResultData<T> ResultData = new ResultData<>();
            ResultData.setCode(SUCCESS_CODE);
            ResultData.setMessage(SUCCESS_MESSAGE);
            return ResultData;
        }

        public static <T> ResultData<T> success(T data) {
            ResultData<T> ResultData = success();
            ResultData.setData(data);
            return ResultData;
        }

        public static <T> ResultData<T> success(String message, T data) {
            ResultData<T> ResultData = success();
            ResultData.setMessage(message);
            ResultData.setData(data);
            return ResultData;
        }

        public static <T> ResultData<T> success(Integer code, String message, T data) {
            ResultData<T> ResultData = new ResultData<>();
            ResultData.setCode(code);
            ResultData.setMessage(message);
            ResultData.setData(data);
            return ResultData;
        }

        public static <T> ResultData<T> fail() {
            ResultData<T> ResultData = new ResultData<>();
            ResultData.setCode(FAIL_CODE);
            ResultData.setMessage(FAIL_MESSAGE);
            return ResultData;
        }

        public static <T> ResultData<T> fail(T data) {
            ResultData<T> ResultData = fail();
            ResultData.setData(data);
            return ResultData;
        }

        public static <T> ResultData<T> fail(String message, T data) {
            ResultData<T> ResultData = fail();
            ResultData.setMessage(message);
            ResultData.setData(data);
            return ResultData;
        }

        public static <T> ResultData<T> fail(Integer code, String message) {
            ResultData<T> ResultData = fail();
            ResultData.setCode(code);
            ResultData.setMessage(message);
            return ResultData;
        }

        public static <T> ResultData<T> fail(Integer code, String message, T data) {
            ResultData<T> ResultData = new ResultData<>();
            ResultData.setCode(code);
            ResultData.setMessage(message);
            ResultData.setData(data);
            return ResultData;
        }
}

