<template>
    <div>
        <el-container>
            <el-header><i class="el-icon-s-custom"></i>admin</el-header>
            <el-main>
                <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                    <el-form-item label="用户名" prop="username">
                        <el-input v-model="ruleForm.username"></el-input>
                    </el-form-item>

                    <el-form-item label="密码" prop="password">
                        <el-input v-model="ruleForm.password"></el-input>
                    </el-form-item>

                    <el-form-item>
                        <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
                        <el-button @click="resetForm('ruleForm')">重置</el-button>
                    </el-form-item>
                </el-form>
            </el-main>
        </el-container>
    </div>
</template>

<script>
    export default {
        name: "Login",
        data() {
            return {
                ruleForm: {
                    username: 'sheng',
                    password: '111111',

                },
                rules: {
                    username: [
                        { required: true, message: '请输入用户名', trigger: 'blur' },
                        { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'blur' },
                        { min: 6, max: 18, message: '长度在 6 到 18 个字符', trigger: 'change' }
                    ]
                }
            };
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        const _this = this;
                        alert('submit!');
                        this.$axios.post('/login', this.ruleForm)
                            .then((response)=>{
                                //拿到前端的jwt，然后将它写入到session中，要让所有的组件都访问到这个jwt。
                               const jwt = response.headers.token;
                               console.log(response.headers)
                               const userInfo = response.data.data;
                               //然后将这两个参数提交到全局配置中去。
                                _this.$store.commit("SET_TOKEN",jwt);    //将信息写到全局中
                                _this.$store.commit("SET_USERINFO",userInfo);

                                //进行数据获取。
                                //console.log(_this.$store.getters.getUser);
                                _this.$router.push('/blogs');
                            })

                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.ruleForm.username = '';
                this.ruleForm.password = '';
            }
        }
    }
</script>

<style scoped>

    .el-header, .el-footer {
        background-color: #B3C0D1;
        color: #333;
        text-align: center;
        line-height: 60px;
    }

    .el-aside {
        background-color: #D3DCE6;
        color: #333;
        text-align: center;
        line-height: 200px;
    }

    .el-main {
        background-color: #E9EEF3;
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
    .demo-ruleForm{
        max-width: 500px;
        margin:0 auto;
    }
</style>