<template>
  <div>
    <div class="view">
        <div class="Info">
            <el-form :model="info" status-icon  :rules="rules" ref="ruleForm" label-width="100px" 
            class="demo-ruleForm">
                <el-form-item label="驾校名称:" prop="name">
                    <el-input maxlength="10" type="text" v-model="info.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="驾校热线:" prop="phone">
                    <el-input type="text" maxlength="11" v-model="info.phone" autocomplete="off">
                    </el-input>
                </el-form-item>
                <el-form-item label="驾校地址:" prop="address" class="address">
                    <el-input type="textarea" autosize v-model="info.address" autocomplete="off">
                    </el-input>
                </el-form-item>
                <el-form-item label="地址经度:" prop="longitude" size="mini" >
                    <el-input type="number" v-model.number="info.longitude" autocomplete="off">
                    </el-input>
                </el-form-item>
                <el-form-item label="地址纬度:" prop="latitude" size="mini">
                    <el-input type="number" v-model.number="info.latitude" 
                    autocomplete="off">
                    </el-input>
                </el-form-item>
                <el-form-item label="驾校标语:">
                    <div class="slogan">
                        <el-tag
                            class="tag_item"
                            :key="index"
                            v-for="(tag,index) in info.slogans"
                            closable
                            size="small"
                            :disable-transitions="false"
                            @close="handleClose(tag)">
                            {{tag}}
                        </el-tag>
                        <el-input
                            class="tag_item"
                            v-if="inputVisible"
                            v-model="inputValue"
                            ref="saveTagInput"
                            maxlength="4"
                            style="width:140px"
                            size="mini"
                            @keyup.enter.native="handleInputConfirm"
                            @blur="handleInputConfirm"
                            >
                        </el-input>
                        <el-button v-else-if="this.info.slogans.length<6" class="tag_item" 
                        size="mini" @click="showInput" style="width:140px">
                            添加标语</el-button>
                    </div>
                </el-form-item>
                <el-form-item label="主要介绍:" size="mini">
                    <div class="text">{{ info.text }}</div>
                    <el-button class="tag_item" size="small " @click="edit">
                            查看、修改内容</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div class="image_view">
            <div class="logo">
                <el-upload
                  ref="logo"
                  list-type="picture"
                  :show-file-list="false"
                  :auto-upload="false"
                  :headers="Headers"
                  :on-success="success"
                  :on-change="logoChange" 
                  accept=".jpg,.jpge,png,.JPGE,.PNG"
                  action="/api/common/getLogoUrl"   
                  :limit="1">
                    <el-button class="tag_item" size="small" @click="clearFile('logo')">更换logo</el-button>
                </el-upload>
                <div style="margin-right:75px">
                    <div class="info_logo">
                        <el-image
                            style="width: 50px; height: 50px"
                            :src="info.logo"
                            :preview-src-list="[info.logo]"
                            fit="contain">
                        </el-image>
                    </div>
                </div>
            </div>
            <div class="back">
                <el-upload
                  ref="back"
                  :auto-upload="false" 
                  :show-file-list="false"
                  list-type="picture"
                  :headers="Headers"
                  :on-success="success1"
                  accept=".jpg,.jpge,png,.JPGE,.PNG"
                  action="/api/common/getBackUrl"
                  :on-change="backChange" 
                  :limit="1">
                    <el-button class="tag_item" size="small" @click="clearFile('back')">更换背景</el-button>
                </el-upload>
                <div>
                    <el-image
                        style="width: 200px; height: 350px"
                        :src="info.back"
                        :preview-src-list="[info.back]"
                        fit="contain">
                    </el-image>
                </div>
            </div>
            <div>
                <el-button class="tag_item" type="primary" @click="saveInfo">保存所有修改</el-button>
            </div>
        </div>
        <div class="preview">
            <div class="bor">
                <div class="hair"></div>
                <div class="line"></div>
            </div>
            <div class="statusbar">
                <div class="statusbar_item">
                    <div class="time">{{time}}</div>
                    <div class="bettery">
                        <div>{{number}}%</div>
                        <div>
                            <div>
                                <div :style="{width:number+'%'}"></div>
                            </div>
                            <div></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="inner" :style="styleObj">
                <div class="vessel">
                    <div class="pre_info">
                        <div class="top">
                            <div class="pre_logo">
                                <img :src="info.logo"/>
                            </div>
                            <div class="title">
                                <div>{{info.name}}</div>
                                <div v-if="info.slogans.length">
                                    <div v-for="(item,index) in info.slogans" :key="index" class="tag">
                                        <span>{{item}}</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="bottom">
                            <div class="pl">
                                <i class="el-icon-phone"></i>
                                <span>{{info.phone}}</span>
                            </div>
                            <div class="pl">
                                <i class="el-icon-location"></i>
                                <span>{{info.address}}</span>
                            </div>
                        </div>
                    </div>
                    <div class="sub_title">
                        驾校简介
                    </div>
                    <div class="introduce">
                        <div>{{info.text}}</div>
                    </div>
                </div>
                <div class="bottom_bar">
                    <div>
                        <i class="el-icon-s-home"></i>
                        <div>首页</div>
                    </div>
                    <div>
                        <i class="iconfont iconfill_jianjie"></i>
                        <div>驾校简介</div>
                    </div>
                    <div>
                        <i class="el-icon-user-solid"></i>
                        <div>个人中心</div>
                    </div>
                </div>
            </div>
        </div>
        <el-dialog title="查看、修改内容" :visible.sync="isShow" center top="80px" width="70%">
            <div contenteditable="true" ref="content" class="content" v-if="isShow">{{ text }}</div>
            <div style="text-align:center;margin: 10px;">
                <el-button class="tag_item" size="small " @click="saveText">保存修改</el-button>
            </div>
            
        </el-dialog>
    </div>
  </div>
</template>

<script>
export default {
    name:'Info',
    data(){
        return{
            time:'',
            number:100,
            inputVisible: false,
            isShow:false,
            inputValue: '',
            text:'',
            info:{
                name:'简单约练',
                phone:'13574576367',
                address:'梧州市万秀区富明街道富民三路82号',
                longitude:111.11,
                latitude:222.22,
                back:'',
                logo:'',
                slogans:['富强','民主','文明','和谐'],
                text:''
            },
            rules: {
                name: [{ required: true, message: '请填写驾校名称', trigger: 'blur' }],
                phone: [{ required: true, message: '请填写驾校热线', trigger: 'blur' },
                        { min: 11, max: 11, message: '输入11位号码', trigger: 'blur' }],
                address: [{ required: true, message: '请填写驾校地址', trigger: 'blur' }],
                longitude: [{ required: true, message: '请填写地址经度', trigger: 'blur' }],
                latitude: [{ required: true, message: '请填写地址纬度', trigger: 'blur' }],
            },
            Headers:{
                token:sessionStorage.getItem('token')
            },
        }
        
    },
    computed:{
        styleObj(){
               return{
                    background:`url(${this.info.back})  no-repeat top/contain`,
               }
        }
    },
    mounted(){
        this.getInfo()
        this.getTime()
        setInterval(()=>{
            this.getTime()
        },3000)
        setInterval(()=>{
            if(this.number>0)
                this.number-=1
        },120000)
    },
    methods:{
        async getInfo(){
            let res = await this.axios.get('/common/getInfo')
            this.info=res.data
        },
        getTime(){
            let h = new Date().getHours()
            let m = new Date().getMinutes()
            h=h<10?'0'+h:h
            m=m<10?'0'+m:m
            this.time = h + ':' + m
        },
        handleClose(tag){
            this.info.slogans.splice(this.info.slogans.indexOf(tag), 1);
        },
        showInput() {
            this.inputVisible = true;
            this.$nextTick(_ => {
            this.$refs.saveTagInput.focus();
            });
        },
        handleInputConfirm() {
            let inputValue = this.inputValue;
            if (inputValue) {
            this.info.slogans.push(inputValue);
            }
            this.inputVisible = false;
            this.inputValue = '';
        },
        edit(){
            this.isShow=true
            this.text=this.info.text
        },
        saveText(){
            this.info.text=this.$refs.content.innerText
            this.$message.success('保存成功！')
        },
        logoChange(file){
            this.info.logo=file.url
        },
        backChange(file){
            this.info.back=file.url
        },
        clearFile(name){
            this.$refs[name].clearFiles()
        },
        success(res){
            if(res.code===2000){
                this.info.logo=res.data
                this.axios.post('/common/saveInfo',this.info)
            }   
        },
        success1(res){
            if(res.code===2000){
                this.info.back=res.data
                this.axios.post('/common/saveInfo',this.info)
            }
        },
        saveInfo(){
            this.$confirm('是否保存修改?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(async () => {
                this.$refs['logo'].submit()
                this.$refs['back'].submit()

                let r = await this.axios.post('/common/saveInfo',this.info)
                if(r.code===2000){
                    this.$message.success(r.data)
                    this.getInfo()
                }
                else
                    this.$message.error(r.data)
            }).catch(() => {
                      
            });
        }
    }
}
</script>

<style scoped>
.view{
    width: 100%;
    display: flex;
    justify-content: space-between;
}
.slogan{
    width: 300px;
    display: flex;
    height: 90px;
    flex-wrap: wrap;
    justify-content: flex-start;
    align-content: flex-start;
}
.tag_item{
    margin: 5px 5px;
}
.text{
    width: 300px;
    max-height: 90px;
    -webkit-line-clamp: 3;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    overflow: hidden;
    text-overflow: ellipsis;
}
.content{
    height: 400px;
    line-height: 20px;
    box-sizing: border-box;
    padding: 8px;
    border-radius: 10px;
    overflow: hidden;
    overflow-y: auto;
    border: 1px solid gainsboro;
}
.content:focus{
    outline: 0.5px solid #409EFF;
}
.image_view{
    height: 550px;
    width: 400px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    text-align: left;
}

.logo,.back{
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.preview{
    width: 280px;
    height: 550px;
    position: relative;
    margin-top: -20px;
    border-radius: 25px;
    overflow: hidden;
}
.bor{
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    position: absolute;
    box-sizing: border-box;
    z-index: 999;
    border-radius: 25px;
    border: 10px solid rgb(35, 35, 35);
   
}
.hair{
    width: 50%;
    height: 20px;
    position: absolute;
    top: -2px;
    left: 50%;
    transform: translateX(-50%);
    border-radius:0 0 20px 20px;
    background: black;
}
.line{
    position: absolute;
    bottom: 3px;
    width: 40%;
    height: 2.5px;
    left: 50%;
    border-radius:0 1px;
    transform: translateX(-50%);
    background: black;
}
.statusbar{
  height: 15px;
  width: 100%;
  position: absolute;
  top: 10px;
}
.statusbar_item{
    height: 15px;
    display: flex;
    justify-content: space-between;
}
.time,.bettery{
    color: black;
    font-size: 12px;
    width: 20px;
    margin: 5px 0px 0px 30px;
}
.bettery{
    display: flex;
    justify-content: space-around;
    align-items: center;
    margin: 10px 15px 0px 0px;
    width: 60px;
}
.bettery>div:last-child{
    display: flex;
    width: 50%;
    align-items: center;
    margin-left: 3px;
}
.bettery>div>div:first-child{
    width: 20px;
    height: 12px;
    border-radius: 2px;
    box-sizing: border-box;
    padding: 0.5px;
    border: 1px solid black;
    background: white;
}
.bettery>div>div:last-child{
    width: 1.5px;
    height: 4px;
    background: black;
}
.bettery>div>div>div{
    width: 100%;
    height: 100%;
    background: rgb(29, 29, 29);
}

.inner{
    width:100%;
    height: 100%;
    overflow: hidden;
    box-sizing: border-box;
    padding: 0px 9px;
    background-size: contain;
}
.vessel{
    width: 100%;
    height: 60%;
    display: flex;
    color: white;
    margin-top: 220px;
    align-items: center;
    flex-direction: column;
    border-radius: 50px 50px 0 0;
    background-image: linear-gradient(45deg,rgb(61,81,252,0.95),rgb(0,254,244,0.95));
}
.pre_info{
    width: 100%;
    height: 20%;
    display: flex;
    margin-top: 25px;
    font-weight: bold;
    flex-direction: column;
    justify-content: space-around;
}
.top,.bottom{
    width: 100%;
    display: flex;
    margin-left: 20px;
}
.pre_info>.top>.pre_logo,.info_logo{
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 50%;
    width: 50px;
    height: 50px;
    border: 1px solid white;
}
.info_logo{
    width: 80px;
    height: 80px;
    cursor: pointer;
    border: 1px solid #0081ff;
}
.pre_info>.top>.pre_logo>img{
    width: 30px;
    height: 30px;
}
 .title{
    font-size: 14px;
    margin-left: 5px;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    justify-content: space-around;
}
.title>div:last-child{
    display: flex;
    width:180px;
    flex-wrap: wrap;
}
.tag{
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 12px;
    min-width: 20px;
    box-sizing: border-box;
    padding: 1px;
    border-radius: 10px;
    margin: 1px;
    height: 10px;
    background: #0081ff;
}
.tag>span{
    display: block;
    transform: scale(0.6);
}

.bottom{
    font-size: 12px;
    flex-direction: column;
    align-items: flex-start;
    justify-content: space-between;
    height: 40px;
    margin: 10px 0 0px 20px;
}
.pl{
    margin: 3px 0px;
}
.sub_title{
    color: #0081ff;
    width: 25%;
    margin-top: 20px;
    border-radius: 10px;
    height: 20px;
    display: flex;
    background: white;
    align-items: center;
    justify-content: center;
    transform: scale(0.9);
    font-size: 12px;
}
.introduce{
    text-align: left;
    width: 100%;
    padding: 10px;
    font-size: 12px;
    box-sizing: border-box;
    line-height: 16px;
    overflow: hidden; 
}
.bottom_bar{
    color: rgb(193, 193, 193);
    width: 100%;
    height: 40px;
    display: flex;
    align-items: center;
    justify-content: space-around;
    position: absolute;
    bottom: 10px;
    background: white;
}
.bottom_bar>div{
    display: flex;
    flex-direction: column;
    justify-content: center;
}
.bottom_bar>div:nth-child(2){
    color: #0081ff;
}
.bottom_bar>div>div{
    font-size: 12px;
    transform: scale(0.7);
}
</style>