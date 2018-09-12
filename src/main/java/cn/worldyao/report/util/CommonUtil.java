package cn.worldyao.report.util;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 工具类
 * @author 董尧
 */
@Resource
public class CommonUtil {
    public static String resource;
    public static InputStream inputStream;
    public static Reader reader;

    /**
     * 获取当前时间
     * @return
     */
    @RequestMapping("getNowTime")
    public static String getNowTime(){
        //定义格式化时间样式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //new Date
        Date date = new Date();
        //格式化后的时间字符串
        return simpleDateFormat.format(date);
    }


    /**
     * 获取文件,文件默认在SRC根目录下
     *
     * @param fileName
     * @return
     */
    public static File getFilePath(String fileName) {
        String path = CommonUtil.class.getClassLoader().getResource(fileName).getPath();
        try {
            path = java.net.URLDecoder.decode(path, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new File(path.substring(1));
    }


    /**
     * 该个方法是计算当前是第几周
     * @return
     */
    @RequestMapping("getThisWeekNum")
    public static int getThisWeekNum() {
        //定义起始时间
        Date startTime = null;
        try {
            //转化为时间类型
            startTime = new SimpleDateFormat("yyyy-MM-dd").parse("2018-03-26");
            //进行计算
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int)((System.currentTimeMillis() - startTime.getTime())/(1000*60*60*24*7) + 1);
    }

    /**
     * 获取时间差，返回一个布尔型的结果
     * 结果为真则可以注册
     * 否者不可以注册
     * @param startTimestamp
     * @param endTimestamp
     * @return
     */
    public static boolean getDifftime (String startTimestamp,String endTimestamp){
        /**
         * (end - start) / (1000*60*60*24) 计算时间差
         * 1000*60*60*24   天数
         * 1000*60*60      小时数
         * 1000*60         分钟数
         */
        if ((Long.parseLong(endTimestamp) - Long.parseLong(startTimestamp)) / (1000*60*60*24) < 7){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 该个方法是返回周报的周次信息
     * @return
     */
    public static String thisWeekInfo(){
        Calendar calendar = Calendar.getInstance();
        //设置该周的第一天为周一
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        // 1表示周日，2表示周一，7表示周六
        calendar.set(Calendar.DAY_OF_WEEK, 2);
        //获取周一的时间
        Date dateMondy = calendar.getTime();
        calendar.set(Calendar.DAY_OF_WEEK, 1);
        //获取周日的时间
        Date dateSunday = calendar.getTime();
        String monday = new SimpleDateFormat("YYYY年MM月dd日").format(dateMondy);
        String sunday = new SimpleDateFormat("MM月dd日").format(dateSunday);
        return monday + "-" + sunday + "（第" + getThisWeekNum() + "周）";
    }

    public static String traDATAJSON(List list){
        return "{\"code\":0, \"msg\":\"ms\", \"count\":" + list.size() + ", \"data\":" + JSON.toJSONString(list) + "}";
    }

    public static String[] getDirList(){
        File file = new File("D:\\xtSoftware\\HOMEWORK\\1\\2018-08-09");

        return file.list();
    }

    public static String isSubmit(int classNum, int groupNum){
        System.out.println(groupNum);
        for (String st : getDirList()){
            if ((st.split("_")[0].contains(classNum + "")) && (st.split("_")[1].contains(groupNum + ""))){
            	if ((st.split("_")[2].contains(CommonUtil.getNowTime().split(" ")[0].substring(5) + ""))){
		            return st;
	            }else {
            		return null;
	            }
            }
        }
        return null;
    }

    public static String timeTrans(String timeString){
        String data = timeString.split(" ")[0];
        String year = data.split("-")[0];
        String mouth = data.split("-")[1];
        String day = data.split("-")[2];
        return (day + "/" + mouth + "/" + year);
    }
}
