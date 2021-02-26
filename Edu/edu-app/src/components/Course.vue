<template>
  <div>
    <Header></Header>
    <div style="background: #eee;">
      <!-- 面包屑导航 -->
      <div class="nav-wrap">
        <p class="nav-p-pc" style="margin-top:-25px;text-align:left;">
          <a href="/">课程列表</a>
          <span class="sharp-content">&gt;</span>
          <span class="nav-sec">{{course.courseName}}</span>
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
                      :src="course.courseImgUrl"
                      alt="课程图片"
                    />
                    <div class="conent-wrap">
                      <div class="name" style="text-align:left;">
                        {{course.courseName}}
                      </div>
                      <div class="des text-omit" style="text-align:left;">
                        {{course.brief}}
                      </div>
                      <div class="title">
                        <div class="teacher-name text-omit">
                          讲师：{{course.teacher.teacherName}}
                          <span class="line"></span>
                         {{course.teacher.position}}
                        </div>
                      </div>
                      <div class="lesson-info">
                        <div class="boook-icon backgroun-img-set"></div>
                        <div class="time">{{totalLessons}} 讲 / {{course.totalDuration}} 课时</div>
                        <div class="person-icon backgroun-img-set"></div>
                        <div class="person">{{course.sales}}人已购买</div>
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
                    <div v-html="course.courseDescription" class="content-p pc-background">
                    </div>

                    <!-- 留言板 开始-->
                    <div class="message">
                      <div class="message-topic">
                        <div class="message-topic-title normal-font">精选留言</div>
                      </div>
                      <div>
                        <div class="message-edit">
                          <textarea rows="20" style="border:none;resize: none;" 
                            contenteditable="true"
                            placeholder="分享学习心得、思考感悟或者给自己一个小鼓励吧！"
                            class="edit-div pcStyle"
                            v-model="comment"
                          ></textarea>
                        </div>
                        <div class="message-edit-footer flex">
                          <button class="message-edit-btn disableBg" @click="saveComment">发表留言</button>
                        </div>
                      </div>
                    

                      <!-- 留言 开始 -->
                      <div class="message-list" v-for="(comment , index) in commentList" :key="index">
                        <div class="message-list-title">
                          <div class="message-list-title-left">
                            <div class="message-list-title-left-name">{{comment.userName}}</div>
                            <div class="message-list-title-left-tag"></div>
                          </div>
                          <!-- 已赞 data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADgAAAA4BAMAAABaqCYtAAAAJFBMVEVHcEwAuI4AtIsAtIsAtIoAtYwAtIsAtYwAtowAx5kAtIsAs4qd4c1kAAAAC3RSTlMAGMfz3VGnbTYIhXtDq8EAAAETSURBVDjLldWhb8JAFAbwg8CGhGSGzNQtULNkWUioWbJkpmYKAQaBITPLLKZysmLz+xfolUL6/XM72msh9N2X8ImaX17vrq+vVeqU39XTuK/kdEMA01jGDY4ZyYWFIRPxp0S8u+8KeBJ+WDxIGFrMJbQm7qhVYSJgr8JUwNsKtYDtCiHgPavcsDVDstsuyDk7NeYDk6G8pM3bWXNawQUijWq8QyPPpQy+50ET9bF09go5pus3cMV0feFEc2DfieZRBU7Up7fjSkwZZgz3DA8MHxl6DP8YRgxjgokimDHcMdwyfGG4ZPjJMCKoY4KJIpgz3DHcMvQYFuPpk/005t1mffHlOs/ETvxXAA0Ul3oQHsp/xD93wxfHcC4VkwAAAABJRU5ErkJggg== -->
                          <div @click="cancelzan(comment)" v-if="JSON.stringify(comment.favoriteRecords).indexOf( user.content.id ) >= 0" class="message-list-title-right">
                            <img class="message-list-title-right-icon" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADgAAAA4BAMAAABaqCYtAAAAJFBMVEVHcEwAuI4AtIsAtIsAtIoAtYwAtIsAtYwAtowAx5kAtIsAs4qd4c1kAAAAC3RSTlMAGMfz3VGnbTYIhXtDq8EAAAETSURBVDjLldWhb8JAFAbwg8CGhGSGzNQtULNkWUioWbJkpmYKAQaBITPLLKZysmLz+xfolUL6/XM72msh9N2X8ImaX17vrq+vVeqU39XTuK/kdEMA01jGDY4ZyYWFIRPxp0S8u+8KeBJ+WDxIGFrMJbQm7qhVYSJgr8JUwNsKtYDtCiHgPavcsDVDstsuyDk7NeYDk6G8pM3bWXNawQUijWq8QyPPpQy+50ET9bF09go5pus3cMV0feFEc2DfieZRBU7Up7fjSkwZZgz3DA8MHxl6DP8YRgxjgokimDHcMdwyfGG4ZPjJMCKoY4KJIpgz3DHcMvQYFuPpk/005t1mffHlOs/ETvxXAA0Ul3oQHsp/xD93wxfHcC4VkwAAAABJRU5ErkJggg==" alt="">
                            <div class="message-list-title-right-praise">{{comment.likeCount}}</div>
                          </div>
                          <!-- 没点过赞 -->
                          <div @click="zan(comment)" class="message-list-title-right">
                            <img class="message-list-title-right-icon" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADgAAAA4BAMAAABaqCYtAAAAKlBMVEVHcExnZ2dzc3NmZmZqampmZmZmZmZnZ2dnZ2dnZ2dmZmZoaGhmZmZmZmZl+8SAAAAADXRSTlMA/AbsFtilbj5YwSqJPyESoQAAAZxJREFUOMt1lTtLA1EQha8xRhPTBEmhuKCCoNgoIlYLMcRKBG0sxIUgCDaBSDohEO0FEbQyIBZaBazERvAPWCwxPnP+i3tnrlGTmVPswn73NXNm7hrzq9m9kZ2ckTUUABifkOEBrK7liR7BMRFOA/uFc+BUgnV8mFisEW5IsIFi9FzBuwR91KJnAm8S9EIbxSBeBRZHk86MrBQJWjymJUC3nlugSyk+SQyhANfxos+s4krfM0DZvmbw2cuSCHNGi3PAfUygXYiU79ryyw1ibf0xZ9intBsz6SBadx24iiZXz8kPxCiTtYdLPzKTVFkkLQAZO/VikwYW/x/wHohcT/MiPQE8W9frxJrlbpiw4xvA0vbNmWyhj2Nrhmy+B7nEyTsN0rIaJAc0SDWqwX7rhAYfMa/Dui0bDZbwZAwUGNjWUWActnUUyN2hwDTaOkxRaSiwj6pRhjHKgTazSkWlwBK1jgIpBwrkHCgwyZ0oQ86BAjkHCjziG0KE8YBvCA/5KacOm6sgrHFAotouT6J23bkkLbsNDjM9yt7yP+IbQYga5De+eBMAAAAASUVORK5CYII=" alt="">
                            <div class="message-list-title-right-praise">{{comment.likeCount}}</div>
                          </div>
                        </div>
                        <div class="message-list-content">
                          {{comment.comment}}
                        </div>
                        <!--删除留言（必须登录才能删除自己的）-->
                        <!--
                        <div class="message-delete pointer">
                          <img class="message-delete-icon" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADgAAAA4BAMAAABaqCYtAAAAD1BMVEWZmZn4+fqysrPu7/DS09OEJUPlAAAAf0lEQVQ4y+3VwQ2AIAwF0C9hAAUHgDgBToD7L2UiUMDiwQMXpQfS9B3ohQ+WVDtibTRCarRJKBzDGVQTQ59RMjSwoVEQDIF4lQYa2Oi6IR6qJ5Yr1Et9ExvnwIGv8A9vxdWmCzRFdMdIF4Se54wkVBwtYQ7/+hMIe643Fcc1PgEbl0u1B0v+VgAAAABJRU5ErkJggg==" alt="">删除
                        </div>
                        -->
                      </div>
                      <!-- 留言 结束 -->

                    </div>
                    <!-- 留言板 结束-->
                    
                  </el-tab-pane>
                  <el-tab-pane label="目录" name="directory">
                    <div
                      class="class-menu-contaniner list-page-container more-sections more-sections-padding"
                    >
                      <!-- 第一章 开始 -->
                      <div v-for="(section,index) in course.courseSectionList.slice(0,1)" :key="index">
                        <div class="section-name single-line">
                          {{section.sectionName}}
                        </div>
                        <div class="class-menu-block">
                          <!-- 每节课 开始 -->
                          <div
                            class="class-level-one over-ellipsis"
                            @click="watchCourse(1,lesson.id,index,lesson.courseMedia)"
                            v-for="(lesson , index) in section.courseLessonList" :key="index">
                            <div class="text-wrap">
                              <div class="content">{{lesson.theme}}</div>
                              <div class="item-status-wrap item-status-wrap-list">
                                <!-- 第一章，前两节 -->
                                <div v-if="index<2">
                                  <!-- 未登录 => 试看 -->
                                  <div v-if="!isLogin" class="item-status test-watch">试看</div>
                                  <!-- 已登录，未购买 => 试看 -->
                                  <div v-else-if="isLogin && !isBuy" class="item-status test-watch">试看</div>
                                  <!-- 已登录，已购买 => 播放 -->
                                  <div v-else class="item-status test-watch">播放</div>
                                </div>
                                  
                                <!-- 第一章，除了前两节的 -->
                                <div v-if="index>1">
                                  <!-- 未登录 => 锁 -->
                                  <div v-if="!isLogin" class="item-status lock"></div>
                                  <!-- 已登录，未购买 => 锁 -->
                                  <div v-else-if="isLogin && !isBuy" class="item-status lock"></div>
                                  <!-- 已登录，已购买 => 播放 -->
                                  <div v-else class="item-status test-watch">播放</div>
                                </div>

                              </div>
                            </div>
                          </div>
                          <!-- 每节课 结束 -->
                        </div>
                      </div>
                      <!-- 第一章 结束 -->

                      <!-- 其余章 开始 -->
                      <div v-for="(section,index) in course.courseSectionList.slice(1,course.courseSectionList.length)" :key="index">
                        <div class="section-name single-line">
                          {{section.sectionName}}
                        </div>
                        <div class="class-menu-block">
                          <!-- 每节课 开始 -->
                          <div
                            class="class-level-one over-ellipsis"
                            @click="watchCourse(2,lesson.id,index,lesson.courseMedia)"
                            v-for="(lesson , index) in section.courseLessonList" :key="index">
                            <div class="text-wrap">
                              <div class="content">{{lesson.theme}}</div>
                              <div class="item-status-wrap item-status-wrap-list">
                                  <!-- 未登录 => 锁 -->
                                  <div v-if="!isLogin" class="item-status lock"></div>
                                  <!-- 已登录，未购买 => 锁 -->
                                  <div v-else-if="isLogin && !isBuy" class="item-status lock"></div>
                                  <!-- 已登录，已购买 => 播放 -->
                                  <div v-else class="item-status test-watch">播放</div>
                              </div>
                            </div>
                          </div>
                          <!-- 每节课 结束 -->
                        </div>
                      </div>
                      <!-- 其余章 结束 -->
                    </div>
                  </el-tab-pane>
                </el-tabs>

                <div class="tab-fix-wrap"></div>
                <div></div>
              </div>
            </div>
          </div>
        </div>

        <!-- 微信支付二维码-->
        <el-dialog :visible.sync="dialogFormVisible" style="width:800px;margin:0px auto;" >
          <h1 style="font-size:30px;color:#00B38A" >微信扫一扫支付</h1>
          <div id="qrcode" style="width:210px;margin:20px auto;"></div>
          <h2 id="statusText"></h2>
          <p id="closeText"></p>
        </el-dialog>

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
            >{{course.discounts}}
          </span>
          <span class="current-price price">
            <span class="current-price-unite">￥</span>
             {{course.price}}
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
      course: null,
      totalLessons: 0,
      commentList: null,
      myCourseList: [],
      isLogin: false,
      isBuy: false,
      user: null,
      comment:null, // 待发表的留言内容
      dialogFormVisible:false, //默认false：隐藏，true：显示
      time:null,// 计时对象
      orderNo:"",// 订单编号
    };
  },
  created() {
    this.course = this.$route.params.course;
    //检查是否登录
    this.user = JSON.parse(localStorage.getItem("user"));
    if (this.user != null) {
      this.isLogin = true;
      //查询当前用于购买的课程
      this.getMyCourseList();
    }
    let x = 0;
    for (let index = 0; index < this.course.courseSectionList.length; index++) {
      const element = this.course.courseSectionList[index]; //每一章
      for (let index = 0; index < element.courseLessonList.length; index++) {
        x++;
      }
    }
    this.totalLessons = x;
    this.getComment();
  },
  methods: {
    watchCourse(status,lessonid,index,media) {
      if (media == null || media == "") {
        this.$message.error("视频地址不存在无法播放");
        return;
      }
      //试看的可以跳转到播放页面
      //锁上的 先验证是否登录
      //登录后，再验证是否购买过
      if (status===1 && index<2) {
        //观看视频
            this.$router.push({
              name: "videoDetail",
              params: { course: this.course,lessonid: lessonid,isBuy:this.isBuy},
            });
      }else{
        //走验证
        if (!this.isLogin) {
          this.$message.error("请登录");
        }else{
          if (!this.isBuy) {
            this.$message.error("请购买");
          }else{
            //观看视频
            this.$router.push({
              name: "videoDetail",
              params: { course: this.course,lessonid: lessonid,isBuy:this.isBuy},
            });
          }
        }
      }

      
    },
    buy(courseid) {
      alert("购买第【" + courseid + "】门课程成功，加油！");
    },
    getComment() {
      return this.axios
        .get("http://localhost:8002/comment/findCourseComment", {
          params: {
            courseId: this.course.id,
            page: 1,
            pageSize: 50,
          },
        })
        .then((res) => {
          this.commentList = res.data.content.list;
          console.log("comment:", this.commentList);
        })
        .catch(() => {
          this.$message.error("获取留言失败");
        });
    },
    getMyCourseList() {
      return this.axios
        .get(
          "http://localhost:8002/course/getCourseByUserId/" +
            this.user.content.id
        )
        .then((res) => {
          this.myCourseList = res.data.content;
          console.log(this.myCourseList);
          //检测当前的课程是否购买过
          for (let index = 0; index < this.myCourseList.length; index++) {
            const element = this.myCourseList[index];
            if (element.id==this.course.id) {
              this.isBuy = true;//标记购买过
              console.log("购买过该课程");
              break;
            }
          }
        })
        .catch(() => {
          this.$message.error("获取课程信息失败");
        });
    },
        // 发表留言
    saveComment(){
       return this.axios
        .get("http://localhost:80/course/comment/saveCourseComment",{
          params:{
            courseid:this.course.id,
            userid:this.user.content.id,
            username:this.user.content.name,
            comment:this.comment,
          }
        })
        .then((result) => {
          // console.log(result);
          // 重新获取本门课的全部留言信息
          this.getComment();
        }).catch( (error)=>{
          this.$message.error("发表留言失败！");
        } ); 
    },
    zan(comment){        
      return this.axios
      .post("http://localhost:8002/comment/favoriteComment/",{
          userId:this.user.content.id,
          commentId:comment.id
        })
      .then((result) => {
        // console.log(result);
        // 重新获取本门课的全部留言信息
        this.getComment();
      }).catch( (error)=>{
        this.$message.error("点赞失败！");
      } );
    },
    // 取消赞
    cancelzan(comment){
      return this.axios
      .post("http://localhost:8002/comment/favoriteComment/")
      .then((result) => {
        // console.log(result);
        // 重新获取本门课的全部留言信息
        this.getComment();
      }).catch( (error)=>{
        this.$message.error("取消赞失败！");
      } );
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
  line-height: 1.267rem;
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
  font-size: 0.873rem;
  color: #666;
  letter-spacing: 0;
  text-align: justify;
  line-height: 1.1rem;
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
.message-list{
    color: #333;
    padding: 20px 0 30px;
}
.message-list-title-left, .message-list-title {
    display: -webkit-box;
    display: -webkit-flex;
    display: flex;
}
.message-list-title-left{
    -webkit-box-flex: 1;
    -webkit-flex: 1;
    flex: 1;
    -webkit-box-align: center;
    -webkit-align-items: center;
    align-items: center;
    text-align: left;
}
.message-list-title-left-name{
    font-size: .373rem;
    margin-right: .133rem;
    font-weight: 700;
}
.message-list-title-left-tag {
    min-width: 26px;
    height: 16px;
    line-height: 16px;
    padding: 1px 5px;
    text-align: center;
    border-radius: 1px;
    font-size: 10px;
    border-radius: 2px;
    color: #fff;
}
.message-list-title-right {
    overflow: hidden;
    display: -webkit-box;
    display: -webkit-flex;
    display: flex;
    -webkit-box-align: center;
    -webkit-align-items: center;
    align-items: center;
}
.message-list-title-right-icon{
    width: 14px;
    height: 14px;
    margin-right: 4px;
}

img {
    border: 0;
    vertical-align: top;
    display: inline-block;
}
.message-list-title-right-praise {
    height: 10px;
    font-size: 14px;
    font-family: PingFangSC-Regular,PingFang SC;
    font-weight: 400;
    color: #666;
    line-height: 13px;
}
.message-list-content{
    color: #666;
    font-size: 16px;
    margin-top: 10px;
    word-wrap: break-word;
    word-break: break-all;
    overflow: hidden;
    line-height: 30px;
    text-align: left;
}
.pointer {
    cursor: pointer;
}
.message-delete {
    color: #999;
    font-size: 14px;
    display: -webkit-box;
    display: -webkit-flex;
    display: flex;
    -webkit-box-align: center;
    -webkit-align-items: center;
    align-items: center;
    padding-top: 8px;
    position: relative;
    width: 50px;
}


.message-delete-icon{
    width: 14px;
    height: 14px;
    margin-right: 5px;
}
</style>
