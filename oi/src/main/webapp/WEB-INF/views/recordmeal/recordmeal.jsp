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
			<a href="#" onclick="moveWeek(-1)">&lt;</a> 
			<label id="currentWeekLabel"></label> 
			<a href="#" onclick="moveWeek(1)">&gt;</a>
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
		<br>
		<div class="meal-record" id="mealRecord">
			<div class="meal">
				<h2>아침</h2>
				<table id="breakfastTable">
					<tr>
						<th>시간</th>
						<th>메뉴</th>
						<th>(인분)</th>
						<th>(g)</th>
						<th>(Kcal)</th>
					</tr>
				</table>
				<button class="btn mealinsertbtn" onclick="openModal('breakfast')">➕ 추가</button>
			</div>

			<div class="meal">
				<h2>점심</h2>
				<table id="lunchTable">
					<tr>
						<th>시간</th>
						<th>메뉴</th>
						<th>(인분)</th>
						<th>(g)</th>
						<th>(Kcal)</th>
					</tr>
				</table>
				<button class="btn mealinsertbtn" onclick="openModal('lunch')">➕ 추가</button>

			</div>

			<div class="meal">
				<h2>저녁</h2>
				<table id="dinnerTable">
					<tr>
						<th>시간</th>
						<th>메뉴</th>
						<th>(인분)</th>
						<th>(g)</th>
						<th>(Kcal)</th>
					</tr>
				</table>
				<button class="btn mealinsertbtn" onclick="openModal('dinner')">➕ 추가</button>
			</div>

			<div class="memo">
				<h2>메모</h2>
				<textarea id="memoInput" placeholder="MEMO"></textarea>
			</div>
		</div>

<form name="mealForm" method="post">
		<div id="mealModal" class="modal">
			<div class="modal-content">
				<h3>식사 추가</h3>
				<input type="hidden" name="content" id="mealNum"> 
				<select name="mealTime" class="form-select">
					<option value="">선 택</option>
					<option value="아침" >아침</option>
					<option value="점심">점심</option>
					<option value="저녁">저녁</option>
					<option value="간식">간식</option>
				</select>
				<input type="text" name="content" id="mealName" placeholder="식단 입력">
				<input type="text" name="content" id="mealDate" placeholder="식단 일자">
				<input type="text" name="content" id="mealUnit" placeholder="(인분)">
				<input type="text" name="content" id="mealCapacity" placeholder="용량(g)">
				<input type="text" name="content" id="mealKcal" placeholder="칼로리(Kcal)">
				<button class="mealinsertbtn" id="mealinsertbtn" onclick="mealInsertOk()">등록</button>
				<button class="mealinsertbtn" onclick="closeModal()">취소</button>
			</div>
		</div>
</form>

	</main>

	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</footer>


</body>
</html>