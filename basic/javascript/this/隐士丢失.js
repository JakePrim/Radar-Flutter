//2. this隐士绑定 -> 和调用位置有关
function fn() {
    console.log(this.a1);//2
}

var obj1 = {
    a1: 10,
    fo: fn
}

var obj2 = {
    a1: 42,
    obj1: obj1
}

obj1.fo();//10 this -> obj1

var f = obj1.fo;
//隐士丢失
f();//2 this -> window

obj2.obj1.fo();//10 this-> obj1

//2. 发生在回调函数中
function doFun(fn) {
    fn();
}

var a1 = "global"

doFun(obj1.fo)//global

//或者
setTimeout(obj1.fo,100);//global