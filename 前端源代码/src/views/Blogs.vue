<template>
    <div id="blogsback">
        <transition name="fade">
            <loading v-if="isLoading"></loading>
        </transition>
        <div  :style ="note" id="img">
        <div class="m-head" >
            <div class="block">
                <a href="/Login">
                <el-popover
                        placement="top-start"
                        title="if"
                        width="200"
                        trigger="hover"
                        content="QQ:346313208">
                    <el-avatar :size="50" :src="circleUrl" slot="reference"></el-avatar>
                </el-popover>
                </a>
            </div>
            <div id="icon-div">
                <el-tooltip class="item" effect="dark" content="点击和我聊天" placement="left">
                    <img @click="toQQ" :src="qqUrl" alt="" style="width:30px;height:30px;margin-left:30px;margin-right: 30px;background-color: white">
                </el-tooltip>
                <el-tooltip class="item" effect="light" content="我的csdn" placement="bottom">
                    <img @click="toCsdn" :src="csdnUrl" alt="" style="width:30px;height:30px;margin-right: 30px;background-color: white">
                </el-tooltip>
                <el-tooltip class="item" effect="light" content="自由编程协会博客园" placement="bottom">
                    <img @click="toFpa" :src="fpaUrl" alt="" style="width:30px;height:30px;margin-right: 30px;background-color: white">
                </el-tooltip>
                <el-tooltip class="item" effect="dark" content="我的Gitee" placement="right">
                    <img @click="toGitee" :src="gitUrl" alt="" style="width:30px;height:30px;margin-right: 30px;background-color: white">
                </el-tooltip>
            </div>
            <div id="my-card">
                <el-card shadow="always" body-style="padding:0px">
                    <p id="star">
                        每颗星星都会以自己的方式闪耀着
                    </p>
                </el-card>
            </div>
        </div>
         <div id="weather">
            <div id="weather-v2-plugin-standard"></div>
        </div>
        </div>
        <div id="inline-input" >
            <el-autocomplete
                    class="inline-input"
                    v-model="input3"
                    :fetch-suggestions="querySearch"
                    placeholder="搜索博客"
                    :trigger-on-focus="false"
                    @select="handleSelect">
            </el-autocomplete>
        </div>
        <div class="block" id="block">
            <el-timeline v-show="ifTop">
                <el-timeline-item :timestamp='topBlog.time1' placement="top" v-for="topBlog in this.topBlogs">
                    <el-card @click.native="readBlog(topBlog.blogId)">
                        <h2 id="zhiding">
                            [置顶]
                        </h2>
                        <h3>{{topBlog.title}}</h3>
                        <p>
<!--                            {{ fontNumber(topBlog.body)}}-->
                            {{topBlog.body}}
                        </p>
                    </el-card>
                </el-timeline-item>
            </el-timeline>
            <el-timeline>
                <el-timeline-item :timestamp='blog.time1' placement="top" v-for="blog in this.blogs">
                    <el-card @click.native="readBlog(blog.blogId)">
                        <h3>{{blog.title}}</h3>
                        <p>
<!--                            {{fontNumber(blog.body)}}-->
                            {{blog.body}}
                        </p>
                    </el-card>
                </el-timeline-item>
            </el-timeline>
        </div>
        <div id="more-blog-button">
            <el-button type="primary" round class="blog_button" @click="moreBlog" v-show="this.showButton">加载更多</el-button>
        </div>
        <el-divider content-position="center" id="textme"></el-divider>
        <div id="foot">
            <a href="http://wpa.qq.com/msgrd?v=3&uin=346313208&site=qq&menu=yes" target="_blank">
                <p>联系我</p>
                <p>Author: 杨逸凡</p>
                <p>@CopyRight All Reserved!</p>
                <p>E-mail: 346313208@qq.com</p>
            </a>
        </div>
        <div style="width:310px;margin:30px auto;padding-left: 3% " id="beian">
            <a target="_blank" href="http://beian.miit.gov.cn" style="display:inline-block;text-decoration:none;height:20px;line-height:20px;">
                <img src="../assets/icp.png" style="float:left;height: 20px;width: 20px"/>
                <p style="float:left;height:20px;line-height:20px;margin: 0px 0px 0px 40px; color:#939393;text-align: center">湘ICP备 2020022350号</p>
            </a>
            <a target="_blank" href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=43022102000116" style="display:inline-block;text-decoration:none;height:20px;line-height:20px;">
                <img src="../assets/beian.png" style="float:left;"/>
                <p style="float:left;height:20px;line-height:20px;margin: 0px 0px 0px 5px; color:#939393;">湘公网安备 43022102000116号</p>
            </a>
            <p id="htmer_time" style="padding-bottom: 20px"></p>
        </div>
    </div>

</template>
<script>
    import Loading from '../components/loading'
    import axios from "../axios";
    export default {
        name: "Blogs",
        components:{ Loading },
        methods:{
            toQQ(){
                window.open("http://wpa.qq.com/msgrd?v=3&uin=346313208&site=qq&menu=yes","blank");
            },
            toCsdn(){
                window.open("https://blog.csdn.net/Jay_Chou345","blank")
            },
            toFpa(){
                window.open("https://freeprogramming.cn/BlogGarden.html?memberId=46","blank")
            },
            toGitee(){
                window.open("https://gitee.com/ifyyf","blank")
            },
            /**
             * 预览字数限制
             * @param date
             * @returns {string|*}
             */
            // fontNumber (date) {
            //     const length = date.length
            //     if (length > 35) {
            //         var str = ''
            //         str = date.substring(0, 35) + '......点击查看文章'
            //         return str
            //     } else {
            //         return date
            //     }
            // },
            readBlog(blogId) {
                // console.log(blogId)
                this.$router.push({
                    path:'BlogRead/'+blogId,
                    // params:{blog:blog}
                })
            },
            moreBlog(){
                this.axios.get('/getBlogs',
                    {
                        params: {
                            pageNum:++this.pageNum
                        }
                    }).then((response) => {
                    this.blogs=this.blogs.concat(response.data.data)
                    if(response.data.data.length!==5){
                        // console.log(response.data.data)
                        this.showButton=false;
                    }else{
                        this.showButton=true;
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
            getTopBlogs(){
                return this.axios.get('/getTopBlogs')
            },
            searchBlog(title){
                return this.axios.get('/searchBlog',
                    {
                        params: {
                            title:title
                        }
                    })
            },
            querySearch(queryString, cb) {
                var sblogs=[]
                this.searchBlog(queryString).then(res=>{
                    // console.log(res.data.data.length)
                    if(res.data.data.length==0){
                        sblogs.push({id:0,value:'没有找到该博客'})
                    }else{
                        for(let i=0;i<res.data.data.length;i++){
                            sblogs.push({id: res.data.data[i].blogId,value: res.data.data[i].title})
                        }
                    }
                })
                cb(sblogs);
            },
            handleSelect(item) {
                if(item.id!=0){
                    this.$router.push({
                        path:'BlogRead/'+item.id,
                        // params:{blog:blog}
                    })
                }
            },
        },
        data:function () {
            return{
                note: {
                    backgroundImage: "url("+require("../assets/20150905141402_driKX.jpeg")+")",
                    backgroundRepeat: "no-repeat",
                    backgroundSize: "100% 100%",
                    marginTop: "5px",
                },
                //这个是头像
                circleUrl: "http://q.qlogo.cn/g?b=qq&nk=346313208&s=160",
                qqUrl:this.axios.defaults.baseURL+"/img/qq.jpg",
                csdnUrl:this.axios.defaults.baseURL+"/img/csdn.png",
                fpaUrl:this.axios.defaults.baseURL+"/img/fpa.png",
                gitUrl:this.axios.defaults.baseURL+"/img/git.jpg",
                blogs:[],
                topBlogs:[],
                pageNum:1,
                showButton:true,
                isLoading:true,
                ifTop:false,
                input3: '',
                select: '',
            }
        },
        created() {
            // this.axios.get('http://if.vaiwan.com/getBlogs',
            //     {
            //         params: {
            //             pageNum:this.pageNum
            //         }
            //     }).then((response) => {
            //         this.blogs=response.data.data
            //     this.isLoading=false
            // })
            var me = this;
            this.axios.all([me.getBlogs(),me.getTopBlogs()])
                .then(me.axios.spread(function(getBlogs, getTopBlogs){
                    // console.log('所有请求完成')
                    // console.log('请求1结果',getBlogs)
                    // console.log('请求2结果',getComments)
                    me.blogs=getBlogs.data.data
                    me.topBlogs=getTopBlogs.data.data
                    // console.log(getTopBlogs)
                    if(getTopBlogs.data.data.length!==0){
                        me.ifTop=true;
                        // console.log("top")
                    }
                    if(me.blogs.length!==5){
                        // console.log(me.blogs)
                        me.showButton=false;
                    }else{
                        // console.log(me.blogs.length)
                        me.showButton=true;
                    }
                    // me.restaurants = me.blogs;
                    // console.log(me.restaurants)
                    // me.isLoading=false
                }))
        },
        mounted () {
            window.addEventListener('scroll', this.scroll,false);
            //天气插件
            window.WIDGET = {
                CONFIG: {
                    "layout": 1,
                    "width": "300",
                    "height": "150",
                    "background": 1,
                    "dataColor": "FFFFFF",
                    "language": "zh",
                    "borderRadius": 5,
                    "city": "CN101250106",
                    "key": "6yYvSSecPy"
                }
            };
            (function (d) {
                var c = d.createElement('link')
                c.rel = 'stylesheet'
                c.href = '/weather-standard.css'
                var s = d.createElement('script')
                s.src = '/weather-standard.js'
                var sn = d.getElementsByTagName('script')[0]
                sn.parentNode.insertBefore(c, sn)
                sn.parentNode.insertBefore(s, sn)
            })(document)
            //网站运行时间
            function secondToDate(second) {
                if (!second) {
                    return 0;
                }
                var time = new Array(0, 0, 0, 0, 0);
                if (second >= 365 * 24 * 3600) {
                    time[0] = parseInt(second / (365 * 24 * 3600));
                    second %= 365 * 24 * 3600;
                }
                if (second >= 24 * 3600) {
                    time[1] = parseInt(second / (24 * 3600));
                    second %= 24 * 3600;
                }
                if (second >= 3600) {
                    time[2] = parseInt(second / 3600);
                    second %= 3600;
                }
                if (second >= 60) {
                    time[3] = parseInt(second / 60);
                    second %= 60;
                }
                if (second > 0) {
                    time[4] = second;
                }
                return time;
            }
            function setTime() {
                //mouth要少一个月不然会出问题
                var create_time = Math.round(new Date(Date.UTC(2020, 10, 18, 0, 0, 0)).getTime() / 1000);
                var timestamp = Math.round((new Date().getTime() + 8 * 60 * 60 * 1000) / 1000);
                var currentTime = secondToDate((  timestamp-create_time));
                var currentTimeHtml = currentTime[0] + '年' + currentTime[1] + '天'
                    + currentTime[2] + '时' + currentTime[3] + '分' + currentTime[4]
                    + '秒';
                if(document.getElementById("htmer_time")!=null)
                    document.getElementById("htmer_time").innerHTML = "小世界已苟活了"+currentTimeHtml;
            }
            //即时刷新
            setInterval(setTime, 1000);
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
        },
        computed:{

        }
    }
</script>

<style scoped>
    #blogsback{
        margin:0;
        padding:0;
        height: 100%;
        /*overflow:hidden;*/
    }
    .el-card:hover{
        -webkit-animation: pulse 3s;
        animation: pulse 3s;
        box-shadow: 0 0 0 2em rgba(255, 255, 255, 0);
    }
    #my-card .el-card{
        width: 300px;
        margin: 10px auto;
        text-align: center;
        background-color: #ffd400;

    }
    #my-card .el-card:hover{
        opacity:0.8;
        transform: scale(1.1);
        transition: all 1s ease 0s ;
        -webkit-user-select:none;
        -moz-user-select:none;
        -ms-user-select:none;
        user-select:none;
    }
    .block{
        max-width: 960px;
        margin: 0 auto;
    }
    .block .el-timeline{
        padding-left: 0px;

    }
    .block .el-card{
        background-color: rgba(255,255,255, 0.3);
    }
    .block .el-card:hover{
        cursor:pointer;
    }
    .m-head{
        max-width: 960px;
        margin: 10px auto;
        text-align: center;

    }
    #more-blog-button{
        max-width: 960px;
        margin: 0 auto;
        text-align: center;
    }
    .blog_button{
        width: 200px;
    }
    #icon-div{
        margin-top: 10px;
    }
    #icon-div img{
        text-align: center;
        background-color: #ffd400;
    }
    #icon-div img:hover{
        opacity:0.8;
        transform: scale(1.1);
        transition: all 1s ease 0s ;
        -webkit-user-select:none;
        -moz-user-select:none;
        -ms-user-select:none;
        user-select:none;
        /*鼠标变小手*/
        cursor:pointer;
    }
    #foot {
        max-width:300px;
        margin: 0 auto -20px;
    }
    #foot a{
        text-decoration: none;
        font-size: 15px;
        line-height:10px;
        color:coral;
        font-style: italic;
        text-align:center;
    }
    #weather{
        max-width: 300px;
        max-height: 150px;
        margin: 10px auto 30px;
        text-align: center;
        /*position: relative;*/
    }
    /*#weather-v2-plugin-standard{*/
    /*    max-width: 300px;*/
    /*    max-height: 150px;*/
    /*    !*实现水平垂直居中*!*/
    /*    position: absolute;*/
    /*    top: 50%;*/
    /*    left: 50%;*/
    /*    transform: translate(-50%,-50%);*/
    /*}*/
    #weather:hover{
        opacity:0.8;
        transform: scale(1.1);
        transition: all 1s ease 0s ;
        -webkit-user-select:none;
        -moz-user-select:none;
        -ms-user-select:none;
        user-select:none;
    }
    #img{
        max-width: 960px;
        margin: 0 auto;
        text-align: center;
        padding-top: 10px;
        padding-bottom: 10px;
    }

    #inline-input{
        margin: 10px auto;
        max-width: 960px;
    }
    #inline-input .inline-input{
        width:100%;
        opacity: 0.7;
    }
    .el-divider{
        margin-top: 80px;

    }
    #htmer_time{
        color: #fd7286;
        font-size: large;
        font-weight: bolder;
        margin-left: -25px;
        background:linear-gradient(135deg,#ffeb3b,#f441a5,#03a9f4,#ffeb3b);
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
        animation:animate 3s linear infinite;
    }
    #star{
        /*background:linear-gradient(135deg,#f441a5,#03a9f4,lightgreen);
        /*-webkit-background-clip: text;*/
        /*-webkit-text-fill-color: transparent;*/
        /*animation:animate 2s linear infinite;*/
    }
    #zhiding{
        margin: -5px -5px -5px 0;
        background:linear-gradient(135deg,#ffeb3b,#f441a5,#03a9f4,#ffeb3b);
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
        animation:animate 3s linear infinite;
    }
    @keyframes animate{
        100%{
            filter: hue-rotate(360deg);
        }
    }
    #textme{
        background-color: rgba(255,255,255, 0.8);
    }
</style>
<style>

</style>