function daumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 최종 주소 변수
            var fullAddr = '';
            var extraAddr = '';

            // 사용자가 선택한 주소 타입에 따라 주소 값을 가져온다.
            if (data.userSelectedType === 'R') {
                fullAddr = data.roadAddress;
            } else {
                fullAddr = data.jibunAddress;
            }

            // 도로명 주소일 때 추가 주소 조합
            if (data.userSelectedType === 'R') {
                if (data.bname !== '') {
                    extraAddr += data.bname;
                }
                if (data.buildingName !== '') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                fullAddr += (extraAddr !== '' ? ' (' + extraAddr + ')' : '');
            }

            // 우편번호와 주소 정보를 입력 필드에 넣는다.
            document.getElementById('zipCode').value = data.zonecode; // 우편번호 입력 필드
            document.getElementById('address').value = fullAddr;     // 주소 입력 필드

            // 커서를 상세주소 필드로 이동한다.
            document.getElementById('address').focus();
        }
    }).open();
}