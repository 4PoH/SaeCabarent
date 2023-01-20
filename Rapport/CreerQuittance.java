package Rapport;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import JDBC.CictOracleDataSource;

public class CreerQuittance {
	
	@SuppressWarnings("deprecation")
	public void generateImpot(int mois, int annee, int idcontrat) throws IOException, SQLException {
		
		CictOracleDataSource cict = new CictOracleDataSource();
		Connection connex = cict.getConnection();
		try(Statement stmt = connex.createStatement()){
			ResultSet rs = stmt.executeQuery("SELECT * FROM bailleur");
			boolean bool = rs.next();
			String path = rs.getString("DESTINATION");
			
			String date = mois+"/"+annee;
			CallableStatement cstm = connex.prepareCall("{CALL generationQuittance(?,?)}");
			cstm.setInt(1, idcontrat);
			cstm.setString(2, date);
			cstm.executeUpdate();
			
			PreparedStatement stmt2 = connex.prepareStatement("SELECT numquitance, montantloyer, montantcharges, datemiseeneffet, montantreglerduloyer, datelocation "
					+"FROM contrat, loue "
					+"WHERE contrat.idcontrat = ? AND loue.idcontrat=contrat.idcontrat AND TO_CHAR(loue.datelocation, 'MM/YYYY') = ?");
			stmt2.setInt(1, idcontrat);
			stmt2.setString(2, date);
			ResultSet rs2 = stmt2.executeQuery();
			bool = rs2.next();
			int numQuit = rs2.getInt("numquitance");
			float loyer = rs2.getFloat("montantloyer");
			float charge = rs2.getFloat("montantcharges");
			Date dateloc = rs2.getDate("datelocation");
			float montRegler = rs2.getFloat("montantreglerduloyer");
			Date dateMiseEffet = rs2.getDate("datemiseeneffet"); 

			PreparedStatement stmt3 = connex.prepareStatement("SELECT locataire.nom, locataire.prenom "
					+"FROM contrat, locataire, relie "
					+"WHERE contrat.idcontrat = ? AND relie.idcontrat=contrat.idcontrat AND relie.idlocataire = locataire.idlocataire");
			stmt3.setInt(1, idcontrat);
			ResultSet rsLoc = stmt3.executeQuery();
			bool = rsLoc.next();
			
			PreparedStatement stmt4 = connex.prepareStatement("SELECT lieuxdelocations.adresse, lieuxdelocations.codepostal, bati.ville "
					+"FROM lieuxdelocations, contrat, loue, bati"
					+"WHERE contrat.idcontrat = ? AND loue.idcontrat=contrat.idcontrat AND lieuxdelocations.idlogement = loue.idlogement "
					+"AND lieuxdelocations.adresse = bati.adresse AND lieuxdelocations.codepostal = bati.codepostal");
			stmt4.setInt(1, idcontrat);
			ResultSet rsLog = stmt4.executeQuery();
			bool = rsLog.next();
			
			String direction = path + "quittance"+numQuit+".docx";
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
			text.setText("Quittance de Loyer du mois de "+numQuit);
			
			XWPFParagraph paragraphe2 = document.createParagraph();
			XWPFRun text2 = paragraphe2.createRun();
			text2.setText("Bailleur : "+ rs.getString("NOM") + " " + rs.getString("PRENOM"));
			text2.addCarriageReturn();
			text2.setText(rs.getString("ADRESSE")+" "+rs.getString("CODEPOSTAL")+" "+rs.getString("VILLE"));
			text2.addCarriageReturn();
			text2.setText("Locataire : "+ rsLoc.getString("NOM") + " " + rsLoc.getString("PRENOM"));
			text2.setText(rsLog.getString("ADRESSE")+" "+rsLog.getString("CODEPOSTAL")+" "+rsLog.getString("VILLE"));
			text2.addCarriageReturn();
			text2.setText("Adresse de la Location :");
			text2.addCarriageReturn();
			text2.setText(rsLog.getString("ADRESSE")+" "+rsLog.getString("CODEPOSTAL")+" "+rsLog.getString("VILLE"));
			text2.addCarriageReturn();
			text2.addCarriageReturn();
			text2.addCarriageReturn();
			text2.setText("Je soussigné "+ rs.getString("NOM") + " " + rs.getString("PRENOM") + " propriétaire du logement "
					+ "désigné ci-dessus, déclare avoir reçu de Monsieur / Madame "
					+ rsLoc.getString("NOM") + " " + rsLoc.getString("PRENOM") +", la somme de " + montRegler + " euros"
					+ ", au titre du paiement du loyer et des charges pour la période de location "
					+ "du "+ dateMiseEffet.getDay() + "/" + date +" au " + dateMiseEffet.getDay() + "/" + (mois+1) + "/"+ annee + " et "
					+ "lui en donne quittance, sous réserve de tous mes droits.");
			
			text2.addCarriageReturn();
			text2.addCarriageReturn();
			text2.setText("Loyer : " + (loyer - charge) + " euros");
			text2.addCarriageReturn();
			text2.setText("Provision Pour charges : " + charge + " euros");
			text2.addCarriageReturn();
			text2.addCarriageReturn();
			text2.setText("Total : " + loyer + " euros");
			text2.addCarriageReturn();
			text2.addCarriageReturn();
			text2.setText("Date du paiement : le " + dateloc.getDay() + "/" + dateloc.getMonth() + "/" + dateloc.getYear());
			text2.addCarriageReturn();
			text2.addCarriageReturn();
			text2.setText("(Signature)");
			
			
			rsLoc.close();
			rsLog.close();
			stmt.close();
			stmt2.close();
			stmt3.close();
			stmt4.close();
			cstm.close();
			rs.close();
			rs2.close();
			stmt.close();
			document.write(fileOut);
			fileOut.close();
			modele.close();
			document.close();
		}
	}
	
}