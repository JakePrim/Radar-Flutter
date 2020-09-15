import {createStore} from "redux";


const reducer = (state = {
    data: [
        {
            id: 1,
            title: "今天晚上升颗星",
            done: true
        }, {
            id: 2,
            title: "本周给大家录10集补充知识",
            done: false
        }
    ]
}, action) => {
    switch (action.type){
        case "ADD":
            console.log(action.title)
            return {
                ...state,
                data:[
                    ...state.data,
                    {
                        id:Date.now(),
                        title:action.title,
                        done:false
                    }
                ]
            }
        case "COMPLETE":
            return {
                ...state,
                data:action.data
            }
        default:
            return state;
    }
}

const store = createStore(reducer);

export default store;