import Vue from 'vue'
import Router from 'vue-router'
// import HelloWorld from '@/components/HelloWorld'
import Gfline from '@/components/Gfline'
import Gftab from '@/components/Gftab'

import echarts from 'echarts/lib/echarts'
// 引入折线图
import 'echarts/lib/chart/line'
// 引入提示框和图例组件
import 'echarts/lib/component/tooltip'
import 'echarts/lib/component/legendScroll'

Vue.use(Router)

Vue.prototype.$echarts = echarts

export default new Router({
  routes: [{
    path: '/',
    name: 'Gfline',
    component: Gfline
  }, {
    path: '/',
    name: 'Gftab',
    component: Gftab
  }]
})
