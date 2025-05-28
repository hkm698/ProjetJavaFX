package fsiAdministration.DAO;

import fsiAdministration.BO.Cours;
import javafx.beans.property.SimpleStringProperty;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoursDAO extends DAO<Cours> {

    @Override
    public boolean create(Cours obj) {
        boolean controle = false;
        try {
            int id = lastId() + 1;
            obj.setIdCours(id);

            setSessionUser(connection); // 🔥 Injecter l'ID utilisateur connecté

            String sql = "INSERT INTO cours(idCours, libelleCours, descriptionCours, idSection) VALUES (?, ?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, obj.getIdCours());
            statement.setString(2, obj.getLibelleCours());
            statement.setString(3, obj.getDescriptionCours());
            statement.setInt(4, obj.getIdSection());

            int rowsInserted = statement.executeUpdate();
            controle = rowsInserted > 0;

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return controle;
    }

    @Override
    public boolean delete(Cours obj) {
        boolean controle = false;
        try {
            setSessionUser(connection);

            String sql = "DELETE FROM cours WHERE idCours = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, obj.getIdCours());

            int rowsDeleted = ps.executeUpdate();
            controle = (rowsDeleted > 0);

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return controle;
    }

    @Override
    public boolean update(Cours obj) {
        boolean controle = false;
        try {
            setSessionUser(connection);

            String sql = "UPDATE cours SET libelleCours = ?, descriptionCours = ?, idSection = ? WHERE idCours = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, obj.getLibelleCours());
            ps.setString(2, obj.getDescriptionCours());
            ps.setInt(3, obj.getIdSection());
            ps.setInt(4, obj.getIdCours());

            int rowsUpdated = ps.executeUpdate();
            controle = (rowsUpdated > 0);

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return controle;
    }

    public int lastId() {
        int controle = 0;
        try {
            ResultSet result = connection.createStatement().executeQuery("SELECT MAX(idCours) FROM cours");
            if (result.next()) {
                controle = result.getInt(1);
            }
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return controle;
    }

    @Override
    public Cours find(int id) {
        Cours cours = null;
        try {
            String sql = "SELECT * FROM cours WHERE idCours = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                cours = new Cours(
                        rs.getInt("idCours"),
                        new SimpleStringProperty(rs.getString("libelleCours")),
                        new SimpleStringProperty(rs.getString("descriptionCours")),
                        rs.getInt("idSection")
                );
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cours;
    }

    @Override
    public List<Cours> findAll() {
        List<Cours> coursList = new ArrayList<>();
        try {
            String sql = "SELECT c.*, s.libelleSection FROM cours c\n" +
                    "JOIN section s ON c.idSection = s.idSection\n";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Cours cours = new Cours();
                cours.setIdCours(rs.getInt("idCours"));
                cours.setLibelleCours(rs.getString("libelleCours"));
                cours.setDescriptionCours(rs.getString("descriptionCours"));
                cours.setIdSection(rs.getInt("idSection"));
                cours.setLibelleSection(rs.getString("libelleSection")); // ✅ nouveau

                coursList.add(cours);


            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coursList;
    }

    public List<Cours> findBySection(int idSection) {
        List<Cours> liste = new ArrayList<>();
        try {
            String sql = "SELECT c.*, s.libelleSection FROM cours c\n" +
                    "JOIN section s ON c.idSection = s.idSection\n" +
                    "WHERE c.idSection = ?\n";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, idSection);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Cours cours = new Cours();
                cours.setIdCours(rs.getInt("idCours"));
                cours.setLibelleCours(rs.getString("libelleCours"));
                cours.setDescriptionCours(rs.getString("descriptionCours"));
                cours.setIdSection(rs.getInt("idSection"));
                cours.setLibelleSection(rs.getString("libelleSection")); // ✅ nouveau
                liste.add(cours);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }
}