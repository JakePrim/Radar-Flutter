import React, {Fragment} from "react";
import './index.css';
import AddTodo from "./addTodo";
import Stats from "./stats";
import Todo from "./todo";
import {useSelector} from "react-redux";

function App() {
    const data = useSelector(state=>state.data);
    return <div id="todoapp">
        <div className="title">
            <h1>todo</h1>
        </div>
        <div className="content">
            <AddTodo />
            {data.length > 0 ?
                <Fragment>
                    <Todo />
                    <Stats />
                </Fragment>
                : ""
            }
        </div>
    </div>
}

export default App;