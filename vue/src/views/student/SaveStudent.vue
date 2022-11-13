<template>
  <div class="form_area">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" status-icon
        label-width="100px" class="demo-ruleForm" label-position="left"  @keyup.enter.native="submitForm('ruleForm')">
        <el-form-item label="姓名:" prop="name" >
            <el-input v-model="ruleForm.name" style="width:260px"></el-input>
        </el-form-item>
        <el-form-item label="性别:" prop="sex">
            <el-radio v-model="ruleForm.sex" label="男" border>男</el-radio>
            <el-radio v-model="ruleForm.sex" label="女" border>女</el-radio>
        </el-form-item>
        <el-form-item label="身份证号:" prop="id_number">
            <el-input v-model="ruleForm.id_number" maxlength="18" clearable style="width:260px"></el-input>
        </el-form-item>
        <el-form-item label="电话号码:" prop="phone">
            <el-input v-model="ruleForm.phone" maxlength="11" clearable style="width:260px"></el-input>
        </el-form-item>
        <el-form-item label="驾照类型:" prop="type">
            <el-select v-model="ruleForm.type" placeholder="请选择" style="width:260px">
                <el-option v-for="item in options" :key="item" :value="item">
                </el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="报名日期:" prop="date">
            <el-date-picker
                v-model="ruleForm.date"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择日期"
                style="width:260px">
            </el-date-picker>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" icon="el-icon-plus" @click="submitForm('ruleForm')">添加</el-button>
            <el-button type="warning" plain icon="el-icon-refresh" @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
    name:"SaveStudent",
    data() {
      return {
        ruleForm: {
          name: '',
          sex: '',
          id_number: '',
          phone: '',
          date: '',
          type: '',
          subject:{a:0,b:0,c:0,d:0}
        },
        // 'A1','A2','A3','B1','B2','M','N','P'
        options:['C1','C2','C3','C4','C5','C6','D','E','F'],
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
        }
      };
    },
    methods:{
         submitForm(name){
            this.$refs[name].validate((valid)=>{
                if(valid){
                  let result = this.axios.post('/api/student/save',this.ruleForm)
                  result.then((data)=>{
                    if(data.code===2000){
                      this.$message.success(data.data)
                      this.resetForm(name)
                    }
                    else
                      this.$message.error(data.data)
                  })
                }
                else
                  return
            })
        },
        resetForm(name){
            this.$refs[name].resetFields();
        },
    }
}
</script>

<style scoped>
.form_area{
    height: 500px;
    overflow: hidden;
}
.demo-ruleForm{
    text-align: left;
    width: 30%;
    margin: 0 auto;
}
.el-form-item{
    margin-bottom: 20px;
}
</style>