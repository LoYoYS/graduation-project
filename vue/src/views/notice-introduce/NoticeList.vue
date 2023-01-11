<template>
  <div>
    <el-empty v-if="!this.noticeList.length" description="暂无公告" 
    style="height:550px" v-loading="loading">
    </el-empty>
    <div v-else class="noticeList"  v-loading="loading">
        <div class="notice_item" @click="goToNotice(item.id)" v-for="item of noticeList" :key="item.id">
            <div class="left">
                <div class="title">{{item.title}}</div>
                <div class="time">{{item.time}}</div>
                <div class="content">
                {{item.content}}
                </div>
            </div>
            <el-dropdown @command="handleCommand">
                <i class="el-icon-more" style="font-size:30px"></i>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item icon="el-icon-edit-outline" :command="'1,'+item.id">编辑</el-dropdown-item>
                    <el-dropdown-item icon="el-icon-delete-solid" :command="'2,'+item.id">删除</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
        <el-pagination
          background
          class="page"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          layout="total, prev, pager, next"
          :total="total">
        </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
    name:'NoticeList',
    data(){
        return{
            loading:true,
            noticeList:[],
            currentPage: 1,
            total:0
        }
    },
    created(){
        this.getNotices()
    },
    methods: {
        async getNotices(){
           let result = await this.axios.get(`/common/list?page=${this.currentPage}`)
           if(result.code===2000){
            this.noticeList=result.data.list
            this.total=result.data.total
            this.loading=false
           }
        },
        handleCurrentChange(index){
            this.loading=true
            this.currentPage=index
            this.getNotices()
        },
        handleCommand(command){
            let arr = command.split(',')
            let id = +arr[1]
            // 编辑
            if(arr[0]==='1')
                this.goToNotice(arr[1])
            // 删除
            else{
                this.$confirm('确定删除吗？','提示',{
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type:"error"
            }).then(async () => {
                let res = await this.axios.delete(`/common/delete?id=${id}`)
                if(res.code===2000){
                    this.$message.success(res.data)
                    this.getNotices()
                }
                else
                    this.$message.error(res.data)
            }).catch(() => {
                return
            })
            }
        },
        goToNotice(id){
            this.$router.push({
             name:'编辑、发布公告',
             params:{id:id}
            })
        }
    },
}
</script>

<style scoped>
.noticeList{
    perspective: 100px;
}
.notice_item{
    width: 100%;
    cursor: pointer;
    position: relative;
    transform-style: preserve-3d;
    overflow: hidden;
    height: 150px;
    border-radius:6px;
    margin-top: 15px;
    transition: all 0.3s;
    border: 0.5px solid rgb(237, 237, 237);
    box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.12), 0 0 3px 0 rgba(0, 0, 0, 0.04);
}
.notice_item:hover{
    transform: translateZ(2px);
    box-shadow: 1px 1px 3px 0 rgba(0, 0, 0, 0.2), 0 0 3px 0 rgba(0, 0, 0, 0.2);
}
.left{
    margin: 10px 0px 0px 20px;
    text-align: left;
    width: 70%;
    height: 100%;
}
.title{
    height: 40px;
    font-size: 25px;
    font-weight: bold;
    line-height: 35px;
    -webkit-line-clamp: 1;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    overflow: hidden;
    text-overflow: ellipsis;
}
.time{
    font-size: 12px;
    width: 200px;
    color: rgb(127, 127, 127);
}
.content{
    position: relative;
    height: 64px;
    margin: 10px 0px;
    text-indent: 35px;
    line-height: 22px;
    -webkit-line-clamp: 3;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    overflow: hidden;
    text-overflow: ellipsis;
}
.el-dropdown{
    position: absolute;
    top: 0;
    width: 40px;
    height: 40px;
    right: 10px;
}
.page{
    margin: 10px auto;
}
</style>