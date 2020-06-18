<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>${projectName}API Documentation</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/font-awesome@4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/google-code-prettify@1.0.5/bin/prettify.min.css">
    <link rel="stylesheet" href="style.css">
</head>
<body onload="PR.prettyPrint()">
<nav class="navbar">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="index.html">
                ${projectName}
            </a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="https://github.com/YeDaxia/JApiDocs" target="_blank">GitHub</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">${currentApiVersion}<span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <#list apiVersionList as version>
                            <#if version != currentApiVersion>
                                <li><a href="../${version}/index.html">${version}</a></li>
                            </#if>
                        </#list>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="book with-summary">
    <div class="book-summary">
        <div class="search-box form-group">
            <input type="text" class="form-control" id="inputSearch" placeholder="${i18n.getMessage('searchPlaceholder')}">
            <span class="glyphicon glyphicon-search form-control-feedback" aria-hidden="true"></span>
        </div>
        <div id="accordion" class="catalog">
            <#list controllerNodeList as ctrolNode>
                <div class="panel">
                    <div id="heading${ctrolNode?index}" data-parent="#accordion" class="catalog-title" data-toggle="collapse"
                         aria-expanded="true" data-target="#collapse${ctrolNode?index}" aria-controls="collapse${ctrolNode?index}">
                        <i class="glyphicon glyphicon-align-justify"></i> ${ctrolNode.description}
                    </div>
                    <div id="collapse${ctrolNode?index}" class="collapse <#if ctrolNode?index == 0>in </#if>" aria-labelledby="heading${ctrolNode?index}">
                        <#list ctrolNode.requestNodes as reqNode>
                            <a class="catalog-item" href="${reqNode.codeFileUrl}">
                                ${reqNode.description}
                            </a>
                        </#list>
                    </div>
                </div>
            </#list>
        </div>
    </div>
    <div class="book-body">
        <div class="body-inner">
            <div class="book-header">
                <div class="d-flex justify-content-between">
                    <a class="header-menu toggle-catalog" href="javascript:void(0)"><i
                                class="glyphicon glyphicon-align-justify"></i> ${i18n.getMessage('catalog')}</a>
                </div>
            </div>
            <div class="page-wrapper">
                <div class="page-inner">
                    <div class="main-content">
                        <h4 style="margin: 20px">${i18n.getMessage('doc.generate.tip')}</h4>
                        <div class="list-group" style="min-width: 200px">
                            <#list controllerNodeList as ctrolNode>
                                <a href="${ctrolNode.docFileName}" class="list-group-item">${ctrolNode.description}</a>
                            </#list>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/autocomplete.js/0/autocomplete.jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/google-code-prettify@1.0.5/bin/prettify.min.js"></script>
<script>

    var search_source_data = [
        <#list controllerNodeList as ctrolNode>
        <#list ctrolNode.requestNodes as reqNode>
        {name: '${ctrolNode.description}.${reqNode.description}', url: '${reqNode.codeFileUrl}'},
        </#list>
        </#list>
    ];


    $('.toggle-catalog').click(function () {
        $('.book').toggleClass('with-summary');
    });

    $('#inputSearch').autocomplete({hint: false}, [
        {
            source: function (query, callback) {
                var result = [];
                for(var i = 0; i !== search_source_data.length; i++){
                    if(search_source_data[i].name.indexOf(query) !== -1){
                        result.push(search_source_data[i]);
                    }
                }
                callback(result);
            },
            displayKey: 'name',
            templates: {
                suggestion: function (suggestion) {
                    return suggestion.name;
                }
            }
        }
    ]).on('autocomplete:selected', function (event, suggestion, dataset, context) {
        self.location = suggestion.url;
    });
</script>
</body>
</html>