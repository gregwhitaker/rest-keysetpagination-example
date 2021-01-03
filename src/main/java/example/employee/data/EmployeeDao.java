package example.employee.data;

import example.employee.data.model.Employee;
import example.employee.data.model.EmployeeAddress;
import org.hashids.Hashids;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for accessing employee information.
 */
@Component
public class EmployeeDao {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeDao.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private Hashids hashids;

    /**
     * Gets information for an employee.
     *
     * @param id employee id
     * @return employee information if found; otherwise <code>null</code>
     */
    public Employee getEmployee(long id) {
        try (Connection conn = dataSource.getConnection()) {
            final String sql = "SELECT e.*, d.name AS department_name FROM employees e JOIN departments d on e.department = d.id WHERE e.id = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, id);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return Employee.from(rs);
                    }

                    // Employee not found
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving employee from database.", e);
        }
    }

    /**
     * Gets all employees in the system (paginated).
     *
     * @param cursor pagination cursor
     * @return a list of employee information
     */
    public List<Employee> getEmployees(String cursor) {
        try (Connection conn = dataSource.getConnection()) {
            final String sql = "SELECT e.*, d.name AS department_name FROM employees e JOIN departments d on e.department = d.id WHERE e.id > ? ORDER BY e.id LIMIT 25";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                if (StringUtils.isEmpty(cursor)) {
                    // Set the starting id to "0" if cursor is not supplied
                    ps.setLong(1, 0L);
                } else {
                    long[] decodedCursor = hashids.decode(cursor);
                    ps.setLong(1, decodedCursor[0]);
                }

                try (ResultSet rs = ps.executeQuery()) {
                    List<Employee> employees = new ArrayList<>();

                    while (rs.next()) {
                        employees.add(Employee.from(rs));
                    }

                    return employees;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving employees from database.", e);
        }
    }

    /**
     * Gets the home address for an employee.
     *
     * @param employeeId employee id
     * @return the employee's home address if found; otherwise <code>null</code>
     */
    public EmployeeAddress getEmployeeHomeAddress(long employeeId) {
        try (Connection conn = dataSource.getConnection()) {
            final String sql = "SELECT e.id AS employee_id, ea.* FROM employees e JOIN employee_addresses ea on e.home_addr = ea.id WHERE e.id = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, employeeId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return EmployeeAddress.from(rs);
                    }

                    // Employee address not found
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving employee home address from database.", e);
        }
    }

    /**
     * Gets the work address for an employee
     *
     * @param employeeId employee id
     * @return the employee's work address if found; otherwise <code>null</code>
     */
    public EmployeeAddress getEmployeeWorkAddress(long employeeId) {
        try (Connection conn = dataSource.getConnection()) {
            final String sql = "SELECT e.id AS employee_id, ea.* FROM employees e JOIN employee_addresses ea on e.work_addr = ea.id WHERE e.id = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, employeeId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return EmployeeAddress.from(rs);
                    }

                    // Employee address not found
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving employee work address from database.", e);
        }
    }
}
