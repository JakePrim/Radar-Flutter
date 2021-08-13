/**
 * this 的深入理解
 * this 提供了一种隐士传递对象的引用
 * 函数中的this是在调用时绑定的，取决于函数的调用位置
 */
//假设没有this 我们需要显示的传递上下文对象
function identifyNo(context){
    return context.name.toUpperCase();
}

function speakNo(context){
    console.log("Hello I'm no this "+identifyNo(context));
}

const me = {
    name:"KyLe"
}

const you = {
    name:"Reader"
}

speakNo(me);//Hello I'm no this KYLE

//有了this 提供了更加优雅的方式隐士的传递上下文对象
function identify(){
    return this.name.toUpperCase();
}

function speak(){
    var greeting = "Hello I'm" + identify.call(this);
    console.log(greeting);
}


//call() 函数就是 显示的设置this对象
console.log(identify.call(me))//KYLE
console.log(identify.call(you))//READER

speak.call(me);//Hello I'mKYLE   修改this的指向为 me

speak.call(you);//Hello I'mREADER 修改this的指向为you

//什么是调用栈
function baz() {
    //当前调用栈 baz
    console.log("baz");
    boo();
}

function boo() {
    //当前调用栈 baz -> boo
    console.log("boo");
    foo();
}

function foo() {
    //当前调用栈 baz -> boo -> foo
    console.log("foo");
}
baz();

//1. this默认绑定-> 全局对象 Global
function fn() {
    console.log(this.a1);//2
}
var a1 = 2;//注意只有var声明的变量才会添加到全局对象中去
fn();



//3. 硬绑定
/**
 * 简单的辅助绑定函数
 * @param {*} fn 
 * @param {*} obj 
 * @returns 
 */
function bind(fn,obj){
    return function(){
        return fn.apply(obj,arguments);
    }
}

var obj2 = {
    a:3
};

function foo3(something){
    console.log(this.a,something);
    return this.a + something;
}

var bar2 = bind(foo3,obj2)

console.log(bar2(5));//8






