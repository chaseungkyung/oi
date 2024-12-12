window.addEventListener('load', e => {
	const sliderWrapEl = document.querySelector('ul.slider-wrap'); // ul
	const sliderListsEl = document.querySelectorAll('.slider-content > ul > li');
	
	if(sliderListsEl.length <= 1) {
		return false;
	}

	const btnLeftEl = document.querySelector('.slider-left .btn-move-left');
	const btnRightEl = document.querySelector('.slider-right .btn-move-right');
	
	/*
	  - 무한루프를 작성하기 위해 가장처음슬라이더를 복사하여 마지막에 추가하고, 
	    가장마지막슬라이더를 복사하여 처음슬라이더에추가
	*/
	const cloneFirst = sliderListsEl[0].cloneNode(true);
    const cloneLast = sliderListsEl[sliderListsEl.length - 1].cloneNode(true);
    sliderWrapEl.insertBefore(cloneLast, sliderListsEl[0]);
    sliderWrapEl.appendChild(cloneFirst);
    // console.log(sliderWrapEl);
    
    // 변수 초기화  
    const speedTime = 500;
    let currentIndex = 0;
    let translate = 0;

    // setup
    const sliderCloneListsEl = document.querySelectorAll('.slider-content > ul > li');
    const itemWidth = sliderCloneListsEl[0].clientWidth;
    currentIndex = 1;
    translate = -itemWidth;
    sliderWrapEl.style.transform = `translateX(${translate}px)`
    
    // 슬라이더 실행
    const sliderMove = index => {
		currentIndex += (-1 * index);
		translate += itemWidth * index;
		sliderWrapEl.style.transform = `translateX(${translate}px)`;
		sliderWrapEl.style.transition = `all ${speedTime}ms ease`;
			// transition : 엘리먼트를 transform(변형) 시키는데 걸리는 시간 설정
    };

    // 이전
	const sliderPrevious = () => {
		sliderMove(1);

		// 처음슬라이더에서 복사한마지막슬라이더로 넘어가는 순간, 
		//   애니메이션 적용을 제거하고 마지막슬라이더위치로 옮겨서 출력
		if (currentIndex === 0) {
			setTimeout(() => {
				sliderWrapEl.style.transition = 'none';
				currentIndex = sliderCloneListsEl.length - 2;
				translate = -(itemWidth * currentIndex);
				sliderWrapEl.style.transform = `translateX(${translate}px)`;
			}, speedTime);
		}
	};

	// 다음
	const sliderNext = () => {
		sliderMove(-1);

		// 마지막슬라이더에서 복사한처음슬라이더로 넘어가는 순간, 
		//   애니메이션 적용을 제거하고 처음슬라이더위치로 옮겨서 출력
		if (currentIndex === sliderCloneListsEl.length -1)
			setTimeout(() => {
				sliderWrapEl.style.transition = 'none';
				currentIndex = 1;
				translate = -itemWidth;
				sliderWrapEl.style.transform = `translateX(${translate}px)`;
          }, speedTime);		
	};
	
	btnLeftEl.addEventListener('click', e => {
		sliderPrevious();
	});

	btnRightEl.addEventListener('click', e => {
		sliderNext();
	});
	
	setInterval(function(){
		sliderNext();
	}, 3000);
});