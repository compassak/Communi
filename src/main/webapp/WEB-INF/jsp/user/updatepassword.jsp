<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>重置密码</title>
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
	//验证邮箱是否合法
	function checkEmail() {
		var mailAddress = $("#mailAddress").val();
	    var str = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
	    if (!str.test(mailAddress)) {
	        $("#mailAddress").val("");        
	         layer.tips('邮箱格式错误', '#mailAddress', {
	               tips: [3, '#0FA6D8'], time: 3000
	         });
	    }
	}
    function submitEmail(){
        var mailAddress = $('#mailAddress').val();
        //改为ajax提交邮箱
        if(mailAddress!=null&&mailAddress!=''){
           $.post('${pageContext.request.contextPath }/user/repswmail.do',{mailAddress:mailAddress},function(data){
               if(data.success){
                  alert('验证码已发送到邮箱，请注意查收');
               }else{
                  alert('验证码发送失败：');
                }
               },'json');
         }
    }
</script>
</head>
<body>
   ${message}
   <br>
   <form action="${pageContext.request.contextPath }/user/updatepassword.do" id="loginUser" method="post">
       <label>用&nbsp;&nbsp;户&nbsp;名：</label>
       <input type="text" placeholder="请输入用户名" name="userName" id="userName" class="list-input" onblur="checkName()"/><br>  
       <label>邮&nbsp;&nbsp;&nbsp;箱：</label>
       <input type="text" placeholder="请输入邮箱" name="mailAddress" id="mailAddress" class="list-input" onblur="checkMail()"/><br>     
       <label>新密&nbsp;&nbsp;&nbsp;码：</label>
       <input type="password" placeholder="请输入新密码" name="password" id="password" class="list-input" onblur="checkPassword()"/><br>
       <label>验&nbsp;&nbsp;证&nbsp;码：</label>
       <input type="text" placeholder="请输入验证码" name="authcode" id="authcode" class="list-input"/><input type="button" value="发邮件获取验证码" onclick="submitEmail();"><br>     	     
       <br>
       <button class="btn btn-success" style="width:120px;" >提交</button>           
   </form>
</body>
</html>