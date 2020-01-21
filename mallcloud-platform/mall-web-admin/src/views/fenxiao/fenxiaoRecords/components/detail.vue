<template> 
    <el-card class="form-container" shadow="never">
        <el-form :model="fenxiaoRecords" :rules="rules" ref="FenxiaoRecordsFrom" label-width="150px">



                    <el-form-item
                            label="orderId"
                            prop="orderId">
                        <el-input v-model="fenxiaoRecords.orderId" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="memberId"
                            prop="memberId">
                        <el-input v-model="fenxiaoRecords.memberId" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="inviteId"
                            prop="inviteId">
                        <el-input v-model="fenxiaoRecords.inviteId" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="money"
                            prop="money">
                        <el-input v-model="fenxiaoRecords.money" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="level"
                            prop="level">
                        <el-input v-model="fenxiaoRecords.level" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="status"
                            prop="status">
                        <el-input v-model="fenxiaoRecords.status" style="width: 370px;"/>
                    </el-form-item>




            <el-form-item>
                <el-button type="primary" @click="onSubmit('FenxiaoRecordsFrom')">提交</el-button>
                <el-button v-if="!isEdit" @click="resetForm('FenxiaoRecordsFrom')">重置</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>
<script>
    import {createFenxiaoRecords, getFenxiaoRecords, updateFenxiaoRecords} from '@/api/fenxiao/fenxiaoRecords'
    import SingleUpload from '@/components/Upload/singleUpload'

    const defaultFenxiaoRecords = {
        name: ''
    };
    export default {
        name: 'FenxiaoRecordsDetail',
        components: {SingleUpload},
        props: {
            isEdit: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
            fenxiaoRecords:
            Object.assign({},
        defaultFenxiaoRecords),
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
                getFenxiaoRecords(this.$route.query.id).then(response => {
                    this.fenxiaoRecords = response.data;
            })
                ;
            } else {
                this.fenxiaoRecords = Object.assign({},
            defaultFenxiaoRecords)
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
                            updateFenxiaoRecords(this.$route.query.id, this.fenxiaoRecords).then(response => {
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
                            createFenxiaoRecords(this.fenxiaoRecords).then(response => {
                                if(response.code == 200
                        )
                            {
                                this.$refs[formName].resetFields();
                                this.fenxiaoRecords = Object.assign({},
                            defaultFenxiaoRecords)
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
                this.fenxiaoRecords = Object.assign({},
            defaultFenxiaoRecords)
                ;
            }
        }
    }
</script>
<style>
</style>


