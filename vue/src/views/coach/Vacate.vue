<template>
    <div class="vessel">
        <!-- 操作条 -->
        <div class="operation">
          <div class="left">
            <el-button type="success" icon="el-icon-success" :disabled="actions.length==0" 
            size="small" @click="accessAll">一键通过</el-button>
            <el-button type="danger" size="small" icon="el-icon-error" @click="refuseAll" 
            :disabled="actions.length==0">一键拒绝</el-button>
          </div>
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
            placeholder="姓名/手机号" maxlength="11" prefix-icon="el-icon-search">
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
          <el-table-column type="selection" align="center"></el-table-column>
          <el-table-column prop="name" label="姓名" align="center" width="150px"></el-table-column>
          <el-table-column prop="phone" label="手机号" align="center" width="150px"></el-table-column>
          <el-table-column prop="startTime" label="开始时间" align="center" width="200px">
          </el-table-column>
          <el-table-column prop="endTime" label="结束时间" align="center" width="200px">
          </el-table-column>
          <el-table-column prop="content" label="请假事由" align="center" show-overflow-tooltip 
          width="auto"></el-table-column>
          <el-table-column label="请假类型" align="center" width="100px">
            <template slot-scope="scope">
              <el-tag type="success" v-if="!scope.row.type">事假</el-tag>
              <el-tag type="warning" v-else>病假</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="状态" align="center" show-overflow-tooltip width="150px">
            <template slot-scope="scope">
              <el-tag type="info" v-if="!scope.row.status">待审批</el-tag>
              <el-tag type="success" v-else-if="scope.row.status===1||scope.row.status===3">
                已通过</el-tag>
              <el-tag type="danger" v-else>已拒绝</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="200px">
            <template slot-scope="scope" v-if="scope.row.status===0">
              <el-button
                size="small"
                type="success"
                icon="el-icon-success"
                @click="check(scope.row.id,1)">通过</el-button>
                <el-button
                size="small"
                type="danger"
                icon="el-icon-error"
                @click="check(scope.row.id,-1)">拒绝</el-button>
            </template>
          </el-table-column>  
        </el-table>
        <!-- 分页 -->
        <el-pagination
          background
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
  name:'Vacate',
  data(){
      return{
        options:[{label:'待审批',value:0},{label:'已通过',value:1},{label:'已拒绝',value:-1}],
        status:null,
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
    // 获取请假列表
   async getList(){
        let res = await this.axios.get('/api/vacate/list',{
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
    async check(id,status){
      let form = new FormData()
      form.append('id',id)
      form.append('status',status)
      let res = await this.axios.put('/api/vacate/check',form)
      if(res.code===2000){
        this.$message.success(res.data)
        this.getList()
      }
      else
        this.$message.error(res.data)
    },
    // 一键通过
    accessAll(){
      this.$confirm('确定执行操作吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async ()=>{
        let res = await this.axios.put('/api/vacate/accessAll',this.actions)
        if(res.code===2000){
          this.$message.success(res.data)
          this.getList()
        }
        else
         this.$message.error(res.data)
      }).catch(()=>{return})
    },
    // 一键拒绝
    refuseAll(){
      this.$confirm('确定执行操作吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async ()=>{
        let res = await this.axios.put('/api/vacate/refuseAll',this.actions)
        if(res.code===2000){
          this.$message.success(res.data)
          this.getList()
        }
        else
         this.$message.error(res.data)
      }).catch(()=>{return})
    },
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
    margin: 10px auto;
}
.operation{
    display: flex;
    justify-content: space-between;
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
</style>