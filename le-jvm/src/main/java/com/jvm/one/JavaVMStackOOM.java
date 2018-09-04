package com.jvm.one;

/**
 * 创建线程导致内存溢出
 * VM Args:  -Xss2M
 * -Xss 栈容量
 * 如果线程请求的栈深度大于虚拟机允许的最大深度，StackOverflowError
 * 如果虚拟机在拓展栈时无法申请到足够的内存空间，OutOfMemoryError
 * @author tangquanbin
 * @date 2018/09/04 22:45
 */
public class JavaVMStackOOM {
    private void  dontStop(){
        while (true){

        }
    }

    public void stackLeakByThread() {
        while (true){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    dontStop();
                }
            });
            thread.start();
        }
    }

    public static void main(String[] args) {
        JavaVMStackOOM oom = new JavaVMStackOOM();
        oom.stackLeakByThread();
    }

    //出现OutOfMemoryError:unable to create new native thread
    //解决优化：通过减少最大堆和栈容量来换取更多的线程

}
