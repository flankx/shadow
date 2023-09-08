layui.use(function () {
    var form = layui.form;
    var layer = layui.layer;
    var util = layui.util;
    var $ = layui.$;
    var element = layui.element;

    // 修改个人资料
    form.on('submit(setmyinfo)', function (data) {
        var field = data.field; // 获取表单字段值
        // console.log(JSON.stringify(field))
        $.ajax({
            type: "post",
            url: "/profile",
            headers: {
                'content-type': 'application/json'
            },
            dataType: 'json',
            data: JSON.stringify(field),
            success: function (r) {
                if (r.code === 200) {
                    // 刷新 iframe 页面
                    window.location.reload();
                } else {
                    layer.open({
                        icon: '5',
                        title: '更新失败！',
                        content: r.message
                    });
                }
            },
            error: function (msg) {
                layer.open({
                    icon: '0',
                    title: '服务器异常！',
                    content: msg
                });
            }
        });
        return false; // 阻止默认 form 跳转
    });
    // 修改用户密码
    form.on('submit(setmypass)', function (data) {
        var field = data.field; // 获取表单字段值
        // console.log(JSON.stringify(field))
        $.ajax({
            type: "post",
            url: "/profile/editPassword",
            headers: {
                'content-type': 'application/json'
            },
            dataType: 'json',
            data: JSON.stringify(field),
            success: function (r) {
                if (r.code === 200) {
                    element.tabChange('edit-profile', 'profile');
                    layer.msg(r.message);
                } else {
                    layer.open({
                        icon: '5',
                        title: '更新失败！',
                        content: r.message
                    });
                }
            },
            error: function (msg) {
                layer.open({
                    icon: '0',
                    title: '服务器异常！',
                    content: msg
                });
            }
        });
        return false; // 阻止默认 form 跳转
    });
    // 头像预览
    util.on('lay-on', {
        // 左侧菜单事件
        preview: function () {
            $.ajax({
                type: "get",
                url: "/user/avatar",
                success: function (r) {
                    // 页面层
                    layer.open({
                        type: 1,// page 页面层，可同时存在多个层
                        title: false, // 不显示标题
                        area: ['300px', '300px'], // 宽高
                        content: '<div style="text-align: center;"><img src="' + r + '" style="margin: 0 auto;" alt=""/></div>'
                    });
                },
                error: function (msg) {
                    layer.open({
                        icon: '0',
                        title: '服务器异常',
                        content: msg
                    });
                }
            });

        },

    });

});