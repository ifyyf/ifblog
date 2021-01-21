import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Element from 'element-ui'
import axios from 'axios'
import VueAxios from 'vue-axios'
import "element-ui/lib/theme-chalk/index.css"
import "./axios"
import vueWaves from './waves'
import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
// import './plugins/hljs'
import 'highlight.js/styles/googlecode.css'
import hljs from 'highlight.js'
Vue.directive('highlight',function (el) {
  let highlight = el.querySelectorAll('code,pre');
  highlight.forEach((block)=>{
    if(block){
      hljs.highlightBlock(block);
    }
  })
})
Vue.component("mavon-editor", mavonEditor);
Vue.use(vueWaves)
Vue.config.productionTip = false
Vue.use(Element);
Vue.use(VueAxios,axios)
Vue.prototype.$axios=axios

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
router.beforeEach((to,from,next)=>{
  document.body.scrollTop = 0
  // firefox
  document.documentElement.scrollTop = 0
  // safari
  window.pageYOffset = 0
  if(to.meta.title){

    document.title = to.meta.title

  }
  next()
})
