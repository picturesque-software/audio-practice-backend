<template>
    <div style="width: 100%;height: 100vh;background-color: darkcyan;overflow: hidden">
        <div style="width: 400px; margin: 100px auto">
            <div style="font-size: 30px; text-align: center; padding: 30px 0">welcome</div>
            <el-form ref="form" :model="form" size="normal" :rules="rules">
                <el-form-item prop="username">
                    <el-input prefix-icon="el-icon-user-solid" v-model="form.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input prefix-icon="el-icon-lock" v-model="form.password" show-password></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button style="width: 100%" type="primary" @click="login">login</el-button>
                </el-form-item>
              <el-form-item>
                <el-button style="width: 100%" type="primary" @click="register">register</el-button>
              </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
    import request from "../utils/request";

    export default {
        name: "Login",
        data(){
            return {
                form: {},
                rules: {
                    username: [
                        {required: true, message: '请输入用户名', trigger: 'blur'},
                    ],
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'},
                    ],
                }
            }
        },
        methods:{
            login(){
                this.$refs['form'].validate((valid) => {
                    if (valid) {
                        request.post("user/login",this.form).then(res=>{
                            if (res.data.code === '0') {
                                this.$message({
                                    type: "success",
                                    message: "登录成功"
                                })
                                sessionStorage.setItem("user", JSON.stringify(res.data.data))  // 缓存用户信息
                                this.$router.push("/")  //登陆成功后进行页面的跳转，跳转到主页
                            } else {
                                this.$message({
                                    type: "error",
                                    message: res.data.msg
                                })
                            }
                        })
                    }
                })
            },
            register(){
              this.$router.push("/register")
            }
        }
    }
</script>

<style scoped>

</style>