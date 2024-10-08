// 물품 등록화면으로 이동
const saveReq = () => {
    location.href = "/items/upload";
};

// 로그인화면으로 이동
const loginReq = () => {
    location.href = "/loginForm";
};

// DOM이 모두 로드된 후에 실행
$(document).ready(function () {

    // 카테고리 버튼 클릭 시 사이드바 토글
    $('#category-btn').on('click', function (event) {
        event.stopPropagation(); // 클릭 이벤트 전파 방지
        $('#side-bar').toggle();
    });

    // 페이지 외부 클릭 시 카테고리 사이드바 닫기
    $(window).on('click', function (event) {
        if (!$(event.target).closest('#category-btn').length && $('#side-bar').is(':visible')) {
            $('#side-bar').hide();
        }
    });

    // 대분류 hover 시 중분류 표시
    $('.parent-category').hover(function () {
        $(this).find('.middle-category').show();
    }, function () {
        $(this).find('.middle-category').hide();
    });

    // 중분류 hover 시 소분류 표시
    $('.middle-category-item').hover(function () {
        $(this).find('.child-category').show();
    }, function () {
        $(this).find('.child-category').hide();
    });

    // 카테고리 클릭 시 아이템 목록 보기
    $('.parent-category, .middle-category-item, .child-category-item').on('click', function () {
        const categoryId = $(this).data('id');
        window.location.href = `/items/category/${categoryId}`;  // 경로를 /items/category로 통일
    });

});
function previewImage(event) {
            const reader = new FileReader();
            reader.onload = function () {
                const output = document.getElementById('imagePreview');
                output.src = reader.result;
                output.style.display = 'block';
            };
            reader.readAsDataURL(event.target.files[0]);
        }
