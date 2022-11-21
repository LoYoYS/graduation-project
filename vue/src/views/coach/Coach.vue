<template>
    <div class="vessel">
        <!-- 操作条 -->
        <div class="operation">
          <div class="left">
            <el-button type="danger" icon="el-icon-delete" :disabled="deleteList.length==0" 
            size="small" @click="deleteCoachList">批量删除</el-button>
            <el-button type="primary" size="small" icon="el-icon-plus" @click="SaveCoach">添加教练</el-button>
            <el-button type="primary" size="small" icon="el-icon-upload" @click="showImport=true">导入数据</el-button>
            <el-button type="primary" size="small" icon="el-icon-download" @click="exportData">导出数据</el-button>
            <el-button type="primary" size="small" icon="el-icon-download" @click="download">下载模板</el-button>
          </div>
          <div class="right">
            <el-select v-model="sex" placeholder="性别" clearable size="small" style="width:170px">
              <el-option
                v-for="item in options"
                :key="item"
                :label="item"
                :value="item">
              </el-option> 
            </el-select>
            <el-input v-model.lazy="keyWord" size="small" style="width:170px" clearable
            placeholder="姓名/手机号" maxlength="11" prefix-icon="el-icon-search">
            </el-input>
            <el-button type="primary" size="small" icon="el-icon-search"  @click="search">搜素</el-button>
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
          <el-table-column prop="id_number" label="身份证号" align="center" width="200px"></el-table-column>
          <el-table-column prop="phone" label="电话号码" align="center" width="auto"></el-table-column>
          <el-table-column prop="date" label="入职日期" align="center" width="auto"></el-table-column>
          <el-table-column prop="car" label="配车" align="center" width="auto">
            <template slot-scope="scope">
              <el-tag type="success" v-if="scope.row.car">{{scope.row.car}}</el-tag>
              <el-tag type="danger" v-else>暂未分配</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="isBindWx" label="绑定微信" align="center" width="auto">
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.isBindWx"
                active-color="#13ce66"
                :active-value="1"
                :inactive-value="0"
                @change="switchChange"
              >
              </el-switch>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="200px">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="primary"
                icon="el-icon-edit" 
                @click="handleEdit(scope.row)">编辑</el-button>
              <el-button
                size="mini"
                type="danger"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)">删除</el-button>
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
        <!-- 编辑框 -->
        <el-dialog :title="title" :visible.sync="isShow" center top="80px">
          <el-form :model="coach" class="demo-ruleForm" :rules="rules" ref="coach"
          label-width="90px" label-position="left" @keyup.enter.native="submitForm('coach')">
            <el-form-item label="姓名:" prop="name" >
              <el-input v-model="coach.name" size="medium"></el-input>
            </el-form-item>
            <el-form-item label="性别:" prop="sex" size="medium">
                <el-radio v-model="coach.sex" label="男" border>男</el-radio>
                <el-radio v-model="coach.sex" label="女" border>女</el-radio>
            </el-form-item>
            <el-form-item label="身份证号:" prop="id_number">
                <el-input v-model="coach.id_number" maxlength="18" size="medium" clearable></el-input>
            </el-form-item>
            <el-form-item label="电话号码:" prop="phone">
                <el-input v-model="coach.phone" size="medium" maxlength="11" clearable></el-input>
            </el-form-item>
             <el-form-item label="入职日期:" prop="date">
                <el-date-picker
                    v-model="coach.date"
                    type="date"
                    size="medium"
                    value-format="yyyy-MM-dd"
                    placeholder="选择日期">
                </el-date-picker>
            </el-form-item>
            <el-form-item label="* 绑定微信:" v-if="coach.id">
            <el-switch
              v-model="coach.isBindWx"
              active-color="#13ce66"
              :active-value="1"
              :inactive-value="0"
              @change="switchChange"
              >
            </el-switch>
          </el-form-item>
            <el-form-item label="* 分配车辆:">
              <el-select v-model="coach.car" placeholder="选择车辆" size="medium" style="width:220px">
                <el-option label="暂不分配" value="" />
                <el-option :label="coach.car" v-if="coach.car" :value="coach.car" />
                <el-option
                  v-for="item in carList"
                  :key="item"
                  :label="item"
                  :value="item">
                </el-option> 
              </el-select>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitForm('coach')" icon="el-icon-edit" style="width:220px">保存</el-button>
            <el-button type="warning" plain @click="reset('coach')" icon="el-icon-refresh" v-if="coach.id==''">重置</el-button>
          </div>
        </el-dialog>
        <el-dialog title="导入数据" :visible.sync="showImport" center @close="closeDialog">
        <el-upload
          class="upload-demo"
          ref="upload"
          drag
          :headers="headers"
          action="/api/coach/importExcel"
          :limit="1"
          :auto-upload="false"
          :on-success="importSuccess"
          :on-error="importError"
          accept=".xls,.xlsx">
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <div class="el-upload__tip" slot="tip">只能上传.xls / .xlsx文件</div>
        </el-upload>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitUpload" icon="el-icon-upload" style="width:220px">点击导入</el-button>
        </div>
      </el-dialog>
    </div>
  </template>
  
  <script>
  export default {
      name:'Coach',
      data(){
          return{
            coach:{},
            oldCoach:null,
            rules: {
              name: [
                { required: true, message: '请输入姓名', trigger: 'blur' },
                { min: 2, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
              ],
              sex: [{ required: true, message: '请选择性别', trigger: 'blur' }],
              id_number: [
                { required: true, message: '请输入身份证号', trigger: 'blur' },
                { min: 18, max: 18,message: '长度18个字符', trigger: 'blur' }
              ],
              phone: [
                { required:true, message: '请输入手机号', trigger: 'blur' },
                { pattern: /^1[3|5|7|8|9]\d{9}$/,message:'请输入正确的手机号码格式',trigger: 'blur'},
              ],
              date: [{ required: true, message: '请选择入职日期', trigger: 'blur' }],
              type: [{ required: true, message: '请选择驾照类型', trigger: 'visible-change' }]
            },
            options:['男','女'],       
            title:'',
            sex:null,
            keyWord:'',
            currentPage: 1,
            size:5,
            total:0,
            isShow:false,
            showImport:false,
            coachList: [],
            carList:[],
            deleteList:[],
            loading:true,
            headers:{token:this.$store.state.userInfo.token}
        }
      },
      created(){
        this.getCoachList()
      },
      methods:{
        // 获取教练列表列表
        getCoachList(){
          this.axios.get('/coach/list',{
            params:{
              keyWord:this.keyWord,
              sex:this.sex,
              size:this.size,
              currentPage:this.currentPage
            }
          }).then((res)=>{
            this.coachList=res.data.list
            this.total=res.data.total
            this.loading=false
          }) 
        },
        // 获取车辆牌照列表
        async getCarList(){
          let res = await this.axios.get('/car/list')
          if(res.code===2000){
            this.carList=res.data
          }
        },
        // 打开添加教练面板
        SaveCoach(){
            this.title='添加教练'
            this.coach={
              id:'',
              name:'',
              sex:'',
              id_number:'',
              phone:'',
              date:'',
              car:''
            }
            this.getCarList()
            this.isShow=true
        },
        // 打开修改教练面板
        handleEdit(row){
          this.title='编辑教练'
          this.oldCoach=JSON.parse(JSON.stringify(row));
          this.coach=JSON.parse(JSON.stringify(row));
          this.getCarList()
          this.isShow=true
        },
        // 删除教练
        handleDelete(row){
          this.$confirm(`确定删除 ${row.name} 教练吗?`, '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'error'
          }).then(() => { 
            this.axios.delete('/coach/delete',{data:row}).then((res)=>{
              if(res.code===2000){
                this.loading=true
                this.$message.success(res.data)
                this.deleteHandlePage(1)
                this.getCoachList()
              }
              else
                this.$message.error(res.data)
          })
          }).catch(() => {
             return          
          }); 
        },
        // 模糊搜索
        search(){
          if(this.keyWord==''||this.keyWord==null)
            return this.$message.error("请输入关键字后再搜索")
          this.loading=true
          this.currentPage=1
          this.getCoachList()
        },
        // 微信绑定开关
        switchChange(val){
          this.coach.isBindWx=val
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
        // 保存修改 
        submitForm(name){
              this.$refs[name].validate((valid)=>{
                if(valid){
                  this.$confirm('确定保存吗?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                  }).then(async ()=>{
                    if(this.coach.id==''){
                      let res = await this.axios.post('/coach/saveCoach',this.coach)
                      if(res.code===2000){
                        this.$message.success(res.data)
                        this.loading=true
                        this.getCoachList()
                        this.reset('coach')
                      }
                      else
                        this.$message.error(res.data)
                    }
                    else{
                      let res = await this.axios.put('/coach/updateCoach',
                      {oldCoach:this.oldCoach,newCoach:this.coach})
                      if(res.code===2000){
                          this.$message.success(res.data)
                          this.loading=true
                          this.getCoachList()
                      }
                      else
                        this.$message.error(res.data)
                    }
                  }).catch(()=>{
                    return
                  })  
                }
                else
                  return
              })
        },
        // 多选教练
        seletChange(s){
          this.deleteList=[]
          s.forEach((item) => {
          this.deleteList.push(item)
          })
        },
        // 批量删除
        deleteCoachList(){
          this.$confirm('确定删除选中的教练吗?', '警告', {
                      confirmButtonText: '确定',
                      cancelButtonText: '取消',
                      type: 'warning'
                  }).then(()=>{
                    this.axios.delete('/coach/deleteList',
                    {data:this.deleteList}).then((res)=>{
                      if(res.code===2000){
                        this.loading=true
                        this.$message.success(res.data)
                        this.deleteHandlePage(this.deleteList.length)
                        this.getCoachList()
                      }
                      else
                        this.$message.error(res.data)
                    })
                  }).catch(()=>{
                    return
            })
        },
        // 重置
        reset(name){
          this.$refs[name].resetFields();
        },
        // 删除数据后页码更新处理函数
        deleteHandlePage(size){
            let totalPage = Math.ceil((this.total - size) / this.size)
            let pagenum = this.currentPage > totalPage ? totalPage : this.currentPage
            this.currentPage = pagenum < 1 ? 1 : pagenum
        },
        // 导入
      submitUpload(){
        this.$refs.upload.submit()
      },
      // 导入成功
      importSuccess(res){
        if(res.code===2000){
          this.$message.success(res.data)
          this.$refs.upload.clearFiles();
          this.getCoachList()
        }
        else
        this.$message.error('导入失败！')
      },
      // 导入失败
      importError(){
        this.$message.error('导入失败！')
      },
      // 导出
      async exportData(){
        let res = await this.axios.post('/coach/exportExcel',{},{responseType:'blob'})
        this.downloadExcel(res)
      },
      // 下载模板
      download(){
        window.open('http://localhost:8081/static/excel/教练信息导入模板.xlsx')
      },
      //下载文件
      downloadExcel(data){
         if(!data){
             return
         }
         let blob = new Blob([data])
         let url = window.URL.createObjectURL(blob);
         let aLink = document.createElement("a");
         aLink.style.display = "none";
         aLink.href = url;
         aLink.setAttribute("download", "教练档案信息.xls");
         document.body.appendChild(aLink);
         aLink.click();
         document.body.removeChild(aLink); //下载完成移除元素
         window.URL.revokeObjectURL(url); //释放掉blob对象
      },
      closeDialog(){this.$refs.upload.clearFiles();}
      },
      watch:{
        // 类型筛选
        sex(){
          this.loading=true
          this.currentPage=1
          this.getCoachList()
        },
      }
  }
  </script>
  
  <style scoped>
  .el-table{
    margin: 10px auto;
  }
  .operation{
    width: 100%;
    height: 40px;
    margin-bottom: 15px;
    display: flex;
    justify-content: space-between;
  }
  .right ,.left{
    width: 450px;
    display: flex;
    justify-content: space-around;
    align-items: center;
  }
  .left{
    width: 550px;
  }
  .demo-ruleForm{
      text-align: left;
      width: 310px;
      margin: 0 auto;
  }
.el-form-item{
  margin-bottom: 15px;
}
.upload-demo{
  text-align: center;
}
</style>