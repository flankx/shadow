//JS
layui.use(['element', 'layer', 'util'], function () {
    var element = layui.element;
    var layer = layui.layer;
    var util = layui.util;
    var $ = layui.$;

    //头部事件
    util.event('lay-header-event', {
        menuLeft: function (othis) { // 左侧菜单事件
            layer.msg('展开左侧菜单的操作', {icon: 0});
        },
        menuRight: function () {  // 右侧菜单事件
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
                content: "<iframe frameborder='0' width='100%' height='100%' scrolling='auto' src='" + id + "' class='layadmin-iframe'></iframe>",
                id: id,
                change: true // 是否添加完毕后即自动切换
            })
        }
        element.tabChange('test-handle', id); // 切换到指定 tab 项
    };

});