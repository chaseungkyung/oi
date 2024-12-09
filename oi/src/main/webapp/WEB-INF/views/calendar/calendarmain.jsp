<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>기록 요약 -오이-</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/layout/footer_layout.css">
<jsp:include page="/WEB-INF/views/layout/headimported.jsp" />
<jsp:include page="/WEB-INF/views/layout/footerimported.jsp" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/calendar/calendarmain.css">
</head>
<body>
	<header><jsp:include page="/WEB-INF/views/layout/header.jsp" /></header>
	<main>
		<h6>캘린더 메인</h6>

		<div class="calendar">
			<div class="title">
				<a href="recordmeal.jsp?year=${year}&month=${month-1}">&lt;</a> <label>${year}년
					${month}월</label> <a href="recordmeal.jsp?year=${year}&month=${month+1}">&gt;</a>
			</div>

			<table class="table">
				<thead>
					<tr>
						<td>Sun</td>
						<td>Mon</td>
						<td>Tue</td>
						<td>Wed</td>
						<td>Thu</td>
						<td>Fri</td>
						<td>Sat</td>
					</tr>
				</thead>
				<!-- 날짜 -->
				<tbody>
					<tr>
						<c:forEach var="i" begin="${preDate}" end="${preDate+week-2}">
							<td class="gray">${i}</td>
						</c:forEach>

						<c:forEach var="i" begin="1" end="${lastDate}">
							<c:set var="cls" value="${year==ty && month==tm && i==td}" />
							<td class="cls">${i}</td>
							<c:set var="week" value="${week+1}" />
							<c:if test="${lastDate != i && week%7 == 1}">
								<c:out value="</tr><tr>" escapeXml="false" />
							</c:if>
						</c:forEach>

						<c:set var="n" value="1" />
						<c:if test="${week%7 != 1}">
							<c:set var="w" value="${week%7 == 0 ? 7 : week%7}" />
							<c:forEach var="i" begin="${w}" end="7">
								<td class="gray">${n}</td>
								<c:set var="n" value="${n+1}" />
							</c:forEach>
						</c:if>
					</tr>
				</tbody>
			</table>

			<div class="footer">
				<a href="">오늘날짜로</a>
			</div>
		</div>

	</main>

	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</footer>
</body>
</html>