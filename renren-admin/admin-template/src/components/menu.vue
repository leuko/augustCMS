<template>
    <div>

      <div v-show="showList">
        <div class="grid-btn">
            <a class="btn btn-primary" @click="add"><i class="fa fa-plus"></i>&nbsp;新增</a>
            <a class="btn btn-primary" @click="update"><i class="fa fa-pencil-square-o"></i>&nbsp;修改</a>
            <a class="btn btn-primary" @click="del"><i class="fa fa-trash-o"></i>&nbsp;删除</a>
        </div>
        <table id="menuTable" data-mobile-responsive="true" data-click-to-select="true">
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
            <div class="col-sm-2 control-label">类型</div>
            <label class="radio-inline">
              <input type="radio" name="type" value="0" v-model="menu.type"/> 目录
            </label>
            <label class="radio-inline">
              <input type="radio" name="type" value="1" v-model="menu.type"/> 菜单
            </label>
            <label class="radio-inline">
              <input type="radio" name="type" value="2" v-model="menu.type"/> 按钮
            </label>
          </div>
          <div class="form-group">
            <div class="col-sm-2 control-label">菜单名称</div>
            <div class="col-sm-10">
              <input type="text" class="form-control" v-model="menu.name" placeholder="菜单名称或按钮名称"/>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-2 control-label">上级菜单</div>
            <div class="col-sm-10">
              <input type="text" class="form-control" style="cursor:pointer;" v-model="menu.parentName" @click="menuTree" readonly="readonly" placeholder="一级菜单"/>
            </div>
          </div>
          <div v-if="menu.type == 1" class="form-group">
            <div class="col-sm-2 control-label">菜单URL</div>
            <div class="col-sm-10">
              <input type="text" class="form-control" v-model="menu.url" placeholder="菜单URL"/>
            </div>
          </div>
          <div v-if="menu.type == 1 || menu.type == 2" class="form-group">
            <div class="col-sm-2 control-label">授权标识</div>
            <div class="col-sm-10">
              <input type="text" class="form-control" v-model="menu.perms" placeholder="多个用逗号分隔，如：user:list,user:create"/>
            </div>
          </div>
          <div v-if="menu.type != 2" class="form-group">
            <div class="col-sm-2 control-label">排序号</div>
            <div class="col-sm-10">
              <input type="number" class="form-control" v-model="menu.orderNum" placeholder="排序号"/>
            </div>
          </div>
          <div v-if="menu.type != 2" class="form-group">
            <div class="col-sm-2 control-label">图标</div>
            <div class="col-sm-10">
              <input type="text" class="form-control" v-model="menu.icon" placeholder="菜单图标"/>
              <code style="margin-top:4px;display: block;">获取图标：http://www.fontawesome.com.cn/faicons/</code>
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
          menu:{
            parentName:null,
            parentId:0,
            type:1,
            orderNum:0
          }
      }
    },
    mounted(){
      window.setting = {
        data: {
          simpleData: {
            enable: true,
            idKey: "menuId",
            pIdKey: "parentId",
            rootPId: -1
          },
          key: {
            url:"nourl"
          }
        }
      };
      window.ztree = null;
      window.Menu = {
        id: "menuTable",
        table: null,
        layerIndex: -1
      };

      /**
       * 初始化表格的列
       */
      window.Menu.initColumn = function () {
        var columns = [
          {field: 'selectItem', radio: true},
          {title: '菜单ID', field: 'menuId', visible: false, align: 'center', valign: 'middle', width: '80px'},
          {title: '菜单名称', field: 'name', align: 'center', valign: 'middle', sortable: true, width: '180px'},
          {title: '上级菜单', field: 'parentName', align: 'center', valign: 'middle', sortable: true, width: '100px'},
          {title: '图标', field: 'icon', align: 'center', valign: 'middle', sortable: true, width: '80px', formatter: function(item, index){
            return item.icon == null ? '' : '<i class="'+item.icon+' fa-lg"></i>';
          }},
          {title: '类型', field: 'type', align: 'center', valign: 'middle', sortable: true, width: '100px', formatter: function(item, index){
            if(item.type === 0){
              return '<span class="label label-primary">目录</span>';
            }
            if(item.type === 1){
              return '<span class="label label-success">菜单</span>';
            }
            if(item.type === 2){
              return '<span class="label label-warning">按钮</span>';
            }
          }},
          {title: '排序号', field: 'orderNum', align: 'center', valign: 'middle', sortable: true, width: '100px'},
          {title: '菜单URL', field: 'url', align: 'center', valign: 'middle', sortable: true, width: '160px'},
          {title: '授权标识', field: 'perms', align: 'center', valign: 'middle', sortable: true}]
        return columns;
      };


      window.getMenuId  = function () {
        var selected = $('#menuTable').bootstrapTreeTable('getSelections');
        if (selected.length == 0) {
          alert("请选择一条记录");
          return null;
        } else {
          return selected[0].id;
        }
      }


      $(function () {
        var colunms = Menu.initColumn();
        var table = new TreeTable(Menu.id, baseURL + "sys/menu/list", colunms);
        table.setExpandColumn(2);
        table.setIdField("menuId");
        table.setCodeField("menuId");
        table.setParentCodeField("parentId");
        table.setExpandAll(false);
        table.init();
        window.Menu.table = table;
      });
    },
    methods: {
      getMenu(menuId){
        //加载菜单树
        $.get(baseURL + "sys/menu/select",(r)=>{
          ztree = $.fn.zTree.init($("#menuTree"), setting, r.menuList);
          var node = ztree.getNodeByParam("menuId", this.menu.parentId);
          ztree.selectNode(node);

          this.menu.parentName = node.name;
        })
      },
      add(){
        this.showList = false;
        this.title = "新增";
        this.menu = {parentName:null,parentId:0,type:1,orderNum:0};
        this.getMenu();
      },
      update() {
        var menuId = getMenuId();
        if(menuId == null){
          return ;
        }

        $.get(baseURL + "sys/menu/info/"+menuId, (r)=>{
          this.showList = false;
          this.title = "修改";
          this.menu = r.menu;

          this.getMenu();
        });
      },
      del() {
        var menuId = getMenuId();
        if(menuId == null){
          return ;
        }

        confirm('确定要删除选中的记录？', function(){
          $.ajax({
            type: "POST",
            url: baseURL + "sys/menu/delete",
            data: "menuId=" + menuId,
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
      saveOrUpdate: function () {
        if(this.validator()){
          return ;
        }

        var url = this.menu.menuId == null ? "sys/menu/save" : "sys/menu/update";
        $.ajax({
          type: "POST",
          url:  baseURL + url,
          contentType: "application/json",
          data: JSON.stringify(this.menu),
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
      menuTree(){
        layer.open({
          type: 1,
          offset: '50px',
          skin: 'layui-layer-molv',
          title: "选择菜单",
          area: ['300px', '450px'],
          shade: 0,
          shadeClose: false,
          content: jQuery("#menuLayer"),
          btn: ['确定', '取消'],
          btn1: function (index) {
            var node = ztree.getSelectedNodes();
            //选择上级菜单
            this.menu.parentId = node[0].menuId;
            this.menu.parentName = node[0].name;

            layer.close(index);
          }
        });
      },
      reload() {
        this.showList = true;
        window.Menu.table.refresh();
      },
      validator: function () {
        if(isBlank(this.menu.name)){
          alert("菜单名称不能为空");
          return true;
        }

        //菜单
        if(this.menu.type === 1 && isBlank(this.menu.url)){
          alert("菜单URL不能为空");
          return true;
        }
      }
    }
  }
</script>
<style></style>
