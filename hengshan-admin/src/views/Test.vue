<template>
    <h1>test</h1>
    <h1>pinia 方式:{{ store.aa }}</h1>
    <h1> vuex 方式:{{ vueStore.state.count}}</h1>
</template>
<script lang="ts" setup>
import { testStore } from '../store/test'
import { useStore } from 'vuex'
const store = testStore()
const vueStore = useStore()
</script>
<style scoped>
</style>
