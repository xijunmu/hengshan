<template>
  <div class="header-container">
    <div class="l-content">
      <!-- 折叠按钮 -->
      <div style="font-size: 25px" class="collapse-btn" @click="store.handleCollapse">
        <el-icon v-if="store.isCollapse">
          <Expand />
        </el-icon>
        <el-icon v-else>
          <Fold />
        </el-icon>
      </div>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item v-for="item in routes" :key="item.path">{{
          item.meta.title
        }}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="r-content">
      <el-avatar class="user-avator" :size="50" :src="imgurl" />
      <el-dropdown class="user-name" trigger="click" @command="handleCommand">
        <span class="el-dropdown-link">
          {{ store.username }}
          <el-icon>
            <ArrowDownBold />
          </el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="user">个人中心</el-dropdown-item>
            <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>
<script lang="ts" setup>
import { computed } from 'vue'
import { sidebarStore } from '@/store/sidebar'
import { useRouter } from 'vue-router'
import imgurl from '@/assets/user.jpeg'
import { logout } from '@/api/common'

const store = sidebarStore()
const router = useRouter()
// console.log(router.currentRoute.value.matched)
const routes = computed(() => {
  return router.currentRoute.value.matched.filter((item) => item.meta.title)
})

const handleCommand = async (command: string) => {
  if (command === 'logout') {
    await logout().then(() => {
      localStorage.removeItem('user')
      localStorage.removeItem('token')
      void router.push('/login')
    })
  } else if (command === 'user') {
    await router.push('/user')
  }
}
</script>

<style scoped>
.header-container {
  margin: 0;
  height: 60px;
  background-color: #fff;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.collapse-btn {
  cursor: pointer;
  margin-right: 8px;
}

.el-breadcrumb {
  font-size: 18px;
  padding-bottom: 5px;
}

.l-content {
  float: right;
  padding-right: 5px;
  display: flex;
  align-items: center;
  color: #545c64;
  display: flex;
  align-items: center;
}

.user-name {
  margin-left: 10px;
  margin-right: 8px;
}

.el-dropdown-link {
  color: #333;
  cursor: pointer;
  display: flex;
  align-items: center;
  padding-top: 15px;
  font-size: 16px;
  margin-right: 40px;
}

.el-dropdown-menu__item {
  text-align: center;
}
</style>
