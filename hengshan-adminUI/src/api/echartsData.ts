import request from '@/utils/request'

export const getLineData = () => {
  return request.post('/getLineData')
}
export const getPieData = () => {
  return request.post('/getPieData')
}
export const getBarData = () => {
  return request.post('/getBarData')
}
export const getTableData = () => {
  return request.post('/getTableData')
}
