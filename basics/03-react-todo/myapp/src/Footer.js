import React,{Component} from 'react'

export default class Footer extends Component{
    render(){
        let {allDone,data,removeDone} = this.props;
        let allCheck = data.length && data.every(item=>item.done);
        let doneLength = data.filter(item=>item.done).length;
        return <div className="footer clearfix">
            <div className="left">
                <input type="checkbox" name="all" id="all" onChange={({target})=>{
                    allDone(target.checked);
                }} checked={allCheck}/> 
                <label htmlFor="all">选中全部</label>
                <span onClick={()=>{
                    console.log('removeDone')
                    removeDone();
                }}>删除选中项</span>
            </div>
            <div className="right">
                <span>当前选中{doneLength}项，共{data.length}条留言</span>
            </div>
        </div>
    }
}