<template>
  <div>
    <div class="grid-btn">
      <div class="form-group col-sm-2">
        <input type="text" class="form-control" v-model="q.jobId" @keyup.enter="query" placeholder="任务ID">
      </div>
      <a class="btn btn-default" @click="query">查询</a>
      &nbsp;&nbsp;<input type="button" class="btn btn-warning" @click="back" value="返回"/>
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
            jobId: null
          }
      }
    },
    mounted(){
      $(()=>{
        $("#jqGrid").jqGrid({
          url: baseURL + 'sys/scheduleLog/list',
          datatype: "json",
          colModel: [
            { label: '日志ID', name: 'logId', width: 50, key: true },
            { label: '任务ID', name: 'jobId', width: 50},
            { label: 'bean名称', name: 'beanName', width: 60 },
            { label: '方法名称', name: 'methodName', width: 60 },
            { label: '参数', name: 'params', width: 60 },
            { label: '状态', name: 'status', width: 50, formatter:(value, options, row)=>{
              return value === 0 ?
                '<span class="label label-success">成功</span>' :
              '<span class="label label-danger pointer" onclick="this.showError('+row.logId+')">失败</span>';
            }},
            { label: '耗时(单位：毫秒)', name: 'times', width: 70 },
            { label: '执行时间', name: 'createTime', width: 80 }
          ],
          viewrecords: true,
          height: 385,
          rowNum: 10,
          rowList : [10,30,50,100,200],
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
      query(){
        $("#jqGrid").jqGrid('setGridParam',{
          postData:{'jobId': this.q.jobId},
          page:1
        }).trigger("reloadGrid");
      },
      showError(logId) {
        $.get(baseURL + "sys/scheduleLog/info/"+logId,(r)=>{
          parent.layer.open({
            title:'失败信息',
            closeBtn:0,
            content: r.log.error
          });
        });
      },
      back(event) {
        history.go(-1);
      }
    }
  }
</script>
<style></style>
