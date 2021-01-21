import axios from 'axios'
import Element from 'element-ui'
//基础url
// axios.defaults.baseURL="http://if.vaiwan.com"
// axios.defaults.baseURL="http://121.196.34.140"
axios.defaults.baseURL="https://www.ifyyf.com"
// axios.defaults.baseURL="http://localhost:8080"
/**
 * 前置拦截
 */
axios.interceptors.request.use(config=>{
    return config
})

/**
 * 后置拦截
 * 每次使用axios都会访问一次这个方法
 */
axios.interceptors.response.use(response=>{
    // console.log("=======")
    // console.log(response)
    // console.log("=======")
    if(response.status===200){
        return response
    }else{
        Element.Message.error('用户名或密码错误');
        return Promise.reject(response.msg)
    }
})