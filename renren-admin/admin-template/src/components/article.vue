<template>
    <div>
        <div class="select-file-modal" style="display: none;">
            <fileupload  ref="fileupload" @selectFileResult="selectFileResult"></fileupload>
        </div>
        <div v-show="showList">
            <div class="grid-btn">
                <a class="btn btn-primary" @click="add"><i class="fa fa-plus"></i>&nbsp;新增</a>
                <a class="btn btn-primary" @click="update"><i class="fa fa-pencil-square-o"></i>&nbsp;修改</a>
                <a class="btn btn-primary" @click="del"><i class="fa fa-trash-o"></i>&nbsp;删除</a>
            </div>
            <table id="jqGrid"></table>
            <div id="jqGridPager"></div>
        </div>
       <div v-show="!showList" class="panel panel-default">
        <div class="panel-heading">{{title}}</div>
        <form class="form-horizontal" >
          <div class="form-group">
            <div class="col-sm-2 control-label">缩略图:</div>
            <div class="col-sm-10">
              <div class="image-crop">
                <img :src="article.thumbnail" style="width:200px;height:150px;margin-bottom:20px;" >
              </div>
              <button class="btn btn-primary" @click="openFileSelected">上传缩略图</button>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-2 control-label">标题:</div>
            <div class="col-sm-10">
              <input type="text" class="form-control" v-model="article.articleTitle" placeholder="标题"/>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-2 control-label">分类:</div>
            <div class="col-sm-10">
              <!--<input type="text" class="form-control" v-model="article.articleCategoryId" placeholder="分类"/>-->
              <select class="form-control" v-model="article.articleCategoryId">
                <option v-for="(item,index) in articleCategoryList" :key="index" :value="item.id">{{item.categoryName}}</option>
              </select>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-2 control-label">日期:</div>
            <div class="col-sm-10">
              <input type="text" placeholder="请选择日期" class="form-control" id="date">
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-2 control-label">描述:</div>
            <div class="col-sm-10">
              <textarea type="text" style="resize:none;" class="form-control" v-model="article.articleDesc" placeholder="描述"></textarea>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-2 control-label"></div>
            <div class="col-sm-10">
              <div class="checkbox">
                <label>
                  <input type="checkbox" v-model="article.status"> 发布
                </label>
              </div>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-2 control-label"></div>
            <div class="col-sm-10">
              <div class="checkbox">
                <label>
                  <input type="checkbox" v-model="article.isTop"> 是否置顶推荐
                </label>
              </div>
            </div>
          </div>
          <editor style="margin:20px 80px 40px;width:900px;"></editor>
          <div class="form-group">
            <div class="col-sm-2 control-label"></div>
            <input type="button" class="btn btn-primary" @click="saveOrUpdate" value="确定"/>
            &nbsp;&nbsp;<input type="button" class="btn btn-warning" @click="reload" value="返回"/>
          </div>
        </form>
      </div>
    </div>
</template>
<script>
    import {dateFormat} from './../utils/index';
    import Editor from './Editor.vue';
    import fileupload from './fileupload.vue';
    export default{
        components:{
          Editor,fileupload
        },
        data(){
            return{
                showList: true,
                title: null,
                articleCategoryList:[],
                article:{
                    id:null,
                    thumbnail:"",
                    articleTitle:"",
                    articleDesc:0,
                    articleCategoryId:1,
                    articalBody:"",
                    status:0,
                    isTop:0,
                    createdAt:dateFormat("yyyy-MM-dd",new Date)
                },
                layerIndex:null

            }
        },
        created(){
          $.ajax({
            url: baseURL + "cms/articlecategory/list",
            contentType: "application/json",
            success:(r)=>{
              console.log(r);
              this.articleCategoryList = r.page.list;
            }
          });
        },
        mounted(){
          $(()=>{
            $("#jqGrid").jqGrid({
              url: baseURL + 'cms/article/list',
              datatype: "json",
              colModel: [
                { label: 'ID', name: 'id', width: 30, key: true },
                { label: '文章标题', name: 'articleTitle', sortable: false, width: 60 },
                { label: '文章类别', name: 'articleCategoryId', sortable: false, width: 60},
                { label: '是否发布', name: 'status', sortable: false, width: 60 ,formatter:function(cellValue,options,rowdata,action){
                    return cellValue?"是":"否";
                }},
                { label: '创建时间', name: 'createdAt', width: 100 },
//                { label: '更新时间', name: 'updatedAt', width: 80 }
              ],
              viewrecords: true,
              height: 385,
              rowNum: 10,
              rowList : [10,30,50],
              rownumbers: true,
              rownumWidth: 25,
              autowidth:true,
              multiselect: true,
              pager: "#jqGridPager",
              jsonReader : {
                root: "page.list",
                page: "page.currPage",
                total: "page.totalPage",
                records: "page.totalCount"
              },
              prmNames : {
                page:"page",
                rows:"limit",
                order: "order"
              },
              gridComplete:function(){
                //隐藏grid底部滚动条
                $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
              }
            });
          });
          window.getArticleId  = function () {
            var selected = $('#menuTable').bootstrapTreeTable('getSelections');
            if (selected.length == 0) {
              alert("请选择一条记录");
              return null;
            } else {
              return selected[0].id;
            }
          }
        },
        updated(){
          layui.use(['laydate'],()=>{
            window.laydate = layui.laydate;
            window.laydate.render({
              elem:"#date",
              value:this.article.createdAt,
              type:"date",
              done:(value,date,endDate)=>{
                this.article.createdAt = value;
              }
            })
          })
        },
        methods: {
            selectFileResult(data){
              this.article.thumbnail = data.url;
              layer.close(this.layerIndex);
            },
            openFileSelected(e){
              this.layerIndex = layer.open({
                type: 1,
                skin:'select-file-modal',
                title: "选择图片",
                area: ['850px', '600px'],
//                shadeClose: true,
                content: $(".select-file-modal"),
                btn: ['确定', '取消'],
                btn1:function(index){
                    console.log(index)
                }
              });
            },
            thumbnailChange(e){
              console.log(e);
              this.article.thumbnail = e.target.files;
            },
            saveOrUpdate(event){
              this.article.status?this.article.status=1:this.article.status=0;
              this.article.isTop?this.article.isTop=1:this.article.isTop=0;
              this.article.articalBody = window.editor.txt.html();
              this.article.createdAt = new Date(this.article.createdAt).getTime();
              var url = this.article.id == null ? "cms/article/save" : "cms/article/update";
              $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(this.article),
                success:(r)=>{
                  if(r.code === 0){
                    alert('操作成功',(index)=>{
                      this.reload();
                    });
                  }else{
                    alert(r.msg);
                  }
                }
              });
            },
            add(){
                this.showList = false;
                this.title = "新增";
            },
            update() {
              var id = getSelectedRow();
              if(id == null){
                return ;
              }

              $.get(baseURL + "cms/article/info/"+id,(r)=>{
                  this.article.id = r.article.id;
                  this.article.articleTitle = r.article.articleTitle;
                  this.article.articleDesc = r.article.articleDesc;
                  this.article.articleCategoryId = r.article.articleCategoryId;
                  this.article.thumbnail = r.article.thumbnail;
                  this.article.isTop = r.article.isTop;
                  this.article.articalBody = r.article.articalBody;
                  window.editor.txt.html(r.article.articalBody);
                  this.article.status = r.article.status;
                  if(r.article.createdAt){
                    this.article.createdAt = dateFormat("yyyy-MM-dd",new Date(r.article.createdAt));
                  }else{
                    this.article.createdAt = dateFormat("yyyy-MM-dd",new Date());
                  }

                  this.showList = false;
                  this.title = "修改";
              });
            },
            del() {
              var ids = getSelectedRows();
              if(ids == null){
                return ;
              }
              confirm('确定要删除选中的记录？',()=>{
                $.ajax({
                  type: "POST",
                  url: baseURL + "cms/article/delete",
                  contentType: "application/json",
                  data: JSON.stringify(ids),
                  success:(r)=>{
                    if(r.code == 0){
                      alert('操作成功',(index)=>{
                        this.reload();
                      });
                    }else{
                      alert(r.msg);
                    }
                  }
                });
              });
            },
            reload: function (event) {
              this.showList = true;
              this.article = {
                    articleTitle:"",
                    articleCategoryId:0,
                    articleDesc:"",
                    isTop:0,
                    status:0,
                    thumbnail:"",
                    articalBody:"",
                    createdAt:dateFormat("yyyy-MM-dd",new Date())
              };
              window.editor.txt.clear()
              var page = $("#jqGrid").jqGrid('getGridParam','page');
              $("#jqGrid").jqGrid('setGridParam',{
                page:page
              }).trigger("reloadGrid");
            }
        }
    }
</script>
<style lang="less">
  .select-file-modal{
     padding:20px;
  }
  .layui-layer-shade{
    display: none;
  }
</style>
