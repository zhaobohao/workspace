/**
 * jquery easyui扩展
 */

$.extend($.fn.tree.methods, {
    /**
     * 获取勾选的id，返回id数组
     * @param idName id名称，默认为‘id’
     */
    getCheckedId: function($tree, idName) {
        var ids = [];
        idName = idName || 'id';
        var nodes = $tree.tree('getChecked');	// get checked nodes
        if(nodes) {
            for (var i = 0; i < nodes.length; i++) {
                var node = nodes[i];
                var id = node[idName];
                if(id) {
                    ids.push(id);
                }
            }
        }
        return ids;
    }
});
