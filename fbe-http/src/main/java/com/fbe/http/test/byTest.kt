package com.fbe.http

import android.widget.TextView
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class byTest {
    //属性委托
    val a: Int = 0
    val count: Int by ::a //::a是属性的引用

    //懒加载委托
    val data: String by lazy {
        request()
    }

    fun request(): String {
        println("网络请求")
        return "请求结果"
    }
}

// 自定义委托的方式1
class StringDelegate(private var s: String = "hello") {
    operator fun getValue(thisRef: Owner, property: KProperty<*>): String {
        return s
    }

    operator fun setValue(thisRef: Owner, property: KProperty<*>, value: String) {
        s = value
    }
}

// 自定义委托的方式2 实现ReadWriteProperty来进行自定义委托
class StringDelegate2(private var s: String = "hello") : ReadWriteProperty<Owner, String> {
    override fun getValue(thisRef: Owner, property: KProperty<*>): String {
        return s
    }

    override fun setValue(thisRef: Owner, property: KProperty<*>, value: String) {
        s = value
    }
}

//提供委托 provideDelegate来实现 在属性委托之前做一些额外的工作
class SmartDelegate : PropertyDelegateProvider<Owner, ReadWriteProperty<Owner, String>> {
    override operator fun provideDelegate(
        thisRef: Owner,
        prop: KProperty<*>
    ): ReadWriteProperty<Owner, String> {
        //根据属性名 来进行委托的变更
        return if (prop.name.contains("log")) {
            StringDelegate2("log")
        } else {
            StringDelegate2("normal")
        }
    }
}

class Owner {
    var text: String by StringDelegate()

    var logText: String by SmartDelegate()
    var normalText: String by SmartDelegate()
}

// 利用委托实现可见性的封装
class Model {
    //data的修改权 留在内部
    private val _data: MutableList<String> = mutableListOf()

    //只读的data 对外暴露
    val data: List<String> by ::_data

    fun load() {
        _data.add("hello")
    }
}

// 数据与view绑定
operator fun TextView.provideDelegate(value: Any?, property: KProperty<*>) =
    object : ReadWriteProperty<Any?, String?> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): String? = text.toString()


        override fun setValue(thisRef: Any?, property: KProperty<*>, value: String?) {
            text = value
        }
    }

fun main() {
//    val textView = TextView(this)
//    var message: String? by textView
//    message = "123"
//    textView.text = "456"
//    println(message)
}


