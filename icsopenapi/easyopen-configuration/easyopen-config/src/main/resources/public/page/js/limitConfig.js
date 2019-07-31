var LimitConfig = (function () {
    var jq = jQuery;
    var tokenRemark = '令牌桶策略：每秒放置固定数量的令牌数，不足的令牌数做等待处理，直到拿到令牌为止。';
    var limitRemark = '漏桶策略：每秒处理固定数量的请求，超出请求返回错误信息。';
    var LimitType = {
        LIMIT: 'LIMIT', TOKEN_BUCKET: 'TOKEN_BUCKET'
    }

    var apiIds = [];

    function initLimitGrid() {
        $('#limitGrid').datagrid({
            loader: function (param, success, error) {
                param.app = app;
                ApiUtil.post('app.limit.pagelist', param, function (resp) {
                    var respData = resp.data;
                    success(respData);
                });
            }
            , toolbar: '#limitGridToolbar'
            , pagination: true
            , rownumbers: true
            , pageSize: 50
            , pageList: [0, 10, 20, 50, 100, 200]
            , idField: 'id'
            , columns: [[
                {field: 'id', checkbox: true},
                {field: 'name', title: '接口名', width: 150},
                {field: 'version', title: '版本号', width: 100},
                {field: 'description', title: '接口描述', width: 200},
                {
                    field: 'limitCount', title: '每秒可处理请求数', sortable: true, formatter: function (val, obj, index) {
                    return obj.limitType == LimitType.LIMIT ? val : '-';
                }
                },
                {
                    field: 'msg', title: 'code/msg', width: 300, formatter: function (val, obj, index) {
                    return obj.limitType == LimitType.LIMIT ? (obj.limitCode || '<span class="color-red">未设置</span>') + '/' + (obj.limitMsg || '<span class="color-red">未设置</span>') : '-';
                }
                },
                {
                    field: 'tokenBucketCount', title: '令牌桶容量', sortable: true, formatter: function (val, obj, index) {
                    return obj.limitType != LimitType.LIMIT ? val : '-';
                }
                },
                {
                    field: 'status', title: '开启状态', sortable: true, formatter: function (val, obj, index) {
                    return val == 1 ? '<span class="green">开启</span>' : '<span class="red">关闭</span>';
                }
                },
                {
                    field: 'limitType',
                    title: '限流策略[<a href="#" onclick="LimitConfig.showLimitType(); return false;">?</a>]',
                    formatter: function (val, obj, index) {
                        return val == LimitType.LIMIT ? '<span>漏桶策略</span>' : '<span>令牌桶策略</span>';
                    }
                },

                {
                    field: '_opt', title: '操作', width: 100, formatter: function (val, obj, index) {
                    return '<a href="#" onclick="LimitConfig.update(' + index + ')">设置</a>';
                }
                },
            ]]
        })
    }

    $('#limitUpdateBtn').click(function () {
        apiIds = $('#limitGrid').datagrid('getCheckedId', 'apiId');
        if (apiIds.length == 0) {
            MsgUtil.topMsg('请勾选数据');
            return;
        }
        var rows = $('#limitGrid').datagrid('getChecked');
        $('.limit-nameversion').hide();
        updateRow(rows[0]);
    });

    function showLimitType() {
        var msg = limitRemark + '<br>' + tokenRemark;
        MsgUtil.msg(msg, '策略说明');
    }

    $('#limitSearchBtn').click(function () {
        search();
    });

    $('#limitSearchFrm').keyup(function (e) {
        if (e.keyCode == 13) {
            search();
        }
        return false;
    }).keydown(function (e) {
        if (e.keyCode == 13) {
            return false;
        }
    });

    $('#limitSaveBtn').click(function () {
        var data = $('#limitBaseFrm').form('getData');
        if (data.status == 0) {
            doUpdate(data);
            return true;
        }
        var validateRet = true;
        if (data.limitType == LimitType.LIMIT) {
            var $frmLimit = $('#limitLmtFrm');
            validateRet = $frmLimit.form('validate');
            if (validateRet) {
                jq.extend(data, $frmLimit.form('getData'));
            }
        } else {
            var $frmToken = $('#limitTokenFrm');
            validateRet = $frmToken.form('validate');
            if (validateRet) {
                jq.extend(data, $frmToken.form('getData'));
            }
        }
        if (validateRet) {
            doUpdate(data);
        }
    });

    function doUpdate(data) {
        data.app = app;
        if (apiIds.length == 0) {
            apiIds = [data.apiId];
        }
        data.apiIds = apiIds;
        ApiUtil.post('app.limit.update', data, function (resp) {
            $('#limitWin').dialog('close');
            $('#limitGrid').datagrid('reload').datagrid('uncheckAll');
        });
    }

    $('.limit-type').click(function () {
        displayLimitTypeFieldset(this.value);
    });

    $('.limit-status').click(function () {
        displayLimitTypeArea(this.value);
    });

    function search() {
        var data = $('#limitSearchFrm').form('getData');
        $('#limitGrid').datagrid('load', data);
    }

    function displayLimitTypeFieldset(limitType) {
        if (limitType == LimitType.LIMIT) {
            $('#LIMIT_ID').show().siblings('fieldset').hide();
        } else {
            $('#LIMIT_ID').hide().siblings('fieldset').show();
        }
    }

    function displayLimitTypeArea(status) {
        if (status == 1) {
            $('#limitArea').show();
        } else {
            $('#limitArea').hide();
        }
    }

    function updateRow(row) {
        $('#limitBaseFrm').form('load', row);
        $('#limitLmtFrm').form('load', row);
        $('#limitTokenFrm').form('load', row);
        displayLimitTypeArea(row.status);
        displayLimitTypeFieldset(row.limitType);
        $('#limitWin').dialog('open');
    }

    function initTextNumberEvent() {
        $('.text-number').each(function (i,el) {
            $(el).textbox('textbox').on('keyup', function () {
                if (this.value.length == 1) {
                    this.value = this.value.replace(/[^1-9]/g, '')
                } else {
                    this.value = this.value.replace(/\D/g, '')
                }
            }).on('afterpaste',function () {
                if (this.value.length == 1) {
                    this.value = this.value.replace(/[^1-9]/g, '')
                } else {
                    this.value = this.value.replace(/\D/g, '')
                }
            });
        });
    }

    return {
        init: function () {
            initLimitGrid();
            initTextNumberEvent();
        }
        , update: function (index) {
            apiIds = [];
            var row = $('#limitGrid').datagrid('getRow', index);
            $('.limit-nameversion').show();
            updateRow(row);
        }
        , showLimitType: function () {
            showLimitType();
        }
    }
})();