package com.fscut.courier.utils;
import org.apache.commons.lang3.StringUtils;
/**
 * @author lxw
 */
public class ValidateUtil {
    private ValidateUtil() {
    }

    public static void paramNotNull(Object object, String errMsg) {
        paramExpTrue(object != null, errMsg);
    }

    public static void paramNotEmpty(String param, String errMsg) {
        paramExpTrue(StringUtils.isNotEmpty(param), errMsg);
    }

    public static void paramErrmsgEmpty(String errMsg) {
        paramExpTrue(StringUtils.isEmpty(errMsg), errMsg);
    }

    public static void paramExpTrue(boolean expValue, String errMsg) {
        if (!expValue) {
            throw new ValidateUtil.ParamInvalidException(errMsg);
        }
    }

    public static void paramExpTrue(boolean expValue, RuntimeException e) {
        if (!expValue) {
            throw e;
        }
    }

    public static void logicalNotNull(Object object, String errMsg) {
        logicalTrue(object != null, errMsg);
    }

    public static void logicalEmpty(String errMsg) {
        logicalTrue(StringUtils.isEmpty(errMsg), errMsg);
    }

    public static void logicalTrue(boolean expValue, String errMsg) {
        if (!expValue) {
            throw new ValidateUtil.LogicalException(errMsg);
        }
    }

    public static class LogicalException extends RuntimeException {
        private static final long serialVersionUID = -2364812553216588268L;

        public LogicalException() {
        }

        public LogicalException(String message) {
            super(message);
        }
    }

    public static class InternalException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public InternalException() {
        }

        public InternalException(String message) {
            super(message);
        }
    }

    public static class ParamInvalidException extends RuntimeException {
        private static final long serialVersionUID = 4379300311608048427L;

        public ParamInvalidException() {
        }

        public ParamInvalidException(String message) {
            super(message);
        }
    }
}
