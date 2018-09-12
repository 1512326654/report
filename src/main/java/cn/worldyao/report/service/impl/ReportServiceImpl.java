package cn.worldyao.report.service.impl;

import cn.worldyao.report.dao.ReportDAO;
import cn.worldyao.report.dao.StudentDAO;
import cn.worldyao.report.entity.*;
import cn.worldyao.report.service.ReportService;
import cn.worldyao.report.util.CommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 董尧
 */
@Service
public class ReportServiceImpl implements ReportService {
	@Resource
	private ReportDAO reportDAO;
	@Resource
	private StudentDAO studentDAO;


	/**
	 * 添加周报接口实现方法
	 * @param stuClass
	 * @param stuSheet
	 * @param stuContext
	 * @return
	 */
	@RequestMapping("addReport")
	public int applyReport(int stuId, int stuClass, int stuSheet, String stuContext) {
		Map<String,Object> map = new HashMap<String, Object>(5);
		/**-----------------------------------**/
		map.put("stuId", stuId);
		map.put("repClass", stuClass);
		map.put("repSheet", stuSheet);
		map.put("repContext", stuContext);

		map.put("repWeek", CommonUtil.getThisWeekNum());
		map.put("repAddtime", CommonUtil.getNowTime());
		/**-----------------------------------**/
		return reportDAO.applyReport(map);
	}

	/**
	 * 学生显示周报
	 * @param stuClass
	 * @param stuWeek
	 * @param stuSheet
	 * @return
	 */
	@RequestMapping("selReport")
	public List<Report> selReport(int stuClass, int stuWeek , int stuSheet){
		Map<String,Object> map = new HashMap<String, Object>(3);
		/**-----------------------------------**/
		map.put("repClass", stuClass);
		map.put("repWeek", stuWeek);
		map.put("repSheet", stuSheet);
		/**-----------------------------------**/
		return reportDAO.selReport(map);
	}

	/**
	 * 学生修改周报
	 * @param repContext
	 * @param repClass
	 * @param repSheet
	 * @return
	 */
	@RequestMapping("studentAlter")
	public int studentAlter(int repClass, int repSheet, String repContext) {
		Map<String,Object> map = new HashMap<String, Object>(5);
		/**-----------------------------------**/
		map.put("repContext", repContext);
		map.put("repUpdate", CommonUtil.getNowTime());
		map.put("repClass", repClass);
		map.put("repSheet", repSheet);
		map.put("repWeek", CommonUtil.getThisWeekNum());
		/**-----------------------------------**/
		return reportDAO.alterReported(map);
	}

	/**
	 * 通过周报序号修改周报
	 * @param repId
	 * @param repContext
	 * @return
	 */
	public boolean alterReportedByREPID(int repId, String repContext) {
		Map<String,Object> map = new HashMap<String, Object>(5);
		/**-----------------------------------**/
		map.put("repId", repId);
		map.put("repContext", repContext);
		map.put("repUpdate", CommonUtil.getNowTime());
		/**-----------------------------------**/
		return (reportDAO.alterReportedByREPID(map) == 1);
	}

	/**
	 * 按班查询该班成员的周报提交状态
	 * @param stuClass
	 * @param stuGroup
	 * @param repWeek
	 * @return
	 */
	@RequestMapping("showGroupReportedStatus")
	public List<MemberReportStatus> showGroupReportedStatus(int stuClass, int stuGroup, int repWeek) {
		Map<String,Integer> map = new HashMap<String, Integer>(3);
		/**-----------------------------------**/
		map.put("stuClass", stuClass);
		map.put("stuGroup", stuGroup);
		map.put("repWeek", repWeek);
		List<MemberReportStatus> list = studentDAO.showMemberReportStatus(map);
		return list;
	}

	/**
	 * 查询组周报提交状态
	 * @param stuClass
	 * @param stuGroup
	 * @param repWeek
	 * @return
	 */
	@RequestMapping("showGroupReportStatus")
	public List<MemberReportStatus> showGroupReportStatus(int stuClass, int stuGroup, int repWeek) {
		Map<String,Integer> map = new HashMap<String, Integer>(3);
		/**-----------------------------------**/
		map.put("stuClass", stuClass);
		map.put("stuGroup", stuGroup);
		map.put("repWeek", repWeek);
		List<MemberReportStatus> list = reportDAO.showGroupReportStatus(map);
		for (MemberReportStatus memberReportStatus : list){
			System.out.println(memberReportStatus);
		}
		return list;
	}

	/**
	 * 周报提交列表(包含分页)
	 * @param stuClass
	 * @param stuGroup
	 * @param repWeek
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<ReportInfo> showReportList(int stuClass, int stuGroup, int repWeek, int pageNum, int pageSize) {
		Map<String,Integer> map = new HashMap<String, Integer>(5);
		/**-----------------------------------**/
		map.put("stuClass", stuClass);
		map.put("stuGroup", stuGroup);
		map.put("repWeek", repWeek);
		map.put("pageNum", pageNum);
		map.put("pageSize", pageSize);
		List<ReportInfo> list = reportDAO.showReportList(map);
		return list;
	}

	/**
	 * 周报显示
	 * @param repId
	 * @return
	 */
	public String showReportByREPID(int repId) {
		return reportDAO.showReportByREPID(repId);
	}

	/**
	 * 显示各组数据统计
	 * @param repClass
	 * @param repWeek
	 * @return
	 */
	public List<GroupNum> eachClassGroupReportedNum(int repClass, int repWeek) {
		Map<String,Integer> map = new HashMap<String, Integer>(3);
		/**-----------------------------------**/
		map.put("repClass", repClass);
		map.put("repWeek", repWeek);
		return reportDAO.eachClassGroupReportedNum(map);
	}

	/**
	 * 时间线
	 * @param repClass
	 * @param repSheet
	 * @return
	 */
	public List<ReportBase> timeLine(int repClass, int repSheet) {
		Map<String,Integer> map = new HashMap<String, Integer>(2);
		/**-----------------------------------**/
		map.put("repClass", repClass);
		map.put("repSheet", repSheet);
		/**-----------------------------------**/
		return reportDAO.timeLine(map);
	}

	/**
	 * 查询本的周报列表
	 * @param repClass
	 * @param repWeek
	 * @return
	 */
	public List<Report> selReportByThisWeek(int repClass, int repWeek) {
		Map<String,Integer> map = new HashMap<String, Integer>(2);
		/**-----------------------------------**/
		map.put("repClass", repClass);
		map.put("repWeek", repWeek);
		/**-----------------------------------**/
		return reportDAO.selReportByThisWeek(map);
	}

	/**
	 * 修改周报对应的信息
	 * @param repClass
	 * @param repSheet
	 * @param stuId
	 * @return
	 */
	public int alterStudentInfo(int repClass , int repSheet, int stuId){
		Map<String,Integer> map = new HashMap<String, Integer>(3);
		/**-----------------------------------**/
		map.put("repClass", repClass);
		map.put("repSheet", repSheet);
		map.put("stuId", stuId);
		/**-----------------------------------**/
		return reportDAO.alterStudentInfo(map);
	}
}
