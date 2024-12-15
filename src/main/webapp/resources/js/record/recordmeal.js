/*

const API_KEY = "XEaV5QgvI1upo1GKGdV%2BXBduU4VuKBUFLpSNAL313umn9xxXhWopCvRCu3c2MXliK2sPxFa6Ba11YnlQJV6uNw%3D%3D"

async function getData() {
    // 템플릿 리터럴을 사용하여 API_KEY를 URL에 삽입
    const url = `http://apis.data.go.kr/1390802/AgriFood/MzenFoodNutri/getKoreanFoodIdntList?result_Code=${API_KEY}`;

    try {
        // 데이터 부르고
        const response = await fetch(url);
        
        // 응답 파싱
        const data = await response.json();
		
		const dataContainer = document.getElementById('data-container');
       
		 data.items.item.forEach(item => {
			const div = document.createElement('div');
			    div.textContent = `Food Name: ${item.foodName}, Nutritional Value: ${item.nutritionalValue}`;
			    dataContainer.appendChild(div);  // #data-container에 항목 추가
			});
    } catch (error) {
        console.error("Error fetching data:", error);
    }
	
document.addEventListener('DOMContentLoaded', getData);
}
*/