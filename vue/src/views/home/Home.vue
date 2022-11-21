<template>
  <div>
    <div v-loading="loading">
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
      <div style="height: 400px;width: 100%;" id="myChart">
      </div>
    </el-card>
    </div>
    
  </div>
</template>

<script>
let echarts = require('echarts/lib/echarts')
require('echarts/lib/chart/bar')
require('echarts/lib/component/tooltip')
require('echarts/lib/component/legend')
require('echarts/lib/component/grid')
export default {
    name:'Home',
    data(){
      return {
        loading:true,
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
          },
          legend: {
            x:'right',
            data:['数量统计'],
            itemWidth:30,
            itemHeight:15,
            textStyle:{
              color:'#0081ff',
              fontSize:16
            }
          },
          xAxis:{
           name:'类型', 
           data:['学员数量','教练数量','用户数量','车辆数量'],
           axisLine: {
              show:true,
              lineStyle: {
                color: '#048CCE'
              }
            }
          },
          yAxis: {
            type:'value',
            name:'数量',
            axisLine: {
              show:true,
              lineStyle: {
                color: '#048CCE'
              }
            }
        },
        series: [
          {
            name:'数量统计',
            type:'bar',
            data:[],
            smooth: true,
            barWidth:80,
            itemStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: '#6851f1'
              }, {
                  offset: 1,
                  color: '#00DDFF'
              }]),
              borderRadius: 20,
            }
          }
        ]
        }
      }
    },
    created(){
      this.getData()
    },
    methods:{
      async getData(){
        let res = await this.axios.post('/notice/getNumber')
        if(res.code===2000){
          this.studentNumber=res.data.studentCount
          this.coachNumber=res.data.coachCount
          this.userNumber=res.data.userCount
          this.carNumber=res.data.carCount
          this.viewNumber.series[0].data=[
            res.data.studentCount,
            res.data.coachCount,
            res.data.userCount,
            res.data.carCount
          ]
          this.getCharts(this.viewNumber)
          this.loading=false
        }
      },
      getCharts(option){
        let myChart = echarts.init(document.getElementById('myChart'))
        myChart.setOption(option)
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
    background-image: linear-gradient(rgb(0, 214, 252),rgb(61,81,252));
  }
  .card:hover,.card:hover *{
    color: white !important;
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