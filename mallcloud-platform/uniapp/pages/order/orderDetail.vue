<template>
	<view class="content">
		<swiper :current="tabCurrentIndex" class="swiper-box" duration="300" @change="changeTab">
			<swiper-item class="tab-content">
				<scroll-view class="list-scroll-content" scroll-y>
					<!-- 订单列表 -->
					<view class="order-item">
						<view class="i-top b-b">
							<text class="time">{{ orderInfo.createTime }}</text>
							<text class="state" :style="{ color: orderInfo.stateTipColor }" @click="navToDetailPage(item)">{{ orderInfo.id }}--</text>
							<text class="state" :style="{ color: orderInfo.stateTipColor }">{{ orderInfo.stateTip }}</text>
							<text v-if="orderInfo.status === 9" class="del-btn yticon icon-iconfontshanchu1" @click="deleteOrder(index)"></text>
						</view>
						<view class="i-top b-b">
							<text class="state" :style="{ color: orderInfo.stateTipColor }">订单商品</text>
						</view>

						<view v-if="goodsItem.type === 1" class="goods-box-single" v-for="(goodsItem, goodsIndex) in orderInfo.orderItemList" :key="goodsIndex">
							<img class="goods-img" :src="goodsItem.productPic" mode="aspectFill"></img>
							<view class="right">
								<text class="title clamp">{{ goodsItem.productName }}</text>
								<text class="attr-box">{{ goodsItem.productAttr }} x {{ goodsItem.productQuantity }}</text>
								<text class="price">{{ goodsItem.productPrice }}</text>
							</view>
						</view>
						<view class="i-top b-b">
							<text class="state" :style="{ color: orderInfo.stateTipColor }">订单赠品</text>
						</view>
						<view v-if="goodsItem.type === 2" class="goods-box-single" v-for="(goodsItem, goodsIndex) in orderInfo.orderItemList" :key="goodsIndex">
							<img class="goods-img" :src="goodsItem.productPic" mode="aspectFill"></img>
							<view class="right">
								<text class="title clamp">{{ goodsItem.productName }}</text>
								<text class="attr-box">{{ goodsItem.productAttr }} x {{ goodsItem.productQuantity }}</text>
								<text class="price">{{ goodsItem.productPrice }}</text>
							</view>
						</view>
						<view class="price-box">
							共
							<text class="num">{{ orderInfo.orderItemList.length }}</text>
							件商品, 优惠金额
							<text class="price">{{ orderInfo.promotionAmount }}</text>
							 优惠券抵扣
                            							<text class="price">{{ orderInfo.couponAmount }}</text>
						</view>
						<view class="price-box">
                            							 积分抵扣
                                                        							<text class="price">{{ orderInfo.integrationAmount }}</text>
							 ,运费
							<text class="price">{{ orderInfo.freightAmount }}</text>
							<view class="yt-list-cell b-b" v-if="groupActivity">
								<text class="cell-tit clamp">活动金额</text>
								<text class="cell-tip">￥{{ groupActivity.price }}</text>
							</view>
							 ,实付款
							<text class="price">{{ orderInfo.payAmount }}</text>
						</view>
						<view class="action-box b-t">
							<button v-if="orderInfo.status == 12" class="action-btn" @click="cancelOrder(orderInfo)">取消订单</button>
							<button v-if="orderInfo.status == 12" class="action-btn recom" @click="payOrder(orderInfo)">立即支付</button>
							<button v-if="orderInfo.status <7" class="action-btn recom" @click="applyRefund(item)">申请退款</button>
							<button v-if="orderInfo.status == 3" class="action-btn recom" @click="confimDelivery(orderInfo)">确认收货</button>
							<button class='action-btn recom'
									hover-class="btn-hover"
									v-if="orderInfo.status === 4"
									@click="toEvaluate(orderInfo.id)"
							>立即评价</button>
						</view>
					</view>
				</scroll-view>
			</swiper-item>
		</swiper>
	</view>
</template>

<script>
import { mapState } from 'vuex';
import Api from '@/common/api';
import uniLoadMore from '@/components/uni-load-more/uni-load-more.vue';
import empty from '@/components/empty';
import Json from '@/Json';
import { formatDate } from '@/common/date';
export default {
	components: {
		uniLoadMore,
		empty
	},
	data() {
		return {
			tabCurrentIndex: 0,
			orderInfo: [],
			navList: [
				{
					status: 0,
					text: '全部',
					loadingType: 'more',
					orderList: []
				},
				{
					status: 12,
					text: '待付款',
					loadingType: 'more',
					orderList: []
				},
				{
					status: 2,
					text: '待发货',
					loadingType: 'more',
					orderList: []
				},
				{
					status: 3,
					text: '已发货',
					loadingType: 'more',
					orderList: []
				},
				{
					status: 4,
					text: '已完成',
					loadingType: 'more',
					orderList: []
				}
			]
		};
	},

	async onLoad(options) {
		let params = { id: options.id };
		let data = await Api.apiCall('get', Api.order.orderDetail, params);
		this.orderInfo =data.data;
		this.orderInfo = Object.assign(this.orderInfo, this.orderStateExp(this.orderInfo.status));
		this.orderInfo.createTime = this.dateFormat(this.orderInfo.createTime);
	},

	methods: {
		//swiper 切换
		changeTab(e) {
			this.tabCurrentIndex = e.target.current;
			this.loadData('tabChange');
		},
		//顶部tab点击
		tabClick(index) {
			this.tabCurrentIndex = index;
		},
		//删除订单
		async deleteOrder(index) {
			uni.showLoading({
				title: '请稍后'
			});
			setTimeout(() => {
				this.navList[this.tabCurrentIndex].orderList.splice(index, 1);
				uni.hideLoading();
			}, 600);
		},
		async payOrder(item) {
			uni.showLoading({
				title: '请稍后'
			});

			let params = { orderId: item.id };
			let data = await Api.apiCall('post', Api.order.weixinAppletPay, params);

			if (data) {
				this.$api.msg(data);
			}
			uni.hideLoading();
		},
		//取消订单
		async cancelOrder(item) {
			uni.showLoading({
				title: '请稍后'
			});
			let params = { orderId: item.id };
			let data = await Api.apiCall('post', Api.order.closeOrder, params);

			if (data) {
				this.$api.msg(data.data);
			}
			uni.hideLoading();
		},
		//订单确认收货
		async confimDelivery(item) {
			uni.showLoading({
				title: '请稍后'
			});
			let params = { id: item.id };
			let data = await Api.apiCall('post', Api.order.confimDelivery, params);

			if (data) {
				this.$api.msg(data);
			}
			uni.hideLoading();
		},
		//订单申请退款
		async applyRefund(item) {
			uni.showLoading({
				title: '请稍后'
			});
			let params = { id: item.id };
			let data = await Api.apiCall('post', Api.order.applyRefund, params);
			console.log(data);
			if (data) {
				this.$api.msg(data.data);
			}
			uni.hideLoading();
		},
// 去评价
		toEvaluate(orderId) {
			this.$common.navigateTo(
					'/pages/order/evaluate?order_id=' + orderId
			)
		},
		//订单状态文字和颜色
		orderStateExp(value) {
			let stateTip = '',
					stateTipColor = '#fa436a';
			if (value === 12) {
				stateTipColor = '#909399';
				return '待付款';
			}if (value === 1) {
				stateTipColor = '#909399';
				return '支付成功，没有回掉';
			}if (value === 2) {
				stateTip = '待发货';
			} else if (value === 3) {
				stateTip = '待收货';
			} else if (value === 4) {
				stateTip = '待评价';
			} else if (value === 5) {
				stateTip = '已完成';
			} else if (value === 6) {
				stateTipColor = '#909399';
				stateTip = '维权中';
			} else if (value === 7) {
				stateTip = ' 维权已完成';
			} else if (value === 8) {
				stateTip = '待分享';
			} else if (value === 13) {
				stateTip = '申请退款';
			} else if (value === 14) {
				stateTip = '已退款';
			}  else if (value === 15) {
				stateTip = '已关闭';
			} else if (value === 16) {
				stateTip = '无效订单';
			} else {
				stateTip = '待付款';
			}

			return { stateTip, stateTipColor };
		},
		dateFormat(time) {
			if (time == null || time === '') {
				return 'N/A';
			}
			let date = new Date(time);
			return formatDate(date, 'yyyy-MM-dd hh:mm:ss');
		}
	}
};
</script>

<style lang="scss">
page,
.content {
	background: $page-color-base;
	height: 100%;
}

.swiper-box {
	height: calc(100% - 40px);
}
.list-scroll-content {
	height: 100%;
}

.navbar {
	display: flex;
	height: 40px;
	padding: 0 5px;
	background: #fff;
	box-shadow: 0 1px 5px rgba(0, 0, 0, 0.06);
	position: relative;
	z-index: 10;
	.nav-item {
		flex: 1;
		display: flex;
		justify-content: center;
		align-items: center;
		height: 100%;
		font-size: 15px;
		color: $font-color-dark;
		position: relative;
		&.current {
			color: $base-color;
			&:after {
				content: '';
				position: absolute;
				left: 50%;
				bottom: 0;
				transform: translateX(-50%);
				width: 44px;
				height: 0;
				border-bottom: 2px solid $base-color;
			}
		}
	}
}

.uni-swiper-item {
	height: auto;
}
.order-item {
	display: flex;
	flex-direction: column;
	padding-left: 30upx;
	background: #fff;
	margin-top: 16upx;
	.i-top {
		display: flex;
		align-items: center;
		height: 80upx;
		padding-right: 30upx;
		font-size: $font-base;
		color: $font-color-dark;
		position: relative;
		.time {
			flex: 1;
		}
		.state {
			color: $base-color;
		}
		.del-btn {
			padding: 10upx 0 10upx 36upx;
			font-size: $font-lg;
			color: $font-color-light;
			position: relative;
			&:after {
				content: '';
				width: 0;
				height: 30upx;
				border-left: 1px solid $border-color-dark;
				position: absolute;
				left: 20upx;
				top: 50%;
				transform: translateY(-50%);
			}
		}
	}
	/* 多条商品 */
	.goods-box {
		height: 160upx;
		padding: 20upx 0;
		white-space: nowrap;
		.goods-item {
			width: 120upx;
			height: 120upx;
			display: inline-block;
			margin-right: 24upx;
		}
		.goods-img {
			display: block;
			width: 100%;
			height: 100%;
		}
	}
	/* 单条商品 */
	.goods-box-single {
		display: flex;
		padding: 20upx 0;
		.goods-img {
			display: block;
			width: 120upx;
			height: 120upx;
		}
		.right {
			flex: 1;
			display: flex;
			flex-direction: column;
			padding: 0 30upx 0 24upx;
			overflow: hidden;
			.title {
				font-size: $font-base + 2upx;
				color: $font-color-dark;
				line-height: 1;
			}
			.attr-box {
				font-size: $font-sm + 2upx;
				color: $font-color-light;
				padding: 10upx 12upx;
			}
			.price {
				font-size: $font-base + 2upx;
				color: $font-color-dark;
				&:before {
					content: '￥';
					font-size: $font-sm;
					margin: 0 2upx 0 8upx;
				}
			}
		}
	}

	.price-box {
		display: flex;
		justify-content: flex-end;
		align-items: baseline;
		padding: 20upx 30upx;
		font-size: $font-sm + 2upx;
		color: $font-color-light;
		.num {
			margin: 0 8upx;
			color: $font-color-dark;
		}
		.price {
			font-size: $font-lg;
			color: $font-color-dark;
			&:before {
				content: '￥';
				font-size: $font-sm;
				margin: 0 2upx 0 8upx;
			}
		}
	}
	.action-box {
		display: flex;
		justify-content: flex-end;
		align-items: center;
		height: 100upx;
		position: relative;
		padding-right: 30upx;
	}
	.action-btn {
		width: 160upx;
		height: 60upx;
		margin: 0;
		margin-left: 24upx;
		padding: 0;
		text-align: center;
		line-height: 60upx;
		font-size: $font-sm + 2upx;
		color: $font-color-dark;
		background: #fff;
		border-radius: 100px;
		&:after {
			border-radius: 100px;
		}
		&.recom {
			background: #fff9f9;
			color: $base-color;
			&:after {
				border-color: #f7bcc8;
			}
		}
	}
}

/* load-more */
.uni-load-more {
	display: flex;
	flex-direction: row;
	height: 80upx;
	align-items: center;
	justify-content: center;
}

.uni-load-more__text {
	font-size: 28upx;
	color: #999;
}

.uni-load-more__img {
	height: 24px;
	width: 24px;
	margin-right: 10px;
}

.uni-load-more__img > view {
	position: absolute;
}

.uni-load-more__img > view view {
	width: 6px;
	height: 2px;
	border-top-left-radius: 1px;
	border-bottom-left-radius: 1px;
	background: #999;
	position: absolute;
	opacity: 0.2;
	transform-origin: 50%;
	animation: load 1.56s ease infinite;
}

.uni-load-more__img > view view:nth-child(1) {
	transform: rotate(90deg);
	top: 2px;
	left: 9px;
}

.uni-load-more__img > view view:nth-child(2) {
	transform: rotate(180deg);
	top: 11px;
	right: 0;
}

.uni-load-more__img > view view:nth-child(3) {
	transform: rotate(270deg);
	bottom: 2px;
	left: 9px;
}

.uni-load-more__img > view view:nth-child(4) {
	top: 11px;
	left: 0;
}

.load1,
.load2,
.load3 {
	height: 24px;
	width: 24px;
}

.load2 {
	transform: rotate(30deg);
}

.load3 {
	transform: rotate(60deg);
}

.load1 view:nth-child(1) {
	animation-delay: 0s;
}

.load2 view:nth-child(1) {
	animation-delay: 0.13s;
}

.load3 view:nth-child(1) {
	animation-delay: 0.26s;
}

.load1 view:nth-child(2) {
	animation-delay: 0.39s;
}

.load2 view:nth-child(2) {
	animation-delay: 0.52s;
}

.load3 view:nth-child(2) {
	animation-delay: 0.65s;
}

.load1 view:nth-child(3) {
	animation-delay: 0.78s;
}

.load2 view:nth-child(3) {
	animation-delay: 0.91s;
}

.load3 view:nth-child(3) {
	animation-delay: 1.04s;
}

.load1 view:nth-child(4) {
	animation-delay: 1.17s;
}

.load2 view:nth-child(4) {
	animation-delay: 1.3s;
}

.load3 view:nth-child(4) {
	animation-delay: 1.43s;
}

@-webkit-keyframes load {
	0% {
		opacity: 1;
	}

	100% {
		opacity: 0.2;
	}
}
</style>
