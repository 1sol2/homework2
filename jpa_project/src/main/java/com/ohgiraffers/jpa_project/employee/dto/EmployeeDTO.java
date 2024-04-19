package com.ohgiraffers.jpa_project.employee.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

@Getter
@Setter
@ToString
public class EmployeeDTO {

    private int empId;
    private String empName;
    private String empNo;
    private String email;
    private String phone;
    private int salary;
    private String jobCode;
    private String salLevel;

}
