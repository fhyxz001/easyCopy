<template>
  <div class="chat">
<!--最下方是一个固定在底部的输入框，右侧有个提交的按钮-->
    <div class="chat-input">
      <el-input type="textarea" :rows="2" v-model="input" placeholder="请输入内容"/>
      <el-button @click="submit" type="primary">发送</el-button>
    </div>
    <div class="chat-output">
      <el-row v-for="(item,index) in output" :key="index">
        <el-col :span="12" v-if="item.type=='user'">
          <div class="chat-output-user">
              <el-tag type="primary" effect="dark" size="medium">{{item.content}}</el-tag>
          </div>
        </el-col>
        <el-col :span="12" v-else>
            <div class="chat-output-ai" v-html="item.content"></div>
        </el-col>
      </el-row>
    </div>
<!--    在右上角增加一个配置的按钮-->
    <el-button class="chat-config" type="primary" icon="el-icon-setting" circle @click="config"></el-button>
  </div>
</template>
<script>
import axios from 'axios'
export default {
  name: "easyCopy",
  data() {
    return {
      input: "",
      output: [],
      chatParam: {
        model: "text-davinci-003",
        prompt: "",
        temperature: 0,
        max_tokens: 1000
      },
      key:""
    };
  },
  methods: {
    submit() {
      //将用户输入的内容添加到output中
      this.output.push({type: "user", content: this.input});
      //使用axios以post的方式调用/api/ChatAI/getAnswer,传入参数chatParam,用户输入框中的内容替换掉prompt，在获得返回值后，将返回值添加到output中，最后清空input
      this.chatParam.prompt = this.input;
      axios.post("/api/ChatAI/getAnswer", this.chatParam).then(res => {
        this.output.push({type: "robot", content: res.data});
        this.input = "";
      });
    }
  },
  //按下回车的时候，触发submit方法，然后清空input，删掉换行符
  mounted() {
    document.onkeydown = e => {
      if (e.keyCode === 13) {
        //如果input为空，就不触发submit
        if (this.input === "") {
          return;
        }
        this.submit();
        this.input = "";
        return false;
      }
    };
  }

}
</script>
<style scoped>
.chat {
  width: 100%;
  height: 100%;
}
.chat-output{
  width: 100%;
  height: 80%;
  overflow: auto;
/*  换行*/
  word-break: break-all;
/*  上下有一定间隔*/
  padding: 10px;

}
.chat-input{
/*  这个输入框始终位于整个屏幕的最下方，并且其中的button始终位于最右侧，且宽高和input大小一致*/
  position: fixed;
  bottom: 0;
  width: 100%;
  height: 10%;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
}
.chat-input button{
  width: 10%;
  height: 60%;
}
.chat-output-user{
  display: flex;
  flex-direction: row-reverse;
}
.chat-output-robot{
  display: flex;
  flex-direction: row;
}
.chat-config{
  position: fixed;
  top: 0;
  right: 0;
}

</style>