function mealInsertOk() {
    const f = document.mealForm;
	    const mealTime = f.mealTime.value.trim();
    if (!mealTime) {
        alert("식사 시간을 선택하세요");
        return;
    }
	    const mealName = f.mealName.value.trim();
    if (!mealName) {
        alert("식단 이름을 입력해주세요");
        return;
    }
	const mealDate = f.mealDate.value.trim();
    const mealUnit = f.mealUnit.value.trim();
    const mealCapacity = f.mealCapacity.value.trim();
    const mealKcal = f.mealKcal.value.trim();

    if (!mealDate || !mealUnit || !mealCapacity || !mealKcal) {
        alert("모든 필드를 입력해주세요.");
        return;
    }
	    
    console.log("Meal Time: ", mealTime);
    console.log("Meal Name: ", mealName);
    console.log("Meal Date: ", mealDate);
    console.log("Meal Unit: ", mealUnit);
    console.log("Meal Capacity: ", mealCapacity);
    console.log("Meal Kcal: ", mealKcal);
	
	let url = 'oi/recordmeal/mealinsert';
	// Ajax로 데이터 전송
    $.ajax({
        type: 'POST',
        url: url,
        data: {
            dietFoodTime: mealTime,
            dietFoodName: mealName,
            dietFoodDate: mealDate,
            dietFoodUnit: mealUnit,
            capacity: mealCapacity,
            kcal: mealKcal
        },
		dataType: 'json',
        success: function(response) {
            if (response.state === "true") {
                alert("식단이 등록되었습니다.");
                closeModal(); 
            } else {
                alert("저장에 실패했습니다.");
            }
        },
        error: function() {
            alert("서버와의 연결에 문제가 발생했습니다.");
        }
    });
}


function ajaxFun(url, method, formData, dataType, fn) {
	
	const settings = {
		type: method,
		data: formData,
		dataType: dataType,
		success: function(data) {
			fn(data);
		},
		beforeSend: function(jqXHR) {
			jqXHR.setRequestHeader('AJAX', true);		// 헤더한테 AJAX라고 넘김
		}
	};

	$.ajax(url, settings);
}

/*
	$(function() {
		$('#mealinsertbtn').click(function() {
			let mealName = $('#mealName').val();
			let mealDate = $('#mealDate').val();
			let mealUnit = $('#mealUnit').val();
			let mealCapacity = $('#mealCapacity').val();
			let mealKcal = $('#mealKcal').val();
			
			let formData = {
				dietFoodName : mealName,
				dietFoodDate : mealDate, 
				dietFoodUnit: mealUnit,
				capacity: mealCapacity,
				kcal: mealKcal
			}
			//이건 내가 필요한 데이터를 모으는 거야		

			// url 이 서버에 요청보내는 거
			let url = '${pageContext.request.contextPath}/recordmeal/mealinsert';
			const fn = function(data) {
				alert(data);
			};
		ajaxFun(url, 'get', formData, 'json', fn);
		});
	});
*/	
	
// 모달창
let currentMealType = ""; // 현재 추가할 식사 타입

function openModal(mealType) {
	currentMealType = mealType;
	document.getElementById('mealModal').style.display = 'flex';
}


function closeModal() {
	document.getElementById('mealModal').style.display = 'none';
	document.getElementById('mealTime').value = "";
	document.getElementById('mealName').value = "";
}

/*
function saveMeal() {
	const dietFoodName = document.getElementById('dietFoodName').value;
	const dietFoodUnit = document.getElementById('dietFoodUnit').value;
	const dietFoodCapacity = document.getElementById('dietFoodCapacity').value;
	const Kcal = document.getElementById('kcal').value;

	$.ajax({
		url: '/recordmeal/mealinsert',
		method: 'POST',
		data: {
			dietFoodName: dietFoodName,
			dietFoodUnit: dietFoodUnit,
			dietFoodCapacity: dietFoodCapacity,
			Kcal: Kcal
		},
		success: function(response) {
			if (response.state === "true") {
				alert("식단 기록이 저장되었습니다.");
				$('#mealModal').modal('hide');
				document.getElementById('dietFoodDate').value = '';
				document.getElementById('dietFoodName').value = '';
				document.getElementById('dietFoodUnit').value = '';
				document.getElementById('dietFoodCapacity').value = '';
				document.getElementById('Kcal').value = '';

			} else {
				alert("저장에 실패했습니다.");
			}
		},
		error: function() {
			alert("오류가 발생했습니다.");
		},
	});
}
*/


window.onload = function() {
	adBanner();
}

function adBanner() {
	const adbn = new XMLHttpRequest();
	adbn.open("GET", "calendarmain.jsp", true);
	adbn.onreadystatechange = function() {
		if (adbn.readyState === 4 && adbn.status === 200) {
			const adBanner = document.getElementById('adBanner');
			adBanner.style.display = "block";
		}
	};
	adbn.send();
}
window.onload = function() {
	adBanner();
}; 