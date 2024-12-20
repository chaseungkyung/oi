<%--
  Created by IntelliJ IDEA.
  User: ujinjo
  Date: 2024. 12. 20.
  Time: 오전 9:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:forEach var="vo" items="${listReplyAnswer}">
    <div class="border-bottom m-1">
        <div class="row p-1">
            <div class="col-auto">
                <div class="reply-writer">
                    <div class="name">${vo.userName}</div>
                </div>
            </div>
            <div class="col align-self-center text-end">
                <span>${vo.reg_date}</span> |
                <c:choose>
                    <c:when
                            test="${vo.userId== sessionScope.member.userId || sessionScope.member.userLevel == 100}">
						<span class="deleteReplyAnswer" data-replyNum="${vo.gcNum}"
                              data-parentNum="${vo.parentNum}">삭제</span>
                    </c:when>
                    <c:otherwise>
						<span class="notifyReplyAnswer" data-replyNum="${vo.gcNum}"
                              data-parentNum="${vo.parentNum}">신고</span>
                    </c:otherwise>
                </c:choose>

            </div>
        </div>

        <div class="p-2">${vo.content}</div>
    </div>
</c:forEach>