<template>
  <div>
    <div class="container">
      <div class="handle-box">
        <el-input v-model="query.name" placeholder="角色名" class="handle-input mr10"></el-input>
        <el-button type="primary" :icon="Search" @click="handleSearch">搜索</el-button>
        <el-button type="primary" :icon="Plus" @click="openDialog(null)">新增</el-button>
      </div>
      <el-table v-loading="loading" :data="tableData" border class="table" header-cell-class-name="table-header"
        ref="multipleTableRef">
        <el-table-column prop="id" :style="{ 'display': 'none' }" label="ID" width="55"></el-table-column>
        <el-table-column prop="name" label="名称" width="100"></el-table-column>
        <el-table-column prop="menu_id" label="权限"></el-table-column>
        <el-table-column prop="state" label="状态" width="80" align="center">
          <template #default="scope">
            <el-switch v-model="scope.row.state" class="ml-2" @change="updateState(scope.row)"
              style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949" />
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" align="center">
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
    <el-dialog :title="isEdit === true ? '编辑角色' : '新增角色'" v-model="dialogVisible" @close="closeDialog" width="30%">
      <el-form :model="form" :rules="rules" ref="roleform" label-width="100px">
        <el-form-item :style="{ 'display': 'none' }" label="id" prop="id">
          <el-input v-model="form.id"></el-input>
        </el-form-item>
        <el-form-item label="角色名称" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="角色状态" prop="state">
          <el-switch v-model="form.state" class="ml-2"
            style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949" />
        </el-form-item>
        <el-form-item label="角色权限" prop="menu_id">
          <el-tree :data="menus" default-expand-all show-checkbox ref="tree" node-key="id" :props="{ label: 'title', children: 'children' }" />
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
import { ElMessage, ElMessageBox, ElTree, type FormInstance } from 'element-plus'
import { Delete, Edit, Search, Plus } from '@element-plus/icons-vue'
import { getRoleList, deleteRole, updateRole, addRole, getMenus } from '@/api/common'
import type { PageList, IMenu, IRole } from '@/api/commonType'

const menus = ref<IMenu[]>([])
const tree = ref<InstanceType<typeof ElTree>>()
const tableData = ref<IRole[]>([])
const pageTotal = ref(0)
const dialogVisible = ref(false)
const isEdit = ref(false)
const loading = ref(true)
const roleform = ref<FormInstance>()
const form = reactive({
  id: 0,
  name: '',
  state: true,
  menu_id: [] as number[]
})
const query = reactive<PageList>({
  name: '',
  page: 1,
  limit: 10
})
const rules = reactive({
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }]
})

// 查询表格数据
const getData = () => {
  loading.value = true
  void getRoleList(query).then(res => {
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
    void deleteRole({ id: [id] })
    getData()
  }).catch(() => { })
}

// 增加或编辑提交
const submit = async () => {
  await roleform.value?.validate((valid) => {
    if (valid) {
      const array1 = tree.value?.getCheckedKeys(true) as number[]
      const array2 = tree.value?.getHalfCheckedKeys() as number[]
      // console.log(array1)
      // console.log(array2)
      form.menu_id = [...array1, ...array2]
      // form.menu_id = array1.concat(array2)
      if (isEdit.value) {
        void updateRole(form)
        ElMessage.success('修改成功')
      } else {
        void addRole(form)
        ElMessage.success('添加成功')
      }
      dialogVisible.value = false
      getData()
    }
  })
}

// 增加或编辑弹出框
const openDialog = async (row: any) => {
  dialogVisible.value = true
  await getMenus().then((res) => {
    menus.value = res.data.menus
  })
  if (row) {
    isEdit.value = true
    // nextTick要放在函数的最后一步，如果放在dialogShow前，nextTick将不会产生作用
    await nextTick(() => {
      Object.assign(form, row)
      const checkedId: number[] = []
      row.menu_id.forEach((id: number) => {
        const node = tree.value?.getNode(id)
        if (node?.isLeaf) {
          checkedId.push(id)
        }
      })
      tree.value?.setCheckedKeys(checkedId, false)
    })
  } else {
    isEdit.value = false
  }
}

// 关闭弹出框
const closeDialog = () => {
  dialogVisible.value = false
  roleform.value?.clearValidate()
  roleform.value?.resetFields()
  tree.value?.setCheckedKeys([])
}

// 更新状态
const updateState = (row: any) => {
  if (row) {
    void updateRole({ id: row.id, state: row.state })
    ElMessage.success('更新状态成功')
  }
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
  width: 200px;
}

.table {
  width: 100%;
  font-size: 14px;
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
  height: 30px;
}
</style>
