import VueRouter from "vue-router";
import Vue from "vue";
/* 进度条 */
import nprogress from 'nprogress'
import 'nprogress/nprogress.css'
import store from "../store/index";
// 使用路由
Vue.use(VueRouter)

const routes = [
    {
        path:'/',
        component:() => import('../views/Login/login')
    },
    {
        path:'/main',
        name:'首页',
        component:() => import('../views/layout/index'),
        children:[
            
        ]
    }, 
    {
        path:'/student',
        name:'学员管理',
        component:() => import('../views/layout/index'),
        children:[
            {
                path:'list',
                name:'学员列表',
                component:() => import('../views/student/Student')
            }
        ]
    }, 
]
const router = new VueRouter({
    mode:'history',
    routes
})
/* 进度条配置 */
nprogress.configure({
    easing: 'ease', // 动画方式,匀速
    speed: 500, // 递增进度条的速度
    showSpinner: true, // 是否显示加载ico
    trickleSpeed: 200, // 自动递增间隔
    minimum: 0.3 // 初始化时的最小百分比
  })
  /* 前置路由守卫 */
  router.beforeEach((to,from,next)=>{
    if(to.path!='/'){
        let flag = store.state.tabList.findIndex(item=>to.name==item.name)
            if(flag === -1){
                store.state.tabList.push({name:to.name,path:to.path})
        }
    }
    nprogress.start()
    if(to.path=='/'){
        next()
    }
    /* else if(!sessionStorage.getItem('token')){
        next('/')
        nprogress.done()
    } */
    else 
        next()   
  })
  /* 后置路由守卫 */
  router.afterEach((to,from)=>{
    nprogress.done()
  })
export default router