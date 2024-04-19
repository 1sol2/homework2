package com.ohgiraffers.jpa_project.employee.service;

import com.ohgiraffers.jpa_project.employee.dto.EmployeeDTO;
import com.ohgiraffers.jpa_project.employee.dto.JobDTO;
import com.ohgiraffers.jpa_project.employee.entity.Employee;
import com.ohgiraffers.jpa_project.employee.entity.Job;
import com.ohgiraffers.jpa_project.employee.repository.EmployeeRepository;
import com.ohgiraffers.jpa_project.employee.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final JobRepository jobRepository;
    private final ModelMapper modelMapper;

    // 1. findByEmpId
    public EmployeeDTO findEmployeeByEmpId(int empId) {
        Employee foundEmployee = employeeRepository.findById(empId).orElseThrow(IllegalArgumentException::new);
        return modelMapper.map(foundEmployee, EmployeeDTO.class);
    }

    // 2. finaAll : Sort
    public List<EmployeeDTO> findEmployeeList() {
        List<Employee> employeeList = employeeRepository.findAll(Sort.by("empId").descending());
        return employeeList.stream()
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .toList();
    }

    // 3. findAll : Pageable
    public Page<EmployeeDTO> findEmployeeList(Pageable pageable) {
        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                Sort.by("empId").descending()
        );

        Page<Employee> employeeList =  employeeRepository.findAll(pageable);
        return employeeList.map(employee -> modelMapper.map(employee, EmployeeDTO.class));
    }

    // 4. Query Method
    public List<EmployeeDTO> findBySalary(Integer salary) {

        List<Employee> employeeList = employeeRepository.findBySalaryGreaterThan(
                salary,
                Sort.by("salary").descending()
        );

        return employeeList.stream()
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .toList();
    }

    /* 5. JPQL or Native Query */
    public List<JobDTO> findAllJob() {

        List<Job> jobList = jobRepository.findAllJob();
        // List<Employee> jobList = employeeRepository.findAll();

        return jobList.stream()
                .map(job -> modelMapper.map(job, JobDTO.class))
                .toList();

    }

    /* 6. save */
    @Transactional
    public void registEmployee(EmployeeDTO employeeDTO) {

        employeeRepository.save(modelMapper.map(employeeDTO, Employee.class));

    }

    /* 7. 수정 */
    @Transactional
    public void modifyEmployee(EmployeeDTO employeeDTO) {
        Employee foundEmp = employeeRepository.findById(employeeDTO.getEmpId()).orElseThrow(IllegalArgumentException::new);

        foundEmp.modifyEmpName(employeeDTO.getEmpName());
    }

    /* 8. deleteById */
    @Transactional
    public void deleteEmployee(Integer empId) {
        employeeRepository.deleteById(empId);
    }
}
