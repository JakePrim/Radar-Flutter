<template>
  <section class="course-task-item">
    <div class="header">
      <el-page-header @back="() => this.$router.back()" :content="content.title"/>
      <el-button type="primary" icon="el-icon-plus">保存</el-button>
    </div>
    <el-table :data="lessons" v-loading="loading" element-loading-text="数据加载中...">
      <el-table-column prop="id" label="ID" width="230"></el-table-column>
      <el-table-column prop="title" label="标题"></el-table-column>
      <el-table-column prop="type" label="类型" align="center" width="180">
        <template slot-scope="scope">
          <span v-if="scope.type === 'video'">录播视频</span>
          <span v-else-if="scope.type === 'test'">随堂测试</span>
          <span v-else>模块作业</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="200">
        <template>
          <el-button size="small">上传视频</el-button>
          <el-button size="small">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
  </section>
</template>

<script>
export default {
  name: 'CourseTaskItem',
  title: '课时配置',
  data () {
    return {
      content: {},
      lessons: [],
      loading: true
    }
  },
  created () {
    const { courseId, taskId } = this.$route.params
    this.$breadcrumbs = [
      { name: 'Courses', text: '课程管理' },
      { name: 'CourseTasks', params: { courseId }, text: '课程结构' },
      { text: '课时配置' }
    ]

    this.loadData(courseId, taskId)
  },
  methods: {
    loadData (courseId, taskId) {
      this.loading = true
      const params = {}
      params.course_id = courseId
      params.parent_id = taskId

      Promise.all([
        this.$services.content.get(taskId),
        this.$services.content.get({ params })
      ])
        .then(([res1, res2]) => {
          this.loading = false
          this.content = res1.data
          this.lessons = res2.data
        })
        .catch(() => {
          this.loading = false
        })
    }
  }
}
</script>

<style lang="scss">
.course-task-item {
  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .el-table {
    margin-top: 20px;
  }
}
</style>
