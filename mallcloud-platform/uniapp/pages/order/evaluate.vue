<template>
	<view class="content">
		<view class="content-top">
			<view class='img-list'>
				<view class='img-list-item'
				v-for="item in info.orderItemList"
				:key="item.id"
				>
					<view class="img-list-item-gray">
						<image class='img-list-item-l small-img' :src='item.productPic' mode='aspectFill'></image>
						<view class='img-list-item-r small-right'
						@click="goodsDetail(item.productId)"
						>
							<view class='little-right-t'>
								<view class='goods-name list-goods-name'>{{ item.productName }}</view>
							</view>
						</view>
					</view>

					<view class="evaluate-num">
						<view class="evaluate-num-t">商品评分</view>
						<view class="evaluate-num-b">
							<uni-rate
							size="18"
							:id="item.id"
							:value="score[item.id]"
							@change="changeScore"
							></uni-rate>
						</view>
					</view>

					<view class="evaluate-content">
						<view class="evaluate-c-t">
							<textarea v-model="textarea[item.id]" placeholder="宝贝满足你的期待吗? 说说你的使用心得" />
						</view>
						<view class="evaluate-c-b">
							<view class="goods-img-item"
							v-if="images[item.id].length"
							v-for="(img, key) in images[item.id]"
							:key="key"
							>
								<image class="del" src="../../../static/image/del.png" mode="" @click="removeImg(item.id, key)"></image>
								<image class="" :src="img.url" mode="" @click="clickImg(img.url)"></image>
							</view>
							<view class="upload-img" v-show="isupload[item.id]">
								<image class="icon" src="../../../static/image/camera.png" mode="" @click="uploadImg(item.id)"></image>
								<view class="">上传照片</view>
							</view>
						</view>
					</view>

				</view>
			</view>

		</view>

		<view class="button-bottom">
			<button class="btn btn-square btn-b" hover-class="btn-hover" @click="toEvaluate" :disabled='submitStatus' :loading='submitStatus'>提交评论</button>
		</view>

	</view>
</template>

<script>
	import Api from '@/common/api';

import uniRate from "@/components/uni-rate/uni-rate.vue"
import { goods } from '@/config/mixins.js'
export default {
	mixins: [goods],
    components: {uniRate},
	data () {
		return {
			orderId: 0,
			info: {}, // 订单详情
			images: [],
            score: [], // 商品评价
            textarea: [], // 商品评价信息
            isupload: [], // 启/禁用 图片上传按钮
			rate: 5,
			submitStatus: false
		}
	},
	onLoad (options) {
		this.orderId = options.order_id
		this.orderId
		? this.orderInfo()
		: this.$common.errorToShow('获取失败', () => {
			uni.navigateBack({
				delta: 1
			})
		})
	},
	computed: {
		// 获取vuex中状态
		maxUploadImg () {
			return 1
		}
	},
	methods: {
		// 获取订单详情
		async orderInfo () {
			let params = { id: this.orderId };
			this.orderInfo = await Api.apiCall('get', Api.order.orderDetail, params);
			let images = []
			let textarea = []
			let upload = []
			let score = []

			this.orderInfo.orderItemList.forEach (item => {
				if (item.type==1){
					images[item.id] = []
					textarea[item.id] = ''
					upload[item.id] = true
					score[item.id] = 5
				}

			})

			this.info = this.orderInfo

			this.images = images
			this.textarea = textarea
			this.score = score
			this.isupload = upload
		},
		// 上传图片
		uploadImg (key) {
			this.$otherApi.uploadFiles(res => {
				if (res.code == 200) {
					let img = {
                        url: res.data,
                        id: res.data
                    }
                    this.images[key].push(img)
					this.$common.successToShow(res.msg)
				} else {
					this.$common.errorToShow(res.msg)
				}
			})
		},

		// 删除图片
		removeImg (id, key) {
			this.images[id].splice(key, 1)
		},
		// 图片点击放大
		clickImg (img) {
			// 预览图片
			uni.previewImage({
				urls: img.split()
			});
		},
		// 改变评分
		changeScore (e) {
			this.score[e.id] = e.value
		},
		// 提交评价
		async toEvaluate () {
			this.submitStatus = true;
			let items = {}
            var myArray=new Array()
			this.images.forEach((item, key) => {
				let itemmm = {
				    goodsId:key,
					images: item.url,
                    score: this.score[key],
                    textarea: this.textarea[key]
				}
				myArray.push(itemmm);
			})
			let params = {
				orderId: this.orderId,
				items: JSON.stringify(myArray)
			}
				uni.showLoading({
					title: '请稍后'
				});

				let data = await Api.apiCall('post', Api.order.orderevaluate, params);
				console.log(data);
				if (data) {
					// 更改订单列表页的订单状态
					let pages = getCurrentPages(); // 当前页
					let beforePage = pages[pages.length - 2]; // 上个页面

					if (beforePage !== undefined && beforePage.route === 'pages/order/order') {
						// #ifdef MP-WEIXIN
						beforePage.$vm.isReload = true
						// #endif

						// #ifdef H5
						beforePage.isReload = true
						// #endif

						// #ifdef MP-ALIPAY
						beforePage.rootVM.isReload = true
						// #endif
					}
					this.submitStatus = false;
					uni.navigateBack({
						delta: 1
					})
				}
				uni.hideLoading();
		}
	},
	watch: {
		images: {
			handler () {
				this.images.forEach((item, key) => {
					this.isupload[key] = item.length > this.maxUploadImg ? false : true
				})
			},
			deep: true
		}
	}
}
</script>

<style>
.img-list-item{
	padding: 30upx 20upx;
}
.img-list-item-gray{
	background-color: #F7F7F7;
	overflow: hidden;
	padding: 18upx 20upx;
}
.small-right{
	width: 520upx;
}
.evaluate-content{
	background-color: #fff;
	padding: 20upx 0upx;
}
.evaluate-c-t{
	width: 100%;
	height: 240upx;
}
.evaluate-c-t textarea{
	width: 100%;
	height: 100%;
	font-size: 26upx;
	padding: 10upx;
}
.evaluate-c-b{
	overflow: hidden;
}
.upload-img{
	width: 146upx;
	height: 146upx;
	margin: 14upx;
	text-align: center;
	color: #999999;
	font-size: 22upx;
	border: 2upx solid #E1E1E1;
	/* #ifdef MP-ALIPAY */
	border-top: 8upx solid #E1E1E1;
	/* #endif */
	border-radius: 4upx;
	display: inline-block;
	float: left;
	padding: 24upx 0;
}
.goods-img-item{
	width: 174upx;
	height: 174upx;
	padding: 14upx;
	float: left;
	position: relative;
}
.goods-img-item:nth-child(4n){
	margin-right: 0;
}
.goods-img-item image{
	width: 100%;
	height: 100%;
}
.del{
	width: 30upx !important;
	height: 30upx !important;
	position: absolute;
	right: 0;
	top: 0;
	z-index: 999;
}
.evaluate-num{
	padding: 20upx 26upx;
	background-color: #fff;
	margin-top: 20upx;
}
.evaluate-num-t{
	color: #333;
	font-size: 28upx;
	margin-bottom: 20upx;
}
.button-bottom .btn{
	width: 100%;
}
</style>
