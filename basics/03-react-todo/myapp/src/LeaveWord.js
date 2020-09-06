import React, { Component,Fragment } from 'react';

export default class LeaveWord extends Component {
    state = {
        name: "",
        info: ""
    }
    render() {
        let { name, info } = this.state;
        let { addWord } = this.props;
        return <Fragment>
            <h2 className="title">留言板</h2>
            <div className="addMessage">
                <input type="text" value={name} placeholder="请输入昵称" onChange={
                    ({ target }) => {
                        this.setState({
                            name: target.value
                        })
                    }
                } />
                <textarea placeholder="请输入留言" value={info} onChange={
                    ({ target }) => {
                        this.setState({
                            info: target.value
                        })
                    }
                }></textarea>
                <button onClick={
                    () => {
                        if (name && info) {
                            addWord({
                                name,
                                info
                            });
                            this.setState({
                                name:'',
                                info:''
                            })
                        }
                    }
                }>提交留言</button>
            </div>
        </Fragment>
    }
}