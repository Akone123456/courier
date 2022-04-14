package com.fscut.courier.utils;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author lxw
 */
public class ResultUtil {

    public ResultUtil() {
    }

    public static ResultUtil.Result ok(Object data) {
        return new ResultUtil.Result(data);
    }

    public static ResultUtil.Result ok() {
        return new ResultUtil.Result((Object)null);
    }

    public static ResultUtil.Result ok(Object data, int count) {
        return new ResultUtil.Result(data, count);
    }

    public static ResultUtil.Result error(ResultUtil.ResponseStatusEnum status, String msg) {
        return error(status, msg, (Object)null);
    }

    public static ResultUtil.Result error(int status, String msg) {
        return error(status, msg, (Object)null);
    }

    public static ResultUtil.Result error(int status, String msg, Object object) {
        return new ResultUtil.Result(status, msg, object);
    }

    public static ResultUtil.Result error(ResultUtil.ResponseStatusEnum status, String msg, Object object) {
        return error(status.getStatusCode(), msg, object);
    }

    public static ResultUtil.Result logicalError(String msg) {
        return logicalError(msg, (Object)null);
    }

    public static ResultUtil.Result logicalError(String msg, Object object) {
        return error(ResultUtil.ResponseStatusEnum.LOGICAL_ERROR, msg, object);
    }

    public static ResultUtil.Result paramsError(String msg) {
        return error(ResultUtil.ResponseStatusEnum.PARAMS_INVALID, msg);
    }

    public static ResultUtil.Result internalError(String msg) {
        return error(ResultUtil.ResponseStatusEnum.INTERNAL_ERROR, msg);
    }

    public static ResultUtil.Result notLoginError(String msg) {
        return error(ResultUtil.ResponseStatusEnum.LOGIN_REQUIRED, msg);
    }

    public static ResultUtil.Result requestDuplicated(String msg) {
        return error(ResultUtil.ResponseStatusEnum.REQUEST_DUPLICATED, msg);
    }

    public static ResultUtil.Result responseRedirect(String url) {
        return error(ResultUtil.ResponseStatusEnum.RESPONSE_REDIRECT, "", url);
    }

    public static enum ResponseStatusEnum {
        STATUS_OK(0),
        PARAMS_INVALID(-1),
        LOGIN_REQUIRED(-2),
        REQUEST_DUPLICATED(-3),
        INTERNAL_ERROR(-4),
        LOGICAL_ERROR(-5),
        RESPONSE_REDIRECT(302);

        private Integer statusCode;

        private ResponseStatusEnum(Integer statusCode) {
            this.statusCode = statusCode;
        }

        public Integer getStatusCode() {
            return this.statusCode;
        }
    }

    public static class Result {
        private Integer status;
        private String msg;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Object data;

        public Result() {
        }

        Result(Integer status, String msg, Object data) {
            this.status = status;
            this.msg = msg;
            this.data = data;
        }

        Result(Object data) {
            this(ResultUtil.ResponseStatusEnum.STATUS_OK.getStatusCode(), "OK", data);
        }

        Result(Object data, int count) {
            this(ResultUtil.ResponseStatusEnum.STATUS_OK.getStatusCode(), "OK", new ResultUtil.Result.PageObjectWrapper(data, count));
        }

        public Integer getStatus() {
            return this.status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getMsg() {
            return this.msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Object getData() {
            return this.data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        private static class PageObjectWrapper {
            private int total;
            private Object items;

            public int getTotal() {
                return this.total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public Object getItems() {
                return this.items;
            }

            public void setItems(Object items) {
                this.items = items;
            }

            PageObjectWrapper(Object items, int total) {
                this.total = total;
                this.items = items;
            }
        }
    }

}
