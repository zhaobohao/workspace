/**
 * 自动保存输入框的值
 * @type {{init: InputCache.init}}
 */
var InputCache = {
    init: function () {
        var storage = window.localStorage;
        if (storage) {
            var dataKey = location.host;

            $('body').find('input[type="text"]').unbind().change(function () {
                var id = this.id;
                if (id) {
                    var data = getCache();
                    data[id] = this.value;
                    setCache(data);
                }
            });

            function getCache() {
                var jsonStr = storage.getItem(dataKey);
                if (!jsonStr) {
                    jsonStr = '{}';
                }
                return JSON.parse(jsonStr);
            }

            function setCache(jsonObj) {
                var jsonStr = JSON.stringify(jsonObj);
                storage.setItem(dataKey, jsonStr);
            }

            // load data
            var cache = getCache();
            var docu = document;

            for (var id in cache) {
                var dom = docu.getElementById(id);
                if (dom) {
                    var oldVal = dom.value;
                    var val = cache[id] || oldVal;
                    dom.value = val;
                }
            }
        }
    }
}

