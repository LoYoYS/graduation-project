import VueRouter from "vue-router";
import Vue from "vue";
/* 进度条 */
import nprogress from 'nprogress'
import 'nprogress/nprogress.css'
import store from "../store/index";
const Index = () => import('../layout/Index')
// 使用路由
Vue.use(VueRouter)

const routes = [
    {
        path:'/login',
        component:() => import('../views/login/Login')
    },
    {
        path:'',
        component:Index,
        children:[
            {
                path:'/',
                name:'首页',
                component:() => import('../views/home/Home')
            },
            {
                path:"/my",
                name:'个人中心',
                component:() => import('../views/my/My')
            }, 
            {
                path:"/user",
                name:'用户列表',
                component:() => import('../views/user/User')
            }
        ]
    },
    {
        path:'/student',
        name:'学员管理',
        component:Index,
        children:[
            {
                path:'list',
                name:'学员列表',
                component:() => import('../views/student/Student')
            },
            {
                path:'save',
                name:'添加学员',
                component:() => import('../views/student/SaveStudent')
            }

        ]  
    },
    {
        path:'/train',
        name:'教练管理',
        component:Index,
        children:[{
            path:'list',
            name:'教练列表',
            component:() => import('../views/train/Train')
            }]  
    },
    {
        path:'/car',
        name:'车辆管理',
        component:Index,
        children:[{
            path:'list',
            name:'车辆列表',
            component:() => import('../views/car/Car')
            }]  
    },
    {
        path:'/notice-introduce',
        name:'公告-简介',
        component:Index,
        children:[
            {
                path:'notices',
                name:'公告列表',
                component:() => import('../views/notice-introduce/Notice')
            },
            {
                path:'introduce',
                name:'驾校简介',
                component:() => import('../views/notice-introduce/Introduce')
            }
        ]  
    }
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
    if(to.path!='/login'){
        let flag = store.state.tabList.findIndex(item=>to.name==item.name)
            if(flag === -1){
                store.state.tabList.push({name:to.name,path:to.path})
        }
    }
    nprogress.start()
    if(to.path=='/login'){
        next()
    }
    else if(!sessionStorage.getItem('token')){
        next('/login')
        nprogress.done()
    }
    else 
        next()   
  })
  /* 后置路由守卫 */
  router.afterEach((to,from)=>{
    nprogress.done()
  })
export default router