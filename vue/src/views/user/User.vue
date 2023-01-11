<template>
    <div class="vessel">
        <!-- 操作条 -->
        <div class="operation">
          <div class="right">
            <el-select v-model="type" placeholder="用户类型" size="small" clearable style="width:170px">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option> 
            </el-select>
            <el-input v-model.lazy="keyWord" size="small" style="width:170px" @clear="clearKeyWord"
            placeholder="昵称" maxlength="11" clearable prefix-icon="el-icon-search">
            </el-input>
            <el-button type="primary" size="small" icon="el-icon-search" 
            clearable @click="search">搜素</el-button>
          </div>
        </div>
        <!-- 表格 -->
        <el-table :data="dataList" border  style="width: 100%" v-loading="loading"
            :header-cell-style="{background:'#f4f3f9',color:'#606266',fontWeight:'bold'}"
            @selection-change="seletChange"
            >
          <el-table-column prop="url" label="用户头像" align="center" width="150px">
            <template slot-scope="scope">
              <el-image
              style="width: 80px; height: 80px;"
              :src="scope.row.url"
              :preview-src-list="[scope.row.url]">
                <div slot="error" style="width:60px;height:50px;margin:45px auto;">
                  暂无图片
                </div>
              </el-image>
            </template>
          </el-table-column>
          <el-table-column prop="nickName" label="昵称" align="center" width="auto"></el-table-column>
          <el-table-column prop="username" label="用户名" align="center" width="auto">
          </el-table-column>
          <el-table-column label="用户类型" align="center" width="auto">
            <template slot-scope="scope">
              <el-tag type="success" v-if="scope.row.role===1">学员</el-tag>
              <el-tag type="priamry" v-else-if="scope.row.role===-1">教练</el-tag>
              <el-tag type="info" v-else>游客</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="身份认证状态" align="center" show-overflow-tooltip width="150px">
            <template slot-scope="scope">
              <el-tag type="success" v-if="scope.row.a_id">已认证</el-tag>
              <el-tag type="danger" v-else>未认证</el-tag>
            </template>
          </el-table-column>
          <!-- <el-table-column label="操作" align="center" width="200px">
              <el-button
                size="small"
                type="primary"
                icon="el-icon-edit"
                @click="check">编辑</el-button>
          </el-table-column>   -->
        </el-table>
        <!-- 分页 -->
        <el-pagination
          background
          class="page"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[5, 10]"
          :page-size="size"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
    </div>
</template>
  
<script>
  export default {
  name:'User',
  data(){
      return{
        options:[{label:'学员',value:1},{label:'教练',value:-1},{label:'游客',value:0}],
        type:null,
        dataList:[],
        actions:[],
        keyWord:null,
        currentPage: 1,
        size:5,
        total:0,
        loading:true,
    }
  },
  created(){
    this.getList()
  },
  methods:{
    // 获取用户列表
   async getList(){
        let res = await this.axios.get('/user/list',{
            params:{
              keyWord:this.keyWord,
              type:this.type,
              size:this.size,
              currentPage:this.currentPage
            }})
        if(res.code===2000){
          this.dataList =res.data.list
          this.total=res.data.total
          this.loading=false
        }
    },
    // 模糊搜索
    search(){
          if(this.keyWord==''||this.keyWord==null)
            return this.$message.error("请输入关键字后再搜索")
          this.loading=true
          this.currentPage=1
          this.getList()
    },
    clearKeyWord(){
      this.loading=true
      this.getList()
    },
    // 显示数量改变
    handleSizeChange(index){
          this.size = index
          this.loading=true
          this.getList()
    },
    // 页码改变
    handleCurrentChange(index){
          this.currentPage=index
          this.loading=true
          this.getList()
    },
    // 多选
    seletChange(s){
      this.actions=[]
      s.forEach((item) => {
          if(item.status===0)
            this.actions.push(item.id)
      })
    },
    // 操作
    check(){
        this.$message.info("暂未开放！")
    },
  },
  watch:{
    type(){
      this.loading=true
      this.getList()
    }
  },
}
</script>
  
<style scoped>
.el-table{
    margin: 30px auto;
}
.operation{
    display: flex;
    justify-content: flex-end;
    width: 100%;
    height: 40px;
    margin-bottom: 15px;
}
.right{
    width: 450px;
    display: flex;
    justify-content: space-around;
    align-items: center;
}
.page{
    position: absolute;
    bottom: 10px;
    left: 50%;
    transform: translateX(-50%);
}
</style>