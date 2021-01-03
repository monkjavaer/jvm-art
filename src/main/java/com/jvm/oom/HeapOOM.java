package com.jvm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * java堆溢出。这个类用于模拟OutOfMemoryError(OOM)异常
 * java堆用于存放对象实例，所以只要不断创建对象，并且保证GC Roots 到对象之间有可达路径
 * 来避免垃圾回收机制清除这些对象，那对象数量达到java最大堆容量限制就将会OOM.
 *
 * vm Args:    -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * 将堆的最小值-Xms和最大值-Xmx设置一样就可以避免堆自动扩展。
 * -XX:+HeapDumpOnOutOfMemoryError出现OOM时可Dump出当前的内存堆转储快照以便分析。
 * @author monkjavaer
 * @date 2018/09/04 21:38
 */
public class HeapOOM {

    static  class  OOMObject{

    }

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<OOMObject>();
        while (true){
            list.add(new OOMObject());
        }
    }

    //java.lang.OutOfMemoryError: Java heap space
    //Dumping heap to java_pid6844.hprof ...
    //Heap dump file created [28171046 bytes in 0.078 secs]

    //OutOfMemoryError: Java heap space解决办法:
    //1.检查程序，看是否有死循环或不必要地重复创建大量对象（可以用内存映像分析工具分析）。
    //2.检查Java虚拟机堆的最小值-Xms和最大值-Xmx，与物理机内存对比看看能否调大，
    //从代码上检查，看看某些对象生命期过长的，尝试减少程序运行期的内存消耗。


}
