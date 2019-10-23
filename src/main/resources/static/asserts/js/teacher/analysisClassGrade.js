//绘制折线统计图
$(function () {
    var myChart = echarts.init(document.getElementById('broken_line'));
    var jsonList = $('#jsonList').val();
    jsonList = $.parseJSON(jsonList);
    var examName = new Array();//考试名
    var chineseGrades = new Array();//语文成绩
    var mathGrades = new Array();//数学成绩
    var englishGrades = new Array();//英语成绩
    var physicsGrades = new Array();//物理成绩
    var chemistryGrades = new Array();//化学成绩
    var biologyGrades = new Array();//生物成绩
    var politicsGrades = new Array();//政治成绩
    var historyGrades = new Array();//历史成绩
    var geographyGrades = new Array();//地理成绩
    var technologyGrades = new Array();//技术成绩
    var totalPointGrades = new Array();//总成绩
    for (var i=0; i<jsonList.length; i++){
        examName.push(jsonList[i].name);
        chineseGrades.push(jsonList[i].chineseAverageGrades);
        mathGrades.push(jsonList[i].mathAverageGrades);
        englishGrades.push(jsonList[i].englishAverageGrades);
        physicsGrades.push(jsonList[i].physicsAverageGrades);
        chemistryGrades.push(jsonList[i].chemistryAverageGrades);
        biologyGrades.push(jsonList[i].biologyAverageGrades);
        politicsGrades.push(jsonList[i].politicsAverageGrades);
        historyGrades.push(jsonList[i].historyAverageGrades);
        geographyGrades.push(jsonList[i].geographyAverageGrades);
        technologyGrades.push(jsonList[i].technologyAverageGrades);
        totalPointGrades.push(jsonList[i].totalPointAverageGrades);
        }

    var option = {
        title: {
            text: '各科平均分'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: jsonList.name
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: examName
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name: '语文',
                type: 'line',
                stack: '总量',
                data: chineseGrades
            },
            {
                name: '数学',
                type: 'line',
                stack: '总量',
                data: mathGrades
            },
            {
                name: '英语',
                type: 'line',
                stack: '总量',
                data: englishGrades
            },
            {
                name: '物理',
                type: 'line',
                stack: '总量',
                data: physicsGrades
            },
            {
                name: '化学',
                type: 'line',
                stack: '总量',
                data: chemistryGrades
            },
            {
                name: '生物',
                type: 'line',
                stack: '总量',
                data: biologyGrades
            },
            {
                name: '政治',
                type: 'line',
                stack: '总量',
                data: politicsGrades
            },
            {
                name: '历史',
                type: 'line',
                stack: '总量',
                data: historyGrades
            },
            {
                name: '地理',
                type: 'line',
                stack: '总量',
                data: geographyGrades
            },
            {
                name: '技术',
                type: 'line',
                stack: '总量',
                data: technologyGrades
            },
            {
                name: '总分',
                type: 'line',
                stack: '总量',
                data: totalPointGrades
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);

})

$(function () {
    var myChart = echarts.init(document.getElementById('pie'));
    var rankingDistribute = $("#rankingDistribute").val();
    rankingDistribute = $.parseJSON(rankingDistribute);
    console.log(rankingDistribute)
    var type = new Array(); //排名类型
    var number = new Array(); //人数
    var jsonArr = [];
    for (var i = 0; i<rankingDistribute.length ; i++){
        type.push(rankingDistribute[i].type);
        number.push(rankingDistribute[i].number)
        jsonArr.push({
            name: rankingDistribute[i].type,
            value: rankingDistribute[i].number
        })
    }
    var option = {
        title: {
            text: '学生排名分布',
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: type
        },
        series: [
            {
                name: '排名',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data:  jsonArr,
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    myChart.setOption(option);


})