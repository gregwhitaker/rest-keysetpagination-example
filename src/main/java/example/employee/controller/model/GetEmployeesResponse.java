package example.employee.controller.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import example.employee.data.model.Employee;
import org.hashids.Hashids;

import java.util.ArrayList;
import java.util.List;

/**
 * API response returned when querying multiple employee records.
 */
@JsonPropertyOrder({
        "count",
        "employees"
})
public class GetEmployeesResponse {

    /**
     * Transforms a list of {@link Employee} objects from the database into a {@link GetEmployeesResponse} object
     * to display to the API user.
     *
     * @param employees list of employees
     * @return employees response object
     */
    public static GetEmployeesResponse from(List<Employee> employees, Hashids hashids) {
        GetEmployeesResponse response = new GetEmployeesResponse();

        if (employees != null || !employees.isEmpty()) {
            response.setCount(employees.size());
            employees.forEach(employee -> response.getEmployees().add(GetEmployeeResponse.from(employee)));

            response.getLinks().add(
                    new Link("next",
                            String.format("/employees?cursor=%s", hashids.encode(employees.get(employees.size() - 1).getId()))));
        }

        return response;
    }

    private int count = 0;
    private List<GetEmployeeResponse> employees = new ArrayList<>();
    private List<Link> links = new ArrayList<>();

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<GetEmployeeResponse> getEmployees() {
        return employees;
    }

    public void setEmployees(List<GetEmployeeResponse> employees) {
        this.employees = employees;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
