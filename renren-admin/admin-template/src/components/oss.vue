<template>
  <div>
    <div v-show="showList">
      <div class="grid-btn">
        <a class="btn btn-danger" @click="addConfig"><i class="fa fa-sun-o"></i>&nbsp;云存储配置</a>
        <a class="btn btn-primary" id="upload"><i class="fa fa-plus"></i>&nbsp;上传文件</a>
        <a class="btn btn-primary" @click="del"><i class="fa fa-trash-o"></i>&nbsp;删除</a>
      </div>
      <table id="jqGrid"></table>
      <div id="jqGridPager"></div>
    </div>
    <div v-show="!showList" class="panel panel-default">
      <div class="panel-heading">{{title}}</div>
      <form class="form-horizontal">
        <div class="form-group">
          <div class="col-sm-2 control-label">存储类型</div>
          <label class="radio-inline">
            <input type="radio" name="type" v-model="config.type" value="1"/> 七牛
          </label>
          <label class="radio-inline">
            <input type="radio" name="type" v-model="config.type" value="2"/> 阿里云
          </label>
          <label class="radio-inline">
            <input type="radio" name="type" v-model="config.type" value="3"/> 腾讯云
          </label>
        </div>
        <div v-show="config.type == 1">
          <div class="form-group">
            <div class="col-sm-2 control-label">&nbsp;</div>
            <p class="form-control-static"><a href="http://www.renren.io/open/qiniu.html" target="_blank">免费申请(七牛)10GB储存空间</a></p>
          </div>
          <div class="form-group">
            <div class="col-sm-2 control-label">域名</div>
            <div class="col-sm-10">
              <input type="text" class="form-control" v-model="config.qiniuDomain" placeholder="七牛绑定的域名"/>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-2 control-label">路径前缀</div>
            <div class="col-sm-10">
              <input type="text" class="form-control" v-model="config.qiniuPrefix" placeholder="不设置默认为空"/>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-2 control-label">AccessKey</div>
            <div class="col-sm-10">
              <input type="text" class="form-control" v-model="config.qiniuAccessKey" placeholder="七牛AccessKey"/>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-2 control-label">SecretKey</div>
            <div class="col-sm-10">
              <input type="text" class="form-control" v-model="config.qiniuSecretKey" placeholder="七牛SecretKey"/>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-2 control-label">空间名</div>
            <div class="col-sm-10">
              <input type="text" class="form-control" v-model="config.qiniuBucketName" placeholder="七牛存储空间名"/>
            </div>
          </div>
        </div>
        <div v-show="config.type == 2">
          <div class="form-group">
            <div class="col-sm-2 control-label">域名</div>
            <div class="col-sm-10">
              <input type="text" class="form-control" v-model="config.aliyunDomain" placeholder="阿里云绑定的域名"/>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-2 control-label">路径前缀</div>
            <div class="col-sm-10">
              <input type="text" class="form-control" v-model="config.aliyunPrefix" placeholder="不设置默认为空"/>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-2 control-label">EndPoint</div>
            <div class="col-sm-10">
              <input type="text" class="form-control" v-model="config.aliyunEndPoint" placeholder="阿里云EndPoint"/>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-2 control-label">AccessKeyId</div>
            <div class="col-sm-10">
              <input type="text" class="form-control" v-model="config.aliyunAccessKeyId" placeholder="阿里云AccessKeyId"/>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-2 control-label">AccessKeySecret</div>
            <div class="col-sm-10">
              <input type="text" class="form-control" v-model="config.aliyunAccessKeySecret" placeholder="阿里云AccessKeySecret"/>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-2 control-label">BucketName</div>
            <div class="col-sm-10">
              <input type="text" class="form-control" v-model="config.aliyunBucketName" placeholder="阿里云BucketName"/>
            </div>
          </div>
        </div>
        <div v-show="config.type == 3">
          <div class="form-group">
            <div class="col-sm-2 control-label">域名</div>
            <div class="col-sm-10">
              <input type="text" class="form-control" v-model="config.qcloudDomain" placeholder="腾讯云绑定的域名"/>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-2 control-label">路径前缀</div>
            <div class="col-sm-10">
              <input type="text" class="form-control" v-model="config.qcloudPrefix" placeholder="不设置默认为空"/>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-2 control-label">AppId</div>
            <div class="col-sm-10">
              <input type="text" class="form-control" v-model="config.qcloudAppId" placeholder="腾讯云AppId"/>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-2 control-label">SecretId</div>
            <div class="col-sm-10">
              <input type="text" class="form-control" v-model="config.qcloudSecretId" placeholder="腾讯云SecretId"/>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-2 control-label">SecretKey</div>
            <div class="col-sm-10">
              <input type="text" class="form-control" v-model="config.qcloudSecretKey" placeholder="腾讯云SecretKey"/>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-2 control-label">BucketName</div>
            <div class="col-sm-10">
              <input type="text" class="form-control" v-model="config.qcloudBucketName" placeholder="腾讯云BucketName"/>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-2 control-label">Bucket所属地区</div>
            <div class="col-sm-10">
              <input type="text" class="form-control" v-model="config.qcloudRegion" placeholder="如：sh（可选值 ，华南：gz 华北：tj 华东：sh）"/>
            </div>
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
          config: {}
      }
    },
    created(){
      this.getConfig();
    },
    mounted(){
      $(function () {
        $("#jqGrid").jqGrid({
          url: baseURL + 'sys/oss/list',
          datatype: "json",
          colModel: [
            { label: 'id', name: 'id', width: 20, key: true },
            { label: 'URL地址', name: 'url', width: 160 },
            { label: '创建时间', name: 'createDate', width: 40 }
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

        new AjaxUpload('#upload', {
          action: baseURL + "sys/oss/upload",
          name: 'file',
          autoSubmit:true,
          responseType:"json",
          onSubmit:(file, extension)=>{
            if(this.config.type == null){
              alert("云存储配置未配置");
              return false;
            }
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

      });
    },
    methods: {
      query() {
        $("#jqGrid").jqGrid("setGridParam",{page:1});
        this.reload();
      },
      getConfig() {
        $.getJSON(baseURL + "sys/oss/config",(r)=>{
          this.config = r.config;
        });
      },
      addConfig(){
        this.showList = false;
        this.title = "云存储配置";
      },
      saveOrUpdate() {
        var url = baseURL + "sys/oss/saveConfig";
        $.ajax({
          type: "POST",
          url: url,
          contentType: "application/json",
          data: JSON.stringify(this.config),
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
      },
      del() {
        var ossIds = getSelectedRows();
        if(ossIds == null){
          return ;
        }

        confirm('确定要删除选中的记录？',()=>{
          $.ajax({
            type: "POST",
            url: baseURL + "sys/oss/delete",
            contentType: "application/json",
            data: JSON.stringify(ossIds),
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
      reload() {
        this.showList = true;
        var page = $("#jqGrid").jqGrid('getGridParam','page');
        $("#jqGrid").jqGrid('setGridParam',{
          page:page
        }).trigger("reloadGrid");
      }
    }
  }
</script>
<style></style>
