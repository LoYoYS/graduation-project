<template>
    <div>
        <div class="notice">
            <div ref="title" contenteditable="true" class="title" 
            v-text="notice.title" placeholder="在此输入公告标题"></div>
            <div ref="content" class="content" contenteditable="true" 
            v-text="notice.content" placeholder="在此输入公告内容"></div>
        </div>
        <el-button class="btn" type="primary" @click="saveNotice">保存</el-button>
    </div>
</template>

<script>
export default {
    name:'Notice',
    data(){
        return{
            notice:{
                id:'',
                title:'',
                content:'',
            },
        }
    },
    created(){
        this.getNotice()
    },
    methods: {
        async getNotice(){
            let id =this.$route.params.id
            if(id){
                let res = await this.axios.get(`/notice/getNotice?id=${id}`)
                if(res.code===2000)
                    this.notice=res.data 
            }
            return
        },
        saveNotice(){
            let title=this.$refs.title.innerHTML
            let content=this.$refs.content.innerHTML
            if(!title.trim()){
                this.$message.error('请输入标题')
                return
            }
            if(!content.trim()){
                this.$message.error('请输入内容')
                return
            }
            this.$confirm('确定保存并提交？','提示',{
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type:"warning"
            }).then(() => {
                this.notice.title=title
                this.notice.content=content
                if(!this.notice.id){
                   this.axios.post('/notice/save',this.notice).then((res)=>{
                    if(res.code===2000){
                        this.$message.success(res.data)
                        this.notice.title=''
                        this.notice.content=''
                    } 
                    else
                        this.$message.error(res.data)
                   }) 
                }
                else{
                    this.axios.post('/notice/update',this.notice).then((res)=>{
                     if(res.code===2000)
                        this.$message.success(res.data)
                     else
                        this.$message.error(res.data)
                    })
                }
            }).catch(() => {
                return
            })
            return    
        }    
    },
}
</script>

<style scoped>
.notice{
    perspective: 100px;
    position: relative;
}
.title{
    height: 40px;
    white-space: nowrap;
    box-sizing: border-box;
    padding: 5px;
    user-select: text;
    transform-style: preserve-3d;
    transition: all 0.3s;
    font-size: 25px;
    overflow: hidden;
    font-weight: bold;
    border-radius: 5px;
}
div:empty:before{
content: attr(placeholder);
color:rgb(161, 161, 161);
}
div:focus:before{
content:none;
}
.notice div:focus,.notice div:hover{
    transform: translateZ(2px);
    outline: 0.5px solid #409EFF;
}
.content{
    width: 800px;
    margin: 10px auto 0 auto;
    text-align: left;
    text-indent: 35px;
    line-height: 20px;
    box-sizing: border-box;
    transform-style: preserve-3d;
    transition: all 0.3s;
    padding: 5px;
    user-select: text;
    border-radius: 8px;
    background-color: rgb(245, 245, 245);
    min-height: 500px;
}
.btn{
    position: absolute;
    bottom: 20px;
    right: 20px;
    width: 100px;
    height: 50px;
}
</style>