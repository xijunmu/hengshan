<template>
  <div>
    <div class="container">
      <div class="handle-box">
        <el-select v-model="query.address" placeholder="地址" class="handle-select mr10">
          <el-option key="1" label="广东省" value="广东省"></el-option>
          <el-option key="2" label="湖南省" value="湖南省"></el-option>
        </el-select>
        <el-input v-model="query.name" placeholder="用户名" class="handle-input mr10"></el-input>
        <el-button type="primary" :icon="Search" @click="handleSearch">搜索</el-button>
        <el-button type="primary" :icon="Plus" @click="openDialog()">新增</el-button>
        <el-button type="danger" @click="removeRows">批量删除</el-button>
      </div>
      <el-table v-loading="loading" :data="tableData" border class="table" header-cell-class-name="table-header"
        ref="multipleTableRef" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="ID" width="55"></el-table-column>
        <el-table-column prop="name" label="姓名" width="100"></el-table-column>
        <el-table-column prop="age" label="年龄" width="55"></el-table-column>
        <el-table-column prop="sex" label="性别" width="55">
          <template #default="scope">
            {{ scope.row.sex === 0 ? '女' : '男' }}
          </template>
        </el-table-column>
        <el-table-column label="头像(查看大图)" width="120" align="center">
          <template #default="scope">
            <el-image class="table-td-thumb" :src="scope.row.thumb" :z-index="10" :preview-src-list="[scope.row.thumb]"
              preview-teleported> </el-image>
          </template>
        </el-table-column>
        <el-table-column prop="address" label="地址"></el-table-column>
        <el-table-column prop="state" label="状态" width="100" align="center">
          <template #default="scope">
            <el-tag :type="scope.row.state === true ? 'success' : scope.row.state === false ? 'danger' : ''">
              {{ scope.row.state === true ? "正常" : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="date" label="注册时间" width="200"></el-table-column>
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
      <el-form :model="form" :rules="rules" ref="userform" label-width="70px">
        <el-form-item :style="{ 'display': 'none' }" label="id" prop="id">
          <el-input v-model="form.id"></el-input>
        </el-form-item>
        <el-form-item label="用户名" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model="form.age"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-select v-model="form.sex" placeholder="请选择">
            <el-option label="男" :value="1" default></el-option>
            <el-option label="女" :value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="form.address"></el-input>
        </el-form-item>
        <el-form-item label="日期" prop="date">
          <el-date-picker value-format="yyyy-MM-DD" type="date" placeholder="日期" v-model="form.date"
            style="width: 100%;"></el-date-picker>
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
import { getUserList, deleteUser, updateUser, addUser } from '@/api/common'
import type { PageList, IUser } from '@/api/commonType'

const tableData = ref<IUser[]>([])
const pageTotal = ref(0)
const dialogVisible = ref(false)
const isEdit = ref(false)
const loading = ref(true)
const userform = ref<FormInstance>()
const form = reactive({
  id: 0,
  name: '',
  age: null,
  address: '',
  state: true,
  sex: 1,
  date: new Date()
})
const query = reactive<PageList>({
  address: '',
  name: '',
  page: 1,
  limit: 10
})
const rules = reactive({
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  sex: [{ required: true, message: '请选择性别', trigger: 'change' }],
  date: [{ required: true, message: '请选择日期', trigger: 'change' }],
  age: [{ required: true, message: '请输入年龄', trigger: 'blur' }],
  address: [{ required: true, message: '请输入地址', trigger: 'blur' }]
})

// 查询表格数据
const getData = () => {
  loading.value = true
  void getUserList(query).then(res => {
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
    void deleteUser({ id: [id] })
    getData()
  }).catch(() => { })
}

// 批量删除
const selectionIds = ref<number[]>([])
const handleSelectionChange = (val: IUser[]) => {
  selectionIds.value = val.map(item => item.id)
}
const removeRows = () => {
  if (selectionIds.value.length === 0) {
    ElMessage.warning('请至少勾选一条数据')
  } else {
    console.log(selectionIds.value)
    void deleteUser({ id: selectionIds.value })
    getData()
  }
}

// 增加或编辑提交
const submit = async () => {
  await userform.value?.validate((valid: boolean) => {
    if (valid) {
      if (isEdit.value) {
        void updateUser(form)
        ElMessage.success('修改成功')
      } else {
        void addUser(form)
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
      // form = JSON.parse(JSON.stringify(row))
      Object.assign(form, row)
    })
  } else {
    isEdit.value = false
  }
}

// 关闭弹出框
const closeDialog = () => {
  userform.value?.resetFields()
  userform.value?.clearValidate()
  dialogVisible.value = false
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
