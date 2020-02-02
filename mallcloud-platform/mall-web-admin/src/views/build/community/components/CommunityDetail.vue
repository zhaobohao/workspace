<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="community" :rules="rules" ref="communityFrom" label-width="100px">

      <el-form-item label="小区名称：" prop="name">
        <el-input v-model="community.name"></el-input>
      </el-form-item>
      <el-form-item label="小区栋数：" prop="counts">
        <el-input v-model="community.counts"></el-input>
      </el-form-item>
      <el-form-item label="小区地址：" prop="address">
        <el-input v-model="community.address"></el-input>
      </el-form-item>

      <el-form-item label="地标：" prop="nearbyLandmarks">
        <el-input v-model="community.nearbyLandmarks"></el-input>
      </el-form-item>

      <el-form-item label="电话：" prop="phone">
        <el-input v-model="community.phone"></el-input>
      </el-form-item>
      <el-form-item label="地图：" prop="phone">

        <input v-model.number="center.lng" />
        <input v-model.number="center.lat" />
        <!--<label>关键词：<input v-model="keyword"></label>
        <label>地区：<input v-model="location"></label>-->

        <baidu-map  ak="15BWmtGEGGkZ8lSPUGah30XZ6IGw57HE"
                    class="map bm-view"

                :scroll-wheel-zoom="true"
                :center="center"
                :zoom="zoom"
                    :auto-resize="true"
                @moving="syncCenterAndZoom"
                @moveend="syncCenterAndZoom"
                @zoomend="syncCenterAndZoom">
          <bm-copyright
                  anchor="BMAP_ANCHOR_TOP_RIGHT"
                  :copyright="[{id: 1, content: 'Copyright Message', bounds: {ne: {lng: 110, lat: 40}, sw:{lng: 0, lat: 0}}}, {id: 2, content: '<a>mallplus多租户商城</a>'}]">
          </bm-copyright>
          <bm-city-list anchor="BMAP_ANCHOR_TOP_LEFT" @changeAfter="changeAfters"></bm-city-list>

         <!-- <bm-local-search :keyword="keyword" :auto-viewport="true" :location="location"></bm-local-search>-->
        </baidu-map>

      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit('communityFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('communityFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>

  import BaiduMap from 'vue-baidu-map/components/map/Map.vue'
  import BmLocalSearch from 'vue-baidu-map/components/search/LocalSearch';
  import BmCityList from 'vue-baidu-map/components/controls/CityList';
  import BmCopyright from 'vue-baidu-map/components/controls/Copyright';
  import BmView from 'vue-baidu-map/components/map/MapView';
  import {createCommunity, getCommunity, updateCommunity} from '@/api/build/community'
  import SingleUpload from '@/components/Upload/singleUpload'
  import {formatDate} from '@/utils/date';
  const defaultCommunity={
    name: ''
  };
  export default {
    name: 'CommunityDetail',
    components:{SingleUpload,BaiduMap,BmLocalSearch,BmCityList,BmView,BmCopyright},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        community:Object.assign({}, defaultCommunity),
        rules: {
          name: [
            {required: true, message: '请输入名称', trigger: 'blur'},
            {min: 2, max: 140, message: '长度在 2 到 140 个字符', trigger: 'blur'}
          ],
          logo: [
            {required: true, message: '请输入品牌logo', trigger: 'blur'}
          ],
          sort: [
            {type: 'number', message: '排序必须为数字'}
          ],
        },
        location: '北京',
        keyword: '百度',
        center: {
          lng: 116.404,
          lat: 39.915
        },
        zoom: 15
      }
    },
    mounted (){

     // this.handleScroll()
     // window.addEventListener('mousewheel',this.handleScroll,false)
    },
    created() {
      if (this.isEdit) {
        getCommunity(this.$route.query.id).then(response => {
          this.community = response.data;
        });
      }else{
        this.community = Object.assign({},defaultCommunity);
      }
    },

    methods: {
      changeAfters(e){
        console.log('123')
        console.log(e)
      },
      handleScroll (e) {
        //修复body高度为100%，百度地图鼠标滚轮滚动产生位移问题，以及全景鼠标hover后未显示全景信息问题
        document.body.scrollTop=0
        document.documentElement.scrollTop =0
      },
      syncCenterAndZoom (e) {

        const {lng, lat} = e.target.getCenter()
        this.center.lng = lng
        this.center.lat = lat
        this.zoom = e.target.getZoom()
      },
      onSubmit(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$confirm('是否提交数据', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              this.community.companyId=this.$route.query.cid
              this.community.latitude=this.center.lat;
              this.community.longitude=this.center.lng;
              if (this.isEdit) {
                updateCommunity(this.$route.query.id, this.community).then(response => {
                    if(response.code==200){
                      this.$refs[formName].resetFields();
                      this.$message({
                          message: '修改成功',
                          type: 'success',
                          duration:1000
                      });
                      this.$router.back();
                  }else{
                      this.$message({
                          message: response.msg,
                          type: 'error',
                          duration:1000
                      });
                  }

                });
              } else {
                createCommunity(this.community).then(response => {
                    if(response.code==200){
                      this.$refs[formName].resetFields();
                      this.community = Object.assign({},defaultCommunity);
                      this.$message({
                          message: '提交成功',
                          type: 'success',
                          duration:1000
                      });
                      this.$router.back();
                  }else{
                      this.$message({
                          message: response.msg,
                          type: 'error',
                          duration:1000
                      });
                  }

                });
              }
            });

          } else {
            this.$message({
              message: '验证失败',
              type: 'error',
              duration:1000
            });
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
        this.community = Object.assign({},defaultCommunity);
      },


    },

  }
</script>
<style>
  .bm-view {
    width: 100%;
    height: 400px;
  }

</style>


