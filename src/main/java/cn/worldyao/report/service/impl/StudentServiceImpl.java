package cn.worldyao.report.service.impl;

import cn.worldyao.report.dao.StudentDAO;
import cn.worldyao.report.dao.SuggestDAO;
import cn.worldyao.report.entity.*;
import cn.worldyao.report.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
//import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 董尧
 */
@Service
public class StudentServiceImpl implements StudentService {
	@Resource
	private StudentDAO iStudentDAO;
	@Resource
	private ReportServiceImpl reportService;
	@Resource
	private SuggestDAO suggestDAO;
	@Autowired
	private DataSourceTransactionManager transactionManager;

	/**
	 * 学生登录
	 * @param stuName
	 * @param stuSheet
	 * @return
	 */
	@RequestMapping("/studentLogin")
	public Student studentLogin(String stuName, int stuSheet) {
		Map<String,Object> map = new HashMap<String, Object>(2);
		map.put("stuName",stuName);
		map.put("stuSheet",stuSheet);
		return iStudentDAO.studentLogin(map);
	}

	/**
	 * 添加周报
	 * @param stuClass
	 * @param stuSheet
	 * @param stuContext
	 * @return
	 */
	@RequestMapping("/studentAddReport")
	public boolean applyReport(int stuId, int stuClass, int stuSheet, String stuContext) {
		return (reportService.applyReport(stuId, stuClass, stuSheet, stuContext) == 1);
	}

	/**
	 * 显示当周周报内容
	 * @param stuClass
	 * @param stuSheet
	 * @param stuWeek
	 * @return
	 */
	public String showThisWeekReport(int stuClass, int stuWeek , int stuSheet){
		List<Report> list = reportService.selReport(stuClass, stuWeek, stuSheet);
		if (list.size() != 0){
			return list.get(0).getRepContext();
		}
		return null;
	}

	/**
	 * 该周是否提交过周报
	 * @param stuClass
	 * @param stuWeek
	 * @param stuSheet
	 * @return
	 */
	public boolean studentThisReported(int stuClass, int stuWeek , int stuSheet){
		return (reportService.selReport(stuClass, stuWeek, stuSheet).size() == 0);
	}

	/**
	 * 修改周报
	 * @return
	 */
	public boolean studentAlter(int repClass, int repSheet ,String repContext) {
		return (reportService.studentAlter(repClass, repSheet, repContext) == 1);
	}

	/**
	 * 本组组长查询本组本周组员周报提交状态
	 * @param stuClass
	 * @param repWeek
	 * @param stuGroup
	 * @return
	 */
	public List<MemberReportStatus> showMemberReportStatus(int stuClass, int stuGroup, int repWeek) {
		Map<String,Integer> map = new HashMap<String, Integer>(3);
		map.put("stuClass",stuClass);
		map.put("stuGroup",stuGroup);
		map.put("repWeek",repWeek);
		return iStudentDAO.showMemberReportStatus(map);
	}

	/**
	 * 本组本周数据统计
	 * @param stuClass
	 * @param stuGroup
	 * @param repWeek
	 * @return
	 */
	public GroupReported showGroupDataStatistics(int stuClass, int stuGroup, int repWeek) {
		Map<String,Integer> map = new HashMap<String, Integer>(3);
		map.put("stuClass",stuClass);
		map.put("stuGroup",stuGroup);
		map.put("repWeek",repWeek);
		return iStudentDAO.showGroupDataStatistics(map);
	}

	/**
	 *
	 * @param stuClass
	 * @param stuGroup
	 * @return
	 */
	public List<MemberReportStatus> showGroupReportStatus(int stuClass, int stuGroup, int repWeek) {
		Map<String,Integer> map = new HashMap<String, Integer>(3);
		map.put("stuClass",stuClass);
		map.put("stuGroup",stuGroup);
		map.put("repWeek",repWeek);
		return reportService.showGroupReportStatus(stuClass, stuGroup, repWeek);
	}

	/**
	 * 周报提交列表
	 * @param stuClass
	 * @param stuGroup
	 * @param repWeek
	 * @return
	 */
	public List<ReportInfo> showReportList(int stuClass, int stuGroup, int repWeek) {
		return reportService.showReportList(stuClass, stuGroup, repWeek,0,0);
	}

	/**
	 * 周报显示
	 * @param repId
	 * @return
	 */
	public String showReportByREPID(int repId) {
		return reportService.showReportByREPID(repId);
	}


	/**
	 * 通过周报序号修改周报
	 * @param repId
	 * @param repContext
	 * @return
	 */
	public boolean alterReportedByREPID(int repId, String repContext) {
		return reportService.alterReportedByREPID(repId, repContext);
	}


	/**
	 * 信息关联
	 * @param map
	 * @return
	 */
	public int stuConnectionInfo(Map<String,Integer> map) {
		return iStudentDAO.stuConnectionInfo(map);
	}

	/**
	 * 时间线
	 * @param repClass
	 * @param repSheet
	 * @return
	 */
	public List<ReportBase> timeLine(int repClass, int repSheet) {
		return reportService.timeLine(repClass, repSheet);
	}

	/**
	 * 提交意见
	 * @param suggest
	 * @return
	 */
	public int addSuggest(Suggest suggest) {
		return suggestDAO.addSuggest(suggest);
	}

	/**
	 * 修改学生信息，不包括姓名
	 * @param student
	 * @return
	 */
//	@Transactional
	public int alterStudentInfo(Student student){
		DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
		defaultTransactionDefinition.setName("connection");
		defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(defaultTransactionDefinition);
		try {
			reportService.alterStudentInfo(student.getStuClass() , student.getStuSheet(), student.getStuId());
			iStudentDAO.alterStudentInfo(student);
		}catch (Exception e) {
			transactionManager.rollback(status);
			return 0;
		}
		return 1;
	}

}