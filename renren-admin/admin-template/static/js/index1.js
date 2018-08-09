
$(function() {
    $(window).on('resize', function () {
        var $content = $('#larry-tab .layui-tab-content');
        $content.height($(this).height() - 140);
        $content.find('#app').each(function () {
            $(this).height($content.height());
        });
    }).resize();
})


isquery=true;
var vm = new Vue({
    el: '#layui_layout',
    data: {
        user: {},
        menuList: {},
        password: '',
        newPassword: '',
        navTitle: "首页"
    },
    methods: {
        getMenuList: function () {
            $.getJSON("http://localhost:8081/renren-admin/sys/menu/nav", function (r) {
                vm.menuList = r.menuList;
            });
        },
        getUser: function () {
            $.getJSON("http://localhost:8081/renren-admin/sys/user/info?_" + $.now(), function (r) {
                // vm.user = r.user;
                vm.user = {username:"admin"};
            });
        },
        updatePassword: function () {
            layer.open({
                type: 1,
                skin: 'layui-layer-molv',
                title: "修改密码",
                area: ['550px', '270px'],
                shadeClose: false,
                content: jQuery("#passwordLayer"),
                btn: ['修改', '取消'],
                btn1: function (index) {
                    var data = "password=" + vm.password + "&newPassword=" + vm.newPassword;
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
        },
        donate: function () {
            layer.open({
                type: 2,
                title: false,
                area: ['806px', '467px'],
                closeBtn: 1,
                shadeClose: false,
                content: ['http://cdn.renren.io/donate.jpg', 'no']
            });
        }
    },
    created: function () {
        this.getMenuList();
        this.getUser();
    },updated:function(){

        if($("#larry-side .layui-nav-item>a").length==0 || !isquery){
            return;
        }
        console.log("执行")
        isquery=false;
        layui.config({
            base: 'static/js/',
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
                        $(this).on('click', function () {
                            var $a = $(this).children('a');
                            var href = $a.data('url');
                            var icon = $a.children('i:first').data('icon');
                            var title = $a.children('span').text();
                            var data = {
                                href: href,
                                icon: icon,
                                title: title
                            }
                            navtab.tabAdd(data);
                        });
                    });
                } else {

                    $this.on('click', function () {
                        var $a = $(this).children('a');
                        var href = $a.data('url');
                        var icon = $a.children('i:first').data('icon');
                        var title = $a.children('span').text();
                        var data = {
                            href: href,
                            icon: icon,
                            title: title
                        }
                        navtab.tabAdd(data);
                    });
                }
            });
            $('.larry-side-menu').click(function () {
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
    }
});
