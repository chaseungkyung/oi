<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주변 헬스장</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/layout/footer_layout.css">
<jsp:include page="/WEB-INF/views/layout/headimported.jsp" />
<jsp:include page="/WEB-INF/views/layout/footerimported.jsp" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/calendar/calendarmain.css">
	<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/findgym/findGym.css">
</head>
<body>
	
	<header><jsp:include page="/WEB-INF/views/layout/header.jsp" /></header>
	
<main>
	<div class="container">
		<div class="body-container">	
			<div class="body-title">
				<h3><i class="bi bi-whatsapp"></i> 문의하기 </h3>
			</div>
			
			<div class="body-main">
				
				<table class="table mb-0">
					<tbody>
						<tr>
							<td colspan="2" align="center" class="px-0 pb-0">
								<div class="row gx-0">
									<div class="col-sm-1 bg-primary me-1">
										<p class="form-control-plaintext text-white">Q.</p>
									</div>
									<div class="col bg-primary">
										<p class="form-control-plaintext text-white">${dto.title}</p>
									</div>
								</div>
							</td>
						</tr>					
						<tr>
							<td width="50%">
								이름 : ${dto.userName}
							</td>
							<td align="right">
								문의일자 : ${dto.reg_date}
							</td>
						</tr>
						
						<tr>
							<td colspan="2" valign="top" height="200">
								${dto.question}
							</td>
						</tr>
					</tbody>
				</table>
					
				<c:if test="${not empty dto.answer}">
					<table class="table mb-0">
						<tbody>
							<tr>
								<td colspan="2" align="center" class="p-0">
									<div class="row gx-0">
										<div class="col-sm-1 bg-success me-1">
											<p class="form-control-plaintext text-white">A.</p>
										</div>
										<div class="col bg-success">
											<p class="form-control-plaintext text-white">${dto.title}</p>
										</div>
									</div>
								</td>
							</tr>
						
							<tr>
								<td width="50%">
									담당자 : ${dto.answerName}				
								</td>
								<td align="right">
									답변일자 :  ${dto.answer_date}
								</td>
							</tr>
							
							<tr>
								<td colspan="2" valign="top" height="150">
									${dto.answer}
								</td>
							</tr>
						</tbody>
					</table>
				</c:if>
					
				<table class="table mb-2">
					<tr>
						<td colspan="2">
							이전글 :
							<c:if test="${not empty prevDto}">
								<c:choose>
									<c:when test="${prevDto.secret==1}">
										<c:if test="${sessionScope.member.userId==prevDto.userId || sessionScope.member.userLevel >= 51}">
											<a href="${pageContext.request.contextPath}/qna/article?num=${prevDto.num}&${query}">${prevDto.title}</a>
										</c:if>
										<c:if test="${sessionScope.member.userId!=prevDto.userId && sessionScope.member.userLevel < 51}">
											비밀글 입니다.
										</c:if>
										<i class="bi bi-file-lock2"></i>
									</c:when>
									<c:otherwise>
										<a href="${pageContext.request.contextPath}/qna/article?num=${prevDto.num}&${query}">${prevDto.title}</a>
									</c:otherwise>
								</c:choose>
							</c:if>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							다음글 :
							<c:if test="${not empty nextDto}">
								<c:choose>
									<c:when test="${nextDto.secret==1}">
										<c:if test="${sessionScope.member.userId==nextDto.userId || sessionScope.member.userLevel >= 51}">
											<a href="${pageContext.request.contextPath}/qna/article?num=${nextDto.num}&${query}">${nextDto.title}</a>
										</c:if>
										<c:if test="${sessionScope.member.userId!=nextDto.userId && sessionScope.member.userLevel < 51}">
											비밀글 입니다.
										</c:if>
										<i class="bi bi-file-lock2"></i>
									</c:when>
									<c:otherwise>
										<a href="${pageContext.request.contextPath}/qna/article?num=${nextDto.num}&${query}">${nextDto.title}</a>
									</c:otherwise>
								</c:choose>
							</c:if>
						</td>
					</tr>
				</table>
				
				<table class="table table-borderless">
					<tr>
						<td width="50%">
							<c:if test="${sessionScope.member.userId==dto.userId && empty dto.answer}">
								<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/qna/update?num=${dto.num}&page=${page}';">수정</button>
							</c:if>
					    	
				    		<c:if test="${sessionScope.member.userId==dto.userId || sessionScope.member.userLevel >= 51}">
				    			<button type="button" class="btn btn-light" onclick="deleteOk('question');">삭제</button>
				    		</c:if>
						</td>
						<td class="text-end">
							<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/qna/list?${query}';">리스트</button>
						</td>
					</tr>
				</table>
				
			</div>
		</div>
	</div>
</main>
	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</footer>
</body>
</html>