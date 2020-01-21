<template> 
    <el-card class="form-container" shadow="never">
        <el-form :model="dict" :rules="rules" ref="dictFrom" label-width="150px">

            <el-form-item label="类型：" prop="type">
                <el-input v-model="dict.type"></el-input>
            </el-form-item>
                            <el-form-item label="标签：" prop="key">
                    <el-input v-model="dict.keyname"></el-input>
                </el-form-item>
                            <el-form-item label="值：" prop="value">
                    <el-input v-model="dict.valuename"></el-input>
                </el-form-item>

                            <el-form-item label="备注：" prop="remark">
                    <el-input v-model="dict.remark"></el-input>
                </el-form-item>

                        <el-form-item>
                <el-button type="primary" @click="onSubmit('dictFrom')">提交</el-button>
                <el-button v-if="!isEdit" @click="resetForm('dictFrom')">重置</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>
<script>
    import {createDict, getDict, updateDict} from '@/api/sys/dict'
    import SingleUpload from '@/components/Upload/singleUpload'
    import {formatDate} from '@/utils/date';

    const defaultDict= {
        name: ''
      };
    export default {
        name: 'DictDetail',
        components: {SingleUpload},
        props: {
            isEdit: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
            dict:
            Object.assign({},
        defaultDict),
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
                getDict(this.$route.query.id).then(response => {
                    this.dict = response.data;
            })
                ;
            } else {
                this.dict = Object.assign({},
            defaultDict)
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
                            updateDict(this.$route.query.id, this.dict).then(response => {
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
                            createDict(this.dict).then(response => {
                                if(response.code == 200
                        )
                            {
                                this.$refs[formName].resetFields();
                                this.dict = Object.assign({},
                            defaultDict)
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
                this.dict = Object.assign({},
            defaultDict)
                ;
            }
        }
    }
</script>
<style>
</style>


