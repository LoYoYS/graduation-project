<template>
    <div class="vessel">
        <!-- 操作条 -->
        <div class="operation">
          <div class="left">
            <el-button type="danger" icon="el-icon-delete" :disabled="deleteList.length==0" 
            size="small" @click="deleteStudentList">批量删除</el-button>
            <el-button type="primary" size="small" icon="el-icon-plus" @click="toAdd">添加学员</el-button>
          </div>
          <div class="right">
            <el-select v-model="type" placeholder="驾照类型" size="small" style="width:170px">
              <el-option label="全部" value="" />
              <el-option
                v-for="item in options"
                :key="item"
                :label="item"
                :value="item">
              </el-option> 
            </el-select>
            <el-select v-model="subject" clearable placeholder="已通过科目" size="small" style="width:170px">
              <el-option
                v-for="item in options1"
                :key="item.label"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
            <el-input v-model.lazy="keyWord" size="small" style="width:170px" 
            placeholder="姓名/手机号" prefix-icon="el-icon-search">
            </el-input>
            <el-button type="primary" size="small" icon="el-icon-search"  @click="search">搜素</el-button>
          </div>
        </div>
        <!-- 表格 -->
        <el-table :data="studentList" border  style="width: 100%" 
            :header-cell-style="{background:'#f4f3f9',color:'#606266',fontWeight:'bold'}"
            @selection-change="seletChange"
            >
          <el-table-column type="selection" align="center"></el-table-column>
          <el-table-column prop="name" label="姓名" align="center" width="auto"></el-table-column>
          <el-table-column prop="sex" label="性别" align="center" width="auto"></el-table-column>
          <el-table-column prop="id_number" label="身份证号" align="center" width="200px"></el-table-column>
          <el-table-column prop="phone" label="电话号码" align="center" width="150px"></el-table-column>
          <el-table-column prop="date" label="报名日期" align="center" width="130px"></el-table-column>
          <el-table-column prop="type" label="驾照类型" align="center" width="auto"></el-table-column>
          <el-table-column prop="subject.subject_a" label="科目一" align="center" width="auto">
            <template slot-scope="scope">
                <el-tag type="success" v-if="scope.row.subject.a">已通过</el-tag>
                <el-tag type="danger" v-else>未通过</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="subject.subject_b" label="科目二" align="center" width="auto">
            <template slot-scope="scope">
                <el-tag type="success" v-if="scope.row.subject.b">已通过</el-tag>
                <el-tag type="danger" v-else>未通过</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="subject.subject_c" label="科目三" align="center" width="auto">
            <template slot-scope="scope">
                <el-tag type="success" v-if="scope.row.subject.c">已通过</el-tag>
                <el-tag type="danger" v-else>未通过</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="subject.subject_d" label="科目四" align="center" width="auto">
            <template slot-scope="scope">
                <el-tag type="success" v-if="scope.row.subject.d">已通过</el-tag>
                <el-tag type="danger" v-else>未通过</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="150px">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="primary"
                @click="handleEdit(scope.row)">编辑</el-button>
              <el-button
                size="mini"
                type="danger"
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
        <div v-if="isShow">
          <el-dialog title="编辑学员" :visible.sync="isShow" center top="50px">
          <el-form :model="student" class="demo-ruleForm" :rules="rules" ref="student"
          label-width="90px" label-position="left" @keyup.enter="submitForm('student')">
            <el-form-item label="姓名:" prop="name" >
              <el-input v-model="student.name" size="medium"></el-input>
            </el-form-item>
            <el-form-item label="性别:" prop="sex" size="medium">
                <el-radio v-model="student.sex" label="男" border>男</el-radio>
                <el-radio v-model="student.sex" label="女" border>女</el-radio>
            </el-form-item>
            <el-form-item label="身份证号:" prop="id_number">
                <el-input v-model="student.id_number" maxlength="18" size="medium" clearable></el-input>
            </el-form-item>
            <el-form-item label="电话号码:" prop="phone">
                <el-input v-model="student.phone" size="medium" maxlength="11" clearable></el-input>
            </el-form-item>
            <el-form-item label="驾照类型:" prop="type">
                <el-select v-model="student.type" placeholder="请选择" size="medium">
                    <el-option v-for="item in options" :key="item" :value="item">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="报名日期:" prop="date">
                <el-date-picker
                    v-model="student.date"
                    type="date"
                    size="medium"
                    value-format="yyyy-MM-dd"
                    placeholder="选择日期">
                </el-date-picker>
            </el-form-item>
            <el-form-item label="* 完成科目:">
                <el-checkbox :checked="student.subject.a===1" @change="checkBoxChange('a')">科目一</el-checkbox>
                <el-checkbox :checked="student.subject.b===1" @change="checkBoxChange('b')">科目二</el-checkbox>
                <el-checkbox :checked="student.subject.c===1" @change="checkBoxChange('c')">科目三</el-checkbox>
                <el-checkbox :checked="student.subject.d===1" @change="checkBoxChange('d')">科目四</el-checkbox>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitForm('student')" icon="el-icon-edit" style="width:220px">修改</el-button>
          </div>
        </el-dialog>
        </div>
    </div>
  </template>
  
  <script>
  export default {
      name:'Train',
      data(){
          return{
            student:{
              subject:{
                a:0,
                b:0,
                c:0,
                d:0
              }
            },
            oldstudent:{},
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
              date: [{ required: true, message: '请选择日期', trigger: 'blur' }],
              type: [{ required: true, message: '请选择驾照类型', trigger: 'visible-change' }]
            },       
            options:['C1','C2','C3','C4','C5','C6','D','E','F'],
            options1:[{label:'全部',value:''},{label:'科目一',value:'a'},{label:'科目二',value:'b'},
            {label:'科目三',value:'c'},{label:'科目四',value:'d'}],
            keyWord:null,
            type:null,
            subject:null,
            currentPage: 1,
            size:5,
            total:0,
            isShow:false,
            studentList: [],
            deleteList:[]
        }
      },
      created(){
        this.getStudentList()
      },
      methods:{
        // 获取学员列表
        getStudentList(){
          this.axios.get('/api/student/list',{
            params:{
              keyWord:this.keyWord,
              type:this.type,
              subject:this.subject,
              size:this.size,
              currentPage:this.currentPage
            }
          }).then((res)=>{
            this.studentList=res.data.list
            this.total=res.data.total
          })
        },
        // 修改学员
        handleEdit(row){
          this.student=JSON.parse(JSON.stringify(row));
          this.oldstudent = JSON.parse(JSON.stringify(row));
          this.isShow=true
        },
        // 删除学员
        handleDelete(row){
          this.$confirm(`确定删除 ${row.name} 学员吗?`, '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'error'
          }).then(() => { 
            this.axios.delete('/api/student/delete',{data:row}).then((res)=>{
              if(res.code===2000){
                this.$message({
                  message:res.data,
                  type:"success"
                })
                this.getStudentList()
              }
              else
                this.$message({
                  message:res.data,
                  type:'error'
              })
          })
          }).catch(() => {
             return          
          }); 
        },
        // 模糊搜索
        search(){
          this.currentPage=1
          this.getStudentList()
        },
        // 显示数量改变
        handleSizeChange(index){
          this.size = index
          this.getStudentList()
        },
        // 页码改变
        handleCurrentChange(index){
          this.currentPage=index
          this.getStudentList()
        },
        // 跳转添加
        toAdd(){
          this.$router.push({path:'/student/save'})
        },
        // 更新学员科目
        checkBoxChange(s){
          this.student.subject[s]=this.student.subject[s]===1?0:1
        },
        // 提交修改学员 
        submitForm(name){
              this.$refs[name].validate((valid)=>{
                  if(valid){
                    this.$confirm('确定修改吗?', '提示', {
                      confirmButtonText: '确定',
                      cancelButtonText: '取消',
                      type: 'info'
                    }).then(()=>{
                      this.axios.post('/api/student/update',
                        {oldStudent:this.oldstudent,newStudent:this.student}
                        ).then((res)=>{
                          if(res.code===2000){
                            this.$message({
                              message:res.data,
                              type:'success'
                            })
                            this.getStudentList()
                          }
                          else
                            this.$message({
                              message:res.data,
                              type:'error'
                            })
                      })
                    }).catch(()=>{
                      return
                    })  
                  }
                  else
                    return
              })
        },
        // 多选学员
        seletChange(s){
          this.deleteList=[]
          s.forEach((item) => {
          this.deleteList.push(item)
          })
        },
        // 批量删除
        deleteStudentList(){
          this.$confirm('确定删除选中的学员吗?', '警告', {
                      confirmButtonText: '确定',
                      cancelButtonText: '取消',
                      type: 'warning'
                  }).then(()=>{
                    this.axios.delete('/api/student/deleteList',
                    {data:this.deleteList}).then((res)=>{
                      if(res.code===2000){
                        this.$message({
                          message:res.data,
                          type:'success'
                        })
                        this.getStudentList()
                      }
                      else{
                        this.$message({
                          message:res.data,
                          type:'error'
                        })
                      }
                    })
                  }).catch(()=>{
                    return
            })
        } 
      },
      watch:{
        // 类型筛选
        type(){
          this.currentPage=1
          this.getStudentList()
        },
        // 科目筛选
        subject(){
          this.currentPage=1
          this.getStudentList()
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
  .right{
    width: 650px;
    display: flex;
    justify-content: space-around;
    align-items: center;
  }
  .demo-ruleForm{
      text-align: left;
      width: 300px;
      margin: 0 auto;
  }
  </style>