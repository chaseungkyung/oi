function sendInsert(){
	const form = document.insertForm;
	const text = document.getElementById('filedescribe');
	if(! form.content.value ){
		form.content.focus();
		return;
	}
	if(form.fileinput.files.length === 0){
		text.innerText = "사진을 선택해주세요";
		return;
	}
	
	const files = form.fileinput.files;
	const maxsize = 2 * 1024 * 1024;
	
	for( el of files){
		if(el.size > maxsize) text.innerHTML="용량은 2MB까지만 가능합니다";
		return;
	}
	
	form.action = "${pageContext.request.contextPath}/completeworkout/insertwtd";
	form.submit();
};