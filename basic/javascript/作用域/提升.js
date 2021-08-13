/**
 * 提升：先有鸡还是先有蛋的问题 JavaScript代码真的是从上到下一行一行执行的吗？
 * TODO 所有的声明(变量和函数)都会被移动到各自作用域的最顶端。声明才会被提升，而赋值在内的操作不会被提升。
 * 重复声明：普通的var和函数重复声明，var的声明会被忽略，函数优先提升。如果函数重复声明，后声明的会覆盖之前声明的
 */

a = 2;
var a;
console.log(a);//2 a输出的并不是undefined 而是2
/**
 * 等价于：
 * var a;
 * a = 2;
 * console.log(a)
 */

//考虑如下代码
console.log(b);//这里不会输出3，而是undefined
var b = 3;
/**
 * 等价于：
 * var b;
 * console.log(b)
 * b = 3;
 */

//到底是声明(蛋)在前，还是赋值(鸡)在前
/**
 * js在编译阶段：一部分工作就是找到所有声明，并在合适的作用域将他们关联起来，变量和函数在内的所有声明都会在任何代码被执行前处理
 */
//var b = 3;会拆分成 var b;(在编译阶段) b = 3;(留在原地等待执行阶段)
//先声明后赋值，也就是变量的声明会被提升，只有变量本身会被提升，赋值和其他逻辑留在原地执行

//函数的声明也会被提升，每个 作用域都会进行提升操作
foo();

function foo() {
    console.log(a);//undefined
    var a = 10;
    /**
     * var a;
     * console.log(a);
     * a = 10;
     */
}

/**
 * 等价于：
 * function foo() {
 * var a;
 * console.log(a);
 * a = 10;
 *}
 * foo();
 */

//TODO 注意：函数表达式不会被提升
// fooT();//TypeError: fooT is not a function ；fooT会被提升
// bar();//调用具名函数，这里也会报错 因为fooT 没有被赋值 ReferenceError: bar is not defined
var fooT = function bar(){
}
/**
 * 等价于：
 * var fooT;
 * fooT();
 * bar();
 * fooT = function (){
 *     var bar = --self--
 * }
 */

/**
 * 函数和变量都会被提升，但是函数优先提升还是变量呢？
 * 需要记住：JavaScript 函数是一等共鸣
 * TODO 函数的优先最高
 */
//如下代码：
bar();// 输出：1
var bar;//TODO 函数优先提升，重复声明了所以被忽略了 函数的声明会提升到普通变量之前

function bar(){
    console.log(1);
}
bar = function (){
    console.log(2);
}
bar();//2
//由于函数的优先级最高的，在多个重复声明的代码中等价于如下：
/**
 * function bar(){
 *     console.log(1);
 * }
 * //var bar; 重复声明被忽略
 * bar();//1
 * bar = function(){
 *     console.log(2);
 * }
 * bar();//2
 */

//TODO :尽管var的重复声明会被忽略掉，但是函数的重复声明后面的会覆盖前面的
zoo();//3
function zoo(){
    console.log(1);
}
var zoo = function (){
    console.log(2);
}
function zoo(){
    console.log(3);
}
zoo();//2
/**
 * 上述代码经过编译后：
 * function zoo(){
 *     console.log(3);
 * }
 * //var zoo; 重复声明被忽略了
 * zoo(); 1
 * zoo = function(){
 *     console.log(2);
 * }
 * zoo(); 2
 */

