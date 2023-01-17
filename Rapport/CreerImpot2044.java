package Rapport;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import JDBC.CictOracleDataSource;

public class CreerImpot2044 {
	
	public void generateImpot(int annee) throws IOException, SQLException {
		
		CictOracleDataSource cict = new CictOracleDataSource();
		Connection connex = cict.getConnection();
		try(Statement stmt = connex.createStatement()){
			ResultSet rs = stmt.executeQuery("SELECT * FROM bailleur");
			boolean bool = rs.next();
			String path = rs.getString("DESTINATION");
			String direction = path + "declaration2044.docx";
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
			text.setText("Declaration des revenus CERFA 2044 : Regime Reel");
			
			XWPFParagraph paragraphe2 = document.createParagraph();
			XWPFRun text2 = paragraphe2.createRun();
			
			text2.addCarriageReturn();
			text2.addCarriageReturn();
			text2.setText("Nom et prenoms : ");
			text.addTab();
			text2.setText(rs.getString("NOM") + " " + rs.getString("PRENOM"));
			
			XWPFRun text3 = paragraphe.createRun();
			text3.addCarriageReturn();
			text3.addCarriageReturn();
			text3.addTab();
			text3.addTab();
			text3.addTab();
			text3.addTab();
			text3.setText("Proprietes Rural (Page 2)");
			text3.addCarriageReturn();
			
			//Stock des Locataires + Bati
			ArrayList<String> listeVille = new ArrayList<String>();
			ArrayList<String> listeAdresse = new ArrayList<String>();
			ArrayList<String> listeCode = new ArrayList<String>();
			HashMap<String,String> listeLocataire = new HashMap<String,String>();
			HashMap<String,String> listeObtention = new HashMap<String,String>();
			Statement stmt2 = connex.createStatement();
			ResultSet rs2 = stmt2.executeQuery("SELECT * FROM bati");
			int i=0;
			while (rs2.next()) {
				listeAdresse.add(rs2.getString("ADRESSE"));
				listeVille.add(rs2.getString("VILLE"));
				listeCode.add(rs2.getString("CODEPOSTAL"));
				listeObtention.put(rs2.getString("ADRESSE") + " " + rs2.getString("CODEPOSTAL"), rs2.getString("DATEOBTENTION"));
				PreparedStatement stmt3 = connex.prepareStatement("SELECT distinct locataire.Nom, locataire.Prenom, lieuxdelocations.idlogement"
				+"FROM lieuxdelocations, locataire, loue, contrat, relie"
				+"WHERE loue.idcontrat=contrat.idcontrat AND contrat.idcontrat = relie.idcontrat AND relie.idlocataire = locataire.idlocataire"
				+"AND loue.idlogement=lieuxdelocations.idlogement AND lieuxdelocations.adresse = ? AND lieuxdelocations.codepostal = ?"
				+"AND TO_CHAR(loue.datelocation, 'yyyy') = ?");
				stmt3.setString(1, rs2.getString("ADRESSE"));
				stmt3.setString(2, rs2.getString("CODEPOSTAL"));
				stmt3.setInt(3, annee);
				ResultSet rs3 = stmt3.executeQuery();
				while (rs3.next()) {
					listeLocataire.put(rs3.getString("NOM") + " " + rs3.getString("PRENOM"), listeAdresse.get(i) + " " + listeCode.get(i));
				}
				i++;
			}
			//Tableau des batiments
			XWPFTable tableBati = document.createTable();
			tableBati.addNewCol();
			tableBati.addNewCol();
			tableBati.addNewCol();
			tableBati.getRow(0).getCell(1).setText("Nom et Prenom du locataire");
			tableBati.getRow(0).getCell(2).setText("Date Obtention");
			tableBati.getRow(0).getCell(3).setText("Adresse");
			
			for (int a = 0; a < listeAdresse.size() ;a++) {
				tableBati.createRow();
				tableBati.getRow(a+1).getCell(0).setText("Immeuble " + a+1);
				String locataire = "";
				for (String key : listeLocataire.keySet()) {
					if (listeLocataire.get(key) == listeAdresse.get(a) + " " + listeCode.get(a)) {
						locataire = key;
						listeLocataire.remove(key);
						break;
					}
				}
				tableBati.getRow(i).getCell(1).setText(locataire);
				tableBati.getRow(i).getCell(2).setText(listeObtention.get(listeAdresse.get(a) + " " + listeCode.get(a)));
				tableBati.getRow(i).getCell(3).setText(listeAdresse.get(a) + " " + listeCode.get(a) + " " + listeVille.get(a));
			}
			//Creation tableau des recettes
			XWPFRun text4 = paragraphe.createRun();
			text4.addCarriageReturn();
			text4.addCarriageReturn();
			text4.setText("Tableau des Recettes :");
			text4.addCarriageReturn();
			XWPFTable tableRecette = document.createTable();
			tableRecette.createRow();
			tableRecette.getRow(1).getCell(0).setText("Prime Assurance");
			tableRecette.createRow();
			tableRecette.getRow(2).getCell(0).setText("Depense de reparation, d'entretien (Travaux)");
			tableRecette.createRow();
			tableRecette.getRow(3).getCell(0).setText("Prime Assurance");
			tableRecette.createRow();
			tableRecette.getRow(4).getCell(0).setText("Depense de reparation, d'entretien (Travaux)");
			tableRecette.createRow();
			tableRecette.getRow(5).getCell(0).setText("Taxes foncieres");
			tableRecette.createRow();
			tableRecette.getRow(6).getCell(0).setText("Total des frais et des charges");
			
			float Assur;
			float Trav;
			float TaxeFonc;
			float Total;
			float Recette;
			float Gain;
			for (int a = 0; a < listeAdresse.size() ;a++) {
				tableRecette.addNewCol();
				
				CallableStatement as = connex.prepareCall("{? = CALL MONTANTJURIDIQUEANNNEEBATI(?,?,?)}");
				as.registerOutParameter(1,Types.FLOAT);
				as.setString(2, listeAdresse.get(a));
				as.setString(3, listeCode.get(a));
				as.setInt(4, annee);
				as.executeUpdate();
				Assur = as.getFloat(1);
				
				CallableStatement tr = connex.prepareCall("{? = CALL MONTANTTRAVBATIANNEE(?,?,?)}");
				tr.registerOutParameter(1,Types.FLOAT);
				tr.setString(2, listeAdresse.get(a));
				tr.setString(3, listeCode.get(a));
				tr.setInt(4, annee);
				tr.executeUpdate();
				Trav = tr.getFloat(1);
				
				CallableStatement taxe = connex.prepareCall("{? = CALL MONTANTTAXEANNEEBATI(?,?,?)}");
				taxe.registerOutParameter(1,Types.FLOAT);
				taxe.setString(2, listeAdresse.get(a));
				taxe.setString(3, listeCode.get(a));
				taxe.setInt(4, annee);
				taxe.executeUpdate();
				TaxeFonc = taxe.getFloat(1);
				
				CallableStatement loyer = connex.prepareCall("{? = CALL gainBatiAnnee(?,?,?)}");
				loyer.registerOutParameter(1,Types.FLOAT);
				loyer.setString(2, listeAdresse.get(a));
				loyer.setString(3, listeCode.get(a));
				loyer.setInt(4, annee);
				loyer.executeUpdate();
				Gain = loyer.getFloat(1);
				
				Total = Assur + Trav + TaxeFonc;
				Recette = Gain - Total;
				
				tableRecette.getRow(1).getCell(a+1).setText(""+Gain);
				tableRecette.getRow(2).getCell(a+1).setText(""+Gain);
				tableRecette.getRow(3).getCell(a+1).setText(""+Assur);
				tableRecette.getRow(4).getCell(a+1).setText(""+Trav);
				tableRecette.getRow(5).getCell(a+1).setText(""+TaxeFonc);
				tableRecette.getRow(6).getCell(a+1).setText(""+Total);
				tableRecette.getRow(7).getCell(a+1).setText(""+(Gain));
				tableRecette.getRow(8).getCell(a+1).setText(""+(Gain));
			}
			
			//Tableau Travaux
			XWPFRun text5 = paragraphe.createRun();
			text5.addCarriageReturn();
			text5.addCarriageReturn();
			text5.setText("Tableau des Travaux : ");
			XWPFTable tableTravaux= document.createTable();
			tableTravaux.addNewCol();
			tableTravaux.addNewCol();
			tableTravaux.addNewCol();
			tableTravaux.getRow(0).getCell(0).setText("N�Immeuble et nature des travaux");
			tableTravaux.getRow(0).getCell(1).setText("Nom et adresse entrepreneurs");
			tableTravaux.getRow(0).getCell(2).setText("Date de paiement");
			tableTravaux.getRow(0).getCell(3).setText("Montant");
			for (int a=0; a<listeAdresse.size(); a++) {
				PreparedStatement stmt4 = connex.prepareStatement("SELECT bati.adresse as Badresse, bati.codepostal, travauxpartiescommune.libelle as trav, nom, entreprise.adresse as Eadresse, datepaiement, montantnondeductible"
						+"FROM bati, entreprise, travauxpartiescommune, concernecommun"
						+"WHERE bati.adresse = ? AND bati.codepostal = ? AND bati.adresse = concernecommun.adresse AND bati.codepostal = concernecommun.codepostal"
						+"AND concernecommun.siren = travauxpartiescommune.siren AND concernecommun.numfact = travauxpartiescommune.numfact "
						+"AND entreprise.siren = travauxpartiescommune.siren AND concernecommun.datepaiement IS NOT NULL AND TO_CHAR(concernecommun.datepaiement, 'yyyy') = ?");
				stmt4.setString(1, listeAdresse.get(a));
				stmt4.setString(2, listeCode.get(a));
				stmt4.setInt(3, annee);
				ResultSet rs4 = stmt4.executeQuery();
				int x = 1;
				while (rs4.next()) {
					tableTravaux.createRow();
					tableTravaux.getRow(x).getCell(0).setText(rs4.getString("Badresse")+" "+rs4.getString("trav"));;
					tableTravaux.getRow(x).getCell(0).setText(rs4.getString("nom")+" "+rs4.getString("Eadresse"));;
					tableTravaux.getRow(x).getCell(0).setText(rs4.getString("datepaiement"));
					tableTravaux.getRow(x).getCell(0).setText(rs4.getString("montantnondeductible"));
					x++;
				}
				
				PreparedStatement stmt5 = connex.prepareStatement("SELECT lieuxdelocations.adresse as Badresse, lieuxdelocations.codepostal, travaux.libelle as trav, nom, entreprise.adresse as Eadresse, datefact, montantnondeductible*(1-pourcentagepart) as tot"
						+"FROM lieuxdelocations, entreprise, travaux, concerne"
						+"WHERE lieuxdelocations.adresse = ? AND lieuxdelocations.codepostal = ? AND lieuxdelocations.idlogement = concerne.idlogement"
						+"AND concerne.siren = travaux.siren AND concerne.numfact = travaux.numfact "
						+"AND entreprise.siren = travaux.siren AND concerne.datefact IS NOT NULL AND TO_CHAR(concerne.datefact, 'yyyy') = ?");
				stmt5.setString(1, listeAdresse.get(a));
				stmt5.setString(2, listeCode.get(a));
				stmt5.setInt(3, annee);
				ResultSet rs5 = stmt5.executeQuery();
				while (rs5.next()) {
					tableTravaux.createRow();
					tableTravaux.getRow(x).getCell(0).setText(rs5.getString("Badresse")+" "+rs4.getString("trav"));;
					tableTravaux.getRow(x).getCell(0).setText(rs5.getString("nom")+" "+rs4.getString("Eadresse"));;
					tableTravaux.getRow(x).getCell(0).setText(rs5.getString("datefact"));
					tableTravaux.getRow(x).getCell(0).setText(rs5.getString("tot"));
					x++;
				}
			}
			
			//Tableau Locataire
			XWPFRun text6 = paragraphe.createRun();
			text6.addCarriageReturn();
			text6.addCarriageReturn();
			text6.addTab();
			text6.addTab();
			text6.addTab();
			text6.addTab();
			text6.setText("(Page 6) Autres Locataires :");
			text6.addCarriageReturn();
			text6.setText("Tableau des Locataires :");
			XWPFTable tableLocataire = document.createTable();
			tableLocataire.addNewCol();
			tableLocataire.getRow(0).getCell(0).setText("Locataire");
			tableLocataire.getRow(0).getCell(1).setText("Bati ou se trouve le logement");
			int y=1;
			for (String key : listeLocataire.keySet()) {
				tableLocataire.createRow();
				tableLocataire.getRow(y).getCell(0).setText(key);
				tableLocataire.getRow(y).getCell(1).setText(listeLocataire.get(key));
				y++;
			}
			
			document.write(fileOut);
			fileOut.close();
			modele.close();
			document.close();
		}
	}
	
}
