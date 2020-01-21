<template> 
    <el-card class="form-container" shadow="never">
        <el-form :model="fenxiaoChecks" :rules="rules" ref="FenxiaoChecksFrom" label-width="150px">




                    <el-form-item
                            label="会员编号"
                            prop="memberId">
                        <el-input v-model="fenxiaoChecks.memberId" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="会员手机"
                            prop="phone">
                        <el-input v-model="fenxiaoChecks.phone" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="审核状态"
                            prop="status">
                        <el-input v-model="fenxiaoChecks.status" style="width: 370px;"/>
                    </el-form-item>




            <el-form-item>
                <el-button type="primary" @click="onSubmit('FenxiaoChecksFrom')">提交</el-button>
                <el-button v-if="!isEdit" @click="resetForm('FenxiaoChecksFrom')">重置</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>
<script>
    import {createFenxiaoChecks, getFenxiaoChecks, updateFenxiaoChecks} from '@/api/fenxiao/fenxiaoChecks'
    import SingleUpload from '@/components/Upload/singleUpload'

    const defaultFenxiaoChecks = {
        name: ''
    };
    export default {
        name: 'FenxiaoChecksDetail',
        components: {SingleUpload},
        props: {
            isEdit: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
            fenxiaoChecks:
            Object.assign({},
        defaultFenxiaoChecks),
            rules: {
                name: [
                    {required: true, message: '请输入品牌名称', trigger: 'blur'},
                    {min: 2, max: 140, message: '长度在 2 到 140 个字符', trigger: 'blur'}
                ],
                    logo
            :
                [
                    {required: true, message: '请输入品牌logo', trigger: 'blur'}
                ],
                    sort
            :
                [
                    {type: 'number', message: '排序必须为数字'}
                ],
            }
        }
        },
        created() {
            if (this.isEdit) {
                getFenxiaoChecks(this.$route.query.id).then(response => {
                    this.fenxiaoChecks = response.data;
            })
                ;
            } else {
                this.fenxiaoChecks = Object.assign({},
            defaultFenxiaoChecks)
                ;
            }
        },
        methods: {
            onSubmit(formName) {
                this.$refs[formName].validate((valid) => {
                    if(valid) {
                        this.$confirm('是否提交数据', '提示', {
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            type: 'warning'
                        }).then(() => {
                            if(this.isEdit
                    )
                        {
                            updateFenxiaoChecks(this.$route.query.id, this.fenxiaoChecks).then(response => {
                                if(response.code == 200
                        )
                            {
                                this.$refs[formName].resetFields();
                                this.$message({
                                    message: '修改成功',
                                    type: 'success',
                                    duration: 1000
                                });
                                this.$router.back();
                            }
                        else
                            {
                                this.$message({
                                    message: response.msg,
                                    type: 'error',
                                    duration: 1000
                                });
                            }

                        })
                            ;
                        }
                    else
                        {
                            createFenxiaoChecks(this.fenxiaoChecks).then(response => {
                                if(response.code == 200
                        )
                            {
                                this.$refs[formName].resetFields();
                                this.fenxiaoChecks = Object.assign({},
                            defaultFenxiaoChecks)
                                ;
                                this.$message({
                                    message: '提交成功',
                                    type: 'success',
                                    duration: 1000
                                });
                                this.$router.back();
                            }
                        else
                            {
                                this.$message({
                                    message: response.msg,
                                    type: 'error',
                                    duration: 1000
                                });
                            }

                        })
                            ;
                        }
                    })
                        ;

                    } else {
                        this.$message({
                            message: '验证失败',
                            type: 'error',
                            duration: 1000
                        });
                return false;
            }
            })
                ;
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
                this.fenxiaoChecks = Object.assign({},
            defaultFenxiaoChecks)
                ;
            }
        }
    }
</script>
<style>
</style>


