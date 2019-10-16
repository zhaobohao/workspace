
docEvent.bind(function (docItem,layui) {
    selectItem(docItem, layui);
});

var treetable;

function selectItem(docItem, layui) {
    var nameVersion = docItem.nameVersion;
    treetable = treetable || layui.treetable;
    $('.sop-name').text(docItem.name);
    $('.sop-version').text(docItem.version);
    $('.sop-summary').text(docItem['summary']);
    $('.sop-description').text(docItem.description || docItem['summary']);

    createRequestParameter(docItem);
    createResponseParameter(docItem);
    createResponseCode(docItem);

    var $li = $('#docItemTree').find('li[nameversion="'+nameVersion+'"]');
    $li.addClass('layui-this').siblings().removeClass('layui-this');

    $('#httpMethodList').text(docItem.httpMethodList.join(' / ').toUpperCase());
}

function createRequestParameter(docItem) {
    var data = buildTreeData(docItem.requestParameters);
    createTreeTable('treeTableReq', data);
}

function createResponseParameter(docItem) {
    var data = buildTreeData(docItem.responseParameters);
    createTreeTable('treeTableResp', data);
}

function buildTreeData(parameters, parentId) {
    var data = [];
    parentId = parentId || 0;
    for (var i = 0; i < parameters.length; i++) {
        var parameter = parameters[i];
        parameter.id = parentId * 100 + (i + 1);
        parameter.parentId = parentId;
        data.push(parameter);
        var refs = parameter.refs;
        if (refs && refs.length > 0) {
            var childData = buildTreeData(refs, parameter.id);
            data = data.concat(childData);
        }
    }
    return data;
}

function createTreeTable(id, data) {
    var el = '#' + id;
    treetable.render({
        elem: el,
        treeColIndex: 0,
        treeSpid: 0,
        treeIdName: 'id',
        treePidName: 'parentId',
        treeDefaultClose: false,
        treeLinkage: false,
        data: data,
        page: false,
        cols: [[
            {field: 'name', title: '参数'}
            ,{field: 'type', title: '类型', width: 80}
            ,{field: 'required', title: '是否必填', width: 100, templet:function (row) {
                    return row.required ? '<span style="color: red;">是</span>' : '否';
                }}
            ,{field: 'maxLength', title: '最大长度', width: 100}
            ,{field: 'description', title: '描述', width: 200}
            ,{field: 'paramExample', title: '示例值', width: 200}
        ]]
    });
}

function createResponseCode(docItem) {
    var method = docItem.name.replace(/\./g, '_');
    var responseParameters = docItem.responseParameters;
    var bizResult = buildResult(responseParameters);
    var json = '{\n' +
        '    "'+method+'_response": {\n' +
        '        "code": "10000",\n' +
        '        "msg": "Success",\n' +
        bizResult +
        '    }' +
        '}';
    json = formatJson(json);
    $('#responseExampleJson').text(json);

    var errorJson = '{\n' +
        '    "'+method+'_response": {\n' +
        '        "code": "20000",\n' +
        '        "msg": "Service is temporarily unavailable",\n' +
        '        "sub_code": "isp.unknown-error",\n' +
        '        "sub_msg": "服务暂不可用"\n' +
        '    }' +
        '}';
    errorJson = formatJson(errorJson);
    $('#responseErrorJson').text(errorJson);
}

function buildResult(parameters) {
    var result = [];
    for (var i = 0; i < parameters.length; i++) {
        var parameter = parameters[i];
        result.push('\"'+parameter.name+'\": ' + buildExample(parameter))
    }
    return result.join(",");
}

function buildExample(parameter) {
    var refs = parameter.refs;
    if (refs) {
        // {...}
        var content = '{' + buildResult(refs) + '}';
        if (parameter.type == 'array') {
            // [{...}]
            content = '[' + content + ']';
        }
        return content;
    } else {
        return '\"' + parameter.example + '\"';
    }
}
