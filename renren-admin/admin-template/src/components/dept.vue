<template>
    <div>
      <div v-show="showList">
        <div class="grid-btn">
            <a class="btn btn-primary" @click="add"><i class="fa fa-plus"></i>&nbsp;新增</a>
            <a class="btn btn-primary" @click="update"><i class="fa fa-pencil-square-o"></i>&nbsp;修改</a>
            <a class="btn btn-primary" @click="del"><i class="fa fa-trash-o"></i>&nbsp;删除</a>
        </div>
        <table id="deptTable" data-mobile-responsive="true" data-click-to-select="true">
          <thead>
          <tr>
            <th data-field="selectItem" data-checkbox="true"></th>
          </tr>
          </thead>
        </table>
      </div>

      <div v-show="!showList" class="panel panel-default">
        <div class="panel-heading">{{title}}</div>
        <form class="form-horizontal">
          <div class="form-group">
            <div class="col-sm-2 control-label">部门名称</div>
            <div class="col-sm-10">
              <input type="text" class="form-control" v-model="dept.name" placeholder="部门名称"/>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-2 control-label">上级部门</div>
            <div class="col-sm-10">
              <input type="text" class="form-control" style="cursor:pointer;" v-model="dept.parentName" @click="deptTree" readonly="readonly" placeholder="一级部门"/>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-2 control-label">排序号</div>
            <div class="col-sm-10">
              <input type="number" class="form-control" v-model="dept.orderNum" placeholder="排序号"/>
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
          showList: true,
          title: null,
          dept:{
            parentName:null,
            parentId:0,
            orderNum:0
        }
      }
    },
    mounted(){
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
      window.ztree=null;
      window.Dept = {
        id: "deptTable",
        table: null,
        layerIndex: -1
      };

      /**
       * 初始化表格的列
       */
      Dept.initColumn = function () {
        var columns = [
          {field: 'selectItem', radio: true},
          {title: '部门ID', field: 'deptId', visible: false, align: 'center', valign: 'middle', width: '80px'},
          {title: '部门名称', field: 'name', align: 'center', valign: 'middle', sortable: true, width: '180px'},
          {title: '上级部门', field: 'parentName', align: 'center', valign: 'middle', sortable: true, width: '100px'},
          {title: '排序号', field: 'orderNum', align: 'center', valign: 'middle', sortable: true, width: '100px'}]
        return columns;
      };


      window.getDeptId = function () {
        var selected = $('#deptTable').bootstrapTreeTable('getSelections');
        if (selected.length == 0) {
          alert("请选择一条记录");
          return null;
        } else {
          return selected[0].id;
        }
      }


      $(function () {
        $.get(baseURL + "sys/dept/info", function(r){
          var colunms = Dept.initColumn();
          var table = new TreeTable(Dept.id, baseURL + "sys/dept/list", colunms);
          table.setRootCodeValue(r.deptId);
          table.setExpandColumn(2);
          table.setIdField("deptId");
          table.setCodeField("deptId");
          table.setParentCodeField("parentId");
          table.setExpandAll(false);
          table.init();
          Dept.table = table;
        });
      });
    },
    methods: {
      getDept(){
        //加载部门树
        $.get(baseURL + "sys/dept/select", (r)=>{
          ztree = $.fn.zTree.init($("#deptTree"), setting, r.deptList);
          var node = ztree.getNodeByParam("deptId", this.dept.parentId);
          ztree.selectNode(node);

          this.dept.parentName = node.name;
        })
      },
      add(){
        this.showList = false;
        this.title = "新增";
        this.dept = {parentName:null,parentId:0,orderNum:0};
        this.getDept();
      },
      update() {
        var deptId = getDeptId();
        if(deptId == null){
          return ;
        }

        $.get(baseURL + "sys/dept/info/"+deptId,(r)=>{
          this.showList = false;
          this.title = "修改";
          this.dept = r.dept;

          this.getDept();
        });
      },
      del() {
        var deptId = getDeptId();
        if(deptId == null){
          return ;
        }

        confirm('确定要删除选中的记录？', function(){
          $.ajax({
            type: "POST",
            url: baseURL + "sys/dept/delete",
            data: "deptId=" + deptId,
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
        });
      },
      saveOrUpdate(event) {
        var url = this.dept.deptId == null ? "sys/dept/save" : "sys/dept/update";
        $.ajax({
          type: "POST",
          url: baseURL + url,
          contentType: "application/json",
          data: JSON.stringify(this.dept),
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
          btn1: (index)=>{
            var node = ztree.getSelectedNodes();
            //选择上级部门
            this.dept.parentId = node[0].deptId;
            this.dept.parentName = node[0].name;

            layer.close(index);
          }
        });
      },
      reload: function () {
        this.showList = true;
        Dept.table.refresh();
      }
    }
  }
</script>
<style></style>
