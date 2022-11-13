<template>
  <div>
    <el-row :gutter="50">
      <el-col :span="6">
        <div class="card">
          <div class="icon">
            <i class="iconfont iconrenqun icon_class" style="color:#1cbbb4;font-size: 4rem;"></i>
          </div>
          <div class="title">
            <div>学员数</div>
            <div style="color:rgb(130, 130, 130);">{{studentNumber}}</div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="card">
          <div class="icon">
            <i class="el-icon-s-custom" style="color:#0081ff"></i>
          </div>
          <div class="title">
            <div>教练数</div>
            <div style="color:rgb(130, 130, 130);">{{coachNumber}}</div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="card">
          <div class="icon">
            <i class="iconfont icon24gf-userGroup2 icon_class" style="color:#39b54a;"></i>
          </div>
          <div class="title">
            <div>用户数</div>
            <div style="color:rgb(130, 130, 130);">{{userNumber}}</div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="card">
          <div class="icon">
            <i class="iconfont icon-cheliang icon_class" style="color:#9c26b0;"></i>
          </div>
          <div class="title">
            <div>车辆数</div>
            <div style="color:rgb(130, 130, 130);">{{carNumber}}</div>
          </div>
        </div>
      </el-col>
    </el-row>
    <el-card style="margin-top: 4rem">
      <div style="text-align:left;">柱形图</div>
      <div style="height: 350px">
        <!-- <v-chart :option="viewNumber" v-loading="loading" /> -->
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
    name:'Home',
    data(){
      return {
        loading:false,
        studentNumber:0,
        coachNumber:0,
        userNumber:0,
        carNumber:0,
        viewNumber:{
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross'
            },
          legend: {
            data: ['学员数','教练数','用户数','车辆数']
          },
          xAxis:{
            data: ['学员数','教练数','用户数','车辆数'],
            // type:category,
            // data:[]
          },
          yAxis: {
            type:'value',
            axisLine: {
              lineStyle: {
                // 设置y轴颜色
                color: '#048CCE'
              }
            }
        },
        series: [
          {
            name: '学员数',
            type: 'bar',
            value: 300,
            smooth: true
          },
          {
            name: '教练数',
            type: 'bar',
            value: 300,
            smooth: true
          },
          {
            name: '用户数',
            type: 'bar',
            value: 300,
            smooth: true
          },
          {
            name: '车辆数',
            type: 'bar',
            value: 300,
            smooth: true
          }
        ]
        },
        }
      }
    },
    created(){
      this.getData()
    },
    methods:{
      async getData(){
        let res = await this.axios.post('/api/notice/getNumber')
        if(res.code===2000){
          this.studentNumber=res.data.studentCount
          this.coachNumber=res.data.coachCount
          this.userNumber=res.data.userCount
          this.carNumber=res.data.carCount
        }
      }
    }
}
</script>

<style scoped>
  .el-row {
    margin-bottom: 20px;
   
  }
  .el-col {
    border-radius: 4px;
    height: 50px;
    perspective:80px;
  }
  .card{
    height: 100px;
    display: flex;
    padding: 10px;
    transition: all 0.2s;
    border-radius: 5px;
    align-items: center;
    box-sizing: border-box;
    justify-content: space-around;
    box-shadow: 1px 1px 5px gainsboro;
    
  }
  .card:hover{
    transform: translateZ(2px);
  }
  .title{
    height: 100%;
    display: flex;
    font-size: 25px;
    color: rgb(162, 162, 162);
    font-weight: bold;
    flex-direction: column;
    justify-content: space-around;
  }
  .icon,.icon_class{
    font-size: 3.5rem;
  }
</style>