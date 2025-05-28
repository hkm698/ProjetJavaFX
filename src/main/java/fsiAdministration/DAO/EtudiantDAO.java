package fsiAdministration.DAO;

import fsiAdministration.BO.Etudiant;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EtudiantDAO extends DAO<Etudiant> {

    @Override
    public boolean create(Etudiant obj) {
        boolean controle = false;
        try {
            int id = lastId() + 1;
            obj.setIdEtudiant(id);

            setSessionUser(connection); // 🔥 Injection utilisateur connecté

            String sql = "INSERT INTO Etudiant(idEtudiant, nomEtudiant, prenomEtudiant, idSection, dateNaissance) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, obj.getIdEtudiant());
            statement.setString(2, obj.getNomEtudiant());
            statement.setString(3, obj.getPrenomEtudiant());
            statement.setInt(4, obj.getIdSection());
            statement.setDate(5, Date.valueOf(obj.getDateNaissance()));

            int rowsInserer = statement.executeUpdate();
            controle = rowsInserer > 0;

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return controle;
    }

    public int lastId() {
        int controle = 1;
        try {
            ResultSet result = connection.createStatement().executeQuery("SELECT MAX(idEtudiant) FROM Etudiant");
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
    public boolean delete(Etudiant obj) {
        boolean success = false;
        try {
            setSessionUser(connection);

            String sql = "DELETE FROM etudiant WHERE idEtudiant = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, obj.getIdEtudiant());

            success = ps.executeUpdate() > 0;

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    @Override
    public boolean update(Etudiant obj) {
        boolean success = false;
        try {
            setSessionUser(connection);

            String sql = "UPDATE etudiant SET nomEtudiant = ?, prenomEtudiant = ?, dateNaissance = ?, idSection = ? WHERE idEtudiant = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, obj.getNomEtudiant());
            ps.setString(2, obj.getPrenomEtudiant());
            ps.setDate(3, Date.valueOf(obj.getDateNaissance()));
            ps.setInt(4, obj.getIdSection());
            ps.setInt(5, obj.getIdEtudiant());

            success = ps.executeUpdate() > 0;

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    @Override
    public Etudiant find(int id) {
        Etudiant etu = null;
        try {
            String sql = "SELECT * FROM etudiant WHERE idEtudiant = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                LocalDate naissance = rs.getDate("dateNaissance") != null ? rs.getDate("dateNaissance").toLocalDate() : null;
                etu = new Etudiant(
                        rs.getInt("idEtudiant"),
                        rs.getString("nomEtudiant"),
                        rs.getString("prenomEtudiant"),
                        naissance
                );
                etu.setIdSection(rs.getInt("idSection"));
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etu;
    }

    @Override
    public List<Etudiant> findAll() {
        List<Etudiant> mesEtud = new ArrayList<>();

        try {
            String sql = "SELECT e.*, s.libelleSection FROM etudiant e JOIN section s ON e.idSection = s.idSection";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                LocalDate naissance = rs.getDate("dateNaissance") != null ? rs.getDate("dateNaissance").toLocalDate() : null;
                Etudiant etud = new Etudiant(
                        rs.getInt("idEtudiant"),
                        rs.getString("nomEtudiant"),
                        rs.getString("prenomEtudiant"),
                        naissance
                );
                etud.setIdSection(rs.getInt("idSection"));
                etud.setLibelleSection(rs.getString("libelleSection")); // ✅ ici
                mesEtud.add(etud);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mesEtud;
    }


    public List<Etudiant> findBySection(int idSection) {
        List<Etudiant> liste = new ArrayList<>();
        try {
            String sql = "SELECT e.*, s.libelleSection FROM etudiant e JOIN section s ON e.idSection = s.idSection WHERE e.idSection = ?\n";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, idSection);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LocalDate naissance = rs.getDate("dateNaissance") != null ? rs.getDate("dateNaissance").toLocalDate() : null;
                Etudiant etu = new Etudiant(
                        rs.getInt("idEtudiant"),
                        rs.getString("nomEtudiant"),
                        rs.getString("prenomEtudiant"),
                        naissance
                );
                etu.setIdSection(rs.getInt("idSection"));
                etu.setLibelleSection(rs.getString("libelleSection"));
                liste.add(etu);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }
}