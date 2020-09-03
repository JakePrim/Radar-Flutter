import React,{Component} from 'react';

export default class List extends Component{
    state={
        show:false,
    }
    render(){
        //获取参数
        console.log(this.props);
        let {data,setOpenName,openName} = this.props;
        let {name,children} = data;
        return <dl className={(name === openName) ?"friend-group expanded" : "friend-group"}>
            <dt onClick={()=>{
                setOpenName((name === openName) ? "" : name)
            }}>{name}</dt>
            {children.map((item,index)=>{
                return <dd key={index}>{item.name}</dd>
            })}
        </dl>
    }
}