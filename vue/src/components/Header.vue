<template>
    <div>
        <h3>欢迎来到sheng的博客</h3>
        <div class="block"><el-avatar :size="50" :src="user.avatar"></el-avatar></div>
        <div>{{user.username}}</div>
        <div class="link">
             <span>
                <router-link :to="{name:'Blogs'}">
                          <el-link >主页</el-link>
                </router-link>
            </span>
            <el-divider direction="vertical"></el-divider>
            <span>
                <router-link :to="{name:'BlogAdd'}" type="success">
                          <el-link type="success" :disabled="!hasLogin">发表文章</el-link>
                </router-link>
            </span>


            <el-divider direction="vertical"></el-divider>
            <span v-show="!hasLogin"><el-link type="primary" href="/login">登录</el-link></span>
            <span v-show="hasLogin"><el-link type="danger" @click="logout">退出</el-link></span>

        </div>
    </div>
</template>

<script>
    export default {
        name: "Header",
        data(){
            return{
                user:{
                    username:'请先登录',
                    avatar:'https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png'
                },
                hasLogin:false,
            }
        },
        methods: {
            logout(){
                const _this = this;
                _this.$axios.get("/logout",{header:{
                    "token":localStorage.getItem("token")
                    }}).then(res=>{
                        _this.$store.commit("REMOVE_INFO")
                        _this.$router.push("/login")

                })
            }
        },
        created() {
            //页面数据回显
            if(this.$store.getters.getUser.username){
                this.user.username = this.$store.getters.getUser.username;
                //this.user.avatar = this.$store.getters.getUser.evatar;
                this.hasLogin = true;
            }
        }
    }
</script>

<style scoped>

    .link{
        margin:10px 0px;
    }
</style>