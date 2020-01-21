<template> 
    <el-card class="form-container" shadow="never">
        <el-form :model="subject" :rules="rules" ref="subjectFrom" label-width="150px">

            <el-form-item label="任务名称：" prop="jobName">
                <el-input v-model="subject.jobName"></el-input>
            </el-form-item>
            <el-form-item label="调用目标字符串：" prop="invokeTarget">
                <el-input v-model="subject.invokeTarget"></el-input>
            </el-form-item>
            <el-form-item label="cron表达式：" prop="cronExpression">
                <el-input v-model="subject.cronExpression"></el-input>
            </el-form-item>


            <el-form-item label="执行策略：" prop="misfirePolicy">
                <el-radio-group v-model="subject.misfirePolicy">
                    <el-radio :label="1">立即执行</el-radio>
                    <el-radio :label="2">执行一次</el-radio>
                    <el-radio :label="3">放弃执行</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="并发执行：">
                <el-radio-group v-model="subject.concurrent">
                    <el-radio :label="0">允许</el-radio>
                    <el-radio :label="1">禁止</el-radio>
                </el-radio-group>
            </el-form-item>

            <el-form-item label="状态：">
                <el-radio-group v-model="subject.status">
                    <el-radio :label="0">正常</el-radio>
                    <el-radio :label="1">停止</el-radio>
                </el-radio-group>
            </el-form-item>

            <el-form-item label="备注：" prop="remark">
                <el-input v-model="subject.remark"></el-input>
            </el-form-item>

            <el-form-item>
                <el-button type="primary" @click="onSubmit('subjectFrom')">提交</el-button>
                <el-button v-if="!isEdit" @click="resetForm('subjectFrom')">重置</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>
<script>
    import {createJob, getJob, updateJob} from '@/api/sys/job'
    import SingleUpload from '@/components/Upload/singleUpload'

    const defaultJob = {
        name: ''
    };
    export default {
        name: 'JobDetail',
        components: {SingleUpload},
        props: {
            isEdit: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
                subject:Object.assign({}, defaultJob),
                rules: {
                    name: [
                        {required: true, message: '请输入品牌名称', trigger: 'blur'},
                        {min: 2, max: 140, message: '长度在 2 到 140 个字符', trigger: 'blur'}
                    ],
                    logo:
                        [
                            {required: true, message: '请输入品牌logo', trigger: 'blur'}
                        ],
                    sort:
                        [
                            {type: 'number', message: '排序必须为数字'}
                        ],
                }
            }
        },
        created() {
            if (this.isEdit) {
                getJob(this.$route.query.id).then(response => {
                    this.subject = response.data;
                })
                ;
            } else {
                this.subject = Object.assign({},
                    defaultJob)
                ;
            }
        },
        methods: {
            onSubmit(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.$confirm('是否提交数据', '提示', {
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            type: 'warning'
                        }).then(() => {
                            if (this.isEdit
                            ) {
                                updateJob(this.$route.query.id, this.subject).then(response => {
                                    if (response.code == 200
                                    ) {
                                        this.$refs[formName].resetFields();
                                        this.$message({
                                            message: '修改成功',
                                            type: 'success',
                                            duration: 1000
                                        });
                                        this.$router.back();
                                    } else {
                                        this.$message({
                                            message: response.msg,
                                            type: 'error',
                                            duration: 1000
                                        });
                                    }

                                })
                                ;
                            } else {
                                createJob(this.subject).then(response => {
                                    if (response.code == 200
                                    ) {
                                        this.$refs[formName].resetFields();
                                        this.subject = Object.assign({},
                                            defaultJob)
                                        ;
                                        this.$message({
                                            message: '提交成功',
                                            type: 'success',
                                            duration: 1000
                                        });
                                        this.$router.back();
                                    } else {
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
                this.subject = Object.assign({},
                    defaultJob)
                ;
            }
        }
    }
</script>
<style>
</style>


