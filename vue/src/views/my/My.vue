<template>
  <div>
    <el-tabs v-model="activeName">
        <el-tab-pane label="个人信息" name="info">
            <div class="info">
              <div class="right">
                 <el-image 
                  style="width: 150px; height: 150px"
                  :src="srcList[0]" 
                  :preview-src-list="srcList">
                </el-image>
                <span>
                  {{userInfo.nickName}}
                </span>
              </div>
              <div>
                <el-form :model="userInfo" status-icon  
                @keyup.enter.native="submitForm" label-width="60px">
                  <el-form-item label="昵 称:">
                    <el-input v-model="userInfo.nickName" clearable size="small">
                    </el-input>
                  </el-form-item>
                  <el-form-item label="用户名:">
                    <el-input v-model="userInfo.username" readonly size="small">
                    </el-input>
                  </el-form-item>
                  <el-form-item label="手机号:">
                    <el-input v-model="userInfo.phone" maxlength="11" size="small">
                    </el-input>
                  </el-form-item>
                  <el-form-item label="邮 箱:">
                    <el-input v-model="userInfo.email" size="small">
                    </el-input>
                  </el-form-item>
                  <el-button type="primary" @click="submitForm">点击修改</el-button>
                </el-form>
              </div>
            </div>
        </el-tab-pane>
        <el-tab-pane label="修改密码" name="password">
          <div class="info">
            <div class="content">
                <el-form :model="passwordForm" :rules="rules" status-icon ref="passwordForm"
                label-width="100px">
                  <el-form-item prop="oldPassword" label="旧密码:">
                      <el-input v-model="passwordForm.oldPassword" show-password/>
                  </el-form-item>
                  <el-form-item prop="newPassword" label="新密码:">
                      <el-input v-model="passwordForm.newPassword" show-password/>
                  </el-form-item>
                  <el-form-item prop="again" label="确认密码:">
                      <el-input v-model="passwordForm.again" show-password/>
                  </el-form-item>
                </el-form>
              <el-button type="primary"  @click="updatePassword('passwordForm')">点击修改</el-button>
            </div>
          </div>
        </el-tab-pane>
        <el-tab-pane label="更换头像" name="avatar">
          <div style="height:490px">
            <el-upload
              action="/api/user/avatar"
              :show-file-list="false"
              :headers="Headers"
              :data="data"
              :on-success="success"
              :before-upload="beforeUpload"
              >
              <el-image style="width:200px; height:200px; margin-top:20px" :src="srcList[0]"/>
            </el-upload>
            <h1 style="margin-top:20px">点击图片选择更换新头像</h1>
          </div>
        </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
export default {
    name:'My',
    data(){
      let validatePass = (rule, value, callback) => {
            if (!value) {
                callback(new Error('请再次输入密码'))
              } else if (value !== this.passwordForm.newPassword) {
                callback(new Error('两次输入密码不一致!'))
              } else {
                callback()
              }
          }
      return{
        activeName:'info',
        userInfo:null,
        srcList:[],
        passwordForm:{
          id:this.$store.state.userInfo.id,
          oldPassword:'',
          newPassword:'',
          again:''
        },    
        rules: {
          oldPassword: [{required: true, message: '请输入旧密码', trigger: 'blur'}],
          newPassword: [{required: true, message: '请输入新密码', trigger:'blur'}],
          again: [{required: true, validator: validatePass, trigger: 'blur'}],
        },
        Headers:{
          token:sessionStorage.getItem('token')
        },
        data:{
          id:this.$store.state.userInfo.id
        }
      }
    },
    created(){
      this.userInfo=JSON.parse(JSON.stringify(this.$store.state.userInfo))
      this.srcList.push(this.userInfo.url)
    },
    methods:{
      // 修改信息
      submitForm(){
        this.$confirm('确定修改?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'info'
        }).then(async ()=>{
            let res = await this.axios.put('/api/user/updateUser',this.userInfo)
            if(res.code===2000){
              this.$store.commit('getUserInfo',this.userInfo)
              this.$message({
                message:res.data,
                type:'success'
              })
            }
            else
              this.$message({
                  message:res.data,
                  type:'error'
                })
        }).catch(()=>{
          return
        })
      },
      // 修改密码
      updatePassword(name){
        this.$refs[name].validate(async (valid) => {
            if(valid){
             let res =  await this.axios.put('/api/user/updatePassword',this.passwordForm)
             if(res.code===2000){
              this.$alert(`${res.data},请重新登入`, '提示', {
                confirmButtonText: '确定',
                type: 'success',
                callback: () => {
                  this.$store.commit('logout')
                  this.$store.commit('resetTab')
                  this.$router.push({path:'/login'})
                }
              })
             }
             else
              this.$message({
                message:res.data,
                type:'error'
              })
            }
            else
              return
        })
      },
      // 上传成功回调
      success(response){
        if(response.code===2000){
          this.$store.commit('updateAvatar',response.data)
          this.srcList=[response.data]
          this.$message({
            message:'更换成功',
            type:'success'
          })
        }
        else
        this.$message({
            message:'更换失败',
            type:'error'
          })
      },
      // 图片上传前回调
      beforeUpload(file){
        let type = file.type==='image/jpeg' || file.type==='image/png'
        if(!type){
          this.$message({
            message:'请上传jpg、png格式的图片',
            type:'error'
          })
          return false
        }
        return true
      }
    }
}
</script>

<style scoped>
.info{
  width: 100%;
  height: 490px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.right{
  margin-bottom: 100px;
  margin-right: 50px;
  display: flex;
  flex-direction: column;
}
.right span{
  position: absolute;
  bottom: 35%;
  left: 36%;
  font-size: 20px;
  font-weight: bold;
}
.content{
  width: 350px;
}
</style>