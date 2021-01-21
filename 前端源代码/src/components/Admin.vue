<template>
    <div class="m-content">
        <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
            <el-menu-item index="1">安全退出</el-menu-item>
            <el-menu-item index="6" >博客首页</el-menu-item>
            <el-menu-item index="2" >留言管理</el-menu-item>
            <el-menu-item index="7" >添加博客</el-menu-item>
            <el-menu-item index="3">博客管理</el-menu-item>
            <el-menu-item index="4">评论管理</el-menu-item>
            <el-menu-item index="8">关于管理</el-menu-item>
            <el-menu-item index="9">声明管理</el-menu-item>
            <el-menu-item index="5">友链管理</el-menu-item>
        </el-menu>
        <div class="line"></div>
    </div>
</template>

<script>
    export default {
        name: "Admin",
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
                    this.$store.commit('remove_info')
                    this.$router.push('/')
                }else if(key==2){
                    this.$router.push('/MessageAdmin')
                }else if(key==3){
                    this.$router.push('/BlogEdit')
                }else if(key==4){
                    this.$router.push('/CommentAdmin')
                }else if(key==5){
                    this.$router.push('/FriendAdmin')
                }else if(key==6){
                    this.$router.push('/')
                }else if(key==7){
                    this.$router.push('/AddBlogAdmin')
                }else if(key==8){
                    this.$router.push('/AboutAdmin')
                }else if(key==9){
                    this.$router.push('/StatementAdmin')
                }
            },

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