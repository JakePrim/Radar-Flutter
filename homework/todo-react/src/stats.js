import React, {useEffect, useState} from "react";
import {useSelector, useStore} from "react-redux";
import {useRemoveDone} from "./store/action";

function Stats() {
    const data = useSelector(state => state.data);
    let [doneLen,setDoneLen] = useState(data.filter(item => item.done).length)
    let [unDoneLen,setUnDoneLen] = useState(data.length - doneLen);
    let store = useStore();
    useEffect(() => {
        const unsubscribe = store.subscribe(() => {
            const data = store.getState();
            console.log(data);
            setDoneLen(data.filter(item => item.done).length);
            setUnDoneLen(data.length - doneLen);
        });
    }, [])
    const removeDone = useRemoveDone();
    return <div id="todo-stats">
      <span className="todo-count">
        <span className="number">{unDoneLen}</span>
        <span className="word">项待完成</span>
      </span>
        {
            doneLen > 0 ?
                <span className="todo-clear">
            <a onClick={() => {
                removeDone();
            }}>Clear <span>{doneLen}</span> 已完成事项</a>
          </span>
                : ""
        }
    </div>
}

export default Stats;