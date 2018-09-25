<template>
  <div id="app">
    <!-- <router-view/> -->
    <el-row class="container">
      <el-col>
        <gf-line />
        <gf-tab />
      </el-col>
    </el-row>
  </div>
</template>

<script>
import GfLine  from './components/Gfline.vue'
import GfTab from './components/Gftab.vue'
export default {
  name: 'App',
  components: {
    GfLine, GfTab
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
.container {
  padding: 0;
  flex: 1;
}
.wrapper {
  position: relative;
}
.container, .wrapper {
  height: 100%;
}
</style>
