<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
/* 引入重置样式 */
import './assets/css/reset.css'
export default {
  name: 'App',
  components: {
  },
}
</script>
<style>
#nprogress .bar {
    background-image:linear-gradient(45deg, #0081ff, #1cbbb4) !important;
    height: 3px !important;
}
</style>