package Requetes;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import JDBC.CictOracleDataSource;


public class Requete {
	
	public ResultSet requeteSelection(String requete) throws SQLException {
		ResultSet rsEns = null;
		CictOracleDataSource cict = new CictOracleDataSource();
		try {
			Connection connex = cict.getConnection();
			Statement stEns = connex.createStatement();
			rsEns = stEns.executeQuery(requete);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return rsEns;
	}
	
	public int requeteModification(String requete) throws SQLException {
		int nbLigneRetour = 0;
		CictOracleDataSource cict = new CictOracleDataSource();
		try {
			Connection connex = cict.getConnection();
			Statement stEns = connex.createStatement();
			nbLigneRetour = stEns.executeUpdate(requete);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return nbLigneRetour;
	}
}
