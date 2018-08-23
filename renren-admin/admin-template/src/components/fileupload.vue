<template>
   <div class="fileupload" >
     <a href="javascript:void(0)" id="upload" class="btn btn-primary">上传图片</a>
     <div class="img-content">
         <div class="img-item" v-for="item in fileList" >
           <img :src="item.url" alt="" @click="selected($event,item)">
           <input type="checkbox" :checked="item.checked" style="position:absolute;bottom:2px;right:2px;">
         </div>
     </div>
     <div id="pages"></div>
   </div>
</template>
<script>
  export default {
      props:{
         multi:{
            type:Boolean,
            default:false
         }
      },
      data(){
        return{
            fileList:[],
            currentPage:1,
            totalPage:1,
            totalCount:1,
            selectedList:[]
        }
      },
      methods:{
        selected(e,data){
          e.preventDefault();
          if(!this.multi){
              console.info("选择一张")
              this.$emit("selectFileResult",data)
              this.reload();
          }else{
              console.info("多张");
              data.checked = !data.checked;
              this.selectedList.push(data);
          }
        },
        reload(){
          this.currentPage = 1;
          this.getImgList(this.currentPage);
        },
        getImgList(currentPage){
          $.ajax({
            url: baseURL + 'sys/oss/list',
            type:"get",
            datatype: "json",
            data:{
              limit:10,
              page:currentPage
            },
            success:(res)=>{
              res.page.list.map((item,index)=>{
                item.checked = false;
              })
              this.fileList = res.page.list;
              this.currentPage = res.page.currPage;
              this.totalPage = res.page.totalPage;
              this.totalCount = res.page.totalCount;
            }
          });
        }
      },
      created(){

      },
      updated(){
        if(!this.totalCount){
            return;
        }
        layui.use(["laypage"],()=>{
          window.laypage = layui.laypage;
          window.laypage.render({
            elem:"pages",
            theme:"#ccc",
            count:this.totalCount,
            jump:(obj, first)=>{
              //obj包含了当前分页的所有参数，比如：
              console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
              console.log(obj.limit); //得到每页显示的条数
              //首次不执行
              if(!first){
                //do something
                this.getImgList(obj.curr)
              }
            }
          })
        })
      },
      mounted(){
          $.ajax({
            url: baseURL + 'sys/oss/list',
            type:"get",
            datatype: "json",
            data:{
              limit:10,
              page:this.currentPage++
            },
            success:(res)=>{
              res.page.list.map((item,index)=>{
                  item.checked = false;
              })
              this.fileList = res.page.list;
              this.currentPage = res.page.currPage;
              this.totalPage = res.page.totalPage;
              this.totalCount = res.page.totalCount;
            }
          });

        new AjaxUpload('#upload', {
          action: baseURL + "sys/oss/upload",
          name: 'file',
          autoSubmit:true,
          responseType:"json",
          onSubmit:(file, extension)=>{
            if (!(extension && /^(jpg|jpeg|png|gif)$/.test(extension.toLowerCase()))){
              alert('只支持jpg、png、gif格式的图片！');
              return false;
            }
          },
          onComplete :(file, r)=>{
            if(r.code == 0){
              alert(r.url);
              this.reload();
            }else{
              alert(r.msg);
            }
          }
        });
      }
  }

</script>
<style lang="less">
  .fileupload{
     a{
       margin-left:15px;
     }
     .img-content{
       margin-top:20px;
       display: flex;
       flex-direction: row;
       justify-content:flex-start;
       align-items: center;
       flex-wrap:wrap;
       div.img-item{
         position:relative;
         width:100px;
         height:100px;
         margin:15px;
         img{
           width:100%;
           height:100%;
         }
       }
     }
  }
</style>
