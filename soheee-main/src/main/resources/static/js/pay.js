function requestPay() {
  IMP.init('iamport'); //iamport 대신 자신의 "가맹점 식별코드"를 사용
  IMP.request_pay({
    pg: "inicis",   // INIpayTest
    pay_method: "card",
    merchant_uid : 'merchant_'+new Date().getTime(),
    name : '결제테스트',
    amount : 100,
    buyer_email : 'iamport@siot.do',
    buyer_name : '구매자',
    buyer_tel : '010-1234-5678',
    buyer_addr : '서울특별시 강남구 삼성동',
    buyer_postcode : '123-456'
  }, function (rsp) { // callback
      console.log(rsp);
          if (rsp.success) {
            var msg = '결제가 완료되었습니다.';
            alert(msg);
            location.href = "결제 완료 후 이동할 페이지 url"
          } else {
            var msg = '결제에 실패하였습니다.';
            msg += '에러내용 : ' + rsp.error_msg;
            alert(msg);
          }
  });
}