<template>
  <div class="container">
    <div class="loginbox">
      <h1 class="welcome">WELCOME</h1>
      <el-form :model="ruleForm" :rules="rules" status-icon ref="ruleForm" class="demo-ruleForm">
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
        <el-button class="btn" type="primary" @click="submitForm">登入</el-button>
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
    <!-- 图形验证码 -->
    <!-- <div class="picture" v-if="isShow">
      <div class="verify">
        <h1 style="font-size:18px">拖动下方滑块完成拼图</h1>
        <div class="close" @click="closeMask">
          <i class="el-icon-circle-close"></i>
        </div>
        <span>{{text}}</span>
        <slide-verify 
          ref="slideblock"
          @success="onSuccess"
          slider-text="向右滑"
          >
        </slide-verify>
      </div>
    </div> -->
    <!-- 忘记密码框 -->
    <transition name="el-zoom-in-top">
      <div class="paswordbox" v-if="isShow1">
        <div class="box">
          <div class="close1" @click="showbox">
           <i class="el-icon-circle-close"></i>
          </div>
          <el-form :model="ruleForm1" :rules="rules1" status-icon ref="ruleForm1">
            <el-form-item prop="email">
              <el-input 
                  class="input"
                  placeholder="邮 箱"
                  prefix-icon="el-icon-message"
                  autocomplete="off"
                  v-model="ruleForm1.email"
                  clearable>
              </el-input>
            </el-form-item>
            <el-form-item prop="newPassword">    
              <el-input 
                class="input"
                placeholder="新 密 码" 
                prefix-icon="el-icon-lock"
                autocomplete="off"
                v-model="ruleForm1.newPassword" 
                show-password>
              </el-input>
            </el-form-item>  
              <div class="code">
                <el-form-item prop="code">
                  <el-input style="width:175px" v-model="ruleForm1.code"></el-input>
                </el-form-item>
                <el-button style="width:114px;height:40px" type="primary" @click="sendCode" :loading="isloading" :disabled="isunable">{{btntext}}</el-button>
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
          isShow1:false,
          isunable:false,
          isloading:false,
          btntext:'发送验证码',
          text: '',     
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
              { required: true, message: '请输入账号', trigger: 'blur' },
              { type: 'email', message: '邮箱格式错误', trigger: 'blur' },
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
              // this.closeMask()
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
          const result =  this.axios.post("user/login",this.ruleForm)
          result.then(({data})=>{
            if(data.code==2000){
                  this.$store.state.userInfo=data.data
                  sessionStorage.setItem('token',data.data.token)
                  this.$message({
                  message: data.message,
                  type: 'success'
                  });
                  this.$router.push({
                    path:'/main'
                  })
                }
                else
                  this.$message({
                    message: data.message,
                    type: 'error'
                  }); 
          })},
        // 图形验证码遮罩层关闭、打开
        closeMask(){
            this.isShow=!this.isShow
            this.text=''
        },
        // 验证码成功回调
        onSuccess(times){
              this.text = `只用了${(times / 1000).toFixed(1)}s,速度起飞了`
              clearTimeout(this.timer)
              this.timer=setTimeout(()=>{
                this.closeMask()
                this.login()
              },700) 
          },
        // 忘记密码遮罩层关闭、打开
        showbox(){
          this.isShow1=!this.isShow1
        },
        // 发送验证码
        sendCode(){
          const regEmail = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/ 
          if(regEmail.test(this.ruleForm1.email)){
            this.isunable=true
            this.isloading=true
            this.btntext='发送中'
            const result =  this.axios.get(`user/sendCode?email=${this.ruleForm1.email}`)
            result.then(({data})=>{
              if(data.code==2000){
                this.isloading=false
                this.isloading=false
                this.countDown()
                this.$message({
                  message: '发送成功!',
                  type: 'success'
                  });
              }
              else{
                this.$message({
                  message: '发送失败!',
                  type: 'error'
                  });
              }
            })
          }
          else return this.$message({
                message: '请输入正确的邮箱地址',
                type: 'warning'
                });
        },
        // 保存修改密码
        updatePassword(){
          let form = new FormData()
          form.append('email',this.ruleForm1.email)
          form.append('newPassword',this.ruleForm1.newPassword)
          form.append('code',this.ruleForm1.code)
          this.$refs.ruleForm1.validate((valid) => {
            if(valid){
                this.axios.post('user/update',form).then(({data})=>{
                  if(data.code==2000)
                      this.$message({
                        message: data.data,
                        type: 'success'
                      })
                  else
                  this.$message({
                        message: data.data,
                        type: 'error'
                      })
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
        background-image:linear-gradient(110.01595960288273deg, rgba(48, 245, 251,0.8) 5.533854166666667%,rgb(141, 189, 197)96.67968749999999%);
        display: flex;
        justify-content: center;
        align-items: center;
        position: relative;
        overflow: hidden;
    }
    .loginbox{
      z-index: 1;
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
    .input,.btn{
      width: 300px;
    }
    .btn{
      margin-top: 20px;
    }
    /* 图形验证码遮罩层 */
    .picture{
      position: absolute;
      width: 100%;
      height: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
      background-color: rgba(212, 207, 207, 0.1);
      z-index: 2;
    }
    /* 图形验证码盒子 */
    .verify{
      display: flex;
      position: relative;
      justify-content: center;
      align-items: center;
      flex-wrap: wrap;
      border-radius: 5%;
      width: 400px;
      height: 350px;
      background-color: white;
      /* z-index: 2; */
    }
    /* 关闭按钮 */
    .close i{
      transform: translate(80px,-15px);
      font-size: 25px;
    }
    .picture span{
      position: absolute;
      z-index: 3;
      top: 20%;
      font-size: 18px;
      color: rgb(52, 233, 140);
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
      background-color: rgb(255, 255, 255);
      z-index: 2;
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
      background-color: rgb(203, 238, 251);
    }
    .close1{
      font-size: 25px;
      position: absolute;
      top: 5px;
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
      bottom:-100px;
      box-shadow: 1px 1px 3px rgb(173, 173, 177);
      animation: circle 10s linear 0s infinite normal ;
    }
    .list li:nth-child(1){
      left: 3%;
      width: 150px;
      height: 150px;
      animation-duration: 8s;
      background-image: linear-gradient(112.15334473777807deg, rgba(249, 63, 72,1) 5.533854166666667%,rgba(250, 183, 121,1) 96.67968749999999%);  
    }
    .list li:nth-child(2){
      left: 15%;
      animation-delay: 2s;
      animation-duration: 7s;
      /* animation-timing-function: ease; */
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
      animation-delay: 3s;
      animation-duration: 8s;
      /* animation-timing-function: ease-in-out; */
      background-image: linear-gradient(112.15334473777807deg, rgba(249, 231, 148,1) 5.533854166666667%,rgba(246, 190, 212,1) 96.67968749999999%);   
    }
    .list li:nth-child(5){
      left: 40%;
      width: 200px;
      height: 200px;
      animation-duration: 15s;
      animation-delay: 0.5s;
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
      width: 130px;
      height: 130px;
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
      width: 150px;
      height: 150px;
      animation-duration: 7s;
      /* animation-timing-function: ease-in-out; */
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
      width: 200px;
      height: 200px;
      /* animation-timing-function: ease-out; */
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