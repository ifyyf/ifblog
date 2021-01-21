<template>
    <div>
        <transition name="fade">
            <loading v-if="isLoading"></loading>
        </transition>
        <h1>友链管理正在开发中(其实就是不开发了hhh)</h1>
    </div>
</template>

<script>
    import Loading from '../components/loading'
    export default {
        name: "FriendAdmin",
        components:{ Loading  },
        data(){
            return{
                isLoading:true,
            }
        },
        created() {
            //登入此界面进行验证
            var that=this
            var user=this.$store.getters.getUser
            if(user!==null){
                this.axios.post('/login',
                    {
                        username:user.username,
                        password:user.password
                    }).then((res)=>{
                    // console.log(res.data.data)
                    user=res.data.data
                    if(!user){
                        //跳转页面
                        this.$router.push('/404Page.html')
                    }
                })
                this.isLoading=false
            }else{
                this.$router.push('/404Page.html')
            }
        }
    }
</script>

<style scoped>
h1{
    margin: 0 auto;
    max-width: 960px;
    text-align: center;
}
</style>