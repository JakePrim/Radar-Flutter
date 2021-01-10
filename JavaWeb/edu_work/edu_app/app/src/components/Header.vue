<template>
  <div class="header">{{msg}}</div>
</template>
<script>
export default {
  name:"Header",
  data() {
    return {
      msg:"这是一个header组件"
    }
  },
  props:[
    'msg'
  ]
}
</script>

<style scoped>
</style>