import React,{Component} from 'react';
import './css/index.css';
import data from './data';
import List from './List';

export default class App extends Component{
    state={
        openName:""//存储当前哪一项展开，如果没有展开项 openName为空
    }
    setOpenName=(openName)=>{//使用箭头函数否则this 是undefined
        console.log(openName,this);
        this.setState({
            openName
        })
    }
    render() {
        return <div className="friend-list">
            {
                data.map((item,index)=>{
                    return <List key={index} data={item} openName={this.state.openName} setOpenName={this.setOpenName}></List>
                })
            }
        </div>;
    }
}