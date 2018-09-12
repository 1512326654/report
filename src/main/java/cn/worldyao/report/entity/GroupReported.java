package cn.worldyao.report.entity;

/**
 * @author 董尧
 */
public class GroupReported {
	private int reportednum;
	private int num;

	public int getReportednum() {
		return reportednum;
	}

	public void setReportednum(int reportednum) {
		this.reportednum = reportednum;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	@Override
	public String toString() {
		return "GroupReported{" +
				"reportednum=" + reportednum +
				", num=" + num +
				'}';
	}
}
