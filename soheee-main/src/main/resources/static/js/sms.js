let phoneNum;   // 휴대폰 번호
let checkNum;   // 인증번호


$('#transmit-number').on('click', function() {
   console.log('클릭');

   phoneNum = $('#phoneNumber').val();

   if(!phoneNum) {
        alert('휴대폰 번호를 입력해주세요.')
        return;
   }

   if(phoneNum.length != 11) {
        alert('올바른 휴대폰 번호를 입력해주십시오.');
        return;
   }

   $.ajax({
        type: 'POST',
        url: '/sms/check',
        data: JSON.stringify(phoneNum),
        contentType: 'application/json; charset=utf-8',
        success: function(response) {
            $.ajax({
                type: 'POST',
                url: '/sms/send',
                data: JSON.stringify(phoneNum),
                contentType: 'application/json; charset=utf-8',
                success: function(response) {
                    checkNum = response;
                    alert('인증번호를 전송하였습니다.');
                },
                error: function(xhr,status,error) {
                    alert('인증번호 전송에 실패하였습니다.')
                    return;
                }
            });
        },
        error: function(xhr,status,error) {
            alert(xhr.responseText);
            return;
        }
   });
});

$('#check-number').on('click', function() {
    let insertNumber = $('#number').val();
    if(checkNum != insertNumber) {
        alert('인증번호가 일치하지 않습니다.');
        return;
    }
    $.ajax({
        type: 'POST',
        url: "/sms/searchId",
        data: JSON.stringify(phoneNum),
        contentType: 'application/json; charset=utf-8',
        success: function() {
            alert('아이디 찾기에 성공하였습니다.')
            location.href='/show/searchId';
        },
        error: function(xhr,stats,error) {
            alert(xhr.responseText);
            return;
        }
    });
});