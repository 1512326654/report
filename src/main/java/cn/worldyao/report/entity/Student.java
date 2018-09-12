package cn.worldyao.report.entity;

/**
 * @author 董尧
 */
public class Student {
	private int count;
	private int stuId;
	private String stuName;
	private int stuClass;
	private int stuGroup;
	private int stuSheet;
	private int stuLeader;
	private String leader;
	private int userId;

	private String by2;
	private String by3;
	private String by4;

	public int getStuId() {
		return stuId;
	}

	public void setStuId(int stuId) {
		this.stuId = stuId;
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

	public int getStuLeader() {
		return stuLeader;
	}

	public void setStuLeader(int stuLeader) {
		this.stuLeader = stuLeader;
	}

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Student{" +
				"stuId=" + stuId +
				", stuName='" + stuName + '\'' +
				", stuClass=" + stuClass +
				", stuGroup=" + stuGroup +
				", stuSheet=" + stuSheet +
				", stuLeader=" + stuLeader +
				", leader='" + leader + '\'' +
				", userId=" + userId +
				'}';
	}
}
