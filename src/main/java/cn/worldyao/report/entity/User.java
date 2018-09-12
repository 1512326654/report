package cn.worldyao.report.entity;

public class User {
	private int userId;
	private String userName;
	private String userPass;
	private String userQQ;
	private int user_stuId;

	private String BY1;
	private String BY2;
	private String BY3;
	private String BY4;

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

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	public String getUserQQ() {
		return userQQ;
	}

	public void setUserQQ(String userQQ) {
		this.userQQ = userQQ;
	}

	public int getUser_stuId() {
		return user_stuId;
	}

	public void setUser_stuId(int user_stuId) {
		this.user_stuId = user_stuId;
	}

	@Override
	public String toString() {
		return "User{" +
				"userId=" + userId +
				", userName='" + userName + '\'' +
				", userPass='" + userPass + '\'' +
				", userQQ='" + userQQ + '\'' +
				", user_stuId=" + user_stuId +
				'}';
	}
}
