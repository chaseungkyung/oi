<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<div id="slider1" class="carousel slide modalcarouel col-6">
	<div class="carousel-inner">
		<c:forEach var="file" items="${article.file.saveFileName}"
			varStatus="status">
			<div class="carousel-item ${status.index == 0 ? 'active':''}"
				data-primary="1">
				<img class="object-fit-scale d-block w-100" alt="운동인증"
					src="${pageContext.request.contextPath}/uploads/photo/${file}"
					style="height: 500px; width: 100%;">
			</div>
		</c:forEach>
	</div>
	<button class="carousel-control-prev" type="button"
		data-bs-target="#slider1" data-bs-slide="prev">
		<span class="carousel-control-prev-icon" aria-hidden="true"></span> <span
			class="visually-hidden">Previous</span>
	</button>
	<button class="carousel-control-next" type="button"
		data-bs-target="#slider1" data-bs-slide="next">
		<span class="carousel-control-next-icon" aria-hidden="true"></span> <span
			class="visually-hidden">Next</span>
	</button>
</div>
<!-- 콘텐츠 영역 -->
<div class="table-container col-6">
	<!-- 콘텐츠 -->
	<div class="content">
		<c:set var="defaultfile"
			value="${pageContext.request.contextPath}/resources/images/blank-profile.png" />
		<c:set var="fileroot"
			value="${pageContext.request.contextPath}/uploads/photo/${article.profilePhoto}" />
		<img alt="사진"
			src="${article.profilePhoto == 'default'? defaultfile : fileroot }"
			style="width: 50px; height: 50px; border-radius: 50%;"><span
			style="margin-left: 10px; font-weight: bold;">${article.nickName}</span>
		<div style="margin-top: 10px; font-size: 14px; font-weight: 600;">${article.content}</div>
	</div>
	<hr>
	<!-- 댓글 목록 -->
	<div class="comments-section" style="min-height: 450px; max-height: 450px; overflow-y: auto; font-size: 14px; font-weight: 300;">
		<c:choose>
			<c:when test="${fn:length(commentlist) == 0}">
				<div class="empty">등록된 댓글이 없습니다</div>
			</c:when>
			<c:otherwise>
				<c:forEach var="commentdto" items="${commentlist}">
					<div class="comment mb-3">
						<div class="d-flex align-items-center">
							<c:set var="profile"
								value="${pageContext.request.contextPath}/uploads/photo/${commentdto.memberPhoto}" />
							<img alt="프로필"
								src="${commentdto.memberPhoto == 'default' ? defaultfile : profile}"
								style="width: 40px; height: 40px; border-radius: 50%;"> <span
								style="margin-left: 10px; font-weight: bold;">${commentdto.writernickname}</span>
						</div>
						<div style="margin-left: 50px; margin-top: 5px; ">
							<span style="font-size: 14px; font-weight: 300;">${commentdto.innercontent}</span>
						</div>
					</div>
				</c:forEach>
			</c:otherwise>
		</c:choose>
		<!-- 추가 댓글은 위와 동일하게 반복 -->
	</div>
	<!-- 댓글 입력란 -->
	<div class="input-container">
		<input type="text" class="contents" name="commentcontents" placeholder="댓글을 남겨보세요">
		<button type="button" class="btn btn-primary btn-answer">등록</button>
		<input type="hidden" name="wnum" class="getwnum" value="${article.wnum}">
	</div>
</div>
