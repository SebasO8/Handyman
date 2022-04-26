package com.handyman.Handyman.infrastructure.gateways;

import com.handyman.Handyman.core.domain.service.Service;
import com.handyman.Handyman.core.domain.serviceReport.ServiceReport;
import com.handyman.Handyman.core.domain.technician.Technician;
import com.handyman.Handyman.core.gateways.ServiceReportRepository;
import com.handyman.Handyman.infrastructure.gateways.models.ServiceDBO;
import com.handyman.Handyman.infrastructure.gateways.models.ServiceReportDBO;
import com.handyman.Handyman.infrastructure.gateways.models.TechnicianDBO;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SqlServiceReportRepository implements ServiceReportRepository {
    private final DataSource dataSource;

    public SqlServiceReportRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<ServiceReport> queryWeek(String idTechnician, String startDate, String startDateLimit, String finalDate) {
        String sql = "SELECT * FROM service_report WHERE fk_technician_service = ? AND start_date >= ? :: TIMESTAMP AND start_date < ? :: TIMESTAMP AND final_date <= ? :: TIMESTAMP AND NOT CAST(final_date ::TIMESTAMP as DATE) ::DATE = ? ::TIMESTAMP";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, idTechnician);
            preparedStatement.setString(2, startDate);
            preparedStatement.setString(3, startDateLimit);
            preparedStatement.setString(4, finalDate);
            preparedStatement.setString(5, startDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ServiceReport> result = new ArrayList<>();
            while (resultSet.next()){
                ServiceReportDBO serviceReportDBO = ServiceReportDBO.fromResultSet(resultSet);
                ServiceReport serviceReport = serviceReportDBO.toDomain();
                result.add(serviceReport);
            }
            resultSet.close();
            return result;
        }catch (SQLException exception){
            throw new RuntimeException("Error querying database: " + exception);
        }
    }

    @Override
    public List<Technician> queryTechnicians() {
        String sql = "SELECT * FROM technician_service";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Technician> result = new ArrayList<>();
            while (resultSet.next()){
                TechnicianDBO technicianDBO = TechnicianDBO.fromResultSet(resultSet);
                Technician technician = technicianDBO.toDomain();
                result.add(technician);
            }
            resultSet.close();
            return result;
        }catch (SQLException exception){
            throw new RuntimeException("Error querying database", exception);
        }
    }

    @Override
    public List<Service> queryServices() {
        String sql = "SELECT * FROM service_technician";
        try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Service> result = new ArrayList<>();
            while (resultSet.next()){
                ServiceDBO serviceDBO = ServiceDBO.fromResultSet(resultSet);
                Service service = serviceDBO.toDomain();
                result.add(service);
            }
            resultSet.close();
            return result;
        }catch (SQLException exception){
            throw new RuntimeException("Error querying database", exception);
        }
    }

    @Override
    public void store(ServiceReport serviceReport) {
        String sql = "INSERT INTO service_report (id_service_report, fk_technician_service, fk_service_technician, start_date, final_date) VALUES (?, ?, ?, ? :: TIMESTAMP, ? :: TIMESTAMP);";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, serviceReport.getId().toString());
            preparedStatement.setString(2, serviceReport.getTechnicianId().toString());
            preparedStatement.setString(3, serviceReport.getServiceId().toString());
            preparedStatement.setString(4, serviceReport.getStartDate().toString());
            preparedStatement.setString(5, serviceReport.getFinalDate().toString());
            preparedStatement.executeUpdate();
        } catch (Exception exception) {
            throw new RuntimeException("Error querying database: " + exception);
        }
    }
}
