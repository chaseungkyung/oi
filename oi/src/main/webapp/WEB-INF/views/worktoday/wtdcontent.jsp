<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<c:forEach>
<div class="contentborder">
	<div class="contentpadding">
		<table class="headtable">
			<thead>
				<tr>
					<td style="width: 23px;"><img
						class="rounded-circle object-fit-scale" alt="사진"
						src="${pageContext.request.contextPath}/resources/images/mango.jpg"
						style="width: 20px; height: 20px;"></td>
					<td style="width: auto;">imsocute</td>
					<td>2024-12-07</td>
				</tr>
			</thead>
		</table>
		<table class="bodytable">
			<tbody>
				<tr>
					<td style="height: 400px;">
						<div style="margin-top: 20px;" id="slider" class="carousel slide">
							<div class="carousel-inner">
								<div class="carousel-item active">
									<img class="object-fit-scale d-block w-100" alt="운동인증"
										src="${pageContext.request.contextPath}/resources/images/mango.jpg"
										style="height: 400px; width: 570px;">
								</div>
								<div class="carousel-item">
									<img class="object-fit-scale d-block w-100" alt="운동인증"
										src="${pageContext.request.contextPath}/resources/images/mango.jpg"
										style="height: 400px; width: 570px;">
								</div>
							</div>
							<button class="carousel-control-prev" type="button"
								data-bs-target="#slider" data-bs-slide="prev"></button>
							<span class="carousel-control-prev-icon" aria-hidden="true"></span>
							<button class="carousel-control-next" type="button"
								data-bs-target="#slider" data-bs-slide="next"></button>
							<span class="carousel-control-next-icon" aria-hidden="true"></span>
						</div>
					</td>
				</tr>
				<tr>
					<td><a style="color: black;" href="#"><i
							class="bi bi-heart"></i></a> 0 <a style="color: black;" href="#"><i
							class="bi bi-chat-dots"></i></a> 0</td>
				</tr>
			</tbody>
		</table>
		<div class="contentdiv">내용입니다</div>
	</div>
</div>
</c:forEach>