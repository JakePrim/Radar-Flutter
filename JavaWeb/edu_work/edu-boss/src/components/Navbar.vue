<template>
  <el-header class="navbar" height="auto">
    <el-button class="hamburger" type="text" :icon="sidebar.collapse ? 'el-icon-s-unfold' : 'el-icon-s-fold'" @click="toggleCollapse"></el-button>
    <el-breadcrumb separator="/" replace>
      <el-breadcrumb-item :to="{ name: 'Home' }">Home</el-breadcrumb-item>
      <el-breadcrumb-item v-for="item in navbar.breadcrumbs" :key="item.name" :to="item">{{ item.text || item.name }}</el-breadcrumb-item>
    </el-breadcrumb>
    <el-dropdown @command="handleCommand" v-if="session.user">
      <img class="avatar" :src="session.user.avatar" :alt="session.user.name" :title="session.user.name">
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item command="profile">{{ session.user.name }}</el-dropdown-item>
        <el-dropdown-item command="logout" divided>登出</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </el-header>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'

export default {
  name: 'Navbar',

  computed: mapGetters({
    navbar: 'navbar',
    sidebar: 'sidebar',
    session: 'session'
  }),

  created () {
    this.$store.dispatch('getCurrentUser')
  },

  methods: {
    ...mapActions({
      toggleCollapse: 'toggleSidebarCollapse'
    }),
    async handleCommand (command) {
   
      switch (command) {
        case 'logout':
          await this.$store.dispatch('deleteToken')
          this.$router.replace({ name: 'Login' })
          break
      }
    }
  }
}
</script>

<style lang="scss">
.navbar {
  position: sticky;
  top: 0;
  z-index: 100;
  display: flex;
  align-items: center;
  padding: 0 !important;
  background-color: #fff;

  .hamburger {
    margin-right: 10px;
    padding: 15px;
    font-size: 20px;
    border: 0;
    border-radius: 0;

    &:hover {
      background-color: rgba(0, 0, 0, 0.1);
    }
  }

  .el-dropdown {
    margin-left: auto;
  }

  .avatar {
    display: block;
    margin: 10px;
    width: 30px;
    height: 30px;
    border: 1px solid rgba(0, 0, 0, 0.2);
    border-radius: 50%;
    box-sizing: border-box;
  }
}
</style>
