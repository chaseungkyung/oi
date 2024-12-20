<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<%
    // 광고 이미지를 동적으로 로드하거나 DB에서 가져올 수 있음
    String adImage = "ad1.jpg"; // 예제용 광고 이미지
    String adLink = "https://www.example.com"; // 광고 링크
%>
<html>
<head>
<meta charset="UTF-8">
<title>나의 기록</title>
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

		<div class="btn">
			<div class="btn-record">
				<button type="button" class="record"
					onclick="location.href='${pageContext.request.contextPath}/recordmeal/mealmain';">식단 기록</button>
				<button type="button" class="record"
					onclick="location.href='${pageContext.request.contextPath}/todayworkout/main';">운동 기록</button>
			</div>

			<div class="btn-other"><br>
				<button type="button" class="btn2"
					onclick="location.href='${pageContext.request.contextPath}/calendar/calendarmain'">TODAY</button>
			</div>
		</div>
		
		<div class="calendar1">
			<div class= "ad-right" id="adbanner">
				<a href="http://192.168.0.74:9090/ottss/" target="_blank">
   				 	<img src="${pageContext.request.contextPath}/resources/images/ad7.gif" alt="광고 배너">
				</a>
			</div>
			<div class="title">
				<a href="${pageContext.request.contextPath}/calendar/calendarmain?year=${year}&month=${month-1}">&lt;</a> <label>${year}년
					${month}월</label> <a href="${pageContext.request.contextPath}/calendar/calendarmain?year=${year}&month=${month+1}">&gt;</a>
			</div>
			<br><br>
			<table class="ctable1">
				<thead>
					<tr class="table1">
						<td>SUN</td>
						<td>MON</td>
						<td>TUE</td>
						<td>WED</td>
						<td>THU</td>
						<td>FRI</td>
						<td>SAT</td>
					</tr>
				</thead>

				<tbody>
					<tr class="table2">
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
		</div>
	</main>

	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</footer>
</body>
</html>