package com.algo.tech.ex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynamicInvocationHandler implements InvocationHandler {
    private static Logger logger= LoggerFactory.getLogger(DynamicInvocationHandler.class);
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info("Invoked method: {}", method.getName());
        return 42;
    }
}
