package fsiAdministration.BO;

public class Utilisateur {

    private int idUtilisateur;
    private String loginUtilisateur;
    private String mdpUtilisateur;

    public Utilisateur() {
    }

    public Utilisateur(int idUtilisateur, String mdpUtilisateur, String loginUtilisateur) {
        this.idUtilisateur = idUtilisateur;
        this.mdpUtilisateur = mdpUtilisateur;
        this.loginUtilisateur = loginUtilisateur;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getMdpUtilisateur() {
        return mdpUtilisateur;
    }

    public void setMdpUtilisateur(String mdpUtilisateur) {
        this.mdpUtilisateur = mdpUtilisateur;
    }

    public String getLoginUtilisateur() {
        return loginUtilisateur;
    }

    public void setLoginUtilisateur(String loginUtilisateur) {
        this.loginUtilisateur = loginUtilisateur;
    }
}
