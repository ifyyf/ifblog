<template>
    <div>
        <transition name="fade">
            <loading v-if="isLoading"></loading>
        </transition>
        <div id="login">
            <h1>Login</h1>
        <el-container>
            <el-main>
                <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                    <el-form-item label="用户名" prop="username">
                        <el-input v-model="ruleForm.username"></el-input>
                    </el-form-item>
                    <el-form-item label="密码" prop="password">
                        <el-input v-model="ruleForm.password" show-password></el-input>
                    </el-form-item>
                    <el-form-item class="submit">
                        <el-button id="denglu" type="primary" @click="submitForm('ruleForm')">登录</el-button>
                        <el-button id="fanhui" @click="resetForm('ruleForm')">返回</el-button>
                    </el-form-item>
                </el-form>
            </el-main>
        </el-container>
        </div>
    </div>
</template>

<script>
    import Loading from '../components/loading'
    // import Header from "../components/Header";
    export default {
        components:{ Loading  },
        data() {
            return {
                isLoading:true,
                ruleForm: {
                    username: '',
                    password: '',
                    identity1:'',
                },
                userInfo:{
                    id:0,
                    identity1:'',
                    username:"",
                    password:"",
                },
                rules: {
                    username: [
                        { required: true, message: '请输入用户名', trigger: 'blur' },
                        { min: 3, max: 50, message: '用户名不能少于3个字符', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'change' },
                        { min: 6, message: '密码不能少于6个字符', trigger: 'blur' }
                    ],
                },
            };
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        // alert('submit!');
                        //_this指全局(store)
                        const _this=this
                        this.axios.post('/login',this.ruleForm)
                        .then((res)=>{
                            const userInfo=res.data.data;
                            // console.log(res)
                            if(userInfo!==null){
                                // console.log(res)
                                // console.log(userInfo)
                                //将数据提交到session
                                _this.$store.commit("set_userInfo",userInfo)
                                //获取session中的user信息
                                // console.log(_this.$store.getters.getUser)
                                //跳转页面
                                _this.$router.push('/BlogEdit')
                                this.$message({
                                    message: userInfo.identity1+',登陆成功',
                                    type: 'success',
                                    duration:1000
                                });
                            }else{
                                this.$message({
                                    message: '用户名或密码错误',
                                    type: 'warning',
                                    duration:2000
                                });
                            }
                        })
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
                this.$router.push('/')
            }
        },
        created() {
            //在login页面加载之前就判断session中是否有数据存在,并和数据库进行校验
            //获取session中的user信息
            // console.log(this.$store.getters.getUser)
            var that=this
            // var user=null
            that.userInfo=this.$store.getters.getUser
            // console.log(this.$store.getters.getUser)
            // console.log(!that.userInfo)
            if(that.userInfo){
                this.axios.post('/login',
                    {
                        username:that.userInfo.username,
                        password:that.userInfo.password
                    }).then((res)=>{
                    that.userInfo=res.data.data
                    if(that.userInfo){
                        // console.log(res.data.username)
                        // console.log(that.userInfo.username)
                        //跳转页面
                        this.$router.push('/BlogEdit')
                    }
                })
            }
            this.isLoading=false
        }
    }
</script>

<style scoped>
    #login{
        height: 100%;
        max-width: 960px;
        margin: 50px auto;
        /*background-image: linear-gradient(to right,#fbc2eb,#a6c1ee);*/
    }
    #login h1{
        margin: 0 auto;
        text-align: center;
    }
    #login .el-form-item{
        margin-left: -40px;
    }
    .el-header, .el-footer {
        background-color: lightblue;
        color: #333;
        text-align: center;
        line-height: 60px;
    }
    .el-main {
        color: #333;
        text-align: center;
        line-height: 160px;
    }

    body > .el-container {
        margin-bottom: 40px;
    }

    .el-container:nth-child(5) .el-aside,
    .el-container:nth-child(6) .el-aside {
        line-height: 260px;
    }

    .el-container:nth-child(7) .el-aside {
        line-height: 320px;
    }
    /*#logo{*/
    /*    margin-top: 10px;*/
    /*    height: 60%;*/
    /*    float: left;*/
    /*}*/
    .demo-ruleForm {
        max-width: 600px;
        margin: 0 auto;
        text-align: center;
    }
    /*按钮发光*/
    .submit .el-button{
        margin-top: 20px;
        margin-left: 30px;
        transform:translate(-50%,-50%);
        text-align: center;
        color:#fff;
        /* 控制文本的大小 uppercase定义仅有大写字母*/
        text-transform:uppercase;
        text-decoration:none;
        font-family: sans-serif;
        box-sizing:border-box;
        /*background:linear-gradient(90deg,#03a9f4,#f441a5,#ffeb3b,#03a9f4);*/
        /*background-size:400%;*/
        border-radius:30px;
    }
    .submit #denglu{
        background:linear-gradient(90deg,#ffeb3b,#f441a5,#03a9f4,#ffeb3b);
        background-size:400%;
    }
    .submit #fanhui{
        background:linear-gradient(90deg,#03a9f4,#f441a5,#ffeb3b,#03a9f4);
        background-size:400%;
    }
    .submit .el-button:hover{
        animation:animate 8s linear infinite;
    }
    @keyframes animate{
        0%{
            background-position:0%;
        }
        100%{
            background-position:400%;
        }
    }
    .submit .el-button::before{
        content:'';
        position:absolute;
        top:-5px;
        left:-5px;
        right:-5px;
        bottom:-5px;
        z-index:-1;
        background:linear-gradient(90deg,#ffeb3b,#f441a5,#03a9f4,#ffeb3b);
        background-size:400%;
        border-radius:40px;
        opacity:0;
        transition:0.5s;
    }
    .submit .el-button:hover:before{
        filter:blur(20px);
        opacity:1;
        animation:animate 8s linear infinite;
    }
</style>