/**
 * 函数中的作用域 深入理解
 * 函数作用域：指的是属于这个函数的全部变量都可以在整个函数的范围内使用及复用
 */

/**
 * 隐藏实现 自动执行 避免函数名的污染
 */
var a= 1;
(function IIFE(global){
    var a = 3;
    console.log(a);//3
    console.log(global.a);//1
})(window);//传递参数
console.log(a);//1

//UMD 的形式
(function IIFE(def){
    def(window);
})(function(global){
    var a = 4;
    console.log("2:"+a);//4
    console.log("2:"+global.a);//1
});
