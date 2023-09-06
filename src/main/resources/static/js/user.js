layui.use(['table', 'dropdown'], function () {
    var table = layui.table;
    var dropdown = layui.dropdown;

    // 创建渲染实例
    table.render({
        elem: '#test',
        url: '/user/page', // 此处为静态模拟数据，实际使用时需换成真实接口
        request: {
            pageName: 'current', // 页码参数名称
            limitName: 'size',  // 每页数据参数名称
        },
        response: {
            statusName: 'code' //规定数据状态的字段名称，默认：code
            , statusCode: 200 //规定成功的状态码，默认：0
            , msgName: 'msg' //规定状态信息的字段名称，默认：msg
            , countName: 'count' //规定数据总数的字段名称，默认：count
            , dataName: 'data' //规定数据列表的字段名称，默认：data
        },
        parseData: function (resp) {
            return {
                "code": resp.code,
                "msg": resp.message,
                "count": resp.data.total,
                "data": resp.data.records
            }
        },
        toolbar: '#toolbarDemo',
        defaultToolbar: ['filter', 'exports', 'print', {
            title: '提示',
            layEvent: 'LAYTABLE_TIPS',
            icon: 'layui-icon-tips'
        }],
        height: 'full-35', // 最大高度减去其他容器已占有的高度差
        css: [ // 重设当前表格样式
            '.layui-table-tool-temp{padding-right: 145px;}'
        ].join(''),
        cellMinWidth: 80,
        // totalRow: true, // 开启合计行
        page: true,
        limit: 10,
        limits: [5, 10, 20, 50, 100],
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {field: 'id', fixed: 'left', width: 80, title: 'ID', sort: true, totalRowText: '合计：'},
            {field: 'userName', width: 80, title: '用户'},
            {field: 'nickName', width: 80, title: '昵称'},
            {field: 'sexType', width: 80, title: '性别', templet: '#table-gender', sort: true},
            {
                field: 'phoneNo',
                title: '手机号 <i class="layui-icon layui-icon-tips layui-font-14" lay-event="phone-tips" title="该字段开启了编辑功能" style="margin-left: 5px;"></i>',
                fieldTitle: '手机号',
                hide: 0,
                width: 150,
                edit: 'text'
            },
            {
                field: 'email',
                title: '邮箱 <i class="layui-icon layui-icon-tips layui-font-14" lay-event="email-tips" title="该字段开启了编辑功能" style="margin-left: 5px;"></i>',
                fieldTitle: '邮箱',
                hide: 0,
                width: 150,
                edit: 'text'
            },
            {field: 'permissions', title: '权限', width: 120},
            {field: 'createTime', title: '创建时间', width: 120},
            {field: 'updateTime', title: '更新时间', width: 120},
            {
                field: 'extra',
                title: '签名',
                edit: 'textarea',
                minWidth: 260,
                expandedWidth: 260
                // ,totalRow: '人物：<span class="layui-badge-rim">唐代：{{= d.TOTAL_ROW.era.tang }} </span> <span class="layui-badge-rim">宋代：{{= d.TOTAL_ROW.era.song }}</span> <span class="layui-badge-rim">现代：{{= d.TOTAL_ROW.era.xian }}</span>'
            },
            // {field:'ip', title:'IP', width: 120},
            // {field:'experience', width: 100, title: '积分', sort: true, totalRow: '{{= d.TOTAL_NUMS }} 😊'},
            // {field:'checkin', title:'打卡', width: 100, sort: true, totalRow: '{{= parseInt(d.TOTAL_NUMS) }} 次'},
            {fixed: 'right', title: '操作', width: 134, minWidth: 125, toolbar: '#barDemo'}
        ]],
        done: function () {
            var id = this.id;
            // 下拉按钮测试
            dropdown.render({
                elem: '#dropdownButton', // 可绑定在任意元素中，此处以上述按钮为例
                data: [{
                    id: 'add',
                    title: '添加'
                }, {
                    id: 'update',
                    title: '编辑'
                }, {
                    id: 'delete',
                    title: '删除'
                }],
                // 菜单被点击的事件
                click: function (obj) {
                    var checkStatus = table.checkStatus(id)
                    var data = checkStatus.data; // 获取选中的数据
                    switch (obj.id) {
                        case 'add':
                            layer.open({
                                title: '添加',
                                type: 1,
                                area: ['80%', '80%'],
                                content: '<div style="padding: 16px;">自定义表单元素</div>'
                            });
                            break;
                        case 'update':
                            if (data.length !== 1) return layer.msg('请选择一行');
                            layer.open({
                                title: '编辑',
                                type: 1,
                                area: ['80%', '80%'],
                                content: '<div style="padding: 16px;">自定义表单元素</div>'
                            });
                            break;
                        case 'delete':
                            if (data.length === 0) {
                                return layer.msg('请选择一行');
                            }
                            layer.msg('delete event');
                            break;
                    }
                }
            });

            // 重载测试
            dropdown.render({
                elem: '#reloadTest', // 可绑定在任意元素中，此处以上述按钮为例
                data: [{
                    id: 'reload',
                    title: '重载'
                }, {
                    id: 'reload-deep',
                    title: '重载 - 参数叠加'
                }, {
                    id: 'reloadData',
                    title: '仅重载数据'
                }, {
                    id: 'reloadData-deep',
                    title: '仅重载数据 - 参数叠加'
                }],
                // 菜单被点击的事件
                click: function (obj) {
                    switch (obj.id) {
                        case 'reload':
                            // 重载 - 默认（参数重置）
                            table.reload('test', {
                                where: {
                                    abc: '123456',
                                    //test: '新的 test2',
                                    //token: '新的 token2'
                                },
                                /*
                                cols: [[ // 重置表头
                                  {type: 'checkbox', fixed: 'left'},
                                  {field:'id', title:'ID', width:80, fixed: 'left', unresize: true, sort: true, totalRowText: '合计：'},
                                  {field:'sex', title:'性别', width:80, edit: 'text', sort: true},
                                  {field:'experience', title:'积分', width:80, sort: true, totalRow: true, templet: '<div>{{= d.experience }} 分</div>'},
                                  {field:'logins', title:'登入次数', width:100, sort: true, totalRow: true},
                                  {field:'joinTime', title:'加入时间', width:120}
                                ]]
                                */
                            });
                            break;
                        case 'reload-deep':
                            // 重载 - 深度（参数叠加）
                            table.reload('test', {
                                where: {
                                    abc: 123,
                                    test: '新的 test1'
                                },
                                //defaultToolbar: ['print'], // 重载头部工具栏右侧图标
                                //cols: ins1.config.cols
                            }, true);
                            break;
                        case 'reloadData':
                            // 数据重载 - 参数重置
                            table.reloadData('test', {
                                where: {
                                    abc: '123456',
                                    //test: '新的 test2',
                                    //token: '新的 token2'
                                },
                                scrollPos: 'fixed',  // 保持滚动条位置不变 - v2.7.3 新增
                                height: 2000, // 测试无效参数（即与数据无关的参数设置无效，此处以 height 设置无效为例）
                                //url: '404',
                                //page: {curr: 1, limit: 30} // 重新指向分页
                            });
                            break;
                        case 'reloadData-deep':
                            // 数据重载 - 参数叠加
                            table.reloadData('test', {
                                where: {
                                    abc: 123,
                                    test: '新的 test1'
                                }
                            }, true);
                            break;
                    }
                    layer.msg('可观察 Network 请求参数的变化');
                }
            });

            // 行模式
            dropdown.render({
                elem: '#rowMode',
                data: [{
                    id: 'default-row',
                    title: '单行模式（默认）'
                }, {
                    id: 'multi-row',
                    title: '多行模式'
                }],
                // 菜单被点击的事件
                click: function (obj) {
                    var checkStatus = table.checkStatus(id)
                    var data = checkStatus.data; // 获取选中的数据
                    switch (obj.id) {
                        case 'default-row':
                            table.reload('test', {
                                lineStyle: null // 恢复单行
                            });
                            layer.msg('已设为单行');
                            break;
                        case 'multi-row':
                            table.reload('test', {
                                // 设置行样式，此处以设置多行高度为例。若为单行，则没必要设置改参数 - 注：v2.7.0 新增
                                lineStyle: 'height: 95px;'
                            });
                            layer.msg('即通过设置 lineStyle 参数可开启多行');
                            break;
                    }
                }
            });
        },
        error: function (res, msg) {
            console.log(res, msg)
        }
    });

    // 工具栏事件
    table.on('toolbar(test)', function (obj) {
        var id = obj.config.id;
        var checkStatus = table.checkStatus(id);
        var othis = lay(this);
        switch (obj.event) {
            case 'getCheckData':
                var data = checkStatus.data;
                layer.alert(layui.util.escape(JSON.stringify(data)));
                break;
            case 'getData':
                var getData = table.getData(id);
                console.log(getData);
                layer.alert(layui.util.escape(JSON.stringify(getData)));
                break;
            case 'LAYTABLE_TIPS':
                layer.alert('自定义工具栏图标按钮');
                break;
        }
        ;
    });
    // 表头自定义元素工具事件 --- 2.8.8+
    table.on('colTool(test)', function (obj) {
        var event = obj.event;
        console.log(obj);
        if (event === 'email-tips' || event === 'phone-tips') {
            layer.alert(layui.util.escape(JSON.stringify(obj.col)), {
                title: '当前列属性配置项'
            });
        }
    });

    // 触发单元格工具事件
    table.on('tool(test)', function (obj) { // 双击 toolDouble
        var data = obj.data; // 获得当前行数据
        // console.log(obj)
        if (obj.event === 'edit') {
            layer.open({
                title: '编辑 - id:' + data.id,
                type: 1,
                area: ['80%', '80%'],
                content: '<div style="padding: 16px;">自定义表单元素</div>'
            });
        } else if (obj.event === 'more') {
            // 更多 - 下拉菜单
            dropdown.render({
                elem: this, // 触发事件的 DOM 对象
                show: true, // 外部事件触发即显示
                data: [{
                    title: '查看',
                    id: 'detail'
                }, {
                    title: '删除',
                    id: 'del'
                }],
                click: function (menudata) {
                    if (menudata.id === 'detail') {
                        layer.msg('查看操作，当前行 ID:' + data.id);
                    } else if (menudata.id === 'del') {
                        layer.confirm('真的删除行 [id: ' + data.id + '] 么', function (index) {
                            obj.del(); // 删除对应行（tr）的DOM结构
                            layer.close(index);
                            // 向服务端发送删除指令
                        });
                    }
                },
                align: 'right', // 右对齐弹出
                style: 'box-shadow: 1px 1px 10px rgb(0 0 0 / 12%);' // 设置额外样式
            })
        }
    });

    // 触发表格复选框选择
    table.on('checkbox(test)', function (obj) {
        console.log(obj)
    });

    // 触发表格单选框选择
    table.on('radio(test)', function (obj) {
        console.log(obj)
    });

    // 行单击事件
    table.on('row(test)', function (obj) {
        //console.log(obj);
        //layer.closeAll('tips');
    });
    // 行双击事件
    table.on('rowDouble(test)', function (obj) {
        console.log(obj);
    });

    // 单元格编辑事件
    table.on('edit(test)', function (obj) {
        var field = obj.field; // 得到字段
        var value = obj.value; // 得到修改后的值
        var data = obj.data; // 得到所在行所有键值
        // 值的校验
        if (field === 'email') {
            if (!/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(obj.value)) {
                layer.tips('输入的邮箱格式不正确，请重新编辑', this, {tips: 1});
                return obj.reedit(); // 重新编辑 -- v2.8.0 新增
            }
        }
        // 编辑后续操作，如提交更新请求，以完成真实的数据更新
        // …
        layer.msg('编辑成功', {icon: 1});

        // 其他更新操作
        var update = {};
        update[field] = value;
        obj.update(update);
    });
});