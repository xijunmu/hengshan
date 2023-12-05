import type { MockMethod } from 'vite-plugin-mock'
import Mock from 'mockjs'

const LineData = Mock.mock({
  'data|7': [{
    'date|+1': 20221011,
    apple: '@integer(1000, 8000)',
    huawei: '@integer(1000, 8000)',
    xiaomi: '@integer(1000, 8000)'
  }]
})

const PieData = Mock.mock({
  'data|3': [{
    'name|+1': ['苹果', '华为', '小米'],
    value: '@integer(2000, 4000)'
  }]
})

const BarData = Mock.mock({
  'data|7': [{
    'date|+1': ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
    new: '@integer(100, 200)',
    active: '@integer(100, 500)'
  }]
})

const TableData = Mock.mock({
  'data|3': [{
    'name|+1': ['苹果', '华为', '小米'],
    todayBuy: '@integer(10, 100)',
    monthBuy: '@integer(500, 2000)',
    totalBuy: '@integer(5000, 9000)'
  }]
})

export default [
  {
    url: '/getLineData',
    method: 'post',
    response: () => {
      return { code: 200, list: LineData.data }
    }
  },
  {
    url: '/getPieData',
    method: 'post',
    response: () => {
      return { code: 200, list: PieData.data }
    }
  },
  {
    url: '/getBarData',
    method: 'post',
    response: () => {
      return { code: 200, list: BarData.data }
    }
  },
  {
    url: '/getTableData',
    method: 'post',
    response: () => {
      return { code: 200, list: TableData.data }
    }
  }
] as MockMethod[]
