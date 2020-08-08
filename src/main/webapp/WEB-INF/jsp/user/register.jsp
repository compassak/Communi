<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<script type="text/javascript" src="../js/jquery-1.8.3.js"></script>
<script src="../js/layer/layer.js"></script>
    <meta charset="utf-8">
    <style>
         body{
             text-align:center
         }
         #divcss{
             margin:0 auto;
             border:1px solid #000;
             width:300px;
             height:100px;           
         }
    </style>
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
	      }else{	    	  	      
		     $.ajax({
		         type: "POST",      //请求方式
		          url: "${pageContext.request.contextPath }/user/findUserName.do",    
		          data: "userName="+userName,   //参数	        
		          success: function(data){  //返回值
		          if(data=="false"){
		        	  $("btn").attr({"disabled":"false"});
		             $('#nameLabe').text("用户名通过验证").css("color","green").css("font-size","10px");
	
		          }else{//不存在就显示labe
		        	  $("btn").attr({"disabled":"true"});
		             $('#nameLabe').text("用户名已存在").css("color","red").css("font-size","10px");
		             }   
		         }            
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
    }else{
	    $.ajax({
	        type: "POST",      //请求方式
	         url: "${pageContext.request.contextPath }/user/findMail.do",    
	         data: "mailAddress="+mailAddress,   //参数	        
	         success: function(data){  //返回值
	         if(data=="false"){
	        	$("btn").attr({"disabled":"false"});
	            $('#mailLabe').text("邮箱通过验证").css("color","green").css("font-size","10px");
	
	         }else{//不存在就显示labe
	        	$("btn").attr({"disabled":"true"});
	            $('#mailLabe').text("邮箱已存在").css("color","red").css("font-size","10px");
	            }   
	        }            
	   });
    }
}
//验证手机号是否合法
function checkPhone(){
	var telephoneNumber = $("#telephoneNumber").val();
    var str = /^(1)([0-9]{10})?$/;
    if (!str.test(telephoneNumber)) {
        $("#telephoneNumber").val("");        
         layer.tips('手机号码格式错误', '#telephoneNumber', {
               tips: [3, '#0FA6D8'], time: 3000
         });
    }
}
</script>
 
</head>
<body>   
    <div class="divcss">
          <form action="${pageContext.request.contextPath }/user/userregister.do" method="post">                 
                    <label>用&nbsp;户&nbsp;&nbsp;名：</label>               
                    <input type="text" name="userName" id="userName" placeholder="用户名" onblur="checkName()"/><label id="nameLabe"></label>
                    <br>        
                    <label>密&nbsp;&nbsp;&nbsp;码：</label>
                    <input type="password" name="password" id="password" placeholder="密码" onblur="checkPassword()"/>
                    <br>
                    <label>邮&nbsp;&nbsp;&nbsp;箱：</label>
                    <input type="text" name="mailAddress" id="mailAddress" placeholder="邮箱" onblur="checkEmail()"/><label id="mailLabe"></label>
                    <br>
                    <label>手&nbsp;机&nbsp;&nbsp;号：</label>
                    <input type="text" name="telephoneNumber" id="telephoneNumber" placeholder="手机号" onblur="checkPhone()"/>
                    <br>
                     <input type="submit" id="btn" value="注册" style="width:100px;">                         
            </form>
                       已有账号？<span><a href="${pageContext.request.contextPath }/user/loginpage.do">登录</a></span>     
    </div>   
</body>
</html>