<template>
    <div id="blog_read">
        <ReadHead></ReadHead>
        <transition name="fade">
            <loading v-if="isLoading"></loading>
        </transition>
        <div class="title">
            <h1>{{this.blog.title}}</h1>
        </div>
        <div class="time">
            <h3>时间：{{this.blog.time1}}</h3>
        </div>
        <div class="body">
<!--            <p>内容：{{this.blog.body}}</p>-->
<!--            <article class="markdown-body" style="text-align:left;font-size:20px" v-html="articleDetail"></article>-->
            <mavon-editor
            class="md"
            :value="blog.body"
            :subfield = "prop.subfield"
            :defaultOpen = "prop.defaultOpen"
            :toolbarsFlag = "prop.toolbarsFlag"
            :editable="prop.editable"
            :scrollStyle="prop.scrollStyle"
            :ishljs = "true"
            v-highlight
            />
        </div>
<!--    评论    -->
        <div id="more-comment-button">
            <el-button type="primary" round class="blog_button" @click="moreComment" v-show="this.showButton">加载评论</el-button>
            <el-button @click="addComment" type="primary" icon="el-icon-edit" circle></el-button>
        </div>
        <el-timeline v-show="ifNoComment" style="margin: 20px auto;max-width: 960px;padding-left: 0px">
                <el-card style="max-width: 500px;
        margin: 0 auto;
        text-align: center;">
                    <h3 style="color: #fd7286">Nobody</h3>
                    <h4><pre style="font-size:20px;" >还没有评论哦</pre></h4>
                </el-card>
<!--            <a href="" style="font-size: 20px;" v-show="ifAdmin">回复</a>-->
        </el-timeline>

        <el-timeline v-show="ifComment" style="margin: 20px auto;max-width: 960px;padding-right: 60px">
            <el-timeline-item :timestamp='comment.time1' placement="top" v-for="comment in this.comments">
                <el-card style="max-width: 500px;
        margin: 0 auto;
        text-align: center;">
                    <h3 style="color: #fd7286">{{comment.identity1}}:</h3>
                    <h4>
                    <pre style="font-size:20px;" v-html="comment.body">
<!--                    <article class="markdown-body" style="text-align:left;font-size:20px" v-html="message.body"></article>-->
                    </pre>
                    </h4>
<!--                    <a href="" v-show="ifAdmin" style="font-size: 20px;z-index: 5000">回复</a>-->
                </el-card>
            </el-timeline-item>
        </el-timeline>
        <div class="addComment" v-show="ifShowAddComment">
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                <el-form-item label="姓名" prop="identity1">
                    <el-input v-model="ruleForm.identity1"></el-input>
                </el-form-item>
                <el-form-item  label="邮箱地址" prop="email">
                    <el-input v-model="ruleForm.email"></el-input>
                </el-form-item>
                <el-form-item label="留言内容" prop="body">
                    <el-input type="textarea" v-model="ruleForm.body"></el-input>
                </el-form-item>
                <div id="ifPeople">
                    <Vcode id="BlogReadVcode" :show="isShow" @success="onSuccess()" @close="onClose()" />
                </div>
                <el-form-item id="message-button">
                    <el-button type="primary" @click="submitForm('ruleForm')">提交审核</el-button>
                    <el-button @click="resetForm('ruleForm')">重置</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
    import Loading from '../components/loading'
    import ReadHead from "../components/ReadHead";
    import Vcode from "vue-puzzle-vcode";
    import 'mavon-editor/dist/markdown/github-markdown.min.css';
    export default {
        name: "BlogRead",
        components:{ReadHead, Loading ,Vcode, },
            created(){
                this.blogId=this.$route.params.blogId
                this.axios.get('/readBlog',
                    {
                        params: {
                            blogId:this.blogId
                        }
                    }).then((response) => {
                    this.blog=response.data.data
                })
                var user=this.$store.getters.getUser
                if(user!==null){
                    this.axios.post('/login',
                        {
                            username:user.username,
                            password:user.password
                        }).then((res)=>{
                            user=res.data.data
                        if(user){
                            this.ifAdmin=true
                        }
                    })
                }
                // this.isLoading=false
            },
        data(){
            var checkEmail = (rule, value, cb) => {
                // 验证邮箱的正则表达式
                const regEmail = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
                if (regEmail.test(value)) {
                    // 合法的邮箱
                    return cb();
                }
                cb(new Error("请输入合法的邮箱"));
            };
            return{
                ruleForm: {
                    identity1: '',
                    email:'',
                    body: '',
                },
                rules: {
                    identity1: [
                        { required: true, message: '请输入姓名', trigger: 'blur' },
                        { min: 2, max: 8, message: '长度在 2 到 8 个字符', trigger: 'blur' }
                    ],
                    email: [
                        { required: true, message: '请输入邮箱(不会公开)', trigger: 'blur' },
                        { min: 5, max: 30, message: '长度在 5 到 30 个字符', trigger: 'blur' },
                        {
                            validator: checkEmail,
                            message: "请输入正确的邮箱地址",
                            trigger: "blur"
                        }
                    ],
                    body: [
                        { required: true, message: '请填写评论内容', trigger: 'blur'},
                        { min: 5, message: '长度在5个字符以上', trigger: 'blur'}
                    ]
                },
                blog:{},
                blogId:'',
                isLoading:true,
                ifShowAddComment:false,
                showButton:true,
                isShow:false,
                ifComment:false,
                comments:[],
                pageNum:1,
                ifNoComment:false,
                ifAdmin:false,
            }
        },
        computed: {
            prop () {
                let data = {
                    subfield: false,// 单双栏模式
                    defaultOpen: 'preview',//edit： 默认展示编辑区域 ， preview： 默认展示预览区域
                    editable: false,
                    toolbarsFlag: false,
                    scrollStyle: true
                }
                return data
            }
        },
        methods:{
            addComment(){
                this.ifShowAddComment=!this.ifShowAddComment
            },
            moreComment(){
                this.showButton=false
                this.axios.get('/getComment',{
                    params:{
                        blogId:this.$route.params.blogId,
                        pageNum:this.pageNum
                    }
                }).then((res)=>{
                    // console.log(res)
                    // console.log(this.ruleForm.email)
                    if(res.data.data.length===0){
                        this.ifNoComment=true
                    }else{
                        this.comments=res.data.data
                        this.ifNoComment=false
                        this.ifComment=true
                        // console.log(res)
                        // this.ifShowAddComment=true
                    }
                })
            },
            // 滑块验证成功回调
            onSuccess(msg,blogId) {
                this.isShow=false
                this.axios.get('/addComment',{
                    params:{
                        identity1:this.ruleForm.identity1,
                        body:this.ruleForm.body,
                        email:this.ruleForm.email,
                        blogId:this.$route.params.blogId
                    }
                })
                    .then((res)=>{
                        // console.log(res)
                        // console.log(this.ruleForm.email)
                    })
                this.$message({
                    message: '感谢您的留言,待审核完毕将会显示您的评论！',
                    type: 'success'
                });
                // this.resetForm(this.ruleForm)
                setTimeout(function(){
                    location.reload();   //可以是一句或是很多句代码，也可以是个函数
                },2000);　　　　//延时2秒
            },
            // 滑块验证失败回调
            onClose() {
                this.isShow=false
                this.$message({
                    message: '取消验证！',
                    type: 'warning'
                });
            },
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if(valid){
                        this.isShow=true
                    }else {
                        this.$message({
                            message: '请填写完整',
                            type: 'warning'
                        });
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
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
    #blog_read{
        max-width: 960px;
        margin: 0 auto;
        text-align: center;
        position: relative;
        z-index: 90;
    }
    .title{
        margin-top: 5%;
    }
    .time{
        margin-left: 50%;
    }
    #more-comment-button{
        max-width: 960px;
        margin: 20px auto;
        text-align: center;
    }
    .addComment{
        max-width: 500px;
        margin: 0 auto;
        text-align: center;
        padding-right: 60px;
    }
    .addComment #BlogReadVcode{
        position:static;
        left:0px;
        top:0px;
        z-index:100;
    }
    /* 可一次性设置样式*/
    a:hover, a:visited, a:link, a:active {
        color: coral;
        text-decoration: none;
    }
</style>