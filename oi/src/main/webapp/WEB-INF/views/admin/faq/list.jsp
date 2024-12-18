-<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:if test="${list.size() > 0}">
	<div class="accordion accordion-flush mt-2" id="accordionFlush"> 
		<c:forEach var="dto" items="${list}" varStatus="status">
			<div class="accordion-item" style="border: none;">
				<h2 class="accordion-header mb-1 border" id="flush-heading-${status.index}">
					<button class="accordion-button collapsed bg-light" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapse-${status.index}" aria-expanded="false" aria-controls="flush-collapse-${status.index}">
						${dto.faqTitle}
					</button>
				</h2>
				<div id="flush-collapse-${status.index}" class="accordion-collapse collapse" aria-labelledby="flush-heading-${status.index}" data-bs-parent="#accordionFlush">
					<div class="accordion-body">
						<div class="row border-bottom pb-1">
							<div class="col">
								분류 : ${dto.faqCateName}							
							</div>
							<div class="col-auto">
								<a onclick="updateFaq('${dto.faqNum}', '${pageNo}');" title="수정">
									<i class="bi bi-pencil-square"></i>
								</a>
								<a onclick="deleteFaq('${dto.faqNum}', '${pageNo}');" title="삭제">
									<i class="bi bi-trash"></i>
								</a>
							</div>
						</div>
						<div class="row">
							<div class="col">${dto.faqContent}</div>
						</div>
					</div>
				</div>
			</div>		
		</c:forEach>
	</div>
</c:if>
 
<div class="page-navigation">
	${dataCount == 0 ? "등록된 게시물이 없습니다." : paging}
</div>
