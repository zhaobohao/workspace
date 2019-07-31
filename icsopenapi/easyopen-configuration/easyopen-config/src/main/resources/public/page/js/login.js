$(function () {
    function login() {
        var b = $('#form').form('validate');
        if(b) {
            var data = $('#form').form('getData');
            var pwd = data.password;
            if(!pwd) {
                alert('密码不能为空');
                return false;
            }
            pwd = hex_md5(pwd);
            ApiUtil.post('nologin.admin.easyui.login',{
                username:data.username,
                password:pwd
            },function(resp){
                ApiUtil.setAccessToken(resp.data);
                window.location = 'main.html';
            });
        }
    }

    $('#loginBtn').click(function(){
        login();
        return false;
    });

    $('#form').on('keyup', function(event) {
        if (event.keyCode == "13") {
            login();
        }
    });
});