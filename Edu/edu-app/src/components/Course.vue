<template>
  <div>
    <Header></Header>
    <div style="background: #eee;">
      <!-- 面包屑导航 -->
      <div class="nav-wrap">
        <p class="nav-p-pc" style="margin-top:-25px;text-align:left;">
          <a href="#">课程列表</a>
          <span class="sharp-content">&gt;</span>
          <span class="nav-sec">文案高手的18项修炼</span>
        </p>
      </div>

      <!-- 课程详情 -->
      <div class="container">
        <div style="height: 100%;">
          <div class="weui-tab content-wrapper">
            <div
              id="vux_view_box_body"
              class="weui-tab__panel vux-fix-safari-overflow-scrolling"
            >
              <div style="position: relative;">
                <div class="intro">
                  <div class="intro-content">
                    <img
                      class="course-img"
                      src="https://edu-lagou.oss-cn-beijing.aliyuncs.com/images/2020/07/10/1594348262748358.jpg"
                      alt="课程图片"
                    />
                    <div class="conent-wrap">
                      <div class="name" style="text-align:left;">
                        文案高手的18项修炼
                      </div>
                      <div class="des text-omit" style="text-align:left;">
                        手把手教你写出实用的高转化文案
                      </div>
                      <div class="title">
                        <div class="teacher-name text-omit">
                          讲师：兔妈
                          <span class="line"></span>
                          有赞高级讲师
                        </div>
                      </div>
                      <div class="lesson-info">
                        <div class="boook-icon backgroun-img-set"></div>
                        <div class="time">100讲 / 50课时</div>
                        <div class="person-icon backgroun-img-set"></div>
                        <div class="person">1314人已购买</div>
                      </div>
                    </div>

                    <div class="content-right">
                      <div class="button-wrap" @click="watchCourse(1)">
                        免费试看
                        <div class="small-arrows"></div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <div class="public-class-container is-pc">
                <el-tabs v-model="activeName">
                  <el-tab-pane label="课程信息" name="intro">
                    <div class="content-p pc-background">
                      <p>
                        背景介绍
                        自媒体时代，无论你是做新媒体编辑、运营，还是市场营销、电商，微信推文、推广海报、产品详情页、朋友圈话术……
                      </p>
                    </div>
                  </el-tab-pane>
                  <el-tab-pane label="目录" name="directory">
                    <div
                      class="class-menu-contaniner list-page-container more-sections more-sections-padding"
                    >
                      <div>
                        <div class="section-name single-line">
                          开篇词 | 从小白到文案高手，手把手教你写出爆款文案
                        </div>
                        <div class="class-menu-block">
                          <div
                            class="class-level-one over-ellipsis"
                            @click="watchCourse(1)"
                          >
                            <div class="text-wrap">
                              <div class="content">手把手教你写出爆款文案</div>
                              <div
                                class="item-status-wrap item-status-wrap-list"
                              >
                                <div class="item-status test-watch">试看</div>
                              </div>
                            </div>
                          </div>
                          <div
                            class="class-level-one over-ellipsis isfree "
                            @click="watchCourse(2)"
                          >
                            <div class="text-wrap">
                              <div class="content">从小白到文案高手</div>
                              <div
                                class="item-status-wrap item-status-wrap-list"
                              >
                                <div class="item-status test-watch">试看</div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>

                      <div>
                        <div class="section-name single-line">章节一</div>
                        <div class="class-menu-block">
                          <div
                            class="class-level-one over-ellipsis"
                            @click="watchCourse(3)"
                          >
                            <div class="text-wrap">
                              <div class="content">第1节：简介</div>
                              <div
                                class="item-status-wrap item-status-wrap-list"
                              >
                                <div class="item-status lock"></div>
                              </div>
                            </div>
                          </div>
                          <div
                            class="class-level-one over-ellipsis"
                            @click="watchCourse(4)"
                          >
                            <div class="text-wrap">
                              <div class="content">第2节：使用</div>
                              <div
                                class="item-status-wrap item-status-wrap-list"
                              >
                                <div class="item-status lock"></div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </el-tab-pane>
                </el-tabs>

                <div class="tab-fix-wrap"></div>
                <div></div>
              </div>
            </div>
          </div>
        </div>

        <!-- 底部购买 -->
        <div
          class="public-class-footer"
          slot="bottom"
          style="border:1px solid #eee; height:60px; text-align:left;"
        >
          <span class="product-descript" style="font-size:.347rem"
            >成就自己</span
          >
          <span class="current-price" style="font-size:28px">
            <span class="current-price-unite" style="font-size:.347rem">
              ￥</span
            >100
          </span>
          <span class="current-price price">
            <span class="current-price-unite">￥</span>
            263
          </span>
          <button
            @click="buy(7)"
            type="button"
            class="weui-btn purchase-button weui-btn_mini weui-btn_primary"
            style="width:155px;height:45px;font-size:17px;"
          >
            立即购买
            <!-- ::after -->
          </button>
        </div>

        <!-- 留言板 -->
        <div class="message">
          <div class="message-topic">
            <div class="message-topic-title normal-font">精选留言</div>
          </div>
          <div>
            <div class="message-edit">
              <div
                contenteditable="true"
                placeholder="分享学习心得、思考感悟或者给自己一个小鼓励吧！"
                class="edit-div pcStyle"
              ></div>
              <div class="message-edit-count">
                <span class="message-edit-count-cur">0</span>
                <span class="message-edit-count-max">/2000</span>
              </div>
            </div>

            <div class="message-edit-footer flex">
              <div class="message-edit-emoji">
                <img
                  alt=""
                  class="message-edit-emoji-icon"
                  src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAFAAAABQBAMAAAB8P++eAAAAJ1BMVEVHcEycnJyYmJimpqaXl5eYmJiXl5eYmJiXl5eYmJibm5uXl5eXl5facJpuAAAADHRSTlMAGG8J4pPzTL/PMqpyqtX6AAAC1ElEQVRIx41XO2/TUBS+rolC2yUCWqmIIQjULB5CaFWEGHgIEJKHViCgUhYQDyF5qMJjoQMSYoEMgICpQxESYujABkMWgmuS5vtRHDu+vsev63uHSOf6i8/7O8dCpI/Vu/vJe3TjTashdMd+voP4jO+0y3H122Dn4VYZruMidfxTOtzn1tejZ473NsuR9RB39akU126FyALt1gAIfvGblx4wyTv/gnAb6asFQj7O4lZJ0Ub2coEub2YU94G3eXt+AKO08iVgWOShA3zg8pwLf7cImH1wDHhdHNxzwCVm4Q4mJZm1Bxg3+N/Wy/Ja48q6mJRXygD7SfJKLYzVyUQuwtfUnu3iXvLyA10170nDSHNTB5yRulcw0vaH6ONKnKYDPXBvml4ydl0PrE2dnUGgb01heZETh5KAznx5wrxiUhcX6XdbhimsSVV9XFrEvwj+cyoeDhvvlQRyaT5UansyQ074aMhKNpHqCNr04yfhojMSRZJLL6slvnjho0AUSV2K4GxkaAVwG+/FCfyNpYjIxqJIuoA/EbjKmUitkySwPDzkyJDsbIqqgFOa9ykMW6IqhRTDERm9KyrPHLnlolENtCgrnhkwEEC7GmibA2Gu2tgZ4/CogHvInYAFXKXQzQN9lkJVFP08cMSKQpXZIA+csDJTheuoGpOVNmSFq1pB/YU9Vq2gmms2O2qcxKqwuVS7djLES+zVZO2qCCDmIkagkr0iAlCUkiXK74kpEaUokiLq9VneLTcmWklSivbWUhNySY2NKe0xInXYhKRxOUwTKaPmeWr52HF7GYntMTUzH2hC4nJ0Z5+GmqMx2fPx0QkLofXtfC8skGZ2fPCB9EwVxPXcQOIj7uyyxN1v50Zcamham1Pcx0bB0EyP4SO/rz14d7JwDBsPdu2qsMrVGS8fxuuM+YJkvHKZL3HGa6H5omm+uhovw+brtfnCbv4JYP5RoftM+Q87klAb2rvNhQAAAABJRU5ErkJggg=="
                />
                <span>表情</span>
              </div>
              <button class="message-edit-btn disableBg">发表留言</button>

              <!-- 表情大全，待完善 -->
              <div
                id="myPanel"
                class="message-edit-emojilist"
                style="display: none;"
              >
                <em class="message-edit-emojilist-tip">◆</em>
                <span class="message-edit-emojilist-tip">◆</span>
                <img
                  src="https://www.lgstatic.com/mds-pipline-fed/instantMessaging/common/img/blank.56398e76.gif"
                  data-src="https://www.lgstatic.com/mds-pipline-fed/instantMessaging/common/img/blank.56398e76.gif"
                  data-code="1f600"
                  class="emoji-icon emoji_1f600"
                  alt=""
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <Footer></Footer>
  </div>
</template>

<script>
import Header from "./Header/Header"; //顶部登录条
import Footer from "./Footer/index"; //顶部登录条
export default {
  name: "Course",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      activeName: "intro",
    };
  },
  methods: {
    watchCourse(lessonid) {
      alert("观看第【" + lessonid + "】节课程视频！");
      this.$router.push({
        name: "videoDetail",
        params: { lessonid: lessonid },
      });
    },
    buy(courseid) {
      alert("购买第【" + courseid + "】门课程成功，加油！");
    },
  },
};
</script>

<style scoped>
.message-edit-emojilist {
  position: absolute;
  top: 30px;
  left: 0;
  width: 415px;
  height: 180px;
  padding: 18px 10px;
  border: 1px solid #e2ebe8;
  box-shadow: 0 2px 3px 0 rgba(0, 0, 0, 0.08);
  background: #fff;
  border-radius: 5px;
  z-index: 9;
  margin-bottom: 1.067rem;
}
.message-edit-emoji {
  font-size: 14px;
  color: #666;
}
.message-edit-footer {
  display: -webkit-box;
  display: -webkit-flex;
  display: flex;
  -webkit-flex-wrap: wrap;
  flex-wrap: wrap;
  -webkit-box-pack: justify;
  -webkit-justify-content: space-between;
  justify-content: space-between;
  margin-top: 20px;
  position: relative;
}
.message-edit-count-cur {
  margin-right: -5px;
}
.message-edit-count {
  position: absolute;
  bottom: 5px;
  right: 10px;
  font-size: 14px;
  text-align: right;
  -webkit-box-flex: 1;
  -webkit-flex: 1;
  flex: 1;
  color: #ccc;
}
.pcStyle {
  box-sizing: border-box;
  position: relative;
  line-height: 30px;
  margin-top: 6px;
  height: 100%;
}
.edit-div {
  width: 100%;
  height: 3.067rem;
  line-height: 0.667rem;
  margin-top: 0.267rem;
  word-break: break-all;
  outline: none;
  color: #666;
  -webkit-user-select: text;
  user-select: text;
  text-align: left;
  font-size: 0.427rem;
  caret-color: #00b38a;
  border-bottom: 4px solid #fff;
  overflow: hidden;
  overflow-y: auto;
}
.message-edit {
  font-size: 16px;
  position: relative;
  padding: 0 14px 34px;
  height: 190px;
  border: 1px solid #ebebeb;
  border-radius: 4px;
  box-sizing: border-box;
  margin-top: 10px;
}

.message-topic {
  display: -webkit-box;
  display: -webkit-flex;
  display: flex;
}
.message-topic .normal-font {
  font-weight: 400;
}
* {
  margin: 0;
  padding: 0;
}
div {
  display: block;
}
.nav-wrap {
  padding-top: 40px;
  height: 50px;
  background: #fff;
}
.nav-p-pc {
  font-size: 16px;
  margin: 17px auto;
  vertical-align: top;
  width: 750px;
}
.nav-p-pc a:active,
.nav-p-pc a:link,
.nav-p-pc a:visited {
  color: #666;
}
.nav-p-pc .sharp-content {
  color: #666;
  margin-left: 5px;
}
.nav-p-pc .nav-sec {
  margin-left: 5px;
  color: #999;
}

.container {
  width: 750px;
  margin: 0 auto;
}
.intro-content {
  overflow: hidden;
  padding: 30px;
  position: relative;
  min-height: 133px;
}
.intro {
  background: #5b5d6e;
  font-family: PingFangSC-Regular;
}
.weui-tab__panel {
  box-sizing: border-box;
  height: 100%;
  padding-bottom: 1.333rem;
  overflow: auto;
  -webkit-overflow-scrolling: touch;
}

.content-wrapper {
  box-sizing: border-box;
  min-height: 100%;
}
.weui-tab {
  position: relative;
  height: 100%;
}
.conent-wrap {
  float: left;
  max-width: 450px;
  position: relative;
}
.name {
  font-family: PingFangSC-Medium;
  font-size: 20px;
  color: #fff;
  font-weight: 700;
  margin-bottom: 53px;
}
.text-omit {
  word-wrap: normal;
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
}
.des {
  font-size: 14px;
  color: #fff;
  position: absolute;
  width: 450px;
  top: 36px;
  left: 0;
}
.title {
  font-size: 12px;
  line-height: 12px;
  color: #fff;
  overflow: hidden;
  margin-bottom: 13px;
  font-weight: 400;
  font-family: PingFangSC-Regular;
}
.teacher-name {
  float: left;
}
.line {
  width: 1px;
  height: 8px;
  opacity: 0.5;
  display: inline-block;
  background: #fff;
  margin-left: 10px;
  margin-right: 10px;
}
.lesson-info {
  overflow: hidden;
  font-family: PingFangSC-Regular;
  font-size: 12px;
  line-height: 20px;
  color: #fff;
  font-weight: 400;
}

.boook-icon[data-v-dd02a830] {
  width: 18px;
  height: 18px;
  background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACQAAAAkCAYAAADhAJiYAAAAAXNSR…gip3QVDYmQv3MNonYRxetQvIHgUR3EkqN76xV++qd/7MATlNn5zIhsuCEAAAAASUVORK5CYII=);
  float: left;
}
.backgroun-img-set {
  background-repeat: no-repeat;
  background-size: 100% 100%;
}
.time {
  margin-left: 8px;
  float: left;
}
.person-icon {
  width: 18px;
  height: 18px;
  background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACQAAAAkCAYAAADhAJiYAAAAAXNSR…JiFjKvv30ew3xoAZneN6xkDKPekFmAr5bL5fWApqD/vx34B1s5dQzzKE5bAAAAAElFTkSuQmCC);
  margin-left: 30px;
  float: left;
}

.backgroun-img-set {
  background-repeat: no-repeat;
  background-size: 100% 100%;
}
.person {
  margin-left: 4px;
  float: left;
}
.content-right {
  position: absolute;
  top: 118px;
  right: 40px;
}
.button-wrap {
  font-family: PingFangSC-Medium;
  font-size: 14px;
  color: #fff;
  height: 34px;
  padding: 0 22px;
  border: 1px solid hsla(0, 0%, 88%, 0.5);
  border-radius: 2px;
  line-height: 34px;
  font-weight: 700;
  cursor: pointer;
  position: relative;
  float: right;
}

.small-arrows {
  width: 10px;
  height: 10px;
  background-repeat: no-repeat;
  background-size: 100% 100%;
  background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR…Myh+5soGuoF47UNoy6yQboOuombFBkAQ2lXtaje+zT1EIAsuukPgcISt4AAAAASUVORK5CYII=);
  top: 12px;
  right: 12px;
  position: absolute;
}

.pc-background,
.public-class-container {
  background: #fff;
  min-height: calc(100vh - 4.4rem);
  content: "viewport-units-buggyfill; min-height: calc(100vh - 4.4rem)";
}

.course-img {
  width: 102px;
  max-height: 133px;
  overflow: hidden;
  box-shadow: 0 6px 10px 0 rgba(0, 0, 0, 0.1);
  border-radius: 2px;
  float: left;
  margin-right: 20px;
}

img {
  border: 0;
  vertical-align: top;
  display: inline-block;
}
.content-p {
  font-size: 0.373rem;
  color: #666;
  letter-spacing: 0;
  text-align: justify;
  line-height: 0.56rem;
}
.pc-background,
.public-class-container {
  background: #fff;
  min-height: calc(100vh - 4.4rem);
  content: "viewport-units-buggyfill; min-height: calc(100vh - 4.4rem)";
}

.pc-background {
  padding: 20px;
}

.public-class-footer {
  height: 1.733rem;
  width: 100%;
  line-height: 55px;
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  z-index: 2;
  padding: 0 0.4rem;
  border-top: 1px solid #eee;
  background: #fff;
  box-sizing: border-box;
  max-width: 750px;
  margin: 0 auto;
}

.public-class-footer {
  position: fixed;
}

.public-class-footer .product-descript {
  font-size: 0.347rem;
  color: #333;
}
.public-class-footer .current-price {
  font-size: 0.533rem;
  color: #ff7452;
}
.public-class-footer .current-price.price {
  color: #999;
  text-decoration: line-through;
  padding-left: 0.027rem;
  font-size: 0.16rem;
}
.public-class-footer .current-price {
  font-size: 0.533rem;
  color: #ff7452;
}
.public-class-footer .purchase-button {
  width: 4.8rem;
  height: 1.2rem;
  text-align: center;
  display: inline-block;
  font-size: 0.427rem;
  color: #fff;
  border: none;
  position: absolute;
  right: 0.267rem;
  top: 0.267rem;
  cursor: pointer;
}
.public-class-footer .current-price-unite {
  font-size: 0.347rem;
  margin-right: -0.133rem;
}
button {
  -webkit-appearance: button;
  text-rendering: auto;
  color: buttontext;
  letter-spacing: normal;
  word-spacing: normal;
  text-transform: none;
  text-indent: 0px;
  text-shadow: none;
  display: inline-block;
  text-align: center;
  align-items: flex-start;
  cursor: default;
  background-color: buttonface;
  box-sizing: border-box;
  margin: 0em;
  font: 400 13.3333px Arial;
  padding: 1px 6px;
  border-width: 2px;
  border-style: outset;
  border-color: buttonface;
  border-image: initial;
}
button,
input,
optgroup,
select,
textarea {
  margin: 0;
  padding: 0;
  border: 1px solid #ededed;
  border-radius: 0;
  font-family: Hiragino Sans GB, Microsoft Yahei, SimSun, Arial, Helvetica Neue,
    Helvetica;
}
.weui-btn {
  position: relative;
  display: block;
  margin-left: auto;
  margin-right: auto;
  padding-left: 0.373rem;
  padding-right: 0.373rem;
  box-sizing: border-box;
  font-size: 0.48rem;
  text-align: center;
  text-decoration: none;
  color: #fff;
  line-height: 2.33333333;
  border-radius: 4px;
  -webkit-tap-highlight-color: rgba(0, 0, 0, 0);
  overflow: hidden;
}
.weui-btn_primary {
  background-color: #00b38a;
}
.weui-btn_mini {
  display: inline-block;
  padding: 0 1.32em;
  line-height: 2.3;
  font-size: 0.347rem;
}
button.weui-btn,
input.weui-btn {
  width: 100%;
  border-width: 0;
  outline: 0;
  -webkit-appearance: none;
}
.public-class-footer .purchase-button {
  width: 4.8rem;
  height: 1.2rem;
  text-align: center;
  display: inline-block;
  font-size: 0.427rem;
  color: #fff;
  border: none;
  position: absolute;
  right: 0.267rem;
  top: 0.267rem;
  cursor: pointer;
}

.class-menu-contaniner.more-sections-padding {
  padding-bottom: 3.04rem;
}
.class-menu-contaniner.more-sections {
  padding: 0 20px 50px 30px;
}

.class-menu-contaniner.list-page-container {
  padding: 20px 15px;
  background: #fff;
}
.class-menu-contaniner {
  background: #f6f6f6;
}
div {
  box-sizing: border-box;
}

.section-name {
  font-size: 18px;
  color: #333;
  font-weight: 700;
  padding: 30px 0 20px;
  max-width: 540px;
  font-family: PingFangSC-Regular;
}

.single-line {
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  margin: 0px;
  text-align: left;
  line-height: 18px;
}

.class-menu-contaniner.more-sections .class-menu-block {
  background: #fbfbfb;
  font-size: 0.187rem;
  color: #333;
}
.class-menu-block {
  font-family: PingFangSC-Regular;
  font-size: 16px;
  font-weight: 400;
}

.class-menu-contaniner .more-sections .class-level-one {
  padding-left: 20px;
  font-size: 14px;
  min-height: auto;
}
.class-menu-contaniner .list-page-container .class-level-one {
  border-right: none;
  font-size: 16px;
  color: #333;
  padding-left: 15px;
  min-height: 62px;
  cursor: pointer;
}
.class-menu-contaniner .class-level-one {
  /* border:1px solid red; */
  font-size: 14px;
  color: #666;
  position: relative;
  width: 100%;
  /* min-height: 90px; */
  line-height: 50px;
  height: 50px;
  padding-left: 30px;
  border-right: 1px solid #ededed;
  cursor: pointer;
}
.class-level-one:hover {
  background: rgba(207, 198, 196, 0.534);
}
.class-menu-contaniner .over-ellipsis {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.class-menu-contaniner .class-level-one:first-child .text-wrap {
  border-top: none;
}
.class-menu-contaniner.list-page-container
  .class-level-one
  .text-wrap
  .content {
  width: 600px;
  word-break: break-all;
  white-space: normal;
  text-align: left;
  float: left;
}
.class-menu-contaniner.more-sections .class-level-one .item-status-wrap-list {
  top: 15px;
}
.class-menu-contaniner .class-level-one .item-status.lock {
  width: 20px;
  height: 20px;
  background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACgAAAAoCAYAAACM/rhtAAAAAXNSR0IArs4c6QAAAolJREFUWAntlztLA0EQx01ihIAW2om2VilCHoWN8dB0FpYi2PoNxMbaRvwGaieopaBWKlErIQ9SWNkqdlpoZSDxP5edY+9uk3snAbOwmd2d1y+zucvu2NioBatALJh7x7tWq6222+0N9GIsFpujVYzfMX5Ev8jlcnd+8wQCrNfrC61W6wgwy70AAPkQj8e3s9nsay87lc43YLVaXULAS8BNqwJb1wD5hbX1fD7/ZNX1msd7KbvpqHLQGXBI3kY/wtpiKpWaok5jWiMdxRFf5FL40pKrNu7KymIktpUr9wH1FipzbzF7xvwZlT6HPEWfJUjyxVhDd9U8bzE9EEhyS9FFdUoKOFNyQK5g4RaAej78HktuHxzPW4wkG1L2Yyc4shU2x+xnicHLSukHsMiRkOiEx05StsXYiOHk5xkQ26q/5ygwHoYXpwSsl23lGKzvJj0D4ttPcrB0Ov3DYycp28oxnPw8AzoFDFs/9IDK10yj0ZhvNpsHqIaGPht2VbrEo/dpOZlM7mYymTe2sQEKuAYMZtioz/ITkGDsQNq2WFRuUHBUixnBoNdF9Ven6Rp8JBKJNZxAbngepaxUKmuIfyVyaJzLVkEojN9cv+AIplAoXDOUzKAClOwGPxwBBt2D0CuIo9U++jfJoHDkHzogYu6I/9qdoQQE3ASBsQwKGUUFgzKZ/EeApnL4mIReQZyWf4mDpQ8mk0vogIh+CDg6aR+aMvmcqA4LPkN13HCD28OIeigtigqGAsZBRoBcCb9SVUG6G+hNHCJ5Gqm05DIYVA9JGSSbguYKjpGCdQle5nVbBelWBeUnGwxA0qWJGPRmA6TbFN2qoD1DN0rdMY/0k3KdyTe6SLP9m+B/vKTQX63cZOIAAAAASUVORK5CYII=)
    no-repeat;
  background-size: 100% 100%;
  margin-right: 14px;
  float: right;
  line-height: 20px;
  position: relative;
  top: 13px;
  right: 10px;
}
.class-menu-contaniner .class-level-one .item-status.test-watch {
  width: 46px;
  height: 20px;
  border: 1px solid rgba(0, 179, 138, 0.5);
  border-radius: 2px;
  font-size: 12px;
  text-align: center;
  display: table-cell;
  vertical-align: middle;
  color: #00b38a;
  line-height: 20px;
  position: relative;
  top: 13px;
  right: -25px;
}

.message {
  padding: 20px 20px 1.733rem;
  background: #fff;
  font-family: PingFangSC-Regular, PingFang SC;
  font-size: 16px;
}
.message-edit-emoji-icon {
  width: 20px;
  height: 20px;
}
img {
  border: 0;
  vertical-align: top;
  display: inline-block;
}

.disableBg {
  background: #ccc;
}

.message-edit-btn {
  background: #00b38a;
  color: #fff;
  font-size: 14px;
  width: 107px;
  height: 40px;
  line-height: 40px;
  border: none;
  border-radius: 4px;
}

.message-edit-emojilist em {
  margin-top: -29px;
  color: #e2ebe8;
  font-style: normal;
}
.message-edit-emojilist-tip {
  display: block;
  width: 30px;
  height: 16px;
  font-size: 12px;
  overflow: hidden;
  position: relative;
  margin-left: 15px;
}

em,
i {
  font-style: normal;
}

.emoji-icon {
  margin: 8px;
}
.emoji_1f600 {
  background: url(https://www.lgstatic.com/mds-pipline-fed/instantMessaging/common/img/emojiSprite.8c9eba9b.png) -108px -36px
    no-repeat;
  background-size: 216px auto;
}
[class*=" emoji_"],
[class^="emoji_"] {
  height: 18px;
  width: 18px;
  display: inline-block;
}
</style>
