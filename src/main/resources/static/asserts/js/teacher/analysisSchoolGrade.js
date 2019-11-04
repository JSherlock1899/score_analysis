$('#btn').on("click", function () {
    var startYear = $("#startYear").val();
    var exam = $("#exam").val();
    var cutOffGrade = $("#cutOffGrade").val();
    $.ajax({
        methods: "post",
        url: "/teacher/setCutOffGrade",
        dataType: "html",
        data:{
            "startYear": startYear,
            "exam": exam,
            "cutOffGrade": cutOffGrade
        },
        success: function (data) {
            $("#allbody").html(data);
        },
        error: function (msg) {
            alert('请求出现错误...');
        }
    })
})


//绘制柱形图
$(function() {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('school_grades_bar'));
    // 分别获取从后台传过来的数据
    var jsonArray = $('#jsonArray').val();
    var text = "各班过线人数统计图";
    // 解析json
    jsonArray = $.parseJSON(jsonArray);
    console.log(jsonArray)
    var keyArr = new Array(); // 班级
    var valueArr = new Array();// 过线人数
    for (var i=0;i<jsonArray.length;i++){
        keyArr.push(jsonArray[i].classNumber);
        valueArr.push(jsonArray[i].number);
    }
    // 指定图表的配置项和数据
    var bar_option = {
        title : {
            text : text,
        },
        tooltip : {},
        legend : {
            data : [ '过线人数' ]
        },
        xAxis : {
            data : keyArr,
            axisLabel : {
                interval : 0,
                rotate : 30
            },
            grid : {// 直角坐标系内绘图网格
                show : true,// 是否显示直角坐标系网格。[ default: false ]
                left : "20%",// grid 组件离容器左侧的距离。
                right : "30px",
                borderColor : "#c45455",// 网格的边框颜色
                bottom : "20%" //
            },
        },
        yAxis : {},
        series : [ {
            name : '过线人数',
            type : 'bar',
            data : valueArr
        } ]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(bar_option);

})

$(function () {
    var myChart = echarts.init(document.getElementById('broken_line'));
    var jsongrades = $('#jsongrades').val();
    var classNumber = $('#preClassNumber').val();
    var exams = $('#exams').val();

    jsongrades = $.parseJSON(jsongrades);
    exams = $.parseJSON(exams);
    var classGrade = new Array();//班级成绩
    var examArr = new Array(); // 考试名
    for (var i = 0; i < 3; i++) {
        classGrade.push(jsongrades[i].grade)
        examArr.push(exams[0][i].examName)
    }
    // 折线图
    var option = {
        title: {
            text: '折线图堆叠'
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