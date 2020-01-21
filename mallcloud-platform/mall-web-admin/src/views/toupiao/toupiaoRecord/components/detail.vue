<template> 
    <el-card class="form-container" shadow="never">
        <el-form :model="toupiaoRecord" :rules="rules" ref="ToupiaoRecordFrom" label-width="150px">




                    <el-form-item
                            label="手机"
                            prop="phone">
                        <el-input v-model="toupiaoRecord.phone" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="投票"
                            prop="toupiaoId">
                        <el-input v-model="toupiaoRecord.toupiaoId" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="创建时间"
                            prop="createTime">
                        <el-input v-model="toupiaoRecord.createTime" style="width: 370px;"/>
                    </el-form-item>





            <el-form-item>
                <el-button type="primary" @click="onSubmit('ToupiaoRecordFrom')">提交</el-button>
                <el-button v-if="!isEdit" @click="resetForm('ToupiaoRecordFrom')">重置</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>
<script>
    import {createToupiaoRecord, getToupiaoRecord, updateToupiaoRecord} from '@/api/toupiao/toupiaoRecord'
    import SingleUpload from '@/components/Upload/singleUpload'

    const defaultToupiaoRecord = {
        name: ''
    };
    export default {
        name: 'ToupiaoRecordDetail',
        components: {SingleUpload},
        props: {
            isEdit: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
            toupiaoRecord:
            Object.assign({},
        defaultToupiaoRecord),
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
                getToupiaoRecord(this.$route.query.id).then(response => {
                    this.toupiaoRecord = response.data;
            })
                ;
            } else {
                this.toupiaoRecord = Object.assign({},
            defaultToupiaoRecord)
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
                            updateToupiaoRecord(this.$route.query.id, this.toupiaoRecord).then(response => {
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
                            createToupiaoRecord(this.toupiaoRecord).then(response => {
                                if(response.code == 200
                        )
                            {
                                this.$refs[formName].resetFields();
                                this.toupiaoRecord = Object.assign({},
                            defaultToupiaoRecord)
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
                this.toupiaoRecord = Object.assign({},
            defaultToupiaoRecord)
                ;
            }
        }
    }
</script>
<style>
</style>


