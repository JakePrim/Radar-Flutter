import React, {useState} from "react";
import {useDispatch} from "react-redux";

function AddTodo(){
    const [title,setTitle] = useState('');
    let dispatch = useDispatch();
    return <div id="create-todo">
        <input
            id="new-todo"
            placeholder="What needs to be done?"
            autoComplete="off"
            type="text"
            onKeyDown={({ keyCode, target }) => {
                let val = target.value;
                if (keyCode == 13 && val.trim()) {
                    setTitle(val);
                    dispatch({
                        type:'ADD',
                        title:val
                    });
                    target.value = "";
                }
            }}
        />
    </div>
}

export default AddTodo;