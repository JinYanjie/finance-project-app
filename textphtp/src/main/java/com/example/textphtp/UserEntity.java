package com.example.textphtp;

public class UserEntity extends BaseEntity {


    /**
     * id : 2
     * username : 小快乐
     * email : 3247824@qq.com
     * password : 123456
     * createTime : 2019-03-07T16:49:19.000+0000
     * level : 2
     * phone : 19977665533
     * company : 康策
     */

    private int id;
    private String username;
    private String email;
    private String password;
    private String createTime;
    private int level;
    private String phone;
    private String company;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
