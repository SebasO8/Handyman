package com.handyman.Handyman.infrastructure.gateways;

import com.handyman.Handyman.core.domain.serviceReport.ServiceReport;
import com.handyman.Handyman.core.gateways.ServiceReportRepository;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class SqlServiceReportRepository implements ServiceReportRepository {
    private final DataSource dataSource;

    public SqlServiceReportRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<ServiceReport> queryWeek(int weekNumber) {
        return null;
    }

    @Override
    public List<ServiceReport> queryTechnicians(int weekNumber) {
        return null;
    }

    @Override
    public List<ServiceReport> queryServices(int weekNumber) {
        return null;
    }

    @Override
    public void store(ServiceReport serviceReport) {
        String sql = "INSERT INTO SERVICE_REPORT (ID_SERVICE_REPORT, FK_TECHNICIAN_SERVICE, FK_SERVICE_TECHNICIAN, starDate, finalDate) VALUES (?, ?, ?, ?, ?)";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, serviceReport.getId().toString());
            preparedStatement.setString(2, serviceReport.getTechnicianId().toString());
            preparedStatement.setString(3, serviceReport.getServiceId().toString());
            preparedStatement.setString(4, serviceReport.getStartDate().toString());
            preparedStatement.setString(5, serviceReport.getFinalDate().toString());

            preparedStatement.executeUpdate();
        } catch (Exception exception) {
            throw new RuntimeException("Error querying database", exception);
        }
    }
}
