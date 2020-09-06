import React,{Component, Fragment} from 'react';
import LeavelWord from './LeaveWord';
import Todo from './Todo';
import Footer from './Footer';

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
      info,
      done:false
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
  todoDone=(id,done)=>{
    let {data} = this.state;
    data.forEach(item=>{
      if (item.id === id) {
        item.done = done;
      }
    });
    this.setState({
      data
    });
  }
  allDone=(done)=>{
    let {data} = this.state;
    data.forEach(item=>{
        item.done = done;
    });
    this.setState({
      data
    });
  }
  removeDone=()=>{
    let {data} = this.state;
    data = data.filter(item=>!item.done);
    this.setState({
      data
    });
  }
  changeTitle=(id,info)=>{
    let {data} = this.state;
    data.forEach(item=>{
      if (item.id === id) {
        item.info = info;
      }
    });
    this.setState({
      data
    });
  }
  render(){
    let {data} = this.state;
    console.log(data);
    return <section className="wrap">
      <LeavelWord addWord={this.addWord}/>
      <Todo data={data} removeWord={this.removeWord} todoDone={this.todoDone} changeTitle={this.changeTitle}/>
      <Footer data={data} allDone={this.allDone} removeDone={this.removeDone}/>
    </section>
  }
}

export default App;
