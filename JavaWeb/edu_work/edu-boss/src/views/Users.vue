<template>
  <section class="users">
    <el-form class="actions" :model="filter" label-position="top" inline>
      <!-- <el-form-item label="ID">
        <el-input v-model="filter.id" placeholder="请输入用户 ID"/>
      </el-form-item> -->
      <el-form-item label="用户名">
        <el-input v-model="filter.username" placeholder="请输入用户名"/>
      </el-form-item>
      <el-form-item label="注册时间">
        <el-date-picker v-model="filter.created" type="daterange" start-placeholder="开始日期" end-placeholder="结束日期" range-separator="至" :picker-options="pickerOptions"/>
      </el-form-item>
      <el-form-item label="最近登录时间">
        <el-date-picker v-model="filter.last_login" type="daterange" start-placeholder="开始日期" end-placeholder="结束日期" range-separator="至" :picker-options="pickerOptions"/>
      </el-form-item>
      <el-form-item>
        <el-button @click="handleFilter">查询</el-button>
      </el-form-item>
      <el-form-item class="btn-add">
        <el-button type="primary" icon="el-icon-plus" @click="handleAdd">添加用户</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="users" v-loading="loading" element-loading-text="数据加载中...">
      <el-table-column prop="id" label="ID" width="230"></el-table-column>
      <el-table-column prop="username" label="用户名" width="150"></el-table-column>
      <el-table-column label="基本信息">
        <template slot-scope="scope">
          <img class="avatar" :src="scope.row.avatar" :alt="scope.row.name">
          <span class="name">{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="created" label="注册时间" width="180" :formatter="dateFormatter"></el-table-column>
      <el-table-column prop="last_login" label="最近登录时间" width="180" :formatter="dateFormatter"></el-table-column>
      <el-table-column prop="status" label="状态" align="center" width="120">
        <template slot-scope="scope">
          <i class="status status-success" title="正常" v-if="scope.row.status === 'activated'" @click="handleToggleStatus(scope.row)"></i>
          <i class="status status-warning" title="邮箱未激活" v-else-if="scope.row.status === 'email-unactivated'"></i>
          <i class="status status-warning" title="手机未激活" v-else-if="scope.row.status === 'phone-unactivated'"></i>
          <i class="status status-danger" title="禁用" v-else-if="scope.row.status === 'forbidden'" @click="handleToggleStatus(scope.row)"></i>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="120">
        <template slot-scope="scope">
          <el-button size="mini" :type="scope.row.status === 'activated' ? 'danger' : 'success'" @click="handleToggleStatus(scope.row)">{{ scope.row.status === 'activated' ? '禁用' : '启用' }}</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination v-if="users.length" layout="total, sizes, prev, pager, next, jumper" @size-change="handlePageSizeChange" @current-change="handleCurrentPageChange" :current-page="page" :page-sizes="[20, 30, 50]" :page-size="size" :total="total"/>
  </section>
</template>

<script>
export default {
  name: 'Users',
  title: '用户管理',
  data () {
    const pickerOptions = {
      shortcuts: [
        {
          text: '最近一周',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', [start, end])
          }
        },
        {
          text: '最近一个月',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            picker.$emit('pick', [start, end])
          }
        },
        {
          text: '最近三个月',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
            picker.$emit('pick', [start, end])
          }
        }
      ]
    }

    const filter = {
      id: '',
      username: '',
      created: null,
      last_login: null
    }

    return {
      pickerOptions,
      filter,
      users: [],
      total: 0,
      size: 20,
      page: 1,
      loading: false
    }
  },
  created () {
    // initial data
    this.loadUsers()
  },
  methods: {
    loadUsers () {
      // toggle loading
      this.loading = true

      // paginate
      const params = { _page: this.page, _limit: this.size }

      // // sort
      // if (this.sort) params._sort = this.sort
      // if (this.order) params._order = this.order
      // // search
      // if (this.search) params.q = this.filter.search

      // filter
      if (this.filter.id) params.id = this.filter.id
      if (this.filter.username) params.username = this.filter.username
      if (this.filter.created) {
        params.created_gte = this.filter.created[0]
        params.created_lte = this.filter.created[1]
      } if (this.filter.last_login) {
        params.last_login_gte = this.filter.last_login[0]
        params.last_login_lte = this.filter.last_login[1]
      }

      // request data
      return this.$services.user.get({ params })
        .then(res => {
          // response
          this.users = res.data
          this.total = ~~res.headers['x-total-count']
          // toggle loading
          this.loading = false
        })
        .catch(err => {
          // handle error
          console.error(err)
          this.loading = false
        })
    },

    handleCurrentPageChange (page) {
      this.page = page
      this.loadUsers()
    },

    handlePageSizeChange (size) {
      this.size = size
      this.loadUsers()
    },

    handleFilter () {
      this.loadUsers()
    },

    handleToggleStatus (item) {
      const targetStatus = item.status === 'forbidden' ? 'activated' : 'forbidden'
      this.$services.user
        .patch(item.id, { status: targetStatus })
        .then(res => Object.assign(item, res.data))
    },

    handleAdd () {
      this.$message({ message: '尚未规划此功能' })
    },

    dateFormatter (row, column, value, index) {
      // console.log(row, column, value, index)
      const date = new Date(value)
      // TODO: moment or dayjs or pure
      const pad = n => ('00' + n).substr(-2)

      return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())} ${pad(date.getHours())}:${pad(date.getMinutes())}`
    }
  }
}
</script>

<style lang="scss">
.users {
  .actions {
    display: flex;
    align-items: flex-end;

    label {
      line-height: 1;
    }
  }

  .btn-add {
    margin-left: auto;
    margin-right: 0;
  }

  .avatar {
    margin-right: 10px;
    width: 30px;
    height: 30px;
    border: 1px solid rgba(0, 0, 0, 0.2);
    border-radius: 50%;
    box-sizing: border-box;
    vertical-align: middle;
  }

  .name {
    display: inline-block;
    line-height: 30px;
    vertical-align: middle;
  }

  .el-pagination {
    margin-top: 30px;
    text-align: right;
  }
}
</style>
