<template>
  <div>
    <Header></Header>
    <div
      style="width: 850PX;display: inline-block; margin:0px auto;
"
    >
      <el-tabs v-model="activeName">
        <el-tab-pane label="选课" name="allLesson">
          <ul class="course-ul-pc">
            <!-- 课程信息展示开始 -->
            <li class="course-li">
              <!-- 课程封面图 -->
              <img
                src="https://edu-lagou.oss-cn-beijing.aliyuncs.com/images/2020/07/10/15943482627237468.jpg"
                class="teacher-portrait hover-pointer"
              />
              <!-- 课程文字信息 -->
              <div class="content-main">
                <!-- 课程标题 -->
                <div class="content-title hover-pointer">
                  <div
                    class="p-title"
                    style="text-align:left;"
                    @click="gotoDetail"
                  >
                    <span>
                      修仙仕途
                    </span>
                  </div>
                  <!-- 作者和职称 -->
                  <p class="p-title-buy text-overflow">
                    <span class="p-author-span">
                      苍井空
                    </span>
                    <span class="p-author-line" />
                    <span class="p-author-span">
                      高级培训师
                    </span>
                  </p>
                  <p></p>
                  <!-- 课程简单描述 -->
                  <p class="p-describe" style="text-align:left;">
                    东京热不热
                  </p>
                </div>
                <!-- 课程前两个章节信息 -->
                <ul class="content-course" style="text-align:left;">
                  <!-- 章节1 -->
                  <li
                    class="content-course-lesson text-overflow"
                    style="width:300px" 
                    
                  >
                    <!-- 免费试看图标 -->
                    <img
                      src="@/assets/course-list/free-course.png"
                      class="free-label hover-pointer"
                    />
                    <span class="theme-span hover-pointer">
                      筑基
                      </span>
                  </li>
                  <!-- 章节2 -->
                  <li
                    class="content-course-lesson text-overflow"
                    style="width:300px" 
                    
                  >
                    <!-- 免费试看图标 -->
                    <img
                      src="@/assets/course-list/free-course.png"
                      class="free-label hover-pointer"
                    />
                    <span class="theme-span hover-pointer">
                      结丹
                      </span>
                  </li>
                </ul>
                <!-- 价格信息 -->
                <div class="content-price" style="text-align:left;">
                  <p class="content-price-p">
                    <span class="content-price-orange-sm">￥</span>
                    <span class="content-price-orange">998</span>
                    <span class="current-price">
                      <span class="current-price-unite">￥</span>
                      99
                    </span>
                    <span class="activity-name">成就自己</span>
                    <span class="content-price-buy">66人购买</span>
                  </p>
                  <div class="btn btn-green btn-offset">立即购买</div>
                </div>
              </div>
            </li>
            <!-- 课程信息结束 -->
            
          </ul>
        </el-tab-pane>
        <el-tab-pane label="已购" name="hasPay">
          <img
            src="@/assets/course-list/no-login@2x.png"
            class="no-data-icon"
          />
          <div class="no-data-title">您还没有登录</div>
          <div class="no-data-title">登录后即可查看已购课程</div>
          <div class="btn btn-yellow btn-center">立即登录</div>
        </el-tab-pane>
      </el-tabs>
    </div>
    <Footer></Footer>
  </div>
</template>

<script>
import Header from "./Header/Header"; //顶部登录条
import Footer from "./Footer/index"; //顶部登录条
export default {
  name: "Index",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      activeName: "allLesson",
    };
  },
  created() {
  },
  methods: {
    changeCourseTab(tabName) {
      this.classSelect = tabName;
      sessionStorage && sessionStorage.setItem("courseTab", tabName);
    },
    gotoDetail() {
      this.$router.push({ name: "Course", params: { courseid: 1 } });
    }
  },
};
</script>

<style lang="less" scoped>
@import "../assets/less/common.less";

p {
  margin-top: 0;
}
li {
  list-style: none;
}
flex {
  display: flex;
}
.btn(@a, @b) {
  background-color: @a;
  color: @b;
  border: 1px solid @a;
}
.course-ads {
  margin-bottom: 20px;
  cursor: pointer;
  width: 100%;
  max-height: 280px;
  overflow: hidden;
  img {
    width: 100%;
    border-radius: 2px;
  }
}
.tab-container {
  width: 100%;
  height: 60px;
  background: #ffffff;
  border-bottom: 1px solid #ededed;
}
.vux-tab.course-tab {
  box-sizing: border-box;
  height: 60px;
  width: 200px;
  overflow: hidden;
  font-size: 20px;
  .vux-tab-container {
    width: 260px;
  }
  .vux-tab-item {
    cursor: pointer;
    background: none;
    line-height: 60px;
    font-size: 20px;
  }
  .vux-tab-ink-bar {
    bottom: -1px;
    .vux-tab-bar-inner {
      height: 2px;
    }
  }
}
.tab-container {
  width: 100%;
  height: 60px;
  background: #ffffff;
  border-bottom: 1px solid #ededed;
}
.course-ul {
  padding: 0 20px;
}
.hover-pointer {
  cursor: pointer;
}
.course-ul-pc .course-li {
  padding: 30px 0 40px 0;
  border-bottom: 1px solid #ededed;
  position: relative;
  &:last-child {
    border-bottom: 0;
  }
}
.current-price {
  font-size: 12px;
  color: #999;
  font-weight: 600;
  text-decoration: line-through;
}
.current-price-unite {
  font-size: 12px;
  margin-right: -4px;
}
.p-author-line {
  content: "";
  display: inline-block;
  width: 1px;
  height: 10px;
  position: absolute;
  background: #999;
  margin-left: 5px;
  top: 3px;
}
.teacher-portrait {
  width: 121px;
  height: 159px;
  border-radius: 4px;
  float: left;
}
.content-main {
  line-height: 1.4;
  margin-left: 141px;
  position: relative;
  height: 150px;
}
.content-main .p-title {
  font-size: 20px;
  color: #333;
  font-family: PingFangSC-Medium, PingFang SC;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  width: 350px;
}
.course-ul-pc .content-main .p-title-buy {
  font-size: 12px;
  color: #999;
  display: inline-block;
  margin-top: 6px;
  position: relative;
  float: right;
  top: -28px;
  font-weight: 400;
}
p {
  margin-top: 0;
}
.course-ul-pc .p-author-line {
  content: "";
  display: inline-block;
  width: 1px;
  height: 10px;
  position: absolute;
  background: #999;
  margin-left: 5px;
  top: 3px;
}
.text-overflow {
  word-wrap: normal;
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
}
.course-ul-pc .content-main .p-describe {
  font-size: 14px;
  margin-top: 5px;
  color: #333;
  word-wrap: normal;
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
}
.course-ul-pc .content-main .p-author-span:not(:first-child) {
  margin-left: 18px;
}
.course-ul-pc .content-main .content-course {
  font-size: 14px;
  padding-top: 25px;
  color: #666;
}

ol,
ul {
  margin: 0;
  padding: 0;
  list-style: none;
}
* {
  -webkit-tap-highlight-color: transparent;
}
.course-ul-pc .content-main .content-course-lesson {
  display: inline-block;
  width: 304px;
  margin: 2px 40px 6px 0;
}
.course-ul-pc .content-main .content-course .free-label {
  width: 50px;
  height: 16px;
  margin: 1px 5px 0 0;
}
.course-ul-pc .content-main .content-course-lesson:nth-child(2n) {
  margin-right: 0;
}

.course-ul-pc .content-main .content-course-lesson {
  display: inline-block;
  width: 304px;
  margin: 2px 40px 6px 0;
}
.course-ul-pc .content-main .content-price {
  height: 28px;
  line-height: 28px;
  position: absolute;
  width: 100%;
  bottom: -4px;
}
.course-ul-pc .content-main .content-price-p {
  font-size: 0;
}
.course-ul-pc .content-main .content-price-orange-sm {
  color: #ff7452;
  font-size: 14px;
}

.content-course {
  font-size: 14px;
  padding-top: 25px;
  color: #666;
}
.content-course.free-label {
  width: 50px;
  height: 16px;
  margin: 1px 5px 0 0;
}

.content-course-lesson {
  display: inline-block;
  width: 304px;
  margin: 2px 40px 6px 0;
}
.content-course-lesson:nth-child(2n) {
  margin-right: 0;
}
.disabled-word {
  color: #999;
  cursor: default;
}
.theme-span :hover {
  color: #0abf89;
}

.content-price-p {
  font-size: 0;
}
.content-price-orange {
  color: #ff7452;
  font-size: 20px;
}
.content-price-buy {
  font-size: 14px;
  color: #999;
  margin-left: 20px;
  transform: translate(8px, -2px);
  display: inline-block;
}
.content-price-grey {
  color: #999;
  font-size: 12px;
  margin-left: 5px;
}
.content-price-buyed {
  color: #999;
  font-size: 14px;
}
.content-price {
  height: 28px;
  line-height: 28px;
  position: absolute;
  width: 100%;
  bottom: -4px;
}
.activity-name {
  font-size: 12px;
  line-height: 14px;
  color: rgba(255, 255, 255, 1);
  padding: 2px 4px;
  background: linear-gradient(
    180deg,
    rgba(255, 137, 110, 1) 0%,
    rgba(255, 123, 92, 1) 100%
  );
  border-radius: 2px;
  display: inline-block;
  transform: translate(6px, -3px);
}
.p-title {
  font-size: 20px;
  color: #333;
  font-family: PingFangSC-Medium, PingFang SC;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  width: 350px;
}
.p-title-dec {
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
  overflow: hidden;
}
.p-title-buy {
  // .font(12px);
  font-size: 12px;
  color: #999;
  display: inline-block;
  margin-top: 6px;
  position: relative;
  // .fr;
  top: -28px;
  font-weight: normal;
}

.companion-reading {
  background: rgba(0, 179, 138, 0.06);
  border-radius: 2px;
  font-family: PingFangSC-Regular;
  font-size: 12px;
  color: #00b38a;
  display: inline-block;
  padding: 0 8px;
  height: 22px;
  line-height: 22px;
  margin-left: 10px;
  vertical-align: text-bottom;
  position: relative;

  .triangle {
    position: absolute;
    top: 12px;
    left: -3px;
    width: 0;
    height: 0;
    content: "";
    border: 5px solid;
    border-color: transparent transparent white white;
    transform: rotate(45deg);
    box-shadow: -2px 2px 2px rgba(0, 0, 0, 0.08);
  }
}
.companion-tip {
  display: none;
  position: absolute;
  font-family: HiraginoSansGB-W3;
  font-size: 14px;
  color: #777777;
  background: #ffffff;
  border: 1px solid #e3ebe9;
  box-shadow: 0 2px 3px 0 rgba(0, 0, 0, 0.08);
  border-radius: 3px;
  line-height: 29px;
  top: -7px;
  word-break: keep-all;
  left: 80px;
  padding: 0 10px;
  width: 200px;
  z-index: 2;
}
.companion-reading:hover .companion-tip {
  display: inline-block;
}
.companion-reading:hover .companion-tip {
  display: block;
}
.p-describe {
  // .font(14px);
  font-size: 14px;
  margin-top: 5px;
  color: #333;
  // .text-overflow;
}
.p-author + p {
  margin-top: 0;
}
.p-author {
  margin: 10px 0 5px 0;
  // .font(12px);
  font-size: 12px;
  color: #999;
  position: relative;

  .p-author-line {
    content: "";
    display: inline-block;
    width: 1px;
    height: 10px;
    position: absolute;
    background: #999;
    margin-left: 5px;
    top: 3px;
  }
}
.p-author-span:not(:first-child) {
  margin-left: 18px;
}

.course-ul-container {
  width: 850px;
  display: inline-block;
  float: left;
}
.course-ul {
  padding: 0 20px;
}
.course-ul-pc {
  width: 790px;
  background-color: #fff;
  padding: 0 30px;
  // .fl;
}
.tag-icon {
  width: 57px;
  height: 18px;
  position: absolute;
  top: 30px;
  left: 0;
}

.more-icon {
  width: 10px;
  height: 10px;
  vertical-align: middle;
}
.btn {
  width: 94px;
  height: 34px;
  text-align: center;
  line-height: 34px;
  cursor: pointer;
  border-radius: 3px;
  font-size: 14px;
  box-sizing: content-box;
  &-green {
    .btn(#00b38a, #fff);
    &:hover {
      background: #00a57f;
      border-color: #00a57f;
    }
  }
  &-yellow {
    .btn(#fcd766, #fff);
    &:hover {
      background: #fcd766;
      border-color: #fcd766;
    }
  }
  &-white {
    .btn(#fff, #00b38a);
    border: 1px solid #00b38a;
    &:hover {
      color: #fff;
      border-color: #00b38a;
      background-color: #00b38a;
    }
  }
  &-offset {
    position: absolute;
    right: 0;
    top: -4px;
  }
}

.btn-center {
  margin: 10px auto;
}

.no-data-title {
  font-size: 20px;
  color: #333;
  font-weight: 500;
}
</style>
