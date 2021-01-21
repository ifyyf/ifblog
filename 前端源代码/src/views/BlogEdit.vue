<template>
    <div class="BlogsEdit" >
        <transition name="fade">
            <loading v-if="isLoading"></loading>
        </transition>
        <h1>管理博客</h1>
        <el-table
                ref="singleTable"
                :data="tableData"
                highlight-current-row
                @current-change="handleCurrentChange"
                style="width: 100%">
            <el-table-column
                    type="blogId"
                    width="50">
            </el-table-column>
            <el-table-column
                    property="time1"
                    label="日期"
                    width="120">
            </el-table-column>
            <el-table-column
                    property="title"
                    label="标题"
                    width="120">
            </el-table-column>
            <el-table-column
                    property=body
                    label="内容"
                    :show-overflow-tooltip="true">
            </el-table-column>
            <el-table-column
                    property="top"
                    label="是否置顶">
            </el-table-column>
        </el-table>
        <div style="margin-top: 20px">
            <el-button type="primary" round  @click="moreBlog" v-show="this.showButton">加载更多</el-button>
            <el-button @click="changeBlog" type="success" plain v-bind:disabled="showAdmin">修改</el-button>
            <el-button @click="deleteBlog" type="danger" plain v-bind:disabled="showAdmin">删除</el-button>
            <el-button @click="giveupChoose" type="info" plain v-bind:disabled="showAdmin">取消</el-button>
        </div>
    </div>
</template>

<script>
    import Loading from '../components/loading'
    export default {
        name: "BlogEdit",
        components:{ Loading  },
        data() {
            return {
                tableData: [],
                currentRow: null,
                showButton:true,
                pageNum:1,
                showAdmin:true,
                isLoading:true,
            }
        },
        methods:{
            /**
             * 预览字数限制
             * @param date
             * @returns {string|*}
             */
            fontNumber (date) {
                const length = date.length
                if (length > 35) {
                    var str = ''
                    str = date.substring(0, 35) + '......点击查看文章'
                    return str
                } else {
                    return date
                }
            },
            changeBlog() {
                if(this.currentRow!==null){
                    // console.log('修改'+this.currentRow.blogId)
                    // console.log(this.currentRow)
                    this.$router.push({
                        name:'BlogChange',
                        params: {
                            // context:this.currentRow.body,
                            blogId:this.currentRow.blogId
                        }
                    })
                }else{
                    this.$message({
                        showClose: true,
                        message: '还未选择任何博客',
                        type: 'warning'
                    });
                }

            },
            deleteBlog(){
                var user=this.$store.getters.getUser
                if(this.currentRow.blogId!==null){
                    this.$confirm('此操作将永久删除该博客, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        this.axios.get("/deleteBlog",{
                            params:{
                                blogId:this.currentRow.blogId,
                                username:user.username,
                                password:user.password
                            }
                        }).then((res) => {
                            // console.log(res)
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
                        message: '还未选择任何博客',
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
                // console.log(this.currentRow)
                if(this.showAdmin===true){
                    this.showAdmin=!this.showAdmin
                }

            },
            moreBlog(){
                this.axios.get('/getBlogs',
                    {
                        params: {
                            pageNum:++this.pageNum
                        }
                    }).then((response) => {
                    this.tableData=this.tableData.concat(response.data.data)
                    if(response.data.data.length!==5){
                        // console.log(response.data.data)
                        this.showButton=false;
                    }
                })
            },
            getTopBlogs(){
                return this.axios.get('/getTopBlogs',
                    {
                        params: {
                            pageNum:this.pageNum
                        }
                    })
            },
            getBlogs(){
                return this.axios.get('/getBlogs',
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
                var me = this;
                this.axios.all([me.getBlogs(),me.getTopBlogs()])
                    .then(me.axios.spread(function(getBlogs, getTopBlogs){
                        // console.log('所有请求完成')
                        // console.log('请求1结果',getBlogs)
                        // console.log('请求2结果',getTopBlogs)
                        me.tableData=getTopBlogs.data.data
                        me.tableData=me.tableData.concat(getBlogs.data.data)
                        me.isLoading=false
                    }))
            }else{
                this.$router.push('/404Page.html')
            }
        }
    }
</script>

<style scoped>
    .BlogsEdit{
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