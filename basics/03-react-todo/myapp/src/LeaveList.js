import React, { Component, createRef } from 'react';

export default class LeaveList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isEdit: false,
            val: this.props.data.info//复制原来的数据
        }
    }
    editIpt = createRef()

    componentDidUpdate(pervProps,pervState){
        if ((!pervState.isEdit) && this.state.isEdit) {
            this.editIpt.current.focus();
        }
    }

    render() {
        let { data, removeWord, todoDone ,changeTitle} = this.props;
        let { isEdit, val } = this.state;
        console.log(data);
        return <li className={isEdit ? 'editing' : ''}>
            <div className="top">
                <h3>{data.name}</h3>
                <input type="checkbox" onChange={({ target }) => {
                    todoDone(data.id, target.checked);
                }} checked={data.done} />
            </div>
            <p onDoubleClick={() => {
                this.setState({
                    isEdit: true,
                });
            }}>{data.info}</p>
            <textarea ref={this.editIpt} 
                value={val}
                onChange={({target})=>{
                    this.setState({
                        val:target.value
                    })
                }}
                onBlur={()=>{
                    //失去焦点
                    if(val.trim()){
                        //不为空字符串改变
                        changeTitle(data.id,val);
                    }else{
                        this.setState({
                            val:data.info
                        });
                    }
                    this.setState({
                        isEdit:false
                    })
                }}
            ></textarea>
            <a onClick={() => {
                removeWord(data.id);
            }}>删除</a>
        </li>
    }
} 