package com.ohgiraffers.jpa_project.employee.controller;

import com.ohgiraffers.jpa_project.common.Pagenation;
import com.ohgiraffers.jpa_project.common.PagingButton;
import com.ohgiraffers.jpa_project.employee.dto.EmployeeDTO;
import com.ohgiraffers.jpa_project.employee.dto.JobDTO;
import com.ohgiraffers.jpa_project.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    // 1. 특정 직원 조회

    @GetMapping("/{empId}")
    public String findEmployeeById(@PathVariable int empId, Model model) {

        EmployeeDTO resultEmployee = employeeService.findEmployeeByEmpId(empId);
        model.addAttribute("employee", resultEmployee);

        return "employee/detail";
    }

    // 2. 직원 전체 조회

    @GetMapping("/list")
    public String findEmployeeList(Model model,
                                   @PageableDefault Pageable pageable) {
        // 페이징 처리
        Page<EmployeeDTO> employeeList = employeeService.findEmployeeList(pageable);

        PagingButton paging = Pagenation.getPagingButtonInfo(employeeList);

        model.addAttribute("employeeList", employeeList);
        model.addAttribute("paging", paging);

        return "employee/list";
    }

    // 3. 쿼리메소드 테스트

    @GetMapping("/querymethod")
    public void querymethodPage(){}

    @GetMapping("/search")
    public String findByMenuPrice(@RequestParam Integer salary, Model model) {

        List<EmployeeDTO> employeeList = employeeService.findBySalary(salary);

        model.addAttribute("employeeList", employeeList);

        return "employee/searchResult";
    }

    // 4. 직원 등록하기

    @GetMapping("/regist")
    public void registPage(){}

    @GetMapping("/job")
    @ResponseBody
    public List<JobDTO> findJobList() {
        return employeeService.findAllJob();
    }

    @PostMapping("/regist")
    public String registNewEmployee(@ModelAttribute EmployeeDTO employeeDTO) {
        System.out.println("employeeDTO = " + employeeDTO);

        employeeService.registEmployee(employeeDTO);

        return "redirect:/employee/list";
    }

    // 5. 직원 이름 수정하기

    @GetMapping("/modify")
    public void modifyPage(){}

    @PostMapping("/modify")
    public String modifyEmployee(@ModelAttribute EmployeeDTO employeeDTO) {
        employeeService.modifyEmployee(employeeDTO);
        return "redirect:/employee/" + employeeDTO.getEmpId();
    }

    // 6. 직원 삭제하기

    @GetMapping("/delete")
    public void deletePage(){}

    @PostMapping("/delete")
    public String deleteEmployee(@RequestParam Integer empId) {
        employeeService.deleteEmployee(empId);
        return "redirect:/employee/list";
    }

}
