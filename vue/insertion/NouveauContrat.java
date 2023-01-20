package vue.insertion;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import JDBC.CictOracleDataSource;
import Requetes.Requete;
import vue.Accueil;
import vue.IRL;
import vue.InformationsBailleur;
import vue.Quittances;
import vue.consultation.ChargesSupplementaires;
import vue.consultation.EntretiensPartiesAnciens;
import vue.consultation.FacturesEauAPayees;
import vue.consultation.FacturesEauPayees;
import vue.consultation.FacturesElectriciteAPayees;
import vue.consultation.FacturesElectricitePayees;
import vue.consultation.Impositions;
import vue.consultation.LocatairesAnciens;
import vue.consultation.LocatairesEnCours;
import vue.consultation.LocationsAnciennes;
import vue.consultation.LocationsEnCours;
import vue.consultation.ProtectionJuridique;
import vue.consultation.TaxeFonciere;
import vue.consultation.TravauxAnciens;
import vue.consultation.TravauxEnCours;

public class NouveauContrat extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField textFieldDate;
	private JTextField textFieldCaution;
	protected JLabel LabelLibelleNbLocataire;
	protected JLabel LabelLibelleLocataire;
	protected JCheckBox chckbxColocataire ;
	protected JButton ButtonColocationPlus;
	protected JButton ButtonColocationMoins;
	private int nbLocataire = 1;
	private JComboBox<String> comboBoxTypeLocation;
	private JComboBox<String> comboBoxLocataire;
	private String locataire;
	private String idLocataire;
	private boolean locataireSelected;
	private JComboBox<String> comboBoxLocataire2;
	private String locataire2;
	private String idLocataire2;
	private boolean locataireSelected2;
	private JComboBox<String> comboBoxLocataire3;
	private String locataire3;
	private String idLocataire3;
	private boolean locataireSelected3;
	private JComboBox<String> comboBoxLocataire4;
	private String locataire4;
	private String idLocataire4;
	private boolean locataireSelected4;
	private JComboBox<String> comboBoxLocataire5;
	private String locataire5;
	private String idLocataire5;
	private boolean locataireSelected5;
	private JComboBox<String> comboBoxDocuments;
	private String typeLocation;
	protected NouveauContrat frame;
	private int nbTableRows;
	private ArrayList<String> tableAllSelectedData = new ArrayList<String>();
	private String lastIDContrat;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NouveauContrat frame = new NouveauContrat();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	private void RequeteInsertRelie(String idLoc,String idContrat) throws SQLException {
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
	private String RequeteLastIDContrat() throws SQLException {
		ResultSet retourRequete = null;
		Requete requete = new Requetes.Requete();
		String texteSQL = "select max(IDCONTRAT) from CONTRAT ";
		retourRequete = requete.requeteSelection(texteSQL);
		String result = retourRequete.getString("IDCONTRAT");
		return result;
	}
	
	private ResultSet RequeteGetLocataire() throws SQLException {
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
	// SHOW COMBO NOM PRENOM LOCATAIRE2
	private ResultSet RequeteAfficheComboLocataire2() throws SQLException {
		ResultSet retourRequete = null;
		Requete requete = new Requetes.Requete();
		String texteSQL = "select NOM, PRENOM, IDLOCATAIRE from LOCATAIRE where IDLOCATAIRE !=" + idLocataire + "order by 1";
		retourRequete = requete.requeteSelection(texteSQL);
		return retourRequete;
	}
	// SHOW COMBO NOM PRENOM LOCATAIRE3
	private ResultSet RequeteAfficheComboLocataire3() throws SQLException {
		ResultSet retourRequete = null;
		Requete requete = new Requetes.Requete();
		String texteSQL = "select NOM, PRENOM, IDLOCATAIRE from LOCATAIRE where IDLOCATAIRE !=" + idLocataire + " AND IDLOCATAIRE !=" + idLocataire2 + "order by 1";
		retourRequete = requete.requeteSelection(texteSQL);
		return retourRequete;
	}
	// SHOW COMBO NOM PRENOM LOCATAIRE4
	private ResultSet RequeteAfficheComboLocataire4() throws SQLException {
		ResultSet retourRequete = null;
		Requete requete = new Requetes.Requete();
		String texteSQL = "select NOM, PRENOM, IDLOCATAIRE from LOCATAIRE where IDLOCATAIRE !=" + idLocataire + " AND IDLOCATAIRE !=" + idLocataire2 + " AND IDLOCATAIRE !=" + idLocataire3 +"order by 1";
		retourRequete = requete.requeteSelection(texteSQL);
		return retourRequete;
	}
	// SHOW COMBO NOM PRENOM LOCATAIRE5
	private ResultSet RequeteAfficheComboLocataire5() throws SQLException {
		ResultSet retourRequete = null;
		Requete requete = new Requetes.Requete();
		String texteSQL = "select NOM, PRENOM, IDLOCATAIRE from LOCATAIRE where IDLOCATAIRE !=" + idLocataire + " AND IDLOCATAIRE !=" + idLocataire2 + " AND IDLOCATAIRE !=" + idLocataire3 + " AND IDLOCATAIRE !=" + idLocataire4 + "order by 1";
		retourRequete = requete.requeteSelection(texteSQL);
		return retourRequete;
	}
	
	
	// SHOW COMBO DOCUMENTS
	private ResultSet RequeteAfficheComboDocuments() throws SQLException {
		ResultSet retourRequete = null;
		Requete requete = new Requetes.Requete();
		String texteSQL = "select LIBELLE, DATEDOCUMENT from DOCUMENTCONTRAT order by 2 desc";
		retourRequete = requete.requeteSelection(texteSQL);
		return retourRequete;
	}

	// DB INSERT x3 : STRING FLOAT STRING
	private void RequeteInsertContrat(String DATEMISEENEFFET, float CAUTION, String TYPELOC) throws SQLException {
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
	
	public NouveauContrat() {
		setTitle("Nouveau contrat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 480, 480);
		
		JMenuBar menuBarTop = new JMenuBar();
		menuBarTop.setMargin(new Insets(5, 5, 5, 5));
		setJMenuBar(menuBarTop);
		
		JButton ButtonAccueil = new JButton("Accueil");
		ButtonAccueil.addActionListener(this);
		menuBarTop.add(ButtonAccueil);
		
		JMenu MenuLocations = new JMenu("Locations");
		MenuLocations.addActionListener(this);
		menuBarTop.add(MenuLocations);
		
		JMenuItem MenuItemAncienneLocation = new JMenuItem("Anciennes locations");
		MenuItemAncienneLocation.addActionListener(this);
		MenuLocations.add(MenuItemAncienneLocation);
		
		JMenuItem MenuItemLocationEnCour = new JMenuItem("Locations en cours");
		MenuItemLocationEnCour.addActionListener(this);
		MenuLocations.add(MenuItemLocationEnCour);
		
		JMenuItem MenuItemNouvelleLocation = new JMenuItem("Nouvelles locations");
		MenuItemNouvelleLocation.addActionListener(this);
		MenuLocations.add(MenuItemNouvelleLocation);
		
		JMenuItem MenuItemAnciensLocataires = new JMenuItem("Anciens locataires");
		MenuItemAnciensLocataires.addActionListener(this);
		MenuLocations.add(MenuItemAnciensLocataires);
		
		JMenuItem MenuItemLocatairesEnCours = new JMenuItem("Locataires en cours");
		MenuItemLocatairesEnCours.addActionListener(this);
		MenuLocations.add(MenuItemLocatairesEnCours);
		
		JMenu MenuCharges = new JMenu("Charges");
		MenuCharges.addActionListener(this);
		menuBarTop.add(MenuCharges);
		
		JMenu MenuEntretiens = new JMenu("Entretiens");
		MenuEntretiens.addActionListener(this);
		MenuCharges.add(MenuEntretiens);
		
		JMenuItem MenuItemNouveauxEntretiens = new JMenuItem("Nouveaux entretiens des parties communes");
		MenuItemNouveauxEntretiens.addActionListener(this);
		
		JMenuItem MenuItemAnciensEntretiensPartiesCommunes = new JMenuItem("Entretiens des parties communes");
		MenuItemAnciensEntretiensPartiesCommunes.addActionListener(this);
		MenuItemAnciensEntretiensPartiesCommunes.setSelected(true);
		MenuEntretiens.add(MenuItemAnciensEntretiensPartiesCommunes);
		MenuItemNouveauxEntretiens.setSelected(true);
		MenuEntretiens.add(MenuItemNouveauxEntretiens);
		
		JMenu MenuFacturesEau = new JMenu("Factures d'eau");
		MenuFacturesEau.addActionListener(this);
		MenuCharges.add(MenuFacturesEau);
		
		JMenuItem MenuItemAnciennesFacturesEau = new JMenuItem("Factures d'eau payées");
		MenuItemAnciennesFacturesEau.addActionListener(this);
		MenuFacturesEau.add(MenuItemAnciennesFacturesEau);
		
		JMenuItem MenuItemFacturesEauEnCours = new JMenuItem("Factures d'eau à payées");
		MenuItemFacturesEauEnCours.addActionListener(this);
		MenuFacturesEau.add(MenuItemFacturesEauEnCours);
		
		JMenuItem MenuItemNouvellesFactureEau = new JMenuItem("Nouvelles factures d'eau");
		MenuItemNouvellesFactureEau.addActionListener(this);
		MenuFacturesEau.add(MenuItemNouvellesFactureEau);
		
		JMenu MenuElectricite = new JMenu("Electricité");
		MenuElectricite.addActionListener(this);
		MenuCharges.add(MenuElectricite);
		
		JMenuItem MenuItemAnciennesFacturesElectricite = new JMenuItem("Factures d'électricité payées");
		MenuItemAnciennesFacturesElectricite.addActionListener(this);
		MenuElectricite.add(MenuItemAnciennesFacturesElectricite);
		
		JMenuItem mntmFacturesDlectricitEn = new JMenuItem("Factures d'électricité à payées");
		mntmFacturesDlectricitEn.addActionListener(this);
		MenuElectricite.add(mntmFacturesDlectricitEn);
		
		JMenuItem MenuItemNouvellesFacturesElectricite = new JMenuItem("Nouvelles factures d'électricité");
		MenuElectricite.add(MenuItemNouvellesFacturesElectricite);
		MenuItemNouvellesFacturesElectricite.addActionListener(this);
		
		JMenu MenuTaxesFoncieres = new JMenu("Taxes foncières");
		MenuTaxesFoncieres.addActionListener(this);
		MenuCharges.add(MenuTaxesFoncieres);
		
		JMenuItem MenuItemConsultationTaxesFonciere = new JMenuItem("Consultation taxes foncières");
		MenuItemConsultationTaxesFonciere.addActionListener(this);
		MenuTaxesFoncieres.add(MenuItemConsultationTaxesFonciere);
		
		JMenuItem MenuItemNouvellesTaxesFonciere = new JMenuItem("Nouvelles taxes foncières");
		MenuItemNouvellesTaxesFonciere.addActionListener(this);
		MenuTaxesFoncieres.add(MenuItemNouvellesTaxesFonciere);
		
		JMenu MenuProtectionJuridique = new JMenu("Protection juridique");
		MenuProtectionJuridique.addActionListener(this);
		MenuCharges.add(MenuProtectionJuridique);
		
		JMenuItem MenuItemConsultationProtectionsJuridiques = new JMenuItem("Consultation protection juridique");
		MenuProtectionJuridique.add(MenuItemConsultationProtectionsJuridiques);
		MenuItemConsultationProtectionsJuridiques.addActionListener(this);
		
		JMenuItem MenuItemNouvelleProtectionJuridique = new JMenuItem("Nouvelle protection juridique");
		MenuItemNouvelleProtectionJuridique.addActionListener(this);
		MenuProtectionJuridique.add(MenuItemNouvelleProtectionJuridique);
		
		JMenu MenuChargesSupplementaires = new JMenu("Charges supplémentaires");
		MenuChargesSupplementaires.addActionListener(this);
		MenuCharges.add(MenuChargesSupplementaires);
		
		JMenuItem MenuItemConsultationChargesSupplmentaires = new JMenuItem("Consultation charges supplémentaires");
		MenuItemConsultationChargesSupplmentaires.addActionListener(this);
		MenuChargesSupplementaires.add(MenuItemConsultationChargesSupplmentaires);
		
		JMenuItem MenuItemNouvelleChargesSupplmentaires = new JMenuItem("Nouvelle charges supplémentaires");
		MenuItemNouvelleChargesSupplmentaires.addActionListener(this);
		MenuChargesSupplementaires.add(MenuItemNouvelleChargesSupplmentaires);
		
		JMenu MenuTravaux = new JMenu("Travaux");
		menuBarTop.add(MenuTravaux);
		
		JMenuItem MenuItemAncienTravaux = new JMenuItem("Anciens travaux");
		MenuItemAncienTravaux.addActionListener(this);
		MenuTravaux.add(MenuItemAncienTravaux);
		
		JMenuItem MenuItemTravauxEnCours = new JMenuItem("Travaux en cours");
		MenuItemTravauxEnCours.addActionListener(this);
		MenuTravaux.add(MenuItemTravauxEnCours);
		
		JMenuItem MenuItemNouveauTravaux = new JMenuItem("Nouveaux travaux");
		MenuItemNouveauTravaux.addActionListener(this);
		MenuTravaux.add(MenuItemNouveauTravaux);
		
		JMenu MenuParametres = new JMenu("Paramètres");
		menuBarTop.add(MenuParametres);
		
		JMenuItem MenuItemInfosBailleur = new JMenuItem("Informations bailleur");
		MenuItemInfosBailleur.addActionListener(this);
		MenuParametres.add(MenuItemInfosBailleur);
		
		JMenuItem MenuItemIRL = new JMenuItem("IRL");
		MenuItemIRL.addActionListener(this);
		MenuParametres.add(MenuItemIRL);
		
		JMenu MenuGenerer = new JMenu("Générer les documents");
		menuBarTop.add(MenuGenerer);
		
		JMenuItem MenuItemQuittance = new JMenuItem("Quittances");
		MenuItemQuittance.addActionListener(this);
		MenuGenerer.add(MenuItemQuittance);
		
		JMenuItem MenuItemImpositions = new JMenuItem("Impositions");
		MenuItemImpositions.addActionListener(this);
		MenuGenerer.add(MenuItemImpositions);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textFieldDate = new JTextField();
		textFieldDate.setBounds(136, 97, 75, 20);
		contentPane.add(textFieldDate);
		textFieldDate.setColumns(10);
		
		JLabel LabelDateMiseEnEffet = new JLabel("* Date de mise en effet :");
		LabelDateMiseEnEffet.setBounds(11, 97, 132, 14);
		contentPane.add(LabelDateMiseEnEffet);
		
		textFieldCaution = new JTextField();
		textFieldCaution.setColumns(10);
		textFieldCaution.setBounds(136, 166, 132, 20);
		contentPane.add(textFieldCaution);
		
		JLabel LabelCaution = new JLabel("Caution :");
		LabelCaution.setBounds(11, 165, 132, 14);
		contentPane.add(LabelCaution);
		
		JLabel LabelType = new JLabel("Type de location :");
		LabelType.setBounds(11, 198, 132, 14);
		contentPane.add(LabelType);
		
		JButton ButtonAjouter = new JButton("Ajouter");
		ButtonAjouter.setBounds(307, 384, 132, 23);
		ButtonAjouter.addActionListener(this);
		contentPane.add(ButtonAjouter);
		
		JButton ButtonAnnuler = new JButton("Annuler");
		ButtonAnnuler.setBounds(49, 384, 132, 23);
		ButtonAnnuler.addActionListener(this);
		contentPane.add(ButtonAnnuler);
		
		JLabel LabelContrat = new JLabel("Nouveau contrat");
		LabelContrat.setFont(new Font("Tahoma", Font.BOLD, 20));
		LabelContrat.setBounds(24, 0, 307, 41);
		contentPane.add(LabelContrat);
		
		JComboBox comboBoxTypeLocation = new JComboBox();
		comboBoxTypeLocation.setBounds(136, 197, 182, 22);
		contentPane.add(comboBoxTypeLocation);
		ArrayList<String> typeLoc = new ArrayList<String>();
		typeLoc.add("Appartement");
		typeLoc.add("Maison");
		typeLoc.add("Autres...");
		int indexTypeLoc = 0;
		while ( indexTypeLoc < typeLoc.size()) {
			comboBoxTypeLocation.addItem(typeLoc.get(indexTypeLoc));
			indexTypeLoc++;
		}
		comboBoxTypeLocation.addActionListener(new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent e) {
		        JComboBox jcmbType = (JComboBox) e.getSource();
		        typeLocation = (String) jcmbType.getSelectedItem();
		        System.out.println(typeLocation);
		      }
		    });
		
		
		
		this.LabelLibelleLocataire = new JLabel("* Locataire :");
		LabelLibelleLocataire.setBounds(10, 270, 132, 14);
		contentPane.add(LabelLibelleLocataire);
		
		/**
		JCheckBox chckbxColocataire = new JCheckBox("Colocation");
		chckbxColocataire.setBounds(11, 233, 97, 23);
		contentPane.add(chckbxColocataire);
		chckbxColocataire.addActionListener(this);
		**/
		
		this.LabelLibelleNbLocataire = new JLabel("Locataires : x" + this.nbLocataire);
		LabelLibelleNbLocataire.setBounds(135, 233, 110, 23);
		contentPane.add(LabelLibelleNbLocataire);
		this.LabelLibelleNbLocataire.setVisible(false);
		
		JButton ButtonNouveauLocataire = new JButton("Nouveau Locataire");
		ButtonNouveauLocataire.setBounds(322, 270, 132, 23);
		ButtonNouveauLocataire.addActionListener(this);
		contentPane.add(ButtonNouveauLocataire);
		
		JLabel LabelDocuments = new JLabel("* Documents(s)  :");
		LabelDocuments.setBounds(10, 131, 132, 14);
		contentPane.add(LabelDocuments);
		
		JComboBox comboBoxDocuments = new JComboBox();
		comboBoxDocuments.setBounds(135, 131, 182, 22);
		comboBoxDocuments.setFont(new Font("Tahoma", Font.ROMAN_BASELINE, 10));
		contentPane.add(comboBoxDocuments);
		try {
			ResultSet rsDocDateLib= RequeteAfficheComboDocuments();
			int i = 0;
			rsDocDateLib.next();
			while ( i < rsDocDateLib.getRow()) {
				String libDoc = rsDocDateLib.getString("LIBELLE");
				String dateDoc = rsDocDateLib.getString("DATEDOCUMENT");
				comboBoxDocuments.addItem( libDoc + " : " + dateDoc.substring(0,10) );
				rsDocDateLib.next();
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		JButton ButtonNouveauDocument = new JButton("Nouveau Document");
		ButtonNouveauDocument.setBounds(322, 130, 132, 23);
		ButtonNouveauDocument.addActionListener(this);
		contentPane.add(ButtonNouveauDocument);
		
		/**
		this.ButtonColocationPlus = new JButton("+");
		ButtonColocationPlus.setBounds(300, 233, 50, 23);
		ButtonColocationPlus.addActionListener(this);
		contentPane.add(ButtonColocationPlus);
		ButtonColocationPlus.setVisible(false);
		ButtonColocationPlus.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		this.ButtonColocationMoins = new JButton("-");
		ButtonColocationMoins.setBounds(250, 233, 50, 23);
		ButtonColocationMoins.addActionListener(this);
		contentPane.add(ButtonColocationMoins);
		ButtonColocationMoins.setVisible(false);
		ButtonColocationMoins.setFont(new Font("Tahoma", Font.BOLD, 25));
	
		
	//LOCATAIRE
		this.comboBoxLocataire = new JComboBox();
		comboBoxLocataire.setBounds(136, 270, 182, 22);
		contentPane.add(comboBoxLocataire);
		try {
			ResultSet rsLocNomPrenom = RequeteAfficheComboLocataire();
			int i = 0;
			rsLocNomPrenom.next();
			while ( i < rsLocNomPrenom.getRow()) {
				comboBoxLocataire.addItem(rsLocNomPrenom.getString("NOM") + " " + rsLocNomPrenom.getString("PRENOM") + " - (" + rsLocNomPrenom.getString("IDLOCATAIRE")+ ")");
				rsLocNomPrenom.next();
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// GET COMBO LOCATAIRE SELECTED VALUE
		comboBoxLocataire.addActionListener(new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent e) {
		        JComboBox jcmbType = (JComboBox) e.getSource();
		        locataire = (String) jcmbType.getSelectedItem();
		        idLocataire = locataire.substring(locataire.length()-2, locataire.length()-1);
		        System.out.println(idLocataire);	
		        locataireSelected = true;
		        if ( locataireSelected ) {
		        	ButtonColocationPlus.setVisible(true);
		        }
		      }
		      
		    });
		**/
	//LOCATAIRE2
		this.comboBoxLocataire2 = new JComboBox();
		comboBoxLocataire2.setBounds(136, 290, 182, 22);
		contentPane.add(comboBoxLocataire2);
		comboBoxLocataire2.setVisible(false);

		// GET COMBO LOCATAIRE2 SELECTED VALUE
		comboBoxLocataire2.addActionListener(new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent e) {
		        JComboBox jcmbType = (JComboBox) e.getSource();
		        locataire2 = (String) jcmbType.getSelectedItem();
		        idLocataire2 = locataire2.substring(locataire2.length()-2, locataire2.length()-1);
		        System.out.println(idLocataire2);
		        locataireSelected2=true;
		        if (locataireSelected && locataireSelected2 ) {
		        	ButtonColocationPlus.setVisible(true);
		        }
		      }
		    });
		
	//LOCATAIRE3
		this.comboBoxLocataire3 = new JComboBox();
		comboBoxLocataire3.setBounds(136, 310, 182, 22);
		contentPane.add(comboBoxLocataire3);
		comboBoxLocataire3.setVisible(false);
		
		// GET COMBO LOCATAIRE3 SELECTED VALUE
		comboBoxLocataire3.addActionListener(new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent e) {
		        JComboBox jcmbType = (JComboBox) e.getSource();
		        locataire3 = (String) jcmbType.getSelectedItem();
		        idLocataire3 = locataire3.substring(locataire3.length()-2, locataire3.length()-1);
		        System.out.println(idLocataire3);
		        locataireSelected3=true;
		        if ( locataireSelected && locataireSelected2 && locataireSelected3 ) {
		        	ButtonColocationPlus.setVisible(true);
		        }
		      }
		    });
		
	//LOCATAIRE4
		this.comboBoxLocataire4 = new JComboBox();
		comboBoxLocataire4.setBounds(136, 330, 182, 22);
		contentPane.add(comboBoxLocataire4);
		comboBoxLocataire4.setVisible(false);
		
		// GET COMBO LOCATAIRE4 SELECTED VALUE
		comboBoxLocataire4.addActionListener(new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent e) {
		        JComboBox jcmbType = (JComboBox) e.getSource();
		        locataire4 = (String) jcmbType.getSelectedItem();
		        idLocataire4 = locataire4.substring(locataire4.length()-2, locataire4.length()-1);
		        System.out.println(idLocataire4);
		        locataireSelected4=true;
		        if ( locataireSelected && locataireSelected2 && locataireSelected3 && locataireSelected4 ) {
		        	ButtonColocationPlus.setVisible(true);
		        }
		      }
		    });
		
	//LOCATAIRE5
		this.comboBoxLocataire5 = new JComboBox();
		comboBoxLocataire5.setBounds(136, 350, 182, 22);
		contentPane.add(comboBoxLocataire5);
		comboBoxLocataire5.setVisible(false);

		// GET COMBO LOCATAIRE5 SELECTED VALUE
		comboBoxLocataire5.addActionListener(new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent e) {
		        JComboBox jcmbType = (JComboBox) e.getSource();
		        locataire5 = (String) jcmbType.getSelectedItem();
		        idLocataire5 = locataire5.substring(locataire5.length()-2, locataire5.length()-1);
		        System.out.println(idLocataire5);
		        locataireSelected5=true;
		      }
		    });
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(34, 313, 405, 70);
		contentPane.add(scrollPane);
		
		DefaultTableModel tableModel = new DefaultTableModel();
		JTable table = new JTable(tableModel);
		tableModel.addColumn("Locataire");
		try {
			ResultSet rsTable = RequeteGetLocataire();
			int i = 0;
			rsTable.next();
			while ( i < rsTable.getRow()) {
				String idLocataire = rsTable.getString("idloc");
				String locNom = rsTable.getString("locNom");
				String locPrenom = rsTable.getString("locPrenom");
				tableModel.insertRow(0, new Object[] { " (" +idLocataire +") "+ locNom + " | " +  locPrenom  });
				rsTable.next();
				}
			System.out.println(nbTableRows);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		table.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent mouseEvent) {
		        JTable table =(JTable) mouseEvent.getSource();
		        Point point = mouseEvent.getPoint();
		        int row = table.rowAtPoint(point);
		        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
		        	 String selectedData = null;
		    	        int[] selectedRow = table.getSelectedRows();
		    	        int[] selectedColumns = table.getSelectedColumns();
		    	        for (int i = 0; i < selectedRow.length; i++) {
		    	          for (int j = 0; j < selectedColumns.length; j++) {
		    	            selectedData = (String) table.getValueAt(selectedRow[i], selectedColumns[j]);
		    	          }
		    	        }	    	        
		    	        if (!tableAllSelectedData.contains(selectedData)) {
					        	tableAllSelectedData.add(selectedData);
					        System.out.println("Selected: " + selectedData.substring(selectedData.lastIndexOf("(")+1,selectedData.lastIndexOf(")")));
						} else {
								tableAllSelectedData.remove(selectedData);
								System.out.println("Selected: " + selectedData.substring(selectedData.lastIndexOf("(")+1,selectedData.lastIndexOf(")")));
						}
		        }
		    }
		});

		table.getColumnModel().getColumn(0).setPreferredWidth(310);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		scrollPane.setViewportView(table);
	

	}
	
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
			case "Ajouter":				
				String DATEMISEENEFFET = textFieldDate.getText();
				float CAUTION = Float.parseFloat(textFieldCaution.getText());
				String TYPELOC = this.typeLocation;
				try {				
					RequeteInsertContrat(DATEMISEENEFFET, CAUTION, TYPELOC) ;
					JOptionPane.showMessageDialog(frame, "Nouveau contrat inséré.");
				} catch (SQLException e3) {
					e3.printStackTrace();
				}			
				if(tableAllSelectedData.isEmpty()) { 
					this.dispose();
					new Accueil().setVisible(true);
					break;
				} else {
				      for (int counter = 0; counter < tableAllSelectedData.size(); counter++) { 		     
				          String idLoc =(tableAllSelectedData.get(counter).substring(tableAllSelectedData.get(counter).lastIndexOf("(")+1,tableAllSelectedData.get(counter).lastIndexOf(")"))); 		
						try {
							 String idContrat = RequeteLastIDContrat();
							 RequeteInsertRelie(idLoc,idContrat);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
				    	 
				         
				      }   	
						this.dispose();
						new Accueil().setVisible(true);
						break;
				}

			case "Colocation":
				this.chckbxColocataire = (JCheckBox) e.getSource();
				if (chckbxColocataire.isSelected()) {
					this.LabelLibelleNbLocataire.setVisible(true);		
					this.ButtonColocationPlus.setVisible(false);
					this.ButtonColocationMoins.setVisible(true);
					this.LabelLibelleLocataire.setText("* Locataires :");
				} else {
					this.LabelLibelleNbLocataire.setVisible(false);
					this.ButtonColocationPlus.setVisible(false);
					this.ButtonColocationMoins.setVisible(false);					
					this.comboBoxLocataire2.setVisible(false);
					this.comboBoxLocataire2.removeAllItems();
					this.comboBoxLocataire3.setVisible(false);
					this.comboBoxLocataire3.removeAllItems();
					this.comboBoxLocataire4.setVisible(false);
					this.comboBoxLocataire4.removeAllItems();
					this.comboBoxLocataire5.setVisible(false);
					this.comboBoxLocataire5.removeAllItems();
					this.nbLocataire=1;
					this.LabelLibelleNbLocataire.setText("Locataires : x" + this.nbLocataire);
					this.LabelLibelleLocataire.setText("* Locataire :");
				}
				break;
			case "+":
				if ( this.nbLocataire<5) {
					this.nbLocataire+=1;
					this.LabelLibelleNbLocataire.setText("Locataires : x" + this.nbLocataire);
					switch(this.nbLocataire){
						case 2:
							this.comboBoxLocataire2.setVisible(true);
							try {
								ResultSet rsLoc2NomPrenom = RequeteAfficheComboLocataire2();
								int i = 0;
								rsLoc2NomPrenom.next();
								while ( i < rsLoc2NomPrenom.getRow()) {
									comboBoxLocataire2.addItem(rsLoc2NomPrenom.getString("NOM") + " " + rsLoc2NomPrenom.getString("PRENOM") + " - (" + rsLoc2NomPrenom.getString("IDLOCATAIRE")+ ")");
									rsLoc2NomPrenom.next();
									}
							} catch (SQLException e2) {
								e2.printStackTrace();
							}
							this.ButtonColocationPlus.setVisible(false);
							break;
						case 3:
							this.comboBoxLocataire3.setVisible(true);
							try {
								ResultSet rsLoc3NomPrenom = RequeteAfficheComboLocataire3();
								int i = 0;
								rsLoc3NomPrenom.next();
								while ( i < rsLoc3NomPrenom.getRow()) {
									comboBoxLocataire3.addItem(rsLoc3NomPrenom.getString("NOM") + " " + rsLoc3NomPrenom.getString("PRENOM") + " - (" + rsLoc3NomPrenom.getString("IDLOCATAIRE")+ ")");
									rsLoc3NomPrenom.next();
									}
							} catch (SQLException e3) {
								e3.printStackTrace();
							}
							this.ButtonColocationPlus.setVisible(false);
							
							break;
						case 4:
							this.comboBoxLocataire4.setVisible(true);
							try {
								ResultSet rsLocNomPrenom4 = RequeteAfficheComboLocataire4();
								int i = 0;
								rsLocNomPrenom4.next();
								while ( i < rsLocNomPrenom4.getRow()) {
									comboBoxLocataire4.addItem(rsLocNomPrenom4.getString("NOM") + " " + rsLocNomPrenom4.getString("PRENOM") + " - (" + rsLocNomPrenom4.getString("IDLOCATAIRE")+ ")");
									rsLocNomPrenom4.next();
									}
							} catch (SQLException e4) {
								e4.printStackTrace();
							}
							this.ButtonColocationPlus.setVisible(false);							
							break;
						case 5:
							this.comboBoxLocataire5.setVisible(true);
							try {
								ResultSet rsLocNomPrenom5 = RequeteAfficheComboLocataire5();
								int i = 0;
								rsLocNomPrenom5.next();
								while ( i < rsLocNomPrenom5.getRow()) {
									comboBoxLocataire5.addItem(rsLocNomPrenom5.getString("NOM") + " " + rsLocNomPrenom5.getString("PRENOM") + " - (" + rsLocNomPrenom5.getString("IDLOCATAIRE")+ ")");
									rsLocNomPrenom5.next();
									}
							} catch (SQLException e5) {
								e5.printStackTrace();
							}
							this.ButtonColocationPlus.setVisible(false);							
							break;
					}
				}
				break;
			case "-":
				if ( this.nbLocataire>1) {
					this.nbLocataire-=1;
					this.LabelLibelleNbLocataire.setText("Locataires : x" + this.nbLocataire);
					switch(this.nbLocataire){
					case 4:
						this.comboBoxLocataire5.setVisible(false);
						this.comboBoxLocataire5.removeAllItems();
						break;
					case 3:
						this.comboBoxLocataire4.setVisible(false);
						this.comboBoxLocataire4.removeAllItems();
						break;
					case 2:
						this.comboBoxLocataire3.setVisible(false);
						this.comboBoxLocataire3.removeAllItems();
						break;
					case 1:
						this.comboBoxLocataire2.setVisible(false);
						this.comboBoxLocataire2.removeAllItems();
						break;
					}
				}
				break;
				
			case "Accueil":
				this.dispose();
				new Accueil().setVisible(true);
				break;
				
			case "Anciennes locations":
				this.dispose();
				new LocationsAnciennes().setVisible(true);
				break;
				
			case "Locations en cours":
				this.dispose();
				new LocationsEnCours().setVisible(true);
				break;
				
			case "Nouvelles locations":
				this.dispose();
				new NouvelleLocation().setVisible(true);
				break;
			
			case "Anciens locataires":
				this.dispose();
				new LocatairesAnciens().setVisible(true);
				break;
				
			case "Locataires en cours":
				this.dispose();
				new LocatairesEnCours().setVisible(true);
				break;
				
			case "Entretiens des parties communes":
				this.dispose();
				new EntretiensPartiesAnciens().setVisible(true);
				break;
				
			case "Nouveaux entretiens des parties communes":
				this.dispose();
				new NouveauEntretien().setVisible(true);
				break;
				
			case "Factures d'eau payées":
				this.dispose();
				new FacturesEauPayees().setVisible(true);
				break;
				
			case "Factures d'eau à payées":
				this.dispose();
				new FacturesEauAPayees().setVisible(true);
				break;
				
			case "Nouvelles factures d'eau":
				this.dispose();
				new NouvelleFactureEau().setVisible(true);
				break;
				
			case "Factures d'électricité payées":
				this.dispose();
				new FacturesElectricitePayees().setVisible(true);
				break;
				
			case "Factures d'électricité à payées":
				this.dispose();
				new FacturesElectriciteAPayees().setVisible(true);
				break;
				
			case "Nouvelles factures d'électricité":
				this.dispose();
				new NouvelleFactureElectricite().setVisible(true);
				break;
				
			case "Consultation taxes foncières":
				this.dispose();
				new TaxeFonciere().setVisible(true);
				break;
			
			case "Nouvelles taxes foncières":
				this.dispose();
				new NouvelleTaxeFonciere().setVisible(true);
				break;
				
			case "Consultation protection juridique":
				this.dispose();
				new ProtectionJuridique().setVisible(true);
				break;
			
			case "Nouvelles protection juridique":
				this.dispose();
				new NouvelleProtectionJuridique().setVisible(true);
				break;
				
			case "Consultation charges supplémentaires":
				this.dispose();
				new ChargesSupplementaires().setVisible(true);
				break;
			
			case "Nouvelle charges supplémentaires":
				this.dispose();
				new NouvelleChargeSupp().setVisible(true);
				break;
			
			case "Anciens travaux":
				this.dispose();
				new TravauxAnciens().setVisible(true);
				break;
				
			case "Travaux en cours":
				this.dispose();
				new TravauxEnCours().setVisible(true);
				break;
			
			case "Nouveaux travaux":
				this.dispose();
				new NouveauTravaux().setVisible(true);
				break;
				
			case "Informations bailleur":
				this.dispose();
				new InformationsBailleur().setVisible(true);
				break;
				
			case "IRL":
				this.dispose();
				new IRL().setVisible(true);
				break;
				
			case "Quittances":
				this.dispose();
				new Quittances().setVisible(true);
				break;
				
			case "Impositions":
				this.dispose();
				new Impositions().setVisible(true);
				break;
				
			case "Annuler":
				this.dispose();
				break;
       
			default:
				System.out.println("Choix incorrect");
				break;
		}
	}
}