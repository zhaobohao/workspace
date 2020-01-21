<template> 
    <el-card class="form-container" shadow="never">
        <el-form :model="smsContentMsg" :rules="rules" ref="SmsContentMsgFrom" label-width="150px">


                    <el-form-item
                            label="id"
                            prop="id">
                        <el-input v-model="smsContentMsg.id" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="phone"
                            prop="phone">
                        <el-input v-model="smsContentMsg.phone" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="status"
                            prop="status">
                        <el-input v-model="smsContentMsg.status" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="content"
                            prop="content">
                        <el-input v-model="smsContentMsg.content" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="result"
                            prop="result">
                        <el-input v-model="smsContentMsg.result" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="createTime"
                            prop="createTime">
                        <el-input v-model="smsContentMsg.createTime" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="contentId"
                            prop="contentId">
                        <el-input v-model="smsContentMsg.contentId" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="type"
                            prop="type">
                        <el-input v-model="smsContentMsg.type" style="width: 370px;"/>
                    </el-form-item>


            <el-form-item>
                <el-button type="primary" @click="onSubmit('SmsContentMsgFrom')">提交</el-button>
                <el-button v-if="!isEdit" @click="resetForm('SmsContentMsgFrom')">重置</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>
<script>
    import {createSmsContentMsg, getSmsContentMsg, updateSmsContentMsg} from '@/api/sms/smsContentMsg'
    import SingleUpload from '@/components/Upload/singleUpload'

    const defaultSmsContentMsg = {
        name: ''
    };
    export default {
        name: 'SmsContentMsgDetail',
        components: {SingleUpload},
        props: {
            isEdit: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
            smsContentMsg:
            Object.assign({},
        defaultSmsContentMsg),
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
                getSmsContentMsg(this.$route.query.id).then(response => {
                    this.smsContentMsg = response.data;
            })
                ;
            } else {
                this.smsContentMsg = Object.assign({},
            defaultSmsContentMsg)
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
                            updateSmsContentMsg(this.$route.query.id, this.smsContentMsg).then(response => {
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
                            createSmsContentMsg(this.smsContentMsg).then(response => {
                                if(response.code == 200
                        )
                            {
                                this.$refs[formName].resetFields();
                                this.smsContentMsg = Object.assign({},
                            defaultSmsContentMsg)
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
                this.smsContentMsg = Object.assign({},
            defaultSmsContentMsg)
                ;
            }
        }
    }
</script>
<style>
</style>


