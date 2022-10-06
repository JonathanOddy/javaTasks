package task_1.refactored.notifier;

import task_1.refactored.adapter.EmployeeAdapter;
import task_1.refactored.dao.EmployeeDAO;
import task_1.refactored.dao.EmployeeDAOImpl;
import task_1.refactored.entity.Employee;
import task_1.refactored.mailSender.MailSender;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class SalaryHtmlReportNotifier {

    private Connection connection;

    public SalaryHtmlReportNotifier(Connection databaseConnection) {
        this.connection = databaseConnection;
    }

    public void generateAndSendHtmlSalaryReport(String departmentId, LocalDate dateFrom, LocalDate dateTo, String recipients) {

        EmployeeDAO employeeDAO = new EmployeeDAOImpl(connection);
        List<Employee> employees = employeeDAO.getEmployees(departmentId, dateFrom, dateTo);

        EmployeeAdapter employeeAdapter = new EmployeeAdapter();
        String resultingHtml = employeeAdapter.adaptToHtml(employees);

        MailSender mailSender = new MailSender();
        mailSender.sendMail(recipients, resultingHtml);

    }
}
