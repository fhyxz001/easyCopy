<template>
<!--这个文件作为网站路由，第一个tab是便捷复制，第二个tab是chatAI-->
  <div class="index">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="便捷复制" name="first">
        <easy-copy></easy-copy>
      </el-tab-pane>
      <el-tab-pane label="OpenChatAI" name="second">
        <chat-a-i></chat-a-i>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import easyCopy from "./EasyCopy";
import chatAI from "./ChatAI";
export default {
  name: "index",
  components: {
    easyCopy,
    chatAI
  },
  data() {
    return {
      activeName: "first"
    };
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    }
  }
};
</script>

<style scoped>
.index {
  width: 100%;
  height: 100%;
}
</style>