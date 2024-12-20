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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin/boot-board.css" type="text/css">

<script type="text/javascript">
	function sendOk() {
		const f = document.noticeForm;
		let str;

		str = f.noticeTitle.value.trim();
		if (!str) {
			alert('제목을 입력하세요. ');
			f.noticeTitle.focus();
			return;
		}

		str = f.noticeContent.value.trim();
		if (!str) {
			alert('내용을 입력하세요. ');
			f.noticeContent.focus();
			return;
		}
		

		f.action = '${pageContext.request.contextPath}/admin/notice/${mode}';
		f.submit();
	}

	<c:if test="${mode=='update'}">
	function deleteFile(noticeFileNum) {
		if (!confirm('파일을 삭제 하시겠습니까 ? ')) {
			return;
		}

		let qs = 'noticeNum=${dto.noticeNum}&noticeFileNum=' + noticeFileNum
				+ '&page=${page}&size=${size}';
		let url = '${pageContext.request.contextPath}/admin/notice/deleteFile?'
				+ qs;
		location.href = url;
	}
	</c:if>
</script>
</head>
<body>

	<jsp:include page="/WEB-INF/views/layout/admin_header.jsp" />

	<main>
		<jsp:include page="/WEB-INF/views/layout/left.jsp" />
		<div class="wrapper">
			<div class="body-container">
				<div class="body-title">
					<h3><i class="bi bi-clipboard"></i> 공지사항 </h3>
				</div>

				<div class="body-main">
					<form name="noticeForm" method="post" enctype="multipart/form-data">
						<table class="table write-form mt-5">
							<tr>
								<td class="bg-light col-sm-2" scope="row">제 목</td>
								<td><input type="text" name="noticeTitle" class="form-control"
									value="${dto.noticeTitle}"></td>
							</tr>

							<tr>
								<td class="bg-light col-sm-2" scope="row">공지여부</td>
								<td><input type="checkbox" class="form-check-input"
									name="notice" id="notice" value="1"
									${dto.notice==1 ? "checked" :""}> <label
									class="form-check-label" for="notice">공지 </label></td>
							</tr>

							<tr>
								<td class="bg-light col-sm-2" scope="row">관리자</td>

							</tr>

							<tr>
								<td class="bg-light col-sm-2" scope="row">내 용</td>
								<td><textarea name="noticeContent" class="form-control">${dto.noticeContent}</textarea>
								</td>
							</tr>

							<tr>
								<td class="bg-light col-sm-2">첨&nbsp;&nbsp;&nbsp;&nbsp;부</td>
								<td><input type="file" name="selectFile" multiple
									class="form-control"></td>
							</tr>

							<c:if test="${mode=='update'}">
								<c:forEach var="vo" items="${listFile}">
									<tr>
										<td class="bg-light col-sm-2">첨부된 파일</td>
										<td>
											<p class="form-control-plaintext">
												<a href="javascript:deleteFile('${vo.noticeFileNum}')"><i class="bi bi-trash"></i></a> ${vo.noticeOriFileName}
											</p>
										</td>
									</tr>
								</c:forEach>
							</c:if>
						</table>

						<table class="table table-borderless">
							<tr>
								<td class="text-center">
									<button type="button" class="btn btn-dark" onclick="sendOk();">${mode=='update'?'수정완료':'등록하기'}&nbsp;<i
											class="bi bi-check2"></i>
									</button>
									<button type="reset" class="btn btn-light">다시입력</button>
									<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/admin/notice/list?size=${size}';">${mode=='update'?'수정취소':'등록취소'}&nbsp;<i class="bi bi-x"></i></button> 
									<input type="hidden" name="size" value="${size}"> 
									<c:if test="${mode=='update'}">
										<input type="hidden" name="noticeNum" value="${dto.noticeNum}">
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

	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	
	<jsp:include page="/WEB-INF/views/layout/footerimported.jsp"/>
</body>
</html>