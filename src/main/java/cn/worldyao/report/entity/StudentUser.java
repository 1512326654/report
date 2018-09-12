package cn.worldyao.report.entity;

public class StudentUser {
	private int userId;
	private String userName;
	private String stuName;
	private int stuClass;
	private int stuGroup;
	private int stuSheet;
	private String status;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

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

	public int getStuSheet() {
		return stuSheet;
	}

	public void setStuSheet(int stuSheet) {
		this.stuSheet = stuSheet;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "StudentUser{" +
				"userId=" + userId +
				", userName='" + userName + '\'' +
				", stuName='" + stuName + '\'' +
				", stuClass=" + stuClass +
				", stuGroup=" + stuGroup +
				", stuSheet=" + stuSheet +
				", status='" + status + '\'' +
				'}';
	}
}
