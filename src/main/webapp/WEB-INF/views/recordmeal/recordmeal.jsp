<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>식단 기록</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/layout/footer_layout.css">
<jsp:include page="/WEB-INF/views/layout/headimported.jsp" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/record/recordmain.css">
<jsp:include page="/WEB-INF/views/layout/footerimported.jsp" />
</head>
<body>
	<header><jsp:include page="/WEB-INF/views/layout/header.jsp" /></header>
	<main>


<div class="title1">
	<a href="recordmeal.jsp?year=${year}&month=${month-1}">&lt;</a> <label>${year}년
	${month}월</label> <a href="recordmeal.jsp?year=${year}&month=${month+1}">&gt;</a>
</div>

<table class="rtable">
	<thead>
		<tr class="table1">
			<td class="day" data-day="SUN">SUN</td>
			<td class="day" data-day="MON">MON</td>
			<td class="day" data-day="TUE">TUE</td>
			<td class="day" data-day="WED">WED</td>
			<td class="day" data-day="THU">THU</td>
			<td class="day" data-day="FRI">FRI</td>
			<td class="day" data-day="SAT">SAT</td>
		</tr>
		<tr class="table1">
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
	</thead>
</table>

<div class="meal">
<table class="rtable meal-record">
	<tr class="table1">
		<th>체중</th>
		<td>45Kg</td>
	<tr>
</table>
</div>

<div class="meal-record">
	<!-- 아침, 점심, 저녁 테이블 -->
	<div class="meal">
		<h2>아침</h2>
		<table>
			<tr>
				<th>시간</th>
				<th>식단</th>
			</tr>
			<tr>
				<td>7:00</td>
				<td>아침안먹어</td>
			</tr>
	</table>
	</div>

	<div class="meal">
		<h2>점심</h2>
		<table>
			<tr>
				<th>시간</th>
				<th>식단</th>
			</tr>
			<tr>
				<td>12:50</td>
				<td>미쯔</td>
			</tr>
		</table>
	</div>
	<div class="meal">
		<h2>저녁</h2>
		<table>
			<tr>
				<th>시간</th>
				<th>식단</th>
			</tr>
			<tr>
				<td>18:00</td>
				<td>초코송이</td>
			</tr>
		</table>
	</div>
		<!-- 메모 영역 -->
	<div class="memo">
		<h2>메모</h2>
		<textarea placeholder="MEMO"></textarea>
	</div>
</div>
</main>
	
	
	
<script>
// 요일 클릭 이벤트 처리
document.querySelectorAll('.day').forEach(function(dayElement) {
  dayElement.addEventListener('click', function() {
    var selectedDay = dayElement.getAttribute('data-day'); 		// 클릭된 요일 가져오기

   
</script>


<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>
</body>
</html>