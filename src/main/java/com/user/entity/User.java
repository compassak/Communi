package com.user.entity;

public class User {
    private Integer id;
    private String userName;
    private String password;
	private String iconPath;
    private String mailAddress;
    private String telephoneNumber;
	//激活状态
    private Integer state=0;
	//激活码
    private String code;

    public User() {
		super();
		// TODO Auto-generated constructor stub
	}
   
	public User(String userName, String password, String iconPath, String mailAddress, String telephoneNumber, Integer state,
			String code) {
		super();
		this.userName = userName;
		this.password = password;
		this.iconPath = iconPath;
		this.mailAddress = mailAddress;
		this.telephoneNumber = telephoneNumber;
		this.state = state;
		this.code = code;
	}

    public User(Integer id, String userName, String password, String iconPath, String mailAddress, String telephoneNumber, Integer state,
			String code) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.iconPath = iconPath;
		this.mailAddress = mailAddress;
		this.telephoneNumber = telephoneNumber;
		this.state = state;
		this.code = code;
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIconPath() {
		return iconPath;
	}
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getTelephoneNumber() {
		return telephoneNumber;
	}
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", mailAddress=" + mailAddress
				+ ", telephoneNumber=" + telephoneNumber + ", state=" + state + ", code=" + code + "]";
	}
}