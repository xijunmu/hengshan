<template>
    <div>
      <div class="container">
        <div class="handle-box">
          <el-select v-model="query.address" placeholder="商品分类" class="handle-select mr10">
            <el-option key="1" label="食品" value="0"></el-option>
            <el-option key="2" label="电器" value="1"></el-option>
          </el-select>
          <el-input v-model="query.name" placeholder="名称" class="handle-input mr10"></el-input>
          <el-button type="primary" :icon="Search" @click="handleSearch">搜索</el-button>
          <el-button type="primary" :icon="Plus" @click="handleProduct()">新增</el-button>
          <el-button type="danger" @click="removeRows">批量删除</el-button>
        </div>
        <el-table v-loading="loading" :data="tableData" border class="table" header-cell-class-name="table-header"
          ref="multipleTableRef" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" />
          <el-table-column prop="id" label="ID" width="55"></el-table-column>
          <el-table-column prop="name" label="名称" width="100"></el-table-column>
          <el-table-column prop="price" label="价格" width="100"></el-table-column>
          <el-table-column prop="img" label="图片" width="120" align="center">
          <template #default="scope">
            <el-image style="width: 60px; height: 60px" :src="scope.row.img" />
          </template>
        </el-table-column>
        <!-- el-table-column prop="desc" label="描述" width="100"></el-table-column-->
          <el-table-column label="操作" align="center">
            <template #default="scope">
              <el-button text :icon="Edit" @click="handleProduct(scope.row)"> 编辑 </el-button>
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
    </div>
  </template>

<script lang="ts" setup>
import { reactive, ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Delete, Edit, Search, Plus } from '@element-plus/icons-vue'
import { getProductList, deleteProduct } from '@/api/common'
import type { PageList, Product } from '@/api/commonType'
import { useRouter } from 'vue-router'

const router = useRouter()
const tableData = ref<Product[]>([])
const pageTotal = ref(0)
const loading = ref(true)

const query = reactive<PageList>({
  address: '',
  name: '',
  page: 1,
  limit: 10
})

// 查询表格数据
const getData = () => {
  loading.value = true
  void getProductList(query).then(res => {
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
    void deleteProduct({ id: [id] })
    getData()
  }).catch(() => { })
}

// 批量删除
const selectionIds = ref<number[]>([])
const handleSelectionChange = (val: Product[]) => {
  selectionIds.value = val.map(item => item.id)
}
const removeRows = () => {
  if (selectionIds.value.length === 0) {
    ElMessage.warning('请至少勾选一条数据')
  } else {
    console.log(selectionIds.value)
    void deleteProduct({ id: selectionIds.value })
    getData()
  }
}

const handleProduct = async (row?: any) => {
  void router.push({ path: '/product/info', query: row })
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
    width: 80px;
    height: 80px;
  }
  </style>
