<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%
Calendar cal = Calendar.getInstance();
int ty = cal.get(Calendar.YEAR);
int tm = cal.get(Calendar.MONTH) + 1;
int td = cal.get(Calendar.DATE);

int year = cal.get(Calendar.YEAR);
int month = cal.get(Calendar.MONTH) + 1;

String sy = request.getParameter("year"); // a태그를 눌러야 parameter를 가져오는 거라 이게 실행됨. 누르기 전에는 sy, sm 은 실행x ->값이 null
String sm = request.getParameter("month");

if (sy != null) {
	year = Integer.parseInt(sy);
}

if (sm != null) {
	month = Integer.parseInt(sm);
}

cal.set(year, month - 1, 1); // 월 - 1
year = cal.get(Calendar.YEAR);
month = cal.get(Calendar.MONTH) + 1; // + 1 = 월

int week = cal.get(Calendar.DAY_OF_WEEK); // 1~7

// jstl 로 변경 -> 여기부터 추가
int lastDate = cal.getActualMaximum(Calendar.DATE);

Calendar preCal = (Calendar) cal.clone();
preCal.add(Calendar.DATE, -(week - 1));
int preDate = preCal.get(Calendar.DATE);

pageContext.setAttribute("ty", ty);
pageContext.setAttribute("tm", tm);
pageContext.setAttribute("td", td);

pageContext.setAttribute("year", year);
pageContext.setAttribute("month", month);
pageContext.setAttribute("lastDate", lastDate);
pageContext.setAttribute("preDate", preDate);
pageContext.setAttribute("week", week);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>식단 기록 -오이-</title>
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
		<h3>나의 이번 달 식단</h3>
		
		<div class="calendar">
			<div class="title">
				<a href="recordmeal.jsp?year=${year}&month=${month-1}">&lt;</a> 
				<label>${year}년 ${month}월</label> 
				<a href="recordmeal.jsp?year=${year}&month=${month+1}">&gt;</a>
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
		
		<footer>
			<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
			<jsp:include page="/WEB-INF/views/layout/footerimported.jsp" />
		</footer>
	</main>
</body>
</html>