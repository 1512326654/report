package cn.worldyao.report.entity;

/**
 * @author 董尧
 */
public class Report{
	private int repId;
	private int repClass;
	private int repSheet;
	private int repWeek;
	private String repContext;
	private String repAddTime;
	private String repUpdate;

	public int getRepId() {
		return repId;
	}

	public void setRepId(int repId) {
		this.repId = repId;
	}

	public int getRepClass() {
		return repClass;
	}

	public void setRepClass(int repClass) {
		this.repClass = repClass;
	}

	public int getRepSheet() {
		return repSheet;
	}

	public void setRepSheet(int repSheet) {
		this.repSheet = repSheet;
	}

	public int getRepWeek() {
		return repWeek;
	}

	public void setRepWeek(int repWeek) {
		this.repWeek = repWeek;
	}

	public String getRepContext() {
		return repContext;
	}

	public void setRepContext(String repContext) {
		this.repContext = repContext;
	}

	public String getRepAddTime() {
		return repAddTime;
	}

	public void setRepAddTime(String repAddTime) {
		this.repAddTime = repAddTime;
	}

	public String getRepUpdate() {
		return repUpdate;
	}

	public void setRepUpdate(String repUpdate) {
		this.repUpdate = repUpdate;
	}

	@Override
	public String toString() {
		return "Report{" +
				"repId=" + repId +
				", repClass=" + repClass +
				", repSheet=" + repSheet +
				", repWeek=" + repWeek +
				", repContext='" + repContext + '\'' +
				", repAddTime='" + repAddTime + '\'' +
				", repUpdate='" + repUpdate + '\'' +
				'}';
	}
}
