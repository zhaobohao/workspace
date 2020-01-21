<template>
    <div class="app-container">
        <div class="total-layout">
            <el-row :gutter="20">
                <el-col :span="6" v-for="(item, i) in storeList" :key="i">
                    <div class="total-frame" @click="handleStore(item.id)">
                        <svg-icon icon-class="order" class="total-icon">
                        </svg-icon>
                        <div class="total-title"><a @click="handleStore(item.id)">{{item.name}}</a></div>
                        <div class="total-value">{{item.contactQq}}</div>
                    </div>
                </el-col>
               </el-row>
        </div>
    </div>
</template>

<script>
    import {str2Date} from '@/utils/date';
    import {fetchList,setStoreId} from '@/api/sys/store'

    export default {
        name: 'home',
        data() {

            return {
                storeList: null,
                pickerOptions: {
                    shortcuts: [{
                        text: '最近一周',
                        onClick(picker) {
                            const end = new Date();
                            let start = new Date();
                            start.setFullYear(2018);
                            start.setMonth(10);
                            start.setDate(1);
                            end.setTime(start.getTime() + 3600 * 1000 * 24 * 7);
                            picker.$emit('pick', [start, end]);
                        }
                    }, {
                        text: '最近一月',
                        onClick(picker) {
                            const end = new Date();
                            let start = new Date();
                            start.setFullYear(2018);
                            start.setMonth(10);
                            start.setDate(1);
                            end.setTime(start.getTime() + 3600 * 1000 * 24 * 30);
                            picker.$emit('pick', [start, end]);
                        }
                    }]
                },
                orderData: '',
                goods: '',
                user: '',
                orderStatusCount: '',
                orderCountDate: '',
                chartSettings: {
                    xAxisType: 'time',
                    area: true,
                    axisSite: {right: ['orderAmount']},
                    labelMap: {'orderCount': '订单数量', 'orderAmount': '订单金额'}
                },
                chartData: {
                    columns: [],
                    rows: []
                },
                loading: false,
                dataEmpty: false
            }
        },
        created() {
            this.initOrderCountDate();
        },
        methods: {
            handleStore(id) {
                setStoreId(id).then(res => {
                });
                this.$router.push({path: '/'})
            },
            initOrderCountDate() {
                fetchList().then(res => {
                    if (res.code == 200) {
                        this.storeList = res.data.records;
                    }
                });
                }
        }
    }
</script>

<style scoped>
    .app-container {
        margin-top: 40px;
        margin-left: 120px;
        margin-right: 120px;
    }

    .total-layout {
    }

    .total-frame {
        border: 1px solid #DCDFE6;
        padding: 20px;
        height: 100px;
    }

    .total-icon {
        color: #409EFF;
        width: 60px;
        height: 60px;
    }

    .total-title {
        position: relative;
        font-size: 16px;
        color: #909399;
        left: 70px;
        top: -50px;
    }

    .total-value {
        position: relative;
        font-size: 18px;
        color: #606266;
        left: 70px;
        top: -40px;
    }

    .un-handle-layout {
        margin-top: 20px;
        border: 1px solid #DCDFE6;
    }

    .layout-title {
        color: #606266;
        padding: 15px 20px;
        background: #F2F6FC;
        font-weight: bold;
    }

    .un-handle-content {
        padding: 20px 40px;
    }

    .un-handle-item {
        border-bottom: 1px solid #EBEEF5;
        padding: 10px;
    }

    .overview-layout {
        margin-top: 20px;
    }

    .overview-item-value {
        font-size: 24px;
        text-align: center;
    }

    .overview-item-title {
        margin-top: 10px;
        text-align: center;
    }

    .out-border {
        border: 1px solid #DCDFE6;
    }

    .statistics-layout {
        margin-top: 20px;
        border: 1px solid #DCDFE6;
    }
</style>
