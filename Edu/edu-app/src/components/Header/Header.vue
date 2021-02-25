<template>
  <div class="header-pc-wrap">
    <!-- 登录框 开始-->
    <el-dialog
      style="width:800px;margin:0px auto;"
      title=""
      :visible.sync="dialogFormVisible">
      <el-form>
        <el-form-item>
          <h1 style="font-size:30px;color:#00B38A">拉勾</h1>
        </el-form-item>
        <el-form-item>
          <el-input v-model="phone" placeholder="请输入常用手机号..."></el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="password" placeholder="请输入密码..."></el-input>
        </el-form-item>
      </el-form>
      <el-button
        style="width:100%;margin:0px auto;background-color: #00B38A;font-size:20px"
        type="primary"
        @click="login">确 定</el-button>
      <p></p>
      <!-- 微信登录图标 -->
      <img
        @click="goToLoginWX"
        src="http://www.lgstatic.com/lg-passport-fed/static/pc/modules/common/img/icon-wechat@2x_68c86d1.png"
        alt=""
      />
    </el-dialog>
    <!-- 登录框 结束-->

    <!-- 顶部登录条 -->
    <div class="wrap-box">
      <div @click="toToIndex" class="edu-icon"></div>
      <div @click="toToIndex" class="text">拉勾教育</div>
      <div class="right-var-wrap" v-if="!isLogin">
        <div class="login-handler" @click="goToLogin">登录 | 注册</div>
      </div>
      <div class="right-var-wrap" v-if="isLogin">
        <div
          :class="{ 'tip-icon': true, 'has-new-message': isHasNewMessage }"
          @click="toToNotic"
        >
          <i class="el-icon-bell"></i>
        </div>
        <img :src="userDTO.content.portrait" class="avatar-wrap" />
        <div class="bar-wrap">
          <ul class="account-bar" >
            <li class="user_dropdown" data-lg-tj-track-code="index_user" >
              <span class="unick">{{ userDTO.content.name }}</span>
              <i />
              <ul style="">
                <li @click="goToSetting">
                  账号设置
                </li>
                <li @click="logout">
                  退出
                </li>
              </ul>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Header",

  props: {},
  data() {
    return {
      isLogin: false, // 登录状态，true：已登录，false：未登录
      userDTO:null, // 用来保存登录的用户信息
      isHasNewMessage: false, // 是否有新的推送消息
      dialogFormVisible: false, // 是否显示登录框，true：显示，false：隐藏
      phone: "", // 双向绑定表单 手机号
      password: "", // 双向绑定表单 密码
      userDTO:null,
    };
  },
  computed: {
  },
  watch: {
  },
  mounted() {
  },
  created(){
    //获取本地存储的用户数据
    this.userDTO = JSON.parse(localStorage.getItem("user"));
    console.log("sss:",this.userDTO);
    if(this.userDTO !== null){
      this.isLogin = true;
    }
  },
  methods: {
    goToSetting() {
      this.$router.push("/setting"); // 跳转个人设置页面
    },
    goToLogin() {
      this.dialogFormVisible = true; // 显示登录框
    },
    goToLoginWX() {
      alert("微信登录");
    },
    toToIndex() {
      this.$router.push("/"); //回到首页
    },
    toToNotic(){
    },
    login(){
      // 登录请求远程服务dubbo
      return this.axios.get("http://localhost:8002/user/login",{
        params:{
          phone:this.phone,
          password:this.password
        }
      }).then(res=>{
        this.dialogFormVisible = false;
        this.isLogin = true;
        this.userDTO = res.data;
        // 保存到本地
        console.log("aaa:"+JSON.stringify(this.userDTO));
        localStorage.setItem("user",JSON.stringify(this.userDTO));
      }).catch(()=>{
        this.$message.error('登录失败');
      })
    },
    logout(){
      localStorage.setItem("user",null);
      this.isLogin = false;
    }
  },
};
</script>

<style lang="less" scoped>
.header-pc-wrap {
  width: 100%;
  height: 40px;
  background: rgba(35, 39, 43, 1);
}
.wrap-box {
  width: 1200px;
  height: 100%;
  margin: 0 auto;
}
.edu-icon {
  float: left;
  width: 24px;
  height: 24px;
  background: url("./static/img/Icon@2x.png") no-repeat;
  background-size: 100% 100%;
  margin-top: 8px;
}
.text {
  font-size: 16px;
  font-weight: 500;
  color: rgba(255, 255, 255, 1);
  line-height: 40px;
  float: left;
  margin-left: 6px;
}
.login-handler {
  font-size: 12px;
  font-weight: 500;
  color: rgba(255, 255, 255, 1);
  line-height: 40px;
  float: left;
  cursor: pointer;
}
.right-var-wrap {
  float: right;
  font-size: 0;
  text-align: right;
}
.tip-icon,
.avatar-wrap,
.bar-wrap {
  display: inline-block;
  vertical-align: top;
}
.tip-icon {
  font-size: 16px;
  line-height: 40px;
  margin-right: 26px;
  color: #818895;
  cursor: pointer;
  &:hover {
    color: #fff;
  }
  &.has-new-message {
    position: relative;
    &:after {
      content: " ";
      display: inline-block;
      position: absolute;
      top: 50%;
      width: 6px;
      height: 6px;
      border-radius: 3px;
      background: red;
      right: 0;
      margin-top: -7px;
    }
  }
}
.user_dropdown {

  position: relative;
  cursor: pointer;
  font-size: 14px;
  text-align: center;
  &:hover {
    .unick {
      color: #fff;
    }
    i {
      -webkit-transform: rotate(180deg);
      -moz-transform: rotate(180deg);
      -ms-transform: rotate(180deg);
      -o-transform: rotate(180deg);
      transform: rotate(180deg);
      animation-fill-mode: forwards;
      border-color: #fff transparent transparent;
    }
    > ul {
      display: block;
      position: absolute;
      top: 40px;
      width: 100%;
      min-width: 80px;
      line-height: 30px;
      background-color: #32373e;
      z-index: 1000;
      list-style: none; margin:0px;padding:0px;
      > li {
        width: 100%;
        height: 30px;
        &:hover {
          background-color: #25282d;
          color: #fff;
        }
      }
    }
  }
  .unick {
    display: block;
    height: 40px;
    line-height: 40px;
    font-size: 14px;
    color: #afb5c0;
    max-width: 96px;
    margin: 0 9px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    word-wrap: normal;
    &:hover {
      color: #fff;
    }
  }
  > i {
    position: absolute;
    top: 17px;
    right: 0;
    font-size: 0;
    height: 0;
    width: 0;
    border-width: 5px 4px 0;
    border-style: solid dashed;
    border-color: #afb5c0 transparent transparent;
    overflow: hidden;
    -webkit-transition: all 0.4s ease 0s;
    -moz-transition: all 0.4s ease 0s;
    -ms-transition: all 0.4s ease 0s;
    -o-transition: all 0.4s ease 0s;
    transition: all 0.4s ease 0s;
  }
  > ul {
    display: none;
    color: #afb5c0;
  }
}
.avatar-wrap {
  width: 24px;
  height: 24px;
  margin-top: 8px;
  border-radius: 50%;
}
</style>
