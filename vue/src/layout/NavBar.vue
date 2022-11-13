<template>
  <div>
    <div class="navBar">
      <!-- 折叠按钮 -->
      <div class="foldBtn" @click="changeFold" title="折叠">
        <i :class="isFold"></i>
      </div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item v-for="item of breadcrumbList" 
      :key="item.path"
      :to="item.path">
         {{item.name}}
    </el-breadcrumb-item>
    </el-breadcrumb>
    <div class="rightMenu">
      <div @click="fullScreen()" title="全屏" class="fullScreen">
        <i class="el-icon-full-screen"></i>
      </div>
      <el-dropdown @command="handleCommand">
        <el-avatar icon="el-icon-user-solid" :size="50" :src="$store.state.userInfo.url" fit="cover"></el-avatar>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item icon="el-icon-user-solid" command="setting">个人中心</el-dropdown-item>
          <el-dropdown-item icon="el-icon-switch-button" command="logout">退出登入</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown> 
    </div>
    </div>
     <!-- 动态标签栏 -->
     <div class="tabs-view-container">
      <div class="tabs-wrapper">
        <span :class="isActive(item)" v-for="item of this.$store.state.tabList" :key="item.path" @click="goTo(item)">
          {{ item.name }}
          <i class="el-icon-close" v-if="item.path != '/'" @click.stop="removeTab(item)" />
        </span>
      </div>
      <el-button class="tabs-close-item" style="float: right" @click="closeAllTab">全部关闭</el-button>
    </div>
  </div>
</template>

<script>
export default {
    name:'NavBar',
    data(){
      return{
        breadcrumbList:[],
        isFullScreen:false
      }
    },
    created(){
      this.getBreadcrumb
    },
    methods:{
      // 获取面包屑
      getBreadcrumb(){
        let matched = this.$route.matched
        const first = matched[0]
        const second = matched[1]
        if (first && first.name !== undefined) {
          matched = [{ path: '/', name: '首页' }].concat(matched)
        }
        else if(second && second.name!=='首页'){
          matched = [{ path: '/', name: '首页' }].concat([second])
        }
        else
          matched=[second]
        this.breadcrumbList = matched
      },
      // 全屏
      fullScreen(){
        if(!this.isFullScreen){
          this.isFullScreen=!this.isFullScreen
          if(document.documentElement.RequestFullScreen){
            document.documentElement.RequestFullScreen();
          }
          //兼容火狐
          console.log(document.documentElement.mozRequestFullScreen)
          if(document.documentElement.mozRequestFullScreen){
            document.documentElement.mozRequestFullScreen();
          }
          //兼容谷歌等可以webkitRequestFullScreen也可以webkitRequestFullscreen
          if(document.documentElement.webkitRequestFullScreen){
            document.documentElement.webkitRequestFullScreen();
          }
          //兼容IE,只能写msRequestFullscreen
          if(document.documentElement.msRequestFullscreen){
            document.documentElement.msRequestFullscreen();
          }
        }
        else{
          this.isFullScreen=!this.isFullScreen
          if(document.exitFullScreen){
            document.exitFullscreen()
          }
          //兼容火狐
          console.log(document.mozExitFullScreen)
          if(document.mozCancelFullScreen){
            document.mozCancelFullScreen()
          }
          //兼容谷歌等
          if(document.webkitExitFullscreen){
            document.webkitExitFullscreen()
          }
          //兼容IE
          if(document.msExitFullscreen){
            document.msExitFullscreen()
          }
        }
        
      },
      // 折叠侧边栏
      changeFold(){
         this.$store.commit('changeFold')
      },
      goTo(tab) {
        //跳转标签
        if(tab.path == this.$route.path){
          return
        }
        else
          this.$router.push({ path: tab.path })
      },
      removeTab(tab) {
        //删除标签
        this.$store.commit('removeTab', tab)
        //如果删除的是当前页则返回上一标签页
        if (tab.path == this.$route.path) {
          let tabList = this.$store.state.tabList
          this.$router.push({ path: tabList[tabList.length - 1].path })
        }
      },
      handleCommand(command){
        if(command=='setting'){
          this.$router.push({path:'/my'})
        }
        if(command=='logout'){
          this.$confirm('确定退出登入吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => { 
          this.$message({
            type: 'success',
            message: '已退出!'
          });
          this.$store.commit('logout')
          this.$store.commit('resetTab') 
          this.$router.replace({path:'/login'})
          
        }).catch(() => {
           return          
        });  
        }
      },
      closeAllTab(){
        if(this.$store.state.tabList.length === 1)
          return
        else if(this.$store.state.tabList.length !== 1 && this.$route.path === '/')
          this.$store.commit('resetTab')
        else{
          this.$store.commit('resetTab')
          this.$router.push({ path: '/' })
        }  
      },
    },
    computed:{
      isFold(){
        return this.$store.state.isFold?'el-icon-s-unfold':'el-icon-s-fold'
      },
      //标签是否处于当前页
      isActive(){
        return function (tab) {
          if (tab.path == this.$route.path) {
            return 'tabs-view-item-active'
          }
          return 'tabs-view-item'
        }
      },
    },
    // 监听路由变化
    watch:{
      $route:{
        immediate:true,
        handler(){
          this.getBreadcrumb()
        }
      }
    }
}
</script>

<style>
  .navBar{
    position: relative;
    display: flex;
    height: 56px;
    align-items: center;
    padding: 0 10px;
  }
  .foldBtn{
    cursor: pointer;
    font-size: 20px;
  }
  .rightMenu{
    display: flex;
    justify-content: space-between;
    align-items: center;
    position: absolute;
    right: 10px;
  }
  .fullScreen{
    cursor: pointer;
    font-size: 20px;
    margin-right: 10px;
  }
  .el-dropdown{
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
    width: 50px;
    height: 50px;
  }
  .el-breadcrumb{
    margin-left: 20px;
  }
  .tabs-view-container{
    display: flex;
    align-items: center;
    position: relative;
    height: 34px;
    background: #fff;
    border-bottom: 1px solid #d8dce5;
    box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.12), 0 0 3px 0 rgba(0, 0, 0, 0.04);
  }
  .tabs-wrapper {
    overflow-x: auto;
    overflow-y: hidden;
    white-space: nowrap;
    width: 95%;
  }
  .tabs-close-item {
    position: absolute;
    right: 10px;
    line-height: 25px;
    border: 1px solid #d8dce5;
    color: #495060;
    background: #fff;
    padding: 0 8px;
    font-size: 12px;
  }
  .tabs-view-item {
    cursor: pointer;
    float: left;
    margin-left: 10px;
    line-height: 25px;
    border: 1px solid #d8dce5;
    color: #495060;
    background: #fff;
    padding:0 8px;
    font-size: 12px;
  }
  .tabs-view-item-active {
    cursor: pointer;
    margin-left: 10px;
    float:left;
    line-height: 26px;
    padding: 0 8px;
    font-size: 12px;
    background-image: linear-gradient(rgb(0, 214, 252),rgb(61,81,252));
    color: #fff;
  }
  .tabs-view-item-active:before {
    content: '';
    background: #fff;
    display: inline-block;
    width: 8px;
    height: 8px;
    border-radius: 50%;
    position: relative;
    margin-right: 2px;
  }
</style>