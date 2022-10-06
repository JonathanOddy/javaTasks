package task_1.refactored.adapter;

import task_1.refactored.entity.Employee;

import java.util.Iterator;
import java.util.List;

public class EmployeeAdapter {

    public String adaptToHtml(List<Employee> employeeList) {

        // create a StringBuilder holding a resulting html
        StringBuilder resultingHtml = new StringBuilder();
        resultingHtml.append("<html><body><table><tr><td>Employee</td><td>Salary</td></tr>");
        double totals = 0;

        Iterator<Employee> employeeIterator = employeeList.iterator();
        while (employeeIterator.hasNext()) {
            Employee employee = employeeIterator.next();
            // process each row of query results
            resultingHtml.append("<tr>"); // add row start tag
            resultingHtml.append("<td>").append(employee.getName()).append("</td>"); // appending employee name
            resultingHtml.append("<td>").append(employee.getSalary()).append("</td>"); // appending employee salary for period
            resultingHtml.append("</tr>"); // add row end tag
            totals += employee.getSalary(); // add salary to totals
        }
        resultingHtml.append("<tr><td>Total</td><td>").append(totals).append("</td></tr>");
        resultingHtml.append("</table></body></html>");

        return resultingHtml.toString();
    }
}
