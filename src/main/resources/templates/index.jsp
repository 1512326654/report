<%--
  Created by IntelliJ IDEA.
  User: 董尧
  Date: 2018/6/29
  Time: 17:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>主持人报名</title>
  <!-- Bootstrap -->
  <link href="css/bootstrap.css" rel="stylesheet">
  <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <script type="text/javascript" src="js/layer/layer.js"></script>
  <![endif]-->
</head>
<body>
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <p class="navbar-brand" id="sys_title"><!--获取--></p></div>
  </div>
</nav>
<div class="container-fluid">
  <div class="row">
    <div class="col-md-6 col-md-offset-3">
      <h2 class="text-center" id="sys_title1"><!--获取--></h2><br>
      <h3 class="text-center" id="sys_title2"><!--获取--></h3>
    </div>
  </div>
  <hr>
</div>
<div class="container">
  <div class="row">
    <div class="text-center col-md-6 col-md-offset-3">
      <form action="ApplyServlet" method="post">
        <input id="name" name="name" type="text" class="form-control" placeholder="请输入你的名字" >
        <div class="row">
          <div class="col-md-6">&nbsp;</div>
          <div class="col-md-6">&nbsp;</div>
        </div>
        <button type="submit" class="btn btn-default">点击报名</button>
      </form>
    </div>
  </div>
  <div class="row">
    <div class="col-md-6">&nbsp;</div>
    <div class="col-md-6">&nbsp;</div>
  </div>
  <div class="row">
  </div>
</div>
<div class="row text-center">
  <div class="row">
    <div class="text-center col-md-6 col-md-offset-3">
      <h4></h4>
      <p id="sys_footer"><!--获取--></p>
      <p><a href="http://www.miibeian.gov.cn" id="sys_beian"><!--获取--></a></p>
    </div>
    <div style="width:300px;margin:0 auto; padding:20px 0;">
      <a target="_blank" href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=42110202000149" style="display:inline-block;text-decoration:none;height:20px;line-height:20px;"><img src="images/sec.png" style="float:left;"/><p id="sys_sbeian" style="float:left;height:20px;line-height:20px;margin: 0px 0px 0px 5px; color:#939393;"><!--获取o--></p></a>
    </div>
  </div>
  <hr>
</div>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="js/jquery-3.3.1.min.js"></script>

<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.js"></script>

<script type="text/javascript">
  $(function () {
      $.ajax({
          url:"../SystemServlet",
          type: "post",
          // contentType:"application/json",
          // data:JSON.stringify({"STR":1}),
          data:{
              operating:"selsysinfo"
          },
          success: function (result) {
              var dataObj = JSON.parse(result);
              $("#sys_title").text(dataObj.title);
              $("#sys_title1").text(dataObj.applytitle1);
              $("#sys_title2").text(dataObj.applytitle2);
              $("#sys_footer").text(dataObj.footer);
              $("#sys_beian").text(dataObj.beian);
              $("#sys_sbeian").text(dataObj.sbeian);
          },
          error: function (res) {
          }
      });
      $(".btn-default").click(function () {
          var name = $("#name").val();
          if (name == ""){
              layer.msg('姓名不能为空', {icon: 5});
              setTimeout(function () {
                  return false;
              },3000)
          }
      })

  })
</script>
</body>
</html>
