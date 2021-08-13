import {hello} from "./bar";

let name= "jake";
function awesome(){
    console.log(hello(name).toUpperCase());
}

export {
    awesome
};