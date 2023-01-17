package JDBC;

import java.sql.Connection;

import java.sql.SQLException;
import oracle.jdbc.datasource.impl.OracleDataSource;

public class CictOracleDataSource extends OracleDataSource {
	
	public CictOracleDataSource() throws SQLException {
		this.setURL("jdbc:oracle:thin:@telline.univ-tlse3.fr" + ":1521:etupre");
		this.setUser("DRR3096A"); 
		this.setPassword("$iutinfo");
	}

	public static void main(String [] arg) throws SQLException {
		CictOracleDataSource bd = new CictOracleDataSource();
		try {
			Connection connex = bd.getConnection();
			System.out.println("Connexion reussi");
			connex.close();
		} catch(SQLException e) {
			System.out.println("Connexion rate");
		}
	}	
}