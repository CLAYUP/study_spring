var TENCENT_COS;

function load_properties(path) {
    jQuery.i18n.properties({
        name: 'SaveImagePath', path: path + '/scripts/pro/', mode: 'map', callback: function () {
            TENCENT_COS = $.i18n.prop("url");
        }
    });
}
String.prototype.format = function () {
    var values = arguments;
    return this.replace(/\{(\d+)\}/g, function (match, index) {
        if (values.length <= index) {
            return "";
        } else {
            return values[index];
        }
    });
};