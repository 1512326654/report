
    //显示周报
    function report_show(CLASSNUM, SHEETNUM, SHOWID) {
        $.ajax({
            // url:"../ExcelServlet",
            type: "post",
            data:{
                operating:"showReport",
                classNum:CLASSNUM,
                sheetNum:SHEETNUM,
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



// 提交周报函数
// 包含四个参数分别是：班级号，工作表序号，周报内容，
    function report_add(CLASSNUM, SHEETNUM, REPORTCONTEXT) {
        $.ajax({
            url:"/student/addReport.do",
            type: "POST",
            data:{
                stuClass:CLASSNUM,
                stuSheet:SHEETNUM,
                stuContext:REPORTCONTEXT
            },
            success: function (result) {
                var dataObj2 = JSON.parse(result);
                if (dataObj2.indexOf("成功") != -1) {
                    layer.msg(dataObj2,{icon:6})
                }else{
                    layer.msg(dataObj2,{icon:5})
                }
            },
            error: function (res) {
                layer.msg("您当前的网络不佳，提交失败请稍后再试",{icon:5})
            }
        })
    }

//通过条件查询周报
    function report_show_byname(CLASSNUM, WEEKNUM, SNAME, SHOWID) {
        $.ajax({
            // url:"../LeaderBackStageServlet",
            type: "post",
            data:{
                operating:"showReport",
                classNum:CLASSNUM,
                weekNum:WEEKNUM,
                s_name:SNAME
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

// 修改周报，这个函数是管理员使用，通过给定的班级号，周次号，学生姓名，和周报内容
    function report_edit(CALSSNUM ,STUNAME, WEEKNUM, REPORTCONTEXT) {
        $.ajax({
            // url:"../AdminBackStageServlet",
            type: "POST",
            data:{
                operating:"editReport",
                classNum:CALSSNUM,
                stuName:STUNAME,
                weekNum:WEEKNUM,
                reportContext:REPORTCONTEXT
            },
            success: function (result) {
                var dataObj2 = JSON.parse(result);
                if (dataObj2.indexOf("成功") != -1) {
                    layer.msg(dataObj2,{icon:6});
                }else{
                    layer.msg(dataObj2,{icon:5})
                }
            },
            error: function (res) {
                layer.msg("没有修改成功，请稍后再试",{icon:5})
            }
        })
    }

// 组成员周报提交状态
    function show_menberReportes_status(CLASSNUM, GROUPNUM, SHOWID) {
        $.ajax({
            // url:"../LeaderBackStageServlet",
            type:"POST",
            async: true,
            data:{
                operating:"showMemberReportedStatus",
                classNum:CLASSNUM,
                groupNum:GROUPNUM
            },
            success: function (result) {
                var data = JSON.parse(result);
                var trstyle;
                $.each(data,function(index,element){
                    if ((data[index].status).indexOf("未提交") != -1) {
                        trstyle = "<tr style='color: #F8784D'>"
                    }
                    if ((data[index].status).indexOf("已提交") != -1) {
                        trstyle = "<tr style='color: ##3FF388'>"
                    }
                    $(SHOWID).append(
                        trstyle +
                        "<th>" + data[index].s_name + "</th>" +
                        "<td>" + data[index].status + "</td>" +
                        "</tr>"
                    )
                })
            },
            error: function (res) {
                layer.msg("数据获取失败",{icon:5})
            }
        })
    }

