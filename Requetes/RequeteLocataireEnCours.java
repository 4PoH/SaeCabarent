package Requetes;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import JDBC.CictOracleDataSource;

public class RequeteLocataireEnCours {
	
	String requete_remplir_tableau = "";
	PreparedStatement PrStTab = CictOracleDataSource.getConnection() createStatement();
    ResultSet RSTab = PrStTab.executeQuery(requete_remplir_tableau);

    RSTab.next();

    int i = -1;
    while ( i<rsEns.getRow()) {
        System.out.println(RSTab.getString("NOM") + " " + RSTab.getString("IDENSEIGN"));
        RSTab.next();
        i++;
    }

    rsEns.close();
    stEns.close();
	
}
