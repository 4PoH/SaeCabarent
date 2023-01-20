package Requetes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import JDBC.CictOracleDataSource;

public class RequeteInsertion {
	
	
//TOUTES PAGES D'INSERTION

	// GET STRING SIREN FROM ENTREPRISE.NOM
	public static String RequeteGetStringSirenEntrepriseCombo(String nomEnt) throws SQLException  {
		ResultSet retourRequete = null;
		Requete requete = new Requetes.Requete();
		try {
			String texteSQL = ("select SIREN from ENTREPRISE where NOM = '" + nomEnt + "'");
			retourRequete = requete.requeteSelection(texteSQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		retourRequete.next();
		return retourRequete.getString("SIREN");
	}
	
	// GET INT SIREN FROM ENTREPRISE.NOM
	public static int RequeteGetSirenEntrepriseCombo(String nomEnt) throws SQLException  {
		ResultSet retourRequete = null;
		Requete requete = new Requetes.Requete();
		try {
			String texteSQL = ("select SIREN from ENTREPRISE where NOM = '" + nomEnt + "'");
			retourRequete = requete.requeteSelection(texteSQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		retourRequete.next();
		return retourRequete.getInt("SIREN");
	}	
	// REMPLISSAGE DES COMBOBOX ENTREPRISE avec ENTERPRISE.NOM
	public static ResultSet RequeteAfficheComboEntreprise() throws SQLException {
		ResultSet retourRequete = null;
		Requete requete = new Requetes.Requete();
		String texteSQL = "select SIREN, NOM from ENTREPRISE";
		retourRequete = requete.requeteSelection(texteSQL);
		return retourRequete;
	}

	// GET ALL LOGEMENTS FROM ALL BATIS
	public static ResultSet RequeteGetLogFromBati() throws SQLException {
		ResultSet retourRequete = null;
		Requete requete = new Requetes.Requete();
		String texteSQL = "select lieuxdelocations.idlogement as idLog, lieuxdelocations.libelle as libLoc, bati.libelle as libBati, lieuxdelocations.adresse as adr from lieuxdelocations, bati where bati.adresse = lieuxdelocations.adresse and bati.codepostal = lieuxdelocations.codepostal order by 3,2";
		retourRequete = requete.requeteSelection(texteSQL);
		return retourRequete;
	}
		
	// SHOW COMBO LOGEMENT.ADRESSE, CODEPOSTAL, FROM LOGEMENT
	public static ResultSet RequeteAfficheLogement() throws SQLException {
		ResultSet retourRequete = null;
		Requete requete = new Requetes.Requete();
		String texteSQL = "select IDLOGEMENT, ADRESSE, CODEPOSTAL, LIEUXDELOCATIONS.LIBELLE from LIEUXDELOCATIONS order by 2";
		retourRequete = requete.requeteSelection(texteSQL);
		return retourRequete;
	}
	
// PAGES SANS LIAISONS
// NOUVEAU BATI 
	// DB INSERT x8 : STRING INT STRING STRING STRING STRING BOOLEAN STRING
	public static void RequeteInsertBati(String adresse, int code, String ville, String typeHabitation, String anneeConstruction, String listePartieCommune, String copropriete, String dateObtention) throws SQLException {
		CictOracleDataSource cict = new CictOracleDataSource();
		String requete = "{ call insertBati(?,?,?,?,?,?,?,?) } ";		
				try {
					Connection connection = cict.getConnection();
					CallableStatement cs = connection.prepareCall(requete);
					cs.setString(1, adresse);
					cs.setInt(2, code);
					cs.setString(3, ville);
					cs.setString(4, typeHabitation);
			        cs.setString(5, anneeConstruction);
					cs.setString(6, listePartieCommune);					
					cs.setString(7, copropriete);
					cs.setString(8, dateObtention);
					cs.execute();
				} catch(SQLException e) {
					e.printStackTrace();
				}
	}
	// DB INSERT x8 : int String String String String int String int
	public static void RequeteInsertEntreprise(String siren, String nom, String numero, String adresse, String ville, int codepostal, String email, String siret) throws SQLException {
		CictOracleDataSource cict = new CictOracleDataSource();
		String requete = "{ call insertEntreprise(?,?,?,?,?,?,?,?) } ";		
				try {
					Connection connection = cict.getConnection();
					CallableStatement cs = connection.prepareCall(requete);
					cs.setString(1, siren);					
			        cs.setString(2, nom);
					cs.setString(3, numero);
					cs.setString(4, adresse);
					cs.setString(5, ville);
					cs.setInt(6, codepostal);
					cs.setString(7, email);
					cs.setString(8, siret);
					cs.execute();
				} catch(SQLException e) {
					e.printStackTrace();
				}
	}
// NOUVEAU LOCATAIRE
	// DB INSERT x5 : STRING STRING STRING STRING STRING 
	public static void RequeteInsertLocataire( String NOM, String PRENOM, String MAIL, String TEL, String CATESOCIOPROF ) throws SQLException {
		CictOracleDataSource cict = new CictOracleDataSource();
		String requete = "{ call insertLocataire(?,?,?,?,?) } ";
		
				try {
					Connection connection = cict.getConnection();
					CallableStatement cs = connection.prepareCall(requete);
					cs.setString(1, NOM);
					cs.setString(2, PRENOM);
					cs.setString(3, MAIL);
			        cs.setString(4, TEL);
					cs.setString(5, CATESOCIOPROF);
					cs.execute();
				} catch(SQLException e) {
					e.printStackTrace();
				}
	}
	// NOUVELLe LOCATION 
			// DB INSERT x11 : INT INT STRING FLOAT FLOAT FLOAT INT FLOAT FLOAT STRING FLOAT 
			public static void RequeteInsertContrat(int idLog, int idcontr, String dateloc, 
					float loyer, float loyerregler, float pourcentr, 
					float compteur, float charge, String modepaie, float pourelec) throws SQLException {
				CictOracleDataSource cict = new CictOracleDataSource();
				String requete = "{ call insertLoue(?,?,?,?,?,?,?,?,?,?) } ";		
						try {
							Connection connection = cict.getConnection();
							CallableStatement cs = connection.prepareCall(requete);
							cs.setInt(1, idLog);					
					        cs.setInt(2, idcontr);
							cs.setString(3, dateloc);
							cs.setFloat(4, loyer);
							cs.setFloat(5, loyerregler);
							cs.setFloat(6, pourcentr);
							cs.setFloat(7, compteur);
							cs.setFloat(8, charge);
							cs.setString(9, modepaie);
							cs.setFloat(10, pourelec);
							cs.execute();
						} catch(SQLException e) {
							e.printStackTrace();
						}
			}
			
// AVEC LIAISONS 	
//NOUVELLE CHARGE SUPP
	// DB INSERT (CHARGESUPP.SIREN) X (LOUE.DATELOC,IDLOGEMENT,IDCONTRAT) x2 : STRING STRING
	public static void RequeteInsertSupplemente(String idLogement, String idcontrat, String DATELOCATION, int numfact, int siren, float pourcentagepart, String modepaiement, String datepaiement, String numcheque) throws SQLException {
		CictOracleDataSource cict = new CictOracleDataSource();
		String requete = "{ call insertSupplemente(?,?,?,?,?,?,?,?,?) } ";
				try {
					Connection connection = cict.getConnection();
					CallableStatement cs = connection.prepareCall(requete);
					cs.setString(1, idLogement);
					cs.setString(2, idcontrat);
					cs.setString(3, DATELOCATION);
					cs.setInt(4, numfact);
					cs.setInt(5, siren);
					cs.setFloat(6, pourcentagepart);
					cs.setString(7, modepaiement);
					cs.setString(8, datepaiement);
					cs.setString(9, numcheque);
					cs.execute();
				} catch(SQLException e) {
					e.printStackTrace();
				}
	}
	
	// DB INSERT x7 : INT INT STRING FLOAT STRING STRING+STRING
	public static void RequeteInsertChargeSupp(int siren, int numfact, String lib, String datefact, float total,  String chemin, String pdf ) throws SQLException {
		CictOracleDataSource cict = new CictOracleDataSource();
		String requete = "{ call insertChargeSup(?,?,?,?,?,?,?) } ";
				try {
					Connection connection = cict.getConnection();
					CallableStatement cs = connection.prepareCall(requete);
					cs.setInt(1, siren);
					cs.setInt(2, numfact);
					cs.setString(3, lib);					
			        cs.setString(4, datefact);
			        cs.setFloat(5, total);
					cs.setString(6, chemin);
					cs.setString(7, pdf);
					cs.execute();
				} catch(SQLException e) {
					e.printStackTrace();
				}
	}
	
	// GET ALL LOYERS FOR THE CURRENT MONTH
	public static ResultSet RequeteGetLogLocBati() throws SQLException {
		ResultSet retourRequete = null;
		Requete requete = new Requetes.Requete();
		String texteSQL = "SELECT distinct loue.DATELOCATION as dateLoc, bati.libelle as lib, locataire.nom as nom, bati.adresse as adr,  bati.codepostal as cp, LIEUXDELOCATIONS.IDLOGEMENT, contrat.idcontrat from relie, locataire, contrat, dual, loue, LIEUXDELOCATIONS, bati\n"
				+ "where loue.IDLOGEMENT = LIEUXDELOCATIONS.IDLOGEMENT\n"
				+ "and lieuxdelocations.adresse = bati.adresse\n"
				+ "and lieuxdelocations.codepostal = bati.codepostal\n"
				+ "and loue.idcontrat = contrat.idcontrat\n"
				+ "and contrat.idcontrat = relie.idcontrat\n"
				+ "and relie.idlocataire = locataire.idlocataire\n"
				+ "and loue.DATELOCATION >= (CURRENT_DATE-(30*1))";
		retourRequete = requete.requeteSelection(texteSQL);
		return retourRequete;
	}
 
// NOUVEAU CONTRAT
	// DB INSERT LOCATAIRExCONTRAT x2 : STRING STRING
	public static void RequeteInsertRelie(String idLoc,String idContrat) throws SQLException {
		CictOracleDataSource cict = new CictOracleDataSource();
		String requete = "{ call insertRelie(?,?) } ";
		
				try {
					Connection connection = cict.getConnection();
					CallableStatement cs = connection.prepareCall(requete);
					cs.setString(1, idLoc);
					cs.setString(2, idContrat);
					cs.execute();
				} catch(SQLException e) {
					e.printStackTrace();
				}
	}

	// GET LAST ID CONTRAT
	public static String RequeteLastIDContrat() throws SQLException {
		ResultSet retourRequete = null;
		Requete requete = new Requetes.Requete();
		String texteSQL = "select max(IDCONTRAT) a from CONTRAT ";
		retourRequete = requete.requeteSelection(texteSQL);
		retourRequete.next();
		String result = retourRequete.getString("a");
		return result;
	}
	
	// DB INSERT CONTRAT  x3 : STRING FLOAT STRING
	public static void RequeteInsertContrat(String DATEMISEENEFFET, float CAUTION, String TYPELOC) throws SQLException {
		CictOracleDataSource cict = new CictOracleDataSource();
		String requete = "{ call insertContrat(?,?,?) } ";		
				try {
					Connection connection = cict.getConnection();
					CallableStatement cs = connection.prepareCall(requete);
					cs.setString(1, DATEMISEENEFFET);					
			        cs.setFloat(2, CAUTION);
					cs.setString(3, TYPELOC);
					cs.execute();
				} catch(SQLException e) {
					e.printStackTrace();
				}
	}
	
	//GET LOCATAIRE ID, NOM 
	public static ResultSet RequeteGetLocataire() throws SQLException {
		ResultSet retourRequete = null;
		Requete requete = new Requetes.Requete();
		String texteSQL = "SELECT LOCATAIRE.IDLOCATAIRE as idloc, LOCATAIRE.NOM as locNom, LOCATAIRE.PRENOM as locPrenom from LOCATAIRE order by 2";
		retourRequete = requete.requeteSelection(texteSQL);
		return retourRequete;
	}
	// SHOW COMBO NOM PRENOM LOCATAIRE
	private ResultSet RequeteAfficheComboLocataire() throws SQLException {
		ResultSet retourRequete = null;
		Requete requete = new Requetes.Requete();
		String texteSQL = "select NOM, PRENOM, IDLOCATAIRE from LOCATAIRE order by 1";
		retourRequete = requete.requeteSelection(texteSQL);
		return retourRequete;
	}	

// NOUVEAU DIAGNOSTICS	
	// DB INSERT x8 : STRING INT STRING STRING STRING STRING BOOLEAN STRING
	public static void RequeteInsertBati(int numSiren, String numRef,String nomDiag, String dateObtention, String dateFinva, String numRapp, String direction, String nomPdf, int idLog) throws SQLException {
		CictOracleDataSource cict = new CictOracleDataSource();
		String requete = "{ call insertDiagnostic(?,?,?,?,?,?,?,?,?) } ";		
				try {
					Connection connection = cict.getConnection();
					CallableStatement cs = connection.prepareCall(requete);
					cs.setInt(1, numSiren);
					cs.setString(2, numRef);
					cs.setString(3, nomDiag);
					cs.setString(4, dateObtention);
			        cs.setString(5, dateFinva);
					cs.setString(6, numRapp);					
					cs.setString(7, direction);
					cs.setString(8, nomPdf);
					cs.setInt(9, idLog);
					cs.execute();
				} catch(SQLException e) {
					e.printStackTrace();
				}
	}
	
// NOUVEAU DOCUMENT DE CONTRAT
	// SHOW COMBO * CONTRAT.DATE,TYPE WHERE DEPART IS NULL
	public static ResultSet RequeteAfficheComboContrat() throws SQLException {
		ResultSet retourRequete = null;
		Requete requete = new Requetes.Requete();
		String texteSQL = "select IDCONTRAT, DATEMISEENEFFET, TYPELOC from CONTRAT where DATEDEPART is null";
		retourRequete = requete.requeteSelection(texteSQL);
		return retourRequete;
	}
	
	// DB INSERT x6 : INT INT INT STRING STRING+STRING
	public static void RequeteInsertDocumentContrat(int idcontrat, String libelle, String chemin, String pdf, String date) throws SQLException {
		CictOracleDataSource cict = new CictOracleDataSource();
		String requete = "{ call insertDocumentContrat(?,?,?,?,?) } ";		
				try {
					Connection connection = cict.getConnection();
					CallableStatement cs = connection.prepareCall(requete);
					cs.setInt(1, idcontrat);
					cs.setString(2, libelle);
					cs.setString(3, chemin);
			        cs.setString(4, pdf);
					cs.setString(5, date);
					cs.execute();
				} catch(SQLException e) {
					e.printStackTrace();
				}
	}
	
// NOUVEL ENTRETIEN

	// DB INSERT (BATI.ADRESSE, BATI.CP) x (FACTURE.NUMFAC, SIREN) x2 : STRING STRING
	public static void RequeteInsertConcerneEntretien(String adresse, String codePostal, int siren, int numfact,  String datefact, String modepaiement, String numcheque) throws SQLException {
		CictOracleDataSource cict = new CictOracleDataSource();
		String requete = "{ call insertConcerneEntretien(?,?,?,?,?,?,?) } ";
				try {
					Connection connection = cict.getConnection();
					CallableStatement cs = connection.prepareCall(requete);
					cs.setString(1, adresse);
					cs.setString(2, codePostal);
					cs.setInt(3, siren);
					cs.setInt(4, numfact);
					cs.setString(5, datefact);
					cs.setString(6, modepaiement);
					cs.setString(7, numcheque);
					cs.execute();
				} catch(SQLException e) {
					e.printStackTrace();
				}
	}
	
	// GET ALL BATI
	public static ResultSet RequeteGetBati() throws SQLException {
		ResultSet retourRequete = null;
		Requete requete = new Requetes.Requete();
		String texteSQL = "select libelle as lib, codepostal as cp, adresse as adr from bati order by 2";
		retourRequete = requete.requeteSelection(texteSQL);
		return retourRequete;
	}
	
	// DB INSERT x6 : INT INT INT STRING STRING+STRING
	public static void RequeteInsertEntretienPartisCommune(int siren, int numfact, float total, String datefact, String chemin, String pdf ) throws SQLException {
		CictOracleDataSource cict = new CictOracleDataSource();
		String requete = "{ call insertEntretienPartisCommune(?,?,?,?,?,?) } ";
		
				try {
					Connection connection = cict.getConnection();
					CallableStatement cs = connection.prepareCall(requete);
					cs.setInt(1, siren);
					cs.setInt(2, numfact);
					cs.setFloat(3, total);
			        cs.setString(4, datefact);
					cs.setString(5, chemin);
					cs.setString(6, pdf);
					cs.execute();
				} catch(SQLException e) {
					e.printStackTrace();
				}
	}
	

	// SHOW COMBO CODEPOSTAL, ADRESSE FROM BATIMENT
	public static ResultSet RequeteAfficheComboBatiment() throws SQLException {
		ResultSet retourRequete = null;
		Requete requete = new Requetes.Requete();
		String texteSQL = "select CODEPOSTAL, ADRESSE from BATI order by 1";
		retourRequete = requete.requeteSelection(texteSQL);
		return retourRequete;
	}
	
	// SHOW COMBO CODEPOSTAL, ADRESSE FROM BATIMENT
	public String RequeteGetCPWithAdr(String adresse) throws SQLException  {
		ResultSet retourRequete = null;
		Requete requete = new Requetes.Requete();
		try {
			String texteSQL = ("select CODEPOSTAL from BATI where ADRESSE = '" + adresse + "'");
			retourRequete = requete.requeteSelection(texteSQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		retourRequete.next();
		return retourRequete.getString("CODEPOSTAL");
	}
	
		
	// DB INSERT x8 : FLOAT INT STRING STRING STRING STRING STRING INT
	public static void RequeteInsertLieuLocation(float surfaceLieux,int nbPiece, String typeReseaux, String libelle, String modChauffage, String modProdEau, String adresseLieux,int codepostalLieux) throws SQLException {
		CictOracleDataSource cict = new CictOracleDataSource();
		String requete = "{ call insertLieuxDeLocations(?,?,?,?,?,?,?,?) } ";
				try {
					Connection connection = cict.getConnection();
					CallableStatement cs = connection.prepareCall(requete);
					cs.setFloat(1, surfaceLieux);
					cs.setInt(2, nbPiece);
					cs.setString(3, typeReseaux);
			        cs.setString(4, libelle);
					cs.setString(5, modChauffage);
					cs.setString(6, modProdEau);
					cs.setString(7, adresseLieux);
					cs.setInt(8, codepostalLieux);
					cs.execute();
				} catch(SQLException e) {
					e.printStackTrace();
				}
	}
	
// NOUVEAUX TRAVAUX PARTIES COMMUNES
	// DB INSERT (BATI.ADRESSE, BATI.CP) x (FACTURE.NUMFAC, SIREN) x2 : STRING STRING
	public static void RequeteInsertConcerneCommun(String adresse, String codePostal, String siren, int numfact,  String datefact, String modepaiement, String numcheque) throws SQLException {
		CictOracleDataSource cict = new CictOracleDataSource();
		String requete = "{ call insertConcerneCommun(?,?,?,?,?,?,?) } ";
		
				try {
					Connection connection = cict.getConnection();
					CallableStatement cs = connection.prepareCall(requete);
					cs.setString(1, adresse);
					cs.setString(2, codePostal);
					cs.setString(3, siren);
					cs.setInt(4, numfact);
					cs.setString(5, datefact);
					cs.setString(6, modepaiement);
					cs.setString(7, numcheque);
					cs.execute();
				} catch(SQLException e) {
					e.printStackTrace();
				}
	}	
	
	// DB INSERT x12 : INT INT INT STRING STRING STRING STRING FLOAT FLOAT FLOAT STRING STRING
	public static void RequeteInsertTravauxCommuns(String numSiren, int numFacture,int numeroDevis, String lib, String dateD, String dateF, String details, float montantTrav, float montantNondeduc, float reduc, String direction, String nomPdf) throws SQLException {
		CictOracleDataSource cict = new CictOracleDataSource();
		String requete = "{ call insertTravauxPartiesCommune(?,?,?,?,?,?,?,?,?,?,?,?) } ";		
				try {
					Connection connection = cict.getConnection();
					CallableStatement cs = connection.prepareCall(requete);
					cs.setString(1, numSiren);
					cs.setInt(2, numFacture);
					cs.setInt(3, numeroDevis);
			        cs.setString(4, lib);
					cs.setString(5, dateD);
					cs.setString(6, dateF);
					cs.setString(7, details);
					cs.setFloat(8, montantTrav);
					cs.setFloat(9, montantNondeduc);
					cs.setFloat(10, reduc);
					cs.setString(11, direction);
					cs.setString(12, nomPdf);
					cs.execute();
				} catch(SQLException e) {
					e.printStackTrace();
				}
	}
	
//NOUVEAUX TRAVAUX

	// DB INSERT (LOGEMENT.IDLOGEMENT) x (FACTURE.NUMFAC, SIREN) x2 : STRING STRING
	public static void RequeteInsertConcerne(String idlogement, String siren, int numfact, float pourcentagepart,  String datefact, String modepaiement, String numcheque) throws SQLException {
		CictOracleDataSource cict = new CictOracleDataSource();
		String requete = "{ call insertConcerne(?,?,?,?,?,?,?) } ";
				try {
					Connection connection = cict.getConnection();
					CallableStatement cs = connection.prepareCall(requete);
					cs.setString(1, idlogement);
					cs.setString(2, siren);
					cs.setInt(3, numfact);
					cs.setFloat(4, pourcentagepart);
					cs.setString(5, datefact);
					cs.setString(6, modepaiement);
					cs.setString(7, numcheque);
					cs.execute();
				} catch(SQLException e) {
					e.printStackTrace();
				}
	}
	
	
	// DB INSERT x12 : INT INT INT STRING STRING STRING STRING FLOAT FLOAT FLOAT STRING STRING
	public static void RequeteInsertTravaux(String numSiren, int numFacture,int numeroDevis, String lib, String dateD, String dateF, String details, float montantTrav, float montantNondeduc, float reduc, String direction, String nomPdf) throws SQLException {
		CictOracleDataSource cict = new CictOracleDataSource();
		String requete = "{ call insertTravaux(?,?,?,?,?,?,?,?,?,?,?,?) } ";		
				try {
					Connection connection = cict.getConnection();
					CallableStatement cs = connection.prepareCall(requete);
					cs.setString(1, numSiren);
					cs.setInt(2, numFacture);
					cs.setInt(3, numeroDevis);
			        cs.setString(4, lib);
					cs.setString(5, dateD);
					cs.setString(6, dateF);
					cs.setString(7, details);
					cs.setFloat(8, montantTrav);
					cs.setFloat(9, montantNondeduc);
					cs.setFloat(10, reduc);
					cs.setString(11, direction);
					cs.setString(12, nomPdf);
					cs.execute();
				} catch(SQLException e) {
					e.printStackTrace();
				}
	}
	
//NOUVELLE FACTURE D EAU
	// DB INSERT (BATI.ADRESSE, BATI.CP) x (FACTURE.NUMFAC, SIREN) x2 : STRING STRING
	public static void RequeteInsertRattacher(String adresse, String siren, int numfact, String codePostal, String datefact, String modepaiement, String numcheque) throws SQLException {
		
		CictOracleDataSource cict = new CictOracleDataSource();
		String requete = "{ call insertRattacher(?,?,?,?,?,?,?) } ";
		
				try {
					Connection connection = cict.getConnection();
					CallableStatement cs = connection.prepareCall(requete);
					cs.setString(1, adresse);
					cs.setString(2, siren);
					cs.setInt(3, numfact);
					cs.setString(4, codePostal);
					cs.setString(5, datefact);
					cs.setString(6, modepaiement);
					cs.setString(7, numcheque);
					cs.execute();
				} catch(SQLException e) {
					e.printStackTrace();
				}
	}
	

	

	// DB INSERT x8 : INT INT FLOAT STRING FLOAT FLOAT STRING+STRING INT
	public static void RequeteInsertFacEau(String siren, int numfact, float prixm3, String datefact, float partiefixe, float total, String chemin, String pdf, int frequencefacture ) throws SQLException {
		CictOracleDataSource cict = new CictOracleDataSource();
		String requete = "{ call insertFactureEau(?,?,?,?,?,?,?,?,?) } ";		
				try {
					Connection connection = cict.getConnection();
					CallableStatement cs = connection.prepareCall(requete);
					cs.setString(1, siren);
					cs.setInt(2, numfact);
					cs.setFloat(3, prixm3);
			        cs.setString(4, datefact);
			        cs.setFloat(5, partiefixe);
			        cs.setFloat(6, total);
					cs.setString(7, chemin);
					cs.setString(8, pdf);
					cs.setInt(9, frequencefacture);
					cs.execute();
				} catch(SQLException e) {
					e.printStackTrace();
				}
	}
	
	
// NOUVELLE FACTURE D ELECTRICITE
	// DB INSERT (BATI.ADRESSE, BATI.CP) x (FACTURE.NUMFAC, SIREN) x2 : STRING STRING
	public static void RequeteInsertRelie(String adr, String cp, String numSiren,String numFacture,String datePaiement, String modePaiement, String numeroCheque) throws SQLException {
		CictOracleDataSource cict = new CictOracleDataSource();
		String requete = "{ call insertConcerneElectrique(?,?,?,?,?,?,?) } ";
		
				try {
					Connection connection = cict.getConnection();
					CallableStatement cs = connection.prepareCall(requete);
					cs.setString(1, adr);
					cs.setString(2, cp);
					cs.setString(3, numSiren);
					cs.setString(4, numFacture);
					cs.setString(5, datePaiement);
					cs.setString(6, modePaiement);
					cs.setString(7, numeroCheque);
					cs.execute();
				} catch(SQLException e) {
					e.printStackTrace();
				}
	}	
	// DB INSERT x2 : STRING STRING
		public static void RequeteInsertFacElec(String siren, String numfact, float total, String datefact, String chemin, String pdf ) throws SQLException {
			CictOracleDataSource cict = new CictOracleDataSource();
			String requete = "{ call insertFactureElectrique(?,?,?,?,?,?) } ";
			
					try {
						Connection connection = cict.getConnection();
						CallableStatement cs = connection.prepareCall(requete);
						cs.setString(1, siren);
						cs.setString(2, numfact);
						cs.setFloat(3, total);
				        cs.setString(4, datefact);
						cs.setString(5, chemin);
						cs.setString(6, pdf);
						cs.execute();
					} catch(SQLException e) {
						e.printStackTrace();
					}
		}
		
	// NOUVELLE PROTECTION JURIDIQUE
		// DB INSERT (BATI.ADRESSE, BATI.CP) x (PROTEC.NUMCONTRAT, SIREN) x2 : STRING STRING
		public static void RequeteInsertConcerneProtec(String adresse,String codePostal, int siren, int numcontrat,  float quotitejuris,String datefact, String modepaiement, String numcheque) throws SQLException {
			CictOracleDataSource cict = new CictOracleDataSource();
			String requete = "{ call insertConcerneProtection(?,?,?,?,?,?,?,?) } ";	
			try {
						Connection connection = cict.getConnection();
						CallableStatement cs = connection.prepareCall(requete);
						cs.setString(1, adresse);
						cs.setString(2, codePostal);
						cs.setInt(3, siren);
						cs.setInt(4, numcontrat);
						cs.setFloat(5, quotitejuris);
						cs.setString(6, datefact);
						cs.setString(7, modepaiement);
						cs.setString(8, numcheque);
						cs.execute();
					} catch(SQLException e) {
						e.printStackTrace();
					}
		}

			// DB INSERT x6 : INT INT FLOAT FLOAT STRING+STRING
			public static void RequeteInsertFacElec(int siren, int numeroContr, float primeProtec, float primeJuriProtec, String chemin, String pdf, String date ) throws SQLException {
				CictOracleDataSource cict = new CictOracleDataSource();
				String requete = "{ call insertProtectionJuridique(?,?,?,?,?,?,?) } ";
				
						try {
							Connection connection = cict.getConnection();
							CallableStatement cs = connection.prepareCall(requete);
							cs.setInt(1, siren);
							cs.setInt(2, numeroContr);
							cs.setFloat(3, primeProtec);
					        cs.setFloat(4, primeJuriProtec);
							cs.setString(5, chemin);
							cs.setString(6, pdf);
							cs.setString(7, date);
							cs.execute();
						} catch(SQLException e) {
							e.printStackTrace();
						}
			}

// NOUVELLE TAXE FONCIERE			
			// DB INSERT (BATI.ADRESSE, BATI.CP) x (AVIS.REFAVIS) x2 : STRING STRING
			public static void RequeteInsertRattacher(String adresse, String codePostal, String refAvis,  String datefact, String modepaiement, String numcheque) throws SQLException {
				CictOracleDataSource cict = new CictOracleDataSource();
				String requete = "{ call insertAttacher(?,?,?,?,?,?) } ";
				
						try {
							Connection connection = cict.getConnection();
							CallableStatement cs = connection.prepareCall(requete);
							cs.setString(1, adresse);
							cs.setString(2, codePostal);
							cs.setString(3, refAvis);
							cs.setString(4, datefact);
							cs.setString(5, modepaiement);
							cs.setString(6, numcheque);
							cs.execute();
						} catch(SQLException e) {
							e.printStackTrace();
						}
			}					
					// DB INSERT x6 : STRING STRING FLOAT FLOAT STRING STRING			
					public static void RequeteInsertTaxFonc(String refAvisTaxeF, String anneeTaxeF, float totalOrdureTaxeF, float totalTaxeF, String direct, String nom) throws SQLException {
						CictOracleDataSource cict = new CictOracleDataSource();
						String requete = "{ call insertTaxeFonciere(?,?,?,?,?,?) } ";				
								try {
									Connection connection = cict.getConnection();
									CallableStatement cs = connection.prepareCall(requete);
									cs.setString(1, refAvisTaxeF);
									cs.setString(2, anneeTaxeF);
									cs.setFloat(3, totalOrdureTaxeF);
							        cs.setFloat(4, totalTaxeF);
									cs.setString(5, direct);
									cs.setString(6, nom);
									cs.execute();
								} catch(SQLException e) {
									e.printStackTrace();
								}
					}
}