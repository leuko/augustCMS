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
            roleName: null
          },
          showList: true,
          title:null,
          role:{
            deptId:null,
            deptName:null
          }

      }
    },
    mounted(){
      $(function () {
        $("#jqGrid").jqGrid({
          url: baseURL + 'sys/log/list',
          datatype: "json",
          colModel: [
            { label: 'id', name: 'id', width: 30, key: true },
            { label: '用户名', name: 'username', width: 50 },
            { label: '用户操作', name: 'operation', width: 70 },
            { label: '请求方法', name: 'method', width: 150 },
            { label: '请求参数', name: 'params', width: 80 },
            { label: '执行时长(毫秒)', name: 'time', width: 80 },
            { label: 'IP地址', name: 'ip', width: 70 },
            { label: '创建时间', name: 'createDate', width: 90 }
          ],
          viewrecords: true,
          height: 385,
          rowNum: 10,
          rowList : [10,30,50],
          rownumbers: true,
          rownumWidth: 25,
          autowidth:true,
          multiselect: false,
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
      reload(event) {
        var page = $("#jqGrid").jqGrid('getGridParam','page');
        $("#jqGrid").jqGrid('setGridParam',{
          postData:{'key': this.q.key},
          page:page
        }).trigger("reloadGrid");
      }
    }
  }
</script>
<style></style>
