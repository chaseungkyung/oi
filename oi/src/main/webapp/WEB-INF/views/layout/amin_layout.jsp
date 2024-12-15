<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<Script>
$(function () {
	var url = window.location.pathname;
	var urlRegExp = new RegExp(url.replace(/\/$/,'') + "$");
	
	try {
		$('nav ul>li>a').each(function () {
			if(urlRegExp.test(this.href.replace(/\/$/,''))) {
				$(this).addClass('active_menu');
				return false;
			}
		});		
		if($('nav ul>li>a').hasClass("active_menu")) return false;
		
		var parent = url.replace(/\/$/,'').substr(0, url.replace(/\/$/,'').lastIndexOf("/"));
		if(! parent) parent = "/";
		var urlParentRegExp = new RegExp(parent);
		$('nav >ul>li>a').each(function () {
			if($(this).attr("href")=="#") return true;
			
			var phref = this.href.replace(/\/$/,'').substr(0, this.href.replace(/\/$/,'').lastIndexOf("/"));
			
			if(urlParentRegExp.test(phref)) {
				$(this).addClass('active_menu');
				return false;
			}
		});
		
		if($('nav ul>li>a').hasClass("active_menu")) return false;
		
	} catch (e) {
	}
});

</Script>

    <div class="container-fluid mb-2 p-3 bg-dark text-white d-flex align-items-center justify-content-center">
        <h2 class="fs-4 fw-bold">관리자 페이지</h2>
        <img src="${pageContext.request.contextPath}/resources/images/logo4.svg" alt="logo SVG" class="logo"/>
    </div>
		
        <nav class="vertical-nav">
            <ul class="navbar-nav">
                <li class="menu--item">
                    <a href="#" class="menu--link" title="Home">
                        <span class="menu--label">Home</span>
                    </a>
                </li>
                
                <li class="menu--item">
                    <a href="#" class="menu--link" title="회원관리">
                        <span class="menu--label">회원관리</span>
                    </a>
                </li>
                
                <li class="menu--item">
                    <a href="#" class="menu--link" title="FAQ">
                        <span class="menu--label">FAQ</span>
                    </a>
                </li>
                
                <li class="menu--item">
                    <a href="${pageContext.request.contextPath}/notice/list" class="menu--link" title="공지사항">
                        <span class="menu--label">공지사항</span>
                    </a>
                </li>
                
                <li class="menu--item">
                    <a href="#" class="menu--link" title="QnA">
                        <span class="menu--label">QnA</span>
                    </a>
                </li>
                
                <li class="menu--item">
                    <a href="#" class="menu--link" title="신고">
                        <span class="menu--label">신고</span>
                    </a>
                </li>
                
                <li class="menu--item">
                    <a href="#" class="menu--link" title="이벤트관리">
                        <span class="menu--label">이벤트관리</span>
                    </a>
                </li>
                
                <li class="menu--item">
                    <a href="#" class="menu--link" title="게시글관리">
                        <span class="menu--label">게시글관리</span>
                    </a>
               </li>
            
               <li class="menu--item">
                    <a href="#" class="menu--link" title="댓글관리">
                        <span class="menu--label">댓글관리</span>
                    </a>
               </li>
               
               <li class="menu--item">
  			     	<a href="${pageContext.request.contextPath}/access/logout" class="menu--link" title="logout">      
              			<i class="menu--icon bi bi-unlock"></i>
              			<span class="menu--label">Logout</span>
              		</a>  
               </li>
           </ul>
        
        	<button id="collapse_menu" class="collapse_menu">
				<i class="collapse_menu--icon bi bi-chevron-left"></i>
				<span class="menu--label">Menu</span>
			</button>
        </nav>

</html>