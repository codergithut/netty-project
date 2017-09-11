<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登录</title>
    <link rel="stylesheet" type="text/css" href="./static/css/lib/normalize.css" />
    <link rel="stylesheet" type="text/css" href="./static/css/lib/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="./static/css/user.css" />

    <link rel="stylesheet" type="text/css" href="./static/css/fonts/FontAwesome.otf" />
    <link rel="stylesheet" type="text/css" href="./static/css/fonts/FontAwesome-webfont.eot" />
    <link rel="stylesheet" type="text/css" href="./static/css/fonts/FontAwesome-webfont.svg" />
    <link rel="stylesheet" type="text/css" href="./static/css/fonts/FontAwesome-webfont.ttf" />
    <link rel="stylesheet" type="text/css" href="./static/css/fonts/FontAwesome-webfont.woff" />
    <link rel="stylesheet" type="text/css" href="./static/css/fonts/FontAwesome-webfont.woff2" />

    <link rel="stylesheet" href="./static/css/normalize.css" />
    <style>
        header {background: #51B7EC;padding: 20px 0}
        h4 {margin: 0;text-align: center;color: #FFFFFF;}
        .content .box {width: 500px;margin: 0 auto;}
        .box {text-align: center;}
         .content .box h3 {font-weight: normal;color: #333333;}
        .content .box p {color: #666666;}
        .content .img {padding: 2px;border:1px solid #D1D1D1;display: inline-block;}
        .content .img img {width: 160px;}
        footer {text-align: center;padding: 25px 0;font-size: 12px;}
        footer a {padding: 0 6px;}
    </style>


    <script src="./static/js/lib/jquery-3.1.1.min.js"></script>
    <script src="./static/js/lib/namespace.js"></script>
    <script src="./static/js/lib/plugin.js"></script>
</head>

<div>
    <input type = "hidden" id="guard" value="${ResourceID}"/>
    <input type = "hidden" id="redirect" value="${redirect}"/>
</div>

<body id="tab">
    <div class="user">
        <div class="user-box">
            <div class="user-login">
                <h3 class="user-login-title">使用账户登录</h3>
                <form id="userLogin">
                    <div class="user-login-account user-style-input">
                        <span class="fa fa-envelope fa-lg"></span>
                        <input type="text" id="loginEmail" name="id" placeholder="输入账号" />
                    </div>
                    <div display="none">
                    </div>
                    <div class="user-login-password user-style-input">
                        <span class="fa fa-key fa-lg"></span>
                        <input type="password" id="loginPassword" name="password" id = "password" placeholder="输入密码" />
                    </div>

                    <input type="submit" class="user-login-submit" value="登录" />
                </form>
                <div class="user-scan-a"><a>二维码扫描登录</a></div>
                <div class="user-login-register">没有账户 ? 点击此处 <a>注册</a></div>
            </div>
        </div>
        <footer>a331.info .INC © All rights reserved</footer>
    </div>

    <script src="./static/js/user.js"></script>
    <script type="text/javascript">
        function chageTab(){
           var tab = '<header><h4>快速登录</h4></header><div class="content"><div class="box"><h3>快速安全登录</h3><p>请使用手机版扫描二维码</p><div class="img"><img src="./static/img/test.jpg" /></div></div></div><footer><a>账号密码登录</a>|<a>注册新账号</a></footer>'
           $("#tab").children().remove();
           $("#tab").append(tab);
        }

        $(document).ready(function(){
            setInterval(function(){
                var guard = $("#guard").val();
                $.ajax({
                    url: "http://localhost:8080/getToken?guard=" + guard,
                    context: document.body,
                    async:true,
                    dataType:'json',
                    success: function(data){
                        if(data != null) {
                            for(var item in data){
                                window.location.href=$("#redirect").val() + "?token=" + data[item];
                            }
                        }
                    }
                });
            },1000)
        });
    </script>
</body>
</html>
