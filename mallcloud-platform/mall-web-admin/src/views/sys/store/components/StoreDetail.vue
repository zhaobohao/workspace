<template> 
    <el-card class="form-container" shadow="never">
        <el-form :model="store" :rules="rules" ref="storeFrom" label-width="150px">
                            <el-form-item label="短信数量：" prop="smsQuantity">
                    <el-input v-model="store.smsQuantity"></el-input>
                </el-form-item>
                            <el-form-item label="注册类型：" prop="registerType">
                    <el-input v-model="store.registerType"></el-input>
                </el-form-item>
                            <el-form-item label="到期时间：" prop="expireTime">
                    <el-input v-model="store.expireTime"></el-input>
                </el-form-item>
                            <el-form-item label="尝试时间：" prop="tryTime">
                    <el-input v-model="store.tryTime"></el-input>
                </el-form-item>
                            <el-form-item label="联系电话：" prop="contactMobile">
                    <el-input v-model="store.contactMobile"></el-input>
                </el-form-item>
                            <el-form-item label="地区省：" prop="addressProvince">
                    <el-input v-model="store.addressProvince"></el-input>
                </el-form-item>
                            <el-form-item label="所购物品计划时间：" prop="buyPlanTimes">
                    <el-input v-model="store.buyPlanTimes"></el-input>
                </el-form-item>
                            <el-form-item label="创建时间：" prop="createTime">
                    <el-input v-model="store.createTime"></el-input>
                </el-form-item>
                            <el-form-item label="是否选中：" prop="isChecked">
                    <el-input v-model="store.isChecked"></el-input>
                </el-form-item>
                            <el-form-item label="是否删除：" prop="isDeleted">
                    <el-input v-model="store.isDeleted"></el-input>
                </el-form-item>
                            <el-form-item label="服务电话：" prop="servicePhone">
                    <el-input v-model="store.servicePhone"></el-input>
                </el-form-item>
                            <el-form-item label="地址名：" prop="addressLat">
                    <el-input v-model="store.addressLat"></el-input>
                </el-form-item>
                            <el-form-item label="联系人名：" prop="contactName">
                    <el-input v-model="store.contactName"></el-input>
                </el-form-item>
                            <el-form-item label="删除时间：" prop="deleteTime">
                    <el-input v-model="store.deleteTime"></el-input>
                </el-form-item>
                            <el-form-item label="自己配置文件：" prop="diyProfile">
                    <el-input v-model="store.diyProfile"></el-input>
                </el-form-item>
                            <el-form-item label="行业：" prop="industryTwo">
                    <el-input v-model="store.industryTwo"></el-input>
                </el-form-item>
                            <el-form-item label="：" prop="isStar">
                    <el-input v-model="store.isStar"></el-input>
                </el-form-item>
                            <el-form-item label="尝试：" prop="isTry">
                    <el-input v-model="store.isTry"></el-input>
                </el-form-item>
                            <el-form-item label="图标：" prop="logo">
                    <el-input v-model="store.logo"></el-input>
                </el-form-item>
                            <el-form-item label="地址细节：" prop="addressDetail">
                    <el-input v-model="store.addressDetail"></el-input>
                </el-form-item>
                            <el-form-item label="计划id：" prop="planId">
                    <el-input v-model="store.planId"></el-input>
                </el-form-item>
                            <el-form-item label="支持，维持名称：" prop="supportName">
                    <el-input v-model="store.supportName"></el-input>
                </el-form-item>
                            <el-form-item label="：" prop="name">
                    <el-input v-model="store.name"></el-input>
                </el-form-item>
                            <el-form-item label="1为出售中，3为已售罄，-2为仓库中，-1为回收站：" prop="status">
                    <el-input v-model="store.status"></el-input>

                            <el-form-item label="类型：" prop="type">
                    <el-input v-model="store.type"></el-input>
                </el-form-item>
                            <el-form-item label="联系QQ：" prop="contactQq">
                    <el-input v-model="store.contactQq"></el-input>
                </el-form-item>
                            <el-form-item label="：" prop="addressLng">
                    <el-input v-model="store.addressLng"></el-input>
                </el-form-item>
                            <el-form-item label="：" prop="lastLoginTime">
                    <el-input v-model="store.lastLoginTime"></el-input>
                </el-form-item>
                            <el-form-item label="支持电话：" prop="supportPhone">
                    <el-input v-model="store.supportPhone"></el-input>
                </el-form-item>
                            <el-form-item label="地址区域：" prop="addressArea">
                    <el-input v-model="store.addressArea"></el-input>
                </el-form-item>
                            <el-form-item label="：" prop="contactQrcode">
                    <el-input v-model="store.contactQrcode"></el-input>
                </el-form-item>
                            <el-form-item label="描述：" prop="description">
                    <el-input v-model="store.description"></el-input>
                </el-form-item>
                            <el-form-item label="：" prop="id">
                    <el-input v-model="store.id"></el-input>
                </el-form-item>
                            <el-form-item label="行业1：" prop="industryOne">
                    <el-input v-model="store.industryOne"></el-input>
                </el-form-item>
                            <el-form-item label="地址城市：" prop="addressCity">
                    <el-input v-model="store.addressCity"></el-input>
                </el-form-item>
                        <el-form-item>
                <el-button type="primary" @click="onSubmit('storeFrom')">提交</el-button>
                <el-button v-if="!isEdit" @click="resetForm('storeFrom')">重置</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>
<script>
    import {createStore, getStore, updateStore} from '@/api/sys/store'
    import SingleUpload from '@/components/Upload/singleUpload'
    import {formatDate} from '@/utils/date';

    const default
        Store= {
        name: ''
    };
    export default {
        name: 'StoreDetail',
        components: {SingleUpload},
        props: {
            isEdit: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
            store:
            Object.assign({},
        defaultStore),
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
                getStore(this.$route.query.id).then(response => {
                    this.store = response.data;
            })
                ;
            } else {
                this.store = Object.assign({},
            defaultStore)
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
                            updateStore(this.$route.query.id, this.store).then(response => {
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
                            createStore(this.store).then(response => {
                                if(response.code == 200
                        )
                            {
                                this.$refs[formName].resetFields();
                                this.store = Object.assign({},
                            defaultStore)
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
                this.store = Object.assign({},
            defaultStore)
                ;
            }
        }
    }
</script>
<style>
</style>


