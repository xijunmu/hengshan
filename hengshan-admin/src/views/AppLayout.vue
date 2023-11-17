<template>
  <div class="common-layout">
    <el-container>
      <el-aside>
        <SideBar />
      </el-aside>
      <el-container>
        <el-header>
          <BaseHeader />
        </el-header>
        <el-main>
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<script lang="ts" setup>
import SideBar from '@/components/SideBar.vue'
import BaseHeader from '@/components/BaseHeader.vue'
</script>
<style scoped>
.el-container {
  height: 100vh;
}
.el-aside {
  width: auto;
  background-color: #545c64;
}
header.el-header {
  margin: 0;
  padding: 0;
}
</style>
