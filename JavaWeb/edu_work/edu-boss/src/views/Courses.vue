<template>
  <section class="courses">
    <!-- 表单部分 -->
    <el-form class="actions" :inline="true" :model="filter">
      <el-form-item class="input-title" label="课程名称">
        <el-input v-model="filter.course_name" type="search" placeholder="课程名称" />
      </el-form-item>

      <el-form-item label="状态">
        <el-select v-model="filter.status" placeholder="课程状态">
          <el-option label="全部" value></el-option>
          <el-option label="已发布" value="1"></el-option>
          <el-option label="草稿" value="0"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button @click="filterQuery">查询</el-button>
      </el-form-item>

      <el-form-item class="btn-add">
        <el-button type="primary" icon="el-icon-plus" @click="addCourse">新建课程</el-button>
      </el-form-item>
    </el-form>

    <!-- 表格部分 -->
    <el-table :data="courses" v-loading="loading" element-loading-text="数据加载中...">
      <el-table-column prop="id" label="ID" width="100"></el-table-column>
      <el-table-column prop="course_name" label="课程名称" width="200"></el-table-column>
      <el-table-column prop="price" label="价格" align="center" width="120" :formatter="priceFormatter"></el-table-column>
      <el-table-column prop="sort_num" label="排序" align="center" width="120"></el-table-column>

      <!-- 状态展示 -->
      <el-table-column prop="status" label="状态" align="center" width="120">
        <template slot-scope="scope">
          <i class="status status-success" title="已发布" v-if="scope.row.status == '1'" @click="updateStatus(scope.row)"></i>
          <i class="status status-warning" title="草稿" v-else-if="scope.row.status == '0'"></i>
        </template>
      </el-table-column>

      <!-- 操作部分 -->
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <!-- 状态按钮 -->
          <el-button size="mini" :type="scope.row.status == '1' ? 'danger' : 'success'" @click="updateStatus(scope.row)">{{ scope.row.status == "1" ? "下架" : "发布" }}</el-button>

          <!-- 营销信息按钮 -->
          <el-button size="mini" @click="handleNavigate('CourseItem', scope.row.id)">营销信息</el-button>

          <!-- 内容管理按钮 -->
          <el-button size="mini" @click="handleNavigate('CourseTasks', scope.row.id)">内容管理</el-button>
        </template>
      </el-table-column>
    </el-table>
  </section>
</template>

// JS部分代码
<script>
//引入axios
import { axios } from "../utils";

export default {
  name: "Courses",
  title: "课程管理",
  //定义数据部分
  data() {
    return {
      filter: { course_name: "", status: "" }, //查询对象
      courses: [], //课程信息集合
      loading: false //是否弹出加载
    };
  },

  //钩子函数
  created() {
    this.loadCourses();
  },

  methods: {
    //方法1: 获取课程列表
    loadCourses() {
      this.loading = true
      return axios.get("/course",{
        params:{
          methodName:"findCourseList"
        }
      }).then(resp=>{
        this.courses = resp.data
        this.loading = false
      }).catch(error=>{
        this.$message.error('数据获取失败')
      })
    },

    //方法2: 条件查询课程信息
    filterQuery() {
     this.loading = true
     return axios.get("/course",{
        params:{
          methodName:"findByCourseNameAndStatus",
          course_name: this.filter.course_name,
          status: this.filter.status
        }
      }).then(resp=>{
        this.courses = resp.data
        this.loading = false
      }).catch(error=>{
        this.$message.error('数据获取失败')
      })
    },

    //方法3: 添加课程跳转方法
    addCourse() {
     this.$router.push(
       {
         name:'CourseItem',
         params:{
           courseId:"new"
         }
       })
    },

    //方法4: 修改课程状态
    updateStatus(item) {
      return axios.get("/course",{
        params:{
          methodName:"updateCourseStatus",
          id: item.id
        }
      }).then(resp=>{
        Object.assign(item,resp.data)
      }).catch(error=>{
        this.$message.error('数据获取失败')
      })
    },

    //方法5: 根据路由名称, 导航到对应组件
    handleNavigate(name, id) {
     this.$router.push(
       {
         name:name,
         params:{
           courseId:id
         }
       })
    },

    //价格前面添加¥ 方法
    priceFormatter(row, column, value, index) {
      return `¥${value}`;
    }
  }
};
</script>

<style lang="scss">
.courses {
  .actions {
    display: flex;
    align-items: flex-end;
  }

  .input-title {
    .el-form-item__content {
      min-width: 22vw;
    }
  }

  .btn-add {
    margin-left: auto;
  }
}
</style>
