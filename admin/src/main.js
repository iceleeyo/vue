import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/zh-CN' // lang i18n

import axios from 'axios'
import moment from 'moment'
import md5 from 'js-md5'
import crypto from 'crypto'

import '@/styles/index.scss' // global css

import App from './App'
import router from './router'
import store from './store'

import '@/icons' // icon
import '@/permission' // permission control

Vue.use(ElementUI, { locale })

Vue.config.productionTip = false

Object.defineProperty(Vue.prototype, '$axios', { value: axios })
Object.defineProperty(Vue.prototype, '$md5', { value: md5 })
Object.defineProperty(Vue.prototype, '$crypto', { value: crypto })
Object.defineProperty(Vue.prototype, '$moment', { value: moment })

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
