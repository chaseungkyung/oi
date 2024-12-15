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
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/record/recordmeal.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/record/recordmain.css">
<jsp:include page="/WEB-INF/views/layout/footerimported.jsp" />
</head>
<body>
	<header><jsp:include page="/WEB-INF/views/layout/header.jsp" /></header>
	<main>

		<div class="title1">
			<a href="javascript:void(0);" onclick="moveWeek(-1)">&lt;</a> <label
				id="currentWeekLabel"></label> <a href="javascript:void(0);"
				onclick="moveWeek(1)">&gt;</a>
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
			</thead>
			<tbody id="calendarBody">
				<!-- 주 단위로 날짜가 여기에 출력됨 -->
			</tbody>
		</table>

		<div class="meal">
			<table class="rtable meal-record">
				<tr class="table1">
					<th>체중</th>
					<td>45Kg</td>
				</tr>
			</table>
		</div>

		<div class="meal-record" id="mealRecord">
			<div class="meal">
				<h2>아침</h2>
				<table id="breakfastTable">
					<tr>
						<th>시간</th>
						<th>식단</th>
					</tr>
				</table>
				<button class="btn mealinsertbtn" onclick="openModal('breakfast')">➕</button>
			</div>

			<div class="meal">
				<h2>점심</h2>
				<table id="lunchTable">
					<tr>
						<th>시간</th>
						<th>식단</th>
					</tr>
				</table>
				<button class="btn mealinsertbtn" onclick="openModal('lunch')">➕</button>
				
			</div>

			<div class="meal">
				<h2>저녁</h2>
				<table id="dinnerTable">
					<tr>
						<th>시간</th>
						<th>식단</th>
					</tr>
				</table>
				<button class="btn mealinsertbtn" onclick="openModal('dinner')">+</button>
			</div>

			<div class="memo">
				<h2>메모</h2>
				<textarea id="memoInput" placeholder="MEMO"></textarea>
			</div>
		</div>


		<div id="mealModal" class="modal">
			<div class="modal-content">
				<h3>식사 추가</h3>
				<input type="text" id="mealTime" placeholder="식사 시간 (예: 7:00)">
				<input type="text" id="mealMenu" placeholder="식단 입력">
				<button class="mealinsertbtn " onclick="addMeal()">등록</button>
				<button class="mealinsertbtn" onclick="closeModal()">취소</button>
			</div>
		</div>
		
		
	</main>

	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</footer>
</body>
</html>