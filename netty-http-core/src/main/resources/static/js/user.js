//登录
$(function () {
    (function (c) {
        c.box = $(".user");
        c.login = c.box.find("#userLogin");

        c.register = $("#userRegister");

        //尺寸调节
        c.resize = function () {
            if (chat.size.height >800){
                c.box.height(chat.size.height+"px");
            }else {
                c.box.height(800+"px");
            }
        };
    })(window.chat.user = window.chat.user || {});

    chat.user.resize();
    $(window).resize(function () {
        chat.user.resize();
    });

    //提交登录信息
    chat.user.login.on("click",".user-login-submit",function (e) {
        e.preventDefault();
        var id = $("#loginEmail").val();
        var password = $("#loginPassword").val();
        var guard = $("#guard").val();
        $.ajax({
            url:'http://localhost:8080/login',
            method :'GET',
            dataType:'json',
            data:{id:id,password:password,guard:guard},
        }).done(function(data) {
            //登录跳转
        });
    });

    //提交注册信息
    chat.user.register.on("click",".user-register-submit",function (e) {
       e.preventDefault();
       var data = chat.user.register.serializeArray();
        // $.ajax({
        //     url: "请求地址",
        //     data:data,
        // }).done(function(data) {
        //     //完成后的操作data为返回的数据
        // });
    });

    //注册转登录
    chat.user.box.on("click",".user-register-login-a",function (e) {
       chat.user.box.find(".user-login").show().end()
           .find(".user-register").hide();
    });

    //注册转登录
    chat.user.box.on("click",".user-scan-a",function (e) {
        chageTab();
    });


    //登录转注册
    chat.user.box.on("click",".user-login-register-a",function (e) {
        chat.user.box.find(".user-register").show().end()
            .find(".user-login").hide();
    });
});