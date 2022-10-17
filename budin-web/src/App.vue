<script setup>
import TheWelcome from './components/TheWelcome.vue'
import Portal from './views/Portal.vue'
import Files from '@/views/Files.vue'
import Login from '@/views/Login.vue'
import Collectes from '@/views/Collectes.vue'
import Albums from '@/views/Albums.vue'
</script>

<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<style scoped>
</style>
<script>
export default {
  name: "App",
};
</script>
