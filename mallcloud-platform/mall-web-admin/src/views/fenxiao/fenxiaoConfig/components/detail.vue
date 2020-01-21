<template> 
    <el-card class="form-container" shadow="never">
        <el-form :model="fenxiaoConfig" :rules="rules" ref="FenxiaoConfigFrom" label-width="150px">




                    <el-form-item
                            label="状态"
                            prop="status">
                        <el-input v-model="fenxiaoConfig.status" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="返佣类型"
                            prop="type">
                        <el-input v-model="fenxiaoConfig.type" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="商品返佣比例"
                            prop="fanPercent">
                        <el-input v-model="fenxiaoConfig.fanPercent" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="到账类型"
                            prop="fanType">
                        <el-input v-model="fenxiaoConfig.fanType" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="提现方式"
                            prop="withdrawType">
                        <el-input v-model="fenxiaoConfig.withdrawType" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="一级返佣"
                            prop="onePercent">
                        <el-input v-model="fenxiaoConfig.onePercent" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="二级返佣"
                            prop="twoPercent">
                        <el-input v-model="fenxiaoConfig.twoPercent" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="三级返佣"
                            prop="threePercent">
                        <el-input v-model="fenxiaoConfig.threePercent" style="width: 370px;"/>
                    </el-form-item>







            <el-form-item>
                <el-button type="primary" @click="onSubmit('FenxiaoConfigFrom')">提交</el-button>
                <el-button v-if="!isEdit" @click="resetForm('FenxiaoConfigFrom')">重置</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>
<script>
    import {createFenxiaoConfig, getFenxiaoConfig, updateFenxiaoConfig} from '@/api/fenxiao/fenxiaoConfig'
    import SingleUpload from '@/components/Upload/singleUpload'

    const defaultFenxiaoConfig = {
        name: ''
    };
    export default {
        name: 'FenxiaoConfigDetail',
        components: {SingleUpload},
        props: {
            isEdit: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
            fenxiaoConfig:
            Object.assign({}, defaultFenxiaoConfig),
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
                getFenxiaoConfig(this.$route.query.id).then(response => {
                    this.fenxiaoConfig = response.data;
            })
                ;
            } else {
                this.fenxiaoConfig = Object.assign({},
            defaultFenxiaoConfig)
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
                            updateFenxiaoConfig(this.$route.query.id, this.fenxiaoConfig).then(response => {
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
                            createFenxiaoConfig(this.fenxiaoConfig).then(response => {
                                if(response.code == 200
                        )
                            {
                                this.$refs[formName].resetFields();
                                this.fenxiaoConfig = Object.assign({},
                            defaultFenxiaoConfig)
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
                this.fenxiaoConfig = Object.assign({},
            defaultFenxiaoConfig)
                ;
            }
        }
    }
</script>
<style>
</style>


