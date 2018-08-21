<template>
  <div>
    <div ref="editor" style="text-align:left"></div>
    <!--<button @click="getContent">查看内容</button>-->
  </div>
</template>

<script>
  import E from 'wangeditor'
  export default {
    name: 'editor',
    data () {
      return {
        editorContent: ''
      }
    },
    methods: {
      getContent: function () {
        alert(this.editorContent)
      }
    },
    mounted() {
      window.editor = new E(this.$refs.editor)
      // 配置服务器端地址
      editor.customConfig.uploadImgServer = '/upload'
      editor.customConfig.onchange = (html) => {
        this.editorContent = html
      }
      editor.create()
    }
  }
</script>

<style scoped>
</style>
