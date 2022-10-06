//引入Vue核心库
import Vue from 'vue'
//引入Vuex
import Vuex from 'vuex'
import VuexPersistence from "vuex-persist"
//应用Vuex插件
Vue.use(Vuex)

//准备actions对象——响应组件中用户的动作
const actions = {}
//准备mutations对象——修改state中的数据
const mutations = {
    // 改变折叠状态
    getUserInfo(state,user){
        state.userInfo=user
    },
    changeFold(state){
        state.isFold=!state.isFold
    },
    removeTab(state, tab) {
        var index = state.tabList.findIndex((item) => item.name === tab.name)
        state.tabList.splice(index, 1)
    },
    resetTab(state) {
        state.tabList = [{ name: '首页', path: '/' }]
    },
    logout(state) {
        state.userInfo = null
        sessionStorage.removeItem('token')
    },
    updateAvatar(state,url){
        state.userInfo.url=url
    }
}
//准备state对象——保存具体的数据
const state = {
    /* 侧边栏折叠状态 */
    isFold:false,
    userInfo: null,
    // 标签数组
    tabList: [{ name: '首页', path: '/' }],
}
 
const vueLocal=new VuexPersistence({
    storage:window.sessionStorage
  })


export default new Vuex.Store({
    actions,
    mutations,
    state,
    plugins:[vueLocal.plugin]
})