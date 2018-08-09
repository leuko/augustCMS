<template>
  <div>
    <div v-show="showList">
      <div class="grid-btn">
        <div class="form-group col-sm-2">
          <input type="text" class="form-control" v-model="q.username" @keyup.enter="query" placeholder="用户名">
        </div>
        <a class="btn btn-default" @click="query">查询</a>
        <!--<#if shiro.hasPermission("sys:user:save")>-->
          <a class="btn btn-primary" @click="add"><i class="fa fa-plus"></i>&nbsp;新增</a>
        <!--</#if>-->
        <!--<#if shiro.hasPermission("sys:user:update")>-->
          <a class="btn btn-primary" @click="update"><i class="fa fa-pencil-square-o"></i>&nbsp;修改</a>
        <!--</#if>-->
        <!--<#if shiro.hasPermission("sys:user:delete")>-->
          <a class="btn btn-primary" @click="del"><i class="fa fa-trash-o"></i>&nbsp;删除</a>
        <!--</#if>-->
      </div>
      <table id="jqGrid"></table>
      <div id="jqGridPager"></div>
    </div>
    <div v-show="!showList" class="panel panel-default">
      <div class="panel-heading">{{title}}</div>
      <form class="form-horizontal">
        <div class="form-group">
          <div class="col-sm-2 control-label">用户名</div>
          <div class="col-sm-10">
            <input type="text" class="form-control" v-model="user.username" placeholder="登录账号"/>
          </div>
        </div>
        <div class="form-group">
          <div class="col-sm-2 control-label">所属部门</div>
          <div class="col-sm-10">
            <input type="text" class="form-control" style="cursor:pointer;" v-model="user.deptName" @click="deptTree" readonly="readonly" placeholder="所属部门"/>
          </div>
        </div>
        <div class="form-group">
          <div class="col-sm-2 control-label">密码</div>
          <div class="col-sm-10">
            <input type="text" class="form-control" v-model="user.password" placeholder="密码"/>
          </div>
        </div>
        <div class="form-group">
          <div class="col-sm-2 control-label">邮箱</div>
          <div class="col-sm-10">
            <input type="text" class="form-control" v-model="user.email" placeholder="邮箱"/>
          </div>
        </div>
        <div class="form-group">
          <div class="col-sm-2 control-label">手机号</div>
          <div class="col-sm-10">
            <input type="text" class="form-control" v-model="user.mobile" placeholder="手机号"/>
          </div>
        </div>
        <div class="form-group">
          <div class="col-sm-2 control-label">角色</div>
          <div class="col-sm-10">
            <label v-for="role in roleList" class="checkbox-inline">
              <input type="checkbox" :value="role.roleId" v-model="user.roleIdList">{{role.roleName}}
            </label>
          </div>
        </div>
        <div class="form-group">
          <div class="col-sm-2 control-label">状态</div>
          <label class="radio-inline">
            <input type="radio" name="status" value="0" v-model="user.status"/> 禁用
          </label>
          <label class="radio-inline">
            <input type="radio" name="status" value="1" v-model="user.status"/> 正常
          </label>
        </div>
        <div class="form-group">
          <div class="col-sm-2 control-label"></div>
          <input type="button" class="btn btn-primary" @click="saveOrUpdate" value="确定"/>
          &nbsp;&nbsp;<input type="button" class="btn btn-warning" @click="reload" value="返回"/>
        </div>
      </form>
    </div>
    <!-- 选择部门 -->
    <div id="deptLayer" style="display: none;padding:10px;">
      <ul id="deptTree" class="ztree"></ul>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      q:{
        username: null
      },
      showList: true,
      title:null,
      roleList:{},
      user:{
        status:1,
        deptId:null,
        deptName:null,
        roleIdList:[]
      }
    }
  },
  methods:{
    query() {
      this.reload();
    },
    add(){
      this.showList = false;
      this.title = "新增";
      this.roleList = {};
      this.user = {deptName:null, deptId:null, status:1, roleIdList:[]};

      //获取角色信息
      this.getRoleList();

      this.getDept();
    },
    getDept(){
      //加载部门树
      $.get(baseURL + "sys/dept/list", function(r){
        ztree = $.fn.zTree.init($("#deptTree"), setting, r);
        var node = ztree.getNodeByParam("deptId", this.user.deptId);
        if(node != null){
          ztree.selectNode(node);

          this.user.deptName = node.name;
        }
      })
    },
    update() {
      var userId = getSelectedRow();
      if(userId == null){
        return ;
      }

      this.showList = false;
      this.title = "修改";

      this.getUser(userId);
      //获取角色信息
      this.getRoleList();
    },
    del() {
      var userIds = getSelectedRows();
      if(userIds == null){
        return ;
      }

      confirm('确定要删除选中的记录？', function(){
        $.ajax({
          type: "POST",
          url: baseURL + "sys/user/delete",
          contentType: "application/json",
          data: JSON.stringify(userIds),
          success: function(r){
            if(r.code == 0){
              alert('操作成功', function(){
                this.reload();
              });
            }else{
              alert(r.msg);
            }
          }
        });
      });
    },
    saveOrUpdate() {
      var url = this.user.userId == null ? "sys/user/save" : "sys/user/update";
      $.ajax({
        type: "POST",
        url: baseURL + url,
        contentType: "application/json",
        data: JSON.stringify(this.user),
        success: function(r){
          if(r.code === 0){
            alert('操作成功', function(){
              this.reload();
            });
          }else{
            alert(r.msg);
          }
        }
      });
    },
    getUser(userId){
      $.get(baseURL + "sys/user/info/"+userId, function(r){
        this.user = r.user;
        this.user.password = null;

        this.getDept();
      });
    },
    getRoleList(){
      $.get(baseURL + "sys/role/select", function(r){
        this.roleList = r.list;
      });
    },
    deptTree(){
      layer.open({
        type: 1,
        offset: '50px',
        skin: 'layui-layer-molv',
        title: "选择部门",
        area: ['300px', '450px'],
        shade: 0,
        shadeClose: false,
        content: jQuery("#deptLayer"),
        btn: ['确定', '取消'],
        btn1: function (index) {
          var node = ztree.getSelectedNodes();
          //选择上级部门
          this.user.deptId = node[0].deptId;
          this.user.deptName = node[0].name;

          layer.close(index);
        }
      });
    },
    reload() {
      this.showList = true;
      var page = $("#jqGrid").jqGrid('getGridParam','page');
      $("#jqGrid").jqGrid('setGridParam',{
        postData:{'username': this.q.username},
        page:page
      }).trigger("reloadGrid");
    }
  },
  mounted(){
    $(function () {
      window.ztree = null;
      $("#jqGrid").jqGrid({
        url: baseURL + 'sys/user/list',
        datatype: "json",
        colModel: [
          { label: '用户ID', name: 'userId', index: "user_id", width: 45, key: true },
          { label: '用户名', name: 'username', width: 75 },
          { label: '所属部门', name: 'deptName', sortable: false, width: 75 },
          { label: '邮箱', name: 'email', width: 90 },
          { label: '手机号', name: 'mobile', width: 100 },
          { label: '状态', name: 'status', width: 60, formatter: function(value, options, row){
            return value === 0 ?
              '<span class="label label-danger">禁用</span>' :
              '<span class="label label-success">正常</span>';
          }},
          { label: '创建时间', name: 'createTime', index: "create_time", width: 85}
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
    window.setting = {
      data: {
        simpleData: {
          enable: true,
          idKey: "deptId",
          pIdKey: "parentId",
          rootPId: -1
        },
        key: {
          url:"nourl"
        }
      }
    };
  }
}
</script>
