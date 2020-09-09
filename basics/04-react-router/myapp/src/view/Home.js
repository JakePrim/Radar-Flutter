import React, { Fragment } from 'react';
import HomeImg from '../img/img4.png';
import HomeImg1 from '../img/img_1.png';
import HomeImg2 from '../img/img_2.png';
function Home() {
    return <Fragment>
        <img src={HomeImg} className="banner" />
        <div className="wrap">
            <img src={HomeImg1} />
            <img src={HomeImg2} />
        </div>
    </Fragment>
}

export default Home;