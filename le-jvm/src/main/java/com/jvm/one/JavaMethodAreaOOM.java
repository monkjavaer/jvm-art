package com.jvm.one;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 借助CGLIB使方法区出现OutOfMemoryError
 *
 * VM Args: -XX:PermSize=10M -XX:MaxPermSize=10M
 * -XX:PermSize  -XX:MaxPermSize限制方法区大小，从而间接限制常量池容量。
 * 因为运行时常量池是方法区的一部分。
 * @author tangquanbin
 * @date 2018/09/04 23:16
 */
public class JavaMethodAreaOOM {

    static class OOMObject{

    }
    public static void main(String[] args) {
        while (true){
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    return methodProxy.invokeSuper(objects,args);
                }
            });
            enhancer.create();
        }
    }

    //OutOfMemoryError:PermGen space
    //经常动态生成大量Class的应用中应注意

}
