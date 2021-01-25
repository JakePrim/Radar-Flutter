<template>
  <el-card class="form-container" shadow="never">
    <el-form :model="homeAdvertise" :rules="rules" ref="form" label-width="150px" size="small">
      <el-form-item label="广告位名称：" prop="name">
        <el-input v-model="homeAdvertise.name" class="input-width"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSave()">提交</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
import { axios } from "../../utils";

export default {
  name: "HomeAdvertiseDetail",
  //组件传参
  props: {
    isEdit: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      homeAdvertise: { name: null },
      rules: {
        name: [
          { required: true, message: "请输入广告名称", trigger: "blur" },
          {
            min: 2,
            max: 140,
            message: "长度在 2 到 140 个字符",
            trigger: "blur"
          }
        ]
      }
    };
  },

  //钩子函数
  created() {
    //判断是添加还是修改操作
    if(this.isEdit){
      //获取路由传递的参数
      //修改操作
      this.loadPromotionSpace(this.$route.query.id)
    }else{
      this.homeAdvertise = {}
    }
  },

  methods: {
    //方法1: 保存广告位信息
    handleSave() {
        this.$refs.form.validate(valid=>{
          if(!valid) return false;
          //校验通过请求后台
           axios.post('/PromotionSpace/saveOrUpdatePromotionSpace',this.homeAdvertise)
            .then(res=>{
              //保存或更新成功 返回上一页
              this.$router.back()

            }).catch(()=>{
            this.$message('保存/更新失败')
            })
      })
    },

    //方法2: 回显广告位信息
    loadPromotionSpace(id) {
      ///PromotionSpace/findPromotionSpaceById
      axios.get('/PromotionSpace/findPromotionSpaceById?id='+id).then(res=>{
              Object.assign(this.homeAdvertise,res.data.content)
            }).catch(()=>{
            this.$message('查询数据失败')
            })
    }
  }
};
</script>
<style scoped>
.input-width {
  width: 70%;
}
</style>
