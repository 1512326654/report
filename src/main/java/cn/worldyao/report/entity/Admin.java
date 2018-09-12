package cn.worldyao.report.entity;

public class Admin {
	private int adId;
	private String adUsername;
	private String adName;
	private String adPassword;
	private int adRole;

	private String BY1;
	private String BY2;
	private String BY3;
	private String BY4;

	public String getAdUsername() {
		return adUsername;
	}

	public void setAdUsername(String adUsername) {
		this.adUsername = adUsername;
	}

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	public String getAdPassword() {
		return adPassword;
	}

	public void setAdPassword(String adPassword) {
		this.adPassword = adPassword;
	}

	public int getAdRole() {
		return adRole;
	}

	public void setAdRole(int adRole) {
		this.adRole = adRole;
	}

	@Override
	public String toString() {
		return "Admin{" +
				"adUsername='" + adUsername + '\'' +
				", adName='" + adName + '\'' +
				", adPassword='" + adPassword + '\'' +
				", adRole=" + adRole +
				'}';
	}
}
