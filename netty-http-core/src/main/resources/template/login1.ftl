<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登录</title>
    <link rel="stylesheet" type="text/css" href="static/css/lib/normalize.css" />
    <link rel="stylesheet" type="text/css" href="static/css/lib/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="static/css/user.css" />

    <link rel="stylesheet" type="text/css" href="static/css/fonts/FontAwesome.otf" />
    <link rel="stylesheet" type="text/css" href="static/css/fonts/FontAwesome-webfont.eot" />
    <link rel="stylesheet" type="text/css" href="static/css/fonts/FontAwesome-webfont.svg" />
    <link rel="stylesheet" type="text/css" href="static/css/fonts/FontAwesome-webfont.ttf" />
    <link rel="stylesheet" type="text/css" href="static/css/fonts/FontAwesome-webfont.woff" />
    <link rel="stylesheet" type="text/css" href="static/css/fonts/FontAwesome-webfont.woff2" />

    <script src="static/js/lib/jquery-3.1.1.min.js"></script>
    <script src="static/js/lib/namespace.js"></script>
    <script src="static/js/lib/plugin.js"></script>
</head>
<body>
    <div class="user">
        <div class="user-box">
            <div class="user-login">
                <h3 class="user-login-title">使用账户登录</h3>
                <form id="userLogin">
                    <div class="user-login-account user-style-input">
                        <span class="fa fa-envelope fa-lg"></span>
                        <input type="email" id="loginEmail" name="loginEmail" placeholder="输入账号" />
                    </div>
                    <div class="user-login-password user-style-input">
                        <span class="fa fa-key fa-lg"></span>
                        <input type="password" id="loginPassword" name="loginPassword" placeholder="输入密码" />
                    </div>
                    <div class="user-login-remember">
                        <input type="checkbox" id="loginRemember" name="loginRemember" value="1" />
                        <label title="一周免登录">保持登录状态</label>
                    </div>
                    <input type="submit" class="user-login-submit" value="登录" />
                </form>
                <div class="user-login-register">没有账户 ? 点击此处 <a class="user-login-register-a">注册</a></div>
            </div>

            <div class="user-register">
                <h3 class="user-register-title">创建新用户</h3>
                <form id="userRegister">
                    <div class="user-register-account user-style-input">
                        <span class="fa fa-user"></span>
                        <input type="text" id="registerUser" name="registerUser" placeholder="输入账号" />
                    </div>
                    <div class="user-register-account user-style-input">
                        <span class="fa fa-envelope fa-lg"></span>
                        <input type="email" id="registerEmail" name="registerEmail" placeholder="输入账号" />
                    </div>
                    <div class="user-register-password user-style-input">
                        <span class="fa fa-key fa-lg"></span>
                        <input type="password" id="registerPassword" name="registerPassword" placeholder="输入密码" />
                    </div>
                    <div class="user-register-rePassword user-style-input">
                        <span class="fa fa-key fa-lg"></span>
                        <input type="password" id="registerRePassword" name="registerRePassword" placeholder="重新输入密码" />
                    </div>
                    <input type="submit" class="user-register-submit" value="注册" />
                </form>
                <div class="user-register-login">已经注册 ? 点击此处 <a class="user-register-login-a">登录</a></div>
            </div>
        </div>
        <footer>a331.info .INC © All rights reserved</footer>

    </div>

    <script src="static/js/user.js"></script>
</body>
</html>