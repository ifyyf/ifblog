<template>
    <div class="AddBlogAdmin">
        <transition name="fade">
            <loading v-if="isLoading"></loading>
        </transition>
        <h2>弦外思念透窗花</h2>
        <h3 v-show="this.autoAdd" style="max-width: 960px;margin: 0 auto;text-align: center;color:#333333">于{{this.time}}自动保存博客成功</h3>
        <h4 v-show="this.autoAdd" style="max-width: 960px;margin: 0 auto;text-align: center;color: #fd7286">每3分钟自动保存一次</h4>
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
                @save="AutoAddBlog"
                style="height: 600px;"
        />
        <div class="edit-button">
            <el-button @click="addBlog" type="primary" round >提交</el-button>
        </div>
    </div>
</template>

<script>
    import Loading from '../components/loading'
    import 'mavon-editor/dist/markdown/github-markdown.min.css'
    export default {
        name: "AddBlogAdmin",
        components:{ Loading  },
        data(){
            return {
                isLoading:true,
                context: '# 标题\n正文',//输入的数据
                autoAdd:false,
                time:"2020-12-02 18:06",
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
                },
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
                    }else{
                        this.axios.get('/getAutoBlog', {
                            params: {
                                username:user.username,
                                password:user.password
                            }
                        }).then((res)=>{
                            this.context=res.data.data
                        })
                        this.isLoading=false
                    }
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
                var user=this.$store.getters.getUser
                // console.log(this.context)
                this.axios.post('/addBlog',
                    {
                        context:this.context,
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
            AutoAddBlog(){
                var user=this.$store.getters.getUser
                 // console.log(this.context)
                this.axios.post('/changeAutoBlog',
                    {
                        context:this.context,
                        username:user.username,
                        password:user.password
                    }).then((res)=>{
                    // console.log(res.data.data)
                })
                this.autoAdd=true
                this.time=new Date().toLocaleString()
                // console.log("自动保存")
            }
        },
        mounted() {
             this.autoChange=window.setInterval(()=>{
                setTimeout(this.AutoAddBlog,0)
            },180000)
        },
        destroyed() {
            clearInterval(this.autoChange)
        }
    }
</script>

<style scoped>
.AddBlogAdmin h2{
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