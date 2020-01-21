<template> 
    <el-card class="form-container" shadow="never">
        <el-form :model="smsBargainConfig" :rules="rules" ref="SmsBargainConfigFrom" label-width="150px">


                    <el-form-item
                            label="id"
                            prop="id">
                        <el-input v-model="smsBargainConfig.id" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="每次砍价的参数"
                            prop="parameter">
                        <el-input v-model="smsBargainConfig.parameter" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="逾期失效时间"
                            prop="invalidTime">
                        <el-input v-model="smsBargainConfig.invalidTime" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="修改时间"
                            prop="createTime">
                        <el-input v-model="smsBargainConfig.createTime" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="storeId"
                            prop="storeId">
                        <el-input v-model="smsBargainConfig.storeId" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="商品"
                            prop="goodsId">
                        <el-input v-model="smsBargainConfig.goodsId" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="原价"
                            prop="originPrice">
                        <el-input v-model="smsBargainConfig.originPrice" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="成功价"
                            prop="price">
                        <el-input v-model="smsBargainConfig.price" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="goodsName"
                            prop="goodsName">
                        <el-input v-model="smsBargainConfig.goodsName" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="pepoles"
                            prop="pepoles">
                        <el-input v-model="smsBargainConfig.pepoles" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="pic"
                            prop="pic">
                        <el-input v-model="smsBargainConfig.pic" style="width: 370px;"/>
                    </el-form-item>


            <el-form-item>
                <el-button type="primary" @click="onSubmit('SmsBargainConfigFrom')">提交</el-button>
                <el-button v-if="!isEdit" @click="resetForm('SmsBargainConfigFrom')">重置</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>
<script>
    import {createSmsBargainConfig, getSmsBargainConfig, updateSmsBargainConfig} from '@/api/sms/smsBargainConfig'
    import SingleUpload from '@/components/Upload/singleUpload'

    const defaultSmsBargainConfig = {
        name: ''
    };
    export default {
        name: 'SmsBargainConfigDetail',
        components: {SingleUpload},
        props: {
            isEdit: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
            smsBargainConfig:
            Object.assign({},
        defaultSmsBargainConfig),
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
                getSmsBargainConfig(this.$route.query.id).then(response => {
                    this.smsBargainConfig = response.data;
            })
                ;
            } else {
                this.smsBargainConfig = Object.assign({},
            defaultSmsBargainConfig)
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
                            updateSmsBargainConfig(this.$route.query.id, this.smsBargainConfig).then(response => {
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
                            createSmsBargainConfig(this.smsBargainConfig).then(response => {
                                if(response.code == 200
                        )
                            {
                                this.$refs[formName].resetFields();
                                this.smsBargainConfig = Object.assign({},
                            defaultSmsBargainConfig)
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
                this.smsBargainConfig = Object.assign({},
            defaultSmsBargainConfig)
                ;
            }
        }
    }
</script>
<style>
</style>


