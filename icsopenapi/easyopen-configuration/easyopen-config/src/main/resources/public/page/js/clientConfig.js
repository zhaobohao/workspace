var app = ApiUtil.getParam('app');
var doSave = false;

var ClientConfig = (function () {

    $('#createKeyBtn').click(function () {
        ApiUtil.post('client.pubprikey.create', {}, function (resp) {
            var respData = resp.data;
            $('#baseInfoFrm').form('load', respData);
        });
    });

    $('#createSecretBtn').click(function () {
        ApiUtil.post('client.appkeysecret.create', {}, function (resp) {
            var respData = resp.data;
            $('#baseInfoFrm').form('load', respData);
        });
    });

    $('#updateSecretBtn').click(function () {
        ApiUtil.post('client.appkeysecret.create', {}, function (resp) {
            var respData = resp.data;
            $('#baseInfoFrm').form('load', {secret:respData.secret});
        });
    });


    $('#baseInfoSaveBtn').click(function () {
        var uri = doSave ? 'app.client.add' : 'app.client.update';
        var $form = $('#baseInfoFrm');
        var validateResult = $form.form('validate');
        if (validateResult) {
            var data = $form.form('getData');
            data.app = app;
            ApiUtil.post(uri, data, function (resp) {
                $('#clientInfoWin').dialog('close');
                $('#clientGrid').datagrid('reload');
            });
        }
    });

    $('#searchClientBtn').click(function () {
        search();
    });

    $('#addClientBtn').click(function () {
        addClient();
    });

    $('#appKeySch').on('keyup', function (e) {
        if (e.keyCode == 13) {
            search();
        }
    });

    /**
     * initClientGrid
     */
    function initClientGrid() {
        $('#clientGrid').datagrid({
            loader: function (param, success, error) {
                param.app = app;
                ApiUtil.post('app.client.list', param, function (resp) {
                    var respData = resp.data;
                    success(respData);
                });
            },
            pagination: true,
            singleSelect: true,
            toolbar: '#clientGridToolBar',
            columns: [[
                {field: 'appKey', title: 'appKey', width: 150},
                {field: 'secret', title: 'secret'},
                {
                    field: 'roleList', title: '角色', width: 200, formatter: function (roleList, row, index) {
                    var roleText = [];
                    for (var i = 0, len = roleList.length; i < len; i++) {
                        roleText.push(roleList[i].description);
                    }
                    return roleText.length == 0 ? '<span class="red">未设置</span>' : roleText.join(', ');
                }
                },
                {
                    field: 'gmtCreate', title: '添加时间', width: 200, formatter: function (value, row, index) {
                    return ApiUtil.formatTime(value);
                }
                },
                {
                    field: 'pubKey', title: '公钥/私钥', width: 100, formatter: function (pubKey, row, index) {
                    if(!pubKey || !row.priKey) {
                        return '<span class="gray">未设置</span>';
                    }
                    return '<a href="#" onclick="ClientConfig.viewPubPriKey(' + index + ')">查看</a>';
                }
                },
                {
                    field: 'status', title: '状态', width: 100, formatter: function (status, row, index) {
                    if(status == 0) {
                        return '<span class="green">启用</span>';
                    } else if(status == 1) {
                        return '<span class="red">禁用</span>';
                    } else {
                        return '未知状态';
                    }
                }
                },
                {
                    field: '_opt', title: '操作', width: 100, formatter: function (value, row, index) {
                    var btns = [
                        '<a href="#" onclick="ClientConfig.updateClient(' + index + ')">修改</a>'
                    ];
                    return btns.join(' | ');
                }
                }
            ]]
        });

    }

    function initRoleTree() {
        ApiUtil.post('role.tree.listall', {}, function (resp) {
            var data = resp.data;
            $('#roleTree').combotree({
                multiple: true
                , onlyLeafCheck: true
                , required: true
            }).combotree('loadData', data);
        });
    }

    function search() {
        var val = $('#appKeySch').val();
        var data = {};
        if (val) {
            data.appKey = val;
        }
        $('#clientGrid').datagrid('load', data);
    }

    /**
     * 加载基本信息
     */
    function loadBaseInfo(row) {
        $('#baseInfoFrm').form('clear').form('load', row);

        var roleList = row.roleList || [];
        var roleCodeList = [];
        for (var i = 0, len = roleList.length; i < len; i++) {
            roleCodeList.push(roleList[i].roleCode);
        }

        $('#roleTree').combotree('setValue', roleCodeList);

        $('#clientInfoWin').dialog('open');
    }


    function addClient() {
        doSave = true;
        $('#createSecretBtn').show();
        $('#updateSecretBtn').hide();
        $('#baseInfoAppKey').textbox('readonly', false);
        loadBaseInfo({status:0});
    }

    // public函数
    return {
        init:function () {
            initClientGrid();
            initRoleTree();
        }
        ,updateClient:function (index) {
            doSave = false;
            $('#createSecretBtn').hide();
            $('#updateSecretBtn').show();
            var row = $('#clientGrid').datagrid('getRow', index);
            $('#baseInfoAppKey').textbox('readonly', true);
            loadBaseInfo(row);
        }
        ,viewPubPriKey:function(index){
            var row = $('#clientGrid').datagrid('getRow', index);
            var pubKey = row.pubKey;
            var priKey = row.priKey;
            var width = 550;
            var msg = '公钥:<br><textarea style="width:' + width + 'px;height:100px;">' + pubKey + '</textarea><br>' +
                '私钥:<br><textarea style="width:' + width + 'px;height:170px;">' + priKey + '</textarea>';

            $.messager.show({
                title: '公钥私钥',
                msg: '<div style="overflow-y: auto; overflow-x:auto;">' + msg + '</div>',
                width: 600,
                height: 410,
                showType: null,
                modal: true,
                draggable: true,
                timeout: 0,
                style: {
                    right: '',
                    bottom: ''
                }
            });
        }
    }// end return
})();


