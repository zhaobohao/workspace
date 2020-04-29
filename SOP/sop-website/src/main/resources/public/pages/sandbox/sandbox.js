
docEvent.bind(function (docItem,layui) {
    selectItem(docItem, layui);
});

layui.use('form', function(){
    var form = layui.form;
    //监听提交
    form.on('submit(formSend)', function(data){
        doTest();
        return false;
    });
});

var $body = $('body');
var treetable;
var currentItem;

function selectItem(docItem, layui) {
    var form = layui.form;
    currentItem = docItem;
    resetResultDiv();
    var nameVersion = docItem.nameVersion;
    var multiple = docItem.multiple;
    treetable = treetable || layui.treetable;
    $('.sop-name').text(docItem.name);
    $('.sop-version').text(docItem.version);
    $('.sop-summary').text(docItem['summary']);
    $('.sop-description').text(docItem.description || docItem['summary']);

    createRequestParameter(docItem);

    $('#multipleDiv').css('display', multiple ? 'block' : 'none');

    $('#httpMethodList').html(buildHttpMethodOptions(docItem));

    var $li = $('#docItemTree').find('li[nameversion="'+nameVersion+'"]');
    $li.addClass('layui-this').siblings().removeClass('layui-this');

    form.render();
    InputCache.init();
}

function buildHttpMethodOptions(docItem) {
    var methodList = docItem.httpMethodList;
    var html = []
    for (var i = 0; i < methodList.length; i++) {
        var method = methodList[i];
        html.push('<option value="' + method + '"> ' + method.toUpperCase() + ' </option>');
    }
    return html.join('');
}

function createRequestParameter(docItem) {
    var data = buildTreeData(docItem.requestParameters);
    createTreeTable('treeTableReq', data);
}

function buildTreeData(parameters, parentId,parentMoudle) {
    var data = [];
    parentId = parentId || 0;
    parentMoudle=parentMoudle||"";
    for (var i = 0; i < parameters.length; i++) {
        var parameter = parameters[i];
        parameter.id = parentId * 100 + (i + 1);
        parameter.parentId = parentId;
        parameter.module=parentMoudle;
        data.push(parameter);
        var refs = parameter.refs;
        if (refs && refs.length > 0) {
            var childData = buildTreeData(refs, parameter.id,parentMoudle===""?parameter.name:parentMoudle+"."+parameter.name);
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
        firstTemplet: function (row) {
            var required = row.required ? '<span style="color: red;">*</span> ' : '';
            return required + row.name;
        },
        cols: [[
            {field: 'name', title: '参数',width: 200}
            ,{field: 'val', title: '值', width: 300, templet:function (row) {
                    var id = currentItem.nameVersion + '-' + row.name;
                    var requiredTxt = row.required ? 'required  lay-verify="required"' : '';
                    var module = row.module;
                    var type = row.type == 'file' ? 'file' : 'text';
                    var attrs = [
                        'id="' + id + '"'
                        , 'name="'+row.name+'"'
                        , 'class="layui-input test-input"'
                        , 'type="' + type + '"'
                        , requiredTxt
                        , 'module="'+module+'"'
                    ];

                    return !row.refs ? '<input ' + attrs.join(' ') + '/>' : '';
                }}
            ,{field: 'description', title: '描述'}
        ]]
    });
}

function doTest() {
    var method = currentItem.name;
    var version = currentItem.version;
    var data = {
        appId: $('#appId').val()
        , privateKey: $('#privateKey').val()
        , publicKey: $('#publicKey').val()
        , method: method
        , version: version
        , httpMethod: $('#httpMethodList').val()
    };
    var uploadFileObjects = getUploadFileObjects();
    var $inputs = $body.find('.test-input');
    var bizContent = {};
    $inputs.each(function () {
        var module = $(this).attr('module');
        if (module) {
            var bizContentModule=bizContent;
            module.split(".").forEach(function(val,idx,arr){
                if(!bizContentModule[val])
                {
                    if(val.indexOf("["))
                    {
                        bizContentModule[val]=[{}];
                    }else{
                        bizContentModule[val]={};
                    }
                }
                if(val.indexOf("[")){
                    bizContentModule=bizContentModule[val][0];
                }else{
                    bizContentModule=bizContentModule[val];
                }
            });
            putVal(moduleObj, this);
        } else {
            putVal(bizContent, this);
        }
    });
    data.bizContent = JSON.stringify(bizContent);
    if (isDownloadRequest(currentItem)) {
        data.isDownloadRequest = true;
        downloadFile(data);
        // window.open()
        return;
    }
    // 确定文件数量，并且知道参数名称
    if (uploadFileObjects.length > 0) {
        var formData = new FormData();
        for (var i = 0; i < uploadFileObjects.length; i++) {
            var fileInput = uploadFileObjects[i];
            formData.append(fileInput.name, fileInput.file);
        }
        postFile(data, formData);
    } else {
        postJson(data);
    }
}

function isDownloadRequest(currentItem) {
    var produces = currentItem.produces;
    if (!produces) {
        return false;
    }
    for (var i = 0; i < produces.length; i++) {
        var produce = produces[i];
        if (produce.indexOf('application/octet-stream') > -1) {
            return true;
        }
    }
    return false;
}

function putVal(obj, input) {
    if (input.type == 'text') {
        obj[input.name] = input.value;
    }
}

function getUploadFileObjects() {
    var fileObjects = [];
    $body.find('input[type=file]')
        .each(function () {
            var $input = $(this);
            var name = this.name;
            var fileList = $input.prop('files');
            var multiple = $input.prop('multiple');
            if (multiple) {
                for (var i = 0; i < fileList.length; i++) {
                    fileObjects.push({name: name + i, file: fileList[i]});
                }
            } else {
                fileObjects.push({name: name, file: fileList[0]});
            }
        });
    return fileObjects;
}

function postJson(data) {
    $.ajax({
        url: SopConfig.url + '/sandbox/test'
        , dataType: 'json'
        , data: data
        , method: 'post'
        , success: successHandler
        , error: errorHandler
    });
}

function postFile(data, formData) {
    for(var key in data) {
        formData.append(key, data[key]);
    }
    $.ajax({
        url: SopConfig.url + '/sandbox/test'
        , data: formData
        // ajax2.0可以不用设置请求头，但是jq帮我们自动设置了，这样的话需要我们自己取消掉
        , contentType: false
        // 取消帮我们格式化数据，是什么就是什么
        , processData: false
        , method: 'post'
        , success: successHandler
        , error: errorHandler
    });
}

function downloadFile(data) {
    $('.dl-form').remove();
    var url = SopConfig.url + '/sandbox/test';
    var form = $('<form></form>')
        .addClass('dl-form')
        .attr("action", url)
        .attr("method", "post");

    for(var key in data) {
        form.append($("<input>")
            .attr("type", "hidden")
            .attr("name", key)
            .attr("value", data[key]));
    }
    form.appendTo('body').submit();
}

function successHandler(resp) {
    setReqInfo(resp);
    showRespnfo(resp.apiResult);
}

function errorHandler(xhr,status,error) {
    // {"timestamp":"2019-06-19 15:57:36","status":500,"error":"Internal Server Error","message":"appId不能为空","path":"/sandbox/test"}
    var errorData = xhr.responseJSON;
    if (errorData) {
        setReqInfo('');
        showRespnfo(errorData.message);
    }
}

function showRespnfo(info) {
    var json = formatJson(info);
    setRespInfo(json);
    $('#resultDiv').show();
}

function setReqInfo(resp) {
    var txt = '';
    if (resp) {
        var html = [];
        html.push('【请求参数】：' + resp.params);
        html.push('【待签名内容】：' + resp.beforeSign);
        html.push('【签名(sign)】：' + resp.sign);
        txt = html.join('\r\n')
    }
    $('#req-info-result').val(txt);
}

function setRespInfo(info) {
    $('#resp-info-result').text(info);
}

function resetResultDiv() {
    setReqInfo({
        beforeSign: '',
        params: '',
        privateKey: '',
        publicKey: '',
        sign: ''
    });
    setRespInfo('');
    $('.resp-info-content')
        .addClass('layui-show')
        .siblings().removeClass('layui-show');

    $('.resp-info')
        .addClass('layui-this')
        .siblings().removeClass('layui-this');

    $('#resultDiv').hide();
}
