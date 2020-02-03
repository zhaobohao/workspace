<template> 
    <el-card class="form-container" shadow="never">
        <el-form :model="fenxiaoConfig" ref="fenxiaoConfigForm" label-width="150px">
            <el-form-item label="状态：">
                <el-radio-group v-model="fenxiaoConfig.status">
                    <el-radio :label="1">开启</el-radio>
                    <el-radio :label="0">关闭</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="返佣类型：">
                <el-radio-group v-model="fenxiaoConfig.type">
                    <el-radio :label="1">现金返佣</el-radio>
                    <el-radio :label="0">积分返佣</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="商品返佣比例" prop="fanPercent">
                <el-input v-model="fenxiaoConfig.fanPercent" style="width: 370px;" />
                <span class="note-margin">%</span>
            </el-form-item>

            <el-form-item label="到账类型：">
                <el-radio-group v-model="fenxiaoConfig.fanType">
                    <el-radio :label="1">支付成功</el-radio>
                    <el-radio :label="0">交易成功</el-radio>
                </el-radio-group>
            </el-form-item>

            <el-form-item label="提现方式：">
                <el-radio-group v-model="fenxiaoConfig.withdrawType">
                    <el-radio :label="1">人工提现</el-radio>
                    <el-radio :label="0">微信打款</el-radio>
                </el-radio-group>
            </el-form-item>



            <el-form-item label="一级返佣" prop="onePercent">
                <el-input v-model="fenxiaoConfig.onePercent" style="width: 370px;" />
                <span class="note-margin">%</span>
            </el-form-item>


            <el-form-item label="二级返佣" prop="twoPercent">
                <el-input v-model="fenxiaoConfig.twoPercent" style="width: 370px;" />
                <span class="note-margin">%</span>
            </el-form-item>


            <el-form-item label="三级返佣" prop="threePercent">
                <el-input v-model="fenxiaoConfig.threePercent" style="width: 370px;" />
                <span class="note-margin">%</span>
            </el-form-item>

            <el-form-item>
                <el-button @click="confirm('fenxiaoConfigForm')" type="primary">提交</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>
<script>
    import {
        getFenxiaoConfig,
        updateFenxiaoConfig
    } from '@/api/fenxiao/fenxiaoConfig'
    const defaultOrderSetting = {
        status: 1,
        type: 1,
        fanPercent: 0,
        fanType: 1,
        withdrawType: 1,
        onePercent: 0,
        twoPercent: 0,
        threePercent: 0
    };

    export default {
        name: 'fenxiaoConfig',
        data() {
            return {
                fenxiaoConfig: Object.assign({}, defaultOrderSetting),
            }
        },
        created() {
            this.getDetail();
        },
        methods: {
            confirm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.$confirm('是否要提交修改?', '提示', {
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            type: 'warning'
                        }).then(() => {
                            updateFenxiaoConfig(1, this.fenxiaoConfig).then(response => {
                                this.$message({
                                    type: 'success',
                                    message: '提交成功!',
                                    duration: 1000
                                });
                            })
                        });
                    } else {
                        this.$message({
                            message: '提交参数不合法',
                            type: 'warning'
                        });
                        return false;
                    }
                });
            },
            getDetail() {
                getFenxiaoConfig(1).then(response => {
                    this.fenxiaoConfig = response.data;
                })
            }
        }
    }
</script>
<style scoped>
    .input-width {
        width: 50%;
    }

    .note-margin {
        margin-left: 15px;
    }
</style>