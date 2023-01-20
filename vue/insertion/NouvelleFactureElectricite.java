package vue.insertion;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
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

public class NouvelleFactureElectricite extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField textFieldNumeroFacture;
	private JTextField textFieldDateFac;
	private JTextField textFieldMontant;
	private JTextField textFieldNomPDF;
	private JTextField textFieldRepPDF;
	private JComboBox<String> comboBoxEntreprise;
	private String comboEntNom;
	private NouvelleFactureElectricite frame;
	private JTable table;
	private int nbTableRows;
	private ArrayList<String> tableAllSelectedData = new ArrayList<String>();
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NouvelleFactureElectricite frame = new NouvelleFactureElectricite();
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
	// GET ALL LOCATIONS
	private ResultSet RequeteGetLocation() throws SQLException {
		ResultSet retourRequete = null;
		Requete requete = new Requetes.Requete();
		String texteSQL = "SELECT lieuxdelocations.libelle as lib, lieuxdelocations.idlogement as identifiant, bati.adresse as adr, bati.codepostal as cp from LIEUXDELOCATIONS, bati \n"
				+ "where lieuxdelocations.adresse = bati.adresse\n"
				+ "and lieuxdelocations.codepostal = bati.codepostal order by 4";
		retourRequete = requete.requeteSelection(texteSQL);
		return retourRequete;
	}
	
	// SHOW COMBO * ENTERPRISE.NOM
	private ResultSet RequeteAfficheComboEntreprise() throws SQLException {
		ResultSet retourRequete = null;
		Requete requete = new Requetes.Requete();
		String texteSQL = "select SIREN, NOM from ENTREPRISE";
		retourRequete = requete.requeteSelection(texteSQL);
		return retourRequete;
	}
	
	// GET INT SIREN FROM ENTREPRISE.NOM
	public int RequeteGetSirenEntrepriseCombo(String nomEnt) throws SQLException  {
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
	
	// DB INSERT x6 : INT INT INT STRING STRING+STRING
	/**
	private void RequeteInsertConcerneElectrique(int codepostal,int adresse, int id) throws SQLException {
		CictOracleDataSource cict = new CictOracleDataSource();
		String requete = "{ call insertConcerneElectrique(?,?) } ";
		
				try {
					Connection connection = cict.getConnection();
					CallableStatement cs = connection.prepareCall(requete);
					cs.setString(1, codepostal);
					cs.setString(2, adresse);
					cs.execute();
				} catch(SQLException e) {
					e.printStackTrace();
				}
	}
	**/
	// DB INSERT x2 : STRING STRING
		private void RequeteInsertFacElec(int siren, int numfact, float total, String datefact, String chemin, String pdf ) throws SQLException {
			CictOracleDataSource cict = new CictOracleDataSource();
			String requete = "{ call insertFactureElectrique(?,?,?,?,?,?) } ";
			
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

	public NouvelleFactureElectricite() {
		setTitle("Nouvelle facture d'électricité");
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
		
		textFieldNumeroFacture = new JTextField();
		textFieldNumeroFacture.setBounds(165, 126, 132, 20);
		contentPane.add(textFieldNumeroFacture);
		textFieldNumeroFacture.setColumns(10);
		
		JLabel LabelNumeroFacture = new JLabel("* Numéro de facture :");
		LabelNumeroFacture.setBounds(37, 126, 132, 14);
		contentPane.add(LabelNumeroFacture);
		
		textFieldDateFac = new JTextField();
		textFieldDateFac.setColumns(10);
		textFieldDateFac.setBounds(165, 157, 68, 20);
		contentPane.add(textFieldDateFac);
		
		JLabel LabelDateFacture = new JLabel("* Date de la facture  :");
		LabelDateFacture.setBounds(37, 157, 132, 14);
		contentPane.add(LabelDateFacture);
		
		JLabel LabelMontant = new JLabel("* Montant  total :");
		LabelMontant.setBounds(37, 188, 132, 14);
		contentPane.add(LabelMontant);
		
		textFieldMontant = new JTextField();
		textFieldMontant.setColumns(10);
		textFieldMontant.setBounds(165, 188, 132, 20);
		contentPane.add(textFieldMontant);
		
		JButton ButtonAjouter = new JButton("Ajouter");
		ButtonAjouter.setBounds(307, 384, 132, 23);
		ButtonAjouter.addActionListener(this);
		contentPane.add(ButtonAjouter);
		
		JButton ButtonAnnuler = new JButton("Annuler");
		ButtonAnnuler.setBounds(49, 384, 132, 23);
		ButtonAnnuler.addActionListener(this);
		contentPane.add(ButtonAnnuler);
		
		JLabel LabelFactureDElectricite = new JLabel("Nouvelle facture d'électricité");
		LabelFactureDElectricite.setFont(new Font("Tahoma", Font.BOLD, 20));
		LabelFactureDElectricite.setBounds(37, 0, 350, 41);
		contentPane.add(LabelFactureDElectricite);
		
		textFieldNomPDF = new JTextField();
		textFieldNomPDF.setColumns(10);
		textFieldNomPDF.setBounds(165, 282, 132, 20);
		textFieldNomPDF.setEditable(false);
		contentPane.add(textFieldNomPDF);
		
		textFieldRepPDF = new JTextField();
		textFieldRepPDF.setColumns(10);
		textFieldRepPDF.setBounds(165, 249, 274, 20);
		textFieldRepPDF.setEditable(false);
		contentPane.add(textFieldRepPDF);
		
		JLabel LabelNomPDF = new JLabel("Nom fichier :");
		LabelNomPDF.setBounds(49, 285, 119, 14);
		contentPane.add(LabelNomPDF);
		
		JLabel LabelEntreprise = new JLabel("* Entreprise :");
		LabelEntreprise.setBounds(37, 96, 132, 14);
		contentPane.add(LabelEntreprise);
		
		JButton ButtonNouvelleEntreprise = new JButton("Nouvelle entreprise");
		ButtonNouvelleEntreprise.setBounds(307, 92, 132, 23);
		ButtonNouvelleEntreprise.addActionListener(this);
		contentPane.add(ButtonNouvelleEntreprise);
		
		JComboBox<String> comboBoxEntreprise = new JComboBox<String>();
		comboBoxEntreprise.setBounds(165, 92, 132, 22);
		contentPane.add(comboBoxEntreprise);
			try {
				ResultSet rsEntNom = RequeteAfficheComboEntreprise();
				int i = 0;
				rsEntNom.next();
				while ( i < rsEntNom.getRow()) {
					comboBoxEntreprise.addItem(rsEntNom.getString("NOM"));
					rsEntNom.next();
					}
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		
		JButton ButtonPDF = new JButton("Choix fichier...");
		ButtonPDF.setBounds(165, 219, 132, 23);
		ButtonPDF.addActionListener(this);
		contentPane.add(ButtonPDF);
		
		JLabel LabelCheminPDF = new JLabel("Chemin d'accès  :");
		LabelCheminPDF.setBounds(49, 253, 119, 14);
		contentPane.add(LabelCheminPDF);
		
		JLabel LabelPDF = new JLabel("* Fichier :");
		LabelPDF.setBounds(37, 223, 132, 14);
		contentPane.add(LabelPDF);
		
		// GET COMBO SELECTED VALUE
		comboBoxEntreprise.addActionListener(new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent e) {
		        JComboBox jcmbType = (JComboBox) e.getSource();
		        comboEntNom = (String) jcmbType.getSelectedItem();
		      }
		    });
				
		JButton btnDateDeMaintenant = new JButton("Date");
		btnDateDeMaintenant.setBounds(235, 155, 66, 23);
		btnDateDeMaintenant.addActionListener(this);
		contentPane.add(btnDateDeMaintenant);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(34, 313, 405, 70);
		contentPane.add(scrollPane);
		
		DefaultTableModel tableModel = new DefaultTableModel();
		JTable table = new JTable(tableModel);
		tableModel.addColumn("Locations");
		try {
			ResultSet rsTable = RequeteGetLocation();
			int i = 0;
			rsTable.next();
			while ( i < rsTable.getRow()) {
				/**String jour = rsTable.getString("dateLoc").substring(8,10);
				String mois = rsTable.getString("dateLoc").substring(5,7);
				String annee = rsTable.getString("dateLoc").substring(0,4);
				String dateLoc = jour + "/" + mois + "/" + annee;
				**/
				String identifiant = rsTable.getString("identifiant");
				String lib = rsTable.getString("lib");
				String cp = rsTable.getString("cp");
				String adr = rsTable.getString("adr");
				tableModel.insertRow(0, new Object[] { " (" +identifiant +") "+ lib + " | " +  cp + " - " + adr  });
				rsTable.next();
				nbTableRows++;
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
			case "Date":
				DateTimeFormatter dtfJ = DateTimeFormatter.ofPattern("DD");
				LocalDate nowDate = LocalDate.now();
				String jours = nowDate.toString().substring(8);
				String mois = nowDate.toString().substring(5,7);
				String annee = nowDate.toString().substring(0,4);
				String dateCourrante = ( jours + "/" + mois + "/" + annee );
				this.textFieldDateFac.setText(""+dateCourrante);
				break;
			case "Nouvelle entreprise":
				new NouvelleEntreprise().setVisible(true);
				break;
				
			case "Ajouter":	
					String datefact = textFieldDateFac.getText();		
					String chemin = textFieldRepPDF.getText() ;
					String pdf = textFieldNomPDF.getText() ;				
					int numfact = Integer.parseInt(textFieldNumeroFacture.getText());
			        
			        float total = Float.parseFloat(textFieldMontant.getText());
					try {
						int siren = RequeteGetSirenEntrepriseCombo(comboEntNom);					
						RequeteInsertFacElec(siren, numfact, total, datefact, chemin, pdf);
						JOptionPane.showMessageDialog(frame, "Facture " + numfact + " insérée.");
					} catch (SQLException e3) {
						e3.printStackTrace();
					}				

				if(tableAllSelectedData.isEmpty()) { 

					this.dispose();
					new Accueil().setVisible(true);
					break;
				} else {
				      for (int counter = 0; counter < tableAllSelectedData.size(); counter++) { 		      
				          System.out.println(tableAllSelectedData.get(counter).substring(tableAllSelectedData.get(counter).lastIndexOf("(")+1,tableAllSelectedData.get(counter).lastIndexOf(")"))); 		
				      }   	
					this.dispose();
					new Accueil().setVisible(true);
					break;
				}

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

			case "Choix fichier...":
				JFileChooser pdfChooser = new JFileChooser();
				int reponse = pdfChooser.showOpenDialog(null);
				if (reponse == JFileChooser.APPROVE_OPTION) {
					File file = new File(pdfChooser.getSelectedFile().getAbsolutePath());
					textFieldNomPDF.setText(file.getName());
					textFieldRepPDF.setText(file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - textFieldNomPDF.getText().length() - 1));
				}
				break;       
			default:
				System.out.println("Choix incorrect");
				break;
		}
	}
}