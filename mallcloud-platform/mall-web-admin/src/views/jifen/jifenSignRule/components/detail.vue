<template> 
    <el-card class="form-container" shadow="never">
        <el-form :model="jifenSignRule" :rules="rules" ref="JifenSignRuleFrom" label-width="150px">



                    <el-form-item
                            label="连续签到"
                            prop="contineuCount">
                        <el-input v-model="jifenSignRule.contineuCount" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="赠送积分"
                            prop="donateIntegrtion">
                        <el-input v-model="jifenSignRule.donateIntegrtion" style="width: 370px;"/>
                    </el-form-item>



            <el-form-item>
                <el-button type="primary" @click="onSubmit('JifenSignRuleFrom')">提交</el-button>
                <el-button v-if="!isEdit" @click="resetForm('JifenSignRuleFrom')">重置</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>
<script>
    import {createJifenSignRule, getJifenSignRule, updateJifenSignRule} from '@/api/jifen/jifenSignRule'
    import SingleUpload from '@/components/Upload/singleUpload'

    const defaultJifenSignRule = {
        name: ''
    };
    export default {
        name: 'JifenSignRuleDetail',
        components: {SingleUpload},
        props: {
            isEdit: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
            jifenSignRule:
            Object.assign({},
        defaultJifenSignRule),
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
                getJifenSignRule(this.$route.query.id).then(response => {
                    this.jifenSignRule = response.data;
            })
                ;
            } else {
                this.jifenSignRule = Object.assign({},
            defaultJifenSignRule)
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
                            updateJifenSignRule(this.$route.query.id, this.jifenSignRule).then(response => {
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
                            createJifenSignRule(this.jifenSignRule).then(response => {
                                if(response.code == 200
                        )
                            {
                                this.$refs[formName].resetFields();
                                this.jifenSignRule = Object.assign({},
                            defaultJifenSignRule)
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
                this.jifenSignRule = Object.assign({},
            defaultJifenSignRule)
                ;
            }
        }
    }
</script>
<style>
</style>


