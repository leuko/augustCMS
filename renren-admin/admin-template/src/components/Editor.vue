<template>
  <div>
    <div ref="editor" style="text-align:left"></div>
    <div class="editor-img-modal" style="display: none;">
      <fileupload :multi="true" ref="fileupload"  @selectFileResult="selectFileResult"></fileupload>
    </div>
  </div>
</template>

<script>
  import E from 'wangeditor'
  import fileupload from './fileupload.vue';
  export default {
    name: 'editor',
    components:{
      fileupload
    },
    data () {
      return {
        layerIndex:null
      }
    },
    methods: {
      selectFileResult(data){
        console.log(data)
        //this.article.thumbnail = data.url;
        data.map((item)=>{
          window.editor.cmd.do('insertHtml', '<img src="' + item.url + '" style="max-width:100%;"/>')
        });
        window.layer.close(this.layerIndex);
      },
      uploadInit(){
        var btnId = editor.imgMenuId;
        console.log(btnId)
        var containerId = editor.toolbarElemId;
        var textElemId = editor.textElemId;
        $("#"+btnId).on("click",this.openFileSelected.bind(this))
      },
      openFileSelected(){
        this.layerIndex = window.layer.open({
          type: 1,
          skin:'select-file-modal',
          title: "选择图片",
          area: ['850px', '600px'],
//                shadeClose: true,
          content: $(".editor-img-modal"),
          btn: ['确定', '取消'],
          btn1: function(index){
            this.$emit("selectFileResult",this.selectedList)
            this.selectedList = [];
            this.reload();
          }.bind(this.$refs.fileupload)
        });
      }

    },
    mounted() {
      window.editor = new E(this.$refs.editor)
      // 配置服务器端地址
      editor.create();
      this.uploadInit();
    }
  }
</script>

<style>
</style>
