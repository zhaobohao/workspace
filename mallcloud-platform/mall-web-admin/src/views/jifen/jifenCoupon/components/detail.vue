<template> 
    <el-card class="form-container" shadow="never">
        <el-form :model="jifenCoupon" :rules="rules" ref="JifenCouponFrom" label-width="150px">


            <el-form-item
                    label="创建数量"
                    prop="count">
                <el-input v-model="jifenCoupon.count" style="width: 370px;"/>
            </el-form-item>

                    <el-form-item
                            label="有效期(天)"
                            prop="validDay">
                        <el-input v-model="jifenCoupon.validDay" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="最小积分"
                            prop="minIntegration">
                        <el-input v-model="jifenCoupon.minIntegration" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="最大积分"
                            prop="maxIntegration">
                        <el-input v-model="jifenCoupon.maxIntegration" style="width: 370px;"/>
                    </el-form-item>


                    <el-form-item
                            label="备注"
                            prop="remark">
                        <el-input v-model="jifenCoupon.remark" style="width: 370px;"/>
                    </el-form-item>





            <el-form-item>
                <el-button type="primary" @click="onSubmit('JifenCouponFrom')">提交</el-button>
                <el-button v-if="!isEdit" @click="resetForm('JifenCouponFrom')">重置</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>
<script>
    import {createJifenCoupon, getJifenCoupon, updateJifenCoupon} from '@/api/jifen/jifenCoupon'
    import SingleUpload from '@/components/Upload/singleUpload'

    const defaultJifenCoupon = {
        name: ''
    };
    export default {
        name: 'JifenCouponDetail',
        components: {SingleUpload},
        props: {
            isEdit: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
            jifenCoupon:
            Object.assign({},
        defaultJifenCoupon),
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
                getJifenCoupon(this.$route.query.id).then(response => {
                    this.jifenCoupon = response.data;
            })
                ;
            } else {
                this.jifenCoupon = Object.assign({},
            defaultJifenCoupon)
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
                            updateJifenCoupon(this.$route.query.id, this.jifenCoupon).then(response => {
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
                            createJifenCoupon(this.jifenCoupon).then(response => {
                                if(response.code == 200
                        )
                            {
                                this.$refs[formName].resetFields();
                                this.jifenCoupon = Object.assign({},
                            defaultJifenCoupon)
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
                this.jifenCoupon = Object.assign({},
            defaultJifenCoupon)
                ;
            }
        }
    }
</script>
<style>
</style>


