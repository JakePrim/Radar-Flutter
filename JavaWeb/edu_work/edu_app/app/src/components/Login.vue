<template>
  <el-dialog title="用户登录" :visible.sync="dialogFormVisible" :show-close="false">

    <el-form>
      <el-form-item label="用户名称" :label-width="formLabelWidth">
        <el-input v-model="user.username" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="用户密码" :label-width="formLabelWidth">
        <el-input v-model="user.password" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>

    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="login">登录</el-button>
    </div>
  </el-dialog>
</template>
<script>
export default {
  name:'Login',
  data(){
    return {
        dialogFormVisible: true,
        formLabelWidth: '120px',
        user:{
          username:'',
          password:''
        }
    }
  },
  methods:{
    login(){
      //登录的方法
      //获取用户名和密码
      // alert(this.$data.user.username)
      const url = 'https://9b2b373a-7e7e-467d-a7e3-ca73f32681f4.mock.pstmn.io/login';
      //发送请求
      this.axios(url,{
        //携带的参数
        params:{
          username:this.user.username,
          password:this.user.password
        }
      }).then(resp=>{
        console.log(resp);
        alert("登录成功")
        this.dialogFormVisible = false
        //跳转到首页 使用路由
        this.$router.push('index')
      }).catch((error)=>{
        //消息提示登录失败
        this.$message.error('登录失败，请重试');
      });
    }
  }
}
</script>
<style scoped>
</style>