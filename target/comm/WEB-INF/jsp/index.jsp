<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2020/3/9
  Time: 14:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset=UTF-8">
    <title>index</title>
    <c:set var="basePath" value="${pageContext.request.contextPath}"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/css/bootstrap.min.css"/>
</head>
<body>

<!-- 搜索导航栏 -->
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header navbar-left col-md-8">
            <a class="navbar-brand col-md-6" href="index.jsp">校园二手交易平台</a>
            <a class="navbar-brand col-md-6 text-center">
                <form id="form1" method="post" action="${basePath}/search">
                    <div class="input-group input-group-sm">
                        <input name="username" type="text" class="form-control" placeholder="Search">
                        <span class="input-group-addon">
                        <span class="glyphicon glyphicon-search" onclick="submitName()"></span>
                    </span>
                    </div>
                </form>
            </a>
        </div>
        <ul class="nav navbar-nav navbar-right">
            <%--@elvariable id="user" type="java.lang.Object"--%>
            <c:if test="${user == null}">
                <li><a href="${basePath}/user/loginpage.do"><span class="glyphicon glyphicon-log-in"></span> 登陆</a></li>
            </c:if>
            <c:if test="${user.iconPath != null}">
                <li><img style="width: 40px; margin-top: 5px; margin-right: 20px;" src="${basePath}/${user.iconPath}" class="img-circle" alt="icon"></li>
                <li><a href="${basePath}/comm/chat"><span class="glyphicon glyphicon-envelope visible-lg"></span></a></li>
            </c:if>
            <li><a href="${basePath}/user/registerpage.do"><span class="glyphicon glyphicon-user"></span> 注册</a></li>
        </ul>
    </div>
</nav>

<!-- user表格 -->
<table class="table">
    <%--@elvariable id="users" type="java.util.List"--%>
    <c:if test="${users == null}">
    <c:out value="No results!"/>
</c:if>
    <tbody class="text-center">
    <c:forEach var="user" items="${users}">
        <tr>
            <td><img style="width: 50px;" src="${basePath}/${user.iconPath}" class="img-circle" alt="icon"></td>
            <td><b>${user.userName}</b></td>
            <td>${user.mailAddress}</td>
            <td><a href="${basePath}/comm/userChat?id=${user.id}"><button class="btn btn-info">Chat</button></a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
<script src="${basePath}/js/jquery-3.4.1.min.js"></script>
<script src="${basePath}/js/bootstrap.min.js"></script>
<script type="text/javascript">
    //搜索
    function submitName() {
        $("#form1").submit();
    }
</script>
</html>
