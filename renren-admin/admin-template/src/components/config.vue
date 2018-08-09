<template>
  <div>
    <div v-show="showList">
      <div class="grid-btn">
        <div class="form-group col-sm-2">
          <input type="text" class="form-control" v-model="q.paramKey" @keyup.enter="query" placeholder="参数名">
        </div>
        <a class="btn btn-default" @click="query">查询</a>

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
          <div class="col-sm-2 control-label">参数名</div>
          <div class="col-sm-10">
            <input type="text" class="form-control" v-model="config.paramKey" placeholder="参数名"/>
          </div>
        </div>
        <div class="form-group">
          <div class="col-sm-2 control-label">参数值</div>
          <div class="col-sm-10">
            <input type="text" class="form-control" v-model="config.paramValue" placeholder="参数值"/>
          </div>
        </div>
        <div class="form-group">
          <div class="col-sm-2 control-label">备注</div>
          <div class="col-sm-10">
            <input type="text" class="form-control" v-model="config.remark" placeholder="备注"/>
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
          q:{
            paramKey: null
          },
          showList: true,
          title: null,
          config: {}
      }
    },
    mounted(){
      $(function () {
        $("#jqGrid").jqGrid({
          url: baseURL + 'sys/config/list',
          datatype: "json",
          colModel: [
            { label: 'ID', name: 'id', width: 30, key: true },
            { label: '参数名', name: 'paramKey', sortable: false, width: 60 },
            { label: '参数值', name: 'paramValue', width: 100 },
            { label: '备注', name: 'remark', width: 80 }
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
    },
    methods: {
      query() {
        this.reload();
      },
      add(){
        this.showList = false;
        this.title = "新增";
        this.config = {};
      },
      update() {
        var id = getSelectedRow();
        if(id == null){
          return ;
        }

        $.get(baseURL + "sys/config/info/"+id,(r)=>{
          this.showList = false;
          this.title = "修改";
          this.config = r.config;
        });
      },
      del(event){
        var ids = getSelectedRows();
        if(ids == null){
          return ;
        }

        confirm('确定要删除选中的记录？',()=>{
          $.ajax({
            type: "POST",
            url: baseURL + "sys/config/delete",
            contentType: "application/json",
            data: JSON.stringify(ids),
            success: function(r){
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
      saveOrUpdate: function (event) {
        var url = this.config.id == null ? "sys/config/save" : "sys/config/update";
        $.ajax({
          type: "POST",
          url: baseURL + url,
          contentType: "application/json",
          data: JSON.stringify(this.config),
          success: function(r){
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
      reload: function (event) {
        this.showList = true;
        var page = $("#jqGrid").jqGrid('getGridParam','page');
        $("#jqGrid").jqGrid('setGridParam',{
          postData:{'paramKey': this.q.paramKey},
          page:page
        }).trigger("reloadGrid");
      }
    }
  }
</script>
<style></style>
