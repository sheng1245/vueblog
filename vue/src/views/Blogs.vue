<template>
    <div class="me">
        <Header></Header>
        <div class="block">
            <el-timeline class = "left1">
                <el-timeline-item :timestamp="blog.created" placement="top" v-for="(blog,index) in blogs" :key="index" class="me">
                    <el-card class="me">
                        <h4>
                            <router-link :to="{name:'BlogDetail',params:{blogId:blog.id}}">
                               {{blog.title}}
                            </router-link>
                        </h4>
                        <p>{{blog.description}}</p>
                        <router-link :to="{name: 'BlogEdit', params: {blogId: blog.id}}" type="success" v-if="userid1==blog.userId">
                            <el-link type="success">编辑</el-link>
                        </router-link>

                        <el-button @click="del(blog.id)" type="danger" icon="el-icon-delete" circle style="position:relative;float:right" v-if="userid1==blog.userId"></el-button>

                    </el-card>
                </el-timeline-item>

            </el-timeline>

            <el-pagination
                    background
                    layout="prev, pager, next"
                    :current-page="currentPage"
                    :page-size="pageSize"
                    :total="total"
                    @current-change=page
            >
            </el-pagination>
        </div>
    </div>
</template>

<script>
    import Header from '../components/Header'   //引入header
    export default {
        name: "Blogs.vue",
        components: {Header},
        data(){
            return{
                blogs:{},
                currentPage:1,
                total:0,
                pageSize:5,
                userid1:0
            }
        },
        methods:{
            page(currentPage){
                const _this = this
                _this.$axios.get("/blogs?currentPage="+currentPage).then(res=>{
                    console.log(res)
                    _this.blogs = res.data.data.records
                    //console.log(_this.blogs)
                    _this.currentPage = res.data.data.currentPage
                    _this.total = res.data.data.total
                    _this.pageSize = res.data.data.size
                    _this.userid1 = _this.$store.getters.getUser.id
                    console.log(_this.userid1)
                })
            },
            del(index){
                const _this = this
                //console.log(index)
                _this.$axios.get("/del/"+index).then(res=> {
                     //console.log("1111")
                    _this.page(1);     //进行函数的回调，这样就可以等同于刷新页面了。

                })
            }
        },
        created() {
            this.page(1)
        }
    }
</script>

<style scoped>


    .me{
       padding-right:28px;
    }
   .left1{
       text-align: left;
       margin:0 auto;
   }
</style>