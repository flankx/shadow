layui.use(['element', 'layer', 'util'], function () {
    var element = layui.element;
    var layer = layui.layer;
    var util = layui.util;
    var $ = layui.$;
    // 标志位
    let isShow = true;
    //头部事件
    util.event('lay-header-event', {
        // 左侧菜单事件 - 侧边伸缩
        menuLeft: function (object) {
            $('.layui-nav-item span').each(function () {
                if ($(this).is(':hidden')) {
                    $(this).show();
                } else {
                    $(this).hide();
                }
            });
            $('#layui-header-title span').each(function () {
                if ($(this).is(':hidden')) {
                    $(this).show();
                } else {
                    $(this).hide();
                }
            });
            // 判断isShow的状态
            if (isShow) {
                // 设置宽度
                $('#layui-header-title').width(60);
                $('.layui-side.layui-bg-black').width(60);
                // 修改图标的位置
                $('.layui-side-scroll i').css('margin-right', '70%');
                // 将left、footer、body的宽度修改
                $('.layui-layout-left').css('left', 60 + 'px');
                $('.layui-body').css('left', 60 + 'px');
                $('.layui-footer').css('left', 60 + 'px');
                // 将二级导航栏隐藏
                $('dd span').each(function () {
                    $(this).hide();
                });
                //修改标志位
                isShow = false;
            } else {
                // 设置宽度
                $('#layui-header-title').width(200);
                $('.layui-side.layui-bg-black').width(200);
                // 修改图标的位置
                $('.layui-side-scroll i').css('margin-right', '10%');
                // 将left、footer、body的宽度修改
                $('.layui-layout-left').css('left', 200 + 'px');
                $('.layui-body').css('left', 200 + 'px');
                $('.layui-footer').css('left', 200 + 'px');
                // 将二级导航栏显示
                $('dd span').each(function () {
                    $(this).show();
                });
                isShow = true;
            }
        },
        // 右侧菜单事件
        menuRight: function () {
            layer.open({
                type: 1,
                title: '更多',
                content: '<div style="padding: 15px;">处理右侧面板的操作</div>',
                area: ['260px', '100%'],
                offset: 'rt', // 右上角
                anim: 'slideLeft', // 从右侧抽屉滑出
                shadeClose: true,
                scrollbar: false
            });
        },
        // 刷新
        refresh: function () {
            window.location.reload();
        },
        // 标签
        note: function () {

        },
        // 皮肤
        theme: function () {

        },
        // 全屏显示
        fullscreen: function () {
            var doc = document.documentElement;
            if ($(document.body).hasClass("full-screen")) {
                $(document.body).removeClass("full-screen");
                if (document.exitFullscreen) {
                    document.exitFullscreen();
                } else if (document.mozCancelFullScreen) {
                    document.mozCancelFullScreen();
                } else if (document.webkitExitFullscreen) {
                    document.webkitExitFullscreen();
                }
            } else {
                $(document.body).addClass("full-screen");
                if (doc.requestFullscreen) {
                    doc.requestFullscreen();
                } else if (doc.mozRequestFullScreen) {
                    doc.mozRequestFullScreen();
                } else if (doc.webkitRequestFullscreen) {
                    doc.webkitRequestFullscreen();
                } else if (doc.msRequestFullscreen) {
                    doc.msRequestFullscreen();
                }
            }
        }

    });

    // a 标签 点击事件
    window.openNewTab = function (obj) {
        var id = obj.getAttribute("data-id");
        var title = obj.getAttribute("title");
        var $node = $('li[lay-id="' + id + '"]');
        if ($node.length === 0) {
            element.tabAdd('test-handle', {
                title: title,
                content: '<iframe frameborder="0" scrolling="auto" src="' + id + '" class="layaui-iframe"></iframe>',
                id: id,
                change: true // 是否添加完毕后即自动切换
            })
        }
        element.tabChange('test-handle', id); // 切换到指定 tab 项
    };

});