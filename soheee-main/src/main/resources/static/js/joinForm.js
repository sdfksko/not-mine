function sample6_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                document.getElementById("sample6_extraAddress").value = extraAddr;

            } else {
                document.getElementById("sample6_extraAddress").value = '';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('sample6_postcode').value = data.zonecode;
            document.getElementById("sample6_address").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("sample6_detailAddress").focus();
        }
    }).open();
}

$('#join-btn').on('click', function() {
    console.log('클릭');
    const memberData = {
        userId: $('#userId').val(),
        userPassword: $('#userPassword').val(),
        username: $('#username').val(),
        nickname: $('#nickname').val(),
        email: $('#email').val(),
        phoneNumber: $('#phoneNumber').val(),
        postalAddress: $('#sample6_postcode').val(),
        address: $('#sample6_address').val(),
        detailedAddress: $('#sample6_detailAddress').val()
    };

    const phoneNum = memberData.phoneNumber;

    console.log(memberData);

    if (!memberData.userId) {
        alert('아이디를 입력해 주세요.');
        return;
    }

    if (!memberData.userPassword) {
        alert('비밀번호를 입력해 주세요.');
        return;
    }

    if (!memberData.username) {
        alert('이름을 입력해 주세요.');
        return;
        }

    if (!memberData.nickname) {
        alert('닉네임을 입력해 주세요.');
        return;
    }

    if (!memberData.email) {
        alert('이메일을 입력해 주세요.');
        return;
    }

    if (!memberData.phoneNumber) {
        alert('휴대폰 번호를 입력해 주세요.');
        return;
    }

if (phoneNum.length != 11) {
        alert('올바른 휴대폰 번호를 입력해주십시오.');
        return;
    }

    if (!memberData.postalAddress) {
        alert('우편번호를 입력해 주세요.');
        return;
    }

    if (!memberData.address) {
        alert('주소를 입력해 주세요.');
        return;
    }

    if (!memberData.detailedAddress) {
        alert('상세주소를 입력해 주세요.');
        return;
    }

    $.ajax({
        type: 'POST',
        url: '/check/userId',
        data: JSON.stringify(memberData),
        contentType: 'application/json; charset=utf-8',
        success: function() {
            $.ajax({
                type: 'POST',
                url: '/check/nickname',
                data: JSON.stringify(memberData),
                contentType: 'application/json; charset=utf-8',
                success: function() {
                    $.ajax({
                        type: 'POST',
                        url: '/check/email',
                        data: JSON.stringify(memberData),
                        contentType: 'application/json; charset=utf-8',
                        success: function() {
                            $.ajax({
                                type: 'POST',
                                url: '/check/phoneNumber',
                                data: JSON.stringify(memberData),
                                contentType: 'application/json; charset=utf-8',
                                success: function() {
                                    $.ajax({
                                        type: 'POST',
                                        url: '/join',
                                        data: JSON.stringify(memberData),
                                        contentType: 'application/json; charset=utf-8',
                                        success: function() {
                                            alert('회원가입에 성공하였습니다.');
                                            location.href='/loginForm';
                                        },
                                        error: function(xhr,status,error) {
                                            alert('회원가입에 실패하였습니다.')
                                            return;
                                        }
                                    });
                                },
                                error: function(xhr,status,error) {
                                    alert(xhr.responseText);
                                    return;
                                }
                            });
                        },
                        error: function(xhr,status,error) {
                            alert(xhr.responseText);
                            return;
                        }
                    });
                },
                error: function(xhr,status,error) {
                    alert(xhr.responseText);
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