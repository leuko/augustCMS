<template>
    <div>
      <div v-show="showList">
        <div class="grid-btn">
          <div class="form-group col-sm-2">
            <input type="text" class="form-control" v-model="q.roleName" @keyup.enter="query" placeholder="角色名称">
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
            <div class="col-sm-2 control-label">角色名称</div>
            <div class="col-sm-10">
              <input type="text" class="form-control" v-model="role.roleName" placeholder="角色名称"/>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-2 control-label">所属部门</div>
            <div class="col-sm-10">
              <input type="text" class="form-control" style="cursor:pointer;" v-model="role.deptName" @click="deptTree" readonly="readonly" placeholder="所属部门"/>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-2 control-label">备注</div>
            <div class="col-sm-10">
              <input type="text" class="form-control" v-model="role.remark" placeholder="备注"/>
            </div>
          </div>
          <div class="form-inline clearfix" style="margin-top:30px;margin-left:26px;">
            <div class="form-group col-md-6">
              <strong class="col-sm-5 control-label">功能权限</strong>
              <div class="col-sm-10">
                <ul id="menuTree" class="ztree"></ul>
              </div>
            </div>
            <div class="form-group col-md-6">
              <strong class="col-sm-5 control-label">数据权限</strong>
              <div class="col-sm-10">
                <ul id="dataTree" class="ztree"></ul>
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
      <!-- 选择部门 -->
      <div id="deptLayer" style="display: none;padding:10px;">
        <ul id="deptTree" class="ztree"></ul>
      </div>
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
          url: baseURL + 'sys/role/list',
          datatype: "json",
          colModel: [
            { label: '角色ID', name: 'roleId', index: "role_id", width: 45, key: true },
            { label: '角色名称', name: 'roleName', index: "role_name", width: 75 },
            { label: '所属部门', name: 'deptName', sortable: false, width: 75 },
            { label: '备注', name: 'remark', width: 100 },
            { label: '创建时间', name: 'createTime', index: "create_time", width: 80}
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

//数据树
      window.data_ztree = null;
      window.data_setting = {
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
        },
        check:{
          enable:true,
          nocheckInherit:true,
          chkboxType:{ "Y" : "", "N" : "" }
        }
      };
    },
    methods: {
      query() {
        this.reload();
      },
      add(){
        this.showList = false;
        this.title = "新增";
        this.role = {deptName:null, deptId:null};
        this.getMenuTree(null);

        this.getDept();

        this.getDataTree();
      },
      update() {
        var roleId = getSelectedRow();
        if(roleId == null){
          return ;
        }

        this.showList = false;
        this.title = "修改";
        this.getDataTree();
        this.getMenuTree(roleId);

        this.getDept();
      },
      del() {
        var roleIds = getSelectedRows();
        if(roleIds == null){
          return ;
        }

        confirm('确定要删除选中的记录？',()=>{
          $.ajax({
            type: "POST",
            url: baseURL + "sys/role/delete",
            contentType: "application/json",
            data: JSON.stringify(roleIds),
            success:(r)=>{
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
      getRole(roleId){
        $.get(baseURL + "sys/role/info/"+roleId,(r)=>{
          this.role = r.role;

          //勾选角色所拥有的菜单
          var menuIds = this.role.menuIdList;
          for(var i=0; i<menuIds.length; i++) {
            var node = menu_ztree.getNodeByParam("menuId", menuIds[i]);
            menu_ztree.checkNode(node, true, false);
          }

          //勾选角色所拥有的部门数据权限
          var deptIds = this.role.deptIdList;
          for(var i=0; i<deptIds.length; i++) {
            var node = data_ztree.getNodeByParam("deptId", deptIds[i]);
            data_ztree.checkNode(node, true, false);
          }

          this.getDept();
        });
      },
      saveOrUpdate: function () {
        //获取选择的菜单
        var nodes = menu_ztree.getCheckedNodes(true);
        var menuIdList = new Array();
        for(var i=0; i<nodes.length; i++) {
          menuIdList.push(nodes[i].menuId);
        }
        this.role.menuIdList = menuIdList;

        //获取选择的数据
        var nodes = data_ztree.getCheckedNodes(true);
        var deptIdList = new Array();
        for(var i=0; i<nodes.length; i++) {
          deptIdList.push(nodes[i].deptId);
        }
        this.role.deptIdList = deptIdList;

        var url = this.role.roleId == null ? "sys/role/save" : "sys/role/update";
        $.ajax({
          type: "POST",
          url: baseURL + url,
          contentType: "application/json",
          data: JSON.stringify(this.role),
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
      getMenuTree: function(roleId) {
        //加载菜单树
        $.get(baseURL + "sys/menu/list",(r)=>{
          menu_ztree = $.fn.zTree.init($("#menuTree"), menu_setting, r);
          //展开所有节点
          menu_ztree.expandAll(true);

          if(roleId != null){
            this.getRole(roleId);
          }
        });
      },
      getDataTree(roleId) {
        //加载菜单树
        $.get(baseURL + "sys/dept/list", function(r){
          data_ztree = $.fn.zTree.init($("#dataTree"), data_setting, r);
          //展开所有节点
          data_ztree.expandAll(true);
        });
      },
      getDept(){
        //加载部门树
        $.get(baseURL + "sys/dept/list",(r)=>{
          dept_ztree = $.fn.zTree.init($("#deptTree"), dept_setting, r);
          var node = dept_ztree.getNodeByParam("deptId", this.role.deptId);
          if(node != null){
            dept_ztree.selectNode(node);

            this.role.deptName = node.name;
          }
        })
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
          btn1:(index)=>{
            var node = dept_ztree.getSelectedNodes();
            //选择上级部门
            this.role.deptId = node[0].deptId;
            this.role.deptName = node[0].name;

            layer.close(index);
          }
        });
      },
      reload() {
        this.showList = true;
        var page = $("#jqGrid").jqGrid('getGridParam','page');
        $("#jqGrid").jqGrid('setGridParam',{
          postData:{'roleName': this.q.roleName},
          page:page
        }).trigger("reloadGrid");
      }
    }
  }
</script>
<style></style>
