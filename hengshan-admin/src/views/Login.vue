<template>
  <div class="login-wrap">
    <div class="ms-login">
      <div class="ms-title">后台管理系统</div>
      <el-form :model="user" :rules="rules" ref="form" label-width="0px" class="ms-content">
        <el-form-item prop="username">
          <el-input v-model="user.username" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password" placeholder="密码" v-model="user.password"></el-input>
        </el-form-item>
        <el-form-item prop="imgcode">
          <el-input class="imgcode" placeholder="验证码" v-model="user.imgcode" @keyup.enter="submitForm"></el-input>
          <img class="code" alt="验证码" :src="imgCode" @click="loadImgCode" />
        </el-form-item>
        <div class="login-btn">
          <el-button type="primary" @click="submitForm">登录</el-button>
        </div>
        <p class="login-tips">Tips : 用户名和密码随便填。</p>
      </el-form>
    </div>
  </div>
</template>
<script lang="ts" setup>
import { reactive, ref, onMounted } from 'vue'
import { getImgCode, login } from '@/api/common'
import type { FormInstance } from 'element-plus'
import { useRouter } from 'vue-router'
import { sidebarStore } from '@/store/sidebar'

const store = sidebarStore()
const router = useRouter()
const form = ref<FormInstance>()
const imgCode = ref('')
const user = reactive({
  username: 'admin',
  password: '123',
  imgcode: ''
})
const rules = reactive({
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
})

const submitForm = async () => {
  const valid = await form.value?.validate()
  if (valid != null && !valid) {
    return false
  }
  await login(user).then(({ data }) => {
    console.log(data)
    store.setLoginInfo(user.username, data.data.token)
    void router.push('home')
  })
}

const loadImgCode = () => {
  void getImgCode().then(({ data }) => {
    imgCode.value = data
  })
}
onMounted(() => {
  loadImgCode()
})

</script>
<style scoped>
.login-wrap {
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  min-height: 100vh;
  width: 100%;
  background-image: url(@/assets/login-bg.jpg);
}

.ms-login {
  position: absolute;
  left: 35%;
  top: 30%;
  width: 350px;
  border-radius: 5px;
  background: rgba(255, 255, 255, 0.3);
  overflow: hidden;
}

.ms-title {
  width: 100%;
  line-height: 50px;
  text-align: center;
  font-size: 20px;
  color: #030303;
  border-bottom: 1px solid #ddd;
}

.ms-content {
  padding: 30px 30px;
}

.login-btn {
  text-align: center;
}

.login-btn button {
  width: 100%;
  height: 36px;
  margin-bottom: 10px;
}

.login-tips {
  font-size: 12px;
  line-height: 30px;
  color: #999;
}

.imgcode {
  width: 200px;
  margin-right: 8px;
}

.code {
  cursor: pointer;
}
</style>
