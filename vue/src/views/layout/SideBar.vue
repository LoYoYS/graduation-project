<template>
  <div class="content">
      <el-menu
      :default-active="this.$route.path"
      class="el-menu-vertical-demo"
      :collapse="this.$store.state.isFold"
      background-color="#718093"
      text-color="#fff"
      router
      active-text-color="#ffd04b">
      <el-menu-item index="/main">
          <i class="el-icon-s-home"></i>
          <span slot="title">首页</span>
      </el-menu-item>
      <el-submenu index="/student">
        <template slot="title">
              <i class="el-icon-menu"></i>
              <span>学员管理</span>
        </template>
        <el-menu-item index="/student/list">
            <i class="el-icon-menu"></i>
            <span slot="title">学员列表</span>
        </el-menu-item>
      </el-submenu>
      <el-submenu index="2">
        <template slot="title">
              <i class="el-icon-menu"></i>
              <span>教练管理</span>
        </template>
        <el-menu-item index="2">
            <i class="el-icon-menu"></i>
            <span slot="title">教练列表</span>
        </el-menu-item>
      </el-submenu>
      <el-submenu index="3">
        <template slot="title">
              <i class="el-icon-menu"></i>
              <span>车辆管理</span>
        </template>
        <el-menu-item index="2">
            <i class="el-icon-menu"></i>
            <span slot="title">车辆列表</span>
        </el-menu-item>
      </el-submenu>
      <el-submenu index="4">
        <template slot="title">
              <i class="el-icon-menu"></i>
              <span>公告管理</span>
        </template>
        <el-menu-item index="2">
            <i class="el-icon-menu"></i>
            <span slot="title">公告列表</span>
        </el-menu-item>
      </el-submenu>
      <el-submenu index="5">
        <template slot="title">
              <i class="el-icon-menu"></i>
              <span>驾校简介</span>
        </template>
        <el-menu-item index="2">
            <i class="el-icon-menu"></i>
            <span slot="title">简介详情</span>
        </el-menu-item>
        <el-menu-item index="2">
            <i class="el-icon-menu"></i>
            <span slot="title">简介详情</span>
        </el-menu-item>
      </el-submenu>
      <el-submenu index="6">
        <template slot="title">
              <i class="el-icon-menu"></i>
              <span>个人中心</span>
        </template>
        <el-menu-item index="2">
            <i class="el-icon-menu"></i>
            <span slot="title">个人信息</span>
        </el-menu-item>
        <el-menu-item index="3">
            <i class="el-icon-menu"></i>
            <span slot="title">修改密码</span>
        </el-menu-item>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
export default {
    name:'SideBar',
}
</script>

<style>
  .el-menu-vertical-demo:not(.el-menu--collapse){
    width: 210px;
  }
  .el-menu-vertical-demo{
    height: 100vh;
    background-color: rgb(40, 44, 65);
  }
  .el-menu-item{
    height: 60px;
  }
  .el-submenu__title:hover,.el-menu-item:hover,.el-submenu >.el-menu-item:hover{

    color: rgb(44, 144, 237) !important;
    background-color:white !important;
  }
  .el-menu-item.is-active,.el-submenu .el-menu-item.is-active {
        background-color:rgb(44, 144, 237) !important;
  }
  
</style>