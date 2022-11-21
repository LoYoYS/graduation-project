<template>
    <div class="vessel">
        <!-- 操作条 -->
        <div class="operation">
          <el-button type="danger" icon="el-icon-delete" :disabled="deletes.length==0"  size="small"
          @click="deleteList" style="height:32px; margin-left:20px">批量删除记录</el-button>
          <div class="right">
            <el-input v-model.lazy="keyWord" size="small" style="width:170px" clearable
            placeholder="姓名/手机号" maxlength="11" prefix-icon="el-icon-search">
            </el-input>
            <el-button type="primary" size="small" icon="el-icon-search"  @click="search">
              搜素</el-button>
          </div>
        </div>
        <!-- 表格 -->
        <el-table :data="coachList" border  style="width: 100%" v-loading="loading"
            :header-cell-style="{background:'#f4f3f9',color:'#606266',fontWeight:'bold'}"
            @selection-change="seletChange"
            >
          <el-table-column type="selection" align="center"></el-table-column>
          <el-table-column prop="name" label="姓名" align="center" width="150px"></el-table-column>
          <el-table-column prop="sex" label="性别" align="center" width="100px"></el-table-column>
          <el-table-column prop="id_number" label="身份证号" align="center" width="auto"></el-table-column>
          <el-table-column prop="phone" label="电话号码" align="center" width="auto"></el-table-column>
          <el-table-column label="操作" align="center" width="auto">
            <template slot-scope="scope">
              <el-button
                size="small"
                type="primary"
                icon="el-icon-s-order"
                @click="handleEdit(scope.row.id)">查看排班记录</el-button>
                <el-button
                size="small"
                type="danger"
                icon="el-icon-delete"
                @click="handleDelete(scope.row.id)">删除排班记录</el-button>
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
        <!-- dialog -->
        <el-dialog :visible.sync="isShow"
          center top="40px" width="90%">
            <div slot="title" class="dialog_title">
              <span style="font-size:25px">{{title}}</span>
              <div>
                <span>选择时间：</span>
                <el-date-picker
                  v-model="date"
                  @change="dateChange"
                  type="month"
                  size="small"
                  value-format="yyyy-MM-DD">
                </el-date-picker>
              </div>
            </div>
            <div class="list" v-show="dataList.length" v-loading="loading"> 
              <div class="item" v-for="(item,index) in dataList" :key="index">
                  <div>日期：{{item.date}}</div>
                  <div>
                    <span>出勤：</span>
                    <el-switch
                      v-model="item.work"
                      :active-value="1"
                      :inactive-value="0"
                      active-text="是"
                      inactive-text="否"
                      @change="valueChange(item)">
                    </el-switch>
                  </div>
                  <div>
                    <span>驾照：</span>
                    <el-select v-model="item.type" @change="valueChange(item)"
                      placeholder="请选择" size="mini" style="width:90px;">
                      <el-option v-for="item in typeList" :key="item" :value="item">
                      </el-option>
                    </el-select>
                  </div>
                  <div>
                    <span>科目：</span>
                    <el-select v-model="item.subject" @change="valueChange(item)" 
                      placeholder="请选择" size="mini" style="width:90px;">
                      <el-option v-for="item in subjectList" :key="item.value" 
                      :value="item.value" :label="item.label">
                      </el-option>
                    </el-select>
                  </div>
              </div>
              <div class="btn">
                <el-button type="primary" icon="el-icon-edit" @click="saveArrange"
                v-show="isFirst" style="width:150px;height: 50px;">保 存</el-button>
                <el-button type="danger" icon="el-icon-delete" @click="deleteArrange"
                v-show="!isFirst" style="width:160px;height: 50px; margin:0">删 除</el-button>
              </div>
            </div>
            <div v-show="!dataList.length"  v-loading="loading">
              <el-empty description="本月未排班" style="height:500px">
                <el-button type="primary" @click="edit" size="small">点击生成当前月排班表</el-button>
              </el-empty>
            </div> 
        </el-dialog>
    </div>
</template>
  
<script>
  export default {
  name:'Arrange',
  data(){
      return{
        typeList:['C1','C2','C3','C4','C5','C6','D','E','F'],
        subjectList:[{label:'科目二',value:0},{label:'科目三',value:1}],
        dataList:[],
        coachList:null,
        deletes:[],
        coachID:0,
        date:'',  
        keyWord:null,
        title:'',
        currentPage: 1,
        size:5,
        total:0,
        isShow:false,
        loading:true,
        // 标识是否为第一次添加排班记录
        isFirst:true
    }
  },
  created(){
    this.getCoachList()
  },
  methods:{
    // 获取教练列表
    getCoachList(){
          this.axios.get('/coach/list',{
            params:{
              keyWord:this.keyWord,
              status:this.status,
              size:this.size,
              currentPage:this.currentPage
            }
          }).then((res)=>{
            this.coachList=res.data.list
            this.total=res.data.total
            this.loading=false
          }) 
    },
    // 模糊搜索
    search(){
      if(this.keyWord==''||this.keyWord==null)
            return this.$message.error("请输入关键字后再搜索")
          this.loading=true  
          this.currentPage=1
          this.getCoachList()
    },
    // 显示数量改变
    handleSizeChange(index){
      this.loading=true
      this.size = index
      this.getCoachList()
    },
    // 页码改变
    handleCurrentChange(index){
      this.loading=true
      this.currentPage=index
      this.getCoachList()
    },
    // 多选教练
    seletChange(s){
      this.deletes=[]
      s.forEach((item) => {
      this.deletes.push(item.id)
      })
    },
    // 打开查看排班面板
    handleEdit(row){
          this.date=new Date()
          this.loading=true
          this.coachID=row
          this.getArrange()
          this.title='排班情况'
          this.isShow=true
    },
    //生成排班表
    edit(){
      if(!this.dataList.length){
        this.dataList=[]
        this.title='生成排班表'
        let date = new Date(this.date)
        let year = date.getFullYear()
        let month = date.getMonth()+1
        let days = new Date(year,month,0).getDate()
        month=month<10?'0'+month:month
        for(let i=1;i<=days;i++){
          let day=i<10?'0'+i:i
          this.dataList.push({
            c_id:this.coachID,
            date:`${year}-${month}-${day}`,
            work:1,
            type:'C1',
            subject:0
          })
        }
        this.isFirst=true
      }
    },
    //获取某教练当月排班表
    async getArrange(){
      let res = await this.axios.get('/arrange/list',{
        params:{id:this.coachID,date:this.date}}
      )
      if(res.code===2000){
        this.dataList=res.data
        if(res.data.length)
          this.isFirst=false
      }
      this.loading=false
    },
    //保存排班表
    saveArrange(){
      this.$confirm('确定保存吗?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                  }).then(async ()=>{
                    let res = await this.axios.post('/arrange/saveArrange',this.dataList)
                    if(res.code===2000){
                      this.$message.success(res.data)
                      this.getArrange()
                    }
                    else
                      this.$message.error(res.data)
                  }).catch(()=>{
                    return
                  })
    },
    // 删除某教练某月排班记录
    deleteArrange(){
      this.$confirm('确定删除当前教练本月的排班记录?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'error'
          }).then(async ()=>{
              let d = new Date(this.date)
              let res = await this.axios.delete('/arrange/deleteArrange',
              {params:{id:this.coachID,date:d.toLocaleDateString()}})
              if(res.code===2000){
                this.loading=true
                this.$message.success(res.data)
                this.getArrange()
              }
              else
                this.$message.error(res.data)
              }).catch(()=>{
                  return
              })
    },
    // 修改某一天
    async valueChange(e){
      if(this.isFirst)
        return
       let res = await this.axios.put('/arrange/updateArrange',e)
       if(res.code===2000)
          this.$message.success(res.data)         
        else
          this.$message.error(res.data)
    },
    //月份改变
    dateChange(){
      this.loading=true
      this.getArrange()
    },
    // 删除某个教练全部记录
    handleDelete(id){
      this.$confirm('确定删除当前教练的所有排班记录?', '提示', 
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(async ()=>{
        let res = await this.axios.delete(`/arrange/deleteArrangeAll?id=${id}`)
        if(res.code===2000)
          this.$message.success(res.data)
        else
          this.$message.error(res.data)
      }).catch(()=>{
        return
      })
    },
    // 批量删除
    deleteList(){
      this.$confirm('确定删除选中教练的全部排班记录?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'error'
        }).then(async ()=>{
          let res = await this.axios.delete('/arrange/deleteArrangeList',
          {data:this.deletes})
          if(res.code===2000)
            this.$message.success(res.data)
          else
            this.$message.error(res.data)
          this.getCoachList()
        }).catch(()=>{
          return
        })
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
    width: 300px;
    display: flex;
    justify-content: space-around;
    align-items: center;
}
.demo-ruleForm{
      text-align: left;
      width: 310px;
      margin: 0 auto;
}
.dialog_title{
  display: flex;
  justify-content: center;
  align-items: center;
}
.dialog_title>div{
  position: absolute;
  right: 10%;
}
.list{
  display: flex;
  margin: 0 auto;
  flex-wrap: wrap;
  perspective: 300px;
}
.item{
    width: 150px;
    height: 135px;
    margin: 6px 7px;
    box-sizing: border-box;
    padding: 5px;
    display: flex;
    flex-direction: column;
    border: 1px solid gray;
    transition: all 0.3s;
}
.item div{
  height: 25px;
  display: flex;
  align-items: center;
  margin: 3px 0;
}
.btn{
    width: 150px;
    height: 130px;
    box-sizing: border-box;
    padding: 10px 5px;
    margin-left: 6px;
    display: flex;
    flex-wrap: wrap;
    align-content: space-around;
    justify-content: center;
}
</style>