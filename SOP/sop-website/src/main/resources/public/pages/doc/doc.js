var docEvent = {
    bind: function (fn) {
        this.onSelectDocItem = fn;
    },
    onSelectDocItem: function () {}
};
layui.config({
    base: '../../assets/lib/layuiext/module/'
}).extend({
    treetable: 'treetable-lay/treetable'
}).use(['element', 'form', 'treetable'], function(){ //加载code模块
    var form = layui.form;
    var treetable = layui.treetable;
    var $ = layui.jquery;

    // key:module
    var docItemStore = {};
    
    function initDocModules() {
        $.getJSON(SopConfig.url + '/doc/getDocBaseInfo', function (baseInfo) {
            var html = [];
            var docInfoList = baseInfo.docInfoList;
            for (var i = 0; i < docInfoList.length; i++) {
                var docInfo = docInfoList[i];
                var selected = i === 0 ? 'selected="selected"' : '';
                var title = docInfo.title;
                html.push('<option value="' + title + '" ' + selected + '>' + title + '</option>');
            }
            $('#moduleList').html(html.join(''));
            $('.url-prod').text(baseInfo.urlProd);
            $('.url-sandbox').text(baseInfo.urlSandbox);
            form.render('select');

            if (docInfoList && docInfoList.length > 0) {
                selectDocInfo(docInfoList[0].title);
            }
        })
    }
    
    function selectDocInfo(title) {
        $.getJSON(SopConfig.url + '/doc/docinfo/' + title, function (docInfo) {
            var moduleList = docInfo.docModuleList;
            var html = [];
            var firstItem;
            for (var j = 0; j < moduleList.length; j++) {
                var module = moduleList[j];
                var docItems = module.docItems;
                html.push('<li><h2>' + module.module + '</h2></li>');
                for (var i = 0; i < docItems.length; i++) {
                    var docItem = docItems[i];
                    var first = j === 0 && i === 0;
                    if (first) {
                        firstItem = docItem;
                    }
                    docItemStore[docItem.nameVersion] = docItem;
                    /*
                    <li class="site-tree-noicon layui-this">
                    <a href="/">
                        <cite>统一收单交易退款查询</cite>
                    </a>
                </li>
                     */
                    html.push('<li class="site-tree-noicon" nameversion="'+docItem.nameVersion+'">');
                    html.push('<a href="#"><cite>' + docItem['summary'] + ' ' + docItem.version + '</cite></a>')
                }
            }

            $('#docItemTree').html(html.join(''));
            if (firstItem) {
                selectDocItem(firstItem.nameVersion);
            }
        })
    }

    function initEvent() {
        form.on('select(moduleListFilter)', function (data) {
            selectDocInfo(data.value);
        });
        $('#docItemTree').on('click', 'li', function () {
            var $li = $(this);
            selectDocItem($li.attr('nameversion'));
        })
    }

    function selectDocItem(nameVersion) {
        var docItem = docItemStore[nameVersion];
        if (docItem) {
            docEvent.onSelectDocItem(docItem, layui);
        }
    }

    initDocModules();
    initEvent();
});