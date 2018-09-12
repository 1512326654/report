package cn.worldyao.report.entity;


/**
 * 周报基础属性
 * @author 董尧
 */
//@MappedSuperclass
public class ReportBase {
	private int repId;
	private int repClass;
	private int repWeek;
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

	public int getRepWeek() {
		return repWeek;
	}

	public void setRepWeek(int repWeek) {
		this.repWeek = repWeek;
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
		return "ReportBase{" +
				"repId=" + repId +
				", repClass=" + repClass +
				", repWeek=" + repWeek +
				", repAddTime='" + repAddTime + '\'' +
				", repUpdate='" + repUpdate + '\'' +
				'}';
	}
}
