import {useDispatch, useSelector} from "react-redux";

function useChangeDone() {
    const dispatch = useDispatch();
    let data = useSelector(state => state.data);
    return (id, done) => {
        data.forEach(item => {
            if (item.id === id) {
                item.done = done;
            }
        });
        dispatch({
            type: "COMPLETE",
            data
        });
    }
}

function useRemove(){
    const dispatch = useDispatch();
    let data = useSelector(state => state.data);
    return (id) => {
        data = data.filter(item=>item.id!==id);
        dispatch({
            type: "COMPLETE",
            data
        });
    }
}

function useChangeTitle(){
    const dispatch = useDispatch();
    let data = useSelector(state => state.data);
    return (id,val) => {
        data.forEach(item => {
            if (item.id === id) {
                item.title = val;
            }
        });
        dispatch({
            type: "COMPLETE",
            data
        });
    }
}

function useRemoveDone(){
    const dispatch = useDispatch();
    let data = useSelector(state => state.data);
    return () => {
        data = data.filter(item=>!item.done)
        dispatch({
            type: "COMPLETE",
            data
        });
    }
}

export {useChangeDone,useRemove,useChangeTitle,useRemoveDone}