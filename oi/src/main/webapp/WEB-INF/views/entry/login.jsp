<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>OI</title>
<jsp:include page="/WEB-INF/views/layout/headimported.jsp"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/entry/login_.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/entry/login_.js"></script>
</head>
<body>
	<header><jsp:include page="/WEB-INF/views/layout/header.jsp"/></header>

	<div id="login">
		<div id="formcover">
			<form name="loginForm" method="post">
				<label for="idinput" class="form-label">Id</label> <input
					type="text" id="idinput" name="userid" class="form-control"
					aria-describedby="passwordHelpBlock"> <label
					for="inputPassword" class="form-label">Password</label> <input
					type="password" id="inputPassword" name="userpwd" class="form-control"
					aria-describedby="passwordHelpBlock">
				<div id="btncover">
				<div id="errormsg">${msg}</div>
				<button type="button" class="btn btn-outline-success" style="color: black;" onclick="submit();">Success</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>