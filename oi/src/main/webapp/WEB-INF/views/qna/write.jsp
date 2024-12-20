<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>OI</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<jsp:include page="/WEB-INF/views/layout/headimported.jsp" />
<jsp:include page="/WEB-INF/views/layout/footerimported.jsp" />
	<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/findgym/findGym.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/findgym/boot-board.css" type="text/css">
<script type="text/javascript">
function sendOk() {
    const f = document.questionForm;
	let str;
	
    str = f.title.value.trim();
    if(!str) {
        alert('제목을 입력하세요. ');
        f.title.focus();
        return;
    }

    str = f.question.value.trim();
    if(!str) {
        alert('내용을 입력하세요. ');
        f.question.focus();
        return;
    }

    f.action = '${pageContext.request.contextPath}/qna/${mode}';
    f.submit();
}
</script>
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
				<form name="questionForm" method="post">
					<table class="table write-form mt-5">
						<tr>
							<td class="bg-light col-sm-2" scope="row">제 목</td>
							<td>
								<input type="text" name="title" class="form-control" value="${dto.questionTitle}">
							</td>
						</tr>
	        
						<tr>
							<td class="bg-light col-sm-2" scope="row">작성자명</td>
	 						<td>
								<p class="form-control-plaintext">${sessionScope.member.nickname}</p>
							</td>
						</tr>

						
						<tr>
							<td class="bg-light col-sm-2" scope="row">내 용</td>
							<td>
								<textarea name="question" class="form-control">${dto.questionCon}</textarea>
							</td>
						</tr>
					</table>
					
					<table class="table table-borderless">
	 					<tr>
							<td class="text-center">
								<button type="button" class="btn btn-dark" onclick="sendOk();">${mode=='update'?'수정완료':'등록하기'}&nbsp;<i class="bi bi-check2"></i></button>
								<button type="reset" class="btn btn-light">다시입력</button>
								<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/qna/list';">${mode=='update'?'수정취소':'등록취소'}&nbsp;<i class="bi bi-x"></i></button>
								<c:if test="${mode=='update'}">
									<input type="hidden" name="num" value="${dto.questionNum}">
									<input type="hidden" name="page" value="${page}">
								</c:if>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
</main>

	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</footer>
</body>
</html>