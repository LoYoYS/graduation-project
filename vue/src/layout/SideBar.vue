<template>
  <div class="content">
      <el-menu
      :default-active="this.$route.path"
      class="el-menu-vertical-demo"
      :collapse="this.$store.state.isFold"
      background-color="#302f4b"
      text-color="#fff"
      router
      active-text-color="#ffd04b">
      <el-menu-item index="/">
          <i class="el-icon-s-home"></i>
          <span slot="title">首页</span>
      </el-menu-item>
      <el-submenu index="/student">
        <template slot="title">
              <i class="el-icon-s-tools"></i>
              <span>学员管理</span>
        </template>
        <el-menu-item index="/student/list">
            <i class="el-icon-menu"></i>
            <span slot="title">学员列表</span>
        </el-menu-item>
        <el-menu-item index="/student/save">
            <i class="el-icon-circle-plus"></i>
            <span slot="title">添加学员</span>
        </el-menu-item>
      </el-submenu>
      <el-submenu index="/train">
        <template slot="title">
              <i class="el-icon-s-management"></i>
              <span>教练管理</span>
        </template>
        <el-menu-item index="/train/list">
            <i class="el-icon-s-data"></i>
            <span slot="title">教练列表</span>
        </el-menu-item>
      </el-submenu>
      <el-submenu index="/car">
        <template slot="title">
              <i class="el-icon-s-help"></i>
              <span>车辆管理</span>
        </template>
        <el-menu-item index="/car/list">
            <i class="el-icon-s-data"></i>
            <span slot="title">车辆列表</span>
        </el-menu-item>
      </el-submenu>
      <el-submenu index="/notice-introduce">
        <template slot="title">
              <i class="el-icon-s-data"></i>
              <span>公告、简介</span>
        </template>
        <el-menu-item index="/notice-introduce/notices">
            <i class="el-icon-message-solid"></i>
            <span slot="title">公告列表</span>
        </el-menu-item>
        <el-menu-item index="/notice-introduce/introduce">
            <i class="el-icon-s-order"></i>
            <span slot="title">驾校简介</span>
        </el-menu-item>
      </el-submenu>
      <el-menu-item index="/user">
            <i class="el-icon-info"></i>
            <span slot="title">用户列表</span>
      </el-menu-item>
      <el-menu-item index="/my">
          <i class="el-icon-user-solid"></i>
          <span slot="title">个人中心</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
export default {
    name:'SideBar',
}
</script>

<style scoped>
  .el-menu-vertical-demo:not(.el-menu--collapse){
    width: 210px;
  }
  .el-menu-vertical-demo{
    position: fixed;
    top: 0;
    left: 0;
    bottom: 0;
  }
/*   .el-submenu__title:hover,.el-menu-item:hover{
    color: rgb(44, 144, 237) !important;
  } */
  .el-menu-item.is-active{
        background-image:linear-gradient(45deg, #0081ff, #1cbbb4) !important;
  }
/*   i:hover{
    color: rgb(44, 144, 237) !important;
  }
  .el-menu-item.is-active >i{
    color: #ffd04b !important;
  }   */  
</style>