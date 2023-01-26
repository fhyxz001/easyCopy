<template>
  <div class="bg">
    <div class="easyCopy">
  <div class="easycopy_item">
    <div class="cover">
      <img class="head_img" src="../assets/head.png" alt="head">
    </div>
    <p class="tip">
      目前支持网站：
      <a target="_blank" href="https://news.dmzj.com/donghuaqingbao">动漫之家</a>、
      <a target="_blank" href="https://www.3dmgame.com/news/">3DM新闻</a>、
      <a target="_blank" href="https://www.gao7.com/">搞趣网</a>、
      <a target="_blank" href="http://www.hotacg.com/">热点ACG</a>、
      <a target="_blank" href="https://www.vgtime.com/topic/index.jhtml">游戏时光</a>、
      <a target="_blank" href="https://www.ali213.net/news/new/">游侠资讯</a>
      <a target="_blank" href="http://news.sohu.com/">搜狐网</a>
      <a target="_blank" href="https://www.gamersky.com/">游民星空</a>
    </p>
    <div class="easyCopy-input">
      <el-input type="text"  v-model="url" placeholder="请输入url"/>
      <el-button type="primary" @click="pasteUrl">粘贴</el-button>
<!--      重置-->
      <el-button @click="resetUrl">重置</el-button>
    </div>
    <div class="easyCopy-input">
<!--      下拉选项，参数是options，v-model是type-->
      <el-select v-model="type" placeholder="请选择转载论坛">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
        &nbsp;&nbsp;&nbsp;&nbsp;<el-button style="float: right" type="primary" @click="parse">解析</el-button>
    </div>
    <el-divider></el-divider>
    <div class="easyCopy-output"  v-if="title!=''" >
      <el-input type="textarea" :rows="2" v-model="title" placeholder="解析标题"/>
      <el-button @click="copy(title)">复制</el-button>
    </div>
    <!--    content返回值显示框，右侧有个复制按钮，点击可以复制到剪切板-->
    <div class="easyCopy-output" v-if="content!=''" >
      <el-input type="textarea" :rows="20" v-model="content" placeholder="解析内容"/>
      <el-button @click="copy(content)">复制</el-button>
    </div>
    </div>
  </div>
  </div>
</template>
<script>
import axios from 'axios'
export default {
  name: "easyCopy",
  data() {
    return {
      url: "",
      title: "",
      content: "",
      type: 0,
      comments: "",
      options:[
        {value: 0, label: '萌享'},
        {value: 1, label: '花火论坛'},
      ]
    };
  },
  methods: {
    parse() {
      //加载动画
      this.$loading({
        lock: true,
        text: '解析中',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
      //使用axios,url采用vue.config.js中的代理，解决跨域问题
      axios.get("/api/easyCopy/copy?url=" + this.url+"&siteType="+this.type+"&comments="+this.comments).then(res => {
        //将返回的title和content赋值给data中的title和content
        this.title = res.data.title;
        this.content = res.data.content;
        //elemnt的message组件，显示解析成功
        this.$message({
          message: "解析成功",
          type: "success"
        });
      }).catch(err => {
        //elemnt的message组件，显示解析失败
        this.$message({
          message: "解析失败",
          type: "error"
        });
      }).finally(() => {
        //关闭加载动画
        this.$loading().close();
      });
    },
    copy(text) {
      //带着格式复制
      let input = document.createElement("textarea");
      input.value = text;
      document.body.appendChild(input);
      input.select();
      document.execCommand("Copy");
      document.body.removeChild(input);
      this.$message({
        message: "复制成功",
        type: "success"
      });
    },
    pasteUrl(){
      //获取剪切板内容
      navigator.clipboard.readText().then(clipText => {
        //将剪切板内容赋值给url
        this.url = clipText;
      });
    },
    resetUrl(){
      //重置url
      this.url = "";
    }
  }
}
</script>
<style scoped>
.easyCopy-input{
/*  左边input右边button，长度大概400px，下间距10px*/
  display: flex;
  width:80%;
  margin-bottom: 10px;
}
.easyCopy-input button{
  /*外边距10px*/
  margin-left: 5px;
}
.easyCopy-output{
/*  左边input右边button，宽400px,下间距10px,自适应撑满高度*/
  display: flex;
  width: 80%;
  margin-bottom: 10px;
  height: 100%;

}
.easyCopy-output button{
  /*外边距10px*/
  margin-left: 5px
}
.cover{
  position: relative;
}
.cover:after {
  position: absolute;
  content: '';
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  box-shadow:0 0 5px 2px #ffffff inset;
}
.head_img{
  display: block;
  margin-bottom: 20px;
}

</style>