<template>
    <div>
        <Header></Header>
        <div class="mblog">
            <h2>{{blog.title}}</h2>

            <el-link icon="el-icon-edit" v-if="ownBlog">
               <router-link :to="{name:'BlogEdit',params:{blogId:blog.id}}"
               >编辑</router-link>
            </el-link>

            <el-divider></el-divider>
            <div class ="markdown-body" v-html="blog.content">

            </div>
        </div>
    </div>
</template>

<script>
    import Header from "../components/Header"
    import 'github-markdown-css/github-markdown.css'
    export default {
        name: "BlogDetail.vue",
        components: {Header},
        data(){
            return{
                blog:{
                    id:"",
                    title:"",
                    content:""
                },
                ownBlog:false
            }
        },
        created(){
            const blogId = this.$route.params.blogId;
            console.log(blogId)
            const _this = this
            this.$axios.get('/blog/'+blogId).then(res=>{
                const blog = res.data.data
                _this.blog.id = blog.id
                _this.blog.title = blog.title
                //这里要将md格式的文档进行转换。
                var MardownIt = require("markdown-it")
                var md = new MardownIt()
                var result = md.render(blog.content)
                //但是还不是很好看，我们还要进行一些样式的渲染。

                _this.blog.content = result

                _this.ownBlog = (blog.userId === _this.$store.getters.getUser.id)    //如果是当前用户的博客，才可以显示编辑。



            })
        }
    }
</script>

<style scoped>

    .mblog{
        box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
        width:100%;
        min-height: 700px;
        text-align: left;
        padding:20px
    }
    div{
        background-color:wheat;
    }
</style>