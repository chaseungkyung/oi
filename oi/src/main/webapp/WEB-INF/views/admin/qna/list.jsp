<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>spring</title>
<jsp:include page="/WEB-INF/views/layout/adminheadimported.jsp"></jsp:include>

<style type="text/css">
.body-main {
	max-width: 800px;
}
</style>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/findgym/boot-board.css" type="text/css">
<script type="text/javascript">
function searchList() {
	const f = document.searchForm;
	f.submit();
}
</script>
</head>
<body>

<jsp:include page="/WEB-INF/views/layout/admin_header.jsp" />

	<main>
		<jsp:include page="/WEB-INF/views/layout/left.jsp" />
	
	<div class="wrapper">
		<div class="body-container">	
			<div class="body-title">
				<h3><i class="bi bi-whatsapp"></i> 문의하기 </h3>
			</div>
			
			<div class="body-main">
		        <div class="row board-list-header">
		            <div class="col-auto me-auto">${dataCount}개(${page}/${total_page} 페이지)</div>
		            <div class="col-auto">&nbsp;</div>
		        </div>				
				
				<table class="table table-hover board-list">
					<thead class="table-light">
						<tr>
							<th class="num">번호</th>
							<th class="subject">제목</th>
							<th class="name">작성자</th>
							<th class="date">질문일자</th>
							<th class="hit" style="width: 80px;">처리결과</th>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach var="dto" items="${list}" varStatus="status">
							<tr>
								<td>${dataCount - (page-1) * size - status.index}</td>
								<td class="left">
									<a href="${articleUrl}&num=${dto.questionNum}">${dto.questionTitle}</a>
								</td>
								<td>${dto.questionName}</td>
								<td>${dto.questionDate}</td>
								<td>${not empty dto.answerId?"답변완료":"답변대기"}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<div class="page-navigation">
					${dataCount == 0 ? "등록된 게시물이 없습니다." : paging}
				</div>

				<div class="row board-list-footer">
					<div class="col">
						<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/admin/qna/list';"><i class="bi bi-arrow-clockwise"></i></button>
					</div>
					<div class="col-6 text-center">
						<form class="row" name="searchForm" action="${pageContext.request.contextPath}/admin/qna/list" method="post">
							<div class="col p-1">
								<input type="text" name="kwd" value="${kwd}" class="form-control" placeholder="검색 키워드를 입력하세요">
							</div>
							<div class="col-auto p-1">
								<button type="button" class="btn btn-light" onclick="searchList()"> <i class="bi bi-search"></i> </button>
							</div>
						</form>
					</div>
					<div class="col text-end">
						&nbsp;
					</div>
				</div>

			</div>
		</div>
	</div>
</main>

	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/views/layout/footerimported.jsp" />
</body>
</html>