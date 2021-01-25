<template>
  <el-card class="form-container" shadow="never">
    <el-tree :data="menuTreeList" show-checkbox default-expand-all node-key="id" ref="tree" highlight-current :props="defaultProps"></el-tree>
    <div style="margin-top: 20px" align="center">
      <el-button type="primary" @click="handleSave()">保存</el-button>
      <el-button @click="handleClear()">清空</el-button>
    </div>
  </el-card>
</template>

<script>
import { axios } from "../../utils";

export default {
  name: "allocMenu",
  title: "角色菜单管理",
  data() {
    return {
      menuTreeList: [], //菜单数据
      checkedMenuId: [], //被选中的菜单

      //树形结构子节点设置
      defaultProps: {
        children: "subMenuList",
        label: "name"
      },
      roleId: null
    };
  },
  //钩子函数
  created() {
    //获取路由携带的id
    this.roleId = this.$route.query.roleId;

    //获取菜单列表
    this.treeList();

    //获取角色所拥有的菜单信息
    this.getRoleMenu(this.roleId);
  },
  methods: {
    //方法1: 获取菜单列表,使用树形控件展示
    treeList() {
      axios.get('/role/findAllMenu').then(res=>{
        this.menuTreeList = res.data.content.parentMenuList
      }).catch(()=>{
        this.$message('查询菜单信息失败')
      })
    },

    //方法2: 获取当前角色所拥有菜单列表id
    getRoleMenu(roleId) {
      axios.get('/role/findMenuIdByRoleId?roleId='+roleId).then(res=>{
        this.$refs.tree.setCheckedKeys(res.data.content)
      }).catch(()=>{
        this.$message('获取当前角色拥有的菜单信息失败')
      })
    },

    //方法3: 修改角色所拥有的菜单列表
    handleSave() {
      //1. 获取所有被选中的节点
      const checkedNodes = this.$refs.tree.getCheckedNodes();

      //2. 定义数组保存菜单id
      const checkedMenuIds = []

      //3. 遍历菜单判断
      if (checkedNodes != null && checkedNodes.length>0) {
        for(let i =0;i<checkedNodes.length ; i++){
          const node = checkedNodes[i];
          //保存菜单id
          checkedMenuIds.push(node.id)

          //当前节点是子节点，该子节点的父节点id没有被保存
          if (node.parentId !== -1 && checkedMenuIds.filter(item => node.parentId).length === 0) {
            checkedMenuIds.push(node.parentId)
          }
        }
      }
      axios.post('/role/roleContextMenu',{
        roleId:this.roleId,
        menuIdList:checkedMenuIds
      }).then(res=>{
        this.$router.back()
      }).catch(()=>{
        this.$message('修改失败')
      })
    },

    //清空选择
    handleClear() {
      this.$refs.tree.setCheckedKeys([]);
    }
  }
};
</script>

<style scoped>
</style>
