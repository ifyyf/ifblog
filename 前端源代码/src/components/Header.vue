<template>
    <div class="m-content">
        <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
            <el-menu-item index="1">博客首页</el-menu-item>
            <el-menu-item index="2" >留言板</el-menu-item>
<!--            <el-menu-item index="3">后台管理</el-menu-item>-->
            <el-submenu index="4">
                <template slot="title">关于我的</template>
                <el-menu-item index="4">关于作者</el-menu-item>
                <el-menu-item index="5">网站声明</el-menu-item>
                <el-submenu index="2-3">
                    <template slot="title">友情链接</template>
                    <el-menu-item index="6">自由编程协会</el-menu-item>
                    <el-menu-item index="7">暂无</el-menu-item>
                    <el-menu-item index="8">暂无</el-menu-item>
                </el-submenu>
            </el-submenu>
        </el-menu>
        <div class="line"></div>
    </div>
</template>

<script>
    export default {
        name: "Header",
        data () {
            return {
                activeIndex: '1',
                activeIndex2: '1',
            }
        },
        methods: {
            handleSelect(key, keyPath) {
                // console.log(key);
                if(key==1){
                    this.$router.push('/')
                }else if(key==2){
                    this.$router.push('/Message')
                // }else if(key==3){
                    //     this.$router.push('/Login')
                }else if(key==4){
                    this.$router.push('/About')
                }else if(key==5){
                    this.$router.push('/Statement')
                }else if(key==6){
                    window.open("https://freeprogramming.cn/","blank");
                }
            }
        }
    }
</script>

<style scoped>
    .m-content{
        margin: 0 auto;
        max-width: 960px;
        text-align: center;
    }
    .el-menu-demo{
        max-width: 960px;
        text-align: center;
        margin: 0 auto;
    }
</style>