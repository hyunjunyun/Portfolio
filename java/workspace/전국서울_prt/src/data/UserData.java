package data;

public class UserData {

	private String userName;
	private String userPhone;
	private int userNo;
	private int userNotice;
	private boolean loginCheck;

	public void setUName(String userName) {
		this.userName = userName;
	}

	public String getUName() {
		return userName;
	}

	public void setCheck(boolean loginCheck) {
		this.loginCheck = loginCheck;
	}

	public boolean getCheck() {
		return loginCheck;
	}
	
	public void setPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	
	public String getPhone() {
		return userPhone;
	}
	
	public void setNo(int userNo) {
		this.userNo = userNo;
	}
	
	public int getNo() {
		return userNo;
	}
	
	public void setNotice(int userNotice) {
		this.userNotice = userNotice;
	}
	
	public int getNotice() {
		return userNotice;
	}
	
}