package task_1.refactored.dao;

import task_1.refactored.entity.Employee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    
    private Connection connection;

    public EmployeeDAOImpl(Connection connection) {
        this.connection = connection;
    }
    
    @Override
    public List<Employee> getEmployees(String departmentId, LocalDate dateFrom, LocalDate dateTo) {

        List<Employee> employeeList = new ArrayList<>();
        try {
            // prepare statement with sql
            PreparedStatement ps = getPreparedStatement();
            // inject parameters to sql
            injectParametersToPreparedStatement(departmentId, dateFrom, dateTo, ps);
            // execute query and get the results
            ResultSet results = ps.executeQuery();
            // translate data from ResultSet to List<Employee>
            getEmployeeListFromResult(employeeList, results);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeList;
    }

    private void getEmployeeListFromResult(List<Employee> employeeList, ResultSet results) throws SQLException {
        while (results.next()) {
            String employeeId = results.getString("emp_id");
            String employeeName = results.getString("amp_name");
            double employeeSalary = results.getDouble("salary");
            employeeList.add(new Employee(employeeId, employeeName, employeeSalary));
        }
    }

    private PreparedStatement getPreparedStatement() throws SQLException {
        return connection.prepareStatement(
                "select emp.id as emp_id, emp.name as amp_name, sum(salary) as salary from employee emp left join" +
                "salary_payments sp on emp.id = sp.employee_id where emp.department_id = ? and" +
                " sp.date >= ? and sp.date <= ? group by emp.id, emp.name"
        );
    }

    private void injectParametersToPreparedStatement(String departmentId, LocalDate dateFrom, LocalDate dateTo, PreparedStatement ps) throws SQLException {
        ps.setString(0, departmentId);
        ps.setDate(1, new java.sql.Date(dateFrom.toEpochDay()));
        ps.setDate(2, new java.sql.Date(dateTo.toEpochDay()));
    }
    
}
