<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
import Header from "./components/Header/Header"; //顶部登录条
import Footer from "./components/Footer/index"; //顶部登录条
export default {
  name: "app",
  components: {
    Header,
    Footer
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
