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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/wtd/insertwtd.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/wtd/wtdinsert.js"></script>
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
								<td class="col-auto"><img
									style="width: 20px; height: 20px;"
									class="rounded-circle object-fit-scale"
									src="${pageContext.request.contextPath}/resources/images/mango.jpg"></td>
								<td class="col-auto"><span style="font-weight: 300; font-size: 14px;">${sessionScope.member.nickname}</span></td>
							</tr>
						</thead>
						<tbody>
							<tr >
								<td colspan="1"><label for="content" class="form-label">오늘을 기록해보세요</label> <textarea class="form-control" id="content"
										name="content" rows="3"></textarea></td>
							</tr>
							<tr>
								<td colspan="1"><label for="formselected"
									class="form-label" id="filedescribe">사진과 함께 인증하세요</label> <input
									class="form-control form-control-sm" id="formselected"
									type="file" multiple name="fileinput" accept="image/*"></td>
							</tr>
						</tbody>
						<tfoot>
							<tr class="row">
								<td class="col-auto"><button type="button" class="btn btn-outline-success" onclick="sendInsert();">완료</button></td>
								<td class="col-auto"><button type="button" class="btn btn-outline-secondary" onclick="location.href='${pageContext.request.contextPath}/completeworkout/main'">취소</button></td>
							</tr>
						</tfoot>
					</table>
				</div>
			</form>
		</div>
	</main>

	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</footer>
</body>
</html>