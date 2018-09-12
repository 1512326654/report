package cn.worldyao.report.controller;


import cn.worldyao.report.entity.*;
import cn.worldyao.report.service.ExerciseService;
import cn.worldyao.report.service.StudentService;
import cn.worldyao.report.service.UserService;
import cn.worldyao.report.util.CommonUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 董尧
 */
@Controller
@RequestMapping("student")
@SessionAttributes("info")
public class StudentController {
	@Resource
	private StudentService studentService;
	@Resource
	private UserService userService;
	@Resource
	private ExerciseService exerciseService;


	private final static int EXERCISE_PAGE_SIZE = 5;

	/**
	 * 学生登录
	 *
	 * @param stuName
	 * @param stuPass
	 */
	@RequestMapping(value = "/login", method = {RequestMethod.POST})
	public ModelAndView studentLogin(String stuName, String stuPass, ModelMap model) {
		ModelAndView modelAndView = new ModelAndView();
		if ((stuName.length() != 0) || (stuPass.length() != 0)) {
			User user = userService.userLogin(stuName, DigestUtils.md5Hex(stuPass).toUpperCase());
//			判断是否存在用户
			if (user != null) {
//				判断用户信息是否关联
				if (user.getUser_stuId() == 0) {
//					未关联
					Student student = new Student();
					student.setStuName(user.getUserName());
					Map map = new HashMap();
					map.put("student", student);
					map.put("userId", user.getUserId());
					map.put("flag", false);
					model.addAttribute("info", map);
					modelAndView.setViewName("student/index");
				} else {
//					已关联
					Student student = userService.studentConnection(user.getUser_stuId());
					Map map = new HashMap(2);
					map.put("student", student);
					map.put("flag", true);
//					验证是否为组长
					if (student.getStuLeader() == -1) {
						List<ReportBase> list = studentService.timeLine(student.getStuClass(),student.getStuSheet());
						for (ReportBase reportBase : list){
							reportBase.setRepAddTime(CommonUtil.timeTrans(reportBase.getRepAddTime()));
							System.out.println(reportBase);
						}
						map.put("timeLine",list);
						modelAndView.setViewName("student/index");
					} else if (student.getStuLeader() == 3) {
						System.out.println("--------------------------------");
						List<ReportBase> list = studentService.timeLine(student.getStuClass(),student.getStuSheet());
						for (ReportBase reportBase : list){
							reportBase.setRepAddTime(CommonUtil.timeTrans(reportBase.getRepAddTime()));
							System.out.println(reportBase);
						}
						map.put("timeLine",list);
						modelAndView.setViewName("week/index");
					}
					model.addAttribute("info", map);
					System.out.println(student);
					System.out.println("-------------15" + modelAndView);
				}
			} else {
				modelAndView.setViewName("/login2");
			}
		} else {
			modelAndView.setViewName("/login2");
		}
		return modelAndView;
	}



	/**
	 * 退出登录
	 * @param session
	 * @return
	 */
	@RequestMapping("/loginOut")
	public ModelAndView loginOut(HttpSession session) {
		session.removeAttribute("info");
		session.invalidate();
		return new ModelAndView("/login2");
	}


	/**
	 * 信息关联
	 *
	 * @param stuId
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/connectionInfo", method = {RequestMethod.POST}, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String connectionInfo(int stuId, int userId) {
//		ModelAndView modelAndView = new ModelAndView();
		System.out.println("--------------------->stuId" + stuId + "userId" + userId);
		if (userService.connectionInfo(stuId, userId)) {
//			modelAndView.setViewName("redirect:/student/toTndex");
//			modelAndView.addObject("flag", "关联成功");
//			return modelAndView;
			return JSON.toJSONString("关联成功");
		}
		return JSON.toJSONString("该用户已经被关联");
//		modelAndView.addObject("flag", "该用户已经被关联");
//		return modelAndView;
	}
/*	*//**
	 * 跳转页面到suggest
	 *
	 * @return
	 *//*
	@RequestMapping(value = "/connectionInfo", method = {RequestMethod.POST}, produces = "text/html;charset=UTF-8")
	public ModelAndView index(@ModelAttribute("info") Map map , HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("student/index");
		return modelAndView;
	}*/

	/**
	 * 学生添加周报
	 *
	 * @param stuClass
	 * @param stuSheet
	 * @param stuContext
	 */
	@RequestMapping(value = "/addReport", method = {RequestMethod.POST}, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String studentAddReport(int stuId, int stuClass, int stuSheet, String stuContext) {
		//判断是否提交周报
		if (studentService.studentThisReported(stuClass, CommonUtil.getThisWeekNum(), stuSheet)) {
			if (studentService.applyReport(stuId, stuClass, stuSheet, stuContext)) {
				return JSON.toJSONString("提交周报成功", SerializerFeature.PrettyFormat);
			}
		} else {
			if (studentService.studentAlter(stuClass, stuSheet, stuContext)) {
				return JSON.toJSONString("修改周报成功！！", SerializerFeature.PrettyFormat);
			}
		}
		return JSON.toJSONString("未知原因的错误！！", SerializerFeature.PrettyFormat);
	}

	/**
	 * 显示周报信息
	 *
	 * @param stuClass
	 * @param stuWeek
	 * @param stuSheet
	 * @return
	 */
	@RequestMapping(value = "/showReport", method = {RequestMethod.POST}, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String studentShowReport(int stuClass, int stuWeek, int stuSheet) {
		String requestContext = studentService.showThisWeekReport(stuClass, stuWeek, stuSheet);
		if (requestContext != null) {
			return JSON.toJSONString(requestContext);
		}
		return null;
	}


	@RequestMapping(value = "/getExerciseTypeNum", method = {RequestMethod.POST}, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getExerciseTypeNum(int extypeId, int exCataId) {
		int excount = exerciseService.getExerciseTypeNum(extypeId, exCataId);

		int page = excount / EXERCISE_PAGE_SIZE ;

		System.out.println(JSON.toJSONString(page));
		return JSON.toJSONString(page);
	}


	/**
	 * 显示题目
	 * @param extypeId
	 * @param exCataId
	 * @param pageNum
	 * @return
	 */
	@RequestMapping(value = "/showExercise", method = {RequestMethod.POST}, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String showExercise(int extypeId, int exCataId, int pageNum) {
		System.out.println(extypeId);
		System.out.println(exCataId);
		System.out.println(pageNum);
		List<Exercise> list = exerciseService.getExerciseType(extypeId, exCataId, pageNum, EXERCISE_PAGE_SIZE);
		for (Exercise exercise : list){
			exercise.setExContent(exercise.getExContent().replace("\n","<br/>").replace(" ","&nbsp"));
			System.out.println(exercise.getExContent().contains("\\&[a-zA-Z]{1,10};"));
		}
		System.out.println(JSON.toJSONString(list));
		return JSON.toJSONString(list);
//		return null;
	}

	/**
	 * 获取返回的答案数据
	 * @param resultAnswer
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/getResultAnswer", method = {RequestMethod.POST}, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public void getResultAnswer(@RequestBody String resultAnswer) throws UnsupportedEncodingException {
		System.out.println(resultAnswer);
		System.out.println(URLDecoder.decode(resultAnswer,"UTF-8"));
//		System.out.println(JSONArray.parse(resultAnswer));
//		System.out.println(4545);
//		List list = Arrays.asList(resultAnswer);
//		for (Object o : list){
//			System.out.println(o);
//		}
	}


	/**
	 * 获取分类列表
	 * @return
	 */
	@RequestMapping(value = "/showExerciseType", method = {RequestMethod.POST}, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String showExerciseType() {
		return JSON.toJSONString(exerciseService.getType());
	}

	/**
	 * 显示本周的周报内容
	 *
	 * @param stuClass
	 * @param stuSheet
	 * @return
	 */
	@RequestMapping(value = "/showThisWeekReport", method = {RequestMethod.POST}, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String studentShowThisWeekReport(int stuClass, int stuSheet) {
		System.out.println("------------------>showThisWeekReport");
		return studentShowReport(stuClass, CommonUtil.getThisWeekNum(), stuSheet);
	}

	/**
	 * 显示时间轴
	 *
	 * @param stuClass
	 * @param stuSheet
	 * @return
	 */
	@RequestMapping(value = "/showTimeLine", method = {RequestMethod.POST}, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String showTimeLine(int stuClass, int stuSheet) {
		List<ReportBase> list = studentService.timeLine(stuClass,stuSheet);
		for (ReportBase reportBase : list){
			reportBase.setRepAddTime(CommonUtil.timeTrans(reportBase.getRepAddTime()));
			System.out.println(reportBase);
		}
		System.out.println("------------------>showTimeLine");
		System.out.println("------------------>" + JSON.toJSONString(list));
		return JSON.toJSONString(list);
	}

	/**
	 * 成员的周报提交状态
	 *
	 * @param stuClass
	 * @param stuGroup
	 * @return
	 */
	@RequestMapping(value = "/showMemberGroupReportStatus", method = {RequestMethod.POST}, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String showMemberGroupReportStatus(int stuClass, int stuGroup, boolean index) {
		List<MemberReportStatus> list = studentService.showMemberReportStatus(stuClass, stuGroup, CommonUtil.getThisWeekNum());
		if (index) {
			return JSON.toJSONString(list).toString();
		} else {
			JSONDate jsonDate = new JSONDate(list, 50);
			return jsonDate.toString();
		}

	}

	/**
	 * 周报提交数据统计
	 *
	 * @param stuClass
	 * @param stuGroup
	 * @return
	 */
	@RequestMapping(value = "/showGroupDataStatistics", method = {RequestMethod.GET}, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String showGroupDataStatistics(int stuClass, int stuGroup) {
		return JSON.toJSONString(studentService.showGroupDataStatistics(stuClass, stuGroup, CommonUtil.getThisWeekNum()));
	}


	/**
	 * 组周报的提交状态
	 *
	 * @param stuClass
	 * @param stuGroup
	 * @return
	 */
	@RequestMapping(value = "/showGroupReportStatus", method = {RequestMethod.GET}, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String showGroupReportStatus(int stuClass, int stuGroup) {
		return JSON.toJSONString(studentService.showGroupReportStatus(stuClass, stuGroup, CommonUtil.getThisWeekNum()));
	}

	/**
	 * 组员周报提交列表
	 *
	 * @param stuClass
	 * @param stuGroup
	 * @return
	 */
	@RequestMapping(value = "/showGroupReportList", method = {RequestMethod.POST}, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String showGroupReportList(int stuClass, int stuGroup) {
		//获取列表
		List list = studentService.showReportList(stuClass, stuGroup, CommonUtil.getThisWeekNum());
		//layui数据表格JSON转换
		JSONDate jsonDate = new JSONDate(list, list.size());
		return jsonDate.toString();
	}

	/**
	 * 通过REPID显示周报信息
	 *
	 * @param repId
	 * @return
	 */
	@RequestMapping(value = "/showReportByREPID", method = {RequestMethod.POST}, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String showReportByREPID(int repId) {
		System.out.println(repId);
		return JSON.toJSONString(studentService.showReportByREPID(repId));
	}


	/**
	 * 通过REPID显示周报信息
	 *
	 * @param repId
	 * @return
	 */
	@RequestMapping(value = "/alterReportedByREPID", method = {RequestMethod.POST}, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String alterReportedByREPID(int repId, String repContext) {
		System.out.println(repId);
		if (studentService.alterReportedByREPID(repId, repContext)) {
			return JSON.toJSONString("修改周报成功");
		}
		return JSON.toJSONString("修改周报失败");
	}


	/**
	 * 检测有没有提交
	 *
	 * @param classNum
	 * @param groupNum
	 * @return
	 */
	@RequestMapping(value = "/isSubmit", method = {RequestMethod.GET}, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String isSubmit(int classNum, int groupNum) {
		String s = CommonUtil.isSubmit(classNum, groupNum);
		if (s != null) {
			return JSON.toJSONString(s);
		} else {
			return JSON.toJSONString(false);
		}
	}

	/**
	 * 信息关联确认
	 *
	 * @param connectName
	 * @param connectPass
	 * @return
	 */
	@RequestMapping(value = "/connectionInfoCheck", method = {RequestMethod.POST}, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String connectionInfo(String connectName, int connectPass) {
		Student student = studentService.studentLogin(connectName, connectPass);
		System.out.println(student);
		if (student != null) {
			if (student.getUserId() != 1) {
				return JSON.toJSONString(student);
				//			System.out.println(student.getStuId());
//			System.out.println(userService.connectionInfo(student.getStuId(),userId));
			} else {
				return JSON.toJSONString("该用户已经被关联");
			}
		} else {
			return JSON.toJSONString("不存在此用户或者关联信息错误！！");
		}
	}





	@RequestMapping(value = "/addSuggest", method = {RequestMethod.POST}, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String addSuggest(int stuClass, String stuName, String suContent) {
		if (suContent.length() == 0 ){
			return JSON.toJSONString("内容为空不能提交！！");
		}else {
			Suggest suggest = new Suggest();
			suggest.setStuClass(stuClass);
			suggest.setStuName(stuName);
			suggest.setSuContent(suContent);
			if (studentService.addSuggest(suggest) >  0){
				return JSON.toJSONString("您的建议提交成功！感谢您的建议！！");
			}else {
				return JSON.toJSONString("提交失败！！");
			}
		}
	}


	/**
	 * 上传文件
	 *
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/upload", method = {RequestMethod.POST}, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String upLoad(@RequestParam("file") MultipartFile file) throws Exception {
		String fileName = file.getOriginalFilename();
		String[] fileAnalyze = fileName.split("_");
		String classNum = fileAnalyze[0].replace("班", "");
		System.out.println(classNum);
		//文件夹判断
		File fileDir = new File("D:" + File.separator + "xtSoftware" + File.separator + "HOMEWORK" + File.separator + classNum + File.separator + CommonUtil.getNowTime().split(" ")[0]);
		//文件夹如果不存在则创建
		if (!fileDir.exists()) {
			System.out.println(fileDir.mkdirs());
		}
		if (!(new File(fileDir + File.separator + fileName)).exists()) {
			InputStream inputStream = file.getInputStream();
			OutputStream outputStream = new FileOutputStream(fileDir + File.separator + fileName);
			byte[] bytes = new byte[1024];
			while ((inputStream.read(bytes) != -1)) {
				outputStream.write(bytes);
			}
			inputStream.close();
			outputStream.close();
			return "{\"code\": 0,\"msg\": \"上传成功\",\"data\": {\"src\": \"1472\"}}";
		} else {
			return "{\"code\": 0,\"msg\": \"已经上传过\",\"data\": {\"src\": \"1472\"}}";
		}
	}

	/**--------------------------------------------页面跳转----------------------------------------------**/
	//页面跳转

	/**
	 * 跳转页面到report-status
	 * <p>
	 * //	 * @param stuClass
	 * //	 * @param stuGroup
	 *
	 * @return
	 */
	@RequestMapping(value = "/reportStatus", method = {RequestMethod.GET}, produces = "text/html;charset=UTF-8")
	public ModelAndView reportStatus() {
		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.addObject("stuClass", stuClass);
//		modelAndView.addObject("stuGroup", stuGroup);
		modelAndView.setViewName("week/report-status");
		return modelAndView;
	}

	/**
	 * 跳转页面到welcome
	 * <p>
	 * //	 * @param stuClass
	 * //	 * @param stuGroup
	 *
	 * @return
	 */
	@RequestMapping(value = "/reportWelcome", method = {RequestMethod.GET}, produces = "text/html;charset=UTF-8")
	public ModelAndView reportWelcome() {
		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.addObject("stuClass", stuClass);
//		modelAndView.addObject("stuGroup", stuGroup);
//		modelAndView.addObject("stuName", stuName);
		modelAndView.setViewName("week/welcome");
		return modelAndView;
	}

	/**
	 * 跳转页面到groupreport-list
	 * <p>
	 * //	 * @param stuClass
	 * //	 * @param stuGroup
	 *
	 * @return
	 */
	@RequestMapping(value = "/groupReportList", method = {RequestMethod.GET}, produces = "text/html;charset=UTF-8")
	public ModelAndView groupReportList() {
		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.addObject("stuClass", stuClass);
//		modelAndView.addObject("stuGroup", stuGroup);
		modelAndView.setViewName("week/groupreport-list");
		return modelAndView;
	}

	@RequestMapping(value = "/weekIndex", method = {RequestMethod.GET}, produces = "text/html;charset=UTF-8")
	public ModelAndView weekIndex() {
		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.addObject("stuClass", stuClass);
//		modelAndView.addObject("stuGroup", stuGroup);
		modelAndView.setViewName("week/index");
		return modelAndView;
	}


	/**
	 * 跳转页面到groupreport-list
	 * <p>
	 * //	 * @param stuClass
	 * //	 * @param stuSheet
	 *
	 * @return
	 */
	@RequestMapping(value = "/groupReportAdd", method = {RequestMethod.GET}, produces = "text/html;charset=UTF-8")
	public ModelAndView groupReportAdd() {
		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.addObject("stuClass", stuClass);
//		modelAndView.addObject("stuSheet", stuSheet);
		modelAndView.setViewName("week/groupreport-add");
		return modelAndView;
	}

	/**
	 * 跳转页面到report-look
	 *
	 * @return
	 */
	@RequestMapping(value = "/reportLook", method = {RequestMethod.GET}, produces = "text/html;charset=UTF-8")
	public ModelAndView reportLook() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("week/report-look");
		return modelAndView;
	}

	/**
	 * 跳转页面到report-edit
	 *
	 * @return
	 */
	@RequestMapping(value = "/reportEdit", method = {RequestMethod.GET}, produces = "text/html;charset=UTF-8")
	public ModelAndView reportEdit() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("week/report-edit");
		return modelAndView;
	}

	/**
	 * 跳转页面到report-edit
	 *
	 * @return
	 */
	@RequestMapping(value = "/reportAdd", method = {RequestMethod.GET}, produces = "text/html;charset=UTF-8")
	public ModelAndView reportAdd() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("student/report-add");
		return modelAndView;
	}

	/**
	 * 跳转页面到suggest
	 *
	 * @return
	 */
	@RequestMapping(value = "/suggest", method = {RequestMethod.GET}, produces = "text/html;charset=UTF-8")
	public ModelAndView suggest() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("student/suggest");
		return modelAndView;
	}

	/**
	 * 跳转页面到suggest
	 *
	 * @return
	 */
	@RequestMapping(value = "/checkInfo", method = {RequestMethod.GET}, produces = "text/html;charset=UTF-8")
	public ModelAndView checkInfo() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("student/connectionCheck");
		return modelAndView;
	}

	/**
	 * onespace
	 *
	 * @return
	 */
	@RequestMapping(value = "/oneSpace", method = {RequestMethod.GET}, produces = "text/html;charset=UTF-8")
	public ModelAndView oneSpace() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("student/onespace");
		return modelAndView;
	}

	/**
	 * homeWorkUpload
	 *
	 * @return
	 */
	@RequestMapping(value = "/homeWorkUpload", method = {RequestMethod.GET}, produces = "text/html;charset=UTF-8")
	public ModelAndView homeWorkUpload() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("week/homework-upload");
		return modelAndView;
	}

	/**
	 * homeWorkUpload
	 *
	 * @return
	 */
	@RequestMapping(value = "/exercise", method = {RequestMethod.GET}, produces = "text/html;charset=UTF-8")
	public ModelAndView exercise() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("student/exercise");
		return modelAndView;
	}

	@RequestMapping(value = "/exerciseCata", method = {RequestMethod.GET}, produces = "text/html;charset=UTF-8")
	public ModelAndView exerciseCata() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("student/exercisecata");
		return modelAndView;
	}

	/**
	 * 跳转页面到suggest
	 *
	 * @return
	 */
	@RequestMapping(value = "/toTndex", method = {RequestMethod.GET}, produces = "text/html;charset=UTF-8")
	public ModelAndView index(@ModelAttribute("info") Map map , HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("student/index");
		return modelAndView;
	}

}
