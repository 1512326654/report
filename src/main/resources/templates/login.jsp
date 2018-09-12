<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>江湖中的小妖</title>
    <link href="/css/AdminLoginStyle.css" rel="stylesheet" type="text/css" media="all"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</head>
<body>
<!-- contact-form -->
<div class="message warning">
    <div class="inset">
        <div class="login-head">
            <h1>江湖中的小妖-总后台V1.8</h1>
            <%--<div class="alert-close"> </div>--%>
        </div>
        <form action="/admin/login" method="post">
            <li>
                <input type="text" name="adUsername" class="text" value="请输入用户名" onfocus="this.value = '';"
                       onblur="if (this.value == '') {this.value = '请输入用户名';}"><a href="#" class=" icon user"></a>
            </li>
            <div class="clear"></div>
            <li>
                <input type="password" name="adPassword" value="请输入密码" onfocus="this.value = '';"
                       onblur="if (this.value == '') {this.value = '请输入密码';}"> <a href="#" class="icon lock"></a>
            </li>
            <div class="clear"></div>
            <div class="submit">
                <input type="submit" value="登录">
                <h4><a href="#">Lost your Password ?</a></h4>
                <div class="clear"></div>
            </div>
        </form>
    </div>
</div>
</div>
<div class="clear"></div>
<!--- footer --->
<div class="footer">
    <p>Copyright &copy; 2018.</p>
</div>
</body>
</html>