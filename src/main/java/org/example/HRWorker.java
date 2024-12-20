package org.example;

public class HRWorker {
    private int userId;
    private String name;
    private String surname;
    private String patronymic;
    private int age;
    private long phoneNumber;
    private double salary;
    private int departmentId;
    private String workLogIn;
    private String workPassword;

    public HRWorker(){

    }

    public String getWorkLogIn() {
        return workLogIn;
    }

    public void setWorkLogIn(String workLogIn) {
        this.workLogIn = workLogIn;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getWorkPassword() {
        return workPassword;
    }

    public void setWorkPassword(String workPassword) {
        this.workPassword = workPassword;
    }
}
