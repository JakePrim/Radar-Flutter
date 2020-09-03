import React,{Component} from 'react';

export default class LeaveList extends Component{
    render(){
        let {data,removeWord} = this.props;
        console.log(data);
        return <ul className="messageList">
            {
                data.map((item,index)=>{
                return <li key={index}>
                <h3>{item.name}</h3>
                <p>{item.info}</p>
                <a onClick={()=>{
                    removeWord(item.id);
                }}>删除</a>
                </li>
                })
            }
        </ul>
    }
} 