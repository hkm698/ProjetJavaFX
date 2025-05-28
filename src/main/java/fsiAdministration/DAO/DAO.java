package fsiAdministration.DAO;

import fsiAdministration.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public abstract class DAO<T> {

    protected final Connection connection;

    /**
     * Initialise la connexion depuis la classe Session
     */
    public DAO() {
        this.connection = Session.getConnection();
    }

    /**
     * Méthode de création
     * @param obj l'objet à insérer
     * @return succès ou non
     */
    public abstract boolean create(T obj);

    /**
     * Méthode pour effacer
     * @param obj l'objet à supprimer
     * @return succès ou non
     */
    public abstract boolean delete(T obj);

    /**
     * Méthode de mise à jour
     * @param obj l'objet à modifier
     * @return succès ou non
     */
    public abstract boolean update(T obj);

    /**
     * Méthode de recherche par identifiant
     * @param id identifiant
     * @return objet trouvé ou null
     */
    public abstract T find(int id);

    /**
     * Méthode de récupération de tous les objets
     * @return liste complète
     */
    public abstract List<T> findAll();

    /**
     * Définit l'utilisateur de session dans la base PostgreSQL (set_config)
     * @param conn connexion active
     * @throws SQLException si erreur d'exécution SQL
     */
    public void setSessionUser(Connection conn) throws SQLException {
        int id = Session.getUtilisateur().getIdUtilisateur();
        PreparedStatement ps = conn.prepareStatement("SELECT set_config('app.current_user_id', ?, false)");
        ps.setString(1, String.valueOf(id));
        ps.execute();
        ps.close();
    }
}
