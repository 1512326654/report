// 提交周报函数
// 包含四个参数分别是：班级号，工作表序号，周报内容，消除的ID
function report_add(STUID, STUCLASS, STUSHEET, REPORTCONTEXT) {
    $.ajax({
        url:"/student/addReport.do",
        type: "POST",
        data:{
            stuId:STUID,
            stuClass:STUCLASS,
            stuSheet:STUSHEET,
            stuContext:REPORTCONTEXT
        },
        success: function (result) {
            var dataObj2 = JSON.parse(result);
            // alert(dataObj2);
            if (dataObj2.indexOf("成功") != -1) {
                layer.msg(dataObj2 + "正在刷新",{icon:6});
                setTimeout(function () {
                    location.replace(location.href)
                },2000)
            }else{
                layer.msg(dataObj2,{icon:5});
            }
        },
        error: function (res) {
            layer.msg("您当前的网络不佳，提交失败请稍后再试",{icon:5})
        }
    })
}

//显示周报
function report_show(STUCLASS, STUSHEET, SHOWID) {
    $.ajax({
        url:"/student/showThisWeekReport.do",
        type: "POST",
        data:{
            stuClass:STUCLASS,
            stuSheet:STUSHEET
        },
        success: function (result) {
            var dataObj2 = JSON.parse(result);
            $(SHOWID).val(dataObj2);
        },
        error: function (res) {
            // layer.msg("加载周报信息失败！",{icon:5})
        }
    })
}

// 组成员周报提交数据统计
function showGroupDataStatistics(STUCALSS, STUGROUP, SHOWID) {
    $.ajax({
        url:"/student/showGroupDataStatistics.do",
        type:"GET",
        data:{
            stuClass:STUCALSS,
            stuGroup:STUGROUP
        },
        success: function (result) {
            var data = JSON.parse(result);
            $(SHOWID).text(data.reportednum + "/" + data.num)
        },
        error: function (res) {
            layer.msg("数据获取失败",{icon:5})
        }
    });
}

function showReportByREPID(repId, SHOWID) {
    $.ajax({
        url:"/student/showReportByREPID.do",
        type: "POST",
        data:{
            repId:repId
        },
        success: function (result) {
            var dataObj2 = JSON.parse(result);
            $(SHOWID).val(dataObj2);
        },
        error: function (res) {
            // layer.msg("加载周报信息失败！",{icon:5})
        }
    })
}
