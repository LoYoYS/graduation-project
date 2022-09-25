<template>
    <el-container>
      <el-aside width="auto">
        <SideBar/>
      </el-aside>
      <el-container class="main-container">
        <el-header>
          <NavBar/>
        </el-header>
        <el-main>
            <router-view></router-view>
        </el-main>
      </el-container>
  </el-container>
</template>

<script>
import NavBar from './NavBar.vue';
import SideBar from './SideBar.vue';
    export default {
    components: { NavBar, SideBar},
}
</script>

<style scoped>
  .main-container {
  transition: margin-left 0.45s;
  min-height: 100vh;
}
  .el-header{
    color: #333;
    text-align: center;
    height: 90px !important;
    line-height: 56px;
    padding: 0;
  }
  
  .el-main {
    color: #333;
    text-align: center;
    line-height: 160px;
    padding: 10px;
  }
</style>