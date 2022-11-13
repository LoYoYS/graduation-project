<template>
    <div class="vessel">
        <!-- 操作条 -->
        <div class="operation">
          <div class="left">
            <el-button type="danger" icon="el-icon-delete" :disabled="deleteList.length==0" 
            size="small" @click="deleteCarList">批量删除</el-button>
            <el-button type="primary" size="small" icon="el-icon-plus" @click="saveCar">
              添加车辆</el-button>
          </div>
          <div class="right">
            <el-select v-model="type" placeholder="车辆类型" clearable @clear="clear" size="small" style="width:170px">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option> 
            </el-select>
            <el-input v-model.lazy="keyWord" size="small" style="width:170px" clearable
            placeholder="车牌号" maxlength="11" prefix-icon="el-icon-search">
            </el-input>
            <el-button type="primary" size="small" icon="el-icon-search"  @click="search">搜素</el-button>
          </div>
        </div>
        <!-- 表格 -->
        <el-table :data="carList" border  style="width: 100%" v-loading="loading"
            :header-cell-style="{background:'#f4f3f9',color:'#606266',fontWeight:'bold'}"
            @selection-change="seletChange"
            >
          <el-table-column type="selection" align="center"></el-table-column>
          <el-table-column prop="url" label="图片" align="center" width="300px">
            <template slot-scope="scope">
              <el-image
              style="width: 200px; height: 100px;"
              :src="scope.row.url"
              :preview-src-list="[scope.row.url]">
                <div slot="error" style="width:60px;height:50px;margin:45px auto;">
                  暂无图片
                </div>
              </el-image>
            </template>
          </el-table-column>
          <el-table-column prop="number" label="车牌号" align="center" width="100px"></el-table-column>
          <el-table-column prop="brand" label="品牌" align="center" width="200px"></el-table-column>
          <el-table-column prop="type" label="车辆类型" align="center" width="auto">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.type">汽车</el-tag>
              <el-tag v-else>摩托车</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="coach_name" label="使用者" align="center" width="auto">
            <template slot-scope="scope">
              <div v-if="scope.row.coach_name">{{scope.row.coach_name}}</div>
              <div v-else>无</div>
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
            <el-form :model="car" class="demo-ruleForm" :rules="rules" ref="car"
            label-width="90px" label-position="rigth" @keyup.enter.native="submitForm('car')">
                <el-form-item label="车牌号:" prop="number" size="medium">
                    <el-input v-model="car.number" size="medium" style="width:220px">
                    </el-input>
                </el-form-item>
                <el-form-item label="品牌:" prop="brand">
                    <el-input v-model="car.brand" maxlength="18" size="medium" style="width:220px">
                    </el-input>
                </el-form-item>
                <el-form-item label="车辆类型:" prop="type">
                  <el-select v-model="car.type" 
                  placeholder="选择类型"
                  size="medium"
                  style="width:220px">
                    <el-option
                      v-for="item in options"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option> 
                   </el-select>
                </el-form-item>
                <el-form-item label="* 使用者:" prop="coach_id">
                  <el-select v-model="car.coach_id" 
                  placeholder="选择使用者"
                  size="medium"
                  style="width:220px">
                    <el-option label="无" value="" />
                    <el-option
                      v-for="item in coachList"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id">
                    </el-option> 
                   </el-select>
                </el-form-item>
                <el-form-item label="* 图片:">
                    <el-upload
                      class="avatar-uploader"
                      ref="document"
                      action="/api/car/getUrl"   
                      :before-upload="beforeUpload"
                      :on-remove="handleRemove"
                      :on-success="getUrlsuccess"
                      :limit="1">
                      <el-image v-if="car.url" :src="car.url" style="width: 148px; height: 148px"/>
                      <div v-else class="addPhoto">
                        <i class="el-icon-plus"></i>
                      </div>
                      <div slot="tip" class="el-upload__tip">只能上传jpg/png文件,且不超过1MB</div>
                    </el-upload>
                </el-form-item>
            </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitForm('car')" icon="el-icon-edit" style="width:220px">保存</el-button>
            <el-button type="warning" plain @click="reset('car')" icon="el-icon-refresh" v-if="car.id==''">重置</el-button>
          </div>
        </el-dialog>
    </div>
  </template>
  
  <script>
  export default {
      name:'Car',
      data(){
          return{
            car:{},
            oldCar:null,
            rules: {
              number: [{required: true, message: '请输入车牌号', trigger: 'blur'},
                {pattern:/^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$/,
                message:'请输入正确的车辆牌照', trigger: 'blur'}
              ],
              brand: [{required: true, message: '请输入车辆品牌', trigger: 'blur'}],
              type: [{required: true, message: '请选择车辆类型', trigger: 'blur'}],
              coach_id: [{required: false}],
            },       
            options:[{label:'小车',value:1},{label:'摩托车',value:0}],
            type:null,
            keyWord:null,
            title:'添加车辆',
            currentPage: 1,
            size:5,
            total:0,
            isShow:false,
            carList:[],
            coachList:[],
            deleteList:[],
            loading:true
        }
      },
      created(){
        this.getCarList()
      },
      methods:{
        // 获取车辆列表列表
        getCarList(){
          this.axios.get('/api/car/getList',{
            params:{
              keyWord:this.keyWord,
              type:this.type,
              size:this.size,
              currentPage:this.currentPage
            }
          }).then((res)=>{
            this.carList=res.data.list
            this.total=res.data.total
            this.loading=false
          })
          
        },
        async getCoachList(){
         let result = await this.axios.get('/api/coach/getCoachList')
         if(result.code===2000){
            this.coachList=result.data
         }
        },
        // 打开添加车辆面板
        saveCar(){
          this.car={
            id:'',
            brand:'',
            number:'',
            coach_id:'',
            type:'',
            url:''
          }
          this.getCoachList()
          this.isShow=true
        },
        // 打开修改车辆面板
        handleEdit(row){
          this.title='编辑车辆'
          this.getCoachList()
          this.oldCar=JSON.parse(JSON.stringify(row));
          this.car=JSON.parse(JSON.stringify(row));
          if(row.coach_id)
            this.coachList.push({id:row.coach_id,name:row.coach_name})
          this.isShow=true
        },
        // 删除车辆
        handleDelete(row){
          this.$confirm(`确定删除 ${row.number} 车辆吗?`, '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'error'
          }).then(() => { 
            this.axios.delete('/api/car/deleteCar',{data:row}).then((res)=>{
              if(res.code===2000){
                this.$message.success(res.data)
                this.deleteHandlePage(1)
                this.loading=true
                this.getCarList()
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
          this.getCarList()
        },
        // 显示数量改变
        handleSizeChange(index){
          this.loading=true
          this.size = index
          this.getCarList()
        },
        // 页码改变
        handleCurrentChange(index){
          this.loading=true
          this.currentPage=index
          this.getCarList()
        },
        // 添加、编辑 
        submitForm(name){
              this.$refs[name].validate((valid)=>{
                if(valid){
                  this.$confirm('确定保存吗?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                  }).then(async ()=>{
                    if(this.car.id==''){
                      let res = await this.axios.post('/api/car/saveCar',this.car)
                      if(res.code===2000){
                        this.$message.success(res.data)
                        this.getCarList()
                        this.reset('car')
                        this.getCoachList()
                      }
                      else
                        this.$message.error(res.data)
                    }
                    else{
                      let res = await this.axios.put('/api/car/updateCar',
                      {oldCar:this.oldCar,newCar:this.car})
                      if(res.code===2000){
                        this.$message.success(res.data)
                        this.getCarList()
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
        // 选择框事件
        seletChange(s){
          this.deleteList=[]
          s.forEach((item) => {
          this.deleteList.push(item)
          })
        },
        // 批量删除
        deleteCarList(){
          this.$confirm('确定删除选中的车辆吗?', '警告', {
                      confirmButtonText: '确定',
                      cancelButtonText: '取消',
                      type: 'warning'
                  }).then(()=>{
                    this.axios.delete('/api/car/deleteCarList',
                    {data:this.deleteList}).then((res)=>{
                      if(res.code===2000){
                        this.$message.success(res.data)
                        this.loading=true
                        this.deleteHandlePage(this.deleteList.length)
                        this.getCarList()
                      }
                      else{
                        this.$message.error(res,data)
                      }
                    })
                  }).catch(()=>{
                    return
            })
        },
        // 重置表单
        reset(name){
          this.car.url=''
          this.$refs[name].resetFields();
          this.$refs['document'].clearFiles();
        },
        beforeUpload(file){
        let type = file.type==='image/jpeg' || file.type==='image/png'
        if(!type){
          this.$message.error('请上传jpg、png格式的图片')
          return false
        }
        return true
        },
        getUrlsuccess(res){
          if(res.code===2000)
            this.car.url=res.data
        },
        handleRemove(){
          this.car.url=''
        },
        // 删除数据后页码更新处理函数
        deleteHandlePage(size){
            let totalPage = Math.ceil((this.total - size) / this.size)
            let pagenum = this.currentPage > totalPage ? totalPage : this.currentPage
            this.currentPage = pagenum < 1 ? 1 : pagenum
        },
        clear(){
          this.loading=true
        } 
      },
      watch:{
        // 类型筛选
        type(){
          this.loading=true
          this.currentPage=1
          this.getCarList()
        }
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
  .right{
    width: 450px;
    display: flex;
    justify-content: space-around;
    align-items: center;
  }
  .demo-ruleForm{
      height: 450px;
      width: 400px;
      margin: 0 auto;
  }
  .addPhoto{
    width: 148px;
    height: 148px;
    font-size: 30px;
    border: 1px dotted gray;
    border-radius: 6px;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .addPhoto:hover{
    border: 1px dotted rgb(35, 156, 193);
  }
</style>