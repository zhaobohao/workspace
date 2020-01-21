<template> 
    <el-card class="form-container" shadow="never">
        <el-form :model="jifenDonateRule" :rules="rules" ref="JifenDonateRuleFrom" label-width="150px">



            <el-form-item label="赠送场景：">
                <el-select
                        v-model="jifenDonateRule.donateType"

                        placeholder="请选择赠送场景">
                    <el-option
                            v-for="item in unitOptions"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                    </el-option>
                </el-select>

            </el-form-item>

                    <el-form-item v-if="jifenDonateRule.donateType==2 || jifenDonateRule.donateType==3"
                            label="赠送条件"
                            prop="donateCondtion">
                        <el-input v-model="jifenDonateRule.donateCondtion" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="赠送积分"
                            prop="donateIntegration">
                        <el-input v-model="jifenDonateRule.donateIntegration" style="width: 370px;"/>
                    </el-form-item>

            <el-form-item>
                <el-button type="primary" @click="onSubmit('JifenDonateRuleFrom')">提交</el-button>
                <el-button v-if="!isEdit" @click="resetForm('JifenDonateRuleFrom')">重置</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>
<script>
    import {createJifenDonateRule, getJifenDonateRule, updateJifenDonateRule} from '@/api/jifen/jifenDonateRule'
    import SingleUpload from '@/components/Upload/singleUpload'

    const defaultJifenDonateRule = {
        name: ''
    };
    export default {
        name: 'JifenDonateRuleDetail',
        components: {SingleUpload},
        props: {
            isEdit: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
            jifenDonateRule:
            Object.assign({},
        defaultJifenDonateRule),
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
            },
                unitOptions:[{
                    id: "1",
                    name: "注册送",
                },
                    {
                        id: "2",
                        name: "消费送",
                    },
                    {
                        id: "3",
                        name: "充值送",
                    },

                    {
                        id: "4",
                        name: "好评送",
                    },{
                        id: "5",
                        name: "邀请用户",
                    },{
                        id: "6",
                        name: "转发微信",
                    }],
        }
        },
        created() {
            if (this.isEdit) {
                getJifenDonateRule(this.$route.query.id).then(response => {
                    this.jifenDonateRule = response.data;
            })
                ;
            } else {
                this.jifenDonateRule = Object.assign({},
            defaultJifenDonateRule)
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
                            updateJifenDonateRule(this.$route.query.id, this.jifenDonateRule).then(response => {
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
                            createJifenDonateRule(this.jifenDonateRule).then(response => {
                                if(response.code == 200
                        )
                            {
                                this.$refs[formName].resetFields();
                                this.jifenDonateRule = Object.assign({},
                            defaultJifenDonateRule)
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
                this.jifenDonateRule = Object.assign({},
            defaultJifenDonateRule)
                ;
            }
        }
    }
</script>
<style>
</style>


