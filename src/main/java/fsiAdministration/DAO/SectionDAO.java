package fsiAdministration.DAO;

import fsiAdministration.BO.Section;
import javafx.beans.property.SimpleStringProperty;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SectionDAO extends DAO<Section> {

    @Override
    public boolean create(Section obj) {
        boolean success = false;
        try {
            setSessionUser(connection); // 🔥 injection de l'utilisateur connecté

            String sql = "INSERT INTO section (libelleSection) VALUES (?);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, obj.getLibelleSection());

            success = ps.executeUpdate() > 0;

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    @Override
    public boolean delete(Section obj) {
        boolean success = false;
        try {
            setSessionUser(connection);

            String sql = "DELETE FROM section WHERE idSection = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, obj.getIdSection());

            success = ps.executeUpdate() > 0;
            ps.close();

        } catch (SQLException e) {
            // Analyse du message d'erreur PostgreSQL
            String msg = e.getMessage();
            if (e.getSQLState().equals("23503")) { // Violation de contrainte FK
                if (msg.contains("cours_section_fk")) {
                    throw new RuntimeException("Impossible de supprimer cette section : elle contient encore des cours.");
                } else if (msg.contains("eleve_section_fk")) { // ✅ nom réel de la FK
                    throw new RuntimeException("Impossible de supprimer cette section : elle contient encore des étudiants.");
                } else {
                    throw new RuntimeException("Impossible de supprimer cette section car elle est encore liée à d'autres données.");
                }
            } else {
                e.printStackTrace();
                throw new RuntimeException("Erreur SQL lors de la suppression de la section.");
            }
        }
        return success;
    }



    @Override
    public boolean update(Section obj) {
        boolean success = false;
        try {
            setSessionUser(connection); // 🔥 injection de l'utilisateur connecté

            String sql = "UPDATE section SET libelleSection = ? WHERE idSection = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, obj.getLibelleSection());
            ps.setInt(2, obj.getIdSection());

            success = ps.executeUpdate() > 0;

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    @Override
    public Section find(int id) {
        Section section = null;
        try {
            String sql = "SELECT * FROM section WHERE idSection = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                section = new Section(
                        rs.getInt("idSection"),
                        new SimpleStringProperty(rs.getString("libelleSection"))
                );
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return section;
    }

    @Override
    public List<Section> findAll() {
        List<Section> sections = new ArrayList<>();
        try {
            String sql = "SELECT * FROM section";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Section s = new Section(
                        rs.getInt("idSection"),
                        new SimpleStringProperty(rs.getString("libelleSection"))
                );
                sections.add(s);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sections;
    }
}