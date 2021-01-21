<template>
    <div>
        <transition name="fade">
            <loading v-if="isLoading"></loading>
        </transition>
        <el-timeline v-show="ifMessage" style="margin: 20px auto;max-width: 960px;">
            <el-timeline-item :timestamp='message.time1' placement="top" v-for="message in this.messages">
                <el-card style="background-color: rgba(255,255,255, 0.3);">
                    <h3 style="color: #fd7286">{{message.identity1}}:</h3>
                    <h4>
                    <pre style="padding-left:40px;font-size:20px;" v-html="message.body">
<!--                    <article class="markdown-body" style="text-align:left;font-size:20px" v-html="message.body"></article>-->
                    </pre>
                    </h4>
                </el-card>
            </el-timeline-item>
        </el-timeline>
        <div id="more-blog-button">
            <el-button type="primary" round class="blog_button" @click="moreMessage" v-show="this.showButton">加载更多</el-button>
            <el-button @click="addMessage" type="primary" icon="el-icon-edit" circle></el-button>
        </div>
        <div id="setMessage">
            <el-form v-show="ifAddMessage" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
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
                    <Vcode :show="isShow" @success="onSuccess()" @close="onClose()" />
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
    // import SliderCheck from '../components/SliderCheck'
    import Vcode from "vue-puzzle-vcode";
    // import marked from 'marked'
    export default {
        name: "Message",
        components:{ Loading,Vcode  },
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
                isLoading:true,
                ifMessage:false,
                ifAddMessage:false,
                isShow:false,
                messages:[],
                pageNum:1,
                showButton:true,
                textarea:'',
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
                        { required: true, message: '请填写留言内容', trigger: 'blur'},
                        { min: 5, message: '长度在5个字符以上', trigger: 'blur'}
                    ]
                }
            }
        },
        created() {
            this.axios.get('/getMessage',{
                params: {
                    pageNum:this.pageNum
                }
            }).then((response) => {
                this.messages=response.data.data
                // console.log(this.messages)
                if(this.messages.length!==0){
                    this.ifMessage=true
                }
                this.isLoading=false
            })
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
            },
        },
        methods:{
            moreMessage(){
                this.axios.get('/getMessage',
                    {
                        params: {
                            pageNum:++this.pageNum
                        }
                    }).then((response) => {
                    this.messages=this.messages.concat(response.data.data)
                    if(response.data.data.length!==5){
                        // console.log(response.data.data)
                        this.showButton=false;
                    }
                })
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
            addMessage(){
                this.ifAddMessage=!this.ifAddMessage;
            },
            // 滑块验证成功回调
            onSuccess(msg) {
                this.isShow=false
                this.axios.get('/addMessage',{
                    params:{
                        identity1:this.ruleForm.identity1,
                        body:this.ruleForm.body,
                        email:this.ruleForm.email
                    }
                })
                    .then((res)=>{
                        // console.log(res)
                        // console.log(this.ruleForm.email)
                    })
                this.$message({
                    message: '感谢您的留言,待审核完毕将会显示您的留言！',
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
        },
    }
</script>

<style scoped>
    h1{
        margin: 0 auto;
        max-width: 960px;
        text-align: center;
    }
    #more-blog-button{
        max-width: 960px;
        margin: 0 auto;
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
    #setMessage{
        margin: 10px auto;
        max-width: 500px;
    }
    #setMessage #message-button{
        margin: 0 auto;
        text-align: center;
    }
    #ifPeople{
        margin: 0 auto;
        max-width: 960px;
        text-align: center;
    }
    .el-timeline{
        padding-left: 0px;
    }
    pre {
    white-space: pre-wrap;
        white-space: -moz-pre-wrap;
        /*white-space: -pre-wrap;*/
        white-space: -o-pre-wrap;
        *word-wrap: break-word;
        *white-space : normal ;
        /*white-space:pre-line*/
    }
</style>