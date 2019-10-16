$("#classNumber,#examName,#pageSize").change(function () {
    var classNumber = $("#classNumber option:selected").val();
    var examName = $("#examName option:selected").val();
    var pageSize = $("#pageSize option:selected").val();
    var pageNum = $("#pageNum").val();
    //排序科目
    var subject = $(this).parent().attr("class");
    $.ajax({
        method: "post",
        url: "/teacher/selectStuGrade",
        datatype:"html",
        data:{
            "classNumber": classNumber,
            "exam": examName,
            "pageNum": pageNum,
            "pageSize": pageSize,
            "subject": subject,
            "sort": "asc"
        },
        success: function (msg) {
            $("#allbody").html(msg);
            var subject = $('#subject').val();
            var sort = $('#sort').val();
            $('#subject img').addClass(sort);
        },
        error: function (msg) {
            alert('请求出现错误...');
        }
    })
})

//改变排序的图片,查询并排序
$(".sort").click(function () {
    var str = '';
    //取出图片名
    str = this.src.slice(37);
    var sort = $('#sort').val();
    if (sort == "desc") {
        this.src = '/asserts/images/rise.png';
        $(this).removeClass("desc");
        $(this).addClass("asc")
    } else {
        this.src = '/asserts/images/decline.png';
        $(this).removeClass("asc");
        $(this).addClass("desc")
    }
    //查询并排序
    var classNumber = $("#classNumber option:selected").val();
    var examName = $("#examName option:selected").val();
    var pageSize = $("#pageSize option:selected").val();
    var pageNum = $("#pageNum").val();
    //排序科目
    var subject = $(this).parent().attr("class");
    //获取排序是升序还是降序
    var sort = $(this).attr("class").split(" ")[1];
    $.ajax({
        method: "post",
        url: "/teacher/selectStuGrade",
        datatype:"json",
        data:{
            "classNumber": classNumber,
            "exam": examName,
            "pageNum": pageNum,
            "pageSize": pageSize,
            "subject": subject,
            "sort": sort
        },
        success: function (msg) {
            $("#allbody").html(msg);
            var subject = $('#subject').val();
            var sort = $('#sort').val();
            $('#subject img').addClass(sort);
            // $('#' + subject).attr("sort " + sort);
        },
        error: function (msg) {
            alert('请求出现错误...');
        }
    })
})