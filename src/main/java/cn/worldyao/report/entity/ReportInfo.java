package cn.worldyao.report.entity;

/**
 * 不含周报内容的实体类
 * @author 董尧
 */
public class ReportInfo extends ReportBase{
	private int repId;
	private String stuName;
	private int repClass;
	private int stuGroup;
	private int repWeek;
	private String repAddTime;
	private String repUpdate;

	@Override
	public int getRepId() {
		return repId;
	}

	@Override
	public void setRepId(int repId) {
		this.repId = repId;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	@Override
	public int getRepClass() {
		return repClass;
	}

	@Override
	public void setRepClass(int repClass) {
		this.repClass = repClass;
	}

	public int getStuGroup() {
		return stuGroup;
	}

	public void setStuGroup(int stuGroup) {
		this.stuGroup = stuGroup;
	}

	@Override
	public int getRepWeek() {
		return repWeek;
	}

	@Override
	public void setRepWeek(int repWeek) {
		this.repWeek = repWeek;
	}

	@Override
	public String getRepAddTime() {
		return repAddTime;
	}

	@Override
	public void setRepAddTime(String repAddTime) {
		this.repAddTime = repAddTime;
	}

	@Override
	public String getRepUpdate() {
		return repUpdate;
	}

	@Override
	public void setRepUpdate(String repUpdate) {
		this.repUpdate = repUpdate;
	}

	@Override
	public String toString() {
		return "ReportInfo{" +
				"repId=" + repId +
				", stuName='" + stuName + '\'' +
				", repClass=" + repClass +
				", stuGroup=" + stuGroup +
				", repWeek=" + repWeek +
				", repAddTime='" + repAddTime + '\'' +
				", repUpdate='" + repUpdate + '\'' +
				'}';
	}
}
