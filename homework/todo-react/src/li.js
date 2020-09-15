import React, {createRef, useEffect, useState} from "react";
import {useDispatch} from "react-redux";
import {useChangeDone, useChangeTitle, useRemove} from "./store/action";

function Li(props) {
    const [edit,setEdit] = useState(false);
    const [val,setVal] = useState("");
    let {data} = props;
    let {id,title} = data;
    const [done,setDone] = useState(data.done);
    const changeDone = useChangeDone();
    const remove = useRemove();
    const changeTitle = useChangeTitle();
    const editIpt = createRef();
    useEffect(()=>{
        if(edit){
            editIpt.current.focus();
        }
    },[]);
    return <li className={edit ? "editing" : ""}>
        <div className={`todo ${done ? "done" : ""}`}>
            <div className="display">
                <input
                    className="check"
                    type="checkbox"
                    checked={done}
                    onChange={({target}) => {
                        console.log(target.checked);
                        setDone(target.checked);
                        changeDone(id,target.checked);
                    }}
                />
                <div
                    className="todo-content"
                    onDoubleClick={() => {
                        setEdit(true)
                    }}
                >{title}</div>
                <span
                    className="todo-destroy"
                    onClick={() => {
                        remove(id);
                    }}
                ></span>
            </div>
            <div className="edit">
                <input
                    className="todo-input"
                    type="text"
                    ref={editIpt}
                    value={val}
                    onChange={({target}) => {
                        setVal(target.value);
                    }}
                    onBlur={() => {
                        if (val.trim()) {
                            changeTitle(id, val);
                        } else {
                            setVal(title);
                        }
                        setEdit(false);
                    }}
                />
            </div>
        </div>
    </li>
}

export default Li;