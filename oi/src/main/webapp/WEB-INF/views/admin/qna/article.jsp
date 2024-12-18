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

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/boot-board.css" type="text/css">

<script type="text/javascript">
	function deleteOk(mode) {
		let s = mode === 'question' ? '질문' : '답변';
		
		if(confirm(s + '을 삭제 하시 겠습니까 ? ')) {
			let query = 'num=${dto.questionNum}&${query}&mode=' + mode;
			let url = '${pageContext.request.contextPath}/admin/qna/delete?' + query;
			location.href = url;
		}
	}

	$(function(){
		let answer = '${dto.ansContent}';
		if(! answer) {
			$('.answer-container').show();
		}
	});
	
	$(function(){
		$('.btnSendAnswer').click(function(){
			const f = document.answerForm;
			if(! f.answer.value.trim()) {
				f.answer.focus();
				return false;
			}
			
			f.action = '${pageContext.request.contextPath}/admin/qna/answer';
			f.submit();
		});
	});
	
	$(function(){
		$('.btnUpdateAnswer').click(function(){
			let mode = $(this).attr('data-mode');
			if(mode === 'update') {
				$('.answer-container').show();
				$(this).text('답변 수정 취소');
				$(this).attr('data-mode', 'cancel');
			} else {
				$('.answer-container').hide();
				$(this).attr('data-mode', 'update');
				$(this).text('답변 수정');
			}
		});
	});
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
				
				<table class="table mb-0">
					<tbody>
						<tr>
							<td colspan="2" align="center" class="px-0 pb-0">
								<div class="row gx-0">
									<div class="col-sm-1 bg-primary me-1">
										<p class="form-control-plaintext text-white">Q.</p>
									</div>
									<div class="col bg-primary">
										<p class="form-control-plaintext text-white">
											${dto.questionTitle}
										</p>
									</div>
								</div>
							</td>
						</tr>					
						<tr>
							<td width="50%">
								이름 : ${dto.questionName}
							</td>
							<td align="right">
								문의일자 : ${dto.questionDate}
							</td>
						</tr>
						
						<tr>
							<td colspan="2" valign="top" height="200">
								${dto.questionCon}
							</td>
						</tr>
					</tbody>
				</table>
					
				<c:if test="${not empty dto.ansContent}">
					<table class="table mb-0">
						<tbody>
							<tr>
								<td colspan="2" align="center" class="p-0">
									<div class="row gx-0">
										<div class="col-sm-1 bg-success me-1">
											<p class="form-control-plaintext text-white">A.</p>
										</div>
										<div class="col bg-success">
											<p class="form-control-plaintext text-white">${dto.questionTitle}</p>
										</div>
									</div>
								</td>
							</tr>
						
							<tr>
								<td width="50%">
									담당자 : ${dto.answerName}				
								</td>
								<td align="right">
									답변일자 :  ${dto.ansDate}
								</td>
							</tr>
							
							<tr>
								<td colspan="2" valign="top" height="150">
									${dto.ansContent}
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
								<a href="${pageContext.request.contextPath}/admin/qna/article?num=${prevDto.questionNum}&${query}">${prevDto.questionTitle}</a>
							</c:if>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							다음글 :
							<c:if test="${not empty nextDto}">
								<a href="${pageContext.request.contextPath}/admin/qna/article?num=${nextDto.questionNum}&${query}">${nextDto.questionTitle}</a>
							</c:if>
						</td>
					</tr>
				</table>
				
				<table class="table table-borderless">
					<tr>
						<td width="50%">
			    			<button type="button" class="btn btn-light" onclick="deleteOk('question');">질문삭제</button>
				    		
							<c:if test="${not empty dto.ansContent and sessionScope.member.userId==dto.answerId}">
								<button type="button" class="btn btn-light btnUpdateAnswer" data-mode="update">답변수정</button>
							</c:if>
							<c:if test="${not empty dto.ansContent && (sessionScope.member.userId==dto.answerId || sessionScope.member.userLevel == 99)}">
								<button type="button" class="btn btn-light" onclick="deleteOk('answer');">답변삭제</button>
							</c:if>
				    		
						</td>
						<td class="text-end">
							<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/admin/qna/list?${query}';">리스트</button>
						</td>
					</tr>
				</table>
				
				<div class="answer-container" style="display: none;">
					<form name="answerForm" method="post">
						<div class='form-header'>
							<span class="bold">질문에 대한 답변</span>
						</div>
						
						<table class="table table-borderless reply-form">
							<tr>
								<td>
									<textarea class='form-control' name="answer">${dto.ansContent}</textarea>
								</td>
							</tr>
							<tr>
							   <td align='right'>
							   		<input type="hidden" name="num" value="${dto.questionNum}">	
							   		<input type="hidden" name="page" value="${page}">					   
							        <button type='button' class='btn btn-light btnSendAnswer'>답변 등록</button>
							    </td>
							 </tr>
						</table>
					</form>
				</div>
				
			</div>
		</div>
	</div>
</main>

	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/views/layout/footerimported.jsp" />
</body>
</html>