package cn.worldyao.report.entity;

public class Catalog {
	private int exCataId;
	private String exCataName;

	public int getExCataId() {
		return exCataId;
	}

	public void setExCataId(int exCataId) {
		this.exCataId = exCataId;
	}

	public String getExCataName() {
		return exCataName;
	}

	public void setExCataName(String exCataName) {
		this.exCataName = exCataName;
	}

	@Override
	public String toString() {
		return "Catalog{" +
				"exCataId=" + exCataId +
				", exCataName='" + exCataName + '\'' +
				'}';
	}
}
