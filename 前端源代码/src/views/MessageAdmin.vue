<template>
    <div class="Message-admin">
        <transition name="fade">
            <loading v-if="isLoading"></loading>
        </transition>
        <h1>留言管理</h1>
        <el-table
                ref="singleTable"
                :data="tableData"
                highlight-current-row
                @current-change="handleCurrentChange"
                style="width: 100%">
            <el-table-column
                    type="id"
                    width="50">
            </el-table-column>
            <el-table-column
                    property="time1"
                    label="日期"
                    width="120">
            </el-table-column>
            <el-table-column
                    property="identity1"
                    label="姓名"
                    width="120">
            </el-table-column>
            <el-table-column
                    property="email"
                    label="邮箱"
                    width="120">
            </el-table-column>
            <el-table-column
                    property="body"
                    label="内容"
                    :show-overflow-tooltip="true">
            </el-table-column>
            <el-table-column
                    property="ifShow"
                    label="是否激活">
            </el-table-column>
        </el-table>
        <div style="margin-top: 20px">
            <el-button type="primary" round  @click="moreMessage" v-show="this.showButton">加载更多</el-button>
            <el-button @click="changeMessage" type="success" plain v-bind:disabled="showAdmin">激活</el-button>
            <el-button @click="deleteMessage" type="danger" plain v-bind:disabled="showAdmin">删除</el-button>
            <el-button @click="giveupChoose" type="info" plain v-bind:disabled="showAdmin">取消</el-button>
        </div>
    </div>
</template>

<script>
    import Loading from '../components/loading'
    export default {
        name: "MessageAdmin",
        components:{ Loading  },
        data(){
            return{
                isLoading:true,
                tableData: [],
                pageNum:1,
                currentRow: null,
                showButton:true,
                showAdmin:true,
            }
        },
        methods:{
            moreMessage(){
                var user=this.$store.getters.getUser
                this.axios.get('/getActiveMessage',
                    {
                        params: {
                            pageNum:++this.pageNum,
                            username:user.username,
                            password:user.password
                        }
                    }).then((response) => {
                    this.tableData=this.tableData.concat(response.data.data)
                    if(response.data.data.length!==5){
                        // console.log(response.data.data)
                        this.showButton=false;
                    }
                })
                this.axios.get('/getMessage',
                    {
                        params: {
                            pageNum:this.pageNum
                        }
                    }).then((response) => {
                    this.tableData=this.tableData.concat(response.data.data)
                })
            },
            giveupChoose(){
                this.currentRow=null
                this.$refs.singleTable.setCurrentRow();
                this.showAdmin=!this.showAdmin
            },
            handleCurrentChange(val) {
                this.currentRow=val
                if(this.showAdmin===true){
                    this.showAdmin=!this.showAdmin
                }

            },
            changeMessage(){
                var user=this.$store.getters.getUser
                this.axios.get("/acMessage",{
                    params:{
                        id:this.currentRow.id,
                        username:user.username,
                        password:user.password
                    }
                }).then((res)=>{
                    // console.log(res)
                    this.$message({
                        type: 'success',
                        message: '激活成功!'
                    });
                    setTimeout(function(){
                        location.reload();   //可以是一句或是很多句代码，也可以是个函数
                    },1000);　　　　//延时1秒
                })
            },
            deleteMessage(){
                var user=this.$store.getters.getUser
                if(this.currentRow.blogId!==null){
                    this.$confirm('此操作将永久删除该留言, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        this.axios.get("/deleteMessage",{
                            params:{
                                id:this.currentRow.id,
                                username:user.username,
                                password:user.password
                            }
                        }).then((res)=>{
                            // console.log(res)
                            location.reload();
                        })
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                        setTimeout(function(){
                            location.reload();   //可以是一句或是很多句代码，也可以是个函数
                        },1000);　　　　//延时1秒
                    }).catch(() => {
                        this.$message({
                            type: 'info',
                            message: '已取消删除'
                        });
                    });
                }else{
                    this.$message({
                        showClose: true,
                        message: '还未选择任何留言',
                        type: 'warning'
                    });
                }

            },
            getActiveMessage(username,password){
                return this.axios.get('/getActiveMessage',
                    {
                        params: {
                            pageNum:this.pageNum,
                            username:username,
                            password:password,
                        }
                    })
            },
            getMessage(){
                return this.axios.get('/getMessage',
                    {
                        params: {
                            pageNum:this.pageNum
                        }
                    })
            },
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
            }else{
                this.$router.push('/404Page.html')
            }
            var me = this;
            this.axios.all([me.getActiveMessage(user.username,user.password),me.getMessage()])
                .then(me.axios.spread(function(getActiveMessage, getMessage){
                    // console.log('所有请求完成')
                    // console.log('请求1结果',getBlogs)
                    // console.log('请求2结果',getTopBlogs)
                    me.tableData=getMessage.data.data
                    me.tableData=me.tableData.concat(getActiveMessage.data.data)
                    me.isLoading=false
                }))
        }
    }
</script>

<style scoped>
.Message-admin{
    margin: 0 auto;
    max-width: 960px;
    text-align: center;
}
@media screen and (max-width: 500px) {
    .el-message {
        min-width: 300px !important;
    }
}
@media screen and (max-width: 500px) {
    .el-message-box{
        width: 300px !important;
    }
}
</style>