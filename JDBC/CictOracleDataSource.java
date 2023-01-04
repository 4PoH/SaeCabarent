package JDBC;

import java.sql.Connection;
import java.sql.SQLException;
import oracle.jdbc.datasource.impl.OracleDataSource;

public class CictOracleDataSource extends OracleDataSource {
	public CictOracleDataSource() throws SQLException {
		super();
	}

	public static void main(String [] arg) throws SQLException {
		String url = new String("jdbc:oracle:thin:@telline.univ-tlse3.fr" + ":1521:etupre");
		String nom_utilisateur = new String("DRR3096A"); 
		String mot_passe = new String("$iutinfo");
		OracleDataSource bd = new OracleDataSource();

		System.out.println(String.format("Tentative de connexion à : %s", url));
		
		bd.setURL(url);
		bd.setUser(nom_utilisateur);
		bd.setPassword(mot_passe);
		
		Connection cn = bd.getConnection();
		System.out.println("Connexion reussi");
		
		cn.close();
		System.out.println("La connexion est fermee la");
	}	
}