package cn.worldyao.report.controller;

import cn.worldyao.report.core.Core;
import cn.worldyao.report.entity.*;
import cn.worldyao.report.service.AdminService;
import cn.worldyao.report.service.CoreService;
import cn.worldyao.report.service.StudentService;
import cn.worldyao.report.service.UserService;
import cn.worldyao.report.service.impl.CoreServiceImpl;
import cn.worldyao.report.util.CommonUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 董尧
 */
@Controller
@RequestMapping("admin")
public class AdminController {

	@Autowired
	private AdminService adminServiceImpl;
	@Resource
	private CoreServiceImpl coreServiceImpl;
	@Resource
	private StudentService studentService;
	@Resource
	private CoreService coreService;
	@Resource
	private UserService userService;

	/**
	 * 管理员登陆
	 * @param adUsername
	 * @param adPassword
	 * @return
	 */
	@RequestMapping(value = "/login" , method = {RequestMethod.POST})
	public ModelAndView studentLogin(String adUsername, String adPassword){
		Admin admin = adminServiceImpl.adminLogin(adUsername, adPassword);
		if (admin != null){
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("admin",admin);
			modelAndView.setViewName("admin/index");
			System.out.println("-------------15" + modelAndView);
			return modelAndView;
		}
		return null;
	}

	/**
	 * 显示各组数据统计
	 * @param repClass
	 * @return
	 */
	@RequestMapping(value = "/eachClassGroupReportedNum" , method = {RequestMethod.POST} , produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String eachClassGroupReportedNum(int repClass){
		System.out.println(repClass);
		List<GroupNum> list = adminServiceImpl.eachClassGroupReportedNum(repClass, CommonUtil.getThisWeekNum());
		for (GroupNum groupNum : list){
			System.out.println(groupNum);
		}
		return JSON.toJSONString(list);
	}


	/**
	 * 修改学生信息
	 * @param student
	 * @return
	 */
	@RequestMapping(value = "/editStudentInfo" , method = {RequestMethod.POST}, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String editStudentInfo(@RequestBody Student student){
		if (studentService.alterStudentInfo(student) == 1){
			return "{\"msg\":\"修改成功\",\"flag\":1}";
		}else {
			return "{\"msg\":\"修改失败\",\"flag\":0}";
		}
	}

	/**
	 *
	 * 获取组长周报的编号
	 * @return
	 */
	@RequestMapping(value = "/getLeaderSheetNum" , method = {RequestMethod.POST})
	@ResponseBody
	public String getLeaderSheetNum(){
		Map map = coreService.getLeaderSheetNum();
		System.out.println(JSON.toJSONString(map));

		return JSON.toJSONString(map);
	}

	/**
	 *
	 * 修改组长周报的编号
	 * @return
	 */
	@RequestMapping(value = "/setLeaderSheetNum" , method = {RequestMethod.POST}, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String setLeaderSheetNum(@RequestBody String group){
		Map map = JSON.parseObject(group);
		return JSON.toJSONString(coreService.setLeaderSheetNum(map.get("classOne").toString(), map.get("classTwo").toString()));
	}


	/**
	 * 获取学生列表
	 * @param stuClass
	 * @param stuGroup
	 * @return
	 */
	@RequestMapping(value = "/getStudentList" , method = {RequestMethod.POST} , produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getStudentList(int stuClass, int stuGroup, int limitName, int pageName){
		System.out.println(limitName);
		System.out.println(pageName);
		List<Student> list = adminServiceImpl.getStudentList(stuClass, stuGroup,limitName,pageName);
		JSONDate jsonDate = new JSONDate(list, adminServiceImpl.getStudentListNum(stuClass, stuGroup));
		for (Student groupNum : list){
			System.out.println(groupNum);
		}
		return jsonDate.toString();
	}

	/**
	 * 组员周报提交列表
	 * @param stuClass
	 * @param stuGroup
	 * @return
	 */
	@RequestMapping(value = "/showGroupReportList" , method = {RequestMethod.POST} , produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String showGroupReportList(int stuClass, int stuGroup, int pageName, int limitName){
		JSONDate jsonDate = new JSONDate(adminServiceImpl.showReportList(stuClass, stuGroup, CommonUtil.getThisWeekNum(), pageName, limitName) ,adminServiceImpl.getReportListNum(stuClass, stuGroup, CommonUtil.getThisWeekNum()));
		return jsonDate.toString();
	}


	/**
	 * 组周报的提交状态
	 *
	 * @param stuClass
	 * @return
	 */
	@RequestMapping(value = "/showGroupReportStatus", method = {RequestMethod.GET}, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String showGroupReportStatus(int stuClass) {
		return JSON.toJSONString(studentService.showGroupReportStatus(stuClass, 0, CommonUtil.getThisWeekNum()));
	}


	/**
	 * 核心方法转换成Excel
	 * @param stuClass
	 * @return
	 */
	@RequestMapping(value = "/writeToExcel" , method = {RequestMethod.POST} , produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String writeToExcel(int stuClass){
		System.out.println(stuClass);
		System.out.println(coreServiceImpl.writeToExcel(stuClass,CommonUtil.getThisWeekNum()));
		return JSON.toJSONString("ces");
	}


	/**
	 *
	 * @return
	 */
	@RequestMapping(value = "/showAssociationStauts" , method = {RequestMethod.GET} , produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String showAssociationStauts(){
		List<StudentUser> list = userService.showAssociationStauts();
		return new JSONDate(list,(long)list.size()).toString();
	}



	@RequestMapping(value = "/Download" , method = {RequestMethod.GET} , produces = "text/html;charset=UTF-8")
	public ResponseEntity<byte[]> export(int stuClass) throws IOException {
		String fileName = null;
		if (stuClass == 1){
			fileName = "1e.xlsx";
		}
		if (stuClass == 2){
			fileName = "2e.xlsx";
		}
		String filePath = Core.class.getClassLoader().getResource(fileName).getPath();;
		System.out.println(filePath);

		HttpHeaders headers = new HttpHeaders();
		File file = new File(filePath);

		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", fileName);

		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
				headers, HttpStatus.CREATED);
	}


	/**------------------------------------------------------------页面跳转------------------------------------------------------------------**/

	/**
	 * 跳转页面到welcome
	 * @return
	 */
	@RequestMapping(value = "/welcome" , method = {RequestMethod.GET} , produces = "text/html;charset=UTF-8")
	public ModelAndView adminWelcome(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/welcome");
		return modelAndView;
	}

	/**
	 * 跳转页面到report-list
	 * @return
	 */
	@RequestMapping(value = "/reportList" , method = {RequestMethod.GET} , produces = "text/html;charset=UTF-8")
	public ModelAndView reportList(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/report-list");
		return modelAndView;
	}

	/**
	 * 跳转页面到report-borad
	 * @return
	 */
	@RequestMapping(value = "/reportBorad" , method = {RequestMethod.GET} , produces = "text/html;charset=UTF-8")
	public ModelAndView reportBorad(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/report-borad");
		return modelAndView;
	}

	/**
	 * 跳转页面到report-download
	 * @return
	 */
	@RequestMapping(value = "/reportDownload" , method = {RequestMethod.GET} , produces = "text/html;charset=UTF-8")
	public ModelAndView reportDownload(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/report-download");
		return modelAndView;
	}

	/**
	 * 跳转页面到student-list
	 * @return
	 */
	@RequestMapping(value = "/studentList" , method = {RequestMethod.GET} , produces = "text/html;charset=UTF-8")
	public ModelAndView studentList(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/student-list");
		return modelAndView;
	}

	/**
	 * 跳转页面到student-edit
	 * 该个页面是用来修改学生信息
	 * @return
	 */
	@RequestMapping(value = "/studentEdit" , method = {RequestMethod.GET} , produces = "text/html;charset=UTF-8")
	public ModelAndView studentEdit(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/student-edit");
		return modelAndView;
	}
	/**
	 * 跳转页面到homework-list
	 * @return
	 */
	@RequestMapping(value = "/homeworkList" , method = {RequestMethod.GET} , produces = "text/html;charset=UTF-8")
	public ModelAndView homeworkList(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/homework-list");
		return modelAndView;
	}

	/**
	 * 跳转页面到homework-list
	 * @return
	 */
	@RequestMapping(value = "/homeworkAdd" , method = {RequestMethod.GET} , produces = "text/html;charset=UTF-8")
	public ModelAndView homeworkAdd(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/homework-add");
		return modelAndView;
	}

	/**
	 * 跳转页面到homework-list
	 * @return
	 */
	@RequestMapping(value = "/constautsList" , method = {RequestMethod.GET} , produces = "text/html;charset=UTF-8")
	public ModelAndView constautsList(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/constauts-list");
		return modelAndView;
	}


	@RequestMapping(value = "/userList", method = {RequestMethod.GET}, produces = "text/html;charset=UTF-8")
	public ModelAndView userList() {
		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.addObject("stuClass", stuClass);
//		modelAndView.addObject("stuGroup", stuGroup);
		modelAndView.setViewName("admin/user-list");
		return modelAndView;
	}
}
