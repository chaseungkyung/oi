<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>

<jsp:include page="/WEB-INF/views/layout/adminheadimported.jsp"></jsp:include>

<style type="text/css">
.body-main {
	max-width: 800px;
}
</style>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/boot-board.css"
	type="text/css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/bootstrap5/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript">
	function deleteBoard() {
		if (confirm('게시글을 삭제하겠습니까?')) {
			let query = 'num=${dto.noticeNum}&${query}';
			let url = '${pageContext.request.contextPath}/admin/notice/delete?'
					+ query;
			location.href = url;
		}
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
					<h3>
						<i class="bi bi-clipboard"> 공지사항 </i>
					</h3>
				</div>
			</div>

			<div class="body-main">

				<table class="table board-article">
					<thead>
						<tr>
							<td colspan="2" align="center">${dto.noticeTitle}</td>
						</tr>
					</thead>

					<tbody>
						<tr>
							<td width="50%">관리자</td>
							<td align="right">${dto.noticeWriteDate}</td>
						</tr>

						<tr>
							<td colspan="2" valign="top" height="200"
								style="border-bottom: none;">${dto.noticeContent}</td>
						</tr>

						<tr>
							<td colspan="2"><c:forEach var="vo" items="${listFile}"
									varStatus="status">
									<p class="border text-secondary mb-1 p-2">
										<i class="bi bi-folder2-open"></i> <a
											href="${pageContext.request.contextPath}/admin/notice/download?noticeFileNum=${vo.noticeFileNum">${vo.noticeOriFileNum}</a>
									</p>
								</c:forEach></td>
						</tr>

						<tr>
							<td colspan="2">이전글 : <c:if test="${not empty preDto}">
									<a
										href="${pageContext.request.contextPath}/admin/notice/artice?${query}&noticeNum=${preDto.noticeNum}">${preDto.noticeTitle}</a>
								</c:if>
							</td>
						</tr>
						<tr>
							<td colspan="2">다음글 : <c:if test="${not empty nexDto}">
									<a
										href="${pageContext.request.contextPath}/admin/notice/artice?${query}&noticeNum=${nexDto.noticeNum}">${nexDto.noticeTitle}</a>
								</c:if>
							</td>
						</tr>

					</tbody>
				</table>

				<table class="table table-borderless">
					<tr>
						<td width="50%"><c:choose>
								<c:when test="${sesssinScope.member.memberId == dto.memberId }">
									<button type="button" class="btn btn-light"
										onclick="location.href='${pageContext.request.contextPath}/admin/notice/update?noticeNum=${dto.noticeNum}&page=${page}&size=${size}';">수정</button>
								</c:when>
								<c:otherwise>
									<button type="button" class="btn btn-light">수정</button>
								</c:otherwise>
							</c:choose>

							<button type="button" class="btn btn-light"
								onclick="deleteBoard();">삭제</button></td>
						<td class="text-end">
							<button type="button" class="btn btn-lignt"
								onclick="location.href='${pageContext.request.contextPath}/admin/notice/list?${query}';">리스트</button>
						</td>
					</tr>
				</table>

			</div>

		</div>
	</main>

	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/views/layout/footerimported.jsp" />
</body>
</html>