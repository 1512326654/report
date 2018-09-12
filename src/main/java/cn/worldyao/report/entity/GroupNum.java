package cn.worldyao.report.entity;

/**
 * @author 董尧
 */
public class GroupNum {
	private int stuClass;
	private int stuGroup;
	private int reportedNum;
	private int groupNum;

	public int getStuClass() {
		return stuClass;
	}

	public void setStuClass(int stuClass) {
		this.stuClass = stuClass;
	}

	public int getStuGroup() {
		return stuGroup;
	}

	public void setStuGroup(int stuGroup) {
		this.stuGroup = stuGroup;
	}

	public int getReportedNum() {
		return reportedNum;
	}

	public void setReportedNum(int reportedNum) {
		this.reportedNum = reportedNum;
	}

	public int getGroupNum() {
		return groupNum;
	}

	public void setGroupNum(int groupNum) {
		this.groupNum = groupNum;
	}

	@Override
	public String toString() {
		return "GroupNum{" +
				"stuClass=" + stuClass +
				", stuGroup=" + stuGroup +
				", reportedNum=" + reportedNum +
				", groupNum=" + groupNum +
				'}';
	}
}
