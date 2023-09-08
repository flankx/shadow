layui.use(function () {
    var form = layui.form;
    var layer = layui.layer;
    var util = layui.util;
    var upload = layui.upload;
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
                        // area: ['300px', '300px'], // 设置弹层的宽高 默认 auto
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
    // 单图片上传
    var uploadInst = upload.render({
        elem: '#ID-upload-demo-btn',
        url: '/user/uploadAvatar', // 此处用的是第三方的 http 请求演示，实际使用时改成您自己的上传接口即可。
        field: 'file',  // 文件域的字段名
        accept: 'images',   // 指定允许上传时校验的文件类型
        audio: true,    //是否选完文件后自动上传。若为 false，则需设置 bindAction 属性来指向其它按钮提交上传。
        size: 500, // 限制文件大小，单位 KB
        before: function (obj) {
            // 预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
                $('#ID-upload-demo-img').attr('src', result); // 图片链接（base64）
            });

            element.progress('filter-demo', '0%'); // 进度条复位
            layer.msg('上传中', {icon: 16, time: 0});
        },
        done: function (res) {
            // 若上传失败
            if (res.code !== 200) {
                return layer.msg('上传失败');
            }
            // 上传成功的一些操作
            // …
            $('#ID-upload-demo-text').html(''); // 置空上传失败的状态
        },
        error: function () {
            // 演示失败状态，并实现重传
            var demoText = $('#ID-upload-demo-text');
            demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
            demoText.find('.demo-reload').on('click', function () {
                uploadInst.upload();
            });
        },
        // 进度条
        progress: function (n, elem, e) {
            element.progress('filter-demo', n + '%'); // 可配合 layui 进度条元素使用
            if (n == 100) {
                layer.msg('上传完毕', {icon: 1});
            }
        }
    });
});