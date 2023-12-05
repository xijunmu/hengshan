<template>
  <div>
    <div class="container">
      <div class="handle-box">
        <el-input v-model="query.name" placeholder="用户名" class="handle-input mr10"></el-input>
        <el-button type="primary" :icon="Search" @click="handleSearch">搜索</el-button>
        <el-button type="primary" :icon="Plus" @click="openDialog()">新增</el-button>
        <el-button type="danger" @click="removeRows">批量删除</el-button>
        <el-button type="success" @click="exportExcel">导出数据</el-button>
      </div>
      <el-table v-loading="loading" :data="tableData" border class="table" header-cell-class-name="table-header"
        ref="multipleTableRef" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="ID" width="55"></el-table-column>
        <el-table-column prop="name" label="姓名" width="100"></el-table-column>
        <el-table-column prop="price" label="价格" width="100">
          <template #default="scope">￥{{ scope.row.price }}</template>
        </el-table-column>
        <el-table-column prop="phone" label="电话" width="150"></el-table-column>
        <el-table-column prop="address" label="地址"></el-table-column>
        <el-table-column prop="product" label="产品" width="100" align="center"></el-table-column>
        <el-table-column prop="createTime" label="时间" width="180"></el-table-column>
        <el-table-column label="操作" align="center">
          <template #default="scope">
            <el-button text :icon="Edit" @click="openDialog(scope.row)"> 编辑 </el-button>
            <el-button text :icon="Delete" class="red" @click="handleDelete(scope.row.id)"> 删除 </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination background layout="total, sizes, prev, pager, next, jumper" :current-page="query.page"
          :page-size="query.limit" :page-sizes="[10, 20, 50]" :total="pageTotal" @current-change="handlePageChange"
          @size-change="handleSizeChange"></el-pagination>
      </div>
    </div>

    <!-- 编辑或新增弹出框 -->
    <el-dialog :title="isEdit === true ? '编辑用户' : '新增用户'" v-model="dialogVisible" width="30%" @close="closeDialog">
      <el-form :model="form" :rules="rules" ref="orderform" label-width="70px">
        <el-form-item :style="{ 'display': 'none' }" label="id" prop="id">
          <el-input v-model="form.id"></el-input>
        </el-form-item>
        <el-form-item label="用户名" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="form.address"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="closeDialog">取 消</el-button>
          <el-button type="primary" @click="submit">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import { reactive, ref, onMounted, nextTick } from 'vue'
import { ElMessage, ElMessageBox, type FormInstance } from 'element-plus'
import { Delete, Edit, Search, Plus } from '@element-plus/icons-vue'
import { getOrderList, deleteOrder, updateOrder, addOrder } from '@/api/common'
import type { PageList, Order } from '@/api/commonType'
import * as xlsx from 'xlsx'

const tableData = ref<Order[]>([])
const pageTotal = ref(0)
const dialogVisible = ref(false)
const isEdit = ref(false)
const loading = ref(true)
const orderform = ref<FormInstance>()
const form = reactive({
  id: 0,
  name: '',
  address: '',
  price: 0,
  phone: '',
  createTime: new Date()
})
const query = reactive<PageList>({
  name: '',
  page: 1,
  limit: 10
})
const rules = reactive({
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  address: [{ required: true, message: '请输入地址', trigger: 'blur' }]
})

// 查询表格数据
const getData = () => {
  loading.value = true
  void getOrderList(query).then(res => {
    // console.log(res.data)
    tableData.value = res.data.list
    pageTotal.value = res.data.pageTotal
  }).finally(() => {
    loading.value = false
  })
}

// 条件查询
const handleSearch = () => {
  query.page = 1
  getData()
}

// 分页查询
const handlePageChange = (val: number) => {
  query.page = val
  getData()
}
const handleSizeChange = (val: number) => {
  query.limit = val
  query.page = 1
  getData()
}

// 删除
const handleDelete = (id: number) => {
  ElMessageBox.confirm('确定要删除吗？', '提示', {
    type: 'warning'
  }).then(() => {
    ElMessage.success('删除成功')
    void deleteOrder({ id: [id] })
    getData()
  }).catch(() => { })
}

// 批量删除
const selections = ref<Order[]>([])
const handleSelectionChange = (val: Order[]) => {
  selections.value = val
}
const removeRows = () => {
  if (selections.value.length === 0) {
    ElMessage.warning('请至少勾选一条数据')
  } else {
    console.log(selections.value.map(item => item.id))
    void deleteOrder({ id: selections.value.map(item => item.id) })
    getData()
  }
}

// 增加或编辑提交
const submit = async () => {
  await orderform.value?.validate((valid: boolean) => {
    if (valid) {
      if (isEdit.value) {
        void updateOrder(form)
        ElMessage.success('修改成功')
      } else {
        void addOrder(form)
        ElMessage.success('添加成功')
      }
      dialogVisible.value = false
      getData()
    }
  })
}

// 增加或编辑弹出框
const openDialog = async (row?: any) => {
  dialogVisible.value = true
  if (row) {
    isEdit.value = true
    // nextTick要放在函数的最后一步，如果放在dialogShow前，nextTick将不会产生作用
    await nextTick(() => {
      Object.assign(form, row)
    })
  } else {
    isEdit.value = false
  }
}

// 关闭弹出框
const closeDialog = () => {
  orderform.value?.resetFields()
  orderform.value?.clearValidate()
  dialogVisible.value = false
}

// excel导出
const exportExcel = async () => {
  const header: Record<string, string> = {
    id: '编号',
    name: '名称',
    price: '价格',
    address: '地址',
    product: '产品',
    phone: '电话号码',
    createTime: '时间'
  }
  if (selections.value.length === 0) {
    ElMessage.warning('请至少勾选一条数据')
    return
  }
  const data = selections.value.map((item: any) => {
    const obj: Record<string, any> = {}
    for (const key in item) {
      obj[header[key]] = item[key]
    }
    return obj
  })
  const wb = xlsx.utils.book_new()
  const sheet = xlsx.utils.json_to_sheet(data)
  xlsx.utils.book_append_sheet(wb, sheet)
  xlsx.writeFile(wb, 'data.xlsx', { bookType: 'xlsx' })
}

onMounted(() => {
  getData()
})
</script>

<style scoped>
.handle-box {
  margin-bottom: 8px;
}

.handle-select {
  width: 120px;
}

.handle-input {
  width: 300px;
}

.table {
  width: 100%;
  font-size: 14px;
}

.el-table__row {
  height: 40px;
}

.pagination {
  margin: 10px 0;
  float: right;
}

.red {
  color: #F56C6C;
}

.mr10 {
  margin-right: 8px;
}

.table-td-thumb {
  display: block;
  margin: auto;
  width: 40px;
  height: 30px;
}
</style>
