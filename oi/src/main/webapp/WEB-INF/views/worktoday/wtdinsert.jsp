<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/layout/footer_layout.css">
<jsp:include page="/WEB-INF/views/layout/headimported.jsp" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/wtd/insertwtd.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/wtd/wtdinsert.js"></script>
</head>
<body>
	<header><jsp:include page="/WEB-INF/views/layout/header.jsp" /></header>

	<main>
		<div class="container">
			<form name="insertForm" method="post" enctype="multipart/form-data">
				<div class="borderinsert">
					<table>
						<thead>
							<tr class="row">
								<td class="col-auto"><c:set var="defaultfile"
										value="${pageContext.request.contextPath}/resources/images/blank-profile.png" />
									<c:set var="fileroot"
										value="${pageContext.request.contextPath}/uploads/photo/${sessionScope.member.saveprofile}" />
									<img style="width: 20px; height: 20px;"
									class="rounded-circle object-fit-scale"
									src="${sessionScope.member.saveprofile == 'default' ? defaultfile: fileroot }"></td>
								<td class="col-auto"><span
									style="font-weight: 300; font-size: 14px;">${sessionScope.member.nickname}</span></td>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td colspan="1"><label for="content" class="form-label">오늘을
										기록해보세요</label> <textarea class="form-control" id="content"
										name="content" rows="3">${dto.content }</textarea></td>
							</tr>
							<tr>
								<td colspan="1"><label for="formselected"
									class="form-label" id="filedescribe">사진과 함께 인증하세요</label> <input
									class="form-control form-control-sm" id="formselected"
									type="file" multiple name="fileinput" accept="image/*"></td>
							</tr>
							<c:if test="${mode == 'update'}">

							</c:if>
						</tbody>
						<tfoot>
							<tr class="row">
								<td class="col-auto"><button type="button"
										class="btn btn-outline-success"
										onclick="${mode == 'update' ? 'sendUpdate()': 'sendInsert();'}">완료</button></td>
								<td class="col-auto"><button type="button"
										class="btn btn-outline-secondary"
										onclick="location.href='${pageContext.request.contextPath}/completeworkout/main'">취소</button></td>
							</tr>
						</tfoot>
					</table>
				</div>
				<input type="hidden" value="${dto.wnum}" name="wnum">
			</form>
		</div>
	</main>

	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</footer>
	<script type="text/javascript">
		$(function() {
			let mode = '${mode}';

			if (mode == 'update') {

			}
		});
	</script>
</body>
</html>