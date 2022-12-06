<template>
    <div style="padding: 10px">
        <!--    功能区域-->
        <div style="margin: 10px 0">
            <el-button type="primary" @click="add">新增</el-button>
            <el-button type="primary">导入</el-button>
            <el-button type="primary">导出</el-button>
        </div>
        <!--    搜索区域-->
        <div style="margin: 10px 0">
            <el-input v-model="search" placeholder="请输入关键字" style="width: 20%" clearable></el-input>
            <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
        </div>
        <!--      表格区域-->
        <el-table
                stripe
                :data="tableData"
                style="width: 100%">
            <el-table-column
                    prop="id"
                    label="ID"
                    sortable
            >
            </el-table-column>
            <el-table-column
                    prop="name"
                    label="书名">
            </el-table-column>
            <el-table-column
                    prop="price"
                    label="价格">
            </el-table-column>
            <el-table-column
                    prop="author"
                    label="作者">
            </el-table-column>
            <el-table-column
                    prop="createTime"
                    label="出版时间">
            </el-table-column>
            <el-table-column label="操作">
                <template #default="scope">
                    <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
                    <!--确认删除气泡框-->
                    <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.row.id)">
                        <template #reference>
                            <el-button size="mini" type="danger">删除</el-button>
                        </template>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
        <!--      分页区域-->
        <div style="margin: 10px 0">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :page-sizes="[5, 10, 20]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
            </el-pagination>


            <el-dialog title="提示" v-model="dialogVisible" width="30%">
                <el-form :model="form" label-width="120px">
                    <el-form-item label="书名">
                        <el-input v-model="form.name" style="width: 80%"></el-input>
                    </el-form-item>
                    <el-form-item label="价格">
                        <el-input v-model="form.price" style="width: 80%"></el-input>
                    </el-form-item>
                    <el-form-item label="作者">
                        <el-input v-model="form.author" style="width: 80%"></el-input>
                    </el-form-item>
                    <el-form-item label="出版时间">
                        <el-date-picker v-model="form.createTime" value-format="YYYY-MM-DD" type="date" style="width: 80%" clearable></el-date-picker>
                    </el-form-item>
                    <el-form-item label="封面">
                      <el-upload action="http://localhost:9876/files/upload" :on-success="fileUploadSuccess">
                        <el-button type="primary">Click to upload</el-button>
                        <template #tip>
                          <div class="el-upload__tip">
                            jpg/png files with a size less than 500kb
                          </div>
                        </template>
                      </el-upload>
                    </el-form-item>
                </el-form>
                <template #footer>
                <span class="dialog-footer">
                  <el-button @click="dialogVisible = false">取 消</el-button>
                  <el-button type="primary" @click="save">确 定</el-button>
                </span>
                </template>
            </el-dialog>


        </div>
    </div>
</template>

<script>



    import request from "../utils/request";


    export default {
        name: 'Book',
        components: {
        },

        data(){
            return{
                form: {},
                dialogVisible: false,
                search: '',
                currentPage:1,
                total:0,
                pageSize:10,
                // total可以从后台获取
                tableData:[]
            }
        },
        created() {
            this.load()
        },
        methods: {
          fileUploadSuccess(res){
            console.log(res)

          },
            load() {
                request.get("/book/get", {
                    params: {
                        pageNum: this.currentPage,
                        pageSize: this.pageSize,
                        search: this.search
                    }
                }).then(res => {
                    console.log(res);
                    this.tableData = res.data.data.records
                    this.total = res.data.data.total
                })
            },
            add() {
                this.dialogVisible = true;
                this.form = {}
            },
            save() {
                if (this.form.id) {    //update
                    request.put("/book/update", this.form).then(res => {
                        console.log(res)
                        if (res.data.code === '0') {
                            this.$message({
                                type: "success",
                                message: "更新成功"
                            })
                        } else {
                            this.$message({
                                type: "error",
                                message: res.data.msg
                            })
                        }

                    })
                } else {       //new
                    request.post("/book", this.form).then(res => {
                        console.log(res)
                        if (res.data.code === '0') {
                            this.$message({
                                type: "success",
                                message: "新增成功"
                            })
                        } else {
                            this.$message({
                                type: "error",
                                message: res.data.msg
                            })
                        }

                    })
                }
                this.load();   //刷新表格数据

                this.dialogVisible = false;

            },
            handleEdit(row) {
                this.form = JSON.parse(JSON.stringify(row))
                // 避免修改后点取消表格发生变化，故要深拷贝
                this.dialogVisible = true

            },
            handleSizeChange(pageSize) {
                this.pageSize=pageSize
                this.load()
            },
            handleCurrentChange(pageNum) {
                this.currentPage=pageNum
                this.load()
            },
            handleDelete(id){
                console.log(id)
                request.delete("book/delete/" + id).then(res=>{
                    if (res.data.code === '0') {
                        this.$message({
                            type: "success",
                            message: "删除成功"
                        })
                    } else {
                        this.$message({
                            type: "error",
                            message: res.data.msg
                        })
                    }
                    this.load() //reload the table
                })
            }
        }
    }
</script>
