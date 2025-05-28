package fsiAdministration.DAO;

import fsiAdministration.BO.Utilisateur;
import fsiAdministration.Session;

import java.sql.*;
import java.util.List;

public class UtilisateurDAO extends DAO<Utilisateur> {

    @Override
    public boolean create(Utilisateur obj) {
        return false;
    }

    @Override
    public boolean delete(Utilisateur obj) {
        return false;
    }

    @Override
    public boolean update(Utilisateur obj) {
        return false;
    }

    @Override
    public Utilisateur find(int id) {
        return null;
    }

    @Override
    public List<Utilisateur> findAll() {
        return List.of();
    }

    /**
     * Recherche un utilisateur par login et mot de passe
     * @param login identifiant
     * @param password mot de passe
     * @return l'utilisateur trouvé ou null
     */
    public Utilisateur find(String login, String password) {
        Utilisateur user = null;

        try {
            String sql = "SELECT * FROM utilisateur WHERE loginutilisateur = ? AND mdputilisateur = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, login);
            ps.setString(2, password);

            ResultSet result = ps.executeQuery();

            if (result.next()) {
                user = new Utilisateur();
                user.setIdUtilisateur(result.getInt("idutilisateur"));
                user.setLoginUtilisateur(result.getString("loginutilisateur"));
                user.setMdpUtilisateur(result.getString("mdputilisateur"));

                // Stocker l'utilisateur dans la session
                Session.setUtilisateur(user);

                // Injecter l'utilisateur dans la session PostgreSQL
                setSessionUser(connection);
            }

            result.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}
