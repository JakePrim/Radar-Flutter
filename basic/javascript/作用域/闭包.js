/**
 * 闭包：当函数可以记住并访问所在的词法作用域时，就产生了闭包。
 */

//词法作用域的查找规则只是闭包的一部分
// function foo(){
//     var a = 2;
//     function bar(){
//         console.log(a);//2 可以根据词法作用域的查找规则，查找到a
//     }
//     bar();
// }
// foo();

//闭包的展示如下
function foo() {
    var a = 2;

    function bar() {
        console.log(a);
    }

    return bar;
}

var bzz = foo(); //foo执行完毕后 并不会被垃圾回收，因为返回的bar依然持有foo的引用
//bar函数可以在定义时的词法作用域以外的地方被调用，而闭包使得这个函数可以继续访问定义时的词法作用域
bzz();//2 这就是闭包效果 bar持有foo作用域的引用，这个引用就是闭包
//无论何种方式对函数类型值传递，当函数在别处被调用时都可以观察到闭包
//例子：
var fns;

function f1() {
    var a = 21;

    function bar() {
        console.log(a);
    }

    fns = bar;
    b1(bar);
}

function b1(fn) {
    fn();//--- 看这就是闭包 21
    fns();// ---- 看这里发生了闭包
}

f1();

// for (var i = 0; i <=5; i++) {
//     //setTimeout函数会在for循环结束之后才会执行
//     (function (i){//将i 记录在一个独立的作用域中，每次迭代会生成一个新的作用域 当宏任务执行完毕才会执行微任务 setTimeout
//         setTimeout(function (){
//             console.log(i);
//         },i*1000)
//     })(i);
//     //先执行
//     console.log(i)
// }

//块级作用域优化
for (let j = 0; j <= 5; j++) {
    //使用let使得每次循环都会有一个作用域生成 let j = i; 而根据闭包原理 setTimeout持有j的引用，循环一次后 j不会被回收掉
    //setTimeout函数会在for循环结束之后才会执行
    setTimeout(function () {
        console.log(j);
    }, j * 1000)
    //先执行
    console.log(j)
}

//模块的概念  如果只需要一个实力，可以通过IIFE的方式实现
var cool = (function CoolModule() {
    var someting = "cool";
    var anthor = [1, 2, 3];

    function doSomething() {
        console.log(someting);
    }

    function doAnother() {
        console.log(anthor.join('!'));
    }

    return {
        doAnother: doAnother,
        doSomething: doSomething
    }
})();
cool.doAnother();
cool.doSomething();


//现代的模块管理器机制
const MyModule = (function Manager() {
    const modules = {};

    /**
     * 创建模块
     * @param name 模块名
     * @param deps 要引入的模块名
     * @param impl 模块的实现
     */
    function define(name, deps, impl) {
        for (let i = 0; i < deps.length; i++) {
            //根据模块名获取模块的实现并且存储
            deps[i] = modules[deps[i]];
        }
        //记录模块名的模块实现 impl传递deps 引入的模块
        modules[name] = impl.apply(impl, deps);
    }

    //获取某个模块
    function get(name) {
        return modules[name];
    }

    return {
        define,
        get
    }
})();

//bar 模块
MyModule.define("bar", [], function () {
    function hello(who) {
        return "Let me introduce " + who;
    }

    return {
        hello
    }
});

//foo模块，引入bar模块
MyModule.define("foo", ["bar"], function (bar) {
    function awasome() {
        //使用bar模块的方法
        console.log(bar.hello("happy").toUpperCase());
    }

    return {
        awasome
    }
})

const bar = MyModule.get("bar");
const foos = MyModule.get("foo");

console.log(bar.hello("xiaohua"));//Let me introduce xiaohua

foos.awasome();//LET ME INTRODUCE HAPPY

/**
 * 上述是由函数形成的模块机制，并不是静态的模块，可以在运行时修改一个模块的API
 * 在ES6的版本中提供了静态模块，ES6的模块没有“行内”格式，必须被独立的定义在一个文件中，一个文件一个模块(这里其实和java的Class非常相似)
 */




