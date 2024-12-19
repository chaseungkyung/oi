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
			<div>12월</div>
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
					<td class="day" data-day="SUN">15</td>
					<td class="day" data-day="MON">16</td>
					<td class="day" data-day="TUE">17</td>
					<td class="day" data-day="WED">18</td>
					<td class="day" data-day="THU">19</td>
					<td class="day" data-day="FRI">20</td>
					<td class="day" data-day="SAT">21</td>
				</tr>
			</thead>
			<tbody id="calendarBody">
				<!-- 주 단위로 날짜가 여기에 출력됨 -->
			</tbody>
		</table>
		<br>
		<div class="mealTotal">
			<div class="meal-record" id="mealRecord">
				<div class="meal">
					<h2>🕒 오늘의 식단 기록</h2>
					<table id="mealTable">
						<tr>
							<th></th>
							<th>시간</th>
							<th>메뉴</th>
							<th>(g)</th>
							<th>(Kcal)</th>
						</tr>
						<c:forEach var="meal" items="${mealList}">
							<tr>
								<td>${meal.dietFoodTime}</td>
								<td>${meal.dietFoodDate}</td>
								<td>${meal.dietFoodName}</td>
								<td>${meal.capacity}</td>
								<td>${meal.kcal}</td>
							</tr>
						</c:forEach>

					</table>
					<button class="btn mealinsertbtn" onclick="openModal()">➕
						추가</button>
				</div>
				<div class="meal">
					<h2>💟 전체 식단</h2>
					<table id="mealTable">
						<tr>
							<th></th>
							<th>시간</th>
							<th>메뉴</th>
							<th>(g)</th>
							<th>(Kcal)</th>
						</tr>
						<c:forEach var="mealtotal" items="${mealTotal}">
							<tr>
								<td>${mealtotal.dietFoodTime}</td>
								<td>${mealtotal.dietFoodDate}</td>
								<td>${mealtotal.dietFoodName}</td>
								<td>${mealtotal.capacity}</td>
								<td>${mealtotal.kcal}<br></td>
							</tr>
						</c:forEach>
					</table>
				</div>

				<div class="memo">
					<h2>메모</h2>
					<textarea id="memoInput" placeholder="MEMO"></textarea>
				</div>
			</div>
			<div class="mealapi">
				<div class="list-header">
					<ul class="list-content">
						<li>👁 👁 식단 정보</li>
						<li class="list-header-left"><input type="text" id="keyword"
							class="form-control">
							<button type="button" class="btn btn-search">✔ 검색</button></li>
						<li class="list-header-right"></li>
					</ul>
				</div>
			</div>
		</div>
		<form
			action="${pageContext.request.contextPath}/recordmeal/mealdelete"
			method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');"
			style="display: inline;">
			<input type="hidden" name="exerciseNum" value="${dietFoodNum}">
			<button type="submit" class="btn btn-sm btn-danger" aria-label="삭제">
				<i class="fas fa-trash-alt"></i> 삭제
			</button>
		</form>
		<form name="mealForm" method="post">
			<div id="mealModal" class="modal">
				<div class="modal-content">
					<h3>식단 추가</h3>
					<input type="hidden" name="content" id="mealNum"> <select
						name="dietFoodTime" class="form-select">
						<option value="">선 택</option>
						<option value="아침">아침</option>
						<option value="점심">점심</option>
						<option value="저녁">저녁</option>
					</select> <input type="text" name="dietFoodName" id="mealName"
						placeholder="메뉴명"> <input type="text" name="dietFoodDate"
						id="mealDate" placeholder="일자(20241220)"> <input
						type="text" name="capacity" id="mealCapacity" placeholder="용량(g)">
					<input type="text" name="kcal" id="mealKcal"
						placeholder="칼로리(Kcal)">
					<button class="mealinsertbtn" type="button" id="mealinsertbtn"
						onclick="mealInsertOk()">등록</button>
					<button class="mealinsertbtn" type="button" onclick="closeModal()">취소</button>
				</div>
			</div>
		</form>

	</main>

	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</footer>


</body>
</html>