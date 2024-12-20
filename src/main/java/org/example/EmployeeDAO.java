package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class EmployeeDAO extends
        AbstractDAO<Integer, Employee> {

    private String SQL_SELECT_ALL_USERS = "SELECT * FROM users";
    private ResultSet resultSet;
    private String url = "jdbc:postgresql://localhost:5432/postgres";
    private String user = "postgres";
    private String password = "postgres";


    private PreparedStatement preparedStatement;
    private Statement statement;

    public EmployeeDAO() throws SQLException {

    }

    private Connection getConnection() throws SQLException {

        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

    @Override
    public List<Employee> findAll() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = SQL_SELECT_ALL_USERS;
        try (Connection connection = getConnection()) {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String patronymic = resultSet.getString("patronymic");
                int age = resultSet.getInt("age");
                long phoneNumber = resultSet.getLong("phone_number");
                double salary = resultSet.getDouble("salary");
                int departmentId = resultSet.getInt("department_id");
                String workLogIn = resultSet.getString("work_log_in");
                String workPassword = resultSet.getString("work_password");
                Employee employee = new Employee(userId, name, surname, patronymic, age, phoneNumber,
                        salary, departmentId, workLogIn, workPassword);
                employees.add(employee);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;

    }


    @Override
    public Employee findEntityById(Integer id) {
        try (Connection connection = getConnection()){
            String sql = SQL_SELECT_ALL_USERS + " WHERE user_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setUserId(resultSet.getInt("user_id"));
                employee.setName(resultSet.getString("name"));
                employee.setSurname(resultSet.getString("surname"));
                employee.setPatronymic(resultSet.getString("patronymic"));
                employee.setAge(resultSet.getInt("age"));
                employee.setPhoneNumber(resultSet.getLong("phone_number"));
                employee.setSalary(resultSet.getDouble("salary"));
                employee.setDepartmentId(resultSet.getInt("department_id"));
                employee.setWorkPassword(resultSet.getString("work_password"));
                return employee;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try (Connection connection = getConnection()){
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;

    }

    @Override
    public boolean delete(Integer id) {
        try (Connection connection = getConnection()) {
            String sql = "DELETE FROM users WHERE user_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public boolean delete(Employee employee) {
        try (Connection connection = getConnection()) {
            String sql = "DELETE FROM users WHERE user_id = ? AND name = ? AND surname = ? AND patronymic = ? " +
                    "AND age = ? AND phone_number = ? AND salary = ? AND department_id = ? AND work_password = ?";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, employee.getUserId());
            preparedStatement.setString(2, employee.getName());
            preparedStatement.setString(3, employee.getSurname());
            preparedStatement.setString(4, employee.getPatronymic());
            preparedStatement.setInt(5, employee.getAge());
            preparedStatement.setLong(6, employee.getPhoneNumber());
            preparedStatement.setDouble(7, employee.getSalary());
            preparedStatement.setInt(8, employee.getDepartmentId());
            preparedStatement.setString(9, employee.getWorkPassword());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public Employee create(Employee employee) {
        String sql = "INSERT INTO users (name, surname, patronymic, age, phone_number, salary, " +
                "department_id, work_login, work_password) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(sql, statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getSurname());
            preparedStatement.setString(3, employee.getPatronymic());
            preparedStatement.setInt(4, employee.getAge());
            preparedStatement.setLong(5, employee.getPhoneNumber());
            preparedStatement.setDouble(6, employee.getSalary());
            preparedStatement.setInt(7, employee.getDepartmentId());
            preparedStatement.setString(8, employee.getWorkLogIn());
            preparedStatement.setString(9, employee.getWorkPassword());

            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    employee.setUserId(generatedKeys.getInt(1));
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }
    @Override
    public Employee update(Employee employee) {

        String sql = "UPDATE users SET name = ?, surname = ?, patronymic = ?, age = ?, phone_number = ?, " +
                "salary = ?, department_id = ?, work_password = ?";

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getSurname());
            preparedStatement.setString(3, employee.getPatronymic());
            preparedStatement.setInt(4, employee.getAge());
            preparedStatement.setLong(5, employee.getPhoneNumber());
            preparedStatement.setDouble(6, employee.getSalary());
            preparedStatement.setInt(7, employee.getDepartmentId());
            preparedStatement.setString(8, employee.getWorkPassword());


            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating user failed, no rows affected.");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }



    public Employee findUserBySurname(String surname) {
        try(Connection connection = getConnection()) {
            String sql = SQL_SELECT_ALL_USERS + " WHERE surname = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, surname);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setUserId(resultSet.getInt("user_id"));
                employee.setName(resultSet.getString("name"));
                employee.setSurname(resultSet.getString("surname"));
                employee.setPatronymic(resultSet.getString("patronymic"));
                employee.setAge(resultSet.getInt("age"));
                employee.setPhoneNumber(resultSet.getLong("phone_number"));
                employee.setSalary(resultSet.getDouble("salary"));
                employee.setDepartmentId(resultSet.getInt("department_id"));
                employee.setWorkPassword(resultSet.getString("work_password"));
                return employee;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try (Connection connection = getConnection()){
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;

    }
}
