import React,{Component} from 'react'
import LeaveList from './LeaveList';

export default class Todo extends Component{
    render(){
        let {data} = this.props;
        return <ul className="messageList">{
            data.map((item,index)=>{
                return <LeaveList key={item.id} {...this.props} data={item}/>
            }
        )}
        </ul>
    }
}