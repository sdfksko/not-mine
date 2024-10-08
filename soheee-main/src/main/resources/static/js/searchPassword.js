let number;

$(document).ready(function() {
    // 인증번호 전송 버튼 클릭 시 발동 이벤트
    $('#transmitButton').on('click', function() {

        let email = $('.first').val();

        if (email === "") {
            alert("이메일을 입력해주세요.");
            return;
        }


        $.ajax({
            type: 'POST',
            url: '/emailCheck',
            data: JSON.stringify({ email: email }),
            contentType: 'application/json; charset=utf-8',
            success: function(authCode) {

                $('#authCodeInput').val(authCode);

                number = authCode;
                alert('인증번호가 전송되었습니다.');
            },
            error: function(xhr, status, error) {
                alert('인증번호 전송에 실패했습니다. 등록된 이메일이 아닙니다.');
            }
        });
    });
});

function search() {
    console.log('클릭');
    let sample = document.querySelector('#number').value
    if(sample == number) {
        location.href='/new/password';
    } else {
        alert('인증번호가 일치하지 않습니다.');
    }
}