<template>
  <el-form :model="form" ref="productform">
    <el-form-item :style="{ 'display': 'none' }" label="id" prop="id">
      <el-input v-model="form.id"></el-input>
    </el-form-item>
    <el-form-item label="名称" prop="name">
      <el-input v-model="form.name"></el-input>
    </el-form-item>
    <el-form-item label="价格" prop="price">
      <el-input v-model="form.price"></el-input>
    </el-form-item>
    <el-form-item label="图片" prop="img">
      <el-upload v-model:file-list="fileList" list-type="picture-card" action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
        :on-preview="handlePictureCardPreview"
        :on-remove="handleRemove">
        <el-icon>
          <Plus />
        </el-icon>
      </el-upload>
      <el-dialog v-model="dialogVisible">
        <img w-full :src="dialogImageUrl" alt="Preview Image" />
      </el-dialog>
    </el-form-item>
    <el-form-item label="描述" prop="desc">
      <div id="editor"></div>
    </el-form-item>
  </el-form>
</template>
<script lang="ts" setup>
import { reactive, ref, onMounted } from 'vue'
import { type FormInstance, type UploadProps, type UploadUserFile } from 'element-plus'
import { useRoute } from 'vue-router'
import { Plus } from '@element-plus/icons-vue'
import E from 'wangeditor'

const route = useRoute()
const productform = ref<FormInstance>()
const dialogImageUrl = ref('')
const dialogVisible = ref(false)
const form = reactive({
  id: 0,
  name: '',
  price: '',
  img: '',
  desc: '',
  date: new Date()
})

const fileList = ref<UploadUserFile[]>([])
const handleRemove: UploadProps['onRemove'] = (uploadFile, uploadFiles) => {
  console.log(uploadFile, uploadFiles)
}

const handlePictureCardPreview: UploadProps['onPreview'] = (uploadFile) => {
  dialogImageUrl.value = uploadFile.url!
  dialogVisible.value = true
}

const initEditor = () => {
  const editor = new E('#editor')
  editor.create()
  editor.txt.html(route.query.desc as string)
}

const initForm = () => {
  if (route.query.id) {
    Object.assign(form, route.query)
    fileList.value.push({ name: '1.jpeg', url: route.query.img as string })
  }
}

onMounted(() => {
  initForm()
  initEditor()
})
</script>
<style scoped></style>