<template>
    <div class="vessel">
        <!-- 表格上方操作条 -->
        <div class="operation">
          <el-button type="danger" icon="el-icon-delete" :disabled="deletes.length==0"  size="small"
          @click="deleteList" style="height:32px; margin-left:20px">批量删除记录</el-button>
          <div class="right">
            <el-input v-model.lazy="keyWord" size="small" style="width:170px" clearable
            placeholder="姓名/手机号" maxlength="11" prefix-icon="el-icon-search" @clear="clearKeyWord">
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
                @click="handleEdit(scope.row)">查看排班情况</el-button>
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
          class="page"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[5, 10]"
          :page-size="size"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
        <!-- dialog -->
        <el-dialog :visible.sync="isShow" fullscreen
          center :title="title">
            <div class="menu">
              <div class="left">
                <span>当前月份：</span>
                <el-date-picker
                  v-model="date"
                  @change="dateChange"
                  type="month"
                  size="mini"
                  value-format="timestamp"
                  >
                </el-date-picker>
              </div>
              <div class="right_item" v-show="isFirst">
                <div>
                  <span>默认教学类型：</span>
                    <el-select v-model="type" @change="reset"
                      placeholder="请选择" size="mini" style="width:120px;">
                      <el-option v-for="item in typeList" :key="item" :value="item">
                      </el-option>
                    </el-select>
                </div>
                <div style="margin-left:50px">
                    <span>默认教学科目：</span>
                    <el-select v-model="subject" @change="reset" 
                      placeholder="请选择" size="mini" style="width:120px;">
                      <el-option v-for="item in subjectList" :key="item.value" 
                      :value="item.value" :label="item.label">
                      </el-option>
                    </el-select>
                </div>
                <div style="margin-left:50px">
                    <span>默认时间段：</span>
                    <el-select v-model="interval" @change="timeOtionChange" 
                      placeholder="请选择" size="mini" style="width:150px;">
                      <el-option v-for="item in timeOption" :key="item.value" 
                      :value="item.value" :label="item.label">
                      </el-option>
                    </el-select>
                </div>
                <div style="margin-left:50px">
                    <span>默认时间段人数：</span>
                    <el-input-number v-model="num" :min="1" @change="reset"
                    :max="10" size="mini" label="描述文字"></el-input-number>
                </div>
              </div>
            </div>
            <div class="list" v-show="dataList.length" v-loading="loading"> 
              <div class="item" v-for="(item,index) in dataList" :key="index">
                  <div>日期：{{item.date}}&nbsp;&nbsp;</div>
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
                  <div>
                    <el-button type="primary" @click="openInterval(item,index)" 
                    size="mini">查看时间段和人数</el-button>
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
        <!-- 查看时间段及人数弹窗 -->
        <el-dialog :visible.sync="showInterval" 
          center top="40px" width="50%" :title="subtitle">
          <div class="times">
            <div>
              <div class="tiem_item" v-for="(item,index) in intervalList" :key="index">
                <div>
                  <span>时间段：</span>
                  <el-time-picker
                    is-range
                    size="small"
                    :clearable="false"
                    format="HH:mm"
                    value-format="HH:mm"
                    v-model="item.interval"
                    range-separator="至"
                    start-placeholder="开始时间"
                    end-placeholder="结束时间"
                    placeholder="选择时间范围">
                  </el-time-picker>
                </div>
                <div>
                  <span>人数：</span>
                  <el-input-number v-model="item.number" :min="0" 
                      :max="10" size="small" label="描述文字"></el-input-number>
                </div>
                <div>
                  <el-button type="danger" @click="removeInterval(index)" 
                      size="small">删除</el-button>
                </div>
              </div>
            </div>
            <div class="bottom">
              <div>
                <div>
                <el-button type="primary" @click="addInterval" 
                    size="small">添加新的时间段</el-button>
                </div>
                <div>
                  <el-button type="success" @click="saveInterval" 
                      size="small" style="width:110px;">保 存</el-button>
                </div>
              </div>
              <div style="font-size: 12px;color:gray">
              *提示：对话框里的所有操作需保存后才生效
              </div>
            </div>
            
          </div>
        </el-dialog>
    </div>
</template>
  
<script>
  export default {
  name:'Arrange',
  data(){
      return{
        dataList:[],//排班生成数组
        coachList:null,//教练列表
        deletes:[],//批量删除数组
        coachID:0,
        date:'',//月份
        type:'C1',
        subject:0,  
        keyWord:null,
        title:'',
        subtitle:'',
        currentPage: 1,
        size:5,
        total:0,
        value1:['08:00','09:00'],
        isShow:false,//第一层弹窗
        showInterval:false,//第二层弹窗
        loading:true,//加载
        isFirst:true,// 标识是否为第一次添加排班记录
        interval:0,//默认时间段选项
        num:4,//默认人数
        timeOption:[{label:'选项一：每天分为5个时段，从早上8点到下午18点，每个时段2个小时',value:0},
                  {label:'选项二：每天分为10个时段，从早上8点到下午18点，每个时段1个小时',value:1}],
        typeList:['C1','C2','C3','C4','C5','C6','D','E','F'],
        subjectList:[{label:'科目二',value:0},{label:'科目三',value:1}],
        timeList:null,//默认时间段数组
        intervalList:null,//辅助时间段数组
        i:0//辅助下标
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
    // 关键词清空时间
    clearKeyWord(){
      this.loading=true
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
          this.type='C1'
          this.subject=0
          this.num=4
          this.interval=0
          this.timeList=[['08:00','10:00'],['10:00','12:00'],['12:00','14:00'],['14:00','16:00'],
                        ['16:00','18:00']]
          this.loading=true
          this.coachID=row.id
          this.getArrange()
          this.title=row.name+'的排班情况'
          this.isShow=true
    },
    //生成排班表
    edit(){
      if(this.isFirst){
        this.dataList=[]
        this.title='编辑排班'
        let date = new Date(this.date)
        let year = date.getFullYear()
        let month = date.getMonth()+1
        let days = new Date(year,month,0).getDate()
        month=month<10?'0'+month:month
        let list=[]
        this.timeList.forEach((item)=>{
          list.push({
            interval:item,
            number:this.num
          })
        })
        for(let i=1;i<=days;i++){
          let day=i<10?'0'+i:i
          this.dataList.push({
            c_id:this.coachID,
            date:`${year}-${month}-${day}`,
            work:1,
            type:this.type,
            subject:this.subject,
            list:JSON.parse(JSON.stringify(list)) 
          })
        }
        this.isFirst=true
      }
    },
    //获取某教练当月排班表
    async getArrange(){
      let res = await this.axios.get('/arrange/list',{
        params:{id:this.coachID,date:this.formatDate(this.date)}}
      )
      if(res.code===2000){
        this.dataList=[]
        res.data.forEach((item)=>{
          let timelist=[]
          for(let i of item.list){
            timelist.unshift({
              id:i.id,
              a_id:i.a_id,
              c_id:item.c_id,
              interval:[i.start,i.end],
              number:i.number
            })
          }
          this.dataList.push({
            id:item.id,
            c_id:item.c_id,
            date:item.date,
            work:item.work,
            type:item.type,
            subject:item.subject,
            list:JSON.parse(JSON.stringify(timelist))
          })
      })
        if(res.data.length)
          this.isFirst=false
        else
          this.isFirst=true
      }
      this.loading=false
    },
    //保存排班表
    saveArrange(){
      let list=[]
      this.dataList.forEach((item)=>{
          let timelist=[]
          for(let i of item.list){
            timelist.push({
              c_id:item.c_id,
              start:i.interval[0],
              end:i.interval[1],
              number:i.number
            })
          }
          list.push({
            c_id:item.c_id,
            date:item.date,
            work:item.work,
            type:item.type,
            subject:item.subject,
            list:JSON.parse(JSON.stringify(timelist))
          })
      })
      this.$confirm('确定保存吗?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                  }).then(async ()=>{
                    let res = await this.axios.post('/arrange/saveArrange',list)
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
              let res = await this.axios.delete('/arrange/deleteArrange',
              {data:this.dataList})
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
    // 删除全部记录
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
    },
    // 驾照类型、科目改变、人数改变
    reset(){
      if(this.dataList.length)
          this.edit()
    },
    // 日期格式函数
    formatDate(val){
        let date = new Date(val)
        let year = date.getFullYear()
        let month = date.getMonth()+1<10?`0${date.getMonth()+1}`:date.getMonth()+1
        let day = date.getDate()<10?'0'+date.getDate():date.getDate() 
        return `${year}-${month}-${day}`
    },
    // 默认时间段选项改变事件
    timeOtionChange(){
      if(this.interval===1)
        this.timeList=[['08:00','09:00'],['09:00','10:00'],['10:00','11:00'],['11:00','12:00'],
                        ['12:00','13:00'],['13:00','14:00'],['14:00','15:00'],['15:00','16:00'],
                        ['16:00','17:00'],['17:00','18:00']
                      ]
      else
        this.timeList=[['08:00','10:00'],['10:00','12:00'],['12:00','14:00'],['14:00','16:00'],
                        ['16:00','18:00']]

      if(this.dataList.length)
          this.edit()
    },
    // 查看某天时间段和人数
    openInterval(item,index){
      this.subtitle=item.date
      this.i=index
      this.intervalList=JSON.parse(JSON.stringify(item.list))
      this.showInterval=true
    },
    // 删除某个时间段
    removeInterval(index){
      if(this.intervalList.length===1)
        return this.$message.error('至少保留一个时间段！')
      this.intervalList.splice(index,1)
    },
    // 添加时段
    addInterval(){
      if(this.intervalList.length>15)
        return this.$message.error('最多只能添加15个时间段！')
      this.intervalList.push({
        interval:['00:00','12:00'],
        number:this.num
      })
    },
    // 保存时段
    saveInterval(){
      this.$confirm('确定保存吗?', '提示', 
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async ()=>{
        this.dataList[this.i].list=this.intervalList
        if(!this.isFirst){
          let timelist=[]
          this.intervalList.forEach((item)=>{
              timelist.push({
                id:item.id,
                a_id:this.intervalList[0].a_id,
                c_id:this.intervalList[0].c_id,
                start:item.interval[0],
                end:item.interval[1],
                number:item.number
              })
          })
          let res = await this.axios.post('/arrange/updateInterval',timelist)
            if(res.code===2000)
              this.$message.success(res.data)
            else
              this.$message.error(res.data)
        }
        else
          this.$message.success('保存成功！')
      }).catch(()=>{
        return
      })
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
.menu{
  width: 100%;
  display: flex;
  justify-content: flex-start;
  margin-bottom: 10px;
}
.right_item{
  display: flex;
  margin-left: 50px;
}
.list{
  display: flex;
  margin: 0 auto;
  justify-content: center;
  flex-wrap: wrap;
}
.item{
    width: 160px;
    height: 160px;
    margin: 6px 7px;
    box-sizing: border-box;
    padding: 5px;
    display: flex;
    flex-direction: column;
    border: 1px solid gray;
}
.item>div{
  height: 50px;
  width: 100%;
  text-align: center;
  margin: 3px 0px;
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
.times{
  width: 100%;
  min-height: 500px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  margin: 0 auto;
  text-align: center;
}
.tiem_item{
  width: 100%;
  display: flex;
  margin: 10px 0px;
  justify-content: space-around;
}
.bottom{
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
}
.bottom>div:first-child{
  width: 50%;
  display: flex;
  margin: 20px 0px;
  justify-content: space-around;
}
.page{
    position: absolute;
    bottom: 10px;
    left: 50%;
    transform: translateX(-50%);
}
</style>