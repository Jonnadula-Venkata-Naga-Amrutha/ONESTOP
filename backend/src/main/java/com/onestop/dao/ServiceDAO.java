package com.onestop.dao;

import com.onestop.config.DBConnection;
import com.onestop.model.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAO {

    // Add a new service
    public int addService(Service service) throws SQLException {

        String sql = "INSERT INTO provider_services "
                + "(provider_id, category_id, service_name, description, base_price, service_status) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, service.getProviderId());
            ps.setInt(2, service.getCategoryId());
            ps.setString(3, service.getServiceName());
            ps.setString(4, service.getDescription());
            ps.setBigDecimal(5, service.getBasePrice());
            ps.setString(6, service.getServiceStatus());

            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new SQLException("Service creation failed.");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

            throw new SQLException("Service ID was not generated.");
        }
    }

    // Get service by ID
    public Service getServiceById(int serviceId) throws SQLException {

        String sql = "SELECT * FROM provider_services WHERE service_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, serviceId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapService(rs);
                }
            }
        }

        return null;
    }

    // Get all active services
    public List<Service> getAllServices() throws SQLException {

        String sql = "SELECT * FROM provider_services "
                + "WHERE service_status = 'ACTIVE'";

        List<Service> services = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                services.add(mapService(rs));
            }
        }

        return services;
    }

    // Get services by provider
    public List<Service> getServicesByProvider(int providerId)
            throws SQLException {

        String sql = "SELECT * FROM provider_services WHERE provider_id = ?";

        List<Service> services = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, providerId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    services.add(mapService(rs));
                }
            }
        }

        return services;
    }

    // Update service
    public boolean updateService(Service service) throws SQLException {

        String sql = "UPDATE provider_services "
                + "SET category_id = ?, service_name = ?, description = ?, "
                + "base_price = ?, service_status = ? "
                + "WHERE service_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, service.getCategoryId());
            ps.setString(2, service.getServiceName());
            ps.setString(3, service.getDescription());
            ps.setBigDecimal(4, service.getBasePrice());
            ps.setString(5, service.getServiceStatus());
            ps.setInt(6, service.getServiceId());

            return ps.executeUpdate() > 0;
        }
    }

    // Deactivate service instead of permanently deleting it
    public boolean deactivateService(int serviceId) throws SQLException {

        String sql = "UPDATE provider_services "
                + "SET service_status = 'INACTIVE' WHERE service_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, serviceId);

            return ps.executeUpdate() > 0;
        }
    }

    // Convert database row into Service object
    private Service mapService(ResultSet rs) throws SQLException {

        Service service = new Service();

        service.setServiceId(rs.getInt("service_id"));
        service.setProviderId(rs.getInt("provider_id"));
        service.setCategoryId(rs.getInt("category_id"));
        service.setServiceName(rs.getString("service_name"));
        service.setDescription(rs.getString("description"));
        service.setBasePrice(rs.getBigDecimal("base_price"));
        service.setServiceStatus(rs.getString("service_status"));
        service.setCreatedAt(rs.getTimestamp("created_at"));

        return service;
    }
}