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
import { Message } from 'element-ui';


const service=axios.create({
  baseURL: '/api',
  timeout: 30000,// 超时时间
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
    Message.error(res.data.data)
  }
  else
    return res.data
},(err)=>{
  Message.error('请求错误，请稍后重试！')
})

Vue.use(VueAxios, service) 
Vue.config.productionTip = false
new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
