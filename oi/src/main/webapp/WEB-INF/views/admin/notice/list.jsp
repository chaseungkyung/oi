<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style type="text/css">
.body-main {
	max-width: 800px;
}
</style>

<script type="text/javascript">
function changeList() {
	const f = document.listForm;
	f.page.value = '1';
	f.action = '${pageContext.request.contextPath}/admin/notice/list';
	f.submit();
}

function searchList() {
	const f = document.searchForm;
	f.submit();
}

$(function () {
	$('#chkAll').click(function () {
		$('form input[name=nums]').prop('checked', $(this).is(':checked'));
		
	});
	
	$('form input[name=nums]').click(function () {
		let b = $('form input[name=nums]').length === $('form input[name=nums]:checked').lenght;
		$('#chkAll').prop('checked', b);
	});
	
	$('#btnDeleteList').click(function () {
		let cnt = $('input[name=nums]:checked').length;
		if(cnt == 0) {
			alert('삭제할 게시글을 선택하세요');
			return false;
		}
		
		if(confirm('선택한 게시글을 삭제하겠습니까?')) {
			const f = document.listForm;
			f.action = '${pageContext.request.contextPath}/admin/notice/deleteList';
			f.submit();
		}
	});
});	
</script>
</head>
<body>

<main>
	
	<div class="wrapper">
		<div class="body-container">
			<div class="body-title">
				<h3><i class="bi bi-clipboard"></i> 공지사항 </h3>
			</div>
			
			<div class="body-main">
				<form name="listForm" method="post">
					<div class="row board-list-header">
						<div  class="col-auto me-auto">
							<button  type="button" class="btn btn-light" id="btnDeleteList" title="삭제"><i class="bi bi-trash"></i></button>
						</div>
						<div class="col-auto">
							<c:if test="${dataCount != 0}">
								<select name="size" class="form-select" onchange="changeList();">
									<option value="5"  ${size==5 ? "selected ":""}>5개씩 출력</option>
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
									<th class="chk">
										<input type="checkbox" class="form-check-input" name="chkAll" id="chkAll">
									</th>
									<th class="num">번호</th>
									<th class="subject">제목</th>
									<th class="name">작성자</th>
									<th class="date">등록일</th>
								</tr>
							</thead>
							
							<tbody>
								<c:forEach var="dto" items="${listNotice}">
									<tr>
										<td>
											<input type="checkbox" class="form-check-input" name="nums" value="${dto.noticeNum}">
										</td>
										<td><span class="badge bg-primary">공지</span></td>
										<td class="left">
											<span class="d-inline-block text-truncate align-middle" style="max-width: 390px;"><a href="${articleUrl}&num=${dto.noticeNum}" class="text-reset">${dto.noticeTitle}</a></span>
										</td>		<!-- 글이 길면 ...으로 간추리기 위해 span 태그로 감싼 것 / width는 span태그라 준 것-->
										<td>관리자</td>
										<td>${dto.noticeWriteDate}</td>
									</tr>
								</c:forEach>
								<c:forEach var="dto" items="${list}" varStatus="status">
									<tr>
									 	<td>
											<input type="checkbox" class="form-check-input" name="nums" value="${dto.noticeNum}">
										</td>
									<td class="left">
										<span class="d-inline-block text-truncate align-middle" style="max-width: 390px;"><a href="${articleUrl}&num=${dto.noticeNum}" class="text-reset">${dto.noticeTitle}</a></span>
									</td>
									<td>관리자</td>
									<td>${dto.noticeWriteDate}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</form>
					
					<div class="page-navigation">
						${dataCount != 0 ? paging : "등록된 게시글 없음"}				
					</div>
					
					<div class="row board-list-footer">
						<div class="col">
							<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/admin/notice/list';"><i class="bi bi-arrow-clockwise"></i></button>
						</div>
						<div class="col-6 text-center">
							<form class="row" name="searchForm" action="${pageContext.request.contextPath}/admin/notice/list" method="post">
								<div class="col-auto p-1">
									<select name="schType" class="form-select">
										<option value="all" ${schType=="all"?"selected":""}>제목+내용</option>
										<option value="userName">관리자</option>
										<option value="reg_date" ${schType=="noticeWriteDate"?"selected":""}>등록일</option>
										<option value="subject" ${schType=="noticeTitle"?"selected":""}>제목</option>
										<option value="content" ${schType=="noticeContent"?"selected":""}>내용</option>
									</select>
								</div>
								<div class="col-auto p-1">
									<input type="text" name="kwd" value="${kwd}" class="form-control">
								</div>
								<div class="col-auto p-1">
									<input type="hidden" name="size" value="${size}"> 
									<button type="button" class="btn btn-light" onclick="searchList()"> <i class="bi bi-search"></i> </button>
								</div>
							</form>
						</div>
						<div class="col text-end">
							<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/admin/notice/write?size=${size}';">글올리기</button>
						</div>
					</div>
					
			</div>
		</div>
	</div>
</main>
</body>
</html>