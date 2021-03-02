<template>
  <div>
    <template>
      <div class="all-container">
        <div class="all-container-padding bg">
          <el-tabs>
            <el-tab-pane label="基本信息" name="first">
              <el-form>
                <el-form-item
                  label="头像"
                  :label-width="formLabelWidth"
                >
                  <el-upload
                    class="avatar-uploader"
                    action="http://localhost:8002/user/uploadFile"
                    :before-upload="beforeupload"
                    :data="uploadParm"
                    :show-file-list="false"
                    :on-success="handleUpSuccess"
                  >
                    <img
                      class="avatar"
                      style="width:50px;height:50px"
                      :src="user.content.portrait"/>
                    <i
                      class="el-icon-plus avatar-uploader-icon"
                      style="width: 80px; height: 80px"
                    ></i>
                  </el-upload>
                </el-form-item>
                <el-form-item
                  label="用户名"
                  :label-width="formLabelWidth"
                >
                  <el-col :span="8">
                    <el-input v-model="username"></el-input
                  ></el-col>
                </el-form-item>
              </el-form>
              <div class="grid-content bg-purple">
                <el-button type="primary"
                @click="EditorUserClick()"
                  >保存</el-button
                >
              </div>
            </el-tab-pane>
            <el-tab-pane label="修改密码" name="second">
              <el-form>
                <el-form-item
                  label="新密码"
                  :label-width="formLabelWidth"
                >
                  <el-col :span="8"
                    ><el-input
                      placeholder="请输入新密码"
                      id="newkey"
                      type="password"
                      v-model="password"
                    ></el-input
                  ></el-col>
                </el-form-item>
              </el-form>
              <div class="grid-content bg-purple">
                <el-button type="primary" 
                 @click="EditorPassword()"
                  >保存</el-button
                >
              </div>
            </el-tab-pane>
          </el-tabs>
        </div>
      </div>
    </template>
  </div>
</template>
  </div>
</template>
<script>
export default {
  name: "setting",
  data() {
    return {
      uploadParm: {}, //图片的上传
      ruleForm: {}, //修改密码的表单
      activeName: "first",
      loading: true,
      baseUrl: process.env.BASE_API,
      userlist: {}, //用户信息表单
      formLabelWidth: "150px",
      user:null,
      isLogin:false,
      username:"",
      avater:"",
      password:"",
    };
  },
  created() {
    //获取用户信息
    this.user = JSON.parse(localStorage.getItem("user"));
    if (this.user != null) {
      this.isLogin = true;
      this.avater = this.user.content.portrait;
      this.username = this.user.content.name
    }
  },
  methods: {
    handleUpSuccess(){

    },
    beforeupload(){

    },
    EditorUserClick(){
        this.axios.post("http://localhost:8002/user/updateUserInfo",{
            portrait:this.avater,
            name:this.username,
            id:this.user.content.id
        }).then(res=>{
            this.$message.success("更新成功")
            this.user.content.name = this.username;
            this.user.content.portrait = this.avater;
            localStorage.setItem("user",JSON.stringify(this.user));
        }).catch(()=>{
            this.$message.error("更新失败")
        })
    },
    EditorPassword(){
        this.axios.post("http://localhost:8002/user/updatePassword",{
            password:this.password,
            id:this.user.content.id
        }).then(res=>{
            this.$message.success("更新成功")
        }).catch(()=>{
            this.$message.error("更新失败")
        })
    }
  },
};
</script>
<style scoped>
</style>