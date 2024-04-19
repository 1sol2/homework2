package com.ohgiraffers.jpa_project.employee.repository;

import com.ohgiraffers.jpa_project.employee.entity.Employee;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.*;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // 파라미터로 전달 받은 월급을 초과하는 직원 목록 조회
    List<Employee> findBySalaryGreaterThan(Integer salary, Sort sort);

}
