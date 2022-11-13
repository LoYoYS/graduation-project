<template>
    <el-container>
      <el-aside width="auto">
        <SideBar/>
      </el-aside>
      <el-container class="main-container" :class='this.$store.state.isFold?"isFold":""'>
        <el-header>
          <NavBar/>
        </el-header>
        <el-main>
          <div class="container">
            <Title v-show="this.$route.name!='首页'"></Title>
            <router-view></router-view>
          </div>
        </el-main>
      </el-container>
  </el-container>
</template>

<script>
import NavBar from './NavBar.vue';
import SideBar from './SideBar.vue';
import Title from '../components/Title.vue';
    export default {
    components: { NavBar, SideBar,Title},
}
</script>

<style scoped>
  .el-header{
    padding: 0;
    height: 90px !important;
  }
  .main-container {
    transition: margin-left 0.45s;
    min-height: 100vh;
    margin-left: 210px;
    text-align: center;
  }
  .isFold{
    margin-left: 64px;
  }
  .container{
    position: relative;
    min-height: 590px;
    padding: 20px;
    text-align: center;
    box-shadow: 1px 1px 5px rgb(236, 236, 236);
  }
</style>