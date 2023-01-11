<template>
    <div class="vessel">
        <!-- 操作条 -->
        <div class="operation">
          <div class="right">
            <el-select v-model="status" placeholder="状态" size="small" clearable style="width:170px">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option> 
            </el-select>
            <el-input v-model.lazy="keyWord" size="small" style="width:170px" clearable
            placeholder="姓名/手机号" maxlength="11" prefix-icon="el-icon-search" @clear="clearKeyWord">
            </el-input>
            <el-button type="primary" size="small" icon="el-icon-search" 
            clearable @click="search">搜素</el-button>
          </div>
        </div>
        <!-- 表格 -->
        <el-table :data="dataList" border  style="width: 100%" v-loading="loading"
            :header-cell-style="{background:'#f4f3f9',color:'#606266',fontWeight:'bold'}"
            >
          <el-table-column prop="name" label="姓名" align="center"></el-table-column>
          <el-table-column prop="phone" label="手机号码" align="center" ></el-table-column>
          <el-table-column prop="date" label="申请时间" align="center">
          </el-table-column>
          <el-table-column prop="endTime" label="申请更新科目" align="center">
            <template slot-scope="scope">
              <el-tag type="success" v-if="scope.row.subject==='a'">科目一</el-tag>
              <el-tag type="success" v-else-if="scope.row.subject==='b'">科目二</el-tag>
              <el-tag type="success" v-else-if="scope.row.subject==='c'">科目三</el-tag>
              <el-tag type="success" v-else>科目四</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="审批状态" align="center" show-overflow-tooltip>
            <template slot-scope="scope">
              <el-tag type="info" v-if="scope.row.status===0">待处理</el-tag>
              <el-tag type="success" v-else-if="scope.row.status===1">
                已通过</el-tag>
              <el-tag type="danger" v-else>已拒绝</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="图片材料" align="center">
            <template slot-scope="scope">
                <el-button type="primary" icon="el-icon-view" size="small" 
                @click="lookImages(scope.row.urls)">查看</el-button>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="200px">
            <template slot-scope="scope" >
              <el-button
                size="small"
                type="success"
                :disabled="scope.row.status!==0"
                icon="el-icon-success"
                @click="check(scope.row,1)">通过</el-button>
                <el-button
                size="small"
                :disabled="scope.row.status!==0"
                type="danger"
                icon="el-icon-error"
                @click="check(scope.row,2)">拒绝</el-button>
            </template>
          </el-table-column>  
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
        <!-- 查看图片 -->
        <el-dialog title="查看图片" :visible.sync="isShow"  width="60%">
          <div class="imageList" v-if="images.length">
            <div v-for="(url,index) in images" :key="index">
              <el-image :src="url" fit="contain" :preview-src-list="[url]" style="width:400px; height: 250px;">
              </el-image>
            </div>
          </div>
          <el-empty description="暂无图片" v-else></el-empty>
          <div slot="footer">
            <el-button type="primary" @click="isShow=false">关闭</el-button>
          </div>
        </el-dialog>
    </div>
</template>
  
<script>

  export default {
  name:'Apply',
  data(){
      return{
        options:[{label:'待审批',value:0},{label:'已通过',value:1},{label:'已拒绝',value:2}],
        status:null,
        dataList:[],
        actions:[],
        images:[],
        keyWord:null,
        currentPage: 1,
        size:5,
        total:0,
        loading:true,
        isShow:false
    }
  },
  created(){
    this.getList()
  },
  methods:{
    // 获取申请列表
   async getList(){
        let res = await this.axios.get('/student/getList',{
            params:{
              keyWord:this.keyWord,
              status:this.status,
              size:this.size,
              currentPage:this.currentPage
            }})
        if(res.code===2000){
          this.dataList =res.data.list
          this.total=res.data.total
        }
        this.loading=false
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
    // 审批
    async check(item,status){
      item.status=status
      let apply = JSON.parse(JSON.stringify(item))
      let res = await this.axios.put('/student/check',apply)
      if(res.code===2000){
        this.$message.success(res.data)
        this.getList()
      }
      else
        this.$message.error(res.data)
    },
    lookImages(item){
      this.images=JSON.parse(JSON.stringify(item))
      this.isShow=true
    }
  },
  watch:{
    status(){
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
    justify-content: flex-end ;
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
.imageList{
  display: flex;
  width: 100%;
  height: 300px;
  justify-content: space-around;
}
</style>