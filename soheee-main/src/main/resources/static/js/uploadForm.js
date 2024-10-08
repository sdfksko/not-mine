$(document).ready(function() {
    // 대분류 선택 시 중분류 가져오기
    $('#parentCategory').on('change', function() {
        let parentId = $(this).val();
        if (parentId) {
            $.ajax({
                url: '/items/middle/' + parentId,
                method: 'GET',
                success: function(response) {
                    $('#middleCategory').empty().append('<option value="">중분류 선택</option>');
                    $('#childCategory').empty().append('<option value="">소분류 선택</option>');
                    response.forEach(function(category) {
                        $('#middleCategory').append('<option value="' + category.id + '">' + category.name + '</option>');
                    });
                    $('#middleCategory').prop('disabled', false);
                }
            });
        } else {
            $('#middleCategory').empty().append('<option value="">중분류 선택</option>').prop('disabled', true);
            $('#childCategory').empty().append('<option value="">소분류 선택</option>').prop('disabled', true);
        }
    });

    // 중분류 선택 시 소분류 가져오기
    $('#middleCategory').on('change', function() {
        let middleId = $(this).val();
        if (middleId) {
            $.ajax({
                url: '/items/child/' + middleId,
                method: 'GET',
                success: function(response) {
                    $('#childCategory').empty().append('<option value="">소분류 선택</option>');
                    response.forEach(function(category) {
                        $('#childCategory').append('<option value="' + category.id + '">' + category.name + '</option>');
                    });
                    $('#childCategory').prop('disabled', false);
                }
            });
        } else {
            $('#childCategory').empty().append('<option value="">소분류 선택</option>').prop('disabled', true);
        }
    });
});