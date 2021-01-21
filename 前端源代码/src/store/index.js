import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token:'',
    userInfo:JSON.parse(sessionStorage.getItem("userInfo"))
  },
  mutations: {
    /**
     * this.$store.commit("")来使用方法
     */

    /**
     * 设置token
     * @param state
     * @param token
     */
    set_token:(state,token) => {
      state.token=token
      //放置浏览器的localStorage中
      localStorage.setItem("token",token);
    },
    /**
     * 设置user_Info
     * @param state
     * @param userInfo
     */
    set_userInfo:(state,userInfo) => {
      userInfo.identity1='if'
      state.userInfo=userInfo
      //sessionStorage中，会话结束即userInfo消失
       // session中不能存对象，所以要转换一下
      sessionStorage.setItem("userInfo",JSON.stringify(userInfo));
    },
    /**
     * 消除信息
     * @param state
     */
    remove_info:(state)=>{
        state.token='',
        state.userInfo={},
        window.localStorage.clear();
        window.sessionStorage.clear();
    }
  },
  getters:{
    //get
    getUser:state => {
      return state.userInfo
    }
  },
  actions: {
  },
  modules: {
  }
})
