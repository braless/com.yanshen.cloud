package com.yanshen.java.bean;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.ref.WeakReference;

import static org.springframework.transaction.annotation.Propagation.SUPPORTS;

/**
 * 引用
 */
public class Reference {


    public static void main(String[] args) {

    }

    /**
     * 强引用是使用最普遍的引用。如果一个对象具有强引用，那垃圾回收器绝不会回收它。如下：
     *
     * 当内存空间不足时，Java虚拟机宁愿抛出OutOfMemoryError错误，使程序异常终止，也不会靠随意回收具有强引用的对象来解决内存不足的问题。
     * 如果强引用对象不使用时，需要弱化从而使GC能够回收，如下：
     *     strongReference = null;
     * 显式地设置strongReference对象为null，或让其超出对象的生命周期范围，则gc认为该对象不存在引用，这时就可以回收这个对象。具体什么时候收集这要取决于GC算法。
     *
     * 作者：零壹技术栈
     * 链接：https://juejin.cn/post/6844903665241686029
     * 来源：稀土掘金
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */
    public void strongRefer(){
        Object strongReference = new Object();
    }

    /**
     * 软引用
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void weakRefer(){
        String str = new String("abc");
        WeakReference<String> weakReference = new WeakReference<>(str);
        str = null;
    }

}
