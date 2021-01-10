import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
//导入组件库
import ElementUI from 'element-ui'
//导入组件相关样式
import 'element-ui/lib/theme-chalk/index.css'

//导入axios
import axios from 'axios'

//配置Vue的属性
Vue.prototype.axios = axios

//关闭启动提示
Vue.config.productionTip = false

//配置EL 到Vue上
Vue.use(ElementUI)

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount('#app')
