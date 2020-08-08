<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript" src="../js/jquery-1.8.3.js"></script>
<script src="../js/layer/layer.js"></script>
<script type="text/javascript">
//验证用户名是否合法
function checkName() {
	     var userName = $("#userName").val();
	        var str = /^[a-zA-Z\u4e00-\u9fa5]{2,12}$/;
	        if (!str.test(userName)) {
	            $("#userName").val("");        
	             layer.tips('请输入2~12位的中文或字母', '#userName', {
	                   tips: [3, '#0FA6D8'], time: 3000
	             });
	        }
	  }
//验证密码是否合法
function checkPassword() {
     var password = $("#password").val();
        var str = /^[A-Za-z0-9]{5,16}$/;
        if (!str.test(password)) {
            $("#password").val("");        
             layer.tips('请输入5~16位的字母或数字', '#password', {
                   tips: [3, '#0FA6D8'], time: 3000
             });
        }
  }
//生成新验证码
function chageCode(){
    $('#codeImage').attr('src','authCode.do?abc='+Math.random());//链接后添加Math.random，确保每次产生新的验证码，避免缓存问题。
}
//验证验证码是否为空
function checkCode() {
     var verifyCode = $("#verifyCode").val();
        if (verifyCode==null) {
            $("#verifyCode").val("");        
             layer.tips('验证码不能为空', '#verifyCode', {
                   tips: [3, '#0FA6D8'], time: 3000
             });
        }
  }
/*点击图片更换验证码事件*/
$(document).on("click","img",function(){
	$('#verifyCodeImage').attr('src','${pageContext.request.contextPath }/user/getVerifyCode.do?d=' + Math.random());
})
</script>
</head>
<title>欢迎登录</title>
<body>
       ${message}
       <form action="${pageContext.request.contextPath}/user/userlogin.do" id="loginUser" method="post">
             <label>用&nbsp;户&nbsp;&nbsp;名：</label>
             <input type="text" placeholder="请输入用户名"  name="userName" id="userName" class="list-input" onblur="checkName()"/><br>
             <label>密&nbsp;&nbsp;&nbsp;码：</label>
             <input type="password" placeholder="请输入登录密码" name="password" id="password" class="list-input" onblur="checkPassword()"/><br>
             <label>验&nbsp;证&nbsp;&nbsp;码：</label>
             <input type="text" id="verifyCode" name="verifyCode" placeholder="验证码" class="list-input" onblur="checkCode()" maxlength="4">
             <img src="${pageContext.request.contextPath }/user/getVerifyCode.do" width="50" height="20" id="verifyCodeImage">
             <br>
             <button class="btn btn-success" style="width:120px;" >登录</button>           
       </form>
       <br>
       <span><a href="${pageContext.request.contextPath }/user/repswpage.do">忘记密码？</a></span>
            没有账号？<span><a href="${pageContext.request.contextPath }/user/registerpage.do">注册</a></span>         
</body>
</html>