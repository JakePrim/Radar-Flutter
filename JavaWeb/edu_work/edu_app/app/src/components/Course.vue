<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="6">
        <el-input prefix-icon="el-icon-search" placeholder="课程名称" v-model="filter.course_name" clearable>
        </el-input>
      </el-col>
      <el-col :span="1">
        <el-button type="primary" @click="findCourse">查询</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" element-loading-text="拼命加载中" :data="courseList" stripe style="width: 100%">
      <el-table-column prop="id" label="ID">
      </el-table-column>
      <el-table-column prop="course_name" label="课程名称">
      </el-table-column>
      <el-table-column prop="price" label="价格">
      </el-table-column>
      <el-table-column prop="sort_num" label="排序">
      </el-table-column>
      <el-table-column prop="status" label="状态">
      </el-table-column>
    </el-table>
  </div>
</template>
<script>
export default {
  name:'Course',
  data() {
      return {
        loading:true,
        courseList: [],
        filter:{
          course_name:"",
        }
      }
    },
  //钩子函数
  created(){
    //dom页面生成之前执行 请求接口
    alert("准备加载数据")
    this.loadCourse()
  },
  methods:{
    loadCourse(){
      //获取课程信息
      const url = "http://localhost:8080/edu_api/course";
      return this.axios.get(url,{
        params:{
          methodName:'findCourseList'
        }
      }).then(resp=>{
        console.log(resp.data)
        this.courseList = resp.data
        //取消加载动画
        this.loading = false
      }).catch(error=>{
        this.$message.error('请求失败，请重试');
      });
    },
    findCourse(){
      this.loading = true;//开启加载
      let course_name = this.filter.course_name;
      const url = "http://localhost:8080/edu_api/course";
      return this.axios.get(url,{
        params:{
          methodName:'findByCourseNameAndStatus',
          course_name:course_name
        }
      }).then(resp=>{
        this.courseList = resp.data
        //取消加载动画
        this.loading = false
      }).catch(error=>{
        this.$message.error('请求失败，请重试');
      });
    }
  }
}
</script>
<style scoped>
</style>