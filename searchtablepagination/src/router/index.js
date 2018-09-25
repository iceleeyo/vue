import Vue from 'vue'
import Router from 'vue-router'
//import HelloWorld from '@/components/HelloWorld'
import DbTable from '@/components/DbTable'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'DbTable',
      component: DbTable
    }
  ]
})
