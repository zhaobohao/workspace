<template> 
    <el-card class="form-container" shadow="never">
        <el-form :model="fenxiaoUserRelate" :rules="rules" ref="FenxiaoUserRelateFrom" label-width="150px">




                    <el-form-item
                            label="邀请人"
                            prop="memberId">
                        <el-input v-model="fenxiaoUserRelate.memberId" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="被邀请人"
                            prop="inviteId">
                        <el-input v-model="fenxiaoUserRelate.inviteId" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="邀请级别"
                            prop="level">
                        <el-input v-model="fenxiaoUserRelate.level" style="width: 370px;"/>
                    </el-form-item>




            <el-form-item>
                <el-button type="primary" @click="onSubmit('FenxiaoUserRelateFrom')">提交</el-button>
                <el-button v-if="!isEdit" @click="resetForm('FenxiaoUserRelateFrom')">重置</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>
<script>
    import {createFenxiaoUserRelate, getFenxiaoUserRelate, updateFenxiaoUserRelate} from '@/api/fenxiao/fenxiaoUserRelate'
    import SingleUpload from '@/components/Upload/singleUpload'

    const defaultFenxiaoUserRelate = {
        name: ''
    };
    export default {
        name: 'FenxiaoUserRelateDetail',
        components: {SingleUpload},
        props: {
            isEdit: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
            fenxiaoUserRelate:
            Object.assign({},
        defaultFenxiaoUserRelate),
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
                getFenxiaoUserRelate(this.$route.query.id).then(response => {
                    this.fenxiaoUserRelate = response.data;
            })
                ;
            } else {
                this.fenxiaoUserRelate = Object.assign({},
            defaultFenxiaoUserRelate)
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
                            updateFenxiaoUserRelate(this.$route.query.id, this.fenxiaoUserRelate).then(response => {
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
                            createFenxiaoUserRelate(this.fenxiaoUserRelate).then(response => {
                                if(response.code == 200
                        )
                            {
                                this.$refs[formName].resetFields();
                                this.fenxiaoUserRelate = Object.assign({},
                            defaultFenxiaoUserRelate)
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
                this.fenxiaoUserRelate = Object.assign({},
            defaultFenxiaoUserRelate)
                ;
            }
        }
    }
</script>
<style>
</style>


