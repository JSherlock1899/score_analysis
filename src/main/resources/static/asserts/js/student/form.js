// $('#btn').click(function () {

//     var myName = $('#identity').val();
//     var myPass = $('#password').val();
//     if (myName == 'xiao' || myPass == 'qqq') {
//         alert('sss');
//     } else {
//         alert('sdaasda')
//     }
// })

//账户正则
// var reg = /^[1-9]{1,6}$/g;
// var str = "123456";

// 密码正则
// var reg = /^[A-Za-z][A-Za-z0-9]{3,11}$/g;
// var str = "das213"


function bindEvent() {
    $('#btn').click(function () {
        var myName = $('#identity').val(),
            myPass = $('#password').val(),
            reg = /^[1-9]{1,6}$/g,
            reg1 = /^[A-Za-z][A-Za-z0-9]{3,11}$/g;
        if (myName != '' && myPass != '') {
            if (reg.test(myName) && reg1.test(myPass)) {
                //    window.location.href = "/html/index.html"
            }
            if (reg.test(myName)) {
                alert('账号有误')
            }
            if (reg1.test(myPass)) {
                alert('面膜错误')
            }
        }
    })
}


bindEvent();