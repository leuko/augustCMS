<template>
    <div  class="layui-layout layui-layout-admin" id="layui_layout">
      <!-- 顶部区域 -->
      <div class="layui-header header header-demo">
        <div class="layui-main">
          <!-- logo区域 -->
          <div class="admin-logo-box">
            <a class="logo" href="javascript:;">人人权限系统</a>
            <div class="larry-side-menu">
              <i class="fa fa-bars" aria-hidden="true"></i>
            </div>
          </div>
          <!-- 右侧导航 -->
          <ul class="layui-nav larry-header-item">
            <li class="layui-nav-item"><a href="javascript:void(0);" @click="updatePassword"><i class="fa fa-lock"></i> &nbsp;修改密码</a></li>
            <li class="layui-nav-item"><a href="logout"><i class="fa fa-sign-out"></i> &nbsp;退出系统</a></li>
          </ul>
        </div>
      </div>
      <!-- 左侧侧边导航开始 -->
      <div class="layui-side layui-side-bg layui-larry-side" id="larry-side">
        <div class="layui-side-scroll" id="larry-nav-side" lay-filter="side">
          <!-- 左侧菜单 -->
            <side-menu  :menulist="menuList"></side-menu>
        </div>
      </div>

      <!-- 左侧侧边导航结束 -->
      <!-- 右侧主体内容 -->
      <div class="layui-body" id="larry-body" style="bottom: 0;border-left: solid 2px #1AA094;">
        <div class="layui-tab layui-tab-card larry-tab-box" id="larry-tab" lay-filter="main-tab" lay-allowclose="true">
          <ul class="layui-tab-title">
            <li class="layui-this" id="admin-home"><i class="fa fa-home"></i><em>控制台</em></li>
          </ul>
          <div class="layui-tab-content" style="min-height: 150px; ">
            <div class="layui-tab-item layui-show">
              <router-view></router-view>
            </div>
          </div>
        </div>
      </div>
      <!-- 底部区域 -->
      <div class="layui-footer layui-larry-foot" id="larry-footer">
        <div class="layui-mian">
          Copyright &copy; 2018 <a href="http://www.renren.io" target="_blank">renren.io</a> All Rights Reserved
        </div>
      </div>
      <!-- 修改密码 -->
      <div id="passwordLayer" style="display: none;">

        <form class="layui-form" action="">
          <div class="layui-form-item">
            <label class="layui-form-label">账号</label>
            <label class="layui-form-label laber-account">{{user.username}}</label>
          </div>
          <div class="layui-form-item">
            <label class="layui-form-label">原密码</label>
            <div class="layui-input-inline">
              <input type="password" v-model="password" placeholder="原密码" autocomplete="off" class="layui-input">
            </div>
          </div>
          <div class="layui-form-item">
            <label class="layui-form-label">新密码</label>
            <div class="layui-input-inline">
              <input type="text" v-model="newPassword" placeholder="新密码" autocomplete="off" class="layui-input">
            </div>
          </div>
        </form>
      </div>
    </div>
</template>
<script>
  import sideMenu from './components/sideMenu'
  export default {
    components:{
      sideMenu
    },
    data(){
      return{
        user: {},
        menuList: {},
        password: '',
        newPassword: '',
        navTitle: "首页",
      }
    },
    created(){
      this.getMenuList();
      this.getUser();
    },
    updated(){

      if($("#larry-side .layui-nav-item>a").length==0 || !isquery){
        return;
      }
      console.log("执行")
      isquery=false;
      layui.config({
        base: 'statics/js/',
      }).use(['navtab','layer'], function(){
        window.jQuery = window.$ = layui.jquery;
        window.layer = layui.layer;
        var element = layui.element();
        var  navtab = layui.navtab({
          elem: '.larry-tab-box',
          closed:false
        });
        $('#larry-nav-side').children('ul').find('li').each(function () {
          var $this = $(this);
          if ($this.find('dl').length > 0) {

            var $dd = $this.find('dd').each(function () {
              $(this).on('click', function (e) {
                e.preventDefault;
                var $a = $(this).children('a');
//                var href = $a.data('url');
                var href = "/user"
                var icon = $a.children('i:first').data('icon');
                var title = $a.children('span').text();
                var data = {
//                  href: href,
                  icon: icon,
                  title: title
                }
               // navtab.tabAdd(data);
              });
            });
          } else {

            $this.on('click', function (e) {
              e.preventDefault;
              var $a = $(this).children('a');
//              var href = $a.data('#/user');
              var href = "/user"
              var icon = $a.children('i:first').data('icon');
              var title = $a.children('span').text();
              var data = {
//                href: href,
                icon: icon,
                title: title
              }
              //navtab.tabAdd(data);
            });
          }
        });
        $('.larry-side-menu').click(function (e) {
          e.preventDefault;
          var sideWidth = $('#larry-side').width();
          if (sideWidth === 200) {
            $('#larry-body').animate({
              left: '0'
            });
            $('#larry-footer').animate({
              left: '0'
            });
            $('#larry-side').animate({
              width: '0'
            });
          } else {
            $('#larry-body').animate({
              left: '200px'
            });
            $('#larry-footer').animate({
              left: '200px'
            });
            $('#larry-side').animate({
              width: '200px'
            });
          }
        });

      });
    },
    methods:{
      getMenuList() {
        $.getJSON("sys/menu/nav", (r)=>{
          this.menuList = r.menuList;
        });
      },
      getUser() {
        $.getJSON("sys/user/info?_" + $.now(), (r)=>{
            this.user = r.user;
//          this.user = {username:"admin"};
        });
      },
      updatePassword() {
        layer.open({
          type: 1,
          skin: 'layui-layer-molv',
          title: "修改密码",
          area: ['550px', '270px'],
          shadeClose: false,
          content: jQuery("#passwordLayer"),
          btn: ['修改', '取消'],
          btn1: function (index) {
            var data = "password=" + this.password + "&newPassword=" + this.newPassword;
            $.ajax({
              type: "POST",
              url: "sys/user/password",
              data: data,
              dataType: "json",
              success: function (result) {
                if (result.code == 0) {
                  layer.close(index);
                  layer.alert('修改成功', function (index) {
                    location.reload();
                  });
                } else {
                  layer.alert(result.msg);
                }
              }
            });
          }
        });
      }
    }
  }
</script>
