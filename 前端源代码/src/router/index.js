import Vue from 'vue'
import VueRouter from 'vue-router'
// import Login from '../views/Login.vue'
// import Blogs from '../views/Blogs.vue'
// import BlogEdit from '../views/BlogEdit.vue'
// import BlogRead from '../views/BlogRead.vue'
// import NotFound from "../views/NotFound";
// import Message from "../views/Message";
// import About from "../views/About";
// import Statement from "../views/Statement";
// import MessageAdmin from "../views/MessageAdmin";
// import FriendAdmin from "../views/FriendAdmin";
// import CommentAdmin from "../views/CommentAdmin";
// import AddBlogAdmin from "../views/AddBlogAdmin";

Vue.use(VueRouter)
/**
 * 路由懒加载
 * @constructor
 */
const Login = ()=>import("@/views/Login")
const Blogs = ()=>import("@/views/Blogs")
const BlogEdit = ()=>import("@/views/BlogEdit")
const BlogRead = ()=>import("@/views/BlogRead")
const NotFound = ()=>import("@/views/NotFound")
const Message = ()=>import("@/views/Message")
const About = ()=>import("@/views/About")
const Statement = ()=>import("@/views/Statement")
const MessageAdmin = ()=>import("@/views/MessageAdmin")
const FriendAdmin = ()=>import("@/views/FriendAdmin")
const CommentAdmin = ()=>import("@/views/CommentAdmin")
const AddBlogAdmin = ()=>import("@/views/AddBlogAdmin")
const BlogChange = ()=>import("@/views/BlogChange")
const AboutAdmin=()=>import("@/views/AboutAdmin")
const StatementAdmin=()=>import("@/views/StatementAdmin")
const routes = [
  // {
  //   path: '/',
  //   name: 'index',
  //   // redirect:{name:'Blogs'},
  //   meta:{
  //     title: 'if的小世界'
  //   }
  // },
  {
    path: '/',
    name: 'Blogs',
    component: Blogs,
    meta:{
      title: 'if的小世界'
    }
  },
  {
    path: '/Login',
    name: 'Login',
    component: Login,
    meta:{
      title: '管理员页--if的小世界'
    }
  },
  {
    path: '/BlogEdit',
    name: 'BlogEdit',
    component: BlogEdit,
    meta:{
      title: '博客管理--if的小世界'
    }
  },
  {
    path: '/BlogRead/:blogId',
    name: 'BlogRead',
    component: BlogRead,
    meta:{
      title: '好记性不如烂笔头--if的小世界'
    }
  },
  {
    path:'/Message',
    component:Message,
    meta:{
      title: '有事没事踩一踩--if的小世界'
    }
  },
  {
    path:'/About',
    component:About,
    meta:{
      title: '关于我--if的小世界'
    }
  },
  {
    path:'/Statement',
    component:Statement,
    meta:{
      title: '网站声明--if的小世界'
    }
  },
  {
    path:'/MessageAdmin',
    component:MessageAdmin,
    meta:{
      title: '留言管理--if的小世界'
    }
  },
  {
    path:'/CommentAdmin',
    component:CommentAdmin,
    meta:{
      title: '评论管理--if的小世界'
    }
  },
  {
    path:'/FriendAdmin',
    component:FriendAdmin,
    meta:{
      title: '友链管理--if的小世界'
    }
  },
  {
    path:'/AddBlogAdmin',
    component:AddBlogAdmin,
    meta:{
      title: '晨霜攀黛瓦--if的小世界'
    }
  },
  {
    path:'/BlogChange',
    name:'BlogChange',
    component:BlogChange,
    meta:{
      title: '知错就要改--if的小世界'
    }
  },
  {
    path:'/AboutAdmin',
    name:'AboutAdmin',
    component:AboutAdmin,
    meta:{
      title: '知错就要改--if的小世界'
    }
  },
  {
    path:'/StatementAdmin',
    name:'StatementAdmin',
    component:StatementAdmin,
    meta:{
      title: '知错就要改--if的小世界'
    }
  },
  {
    //放最底下，上述全不匹配时跳转NotFound组件
    path: '*',
    name: '404',
    component: NotFound,
    meta:{
      title: '页面不小心走丢了--if的小世界'
    }
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
