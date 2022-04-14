package com.fscut.courier.exception;

import java.io.Serializable;

/**
 * @author lxw
 */
public class BizException extends RuntimeException{
    private static final long serialVersionUID = 5582782064302685659L;
    private final Integer status;
    private final Serializable object;

    public BizException(String message) {
        super(message);
        this.status = null;
        this.object = null;
    }

    public BizException(String message, Serializable object) {
        super(message);
        this.status = null;
        this.object = object;
    }

    public BizException(int status, String message, Serializable object) {
        super(message);
        this.status = status;
        this.object = object;
    }

    public Integer getStatus() {
        return this.status;
    }

    public Object getObject() {
        return this.object;
    }
}
