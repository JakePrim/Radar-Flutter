import Vue from 'vue'
import App from './App.vue'
import router from './router'

// 导入ele组件库
import ElementUI from 'element-ui'
// 导入ele组件相关样式
import 'element-ui/lib/theme-chalk/index.css'
// 配置ele插件,将其安装到Vue上
Vue.use(ElementUI);

// 引入axios
import axios from 'axios'
// 配置axios到Vue
Vue.prototype.axios = axios;

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
