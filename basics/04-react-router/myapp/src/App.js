import React, { Fragment } from 'react';
import {Route} from 'react-router-dom';
import Nav from './component/Nav';
import Home from './view/Home';
import About from './view/About';
import Join from './view/Join';
function App(){
    return <Fragment>
        <Nav/>
        <Route path={["/","/home"]} exact component={Home}/>
        <Route path="/about" exact component={About}/>
        <Route path="/join" exact component={Join}/>
    </Fragment>
}
export default App;