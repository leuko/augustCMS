<template>
    <div>

        <div v-show="showList">
            <div class="grid-btn">
                <a class="btn btn-primary" @click="add"><i class="fa fa-plus"></i>&nbsp;新增</a>
                <a class="btn btn-primary" @click="update"><i class="fa fa-pencil-square-o"></i>&nbsp;修改</a>
                <a class="btn btn-primary" @click="del"><i class="fa fa-trash-o"></i>&nbsp;删除</a>
            </div>
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
        methods: {
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
            }
        }
    }
</script>
<style></style>
