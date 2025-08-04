package com.algo.tech.ex;

import java.lang.reflect.Proxy;
import java.util.Map;

public class DynamicProxyTest {
    public static void main(String[] args){
        proxyTestCase2();
    }

    private static void proxyTestCase2(){
        Map proxyInstance =(Map) Proxy.newProxyInstance(
                DynamicProxyTest.class.getClassLoader(),
                new Class[]{Map.class},
                (proxy, method, methodArgs) ->{
                    if(method.getName().equals("get")){
                        return 42;
                    }else{
                        throw new UnsupportedOperationException(
                                "Unsupported method: "+ method.getName()
                        );
                    }
                }
        );
        Integer nValue= (Integer) proxyInstance.get("hello");
        assert nValue==42;
        try {
            proxyInstance.put("hello", "world");
        }catch( Exception ex){
            ex.printStackTrace();
            assert ex instanceof UnsupportedOperationException;
        }
    }
    private static void proxyTestCase1() {
        Map proxyInstance =(Map) Proxy.newProxyInstance(
                DynamicProxyTest.class.getClassLoader(),
                new Class[]{Map.class},
                new DynamicInvocationHandler()
        );
        proxyInstance.put("hello", "proxy");
    }
}
