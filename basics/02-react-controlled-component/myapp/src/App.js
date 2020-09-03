import React,{Component, Fragment} from 'react';
import LeavelWord from './LeaveWord';
import LeaveList from './LeaveList';

class App extends Component{
  state={
    data:[]
  }
  addWord=(word)=>{
    let {data} = this.state;
    let {name,info} = word;
    data.push({
      id:Date.now(),
      name,
      info
    })
    this.setState({
      data
    })
  }
  removeWord=(id)=>{
    let {data} = this.state;
    data = data.filter(item=>item.id!==id)
    this.setState({
      data
    })
  }
  render(){
    let {data} = this.state;
    console.log(data);
    return <Fragment>
      <LeavelWord addWord={this.addWord}/>
      <LeaveList data={data} removeWord={this.removeWord}/>
    </Fragment>
  }
}

export default App;
