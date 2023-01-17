package Rapport;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import JDBC.CictOracleDataSource;

public class CreerImpot2042 {
	
	public void generateImpot(int annee) throws IOException, SQLException {
		
		CictOracleDataSource cict = new CictOracleDataSource();
		Connection connex = cict.getConnection();
		try(Statement stmt = connex.createStatement()){
			ResultSet rs = stmt.executeQuery("SELECT * FROM bailleur");
			boolean bool = rs.next();
			String path = rs.getString("DESTINATION");
			String direction = path + "declaration2042.docx";
			OutputStream fileOut = new FileOutputStream(direction);
			InputStream modele = new FileInputStream("vide.docx");
			XWPFDocument document = new XWPFDocument(modele);

			XWPFParagraph paragraphe = document.createParagraph();
			XWPFRun text = paragraphe.createRun();
			text.setBold(true);
			text.setFontFamily("Calibri");
			text.setFontSize(22);
			text.addTab();
			text.addTab();
			text.setText("Declaration des revenus CERFA 2042 : Regime Micro-foncier");
			
			XWPFParagraph paragraphe2 = document.createParagraph();
			XWPFRun text2 = paragraphe2.createRun();
			
			text2.addCarriageReturn();
			text2.setText("Nom : ");
			text2.setText(rs.getString("NOM"));
			text2.addTab();
			text2.addTab();
			text2.setText("Prenom : ");
			text2.setText(rs.getString("PRENOM"));
			
			text2.addCarriageReturn();
			text2.setText("Telephone : ");
			text2.setText(rs.getString("NUMTEL"));
			text2.addCarriageReturn();
			text2.setText("Mail : ");
			text2.setText(rs.getString("MAIL"));
			
			text2.addCarriageReturn();
			text2.addCarriageReturn();
			text2.setText("Adresse au 1er Janvier : ");
			text2.addCarriageReturn();
			text2.setText("Adresse : ");
			text2.setText(rs.getString("ADRESSE"));
			text2.setText(rs.getString(" "));
			text2.setText(rs.getString("CODEPOSTAL"));
			text2.setText(rs.getString(" "));
			text2.setText(rs.getString("VILLE"));
			
			text2.addCarriageReturn();
			text2.addCarriageReturn();
			text2.addTab();
			text2.addTab();
			text2.setText("REVENU FONCIER Page 4 :");
			CallableStatement cstm = connex.prepareCall("{? = CALL GainAnnee(?)}");
			cstm.registerOutParameter(1,Types.FLOAT);
			cstm.setInt(2, annee);
			cstm.executeUpdate();
			float total = cstm.getFloat(1);
			text2.addCarriageReturn();
			text2.addCarriageReturn();
			text2.setText("Recette Brutes sans abattement : ");
			text2.addTab();
			text2.setText(""+total);
			
			cstm.close();
			rs.close();
			stmt.close();
			document.write(fileOut);
			fileOut.close();
			modele.close();
			document.close();
		}
	}
	
}
