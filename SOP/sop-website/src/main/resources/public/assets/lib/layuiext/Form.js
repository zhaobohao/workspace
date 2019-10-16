/**
 * 表单插件，使用方法：
 * var myform = layui.Form('formId');
 * myform.setData({...})
 *
 * var data = myform.getData();
 *
 * var name = myform.getData('name')
 */
layui.define(function (exports) {

    /**
     * form = new Form('formId');
     * @param formId
     * @constructor
     */
    var Form = function (formId) {
        this.$form = $('#' + formId);
        this.parseForm(this.$form);
    }

    Form.prototype = {
        fire: function (eventName, data) {
            var handler = this['on' + eventName];
            handler && handler(data);
        }
        , opt: function (attr) {
            return this[attr];
        }
        /**
         * @private
         */
        , parseForm: function ($form) {
            var that = this;
            this.form = $form[0];
        }

        , getEls: function () {
            return this.$form.find('input,select,textarea');
        }

        /**
         * 同load(data)
         */
        , setData: function (data) {
            this.loadData(data);
        }

        /**
         * @private
         */
        , loadData: function (data) {
            this.reset();
            for (var name in data) {
                var val = data[name];
                var $el = this.$form.find('[name="' + name + '"]');

                $el.each(function () {
                    var _$el = $(this);
                    if (_$el.is(':radio') || _$el.is(':checkbox')) {
                        _$el.prop('checked', false);
                        var elVal = _$el.val();

                        if ($.isArray(val)) {
                            for (var i = 0, len = val.length; i < len; i++) {
                                if (elVal == val[i]) {
                                    _$el.prop('checked', true);
                                }
                            }
                        } else {
                            _$el.prop('checked', elVal == val);
                        }

                    } else {
                        _$el.val(val);
                    }

                });
            }
        }
        /**
         * 清除表单中的值,清除错误信息
         */
        , clear: function () {
            this.getEls().each(function () {
                var _$el = $(this);
                if (_$el.is(':radio') || _$el.is(':checkbox')) {
                    this.checked = false;
                } else {
                    this.value = '';
                }

                var msg = _$el.data('msg');
                if (msg) {
                    msg.text('');
                }

            });
        }
        /**
         * 重置表单
         */
        , reset: function () {
            var form = this.form;
            if (form && form.reset) {
                form.reset();
            } else {
                this.clear();
            }
        }
        /**
         * 获取表单数据,如果有fieldName参数则返回表单对应的值<br>
         * var id = form.getData('id') 等同于 var id = form.getData().id;
         * @param {String} fieldName
         * @return {Object} 返回JSON对象,如果有fieldName参数,则返回对应的值
         */
        , getData: function (fieldName) {
            var that = this;
            var data = {};

            this.getEls().each(function () {
                var value = that._getInputVal($(this));
                if (value) {
                    var name = this.name;
                    var dataValue = data[name];
                    if (dataValue) {
                        if ($.isArray(dataValue)) {
                            dataValue.push(value);
                        } else {
                            data[name] = [dataValue, value];
                        }
                    } else {
                        data[name] = value;
                    }
                }
            });

            if (typeof fieldName === 'string') {
                return data[fieldName];
            }

            return data;
        }
        , _getInputVal: function ($input) {
            if ($input.is(":radio") || $input.is(":checkbox")) {
                if ($input.is(':checked')) {
                    return $input.val();
                }
            } else {
                return $input.val();
            }
        }

    }

    exports('Form', function (formId) {
        return new Form(formId);
    });
});