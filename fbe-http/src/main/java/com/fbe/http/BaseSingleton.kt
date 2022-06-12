package com.fbe.http

/**
 * 统一的单例模式-模板类
 */
//in P 逆变作为泛型参数入参，out T 协变作为泛型返回值类型
abstract class BaseSingleton<in P, out T> {
    //var 修饰了 out T，var 有setter为什么不会报错呢？是因为它是private修饰的去掉private就会报错
    private var instance: T? = null

    protected abstract val creator: (P?) -> T

    public fun getInstance(param: P? = null): T =
        instance ?: synchronized(this) {
            instance ?: creator(param).also { instance = it }
        }
}