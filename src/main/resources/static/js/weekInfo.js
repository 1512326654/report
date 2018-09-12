//获取当前周的数字
function getThisWeekNum(){
    //定义起始时间
    var date = "2018-03-26";
    var startTime = getTimestamp(date);
    var nowtime = new Date();
    var fnowtime = formatDate(nowtime);
    var nowTimestamp = getTimestamp(fnowtime);
    var cha = ((nowTimestamp - startTime)/(1000*60*60*24*7)).toString().substring(0,2);
    return parseInt(cha) + 1;
}
//获取时间戳
function getTimestamp(time){
    time = time.replace(/-/g,"/");
    return new Date(time).getTime();
}
//获取当前周字符串
function getThisWeek(){
    var now = new Date();
    var nowTime = now.getTime() ;
    var day = now.getDay();
    var oneDayLong = 24*60*60*1000 ;
    var MondayTime = nowTime - (day-1)*oneDayLong;
    var SundayTime = nowTime + (7-day)*oneDayLong;
    var monday = new Date(MondayTime);
    var sunday = new Date(SundayTime);
    // $("#thisweek").val(formatMon(monday) + "-" + formatSun(sunday) + "（第" + getThisWeekNum() + "周）" )
    return (formatMon(monday) + "-" + formatSun(sunday) + "（第" + getThisWeekNum() + "周）" );
}
//当前周的周一
function formatMon(date){
    var mat={};
    mat.M=date.getMonth()+1;//月份记得加1
    mat.Y=date.getFullYear();
    mat.D=date.getDate();
    mat.M=check(mat.M);
    mat.D=check(mat.D);
    return mat.Y+"年"+mat.M+"月"+mat.D+"日";
}
//当前周的周末
function formatSun(date){
    var mat={};
    mat.M=date.getMonth()+1;//月份记得加1
    mat.D=date.getDate();
    mat.M=check(mat.M);
    mat.D=check(mat.D);
    return mat.M+"月"+mat.D+"日";
}
//检查是不是两位数字，不足补全
function check(str){
    str=str.toString();
    if(str.length<2){
        str='0'+ str;
    }
    return str;
}
//格式化时间
function formatDate(date) {
    var mat={};
    mat.M=date.getMonth()+1;//月份记得加1
    mat.Y=date.getFullYear();
    mat.D=date.getDate();
    mat.M=check(mat.M);
    mat.D=check(mat.D);
    return mat.Y+"-"+mat.M+"-"+mat.D;
}