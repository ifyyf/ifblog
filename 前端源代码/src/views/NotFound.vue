<template>
    <div id="NotFound">
        <transition name="fade">
            <loading v-if="isLoading"></loading>
        </transition>
<!--        <Header></Header>-->
<!--        <h1>404,访问页面不存在</h1>-->
<!--        <br>-->
<!--        <el-button type="primary" round @click="goHome">回到首页</el-button>-->
    </div>
</template>

<script>
    import Loading from '../components/loading'
    // import Header from "../components/Header";
    export default {
        name: "NotFound",
        components:{ Loading  },
        data(){
          return{
              isLoading:true,
          }
        },
        methods:{
            // goHome(){
            //     this.$router.push('/')
            // }
        },
        created() {
            window.location.href="/404Page.html";
            this.isLoading=false
        }
    }
</script>

<style scoped>
    #NotFound{
        margin: 0 auto;
        max-width: 960px;
        text-align: center;
    }
</style>