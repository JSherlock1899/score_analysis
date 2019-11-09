function classJudge() {
    var myOptions = $('#startYearSelect').find('option');
    var newmyOptions = Array.from(myOptions);
    newmyOptions.forEach(function (item, index) {
        var myValue = $(item).attr('value')
        var str = ''
        str += '20' + myValue;
        var newStr = parseInt(str);
        var date = new Date();
        var year = parseInt(date.getFullYear());
        if (year - newStr == 0) {
            $(item).text('高一')
        }
        if (year - newStr == 1) {
            $(item).text('高二')
        }
        if (year - newStr == 2) {
            $(item).text('高三')
        }
        // if (year - newStr !== 2 && year - newStr !== 1 && year - newStr !== 0) {
        // }
    })
}

function gradeOptions() {
    var mygradeOptions = $('#examSelect').find('option');
    var newmygradeOptions = Array.from(mygradeOptions);
    newmygradeOptions.forEach(function (item) {
        var myValue = $(item).attr('value');
        myValue = myValue.substring(0, 2)
        var str = ''
        str += '20' + myValue;
        var newStr = parseInt(str);
        var date = new Date();
        var year = parseInt(date.getFullYear());
        if (year - newStr == 0) {
            var mytext = $(item).html();
            mytext = mytext.substring(2, mytext.length);
            console.log(mytext)
            var sstr = '高一' + mytext;
            $(item).html(sstr)
        }
        if (year - newStr == 1) {
            var mytext = $(item).html();
            mytext = mytext.substring(2, mytext.length);
            var sstr = '高二' + mytext ;
            $(item).html(sstr)
        } if (year - newStr == 2) {
            var mytext = $(item).html();
            mytext = mytext.substring(2, mytext.length);
            var sstr = '高三' + mytext;
            $(item).html(sstr)
        }
    })
}


function transform(subject) {
    switch (subject) {
        case "chinese": return "语文";break;
        case "math": return "数学";break;
        case "english": return "英语";break;
        case "physics": return "物理";break;
        case "chemistry": return "化学";break;
        case "biology": return "生物";break;
        case "politics": return "政治";break;
        case "history": return "历史";break;
        case "geography": return "地理";break;
        case "technology": return "技术";break;
        case "total_point": return "总分";break;
    }
}

//展示loading
function g_showLoading(){
    var idx = layer.msg('处理中...', {icon: 16,shade: [0.5, '#f5f5f5'],scrollbar: false,offset: '0px', time:100000}) ;
    return idx;
}

//salt
var g_passsword_salt="1a2b3c4d";