<template>
  <div>
    <div v-show="showList">
      <div class="grid-btn" style="height:34px;">
        <div class="form-group col-sm-2">
          <input type="text" class="form-control" v-model="q.beanName" @keyup.enter="query" placeholder="bean名称">
        </div>
        <a class="btn btn-default" @click="query">查询</a>

          <a class="btn btn-primary" @click="add"><i class="fa fa-plus"></i>&nbsp;新增</a>
          <a class="btn btn-primary" @click="update"><i class="fa fa-pencil-square-o"></i>&nbsp;修改</a>
          <a class="btn btn-primary" @click="del"><i class="fa fa-trash-o"></i>&nbsp;删除</a>
          <a class="btn btn-primary" @click="pause"><i class="fa fa-pause"></i>&nbsp;暂停</a>
          <a class="btn btn-primary" @click="resume"><i class="fa fa-play"></i>&nbsp;恢复</a>
          <a class="btn btn-primary" @click="runOnce"><i class="fa fa-arrow-circle-right"></i>&nbsp;立即执行</a>
          <a class="btn btn-danger" style="float:right;" href="schedule_log.html">日志列表</a>
      </div>
      <table id="jqGrid"></table>
      <div id="jqGridPager"></div>
    </div>

    <div v-show="!showList" class="panel panel-default">
      <div class="panel-heading">{{title}}</div>
      <form class="form-horizontal">
        <div class="form-group">
          <div class="col-sm-2 control-label">bean名称</div>
          <div class="col-sm-10">
            <input type="text" class="form-control" v-model="schedule.beanName" placeholder="spring bean名称，如：testTask"/>
          </div>
        </div>
        <div class="form-group">
          <div class="col-sm-2 control-label">方法名称</div>
          <div class="col-sm-10">
            <input type="text" class="form-control" v-model="schedule.methodName" placeholder="方法名称"/>
          </div>
        </div>
        <div class="form-group">
          <div class="col-sm-2 control-label">参数</div>
          <div class="col-sm-10">
            <input type="text" class="form-control" v-model="schedule.params" placeholder="参数"/>
          </div>
        </div>
        <div class="form-group">
          <div class="col-sm-2 control-label">cron表达式</div>
          <div class="col-sm-10">
            <input type="text" class="form-control" v-model="schedule.cronExpression" placeholder="如：0 0 12 * * ?"/>
          </div>
        </div>
        <div class="form-group">
          <div class="col-sm-2 control-label">备注</div>
          <div class="col-sm-10">
            <input type="text" class="form-control" v-model="schedule.remark" placeholder="备注"/>
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
            beanName: null
          },
          showList: true,
          title: null,
          schedule: {}
      }
    },
    mounted(){
      $(function () {
        $("#jqGrid").jqGrid({
          url: baseURL + 'sys/schedule/list',
          datatype: "json",
          colModel: [
            { label: '任务ID', name: 'jobId', width: 60, key: true },
            { label: 'bean名称', name: 'beanName', width: 100 },
            { label: '方法名称', name: 'methodName', width: 100 },
            { label: '参数', name: 'params', width: 100 },
            { label: 'cron表达式 ', name: 'cronExpression', width: 100 },
            { label: '备注 ', name: 'remark', width: 100 },
            { label: '状态', name: 'status', width: 60, formatter: function(value, options, row){
              return value === 0 ?
                '<span class="label label-success">正常</span>' :
                '<span class="label label-danger">暂停</span>';
            }}
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
      query: function () {
        this.reload();
      },
      add: function(){
        this.showList = false;
        this.title = "新增";
        this.schedule = {};
      },
      update: function () {
        var jobId = getSelectedRow();
        if(jobId == null){
          return ;
        }

        $.get(baseURL + "sys/schedule/info/"+jobId, function(r){
          this.showList = false;
          this.title = "修改";
          this.schedule = r.schedule;
        });
      },
      saveOrUpdate: function (event) {
        var url = this.schedule.jobId == null ? "sys/schedule/save" : "sys/schedule/update";
        $.ajax({
          type: "POST",
          url: baseURL + url,
          contentType: "application/json",
          data: JSON.stringify(this.schedule),
          success: function(r){
            if(r.code === 0){
              alert('操作成功', function(index){
                this.reload();
              });
            }else{
              alert(r.msg);
            }
          }
        });
      },
      del: function (event) {
        var jobIds = getSelectedRows();
        if(jobIds == null){
          return ;
        }

        confirm('确定要删除选中的记录？', function(){
          $.ajax({
            type: "POST",
            url: baseURL + "sys/schedule/delete",
            contentType: "application/json",
            data: JSON.stringify(jobIds),
            success: function(r){
              if(r.code == 0){
                alert('操作成功', function(index){
                  this.reload();
                });
              }else{
                alert(r.msg);
              }
            }
          });
        });
      },
      pause: function (event) {
        var jobIds = getSelectedRows();
        if(jobIds == null){
          return ;
        }

        confirm('确定要暂停选中的记录？', function(){
          $.ajax({
            type: "POST",
            url: baseURL + "sys/schedule/pause",
            contentType: "application/json",
            data: JSON.stringify(jobIds),
            success: function(r){
              if(r.code == 0){
                alert('操作成功', function(index){
                  this.reload();
                });
              }else{
                alert(r.msg);
              }
            }
          });
        });
      },
      resume: function (event) {
        var jobIds = getSelectedRows();
        if(jobIds == null){
          return ;
        }

        confirm('确定要恢复选中的记录？', function(){
          $.ajax({
            type: "POST",
            url: baseURL + "sys/schedule/resume",
            contentType: "application/json",
            data: JSON.stringify(jobIds),
            success: function(r){
              if(r.code == 0){
                alert('操作成功', function(index){
                  this.reload();
                });
              }else{
                alert(r.msg);
              }
            }
          });
        });
      },
      runOnce: function (event) {
        var jobIds = getSelectedRows();
        if(jobIds == null){
          return ;
        }

        confirm('确定要立即执行选中的记录？', function(){
          $.ajax({
            type: "POST",
            url: baseURL + "sys/schedule/run",
            contentType: "application/json",
            data: JSON.stringify(jobIds),
            success: function(r){
              if(r.code == 0){
                alert('操作成功', function(index){
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
        var page = $("#jqGrid").jqGrid('getGridParam','page');
        $("#jqGrid").jqGrid('setGridParam',{
          postData:{'beanName': this.q.beanName},
          page:page
        }).trigger("reloadGrid");
      }
    }
  }
</script>
<style></style>
