$(function () {
    var myChart = echarts.init(document.getElementById('broken_line'));
    var jsongrades = $('#jsongrades').val();
    var classNumber = $('#preClassNumber').val();
    var subject = $('#subject').val();
    subject = transform(subject);
    var exams = $('#exams').val();
    jsongrades = $.parseJSON(jsongrades);
    exams = $.parseJSON(exams);
    console.log(jsongrades)
    var classGrade = new Array();//班级成绩
    var examArr = new Array(); // 考试名
    for (var i = 0; i < 3; i++) {
        classGrade.push(jsongrades[i].grade)
        examArr.push(exams[0][i].examName)
    }
    // 折线图
    var option = {
        title: {
            text: "历次" + subject + "分析图"
        },
        tooltip: {
            trigger: 'axis'
        },
        legend : {
            data : [ classNumber ]
        },
        grid : {
            left : '3%',
            right : '4%',
            bottom : '3%',
            containLabel : true
        },
        toolbox : {
            feature : {
                saveAsImage : {}
            }
        },

        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: examArr
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name:classNumber,
                type:'line',
                data:classGrade
            },
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
})

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