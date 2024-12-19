function mealInsertOk() {
    const f = document.mealForm;
	    const dietFoodTime = f.dietFoodTime.value.trim();
    if (! dietFoodTime) {
        alert("식사 시간을 선택하세요");
        return;
    }
	    const dietFoodName = f.dietFoodName.value.trim();
    if (! dietFoodName) {
        alert("식단 이름을 입력해주세요");
        return;
    }
	const dietFoodDate = f.dietFoodDate.value.trim();
    const capacity = f.capacity.value.trim();
    const kcal = f.kcal.value.trim();

    if (! dietFoodDate ||  ! capacity || ! kcal) {
        alert("모든 필드를 입력해주세요.");
        return;
    }
	    
    console.log("Meal Time: ", dietFoodTime);
    console.log("Meal Name: ", dietFoodName);
    console.log("Meal Date: ", dietFoodDate);
    console.log("Meal Capacity: ", capacity);
    console.log("Meal kcal: ", kcal);
	
	let url = '/oi/recordmeal/mealinsert';
	
	// Ajax로 데이터 전송
    $.ajax({
        url: url,
        type: 'POST',
        data: {
            dietFoodTime: dietFoodTime,
            dietFoodName: dietFoodName,
            dietFoodDate: dietFoodDate,
            capacity: capacity,
            kcal: kcal
        },
		dataType: 'json',
        success: function(data) {
            if (data.state === "true") {
                alert("식단이 등록되었습니다.");
                closeModal(); 
				
				$.ajax({
				    url: '/oi/recordmeal/mealList',
				    type: 'GET',
				    dataType: 'json',
				    success: function(data) {
						const mealList = data.mealList;
				        const $table = $('#mealTable');
						
						$table.find('tr:gt(0)').remove(); 
						
						let text;
						mealList.forEach(meal => {
							text += `<tr>`;
							text += `<td>${meal.dietFoodUnit}</td>`;
							text += `<td>${meal.dietFoodDate}</td>`;
							text += `<td>${meal.dietFoodName}</td>`;
							text += `<td>${meal.capacity}</td>`;
							text += `<td>${meal.kcal}</td>`;
							text += `</tr>`;
							$table.append(text);
				    });
					},
				    error: function(err) {
				        console.error("리스트 갱신 실패: ", err);
				    }
				});
            } else {
                alert("저장에 실패했습니다.");
            }
        },
        error: function(a) {
            console.log(a.responseText);
        }
    });
}




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

function ajaxFun(url, method, formData, dataType, fn, file = false) {
	const settings = {
			type: method, 
			data: formData,
			dataType:dataType,
			success:function(data) {
				fn(data);
			},
			beforeSend: function(jqXHR) {
			},
			complete: function () {
			},
			error: function(jqXHR) {
				console.log(jqXHR.responseText);
			}
	};
	
	if(file) {
		settings.processData = false;  // file 전송시 필수. 서버로전송할 데이터를 쿼리문자열로 변환여부
		settings.contentType = false;  // file 전송시 필수. 서버에전송할 데이터의 Content-Type. 기본:application/x-www-urlencoded
	}
	
	$.ajax(url, settings);
}

// API
$(function(){
	$('.btn-search').click(function(){
	let kwd = $('#keyword').val().trim();
		if( ! kwd) {
		return false;
		}
	searchMeal(kwd);
	});
		
	function searchMeal(food_Name) {		
		let spec = 'http://apis.data.go.kr/1390802/AgriFood/MzenFoodCode/getKoreanFoodList';
		let key = '7aZjzRKyMraXhRyx91Pdw%2Fgec2y9eFWM8gPnrUVz5jg706DLS3hAESkz4xFMXyLLA%2BSA%2FM0aD1yQyVDsQ0ZbkQ%3D%3D';
		let Page_No = 1;
		let Page_Size = 5;
		
		let qs = "serviceKey="+ encodeURIComponent(key);
		qs += "&Page_No="+ Page_No;
		qs += "&Page_Size="+ Page_Size;
//		qs += "&keyword=" + encodeURIComponent(keyword);
		qs += "&food_Name=" + food_Name;
		
		const fn = function(data) {
			$('.list-header-right').empty();
			$('.list-content').empty();
			
			printXML(data);
		};
		
		ajaxFun(spec, 'GET', qs, 'xml', fn);	
	}
	
	function printXML(data) {
		console.log(data);

	}
});
