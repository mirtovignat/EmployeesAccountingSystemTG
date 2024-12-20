package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO extends AbstractDAO<Integer, Department> {

    private String SQL_SELECT_ALL_DEPARTMENTS = "SELECT * FROM departments";
    private ResultSet resultSet;
    private String url = "jdbc:postgresql://localhost:5432/postgres";
    private String user = "postgres";
    private String password = "postgres";


    private PreparedStatement preparedStatement;
    private Statement statement;

    public DepartmentDAO() throws SQLException {

    }

    private Connection getConnection() throws SQLException {

        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

    @Override
    public List<Department> findAll() {
        List<Department> departments = new ArrayList<>();
        String sql = SQL_SELECT_ALL_DEPARTMENTS;
        try (Connection connection = getConnection()) {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int departmentId = resultSet.getInt("department_id");
                String name = resultSet.getString("name");
                int usersQuantity = resultSet.getInt("users_quantity");
                float budget = resultSet.getFloat("budget");
                String address = resultSet.getString("address");

                Department department = new Department(departmentId, name, usersQuantity,
                        budget, address);
                departments.add(department);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
    }

    @Override
    public Department findEntityById(Integer id) {
        try  (Connection connection = getConnection()){
            String sql = SQL_SELECT_ALL_DEPARTMENTS + " WHERE department_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Department department = new Department();

                department.setDepartmentId(resultSet.getInt("department_id"));
                department.setName(resultSet.getString("name"));
                department.setUsersQuantity(resultSet.getInt("users_quantity"));
                department.setBudget(resultSet.getFloat("budget"));
                department.setAddress(resultSet.getString("address"));
                return department;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try (Connection connection = getConnection()) {
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
            String sql = "DELETE FROM departments WHERE department_id = ?";
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
    public boolean delete(Department department) {
        try (Connection connection = getConnection()) {
            String sql = "DELETE FROM departments WHERE department_id = ? AND name = ? AND " +
                    "users_quantity = ? AND budget = ? AND address = ? ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, department.getDepartmentId());
            preparedStatement.setInt(2, department.getUsersQuantity());
            preparedStatement.setFloat(3, department.getBudget());
            preparedStatement.setString(4, department.getAddress());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Department create(Department department) {
        String sql = "Insert INTO departments (name, users_quantity, budget, " +
                "address, department_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            preparedStatement.setString(1, department.getName());
            preparedStatement.setInt(2, department.getUsersQuantity());
            preparedStatement.setFloat(3, department.getBudget());
            preparedStatement.setString(4, department.getAddress());
            preparedStatement.setInt(5, department.getDepartmentId());
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    department.setDepartmentId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return department;
    }

    @Override
    public Department update(Department department) {

        //statement = connection.prepareStatement(query)
        try (Connection connection = getConnection()) {
            String sql = "UPDATE departments SET name=?, users_quantity=?, budget=?, " +
                    "address=?, department_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, department.getName());
            preparedStatement.setInt(2, department.getUsersQuantity());
            preparedStatement.setFloat(3, department.getBudget());
            preparedStatement.setString(4, department.getAddress());
            preparedStatement.setInt(5, department.getDepartmentId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return department;
    }
}
