package com.company.proxyproject.common;

import com.company.proxyproject.entity.auth.User;

public class GlobalContextHolder {
    private static final GlobalContextHolder INSTANCE = new GlobalContextHolder();

    private static final ThreadLocal<String> TRACE_ID = ThreadLocal.withInitial(String::new);
    private static final ThreadLocal<User> USER = ThreadLocal.withInitial(User::new);

    private GlobalContextHolder() {

    }

    public static void setUser(User user){
        GlobalContextHolder.USER.set(user);
    }

    public static User getUser(){
        return GlobalContextHolder.USER.get();
    }

    public static void clearAll(){
        GlobalContextHolder.TRACE_ID.remove();
    }

    public static GlobalContextHolder getInstance(){
        return INSTANCE;
    }

    public static String getTraceId(){
        return GlobalContextHolder.TRACE_ID.get();
    }

    public static void setTraceId(final String traceId){
        GlobalContextHolder.TRACE_ID.set(traceId);
    }
}
