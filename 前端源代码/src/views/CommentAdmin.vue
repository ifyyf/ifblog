<template>
    <div class="Comment-Admin">
        <transition name="fade">
            <loading v-if="isLoading"></loading>
        </transition>
        <h1>评论管理</h1>
        <el-table
                ref="singleTable"
                :data="tableData"
                highlight-current-row
                @current-change="handleCurrentChange"
                style="width: 100%">
            <el-table-column
                    type="commentId"
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
                    property="blogId"
                    label="博客名称"
                    :show-overflow-tooltip="true">
            </el-table-column>
            <el-table-column
                    label="是否激活"
                    property="ifShow"
                    width="50">
            </el-table-column>
        </el-table>
        <div style="margin-top: 20px">
            <el-button type="primary" round  @click="moreComment" v-show="this.showButton">加载更多</el-button>
            <el-button @click="acComment" type="success" plain v-bind:disabled="showAdmin">激活</el-button>
            <el-button @click="deleteComment" type="danger" plain v-bind:disabled="showAdmin">删除</el-button>
            <el-button @click="giveupChoose" type="info" plain v-bind:disabled="showAdmin">取消</el-button>
        </div>
    </div>
</template>

<script>
    import Loading from '../components/loading'
    export default {
        name: "CommentAdmin",
        components:{ Loading  },
        data() {
            return {
                tableData: [],
                blogs:[],
                currentRow: null,
                showButton:true,
                pageNum:1,
                showAdmin:true,
                isLoading:true,
            }
        },
        methods:{
            getComments(user){
                return this.axios.get('/getComments',
                    {
                        params: {
                            username:user.username,
                            password:user.password
                        }
                    })
            },
            getAcComments(user){
                return this.axios.get('/getAllComments',
                    {
                        params: {
                            pageNum:this.pageNum,
                            username:user.username,
                            password:user.password
                        }
                    })
            },
            getBlogId(blogId){
                return this.axios.get('/readBlog',
                    {
                        params: {
                            blogId:blogId
                        }
                    })
            },
            acComment() {
                if(this.currentRow!==null){
                    var user=this.$store.getters.getUser
                    this.axios.get("/acComment",{
                        params:{
                            commentId:this.currentRow.commentId,
                            username:user.username,
                            password:user.password
                        }
                    })
                    this.$message({
                        message: '激活成功',
                        type: 'success'
                    });
                    setTimeout(function(){
                        location.reload();   //可以是一句或是很多句代码，也可以是个函数
                    },1000);　　　　//延时1秒
                }else{
                    this.$message({
                        showClose: true,
                        message: '还未选择任何评论',
                        type: 'warning'
                    });
                }

            },
            deleteComment(){
                var user=this.$store.getters.getUser
                if(this.currentRow.commentId!==null){
                    this.$confirm('此操作将永久删除该评论, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        this.axios.get("/deleteComment",{
                            params:{
                                commentId:this.currentRow.commentId,
                                username:user.username,
                                password:user.password
                            }
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
                        message: '还未选择任何评论',
                        type: 'warning'
                    });
                }
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
            moreComment(){
                var user=this.$store.getters.getUser
                this.getAcComments(user).then((response) => {
                    this.tableData=this.tableData.concat(response.data.data)
                    if(response.data.data.length!==5){
                        this.showButton=false;
                    }
                })
                ++this.pageNum;
            }
        },
        created() {
            //登入此界面进行验证
            var that=this
            var user=this.$store.getters.getUser
            if(user){
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
                this.getComments(user).then((response) => {
                    this.tableData = (response.data.data);
                    for(let i=0; i<this.tableData.length; i++){
                        this.getBlogId(this.tableData[i].blogId).then(res=>{
                            // console.log("length="+length)
                            // console.log(this.tableData)
                            if(res.data.data.title){
                                this.tableData[i].blogId=res.data.data.title
                            }else{
                                this.tableData[i].blogId='博客已删除'
                            }
                        })
                    }
                })
            }else{
                this.$router.push('/404Page.html')
            }
        },
        mounted() {
            //判断所有资源是否加载完毕
            var _this = this
            var timer = setInterval(function () {
                // 判断文档和所有子资源(图片、音视频等)已完成加载
                if (document.readyState === 'complete') {
                    //执行方法
                    _this.isLoading=false
                    window.clearInterval(timer)
                }
            }, 500)
        }
    }
</script>

<style scoped>
.Comment-Admin{
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