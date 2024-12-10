<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<script type="text/javascript">
const $sensor = $('div#sensor');

//컨텐츠 불러오기 
function loading(page) {
	
}

// 처음 로딩 
$(function () {
	
});

// 감지 콜백 함수 
function callback() {
	
}

// 감지 등록 
const io = new IntersectionObserver(callback);
io.observe($sensor);




</script>