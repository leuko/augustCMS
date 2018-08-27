<template>
    <div>

        <div v-show="showList">
            <div class="grid-btn">
                <a class="btn btn-primary" @click="add"><i class="fa fa-plus"></i>&nbsp;新增</a>
                <a class="btn btn-primary" @click="update"><i class="fa fa-pencil-square-o"></i>&nbsp;修改</a>
                <a class="btn btn-primary" @click="del"><i class="fa fa-trash-o"></i>&nbsp;删除</a>
            </div>
            <table id="jqGrid"></table>
            <div id="jqGridPager"></div>
        </div>
        <div v-show="!showList" class="panel panel-default">
        <div class="panel-heading">{{title}}</div>
        <form class="form-horizontal" style="width:800px;">
          <div class="form-group">
            <div class="col-sm-5 control-label">名称</div>
            <div class="col-sm-7">
              <input type="text" class="form-control" v-model="spiderProject.name" placeholder="名称"/>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-5 control-label">采集链接地址</div>
            <div class="col-sm-7">
              <input type="text" class="form-control" v-model="spiderProject.listUrl" placeholder="采集链接地址"/>
              <span class="text-danger">注: 列表URL, url中可以有{page}</span>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-5 control-label">抓取方式</div>
            <div class="col-sm-7">
              <!--<input type="text" class="form-control" v-model="spiderProject.method" placeholder="抓取方式"/>-->
              <select class="form-control" v-model="spiderProject.method" >
                <option value="GET">GET</option>
                <option value="POST">POST</option>
              </select>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-5 control-label">内容类型</div>
            <div class="col-sm-7">
              <!--<input type="text" class="form-control" v-model="spiderProject.return_content_type" placeholder="内容类型"/>-->
              <select class="form-control" v-model="spiderProject.returnContentType" >
                <option value="HTML">HTML</option>
                <option value="JSON">JSON</option>
                <option value="其他">其他</option>
              </select>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-5 control-label">内容编码</div>
            <div class="col-sm-7">
              <!--<input type="text" class="form-control" v-model="spiderProject.charset_name" placeholder="内容编码"/>-->
              <select class="form-control" v-model="spiderProject.charsetName" >
                <option value="UTF-8">UTF-8</option>
                <option value="GBK">GBK</option>
                <option value="GB2312">GB2312</option>
              </select>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-5 control-label">起始页码</div>
            <div class="col-sm-7">
              <input type="text" class="form-control" v-model="spiderProject.pageStart" placeholder="起始页码"/>
              <span class="text-danger">注: 抓取结束页，会替换列表URL中的{page}</span>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-5 control-label">结束页码</div>
            <div class="col-sm-7">
              <input type="text" class="form-control" v-model="spiderProject.pageEnd" placeholder="结束页码"/>
              <span class="text-danger">注: 抓取结束页，会替换列表URL中的{page}</span>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-5 control-label">伪造User-Agent</div>
            <div class="col-sm-7">
              <input type="text" class="form-control" v-model="spiderProject.spiderOptionUserAgent" placeholder="伪造User-Agent"/>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-5 control-label">伪造来源</div>
            <div class="col-sm-7">
              <input type="text" class="form-control" style="cursor:pointer;resize:none;" v-model="spiderProject.spiderOptionReferer"  placeholder="伪造来源"/>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-5 control-label">伪造Cookie</div>
            <div class="col-sm-7">
              <input type="text" class="form-control" style="cursor:pointer;resize:none;" v-model="spiderProject.spiderOptionCookie"  placeholder="伪造Cookie"/>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-5 control-label">伪造header</div>
            <div class="col-sm-7">
              <textarea type="text" class="form-control" style="cursor:pointer;resize:none;" v-model="spiderProject.spiderOptionHeader"  placeholder="伪造header"></textarea>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-5 control-label">POST数据</div>
            <div class="col-sm-7">
              <textarea type="text" class="form-control" style="cursor:pointer;resize:none;" v-model="spiderProject.spiderOptionPostData"  placeholder="POST数据"></textarea>
              <span class="text-danger">注: POST数据,如果method=post时有效，key:value|base64,urlencode 多个换行，支持modifier</span>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-5 control-label">超时时间</div>
            <div class="col-sm-7">
              <input type="text" class="form-control" style="cursor:pointer;resize:none;" v-model="spiderProject.spiderOptionTimeout"  placeholder="超时时间"/>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-5 control-label">抓取详情页url的选择器语法</div>
            <div class="col-sm-7">
              <input type="text" class="form-control" style="cursor:pointer;resize:none;" v-model="spiderProject.detailUrlSelector"  placeholder="抓取详情页url的选择器语法"/>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-5 control-label">抓取详情页url</div>
            <div class="col-sm-7">
              <input type="text" class="form-control" style="cursor:pointer;resize:none;" v-model="spiderProject.detailUrlArtifact"  placeholder="抓取详情页url"/>
              <span class="text-danger">注: 人工合成，大括号中的变量为，json中的值，如：http://www.baidu.com/{id}-{name}</span>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-5 control-label">抓取详情页缩略图的选择器语法</div>
            <div class="col-sm-7">
              <input type="text" class="form-control" style="cursor:pointer;resize:none;" v-model="spiderProject.picUrlSelector"  placeholder="抓取详情页缩略图的选择器语法"/>
              <span class="text-danger">注: 抓取详情页缩略图的选择器语法，兼容CSS or Jquery；若是JSON数据直接使用列表中的属性名</span>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-5 control-label">抓取详情页title的选择器语法</div>
            <div class="col-sm-7">
              <input type="text" class="form-control" style="cursor:pointer;resize:none;" v-model="spiderProject.titleSelector"  placeholder="抓取详情页title的选择器语法"/>
              <span class="text-danger">注: 抓取详情页title的选择器语法，兼容CSS or Jquery；若是JSON数据，直接使用列表中的属性名</span>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-5 control-label">抓取的数据插入的表</div>
            <div class="col-sm-7">
              <!--<input type="text" class="form-control" style="cursor:pointer;resize:none;" v-model="spiderProject.to_table"  placeholder="抓取的数据插入的表"/>-->
              <select name=""  @change="toggleTable($event,spiderProject.to_table)" class="form-control" v-model="spiderProject.toTable">
                <option value="article">article</option>
              </select>
              <div>

                <span @click="addNewColumn" style="padding:2px 5px;color:#fff;margin-top:15px;background:#1AA094;display:inline-block;">添加新字段</span>
                <div class="layui-card" v-for="item in spiderProjectColumnList" style="margin-top:10px;border:1px solid #ccc;">
                  <div class="layui-card-body" style="padding:30px 0 10px;">
                      <span @click="deleteNewColumn($event,item)" style="position:absolute;width:20px;height:20px;line-height:20px;right:5px;top:5px;border-radius:50%;text-align:center;background:#ccc;">&times;</span>
                      <div class="form-group">
                        <div class="col-sm-5">填充表字段</div>
                        <div class="col-sm-7">
                          <input type="text" class="form-control" v-model="item.tableColumn" placeholder="填充表字段"/>
                        </div>
                      </div>
                      <div class="form-group">
                        <div class="col-sm-5">字段选择器</div>
                        <div class="col-sm-7">
                          <input type="text" class="form-control" v-model="item.fieldSelector" placeholder="伪造User-Agent"/>
                        </div>
                      </div>
                      <div class="form-group">
                        <div class="col-sm-5">字段默认值</div>
                        <div class="col-sm-7">
                          <input type="text" class="form-control"  v-model="item.defaultValue"  placeholder="字段默认值"/>
                        </div>
                      </div>
                      <div class="form-group">
                        <div class="col-sm-5">是否图片</div>
                        <div class="col-sm-7">
                          <input type="text" class="form-control"  v-model="item.isPic"  placeholder="是否图片"/>
                        </div>
                      </div>
                      <div class="form-group">
                        <div class="col-sm-5">是否日期格式</div>
                        <div class="col-sm-7">
                          <input type="text" class="form-control"  v-model="item.isDate"  placeholder="是否日期格式"/>
                        </div>
                      </div>
                      <div class="form-group">
                        <div class="col-sm-5">日期字段格式</div>
                        <div class="col-sm-7">
                          <input type="text" class="form-control"  v-model="item.dateFormat"  placeholder="日期字段格式"/>
                        </div>
                      </div>
                      <div class="form-group">
                        <div class="col-sm-5">过滤内容白名单类型</div>
                        <div class="col-sm-7">
                          <input type="text" class="form-control"  v-model="item.contentWhitelistType"  placeholder="字段默认值"/>
                        </div>
                      </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-6 control-label"></div>
            <input type="button" class="btn btn-primary" @click="test" value="测试"/>
            &nbsp;&nbsp;<input type="button" class="btn btn-danger" @click="saveOrUpdate" value="保存"/>
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
                spiderProjectColumnList:[],
                spiderProject:{
                    id:null,
                    name:"",
                    listUrl:"",
                    method:"",
                    returnContentType:"",
                    charsetName:"",
                    pageStart:1,
                    pageEnd:1,
                    spiderOptionUserAgent:"",
                    spiderOptionReferer:"",
                    spiderOptionCookie:"",
                    spiderOptionHeader:"",
                    spiderOptionPostData:"",
                    spiderOptionTimeout:"",
                    detailUrlSelector:"",
                    detailUrlArtifact:"",
                    picUrlSelector:"",
                    titleSelector:"",
                    toTable:"",
                    createTime:""
                }
            }
        },
        mounted(){
          $(()=>{
            $("#jqGrid").jqGrid({
              url: baseURL + 'spider/spiderproject/list',
              datatype: "json",
              colModel: [
                { label: 'ID', name: 'id', width: 20, key: true },
                { label: '名称', name: 'name', sortable: false, width: 30 },
                { label: '抓取链接url', name: 'listUrl', sortable: false, width: 100},
                { label: '创建时间', name: 'createTime', width: 100 },
//                { label: '更新时间', name: 'updatedAt', width: 80 }
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
              gridComplete:()=>{
                //隐藏grid底部滚动条
                $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
              }
            });
          });
        },
        methods: {
            toggleTable(e,table){
                console.log(table);
                $.ajax({
                    url:"",
                    type:"get",
                    success:function(res){

                    }
                })
            },
            addNewColumn(){
              this.spiderProjectColumnList.push({id:null});
            },
            deleteNewColumn(e,data){
                console.log(data);
                let spiderProjectColumnList = this.spiderProjectColumnList;
                $.ajax({
                  type: "POST",
                  url: baseURL + "spider/spiderprojectcolumn/delete",
                  data: "ids=" + data.id,
                  success:(r)=>{
                    if(r.code === 0){
                      alert('操作成功', ()=>{
                        for(var i=spiderProjectColumnList.length-1;i>=0;i--){
                          if(spiderProjectColumnList[i].id == data.id){
                            spiderProjectColumnList.splice(i,1);
                          }
                        }
                        this.spiderProjectColumnList = spiderProjectColumnList;

                        this.reload();
                      });
                    }else{
                      alert(r.msg);
                    }
                  }
                })
            },
            test(){

            },
            reload: function (event) {
              this.showList = true;
              var page = $("#jqGrid").jqGrid('getGridParam','page');
              $("#jqGrid").jqGrid('setGridParam',{
                page:page
              }).trigger("reloadGrid");
            },
            saveOrUpdate(){
              var url = this.spiderProject.id == null ? "spider/spiderproject/save" : "spider/spiderproject/update";
              this.spiderProject = Object.assign({},this.spiderProject,{spiderProjectColumnEntityList:this.spiderProjectColumnList})
              $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(this.spiderProject),
                success:(r)=>{
                  if(r.code === 0){
                    alert('操作成功',(index)=>{
                      this.spiderProject = Object.assign({});
                      this.spiderProjectColumnList = [];
                      this.reload();
                    });
                  }else{
                    alert(r.msg);
                  }
                }
              });
            },
            add(){
                this.showList = false;
                this.title = "新增";
            },
            update() {
                var id = getSelectedRow();;
                if(id == null){
                    return ;
                }

                $.get(baseURL + "spider/spiderproject/info/"+id, (r)=>{
                    this.showList = false;
                    this.title = "修改";
                    this.spiderProject = r.spiderProject;
                    this.spiderProjectColumnList = r.spiderProject.spiderProjectColumnEntityList;

                });
            },
            del() {
                var ids = getSelectedRows();
                if(ids == null){
                    return ;
                }

                confirm('确定要删除选中的记录？',()=>{
                    $.ajax({
                        type: "POST",
                        url: baseURL + "spider/spiderproject/delete",
                        contentType: "application/json",
                        data:JSON.stringify(ids),
                        success: (r)=>{
                            if(r.code === 0){
                                alert('操作成功', ()=>{
                                    this.reload();
                                });
                            }else{
                                alert(r.msg);
                            }
                        }
                    });
                });
            }
        }
    }
</script>
<style></style>
