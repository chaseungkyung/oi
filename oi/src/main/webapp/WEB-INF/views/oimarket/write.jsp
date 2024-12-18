<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>spring</title>


    <style type="text/css">
        .body-container {
            max-width: 800px;
        }
    </style>
<jsp:include page="/WEB-INF/views/layout/headimported.jsp"/>
    <jsp:include page="/WEB-INF/views/layout/footerimported.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/write/boot-board.css" type="text/css">
    <style type="text/css">
        .write-form .img-viewer {
            cursor: pointer;
            border: 1px solid #ccc;
            width: 45px;
            height: 45px;
            border-radius: 45px;
            background-image: url("${pageContext.request.contextPath}/resources/images/add_photo.png");
            position: relative;
            z-index: 9999;
            background-repeat : no-repeat;
            background-size : cover;
        }
        .img-grid img {
            width: 100px; /* 원하는 이미지 너비 */
            height: 100px; /* 원하는 이미지 높이 */
            object-fit: cover; /* 이미지 비율을 유지하면서 잘 맞게 자름 */
            margin: 5px; /* 이미지 간의 여백 */
            border: 1px solid #ddd; /* 테두리 추가 */
            border-radius: 8px; /* 둥근 모서리 */
            cursor: pointer; /* 마우스 커서 변경 */
        }

    </style>

    <script type="text/javascript">
        function sendOk() {
            const f = document.photoForm;
            let str;

            str = f.subject.value.trim();
            if (!str) {
                alert('제목을 입력하세요. ');
                f.subject.focus();
                return;
            }

            str = f.content.value.trim();
            if (!str) {
                alert('내용을 입력하세요. ');
                f.content.focus();
                return;
            }

            let mode = '${mode}';
            if ((mode === 'write') && (!f.selectFile.value)) {
                alert('이미지 파일을 추가 하세요. ');
                f.selectFile.focus();
                return;
            }

            <c:if test="${mode=='update'}">
<%--수정해야함--%>
            function deleteFile(fileNum) {
                if (!confirm('이미지를 삭제 하시겠습니까 ? ')) {
                    return;
                }

                let query = 'num=${dto.num}&fileNum=' + fileNum + '&page=${page}';
                let url = '${pageContext.request.contextPath}/album/deleteFile?' + query;
                location.href = url;
            }

            </c:if>
            // 자식카테고리 활성화
            document.getElementById("childCategory").disabled = false;

            f.action = 'registration';
            console.log(f.action)
            f.submit();
        }


        // 카테고리 데이터 (ID, 이름, 부모 ID)
        const categories = [
            {id: 1, name: "헬스식품", parentId: null},
            {id: 2, name: "보호대", parentId: null},
            {id: 3, name: "회원권/e-ticket", parentId: null},
            {id: 4, name: "남성의류", parentId: null},
            { id: 5, name: "여성의류", parentId: null },
            { id: 6, name: "용품", parentId: null },
            { id: 7, name: "단백질 보충제", parentId: 1 },
            { id: 8, name: "비타민", parentId: 1 },
            { id: 9, name: "부스터", parentId: 1 },
            { id: 10, name: "스트랩", parentId: 2 },
            { id: 11, name: "무릎/팔꿈치", parentId: 2 },
            { id: 12, name: "리프팅벨트", parentId: 2 },
            { id: 13, name: "횟수권", parentId: 3 },
            { id: 14, name: "기간권", parentId: 3 },
            { id: 15, name: "신발", parentId: 4 },
            { id: 16, name: "상의", parentId: 4 },
            { id: 17, name: "하의", parentId: 4 },
            { id: 18, name: "레깅스", parentId: 4 },
            { id: 19, name: "아우터", parentId: 4 },
            { id: 20, name: "이너웨어", parentId: 4 },
            { id: 21, name: "러닝라인", parentId: 4 },
            { id: 22, name: "신발", parentId: 5 },
            { id: 23, name: "상의", parentId: 5 },
            { id: 24, name: "하의", parentId: 5 },
            { id: 25, name: "레깅스", parentId: 5 },
            { id: 26, name: "아우터", parentId: 5 },
            { id: 27, name: "이너웨어", parentId: 5 },
            { id: 28, name: "러닝라인", parentId: 5 },
            { id: 29, name: "헬스용품", parentId: 6 },
            { id: 30, name: "크로스핏 용품", parentId: 6 },
            { id: 31, name: "필라테스/요가 용품", parentId: 6 },
            { id: 32, name: "기타", parentId: null },
        ];

        // 부모 카테고리 선택 시 자식 옵션 업데이트
        function updateChildOptions() {
            const parentSelect = document.getElementById("parentCategory");
            const childSelect = document.getElementById("childCategory");

            // 부모 ID 가져오기
            const parentId = parseInt(parentSelect.value);
            const parentValue = parentSelect.value;

            if (parentValue === "32") { // "기타" 선택 시
                childSelect.disabled = true;
                childSelect.innerHTML = "<option value='32'>- 기타 -</option>"; // 자식 옵션 초기화
            } else {
                childSelect.disabled = false;
                childSelect.innerHTML = "<option value=''>- 자식 선택 -</option>";

                // 해당 부모의 자식 카테고리 필터링
                const childCategories = categories.filter(cat => cat.parentId === parentId);
                childCategories.forEach(cat => {
                    const option = document.createElement("option");
                    option.value = cat.id;
                    option.textContent = cat.name;
                    childSelect.appendChild(option);
                });
            }

            // 부모 카테고리가 선택되면 자식 카테고리 표시
            childSelect.style.display = parentSelect.value ? "block" : "none";
        }

        // 폼 제출 시 자식 카테고리 값 처리
        function validateForm(event) {
            event.preventDefault();
            const parentSelect = document.getElementById("parentCategory");
            const childSelect = document.getElementById("childCategory");

            if (parentSelect.value === "32") { // "기타" 선택 시
                childSelect.disabled = false;
                childSelect.value = "32"; // "기타" 값으로 설정
            }

            console.log("Child Category Value to Submit:", childSelect.value);
        }
        $(function(){
            var sel_files = [];

            $('.write-form').on('click', '.img-add', function(event){
                $('form[name=photoForm] input[name=selectFile]').trigger('click');
            });

            $('form[name=photoForm] input[name=selectFile]').change(function(){
                if(! this.files) {
                    let dt = new DataTransfer();
                    for(let file of sel_files) {
                        dt.items.add(file);
                    }
                    document.photoForm.selectFile.files = dt.files;

                    return false;
                }

                for(let file of this.files) {
                    sel_files.push(file);

                    const reader = new FileReader();
                    const $img = $('<img>', {class:'item img-item'});
                    $img.attr('data-filename', file.name);
                    reader.onload = e => {
                        $img.attr('src', e.target.result);
                    };
                    reader.readAsDataURL(file);

                    $('.img-grid').append($img);
                }

                let dt = new DataTransfer();
                for(let file of sel_files) {
                    dt.items.add(file);
                }
                document.photoForm.selectFile.files = dt.files;

            });

            $('.write-form').on('click', '.img-item', function(event) {
                if(! confirm("선택한 파일을 삭제 하시겠습니까 ?")) {
                    return false;
                }

                let filename = $(this).attr('data-filename');

                for(let i = 0; i < sel_files.length; i++) {
                    if(filename === sel_files[i].name){
                        sel_files.splice(i, 1);
                        break;
                    }
                }

                let dt = new DataTransfer();
                for(let file of sel_files) {
                    dt.items.add(file);
                }
                document.photoForm.selectFile.files = dt.files;

                $(this).remove();
            });
        });

        function formatPrice(input) {
            let value = input.value.replace(/,/g, ""); // 기존 쉼표 제거

            if (value.length > 11) { // 11자리 초과 방지
                alert("11자리 이상 입력할 수 없습니다.");
                input.value = value.substring(0, 11); // 11자리까지 잘라서 유지
                value = input.value;
            }

            if (isNaN(value)) { // 숫자 이외의 값 방지
                alert("숫자만 입력 가능합니다.");
                input.value = ""; // 숫자가 아니면 초기화
                return;
            }

            input.value = new Intl.NumberFormat().format(value); // 3자리마다 쉼표 추가
         // 3자리마다 쉼표 추가
            document.getElementById("priceInWords").textContent = convertToKoreanWon(value); // 한글 금액 변환
        }

        function convertToKoreanWon(num) {
            if (!num || num === "0") return ""; // 값이 없거나 0이면 표시 안 함

            const units = ["", "만", "억", "조"];
            let result = "";
            let unitIndex = 0;

            while (num > 0) {
                const chunk = num % 10000; // 4자리씩 나눔
                if (chunk > 0) {
                    result = chunk + units[unitIndex] + (result ? " " + result : "");
                }
                num = Math.floor(num / 10000);
                unitIndex++;
            }

            return result.trim() + " 원"; // 최종 결과에 '원' 추가
        }


    </script>
</head>
<body>

<header>
    <jsp:include page="/WEB-INF/views/layout/header.jsp"/>
</header>

<main>
    <div class="container d-flex justify-content-center align-items-center min-vh-100" style="background-color: #fff;">
        <div class="form-container bg-white p-4 rounded shadow-sm" style="width: 100%; max-width: 700px;">
            <h3 class="text-center mb-4 text-secondary">
                <i class="bi bi-image"></i> 중고 상품 올리기
            </h3>
            <form name="photoForm" method="post" enctype="multipart/form-data">
                <table class="table table-borderless write-form align-middle">
                    <!-- 이미지 선택 -->

                    <tr>
                        <th scope="row" class="text-secondary" style="width: 20%;">이미지 선택</th>
                        <td>
                            <div class="img-grid">

                                <img class="item img-add rounded" src="${pageContext.request.contextPath}/resources/images/add_photo.png"> </div>
                                <input type="file" id="selectFile" name="selectFile" accept="image/*" multiple="multiple" style="display: none;" class="form-control">

                        </td>
                    </tr>
                    <c:if test="${mode=='update'}">
                        <tr>
                            <td class="bg-light col-sm-2" scope="row">등록이미지</td>
                            <td>
                                <div class="img-box">
                                    <c:forEach var="vo" items="${listFile}">
                                        <img src="${pageContext.request.contextPath}/uploads/photo/${vo.imageFilename}"
                                             onclick="deleteFile('${vo.fileNum}');">
                                    </c:forEach>
                                </div>
                            </td>
                        </tr>
                    </c:if>

                    <!-- 카테고리 선택 -->
                    <tr>
                        <th scope="row" class="text-secondary">카테고리</th>
                        <td>
                            <select id="parentCategory" name="parentCategory" class="form-control mb-3" onchange="updateChildOptions()">
                                <option value="">- 상위 카테고리 선택 -</option>
                                <option value="1">헬스식품</option>
                                <option value="2">보호대</option>
                                <option value="3">회원권/e-ticket</option>
                                <option value="4">남성의류</option>
                                <option value="5">여성의류</option>
                                <option value="6">용품</option>
                                <option value="32">기타</option>
                            </select>
                            <select id="childCategory" name="childCategory" class="form-control" style="display: none;">
                                <option value="">선택 필수</option>
                            </select>
                        </td>
                    </tr>

                    <!-- 상품 명 -->
                    <tr>
                        <th scope="row" class="text-secondary">상품 명</th>
                        <td>
                            <input type="text" id="subject" name="subject" class="form-control" value="${dto.goodsname}">
                        </td>
                    </tr>

                    <tr>
                        <th scope="row" class="text-secondary">상품 가격</th>
                        <td class="d-flex flex-column">
                            <div class="input-group">
                                <span class="input-group-text">₩</span> <!-- 원화 기호 -->
                                <input type="text" id="price" name="price" class="form-control" value="${dto.goodsPrice}" oninput="formatPrice(this)">
                            </div>
                            <div id="priceInWords" style="margin-top: 8px; font-size: 14px; color: gray;"></div> <!-- 금액 표시 -->
                        </td>
                    </tr>


                    <!-- 작성자 명 -->
                    <tr>
                        <th scope="row" class="text-secondary">작성자명</th>
                        <td>
                            <p class="form-control-plaintext">${sessionScope.member.nickname}</p>
                        </td>
                    </tr>

                    <!-- 상품 설명 -->
                    <tr>
                        <th scope="row" class="text-secondary">상품 설명</th>
                        <td>
                            <textarea id="content" name="content" class="form-control">${dto.goodsExp}</textarea>
                        </td>
                    </tr>
                </table>
                    <!-- 버튼 -->
                <table class="table table-borderless">
                    <tr>
                        <td colspan="2" class="text-center mt-4">
                            <button type="button" class="btn btn-dark" onclick="sendOk();">
                               ${mode=='update'?'수정완료':'상품등록'}&nbsp;<i class="bi bi-check2"></i>
                            </button>
                            <button type="reset" class="btn btn-light border">다시입력</button>
                            <button type="button" class="btn btn-light border" onclick="location.href='${pageContext.request.contextPath}/oimarket/registration';">
                                ${mode=='update'?'수정취소':'등록취소'} &nbsp;<i class="bi bi-x"></i>
                            </button>
                            <c:if test="${mode=='update'}">
                                <input type="hidden" name="num" value="${dto.num}">
                                <input type="hidden" name="page"value="${page}">
                            </c:if>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</main>




<footer>
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>

</footer>


</body>
</html>