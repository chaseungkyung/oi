<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>

<c:forEach var="vo" items="${list}">
<div class="contentborder">
	<div class="contentpadding">
		<table class="headtable">
			<thead>
				<tr>
					<td style="width: 23px;"><img
						class="rounded-circle object-fit-scale" alt="사진"
						src="${pageContext.request.contextPath}/resources/images/mango.jpg"
						style="width: 20px; height: 20px;"></td>
					<td style="width: auto;">${vo.nickName}</td>
					<td>${vo.updatedate}</td>
				</tr>
			</thead>
		</table>
		<table class="bodytable" data-num="${vo.wnum}">
			<tbody>
				<tr>
					<td style="height: 400px;">
						<div style="margin-top: 20px;" id="slider${vo.wnum}" class="carousel slide">
							<div class="carousel-inner">
								<c:forEach var="map" items="${vo.file.file}" varStatus="status">
								<div class="carousel-item ${status.index eq 0 ? 'active':'' }" data-primary="${map.key}">
									<img class="object-fit-scale d-block w-100" alt="운동인증"
										src="${pageContext.request.contextPath}/uploads/photo/${map.value}"
										style="height: 400px; width: 570px;">
								</div>
								</c:forEach>
							</div>
							<button class="carousel-control-prev" type="button"
								data-bs-target="#slider${vo.wnum }" data-bs-slide="prev"></button>
							<span class="carousel-control-prev-icon" aria-hidden="true"></span>
							<button class="carousel-control-next" type="button"
								data-bs-target="#slider${vo.wnum }" data-bs-slide="next"></button>
							<span class="carousel-control-next-icon" aria-hidden="true"></span>
						</div>
					</td>
				</tr>
				<tr>
					<td><a style="color: black;" href="#" class="like emotion"><i
							class="bi ${vo.liked == true ? 'bi-heart-full': 'bi-heart'}"></i></a> ${vo.loved} <a style="color: black;" href="#" class="comment emotion"><i
							class="bi bi-chat-dots"></i></a> ${vo.commentcount}</td>
				</tr>
			</tbody>
		</table>
		<div class="contentdiv">${vo.content}</div>
	</div>
</div>
</c:forEach>
<input type="hidden" class="total_page" value="${total_page}" style="display:none;">
<input type="hidden" class="page" value="${page}" style="display:none;">