let password;

let passwordClone;

$(document).ready(function() {
    $('#new-btn').on('click', function() {
        userPassword = $('.first').val();
        passwordClone = $('.second').val();

        if(userPassword != passwordClone) {
            alert('패스워드가 일치하지 않습니다.');
        } else {
            $.ajax({
                type: 'POST',
                url: '/newPassword',
                data: JSON.stringify({ userPassword: userPassword}),
                contentType: 'application/json; charset=utf-8',
                success: function() {
                    alert('새 비밀번호 생성에 성공하였습니다.');
                    location.href='/loginForm';
                },
                error: function() {
                    alert('새 비밀번호 생성에 실패하였습니다.');
                }
            });
        }
    });
});