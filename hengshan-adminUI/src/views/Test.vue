<template>
    <h1>test</h1>
    <el-button type="primary" @click="store.increment">pinia方式</el-button>
    <h2>pinia方式:{{ store.count }}</h2>
    <el-button type="primary" @click="vuexStore.commit('increment')">vuex 方式</el-button>
    <h2>vuex 方式:{{ vuexStore.state.count }}</h2>
</template>
<script lang="ts" setup>
import { sidebarStore } from '@/store/sidebar'
import { useStore } from 'vuex'

const store = sidebarStore()
const vuexStore = useStore()

</script>
<style lang="scss" scoped>
// @import '@/styles/variables.scss';
h1 {
    color: $color;
}
</style>
