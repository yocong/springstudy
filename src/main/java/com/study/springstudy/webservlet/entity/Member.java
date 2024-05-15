package com.study.springstudy.webservlet.entity;

import java.util.Objects;

public class Member {

    private String account;
    private String password;
    private String userName;

    public Member(String account, String password, String userName) {
        this.account = account;
        this.password = password;
        this.userName = userName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(account, member.account) && Objects.equals(password, member.password) && Objects.equals(userName, member.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, password, userName);
    }

    @Override
    public String toString() {
        return "Member{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
