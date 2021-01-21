<template>
    <div class="BlogChange">
        <transition name="fade">
            <loading v-if="isLoading"></loading>
        </transition>
        <h2>弦外思念透窗花</h2>
        <mavon-editor
                v-model="context"
                :toolbars="toolbars"
                @keydown=""
                @imgAdd="imgAdd"
                @imgDel=""
                @change="change"
                ref="md"
                id="edit"
                :ishljs = "true"
                @save="addBlog"
                style="height: 600px;"
        />
        <div class="edit-button">
            <el-button @click="addBlog" type="primary" round >修改</el-button>
            <el-button @click="toTop" type="success" round>置顶</el-button>
        </div>
    </div>
</template>

<script>
    import Loading from '../components/loading'
    export default {
        name: "BlogChange",
        components:{ Loading  },
        data(){
            return{
                blogId:0,
                isLoading:true,
                ifTop:0,
                context: '# 标题\n正文',//输入的数据
                toolbars: {
                    bold: true, // 粗体
                    italic: true, // 斜体
                    header: true, // 标题
                    underline: true, // 下划线
                    strikethrough: true, // 中划线
                    mark: true, // 标记
                    superscript: true, // 上角标
                    subscript: true, // 下角标
                    quote: true, // 引用
                    ol: true, // 有序列表
                    ul: true, // 无序列表
                    link: true, // 链接
                    imagelink: true, // 图片链接
                    code: true, // code
                    table: true, // 表格
                    fullscreen: true, // 全屏编辑
                    readmodel: true, // 沉浸式阅读
                    htmlcode: true, // 展示html源码
                    help: true, // 帮助
                    /* 1.3.5 */
                    undo: true, // 上一步
                    redo: true, // 下一步
                    trash: true, // 清空
                    save: true, // 保存（触发events中的save事件）
                    /* 1.4.2 */
                    navigation: true, // 导航目录
                    /* 2.1.8 */
                    alignleft: true, // 左对齐
                    aligncenter: true, // 居中
                    alignright: true, // 右对齐
                    /* 2.2.1 */
                    subfield: true, // 单双栏模式
                    preview: true, // 预览
                }
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
                // console.log(this.$route)
                // this.context=this.$route.params.context,
                this.blogId=this.$route.params.blogId
                this.axios.get('/readBlog',
                    {
                        params: {
                            blogId:this.blogId
                        }
                    }).then((response) => {
                    this.context=response.data.data.body
                    // console.log(response.data.data)
                })
            }else{
                this.$router.push('/404Page.html')
            }
        },
        methods:{
            // 将图片上传到服务器，返回地址替换到md中
            imgAdd(pos, $file){
                let formdata = new FormData();
                formdata.append('image', $file);
                //访问后台服务器方法
                this.axios.post('/upload',formdata)
                    .then((res)=>{
                        if (res.data.status === 200) {
                            //回显
                            this.$refs.md.$img2Url(pos, res.data.data.url);
                        } else {
                            this.$message.error(res.data.msg)
                        }
                    }).catch(err => {
                    console.log(err)
                })
            },
            change(value, render) {
                // render 为 markdown 解析后的结果
                this.html = render;
            },
            addBlog(){
                // console.log(this.context)
                var user=this.$store.getters.getUser
                this.axios.post('/changeBlog',
                    {
                        context:this.context,
                        blogId:this.blogId,
                        top:this.ifTop,
                        username:user.username,
                        password:user.password
                    }).then((res)=>{
                    // console.log(res.data.status)
                    if(res.data.status==200){
                        this.$message({
                            showClose: true,
                            message: '博客上传成功',
                            type: 'success'
                        });
                        this.$router.push('/BlogEdit')
                    }else{
                        this.$message({
                            showClose: true,
                            message: '发布失败,请检查服务器是否连接正常',
                            type: 'error'
                        });
                    }
                })
            },
            toTop(){
                if(this.ifTop==0){
                    this.ifTop=1
                }else{
                    this.ifTop=0
                }
                if(this.ifTop==1){
                    this.$message({
                        showClose: true,
                        message: '成功修改为置顶！',
                        type: 'success'
                    });
                }else{
                    this.$message({
                        showClose: true,
                        message: '成功取消置顶！',
                        type: 'success'
                    });
                }

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
    .BlogChange  h2{
        margin: 10px auto;
        max-width: 960px;
        text-align: center;
    }
    #edit{
        height: 400px;
        margin-top: 20px;
    }
    .edit-button{
        margin: 10px auto;
        max-width: 960px;
        text-align: center;
    }
    .edit-button .el-button{
        width: 200px;
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