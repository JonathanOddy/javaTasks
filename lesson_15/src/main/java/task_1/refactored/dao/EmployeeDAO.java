package task_1.refactored.dao;

import task_1.refactored.entity.Employee;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeDAO {

    List<Employee> getEmployees(String departmentId, LocalDate dateFrom, LocalDate dateTo);
}
