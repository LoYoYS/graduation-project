<template>
  <div class="container">
    <div class="loginbox">
      <h1 class="welcome">WELCOME</h1>
      <el-form :model="ruleForm" :rules="rules" status-icon ref="ruleForm" class="demo-ruleForm" @keyup.enter.native="submitForm">
        <el-form-item prop="username">
          <el-input
            class="input"
            placeholder="用户名"
            v-model="ruleForm.username"
            prefix-icon="el-icon-user-solid"
            clearable>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input 
            class="input"
            placeholder="密码" 
            prefix-icon="el-icon-lock"
            v-model="ruleForm.password" 
            show-password>
          </el-input>
        </el-form-item>
        <a href="javascript:;" @click="showbox">忘记密码？</a>
        <el-button class="Btn" type="primary" @click="submitForm">登入</el-button>
      </el-form>
    </div>
    <ul class="list">
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
    </ul>
    <!-- 忘记密码框 -->
    <transition name="el-zoom-in-top">
      <div class="paswordbox" v-if="isShow">
        <div class="box">
          <div class="close1" @click="showbox">
           <i class="el-icon-circle-close"></i>
          </div>
          <el-form :model="ruleForm1" :rules="rules1" status-icon ref="ruleForm1">
            <el-form-item prop="email">
              <el-input 
                  placeholder="邮 箱"
                  prefix-icon="el-icon-message"
                  v-model="ruleForm1.email"
                  clearable>
              </el-input>
            </el-form-item>
            <el-form-item prop="newPassword">    
              <el-input 
                placeholder="新 密 码"
                autocomplete="new-password" 
                prefix-icon="el-icon-lock"
                v-model="ruleForm1.newPassword" 
                show-password>
              </el-input>
            </el-form-item>  
              <div class="code">
                <el-form-item prop="code">
                  <el-input style="width:175px" v-model="ruleForm1.code"></el-input>
                </el-form-item>
                <el-button style="width:114px;height:40px" type="primary" @click="sendCode" :disabled="isunable">{{btntext}}</el-button>
              </div>
              <el-button type="primary" class="input" @click="updatePassword">保 存</el-button>
          </el-form>  
        </div>
      </div>
    </transition>
  </div>
</template>

<script>
  export default {
    data() {
        return {
          /* 是否展示图形验证码 */
          isShow:false,
          isunable:false,
          btntext:'发送验证码',   
          ruleForm: {
            username:'',
            password:'',
          },
          rules: {
            username: [{ required: true, message: '请输入账号', trigger: 'blur' },],
            password: [ { required: true, message: '请输入密码', trigger: 'blur' }, ]
          },
          ruleForm1:{
            email:'',
            newPassword:'',
            code:'',
          },
          rules1: {
            email: [
              { required: true, message: '请输入邮箱', trigger: 'blur' },
              { pattern: /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/,message:'请输入正确的邮箱格式',trigger: 'blur'},
            ],
            newPassword: [{required: true, message: '请输入新密码', trigger:'blur'}],
            code:[{required: true, message: '请输入验证码', trigger:'blur'}]
          },
        };
      },
      methods: {
        // 表单提交
        submitForm() {
          this.$refs.ruleForm.validate((valid) => {
            if (valid) {
              let that=this
              let catptcha = new TencentCaptcha('195684093',function(res){
                  if(res.ret===0){
                    that.login()
                  }
              })
              catptcha.show()
            } else 
              return false;
          });
        },
        /* 登入 */
        login(){
          const result =  this.axios.post("/api/user/login",this.ruleForm)
          result.then((data)=>{
            if(data.code==2000){
                  this.$store.commit('getUserInfo',data.data)
                  sessionStorage.setItem('token',data.data.token)
                  this.$message.success(data.message)
                  this.$router.push({path:'/'})
                }
                else
                  this.$message.error(data.message)
          })},
        // 忘记密码遮罩层关闭、打开
        showbox(){
          this.isShow=!this.isShow
        },
        // 发送验证码
        sendCode(){
          this.$refs.ruleForm1.validateField('email',(err)=>{
              if(!err){
                  this.isunable=true
                  this.countDown()
                  this.$message.success('发送成功！')
                  const result =  this.axios.get(`/api/user/sendCode?email=${this.ruleForm1.email}`)
                  result.then((data)=>{
                  if(data.code==2000){
                    return
                  }
                  else{
                    this.$message.error('发送失败！')
                  }
                })
              }
              else
               return
          })  },
        // 保存修改密码
        updatePassword(){
          let form = new FormData()
          form.append('email',this.ruleForm1.email)
          form.append('newPassword',this.ruleForm1.newPassword)
          form.append('code',this.ruleForm1.code)
          this.$refs.ruleForm1.validate((valid) => {
            if(valid){
                this.axios.post('/api/user/forgetPassword',form).then((data)=>{
                  if(data.code==2000)
                    this.$message.success(data.data)
                  else
                    this.$message.error(data.data)
              })
            }
            else
              return false
          })
        },
        // 验证码倒计时
        countDown(){
            let time = 60
            let timer = setInterval(()=>{
              time--
              this.btntext=time+'s'
              if(time===0){
                clearInterval(timer)
                this.isunable=false
                this.btntext='发送验证码'
              }
            },900)
        }
      }
}
  
</script>
<style scoped>
    .container{
        width: 100vw;
        height: 100vh;
        /* background-image:linear-gradient(110.01595960288273deg, rgba(48, 245, 251,0.8) 5.533854166666667%,rgb(141, 189, 197)96.67968749999999%); */
        background:url(https://s1.ax1x.com/2022/10/13/xa5gQf.jpg)50%/cover no-repeat;
        display: flex;
        justify-content: center;
        align-items: center;
        position: relative;
        overflow: hidden;
    }
    .loginbox{
      z-index: 3;
      width: 450px;
      position: relative;
      text-align: center;
      height: 400px;
      background-color:rgba(231, 244, 245,0.9);
      box-shadow: 5px 5px 5px rgba(46, 47, 47,0.3);
      border-radius: 5%;
    }
    .welcome{
      margin-top: 50px;
      font-size: 40px;
      font-weight: bold;
      letter-spacing: 5px;
    }
    .demo-ruleForm{
      position:absolute;
      left: 50%;
      top: 55%;
      transform: translate(-50%,-50%);
    }
    .input,.Btn{
      width: 300px;
    }
    .Btn{
      margin-top: 20px;
    }
    /* 忘记密码按钮 */
    .demo-ruleForm a{
      color: rgb(0, 180, 251);
      text-decoration: none;
      position: absolute;
      right: 0;
      bottom: 30%;
    }
    /* 忘记密码遮罩层 */
    .paswordbox{
      position: absolute;
      width: 100%;
      height: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
      background:url(https://s1.ax1x.com/2022/10/13/xa5hwQ.jpg)50%/cover no-repeat;
      z-index: 99;
    }
    /* 忘记密码盒子 */
    .box{
      display: flex;
      position: relative;
      justify-content: center;
      align-items: center;
      border-radius: 10px;
      flex-wrap: wrap;
      width: 400px;
      height: 300px;
      background-color:rgba(231, 244, 245,0.9);
      box-shadow: 5px 5px 5px rgba(46, 47, 47,0.3);
    }
    .close1{
      font-size: 25px;
      position: absolute;
      top: 5px;
      cursor: pointer;
      right:7px;
    }
    .code{
      width: 300px;
      display: flex;
      justify-content: space-between;
    }

    .list li{
      width: 100px;
      height: 100px;
      position: absolute;
      border-radius: 50%;
      box-shadow: 0px 0px 1px rgb(194, 189, 189);
      animation: circle 10s linear 0s infinite normal ;
    }
    .list li:nth-child(1){
      left: 3%;
      animation-duration: 8s;
      background-image: linear-gradient(112.15334473777807deg, rgba(249, 63, 72,1) 5.533854166666667%,rgba(250, 183, 121,1) 96.67968749999999%);  
    }
    .list li:nth-child(2){
      left: 15%;
      /* animation-delay: 2s; */
      animation-duration: 7s;
      background-image: linear-gradient(112.15334473777807deg, rgba(247, 238, 147,1) 5.533854166666667%,rgba(234, 220, 34,1) 96.67968749999999%);   
    }
    .list li:nth-child(3){
      left: 20%;
      width: 130px;
      height: 130px;
      background-image: linear-gradient(112.15334473777807deg, rgba(231, 244, 245,1) 5.533854166666667%,rgba(78, 240, 250,1) 96.67968749999999%);   
    }
    .list li:nth-child(4){
      left: 26%;
      /* animation-delay: 3s; */
      animation-duration: 8s;
      background-image: linear-gradient(112.15334473777807deg, rgba(249, 231, 148,1) 5.533854166666667%,rgba(246, 190, 212,1) 96.67968749999999%);   
    }
    .list li:nth-child(5){
      left: 40%;
      animation-duration: 15s;
      /* animation-delay: 0.5s; */
      background-image: linear-gradient(112.15334473777807deg, rgba(98, 185, 183,1) 5.533854166666667%,rgba(236, 246, 237,1) 96.67968749999999%);   
    }
    .list li:nth-child(6){
      left: 45%;
      width: 80px;
      height: 80px;
      animation-delay: 2s;
      background-image: linear-gradient(112.15334473777807deg, rgba(255, 148, 196,1) 5.533854166666667%,rgba(235, 81, 112,1) 96.67968749999999%);   
    }
    .list li:nth-child(7){
      left: 50%;
      animation-delay: 0.3s;
      background-image: linear-gradient(112.15334473777807deg, rgba(255, 148, 196,1) 5.533854166666667%,rgba(235, 81, 112,1) 96.67968749999999%);   
    }
    .list li:nth-child(7){
      left: 60%;
      animation-duration: 11s;
      background-image: linear-gradient(112.15334473777807deg, rgba(186, 213, 241,1) 5.533854166666667%,rgba(42, 39, 107,1) 96.67968749999999%);   
    }
    .list li:nth-child(8){
      left: 68%;
      animation-duration: 7s;
      background-image: linear-gradient(112.15334473777807deg, rgba(114, 55, 146,1) 5.533854166666667%,rgba(239, 173, 183,1) 96.67968749999999%);   
    }
    .list li:nth-child(9){
      left: 75%;
      animation-duration: 9s;
      animation-delay: 0.1s;
      background-image: linear-gradient(112.15334473777807deg, rgba(245, 196, 207,1) 5.533854166666667%,rgba(66, 82, 183,1) 96.67968749999999%);   
    }
    .list li:nth-child(10){
      left: 85%;
      animation-duration: 12s;
      background-image: linear-gradient(112.15334473777807deg, rgba(72, 209, 227,1) 5.533854166666667%,rgba(69, 143, 178,1) 96.67968749999999%);   
    }
    @keyframes circle {
      0%{
        top: 100%;
        transform: translateX(0);}
      38%{transform: translateX(30px);}
      50%{ transform: translateX(0px);}
      78%{ transform: translateX(-30px);}
      100%{
        top:-200px;
        transform: translateX(0);
      }
    }
</style>