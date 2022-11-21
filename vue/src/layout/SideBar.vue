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
      <div class="title">
        <el-image 
          style="width: 25px; height: 25px"
          src="https://pic.imgdb.cn/item/63799e0016f2c2beb1cfbdf9.png" 
          :preview-src-list="['https://pic.imgdb.cn/item/63799e0016f2c2beb1cfbdf9.png']">
        </el-image>
        <div v-if="!this.$store.state.isFold">简单约练小程序</div>
      </div>
      <el-menu-item index="/">
          <i class="el-icon-s-home"></i>
          <span slot="title">首页</span>
      </el-menu-item>
      <el-submenu index="/student">
        <template slot="title">
              <i class="iconfont iconddd icon_class"></i>
              <span>学员管理</span>
        </template>
        <el-menu-item index="/student/list">
            <i class="iconfont icon24gf-userGroup2 icon_class"></i>
            <span slot="title">学员列表</span>
        </el-menu-item>
        <el-menu-item index="/student/save">
            <i class="iconfont icontianjialianxiren icon_class" style="font-size:22px"></i>
            <span slot="title">添加学员</span>
        </el-menu-item>
      </el-submenu>
      <el-submenu index="/coach">
        <template slot="title">
              <i class="el-icon-s-custom"></i>
              <span>教练管理</span>
        </template>
        <el-menu-item index="/coach/list">
            <i class="el-icon-s-data"></i>
            <span slot="title">教练列表</span>
        </el-menu-item>
        <el-menu-item index="/coach/arrange">
            <i class="el-icon-date"></i>
            <span slot="title">排班管理</span>
        </el-menu-item>
        <el-menu-item index="/coach/vacate">
            <i class="el-icon-s-claim"></i>
            <span slot="title">请假管理</span>
        </el-menu-item>
      </el-submenu>
      <el-submenu index="/car">
        <template slot="title">
              <i class="iconfont iconcheliangguanli icon_class"></i>
              <span>车辆管理</span>
        </template>
        <el-menu-item index="/car/list">
            <i class="iconfont icon-cheliang icon_class"></i>
            <span slot="title">车辆列表</span>
        </el-menu-item>
      </el-submenu>
      <el-submenu index="/notice-introduce">
        <template slot="title">
              <i class="el-icon-s-data"></i>
              <span>公告管理</span>
        </template>
        <el-menu-item index="/notice-introduce/notices">
            <i class="el-icon-s-order"></i>
            <span slot="title">公告列表</span>
        </el-menu-item>
        <el-menu-item index="/notice-introduce/notice">
            <i class="iconfont iconbianjiwenjian icon_class"></i>
            <span slot="title">编辑、发布公告</span>
        </el-menu-item>
      </el-submenu>
      <el-menu-item index="/user">
            <i class="iconfont icon24gf-userGroup2 icon_class"></i>
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
    name:'SideBar'
}
</script>

<style scoped>
  .el-menu-vertical-demo:not(.el-menu--collapse){
    width: 210px;
  }
  .title{
    width: 90%;
    height: 30px;
    font-size: 18px;
    letter-spacing: 2px;
    font-weight: bold;
    margin: 20px auto;
    color: white;
    display: flex;
    justify-content: space-evenly;
    align-items: center;
  }
  .title div{
    margin-top: 5px;
  }
  .el-menu-vertical-demo{
    position: fixed;
    top: 0;
    left: 0;
    bottom: 0;
    overflow-y: auto;
    overflow-x: hidden;
  }
  .el-menu-item.is-active{
    background-image: linear-gradient(rgb(0, 214, 252),rgb(61,81,252)) !important;
  }
  .icon_class{
    margin: 0px 5px;
  } 
</style>