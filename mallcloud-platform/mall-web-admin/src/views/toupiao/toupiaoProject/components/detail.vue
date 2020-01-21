<template> 
    <el-card class="form-container" shadow="never">
        <el-form :model="toupiaoProject" :rules="rules" ref="ToupiaoProjectFrom" label-width="150px">



                    <el-form-item
                            label="投票类型"
                            prop="type">
                        <el-input v-model="toupiaoProject.type" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="状态"
                            prop="status">
                        <el-input v-model="toupiaoProject.status" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="标题"
                            prop="name">
                        <el-input v-model="toupiaoProject.name" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="备注"
                            prop="remark">
                        <el-input v-model="toupiaoProject.remark" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="开始时间"
                            prop="startTime">
                        <el-input v-model="toupiaoProject.startTime" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="过期时间"
                            prop="endTime">
                        <el-input v-model="toupiaoProject.endTime" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="创建时间"
                            prop="createTime">
                        <el-input v-model="toupiaoProject.createTime" style="width: 370px;"/>
                    </el-form-item>




            <el-form-item>
                <el-button type="primary" @click="onSubmit('ToupiaoProjectFrom')">提交</el-button>
                <el-button v-if="!isEdit" @click="resetForm('ToupiaoProjectFrom')">重置</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>
<script>
    import {createToupiaoProject, getToupiaoProject, updateToupiaoProject} from '@/api/toupiao/toupiaoProject'
    import SingleUpload from '@/components/Upload/singleUpload'

    const defaultToupiaoProject = {
        name: ''
    };
    export default {
        name: 'ToupiaoProjectDetail',
        components: {SingleUpload},
        props: {
            isEdit: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
            toupiaoProject:
            Object.assign({},
        defaultToupiaoProject),
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
                getToupiaoProject(this.$route.query.id).then(response => {
                    this.toupiaoProject = response.data;
            })
                ;
            } else {
                this.toupiaoProject = Object.assign({},
            defaultToupiaoProject)
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
                            updateToupiaoProject(this.$route.query.id, this.toupiaoProject).then(response => {
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
                            createToupiaoProject(this.toupiaoProject).then(response => {
                                if(response.code == 200
                        )
                            {
                                this.$refs[formName].resetFields();
                                this.toupiaoProject = Object.assign({},
                            defaultToupiaoProject)
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
                this.toupiaoProject = Object.assign({},
            defaultToupiaoProject)
                ;
            }
        }
    }
</script>
<style>
</style>


