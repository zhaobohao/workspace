<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="room" :rules="rules" ref="roomFrom" label-width="150px">

      <el-form-item label="房屋编号：" prop="roomNum">
        <el-input v-model="room.roomNum"></el-input>
      </el-form-item>

      <el-form-item label="室：" prop="section">
        <el-select
                v-model="room.section"

                placeholder="请选择室">
          <el-option
                  v-for="item in cateOptions1"
                  :key="item.id"
                  :label="item.id"
                  :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="户型：" prop="apartment">
        <el-select
                v-model="room.apartment"

                placeholder="请选择户型">
          <el-option
                  v-for="item in cateOptions2"
                  :key="item.id"
                  :label="item.id"
                  :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>


      <el-form-item label="建筑面积：" prop="builtUpArea">
        <el-input v-model="room.builtUpArea"></el-input>
      </el-form-item>
      <el-form-item label="每平米单价：" prop="unitPrice">
        <el-input v-model="room.unitPrice"></el-input>
      </el-form-item>
      <el-form-item label="备注：" prop="remark">
        <el-input v-model="room.remark"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit('roomFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('roomFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {createRoom, getRoom, updateRoom} from '@/api/build/room'
  import SingleUpload from '@/components/Upload/singleUpload'
  import {formatDate} from '@/utils/date';
  const defaultRoom={
    name: ''
  };
  export default {
    name: 'RoomDetail',
    components:{SingleUpload},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        room:Object.assign({}, defaultRoom),
        cateOptions1:[
          {
            id: "一室",
            name: "一室",
          },
          {
            id: "二室",
            name: "二室",
          },
          {
            id: "三室",
            name: "三室",
          },
          {
            id: "四室",
            name: "四室",
          },
          {
            id: "五室",
            name: "五室",
          },
          {
            id: "六室",
            name: "六室",
          },
        ],
        cateOptions2:[
          {
            id: "一厅",
            name: "一厅",
          },
          {
            id: "二厅",
            name: "二厅",
          },
          {
            id: "三厅",
            name: "三厅",
          },
          {
            id: "四厅",
            name: "四厅",
          },
          {
            id: "五厅",
            name: "五厅",
          },
          {
            id: "六厅",
            name: "六厅",
          },
        ],
        rules: {
          name: [
            {required: true, message: '请输入品牌名称', trigger: 'blur'},
            {min: 2, max: 140, message: '长度在 2 到 140 个字符', trigger: 'blur'}
          ],
          logo: [
            {required: true, message: '请输入品牌logo', trigger: 'blur'}
          ],
          sort: [
            {type: 'number', message: '排序必须为数字'}
          ],
        }
      }
    },
    created() {
      if (this.isEdit) {
        getRoom(this.$route.query.id).then(response => {
          this.room = response.data;
        });
      }else{
        this.room = Object.assign({},defaultRoom);
      }
    },
    methods: {
      onSubmit(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$confirm('是否提交数据', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              this.room.unitId=this.$route.query.cid
              if (this.isEdit) {
                updateRoom(this.$route.query.id, this.room).then(response => {
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
                createRoom(this.room).then(response => {
                    if(response.code==200){
                      this.$refs[formName].resetFields();
                      this.room = Object.assign({},defaultRoom);
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
        this.room = Object.assign({},defaultRoom);
        this.room.unitId = Number(this.$route.query.cid);
      }
    }
  }
</script>
<style>
</style>


