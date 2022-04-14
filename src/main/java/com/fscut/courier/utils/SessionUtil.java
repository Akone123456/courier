package com.fscut.courier.utils;


import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
/**
 * @author lxw
 */
public class SessionUtil {
    private SessionUtil() {
    }

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        return attributes == null ? null : attributes.getRequest();
    }

    public static HttpServletResponse getResponse() {
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        return attributes == null ? null : attributes.getResponse();
    }

    public static HttpSession getSession() {
        HttpServletRequest request = getRequest();
        HttpSession session = request == null ? null : request.getSession();
        if (session == null) {
            throw new NullPointerException("Can not get current session!");
        } else {
            return session;
        }
    }

    public static void addToSession(String key, Serializable value) {
        getSession().setAttribute(key, value);
    }

    public static Object getFromSession(String key) {
        return getSession().getAttribute(key);
    }

    public static Object delFromSession(String key) {
        Object object = getFromSession(key);
        getSession().removeAttribute(key);
        return object;
    }

    public static boolean contains(String key) {
        return getSession().getAttribute(key) != null;
    }

    public static String getSessionId() {
        return getSession().getId();
    }

    public static boolean validateBySession(String key, String value, boolean ignoreCase) {
        HttpSession session = getSession();
        boolean var10000;
        if (session.getAttribute(key) != null) {
            label31: {
                if (value != null) {
                    if (ignoreCase) {
                        if (value.equalsIgnoreCase((String)session.getAttribute(key))) {
                            break label31;
                        }
                    } else if (value.equals(session.getAttribute(key))) {
                        break label31;
                    }
                }

                var10000 = false;
                return var10000;
            }
        }

        var10000 = true;
        return var10000;
    }
}
