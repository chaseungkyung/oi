<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.body-container {
	max-width: 800px;
}
</style>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap5/css/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap5/icon/bootstrap-icons.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/layout/header_layout.css" type="text/css">

<script type="text/javascript">
function changeList() {
	const f = document.listForm;
	f.page.value = '1';
	f.action = '${pageContext.request.contextPath}/notice/list';
	f.submit();
}

function searchList() {
	const f = document.searchForm;
	f.submit();
}
</script>

</head>
<body>

<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/views/layout/headimported.jsp"></jsp:include>
</header>

<main>
	<div class="container">
		<div class="body-container">
			<div class="body-title">
				<h3> 공지사항 </h3>
			</div>
			
			<div class="body-main">
				<form name="listForm" method="post">
					<div class="row board-list-header">
						<div class="col-auto me-auto">
							<p class="form-control-plaintext">
								${dataCount}개(${page}/${total_page} 페이지)
							</p>
						</div>
						<div class="col-auto">
							<c:if test="${dataCount != 0}">
								<select name="size" class="form-select" onchange="changeList();">
									<option value="5" ${size==5 ? "selected ":""}>5개씩 출력</option>
									<option value="10" ${size==10 ? "selected ":""}>10개씩 출력</option>
									<option value="15" ${size==15 ? "selected ":""}>15개씩 출력</option>
									<option value="20" ${size==20 ? "selected ":""}>20개씩 출력</option>
									<option value="30" ${size==30 ? "selected ":""}>30개씩 출력</option>
								</select>
							</c:if>
							<input type="hidden" name="page" value="${page}">
							<input type="hidden" name="schType" value="${schType}">
							<input type="hidden" name="kwd" value="${kwd}">
						</div>
					</div>
					
					<table class="table table-hover board-list">
						<thead class="table-light">
							<tr>
								<th class="noticeNum">번호</th>								
								<th class="noticeTitle">제목</th>								
								<th class="memberId">작성자</th>								
								<th class="noticeWriteDate">등록일</th>								
							</tr>
						</thead>
						
						<tbody>
							<c:forEach var="dto" items="${listNotice}">
								<tr>
									<td><span class="badge bg-primary">공지</span></td>
									<td class="left">
										<span class="d-inline-block text-truncate align-middle" style="max-width: 390px;"><a href="${articleUrl}&num=${dto.num}" class="text-reset">${dto.noticeTitle}</a></span>
									</td>
									<td>${dto.memberId}</td>
									<td>${dto.noticeUpdateDate}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</form>
				
				<div class="page-navigation">
					${dataCount == 0 ? "등록된 게시물이 없습니다" : paging}
				</div>
				
				<div class="row board-list-footer">
					<div class="col">
						<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/notice/list';">버튼</button>
					</div>
					<div class="col-6 text-center">
						<form class="row" name="searchForm" action="${pageContext.request.contextPath}/notice/list" method="post">
							<div class="col-auto p-1">
								<select name="schType" class="form-select">
									<option value="all" ${schType=="all"?"selected":""}>제목+내용</option>
									<option value="noticeWriteDate" ${schType=="noticeWriteDate"?"selected":""}>등록일</option>
									<option value="noticeTitle" ${schType=="noticeTitle"?"selected":""}>제목</option>
									<option value="noticeContent" ${schType=="noticeContent"?"selected":""}>내용</option>
								</select>
							</div>
							<div class="col-auto p-1">
								<input type="text" name="kwd" value="${kwd}" class="form-control">
							</div>
							<div class="col-auto p-1">
								<input type="hidden" name="size" value="${size}">
								<button type="button" class="btn btn-light" onclick="searchList()">버튼</button>
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

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</footer>

<jsp:include page="/WEB-INF/views/layout/footerimported.jsp"/>
</body>
</html>