/**
 * 块作用域
 */

//var 不支持块作用域 var最终会属于当前的函数作用域 可以假设最外层也就一个函数作用域
var foo = true;
if (foo){
    var a = foo*2;
    console.log(a);//2
}
console.log(a);//2

//try/catch会创建块级作用域 只能在catch中才会有作用，但是并不能在开发项目中起到作用
try{
    // undefined();//执行一个非法操作，制造一个异常
}catch (e) {
    console.log("error message:"+e);//error message:TypeError: undefined is not a function
}
// console.log(e);//ReferenceError: e is not defined

//let、const
//let可以绑定到任意的作用域中{.....} let声明的变量隐士的劫持了所在的块级作用域
if (foo){
    //也可以显示的创建块 整个 块可以方便移动，不会影响if声明的位置的其他代码
    {
        let bar = foo * 2;
        console.log(bar);
    }
}
// console.log(bar);//ReferenceError: bar is not defined
{
    //注意使用let声明的变量不会被提升
    // console.log(bar);//ReferenceError: Cannot access 'bar' before initialization
    let bar = 3;
}

//块级作用域非常有用的原因在于：闭包和垃圾收集
function process(data){
    //.....做一些事情
}

{
// var someRealBigData = {};//一些大的数据 var 被window持有 存在Global作用域 不会被回收掉
    let someRealBigData = {};//一些大的数据 只在该块级作用域 执行完毕后回收掉 存在Block作用域

// 理论上执行完毕后 会被回收掉
    process(someRealBigData);
}


var btn = document.getElementById("mybtn");

//这里形成一个覆盖整个作用域的闭包
btn.addEventListener("click",function (event) {
    console.log("button clicked");
},false);

//let 的for循环
for (let i = 0; i < 3; i++) {
    console.log(i);
}
//let for循环的原理
{
    let j;
    for (j = 0; j < 3; j++) {
        let i = j;//每个迭代重新绑定！ i无法被外部调用的
        console.log(i);
    }
}

//const     也是创建块级作用域变量，变量的值是固定的常量，必须赋值 不能修改
{
    const a = 1;
    // a = 2; 错误
}

// 总结：任何声明在某个作用域的变量，都将附属于这个作用域