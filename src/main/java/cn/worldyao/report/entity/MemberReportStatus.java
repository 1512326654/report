package cn.worldyao.report.entity;

/**
 * @author 董尧
 */
public class MemberReportStatus {
	private String stuName;
	private String status;

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "{" +
				"\"stuName\":\"" + stuName + '\"' +
				", \"status\":\"" + status + '\"' +
				'}';
	}
}
