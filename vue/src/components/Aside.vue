<template>
    <div>
        <el-menu
                style="width: 200px;min-height: calc(100vh - 50px)"
                :default-active="path"
                router
                class="el-menu-vertical-demo">
            <!--                注意calc运算符前后都要保留空格-->
            <el-sub-menu index="1">
                <template #title>系统管理</template>
                <el-menu-item index="/user" >用户管理</el-menu-item>
                <el-menu-item index="/book" >book管理</el-menu-item>
              <el-menu-item index="/news" >news管理</el-menu-item>
            </el-sub-menu>
        </el-menu>
    </div>
</template>

<script>
    export default {
        name: "Aside",
        data(){
            return{
                path:this.$route.path   //设置默认高亮菜单
            }

        },
        created() {
            console.log(this.$route.path)
        }
    }
</script>

<style scoped>

</style>