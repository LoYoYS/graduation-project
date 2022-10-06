import Vue from 'vue'
import App from './App.vue'
/* 引入element ui */
import './plugins/element'
/* 引入路由 */
import router from './router/index'
/* 引入Vuex */
import store from './store/index'
import axios from 'axios'
import VueAxios from "vue-axios"
import{MessageBox} from 'element-ui'

const service=axios.create({
  baseURL: 'http://localhost:8080',
  timeout: 30000,// 超时时间
  // responseType: "json",
    //请求头
 /*  headers: {
      'Content-Type': 'application-json/x-www-form-urlencoded;charset=utf-8',
  } */
})

/** 添加请求拦截器 **/
service.interceptors.request.use(
  config=>{
  config.headers.token = sessionStorage.getItem('token') === null ? '' : sessionStorage.getItem('token')
  return config;
})
/* 响应拦截器 */
service.interceptors.response.use((res)=>{
  if(res.data.code===9999){
    MessageBox.alert(res.data.data,'错误',{
      confirmButtonText: '确定',
      type:'error'
    }).then(()=>{
        sessionStorage.removeItem('token')
        router.replace({path:'/login'})
    }).catch(()=>{
        sessionStorage.removeItem('token')
        router.replace({path:'/login'})
    })
  }
  else if(res.data.code===5005){
    MessageBox.alert(res.data.data,'错误',{
      confirmButtonText: '确定',
      type:'error'
    }).then(()=>{
      return
    }).catch(()=>{
      return
    })
  }
  else
    return res.data
})

Vue.use(VueAxios, service) 
Vue.config.productionTip = false
new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
