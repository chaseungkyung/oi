<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:forEach var="vo" items="${listCategory}">
	<tr>
		<td> <input type="text" name="faqCateName" class="form-control" disabled value="${vo.faqCateName}"> </td>
		<td>
			<select name="enabled" class="form-select" disabled>
				<option value="1" ${vo.enabled==1?"selected":"" }>활성</option>
				<option value="0" ${vo.enabled==0?"selected":"" }>비활성</option>
			</select>
		</td>
		<td> <input type="text" name="orderNo" class="form-control" disabled value="${vo.orderNo}"> </td>
		<td align="center">
			<input type="hidden" name="faqCateNum" value="${vo.faqCateNum}">
			<div class="category-modify-btn">
				<span class="btnSpanIcon btnCategoryUpdate" title="수정"><i class="bi bi-pencil-square"></i></span>&nbsp;
				<span class="btnSpanIcon btnCategoryDeleteOk" title="삭제"><i class="bi bi-trash"></i></span>
			</div>
			<div class="category-modify-btnOk" style="display:none">
				<span class="btnSpanIcon btnCategoryUpdateOk" title="수정완료"><i class="bi bi-check2-square"></i></span>&nbsp;
				<span class="btnSpanIcon btnCategoryUpdateCancel" title="수정취소"><i class="bi bi-arrow-clockwise"></i></span>
			</div>
		</td>
	</tr>
</c:forEach>
