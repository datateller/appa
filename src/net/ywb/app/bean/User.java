package net.ywb.app.bean;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = -7426302926562472180L;
	private Integer userid;
	private String username;
	private String password;
	private String babyname;
	private String birthday;
	private String sex;
	private String weight;
	private String height;
	private String city;
	private String homeaddr;
	private String schooladdr;

	private String account;
	private Status validate;
	private boolean isrememberMe;
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Status getValidate() {
		return validate;
	}

	public void setValidate(Status validate) {
		this.validate = validate;
	}

	public boolean isRememberMe() {
		return isrememberMe;
	}

	public void setRememberMe(boolean isrememberMe) {
		this.isrememberMe = isrememberMe;
	}

	public Integer getUserId() {
		return userid;
	}

	public void setUserId(Integer userId) {
		this.userid = userId;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String userName) {
		this.username = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [userId=" + userid + ", userName=" + username
				+ ", password=" + password + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userid == null) ? 0 : userid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userid == null) {
			if (other.userid != null)
				return false;
		} else if (!userid.equals(other.userid))
			return false;
		return true;
	}
}
