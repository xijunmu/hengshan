<template>
    <el-menu active-text-color="#ffd04b" background-color="#545c64" class="el-menu-vertical-demo" default-active="2"
        text-color="#fff" @open="handleOpen" @close="handleClose" :collapse="store.isCollapse" router>
        <el-menu-item index="/home">
            <el-icon><HomeFilled /></el-icon>
            <span>首页</span>
        </el-menu-item>
        <el-sub-menu index="/product">
            <template #title>
                <el-icon><Goods /></el-icon>
                <span>商品管理</span>
            </template>
            <el-menu-item index="/product/list">
                <el-icon><Goods /></el-icon>
                <span>商品列表</span>
            </el-menu-item>
            <el-menu-item index="/product/classify">
                <el-icon><Menu /></el-icon>
                <span>商品分类</span>
            </el-menu-item>
            <el-menu-item index="/product/attr">
                <el-icon><Menu /></el-icon>
                <span>商品规格</span>
            </el-menu-item>
            <el-menu-item index="/product/reply">
                <el-icon><ChatDotSquare /></el-icon>
                <span>商品评论</span>
            </el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/order/list">
            <el-icon>
                <document />
            </el-icon>
            <span>订单管理</span>
        </el-menu-item>
        <el-menu-item index="/media">
            <el-icon><Picture /></el-icon>
            <span>媒体管理</span>
        </el-menu-item>
        <el-sub-menu index="/permission">
            <template #title>
                <el-icon><Setting /></el-icon>
                <span>权限管理</span>
            </template>
            <el-menu-item index="/permission/role">
                <el-icon><Avatar /></el-icon>
                <span>角色</span>
            </el-menu-item>
            <el-menu-item index="/permission/admin">
                <el-icon><UserFilled /></el-icon>
                <span>管理员</span>
            </el-menu-item>
            <el-menu-item index="/permission/rule">
                <el-icon><Lock /></el-icon>
                <span>权限规则</span>
            </el-menu-item>
        </el-sub-menu>
    </el-menu>
</template>
<script setup lang="ts">
import { sidebarStore } from '@/store/sidebar'

const store = sidebarStore()
const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
</script>
<style scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}
</style>