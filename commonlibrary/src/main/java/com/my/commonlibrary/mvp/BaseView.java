package com.my.commonlibrary.mvp;

/**
 * 这里除了定义handleMessage,可以定义一些比较常用,每个view都会用到的方法
 * 因为View的实现类可能会是Activity,Fragment或者Dialog以及一些自定义View,所以不能定义一些某个类特有的方法
 * 比如startActivity就是Activity特有的,其他view实现类并不一定具备这个功能
 */
public interface BaseView {

    /**
     * 处理消息,这里面和handler的原理一样,通过swith(what),做不同的操作
     * @param mvpMessage
     */
    void handleMessage(MVPMessage mvpMessage);

}
