function submit(){
	const f = document.loginForm;
	if(! f.userid.value().trim()){
		f.userid.focus();
		return;
	}
	if(! f.userpwd.value().trim()){
		f.userpwd.focus();
		return;
	}
	
	f.action="${pageContext.request.contextPath}/access/login";
	f.submit();
}