package org.example;

import java.sql.SQLException;
import java.util.List;

public class SalaryReportDAO extends AbstractDAO<Integer, SalaryReport>{
    @Override
    public List<SalaryReport> findAll() throws SQLException {
        return List.of();
    }

    @Override
    public SalaryReport findEntityById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean delete(SalaryReport entity) {
        return false;
    }

    @Override
    public SalaryReport create(SalaryReport entity) throws SQLException {
        return null;
    }

    @Override
    public SalaryReport update(SalaryReport entity) {
        return null;
    }
}
