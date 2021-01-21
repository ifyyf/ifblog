<template>
    <div id="statement">
        <transition name="fade">
            <loading v-if="isLoading"></loading>
        </transition>
        <mavon-editor
                class="md"
                :value="statement"
                :subfield = "prop.subfield"
                :defaultOpen = "prop.defaultOpen"
                :toolbarsFlag = "prop.toolbarsFlag"
                :editable="prop.editable"
                :scrollStyle="prop.scrollStyle"
                :ishljs = "true"
        />
    </div>
</template>

<script>
    import Loading from '../components/loading'
    // import Header from "../components/Header";
    export default {
        name: "Statement",
        components:{ Loading  },
        data(){
            return{
                isLoading:true,
                statement:'',
            }
        },
        created() {
            this.axios.get('/getStatement').then((response) => {
                this.statement=response.data.data.statement
                // console.log(about)
                // this.isLoading=false
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
    #statement{
        max-width: 960px;
        margin: 0 auto;
        text-align: center;
    }
</style>