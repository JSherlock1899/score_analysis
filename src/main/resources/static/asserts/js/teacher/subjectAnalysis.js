$(function () {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('school_ranking_bar'));
    var jsonArray = $("#jsonArray").val();
    jsonArray = $.parseJSON(jsonArray);
    //分析的科目
    var subject = $("#subject").val();
    //选择的分段
    var ranking = $("#ranking").val();
    subject = transform(subject);
    $("#subjectSelect").find("option").each(function(){
        if($(this).text() == subject)	{
            $(this).attr("selected",true);
        }
    });
    $("#rankingSelect").find("option").each(function(){
        if($(this).text() == ranking)	{
            $(this).attr("selected",true);
        }
    });
    var keyArr = new Array(); // 班级
    var valueArr = new Array();// 人数
    for (var i=0;i<jsonArray.length;i++){
        keyArr.push(jsonArray[i].classNumber);
        valueArr.push(jsonArray[i].number);
    }
    var text = "各班" + subject + "全校前" + ranking + "名人数";
    // 指定图表的配置项和数据
    var bar_option = {
        title : {
            text : text,
        },
        tooltip : {},
        legend : {
            data : [ '人数' ]
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
            name : '人数',
            type : 'bar',
            data : valueArr
        } ]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(bar_option);
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