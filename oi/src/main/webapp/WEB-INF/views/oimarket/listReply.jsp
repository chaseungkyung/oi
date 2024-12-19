<%--
  Created by IntelliJ IDEA.
  User: ujinjo
  Date: 2024. 12. 20.
  Time: 오전 5:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<div class="reply-info">
    <span class="reply-count">댓글 ${replyCount}개</span> <span>[목록,
		${pageNo}/${total_page} 페이지]</span>
</div>

<table class="table table-borderless">
    <c:forEach var="vo" items="${listReply}">
        <tr class="border table-light">
            <td width="50%">
                <div class="row reply-writer">
                    <div class="col-auto align-self-center">
                        <div class="name">${vo.userName}</div>
                    </div>
                </div>
            </td>
            <td width="50%" align="right" class="align-middle"><span>${vo.reg_date}</span>
                |
                <c:choose>
                    <c:when test="${vo.userId== sessionScope.member.userId || sessionScope.member.userLevel >= 51}">
						<span class="deleteReply" data-replyNum="${vo.gcNum}"
                              data-pageNo="${pageNo}">삭제</span>
                    </c:when>
                    <c:otherwise>
						<span class="notifyReply" data-replyNum="${vo.gcNum}"
                              data-pageNo="${pageNo}">신고</span>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <td colspan="2" valign="top">${vo.content}</td>
        </tr>

        <tr>
            <td>
                <button type="button" class="btn btn-light btnReplyAnswerLayout"
                        data-replyNum="${vo.gcNum}">
                    답글 <span id="answerCount${vo.gcNum}">${vo.answerCount}</span>
                </button>
            </td>
        </tr>

        <tr class="reply-answer">
            <td colspan="2">
                <div class="border rounded">
                    <div id="listReplyAnswer${vo.gcNum}" class="answer-list"></div>
                    <div>
                        <textarea class="form-control m-2"></textarea>
                    </div>
                    <div class="text-end pe-2 pb-1">
                        <button type="button" class="btn btn-light btnSendReplyAnswer"
                                data-replyNum="${vo.gcNum}">답글 등록</button>
                    </div>
                </div>
            </td>
        </tr>
    </c:forEach>
</table>

<div class="page-navigation">${replyCount == 0 ? "등록된 댓글이 없습니다 " : paging }</div>
