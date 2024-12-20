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

</head>
<body>

<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"/>
	<jsp:include page="/WEB-INF/views/layout/headimported.jsp"/>
</header>

<main>
	<div class="container">
		<div class="body-container">
			<div class="body-title">
				<h3> 공지사항 </h3>
			</div>
	
			<div class="body-main">
		
			<table class="table board-article">
				<thead>
					<tr>
						<td colspan="2" align="center">
							${dto.noticeTitle}
						</td>
					</tr>
				</thead>
			
				<tbody>
					<tr>
						<td width="50%">
							${dto.memberId}
						</td>
						<td align="right">
							${dto.noticeWriteDate}
						</td>
					</tr>
				
					<tr>
						<td colspan="2" valign="top" height="200" style="border-bottom:none;">
							${dto.noticeContent}
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<c:forEach>
								<p>
									<a href="${pageContext.request.contextPath}/notice/download?noticeFileNum=${vo.noticeFileNum}">${vo.noticeOriFileName}</a>
								</p>
							</c:forEach>	
						</td>
					</tr>
				
					<tr>
						<td colspan="2">
							이전글:
							<c:if test="${not empty preDto}">
								<a href="${pageContext.request.contextPath}/notice/article?${query}&noticeNum=${preDto.noticeNum}">${preDto.noticeTitle}</a>
							</c:if>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							다음글:
							<c:if test="${not empty nexDto}">
								<a href="${pageContext.request.contextPath}/notice/article?${query}&noticeNum=${nexDto.noticeNum}">${nexDto.noticeTitle}</a>
							</c:if>
						</td>
					</tr>
				</tbody>
			</table>
		
			<table class="table table-borderless">
				<tr>
					<td width="50%">
						&nbsp;
					</td>			
					<td class="text-end">
						<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/notice/list?${query}';">리스트</button>
					</td>
				</tr>
			</table>
		
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