<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>OI</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/layout/footer_layout.css">
<jsp:include page="/WEB-INF/views/layout/headimported.jsp" />
<jsp:include page="/WEB-INF/views/layout/footerimported.jsp" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/record/recordmain.css">
</head>
<body>
<header><jsp:include page="/WEB-INF/views/layout/header.jsp" /></header>
<main>
	<div>${nickname}님 오이에 오신 걸 환영합니다!</div>
</main>
<footer><jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include></footer>
</body>
</html>