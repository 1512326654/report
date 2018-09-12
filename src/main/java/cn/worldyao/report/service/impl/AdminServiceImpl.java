package cn.worldyao.report.service.impl;

import cn.worldyao.report.dao.AdminDAO;
import cn.worldyao.report.dao.ReportDAO;
import cn.worldyao.report.dao.StudentDAO;
import cn.worldyao.report.entity.Admin;
import cn.worldyao.report.entity.GroupNum;
import cn.worldyao.report.entity.ReportInfo;
import cn.worldyao.report.entity.Student;
import cn.worldyao.report.service.AdminService;
import cn.worldyao.report.service.ReportService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 董尧
 */

@Service
public class AdminServiceImpl implements AdminService {

	@Resource
	private AdminDAO adminDAO;
	@Resource
	private ReportService reportService;
	@Resource
	private StudentDAO studentDAO;
	@Resource
	private ReportDAO reportDAO;

	/**
	 * 登录
	 * @param adUsername
	 * @param adPassword
	 * @return
	 */
	public Admin adminLogin(String adUsername, String adPassword) {
		Map<String,Object> map = new HashMap<String, Object>(2);
		map.put("adUsername",adUsername);
		map.put("adPassword",adPassword);
		return adminDAO.adminLogin(map);
	}

	/**
	 * 显示各组数据统计
	 * @param repClass
	 * @param repWeek
	 * @return
	 */
	public List<GroupNum> eachClassGroupReportedNum(int repClass, int repWeek) {
		return reportService.eachClassGroupReportedNum(repClass, repWeek);
	}

	/**
	 * 获取学生信息列表
	 * @param stuClass
	 * @param stuGroup
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	public List<Student> getStudentList(int stuClass, int stuGroup, int pageSize, int pageNum) {
		Map<String,Integer> map = new HashMap<String, Integer>(4);
		map.put("stuClass",stuClass);
		map.put("stuGroup",stuGroup);
		map.put("pageSize",pageSize);
		map.put("pageNum",pageNum);
		return studentDAO.getStudentList(map);
	}

	/**
	 * 获取学生信息总数（分页功能使用）
	 * @param stuClass
	 * @param stuGroup
	 * @return
	 */
	public int getStudentListNum(int stuClass, int stuGroup) {
		Map<String,Integer> map = new HashMap<String, Integer>(2);
		map.put("stuClass",stuClass);
		map.put("stuGroup",stuGroup);
		return studentDAO.getStudentListNum(map);
	}

	/**
	 * 获取周报总数（分页功能使用）
	 * @param stuClass
	 * @param stuGroup
	 * @param repWeek
	 * @return
	 */
	public int getReportListNum(int stuClass, int stuGroup, int repWeek){
		Map<String,Integer> map = new HashMap<String, Integer>(3);
		map.put("stuClass",stuClass);
		map.put("stuGroup",stuGroup);
		map.put("repWeek",repWeek);
		return reportDAO.getReportListNum(map);
	}

	/**
	 * 周报提交列表
	 * @param stuClass
	 * @param stuGroup
	 * @param repWeek
	 * @return
	 */
	public List<ReportInfo> showReportList(int stuClass, int stuGroup, int repWeek, int pageSize, int pageNum) {
		return reportService.showReportList(stuClass, stuGroup, repWeek,pageSize,pageNum);
	}
}

