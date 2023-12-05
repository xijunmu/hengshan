<template>
  <el-row :gutter="20">
    <el-col :span="8">
      <el-card shadow="hover" class="mgb20" style="height: 252px">
        <div class="user-info">
          <el-avatar :size="120" :src="imgurl" />
          <div class="user-info-cont">
            <div class="user-info-name">{{ name }}</div>
            <div>{{ role }}</div>
          </div>
        </div>
        <div class="user-info-list">
          上次登录时间：
          <span>2023-10-20</span>
        </div>
        <div class="user-info-list">
          上次登录地点：
          <span>北京</span>
        </div>
      </el-card>
      <el-card shadow="hover" style="height: 252px">
        <template #header>
          <div class="clearfix">
            <span>语言详情</span>
          </div>
        </template>
        Vue
        <el-progress :percentage="79.4" color="#42b983"></el-progress>
        TypeScript
        <el-progress :percentage="14" color="#f1e05a"></el-progress>
        CSS
        <el-progress :percentage="5.6"></el-progress>
        HTML
        <el-progress :percentage="1" color="#f56c6c"></el-progress>
      </el-card>
      <el-card shadow="hover">
        <el-table :data="tableData">
          <el-table-column v-for="(val, key) in tableLabel" :prop="key" :label="val" :key="key"></el-table-column>
        </el-table>
      </el-card>
    </el-col>
    <el-col :span="16">
      <el-row :gutter="20" class="mgb20">
        <el-col :span="8">
          <el-card shadow="hover" :body-style="{ padding: '0px' }">
            <div class="grid-content grid-con-1">
              <el-icon class="grid-con-icon">
                <User />
              </el-icon>
              <div class="grid-cont-right">
                <div class="grid-num">1234</div>
                <div>用户访问量</div>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card shadow="hover" :body-style="{ padding: '0px' }">
            <div class="grid-content grid-con-2">
              <el-icon class="grid-con-icon">
                <ChatDotRound />
              </el-icon>
              <div class="grid-cont-right">
                <div class="grid-num">321</div>
                <div>系统消息</div>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card shadow="hover" :body-style="{ padding: '0px' }">
            <div class="grid-content grid-con-3">
              <el-icon class="grid-con-icon">
                <Goods />
              </el-icon>
              <div class="grid-cont-right">
                <div class="grid-num">5000</div>
                <div>商品数量</div>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-card shadow="hover">
            <div ref="echarts3" style="height: 240px"></div>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card shadow="hover">
            <div ref="echarts2" style="height: 240px"></div>
          </el-card>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col>
          <el-card shadow="hover">
            <div ref="echarts1" style="height: 260px"></div>
          </el-card>
        </el-col>
      </el-row>
    </el-col>
  </el-row>
</template>
<script lang="ts" setup>
import { getBarData, getLineData, getPieData, getTableData } from '@/api/echartsData'
import * as echarts from 'echarts'
import { onMounted, ref, reactive } from 'vue'
import imgurl from '@/assets/user.jpeg'

const name = localStorage.getItem('user')
const role: string = name === 'admin' ? '超级管理员' : '普通用户'
const tableLabel = {
  name: '品牌',
  todayBuy: '今日销售',
  monthBuy: '月度销售',
  totalBuy: '总销售'
}
const tableData = ref<any[]>([])
const echarts1 = ref<HTMLElement>()
const echarts2 = ref<HTMLElement>()
const echarts3 = ref<HTMLElement>()
// 饼状图
const initPieChart = async () => {
  const pieChart = echarts.init(echarts3.value as HTMLElement)
  const pieData = ref<any[]>([])
  await getPieData().then(res => {
    pieData.value = res.data.list
    const echarts3Option = {
      tooltip: { trigger: 'item' },
      series: [
        {
          data: pieData.value,
          type: 'pie'
        }
      ]
    }
    // console.log(JSON.stringify(echarts3Option))
    pieChart.setOption(echarts3Option)
  })
}

// 柱状图
const initBarChart = async () => {
  const barChart = echarts.init(echarts2.value as HTMLElement)
  const barData = ref<any[]>([])
  await getBarData().then(res => {
    barData.value = res.data.list
    const echarts2Option = {
      legend: { textStyle: { color: '#333' } },
      grid: { left: '20%' },
      tooltip: { trigger: 'axis' },
      xAxis: {
        type: 'category',
        data: barData.value.map(item => item.date),
        axisLine: { lineStyle: { color: '#17b3a3' } },
        axisLabel: { interval: 0, color: '#333' }
      },
      yAxis: [
        {
          type: 'value',
          axisLine: { lineStyle: { color: '#17b3a3' } }
        }
      ],
      color: ['#2ec7c9', '#b6a2de'],
      series: [
        {
          name: '新增用户',
          data: barData.value.map(item => item.new),
          type: 'bar'
        },
        {
          name: '活跃用户',
          data: barData.value.map(item => item.active),
          type: 'bar'
        }
      ]
    }
    // console.log(JSON.stringify(echarts2Option))
    barChart.setOption(echarts2Option)
  })
}

// 折线图
const initLineChart = async () => {
  const lineChart = echarts.init(echarts1.value as HTMLElement)
  const lineData = ref<any[]>([])
  await getLineData().then(res => {
    lineData.value = res.data.list
    const echarts1Option = reactive<any>({})
    const legend = ['apple', 'huawei', 'xiaomi']
    const xAxis = lineData.value.map(item => item.date)
    echarts1Option.xAxis = { data: xAxis }
    echarts1Option.yAxis = {}
    echarts1Option.legend = { data: legend }
    echarts1Option.series = []
    legend.forEach(key => {
      echarts1Option.series.push({
        name: key,
        data: lineData.value.map(item => item[key]),
        type: 'line'
      })
    })
    // console.log(JSON.stringify(echarts1Option))
    lineChart.setOption(echarts1Option)
  })
}

onMounted(async () => {
  void getTableData().then(res => {
    tableData.value = res.data.list
  })
  await initPieChart()
  await initBarChart()
  await initLineChart()
})
</script>
<style scoped>
.el-row {
  margin-bottom: 20px;
}

.grid-content {
  display: flex;
  align-items: center;
  height: 100px;
}

.grid-cont-right {
  flex: 1;
  text-align: center;
  font-size: 14px;
  color: #999;
}

.grid-num {
  font-size: 30px;
  font-weight: bold;
}

.grid-con-icon {
  font-size: 50px;
  width: 100px;
  height: 100px;
  text-align: center;
  line-height: 100px;
  color: #fff;
}

.grid-con-1 .grid-con-icon {
  background: rgb(45, 140, 240);
}

.grid-con-1 .grid-num {
  color: rgb(45, 140, 240);
}

.grid-con-2 .grid-con-icon {
  background: rgb(100, 213, 114);
}

.grid-con-2 .grid-num {
  color: rgb(100, 213, 114);
}

.grid-con-3 .grid-con-icon {
  background: rgb(242, 94, 67);
}

.grid-con-3 .grid-num {
  color: rgb(242, 94, 67);
}

.user-info {
  display: flex;
  align-items: center;
  padding-bottom: 20px;
  border-bottom: 2px solid #ccc;
  margin-bottom: 20px;
}

.user-info-cont {
  padding-left: 50px;
  flex: 1;
  font-size: 14px;
  color: #999;
}

.user-info-cont div:first-child {
  font-size: 30px;
  color: #222;
}

.user-info-list {
  font-size: 14px;
  color: #999;
  line-height: 25px;
}

.user-info-list span {
  margin-left: 70px;
}

.mgb20 {
  margin-bottom: 20px;
}

.todo-item {
  font-size: 14px;
}

.todo-item-del {
  text-decoration: line-through;
  color: #999;
}

.schart {
  width: 100%;
  height: 300px;
}
</style>
