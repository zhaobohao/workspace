<template> 
  <div style="margin-top: 50px">
    <el-form :model="role" :rules="rules" ref="roleFrom"
             label-width="120px" style="width: 680px"
             size="small">
      <el-form-item label="用户名称：" prop="name">
        <el-input v-model="role.nickName"></el-input>
      </el-form-item>

      <el-form-item label="关联权限：">
        <el-transfer
          style="display: inline-block"
          filterable
          :filter-method="filterMethod"
          filter-placeholder="请输入权限名称"
          v-model="selectSubject"
          :titles="subjectTitles"
          :data="subjectList">
        </el-transfer>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit('roleFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('roleFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
  import { getAdmin,userCommunityRelate,communityList } from '@/api/admin'
  import {fetchList as fetchSubjectList} from '@/api/build/community'
  import SingleUpload from '@/components/Upload/singleUpload'
  const defaultRole={
    name: ''
  };
  export default {
    name: 'RoleDetail',
    components:{SingleUpload},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        role:Object.assign({}, defaultRole),
        //所有专题列表
        subjectList: [],
        //专题左右标题
        subjectTitles: ['待选择', '已选择'],
        communityIds:null,
        rules: {

        }
      }
    },
    created() {
      this.getSubjectList();

        communityList(this.$route.query.id).then(res => {
          this.communityIds=res.data;
         });
        getAdmin(this.$route.query.id).then(response => {
          this.role = response.data;
        });

    },
    computed:{
      //选中的专题
      selectSubject:{
        get:function () {
          let subjects =[];
          if(this.communityIds==null||this.communityIds.length<=0){
            return subjects;
          }
          for(let i=0;i<this.communityIds.length;i++){
            subjects.push(this.communityIds[i].communityId);
          }
          return subjects;
        },
        set:function (newValue) {
          console.log(newValue)
          this.communityIds=[];
          for(let i=0;i<newValue.length;i++){
            this.communityIds.push({communityId:newValue[i]});
          }
        }
      },
    },
    methods: {
      getSubjectList() {
        fetchSubjectList().then(response => {
          let list = response.data.records;
          for (let i = 0; i < list.length; i++) {
            this.subjectList.push({
              label: list[i].name,
              key: list[i].id
            });
          }
        });
      },

      filterMethod(query, item) {
        return item.label.indexOf(query) > -1;
      },
      onSubmit(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$confirm('是否提交数据', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              let serviceIds='';
              for(let i=0;i<this.communityIds.length;i++){
                serviceIds += this.communityIds[i].communityId + ',';
              }
              if (serviceIds.endsWith(',')) {
                serviceIds = serviceIds.substr(0, serviceIds.length - 1)
              }
              this.role.communityIds = serviceIds;
              this.role.userId=this.role.id;
              if (this.isEdit) {
                userCommunityRelate( this.role).then(response => {
                  this.$refs[formName].resetFields();
                  this.$message({
                    message: '修改成功',
                    type: 'success',
                    duration:1000
                  });
                  this.$router.back();
                });
              } else {
                userCommunityRelate(this.role).then(response => {
                  this.$refs[formName].resetFields();
                  this.role = Object.assign({},defaultRole);
                  this.$message({
                    message: '提交成功',
                    type: 'success',
                    duration:1000
                  });
                this.$router.back();
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
        this.role = Object.assign({},defaultRole);
      }
    }
  }
</script>
<style scoped>

</style>


