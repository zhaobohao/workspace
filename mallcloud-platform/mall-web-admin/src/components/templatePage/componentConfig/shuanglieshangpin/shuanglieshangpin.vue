<style lang="less" scoped>
  @import './shuanglieshangpin.less';
</style>
<template :options="options">
    <div class="d-config">
        <el-tabs value="content" class="d-tab">
            <el-tab-pane label="内容" name="content">
                <div class="pannelcontent">
                    <div class="pannelItem">
                        <div class="tit">选择商品</div>
                        <el-radio-group v-model="choose">
                            <el-radio label="noauto">
                                <span>手动选择</span>
                            </el-radio>
                        </el-radio-group>
                        <div class="colorGroup">
                            <div class="colorItem" v-for="(item,index) in colorGroup" :key="index">
                                <i type="ios-trash img-trash" size="24" @click="deleteColor(item,index)"></i>
                               <!-- <Icon type="ios-trash img-trash" size="24" @click="deleteColor(item,index)" />-->
                                <img v-if="item.pic" class="colorIcon" :src="item.pic" alt="">
                                <img v-else class="colorIcon" src="../../static/img/default_onegoods.jpg" alt="">
                                <div class="colorInput">
                                    <div class="color_name">{{item.title}}</div>
                                    <div class="color_price" style="margin:10px 0px">￥{{item.price}}</div>
                                    <div class="color_stock">{{item.stock}}</div>
                                </div>
                            </div>
                        </div>

                        <div class="colorAdd" @click="addGoods" v-if="editable == 'enable'">
                            +<span class="colortips">添加商品</span>
                        </div>
                    </div>
                    <div class="pannelItem">
                        <div class="tit">显示内容</div>
                        <el-checkbox-group v-model="goods" @on-change="changeContent">
                            <el-checkbox label="goods_title">
                                <span>商品名称</span>
                            </el-checkbox>
                            <el-checkbox label="goods_price">
                                <span>商品价格</span>
                            </el-checkbox>
                            <el-checkbox label="origin_price">
                                <span>划线原价</span>
                            </el-checkbox>
                            <el-checkbox label="goods_sale">
                                <span>商品销量</span>
                            </el-checkbox>
                            <el-checkbox label="goods_subtitle">
                                <span>副标题</span>
                            </el-checkbox>
                        </el-checkbox-group>
                    </div>
                    <div class="pannelItem">
                        <div class="tit">购物车按钮</div>
                        <el-radio-group v-model="cart" @on-change="changeCart">
                            <el-radio label="cart_show">
                                <span>显示</span>
                            </el-radio>
                            <el-radio label="cart_hide">
                                <span>隐藏</span>
                            </el-radio>
                        </el-radio-group>
                    </div>
                    <div class="pannelItem">
                        <div class="tit">角标设置</div>
                        <el-radio-group v-model="corset" @on-change="changeCore">
                            <el-radio label="cor_hide">
                                <span>不显示</span>
                            </el-radio>
                            <el-radio label="cor_system">
                                <span>系统图标</span>
                            </el-radio>
                        </el-radio-group>
                    </div>
                </div>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
export default {
    props:['options','editable'],
    data () {
        return {
            choose:'noauto',
            goods:['goods_title','goods_price','goods_subtitle'],
            cart:'cart_show',
            corset:'cor_hide',
            newOptions:{},
            colorGroup:[]
        }
    },
    created(){
        let _this = this
        _this.init(_this.options)
    },
    watch:{
        options(){
            let _this = this
            _this.newOptions = _this.options
            console.log('更改数据',_this.newOptions)
            _this.init(_this.newOptions)

        }
    },
    methods:{
        // 初始化
        init(op){
            let _this = this
            let optionsParams = op.options
            if(JSON.stringify(optionsParams) == "{}"){
                _this.restore()
            }else{
                _this.choose = optionsParams.choose
                _this.goods = optionsParams.goods
                _this.cart = optionsParams.cart
                _this.corset = optionsParams.corset
                _this.colorGroup = optionsParams.colorGroup
            }
        },

        // 添加商品
        addGoods(){
            let _this = this
            let changeData = {
                openGood:true,
                mode:'diy',
            }
            _this.$emit('listenToOpenGoodSelect',changeData)
        },
        //删除商品
        deleteColor(item,index){
            let _this = this
            _this.colorGroup.splice(index,1)
            _this.$emit('deleteGoodSelect',item,index)
            _this.changeForm()
        },
        // 恢复初始状态
        restore(){
            let _this = this
            _this.choose = 'noauto'
            _this.goods = ['goods_title','goods_price','goods_subtitle']
            _this.cart = 'cart_show'
            _this.corset = 'cor_hide'
            _this.colorGroup = []
            _this.changeForm()
        },
        // 改变购物车按钮
        changeCart(value){
            let _this = this
            _this.cart = value
            _this.changeForm()
        },
        // 显示内容
        changeContent(value){
            let _this = this
            _this.goods = value
            _this.changeForm()
        },
        // 角标显示
        changeCore(value){
            let _this = this
            _this.corset = value
            _this.changeForm()
        },
        //改变值并且给父组件传递事件
        changeForm(index){
            let _this = this
            let changeData = {
                choose:_this.choose,
                goods:_this.goods,
                cart:_this.cart,
                corset:_this.corset,
                colorGroup:_this.colorGroup,
            }

            _this.$emit('listenToForm',changeData)
        }
    }
}
</script>
