<template>
    <div>

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
        <form class="form-horizontal">
          <div class="form-group">
            <div class="col-sm-2 control-label">文章类别</div>
            <div class="col-sm-10">
              <input type="text" class="form-control" v-model="articleCategory.categoryName" placeholder="文章类别"/>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-2 control-label">备注</div>
            <div class="col-sm-10">
              <textarea type="text" class="form-control" style="cursor:pointer;resize:none;" v-model="articleCategory.categoryRemark"  placeholder="备注"></textarea>
            </div>
          </div>
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
    export default{
        data(){
            return{
                showList: true,
                title: null,
                articleCategory:{
                    id:null,
                    categoryName:null,
                    categoryRemark:"",
                    createdAt:new Date().getTime()
                }
            }
        },
        mounted(){
        $(function () {
          $("#jqGrid").jqGrid({
            url: baseURL + 'cms/articlecategory/list',
            datatype: "json",
            colModel: [
              { label: 'ID', name: 'id', width: 30, key: true },
              { label: '类别名称', name: 'categoryName', sortable: false, width: 60 },
              { label: '备注', name: 'categoryRemark', sortable: false, width: 60 },
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
        methods: {
            saveOrUpdate(){
              var url = this.articleCategory.id == null ? "cms/articlecategory/save" : "cms/articlecategory/update";
              $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(this.articleCategory),
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
                var ids = getSelectedRows();
                if(ids == null){
                  return ;
                }

                $.get(baseURL + "cms/articlecategory/info/"+ids, (r)=>{
                    this.showList = false;
                    this.title = "修改";
                    this.articleCategory = r.articleCategory;

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
                        url: baseURL + "cms/articlecategory/delete",
                        contentType: "application/json",
                        data: JSON.stringify(ids),
                        success:(r)=>{
                            if(r.code === 0){
                                alert('操作成功',()=>{
                                    this.reload();
                                });
                            }else{
                                alert(r.msg);
                            }
                        }
                    });
                });
            },
            reload(){
              this.showList = true;
              this.articleCategory = {categoryName:null,categoryRemark:""};
              var page = $("#jqGrid").jqGrid('getGridParam','page');
              $("#jqGrid").jqGrid('setGridParam',{
                page:page
              }).trigger("reloadGrid");
            }
        }
    }
</script>
<style></style>
