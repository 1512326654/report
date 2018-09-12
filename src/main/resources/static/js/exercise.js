function getType() {
    $.ajax({
        url: "/student/showExerciseType",
        type: "POST",
        data: {},
        success: function (result) {
            var data = JSON.parse(result);
            $.each(data, function (index, item1) {
                if (((index) % 2) == 0) {
                    $(".typemain").append("<div class='layui-row layui-col-space15 exType" + index + "'></div>")
                }
                $(".exType" + index).append(
                    "<div class='layui-col-md6'>" +
                    "   <div class='layui-card'>" +
                    "       <div class='layui-card-body'>" +
                    "           <fieldset class='layui-elem-field layui-field-title exCata" + index + "'>" +
                    "               <legend>" + item1.extypeName + "</legend>" +
                    "           </fieldset>" +
                    "       </div>" +
                    "   </div>" +
                    "</div>"
                );
                $(".exType" + (index - 1)).append(
                    "<div class='layui-col-md6'>" +
                    "   <div class='layui-card'>" +
                    "       <div class='layui-card-body'>" +
                    "           <fieldset class='layui-elem-field layui-field-title exCata" + index + "'>" +
                    "               <legend>" + item1.extypeName + "</legend>" +
                    "           </fieldset>" +
                    "       </div>" +
                    "   </div>" +
                    "</div>"
                );
                var cataList = item1.cataList;
                $.each(cataList, function (index2, item2) {
                    var sel = "type" + item1.extypeId + "cata" + item2.exCataId;
                    $(".exCata" + index).append(
                        "<div class='layui-collapse'>" +
                        "  <div class='layui-colla-item'>" +
                        "      <h2 class='layui-colla-title' onclick='showDiv(" + item1.extypeId + "," + item2.exCataId + ",\"" + item1.extypeName + "\",\"" + item2.exCataName + "\")'>" + item2.exCataName + "</h2>" +
                        "      <ul class='layui-colla-content' id='" + sel + "'>" +
                        "      </ul>" +
                        "  </div>" +
                        " </div>"
                    );
                });
            });
        },
        error: function (re) {
            alert(re)
        }
    })
}

function showDiv(TYPEID, CATAID, TYPENAME, CATANAME) {
    var sel = "type" + TYPEID + "cata" + CATAID;
    $("#" + sel).addClass("layui-show");
    $("#" + sel).html("");
    $.ajax({
        url: "/student/getExerciseTypeNum",
        type: "POST",
        data: {
            extypeId:TYPEID,
            exCataId:CATAID,
            pageNum:1
        },
        success: function (result) {
            var data = JSON.parse(result);
            for (var i = 0 ; i < data ; i++){
                $("#" + sel).append(
                    "<li>" +
                    "<a class='exTitle" + i + "' href='/student/exercise?extypeId=" + TYPEID + "&exCataId=" + CATAID + "&pageNum=" + (i + 1) + "&exTitle=" + TYPENAME + CATANAME + "（" + (i + 1) + "）'>" + TYPENAME + CATANAME + "（" + (i + 1) + "）"  + "</a></li>"
                );
            }
        },
        error: function (re) {
            alert(re)
        }
    })
}


/*function showExercise(TYPEID, CATAID, PAGE) {
    $.ajax({
        url: "/student/showExercise",
        type: "POST",
        data: {
            extypeId:TYPEID,
            exCataId:CATAID,
            pageNum:PAGE
        },
        success: function (result) {
            var data = JSON.parse(result);
            for (var i = 0 ; i < data ; i++){
                $("#" + sel).append(
                    "<li><a>" + TYPENAME + CATANAME + "（" + (i + 1) + "）"  + "</a></li>"
                );
            }
        },
        error: function (re) {
            alert(re)
        }
    })
}*/
