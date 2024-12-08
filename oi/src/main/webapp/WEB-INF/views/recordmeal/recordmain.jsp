<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/layout/footer_layout.css">
<jsp:include page="/WEB-INF/views/layout/headimported.jsp" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/record/recordmain.css">
</head>
<body>
	<header><jsp:include page="/WEB-INF/views/layout/header.jsp" /></header>
	<main>

		<div class="title1">
		<h6>진짜 열받게 가운데 정렬 안되고 난리람~</h6>
			<div class="title2">
				<button type="button" class="record"
					onclick="location.href='${pageContext.request.contextPath}/recordmeal/main';">식단기록
					<img src="${pageContext.request.contextPath}/resources/images/babab.png"> </button>
				<button type="button" class="record" onclick="">운동기록
					<img src="${pageContext.request.contextPath}/resources/images/exer.png">
				</button>
			</div>
			<div>
				<button type="button" class="calendar"
					onclick="location.href='${pageContext.request.contextPath}/mypage/calendar/main';">캘린더</button>
			</div>
		</div>
		<footer>
			<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
			<jsp:include page="/WEB-INF/views/layout/footerimported.jsp" />
		</footer>
	</main>
</body>
</html>