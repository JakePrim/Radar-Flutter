import React from 'react';
import Image3 from '../img/img3.png';
function About(){
    return <div className="wrap">
        <h2>开课吧简介</h2>
        <p>开课吧是慧科教育科技集团有限公司旗下泛互联网人职业提升品牌。</p>
        <p>开课吧整合全球知名IT和互联网企业一线师资及实战项目，面向IT、互联网、产业互联网以及使用互联网技术和应用的广大泛互联网从业者，提供Web前端、Java、Python、大数据、人工智能、产品、设计等热门学科的体系化在线课程，并通过人才赋能系统-“驭风系统”重构因材施教和按需学习的多元化教学场景，呈现公开课、微课、小课、大课、厂课等进阶式课程形态，满足用户多层次和个性化学习需求，实现职业提升和可持续成长。</p>
        <img src={Image3} className="about_img"/>
    </div>
}

export default About;