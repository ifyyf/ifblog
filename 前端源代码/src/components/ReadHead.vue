<template>
    <el-page-header @back="goBack" content="文章页面">
    </el-page-header>
</template>

<script>
    export default {
        name: "ReadHead",
        methods: {
            goBack() {
                this.$router.push('/')
            }
        }
    }
</script>

<style scoped>
    .el-page-header{
        max-height: 40px;
        min-height: 20px;
    }
</style>