<template>
  <div id="app">
<!--      <transition name="fade">-->
<!--          <loading v-if="isLoading"></loading>-->
<!--      </transition>-->
    <el-backtop class="el-backtop"></el-backtop>
    <Header
    v-show="!(path==='/BlogRead/'+this.$route.params.blogId)
    &&!(path==='/BlogEdit')
    &&!(path==='/MessageAdmin')
    &&!(path==='/CommentAdmin')
    &&!(path==='/FriendAdmin')
    &&!(path==='/AddBlogAdmin')
    &&!(path==='/BlogChange')
    &&!(path==='/AboutAdmin')
    &&!(path==='/StatementAdmin')
    " id="header"></Header>
    <ReadHead v-show="(path==='BlogRead/'+this.$route.params.blogId)" id="readheader"></ReadHead>
    <Admin
    v-show="(path==='/BlogEdit')
    ||(path==='/MessageAdmin')
    ||(path==='/CommentAdmin')
    ||(path==='/FriendAdmin')
    ||(path==='/AddBlogAdmin')
    ||(path==='/BlogChange')
    ||(path==='/AboutAdmin')
    ||(path==='/StatementAdmin')
    " id="admin"></Admin>
      <router-view/>
  </div>

</template>

<script>
// import Header from './components/Header'
// import ReadHead from "./components/ReadHead";
// import Admin from "./components/Admin";
import LwFirewords from 'lw_firewords'
/**
 * 组件懒加载
 * @constructor
 */
const Header = ()=>import("./components/Header");
const ReadHead = ()=>import("./components/ReadHead");
const Admin = ()=>import("./components/Admin");
// import Loading from './components/loading'
export default {
  name: 'App',
  data(){
    return{
      path:'',
      // isLoading:true,
    }
  },
  components: {
    Header,ReadHead,Admin
  },
  mounted() {
    this.path=this.$route.path
      const lw_f = new LwFirewords();//创建实例
      lw_f.init();//启动事件
      // const that = this
      // // 初始化页面数据
      // that.loadPageData()
  },
  // methods:{
  //     loadPageData: function() {
  //         // axios 请求页面数据 .then 中将状态值修改 this.isLoading = false
  //
  //     },

  // },
  watch:{
    $route(to,from){
      this.path=to.path
      // console.log(this.path)
    }
  },
};
</script>
<style>
    /*.el-backtop{*/
    /*    position: absolute;*/
    /*    z-index: 100;*/
    /*}*/
    /*滚动条*/
    ::-webkit-scrollbar-track
    {
        background: rgba(0,0,0,.1);
        border-radius: 0;
    }

    ::-webkit-scrollbar
    {
        -webkit-appearance: none;
        width: 8px;
        height: 10px;
    }

    ::-webkit-scrollbar-thumb
    {
        cursor: pointer;
        border-radius: 5px;
        /*background: #fd7286;*/
        transition: color .2s ease;
        background-image: linear-gradient(aquamarine,#fd7286,lightgreen);
    }
/*  滚动条↑  */
    html,body{
        margin:0;
        padding:0;
        height: 100%;
    }
    #app #header{
        opacity: 0.8;
        padding-bottom: 10px;
    }
    #app #admin{
        opacity: 0.8;
    }
    /*背景颜色*/
    #app{
        min-height: 100%;
        background: -webkit-linear-gradient(
                0deg,
                rgba(247, 149, 51, 0.1) 0,
                rgba(243, 112, 85, 0.1) 15%,
                rgba(239, 78, 123, 0.1) 30%,
                rgba(161, 102, 171, 0.1) 44%,
                rgba(80, 115, 184, 0.1) 58%,
                rgba(16, 152, 173, 0.1) 72%,
                rgba(7, 179, 155, 0.1) 86%,
                rgba(109, 186, 130, 0.1) 100%
        );
        background: -moz-linear-gradient(
                0deg,
                rgba(247, 149, 51, 0.1) 0,
                rgba(243, 112, 85, 0.1) 15%,
                rgba(239, 78, 123, 0.1) 30%,
                rgba(161, 102, 171, 0.1) 44%,
                rgba(80, 115, 184, 0.1) 58%,
                rgba(16, 152, 173, 0.1) 72%,
                rgba(7, 179, 155, 0.1) 86%,
                rgba(109, 186, 130, 0.1) 100%
        );
        background: -o-linear-gradient(
                0deg,
                rgba(247, 149, 51, 0.1) 0,
                rgba(243, 112, 85, 0.1) 15%,
                rgba(239, 78, 123, 0.1) 30%,
                rgba(161, 102, 171, 0.1) 44%,
                rgba(80, 115, 184, 0.1) 58%,
                rgba(16, 152, 173, 0.1) 72%,
                rgba(7, 179, 155, 0.1) 86%,
                rgba(109, 186, 130, 0.1) 100%
        );
        background: -ms-linear-gradient(
                0deg,
                rgba(247, 149, 51, 0.1) 0,
                rgba(243, 112, 85, 0.1) 15%,
                rgba(239, 78, 123, 0.1) 30%,
                rgba(161, 102, 171, 0.1) 44%,
                rgba(80, 115, 184, 0.1) 58%,
                rgba(16, 152, 173, 0.1) 72%,
                rgba(7, 179, 155, 0.1) 86%,
                rgba(109, 186, 130, 0.1) 100%
        );
        background: linear-gradient(
                90deg,
                rgba(247, 149, 51, 0.1) 0,
                rgba(243, 112, 85, 0.1) 15%,
                rgba(239, 78, 123, 0.1) 30%,
                rgba(161, 102, 171, 0.1) 44%,
                rgba(80, 115, 184, 0.1) 58%,
                rgba(16, 152, 173, 0.1) 72%,
                rgba(7, 179, 155, 0.1) 86%,
                rgba(109, 186, 130, 0.1) 100%
        );
    }
</style>
