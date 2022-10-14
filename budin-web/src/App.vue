/* eslint-disable */
<template>
  <div id="app">
    <img src="./assets/logo.png">
    <budin/>
  </div>
</template>
<script>
import budin from './components/budin'
export default {
  name: 'App',
  components: {
    budin
  }
}
</script>
<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
