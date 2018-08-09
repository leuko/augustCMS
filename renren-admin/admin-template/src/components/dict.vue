<template>
  <div>
    <div class="grid-btn">
      <div class="form-group col-sm-2">
        <input type="text" class="form-control" v-model="q.key" @keyup.enter="query" placeholder="用户名、用户操作">
      </div>
      <a class="btn btn-default" @click="query">查询</a>
    </div>
    <table id="jqGrid"></table>
    <div id="jqGridPager"></div>
  </div>
</template>
<script>
  export default{
    data(){
        return{
          q:{
            name: null
          },
          showList: true,
          title: null,
          dict: {}

      }
    },
    mounted(){
      $(function () {
        $("#jqGrid").jqGrid({
          url: baseURL + 'sys/dict/list',
          datatype: "json",
          colModel: [
            { label: '字典名称', name: 'name', index: 'name', width: 80 },
            { label: '字典类型', name: 'type', index: 'type', width: 80 },
            { label: '字典码', name: 'code', index: 'code', width: 80 },
            { label: '字典值', name: 'value', index: 'value', width: 80 },
            { label: '排序', name: 'orderNum', index: 'order_num', width: 80 },
            { label: '备注', name: 'remark', index: 'remark', width: 80 }
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
        this.dict = {};
      },
      update(event) {
        var id = getSelectedRow();
        if(id == null){
          return ;
        }
        this.showList = false;
        this.title = "修改";

        this.getInfo(id)
      },
      saveOrUpdate(event) {
        var url = this.dict.id == null ? "sys/dict/save" : "sys/dict/update";
        $.ajax({
          type: "POST",
          url: baseURL + url,
          contentType: "application/json",
          data: JSON.stringify(this.dict),
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
      del(event) {
        var ids = getSelectedRows();
        if(ids == null){
          return ;
        }

        confirm('确定要删除选中的记录？', function(){
          $.ajax({
            type: "POST",
            url: baseURL + "sys/dict/delete",
            contentType: "application/json",
            data: JSON.stringify(ids),
            success: function(r){
              if(r.code == 0){
                alert('操作成功', function(index){
                  $("#jqGrid").trigger("reloadGrid");
                });
              }else{
                alert(r.msg);
              }
            }
          });
        });
      },
      getInfo(id){
        $.get(baseURL + "sys/dict/info/"+id,(r)=>{
          this.dict = r.dict;
        });
      },
      reload: function (event) {
        this.showList = true;
        var page = $("#jqGrid").jqGrid('getGridParam','page');
        $("#jqGrid").jqGrid('setGridParam',{
          postData:{'name': this.q.name},
          page:page
        }).trigger("reloadGrid");
      }
    }
  }
</script>
<style></style>
