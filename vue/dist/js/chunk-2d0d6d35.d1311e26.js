(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d0d6d35"],{"73cf":function(e,r,t){"use strict";t.r(r);var o=t("7a23"),n={style:{width:"100%",height:"100vh","background-color":"darkcyan",overflow:"hidden"}},u={style:{width:"400px",margin:"100px auto"}},i=Object(o["n"])("div",{style:{"font-size":"30px","text-align":"center",padding:"30px 0"}},"welcome to register",-1),c=Object(o["p"])("register"),s=Object(o["p"])("return to login");function l(e,r,t,l,a,d){var f=Object(o["S"])("el-input"),m=Object(o["S"])("el-form-item"),b=Object(o["S"])("el-button"),p=Object(o["S"])("el-form"),g=Object(o["S"])("router-view");return Object(o["L"])(),Object(o["m"])("div",n,[Object(o["n"])("div",u,[i,Object(o["q"])(p,{ref:"form",model:a.form,size:"normal",rules:a.rules},{default:Object(o["hb"])((function(){return[Object(o["q"])(m,{prop:"username"},{default:Object(o["hb"])((function(){return[Object(o["q"])(f,{"prefix-icon":"el-icon-user-solid",modelValue:a.form.username,"onUpdate:modelValue":r[0]||(r[0]=function(e){return a.form.username=e})},null,8,["modelValue"])]})),_:1}),Object(o["q"])(m,{prop:"password"},{default:Object(o["hb"])((function(){return[Object(o["q"])(f,{"prefix-icon":"el-icon-lock",modelValue:a.form.password,"onUpdate:modelValue":r[1]||(r[1]=function(e){return a.form.password=e}),"show-password":""},null,8,["modelValue"])]})),_:1}),Object(o["q"])(m,{prop:"confirm"},{default:Object(o["hb"])((function(){return[Object(o["q"])(f,{"prefix-icon":"el-icon-lock",modelValue:a.form.confirm,"onUpdate:modelValue":r[2]||(r[2]=function(e){return a.form.confirm=e}),"show-password":""},null,8,["modelValue"])]})),_:1}),Object(o["q"])(m,null,{default:Object(o["hb"])((function(){return[Object(o["q"])(b,{style:{width:"100%"},type:"primary",onClick:d.register},{default:Object(o["hb"])((function(){return[c]})),_:1},8,["onClick"])]})),_:1}),Object(o["q"])(m,null,{default:Object(o["hb"])((function(){return[Object(o["q"])(b,{style:{width:"100%"},type:"primary",onClick:d.returnToLogin},{default:Object(o["hb"])((function(){return[s]})),_:1},8,["onClick"])]})),_:1})]})),_:1},8,["model","rules"])]),Object(o["q"])(g)])}var a=t("b775"),d={name:"Register",data:function(){return{form:{},rules:{username:[{required:!0,message:"请输入用户名",trigger:"blur"}],password:[{required:!0,message:"请输入密码",trigger:"blur"}],confirm:[{required:!0,message:"请确认密码",trigger:"blur"}]}}},methods:{register:function(){var e=this;if(this.form.password!==this.form.confirm)return this.$message({type:"error",message:"两次密码输入不一致"}),void(this.form={});this.$refs["form"].validate((function(r){r&&a["a"].post("/user/register",e.form).then((function(r){"0"===r.data.code?(e.$message({type:"success",message:"注册成功"}),e.$router.push("/login")):e.$message({type:"error",message:r.data.msg})}))}))},returnToLogin:function(){this.$router.push("/login")}}};d.render=l;r["default"]=d}}]);
//# sourceMappingURL=chunk-2d0d6d35.d1311e26.js.map