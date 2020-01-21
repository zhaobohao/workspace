<template>
	<view class="pay-type-list">

		<view class="type-item b-b" v-for="item in paymentss" :key="item.code" @click="toPayHandler(item.code)"
			  v-if="!(type == 2 && item.code == 'balancepay')">
			<text v-if=" item.code == 'wechatpay'" class="icon yticon icon-weixinzhifu"></text>
			<text v-if=" item.code == 'alipay'" class="icon yticon icon-alipay"></text>
			<text v-if=" item.code == 'offline'" class="icon yticon icon-weixinzhifu"></text>
			<text v-if=" item.code == 'balancepay'" class="icon yticon icon-erjiye-yucunkuan"></text>

			<view class="con">
				<text class="tit">{{ item.name }}</text>
				<text>{{ item.memo }}</text>
			</view>
		</view>
	</view>


</template>

<script>

import Api from '@/common/api';
export default {
	props: {
		// 如果是商品订单此参数必须
		orderId: {
			type: String,
			default () {
				return ''
			}
		},
		// 如果是充值订单此参数必须
		recharge: {
			type: Number,
			default () {
				return 0
			}
		},
		// 用户id
		uid: {
			type: Number,
			default () {
				return 0
			}
		},
		// 订单类型
		type: {
			type: Number,
			default () {
				return 1
			}
		}
	},
	data () {
		return {
			paymentss: []
		}
	},
	mounted () {
		this.getPayment()
	},
	methods: {
		// 获取可用支付方式列表
		async getPayment () {
		console.log('getPayments')
				let params = {};
				this.orderInfo = await Api.apiCall('get',Api.order.paymentlist,params);
			    this.paymentss = this.formatPayments(this.orderInfo.data)

		},
		// 支付方式处理
		formatPayments (payments) {
			// 如果是充值订单 过滤余额支付 过滤非线上支付方式
			if (this.type === 2) {
				payments = payments.filter(item => item.code !== 'balancepay' || item.is_online === 1)
			}

			// 设置logo图片
			payments.forEach(item => {
				this.$set(item, 'icon', '/static/image/' + item.code + '.png')
			})
			return payments
		},
		// 用户点击支付方式处理
		async toPayHandler (code) {
			let _this = this
			let params = {'orderId':this.orderId};
			let data = {
				payment_code: code,
				payment_type: _this.type
			}

			data['ids'] = (this.type == 1 || this.type == 5 || this.type == 6) ? this.orderId : this.uid
			 if ((this.type == 5 || this.type == 6) && this.recharge) {
				data['params'] = {
					trade_type: 'APP',
					formid: this.orderId
				}
			}
			switch (code) {
				case 'alipay':
				/**
				 * 支付宝支付需要模拟GET提交数据
				 */
				if (_this.type == 1 && _this.orderId) {
					data['params'] = {
						trade_type: 'APP'
					}
				} else if (_this.type == 2 && _this.recharge) {
					data['params'] = {
						trade_type: 'APP',
						money: _this.recharge
					}
				}

					let res = await Api.apiCall('get',Api.order.aliAppPay,params);
					console.log(res);
					if (res) {

						uni.requestPayment({
							provider: "alipay",
							orderInfo: res,
							success: function(data){
								console.log(data);
								_this.$common.successToShow('支付成功', () => {
									_this.redirectHandler(res.data.payment_id)
								})
							}
						});
					} else {
						_this.$comon.errorToShow(res.msg)
					}

				break
				case 'wechatpay':
					// 微信 H5支付
					if (_this.type == 1 && _this.orderId) {
						data['params'] = {
							trade_type: 'APP'
						}
					} else if (_this.type == 2 && _this.recharge) {
						data['params'] = {
							trade_type: 'APP',
							money: _this.recharge
						}
					}

					// 微信app支付
					let res1 = await Api.apiCall('get',Api.order.appPay,params);

					if (res1) {
							// 调用微信支付
							uni.requestPayment({
								provider: "wxpay",
								orderInfo: res1,
								success: function(data){
									_this.$common.successToShow('支付成功', () => {
										_this.redirectHandler(data)
									})
								},
								fail:function(res){
									console.log(JSON.stringify(res));
								}
							});
						} else {
							_this.$common.errorToShow(res.msg)
						}

					break
				case 'balancepay':
					/**
					 *  用户余额支付
					 *
					 */
					let params1 = {'orderId':this.orderId};
					let data1 = await Api.apiCall('post',Api.order.balancePay,params1);
					console.log(data1)
					if (data1) {
						uni.redirectTo({
							url: '/pages/order/payment/result?order=' + JSON.stringify(data1)
						})
					}else {
						this.$api.msg('余额支付失败');
					}
					break
				case 'offline':
					/**
					 * 线下支付
					 */
					_this.$common.modelShow('线下支付说明', '请联系客服进行线下支付',() => {}, false, '取消', '确定')
					break
				}
		},
		// 支付成功后跳转操作
		redirectHandler (paymentId) {
			this.$common.redirectTo('/pages/order/payment/result?id=' + paymentId)
		}
	}

}
</script>
<style lang='scss'>
	.app {
		width: 100%;
	}

	.price-box {
		background-color: #fff;
		height: 265upx;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		font-size: 28upx;
		color: #909399;

		.price{
			font-size: 50upx;
			color: #303133;
			margin-top: 12upx;
			&:before{
				content: '￥';
				font-size: 40upx;
			}
		}
	}

	.pay-type-list {
		margin-top: 20upx;
		background-color: #fff;
		padding-left: 60upx;

		.type-item{
			height: 120upx;
			padding: 20upx 0;
			display: flex;
			justify-content: space-between;
			align-items: center;
			padding-right: 60upx;
			font-size: 30upx;
			position:relative;
		}

		.icon{
			width: 100upx;
			font-size: 52upx;
		}
		.icon-erjiye-yucunkuan {
			color: #fe8e2e;
		}
		.icon-weixinzhifu {
			color: #36cb59;
		}
		.icon-alipay {
			color: #01aaef;
		}
		.tit{
			font-size: $font-lg;
			color: $font-color-dark;
			margin-bottom: 4upx;
		}
		.con{
			flex: 1;
			display: flex;
			flex-direction: column;
			font-size: $font-sm;
			color: $font-color-light;
		}
	}
	.mix-btn {
		display: flex;
		align-items: center;
		justify-content: center;
		width: 630upx;
		height: 80upx;
		margin: 80upx auto 30upx;
		font-size: $font-lg;
		color: #fff;
		background-color: $base-color;
		border-radius: 10upx;
		box-shadow: 1px 2px 5px rgba(219, 63, 96, 0.4);
	}

</style>

