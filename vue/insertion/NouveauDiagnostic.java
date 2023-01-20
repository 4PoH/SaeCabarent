package vue.insertion;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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

import Requetes.RequeteInsertion;
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

public class NouveauDiagnostic extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField textFieldNumRef;
	private JTextField textNomDiagnostic;
	private JTextField textFieldNumRapport;
	private String selectedComboIDLogement;
	private String selectedComboLogement;
	private String comboEntNom;	
	private JTextField textFieldDateObtention;
	private JTextField textFieldDateFin;
	private JTextField textFieldRepPDF;
	private JTextField textFieldNomPDF;
	private NouveauDiagnostic frame;
	private ArrayList<String> tableAllSelectedData = new ArrayList<String>();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NouveauDiagnostic frame = new NouveauDiagnostic();
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
	public NouveauDiagnostic() {
		setTitle("Nouveau diagnostic");
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
		MenuLocations.add(MenuItemAnciensLocataires);
		
		JMenuItem MenuItemLocatairesEnCours = new JMenuItem("Locataires en cours");
		MenuLocations.add(MenuItemLocatairesEnCours);
		
		JMenu MenuCharges = new JMenu("Charges");
		MenuCharges.addActionListener(this);
		menuBarTop.add(MenuCharges);
		
		JMenu MenuEntretiens = new JMenu("Entretiens");
		MenuEntretiens.addActionListener(this);
		MenuCharges.add(MenuEntretiens);
		
		JMenuItem MenuItemAnciensEntretiens = new JMenuItem("Anciens entretiens");
		MenuItemAnciensEntretiens.addActionListener(this);
		MenuItemAnciensEntretiens.setSelected(true);
		MenuEntretiens.add(MenuItemAnciensEntretiens);
		
		JMenuItem mntmEntretiensEnCours = new JMenuItem("Entretiens en cours");
		mntmEntretiensEnCours.addActionListener(this);
		mntmEntretiensEnCours.setSelected(true);
		MenuEntretiens.add(mntmEntretiensEnCours);
		
		JMenuItem MenuItemNouveauxEntretiens = new JMenuItem("Nouveaux entretiens");
		MenuItemNouveauxEntretiens.addActionListener(this);
		
		JMenuItem MenuItemAnciensEntretiensPartiesCommunes = new JMenuItem("Anciens entretiens parties communes");
		MenuItemAnciensEntretiensPartiesCommunes.setSelected(true);
		MenuEntretiens.add(MenuItemAnciensEntretiensPartiesCommunes);
		
		JMenuItem MenuItemEntretiensPartiesCommunes = new JMenuItem("Entretiens parties communes en cours");
		MenuItemEntretiensPartiesCommunes.setSelected(true);
		MenuEntretiens.add(MenuItemEntretiensPartiesCommunes);
		MenuItemNouveauxEntretiens.setSelected(true);
		MenuEntretiens.add(MenuItemNouveauxEntretiens);
		
		JMenu MenuFacturesEau = new JMenu("Factures d'eau");
		MenuFacturesEau.addActionListener(this);
		MenuCharges.add(MenuFacturesEau);
		
		JMenuItem MenuItemAnciennesFacturesEau = new JMenuItem("Anciennes factures d'eau");
		MenuItemAnciennesFacturesEau.addActionListener(this);
		MenuFacturesEau.add(MenuItemAnciennesFacturesEau);
		
		JMenuItem MenuItemFacturesEauEnCours = new JMenuItem("Factures d'eau en cours");
		MenuItemFacturesEauEnCours.addActionListener(this);
		MenuFacturesEau.add(MenuItemFacturesEauEnCours);
		
		JMenuItem MenuItemNouvellesFactureEau = new JMenuItem("Nouvelles factures d'eau");
		MenuItemNouvellesFactureEau.addActionListener(this);
		MenuFacturesEau.add(MenuItemNouvellesFactureEau);
		
		JMenu MenuElectricite = new JMenu("Electricité");
		MenuElectricite.addActionListener(this);
		MenuCharges.add(MenuElectricite);
		
		JMenuItem MenuItemAnciennesFacturesElectricite = new JMenuItem("Anciennes factures d'électricité");
		MenuItemAnciennesFacturesElectricite.addActionListener(this);
		MenuElectricite.add(MenuItemAnciennesFacturesElectricite);
		
		JMenuItem mntmFacturesDlectricitEn = new JMenuItem("Factures d'électricité en cours");
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
		
		textFieldNumRef = new JTextField();
		textFieldNumRef.setColumns(10);
		textFieldNumRef.setBounds(159, 122, 68, 20);
		contentPane.add(textFieldNumRef);
		
		JLabel LabelNumReference = new JLabel("* Numéro de référence :");
		LabelNumReference.setBounds(39, 125, 132, 14);
		contentPane.add(LabelNumReference);
		
		JLabel LabelNomDiagnostic = new JLabel("Nom du diagnostic :");
		LabelNomDiagnostic.setBounds(39, 153, 144, 14);
		contentPane.add(LabelNomDiagnostic);
		
		textNomDiagnostic = new JTextField();
		textNomDiagnostic.setColumns(10);
		textNomDiagnostic.setBounds(159, 150, 132, 20);
		contentPane.add(textNomDiagnostic);
		
		JButton ButtonAjouter = new JButton("Ajouter");
		ButtonAjouter.setBounds(307, 384, 132, 23);
		ButtonAjouter.addActionListener(this);
		contentPane.add(ButtonAjouter);
		
		JButton ButtonAnnuler = new JButton("Annuler");
		ButtonAnnuler.setBounds(49, 384, 132, 23);
		contentPane.add(ButtonAnnuler);
		
		JLabel LabelTaxeFonciere = new JLabel("Nouveau diagnostic");
		LabelTaxeFonciere.setFont(new Font("Tahoma", Font.BOLD, 20));
		LabelTaxeFonciere.setBounds(37, 0, 260, 41);
		contentPane.add(LabelTaxeFonciere);
		
		textFieldNumRapport = new JTextField();
		textFieldNumRapport.setColumns(10);
		textFieldNumRapport.setBounds(159, 234, 68, 20);
		contentPane.add(textFieldNumRapport);
		
		JLabel LabelNumeroRapport = new JLabel("Numéro de rapport :");
		LabelNumeroRapport.setBounds(39, 237, 132, 14);
		contentPane.add(LabelNumeroRapport);
		
		JLabel LabelEntreprise = new JLabel("* Entreprise :");
		LabelEntreprise.setBounds(39, 68, 132, 14);
		contentPane.add(LabelEntreprise);
		
		JComboBox<String> comboBoxEntreprise = new JComboBox<String>();
		comboBoxEntreprise.setBounds(159, 64, 132, 22);
		contentPane.add(comboBoxEntreprise);
		try {
			ResultSet rsEntNom = RequeteInsertion.RequeteAfficheComboEntreprise();
			int i = 0;
			rsEntNom.next();
			while ( i < rsEntNom.getRow()) {
				comboBoxEntreprise.addItem(rsEntNom.getString("NOM"));
				rsEntNom.next();
				}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		// GET COMBO SELECTED VALUE
		comboBoxEntreprise.addActionListener(new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent e) {
		        JComboBox<?> jcmbType = (JComboBox<?>) e.getSource();
		        comboEntNom = (String) jcmbType.getSelectedItem();
		      }
		    });
		
		JButton ButtonNouvelleEntreprise = new JButton("Nouvelle entreprise");
		ButtonNouvelleEntreprise.setBounds(301, 64, 132, 23);
		contentPane.add(ButtonNouvelleEntreprise);
		
		JLabel LabelLogements = new JLabel("* Logement  :");
		LabelLogements.setBounds(41, 93, 132, 14);
		contentPane.add(LabelLogements);
		
		JComboBox<String> comboBoxLogements = new JComboBox<String>();
		comboBoxLogements.setFont(new Font("Tahoma", Font.PLAIN, 8));
		comboBoxLogements.setBounds(159, 92, 132, 22);
		contentPane.add(comboBoxLogements);
		try {
			ResultSet rsLogement = RequeteInsertion.RequeteAfficheLogement();
			int i = 0;
			rsLogement.next();
			while ( i < rsLogement.getRow()) {
				String idLogement = rsLogement.getString("IDLOGEMENT");
				String adresse = rsLogement.getString("ADRESSE");
				String lib = rsLogement.getString("LIBELLE");
				String codePostal = rsLogement.getString("CODEPOSTAL");
				comboBoxLogements.addItem( lib + " - " + codePostal + " - " + adresse +  " (" +idLogement+") ");
				rsLogement.next();
				}
		} catch (SQLException e) {
			e.printStackTrace();
		} 		
		comboBoxLogements.addActionListener(new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent e) {
		        JComboBox<?> jcmbType = (JComboBox<?>) e.getSource();
		        selectedComboLogement = (String) jcmbType.getSelectedItem();
		        selectedComboIDLogement = selectedComboLogement.substring(selectedComboLogement.lastIndexOf("(")+1,selectedComboLogement.lastIndexOf(")"));		        
		      }
		    });
		
		JButton ButtonNouveauLogement = new JButton("Nouveau Logement");
		ButtonNouveauLogement.setBounds(301, 92, 132, 23);
		contentPane.add(ButtonNouveauLogement);
		
		JLabel LabelDateObtention = new JLabel("* Date d'obtention :");
		LabelDateObtention.setBounds(39, 182, 132, 14);
		contentPane.add(LabelDateObtention);
		
		textFieldDateObtention = new JTextField();
		textFieldDateObtention.setColumns(10);
		textFieldDateObtention.setBounds(159, 179, 66, 20);
		contentPane.add(textFieldDateObtention);
		
		JButton btnDateDeMaintenant = new JButton("Date");
		btnDateDeMaintenant.setBounds(225, 178, 66, 23);
		btnDateDeMaintenant.addActionListener(this);
		contentPane.add(btnDateDeMaintenant);
		
		JLabel lblDateDeFin = new JLabel("Date de fin de validité :");
		lblDateDeFin.setBounds(39, 207, 132, 14);
		contentPane.add(lblDateDeFin);
		
		textFieldDateFin = new JTextField();
		textFieldDateFin.setColumns(10);
		textFieldDateFin.setBounds(159, 207, 66, 20);
		contentPane.add(textFieldDateFin);
		
		JLabel LabelPDF = new JLabel("* Fichier :");
		LabelPDF.setBounds(39, 269, 132, 14);
		contentPane.add(LabelPDF);
		
		JButton ButtonPDF = new JButton("Choix fichier...");
		ButtonPDF.setBounds(159, 265, 132, 23);
		ButtonPDF.addActionListener(this);
		contentPane.add(ButtonPDF);
		
		textFieldRepPDF = new JTextField();
		textFieldRepPDF.setEditable(false);
		textFieldRepPDF.setColumns(10);
		textFieldRepPDF.setBounds(383, 261, 78, 20);
		contentPane.add(textFieldRepPDF);
		
		JLabel LabelCheminPDF = new JLabel("Chemin d'accès  :");
		LabelCheminPDF.setBounds(299, 264, 119, 14);
		contentPane.add(LabelCheminPDF);
		
		JLabel LabelNomPDF = new JLabel("Nom fichier :");
		LabelNomPDF.setBounds(299, 296, 119, 14);
		contentPane.add(LabelNomPDF);
		
		textFieldNomPDF = new JTextField();
		textFieldNomPDF.setEditable(false);
		textFieldNomPDF.setColumns(10);
		textFieldNomPDF.setBounds(383, 294, 78, 20);
		contentPane.add(textFieldNomPDF);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(34, 313, 405, 70);
		contentPane.add(scrollPane);
		
		DefaultTableModel tableModel = new DefaultTableModel();
		JTable table = new JTable(tableModel);
		tableModel.addColumn("Logements");
		try {
			ResultSet rsTable = RequeteInsertion.RequeteGetLogFromBati();
			int i = 0;
			rsTable.next();
			while ( i < rsTable.getRow()) {
				String adr = rsTable.getString("adr");
				String idLog = rsTable.getString("idLog");
				String libLoc = rsTable.getString("libLoc");
				String libBati = rsTable.getString("libBati");
				
				tableModel.insertRow(0, new Object[] { "{" + idLog +"} "+ libLoc + " - " +  libBati + " " + adr });
				rsTable.next();
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		table.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent mouseEvent) {
		        JTable table =(JTable) mouseEvent.getSource();
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
					        String idLog = selectedData.substring(selectedData.lastIndexOf("{")+1,selectedData.lastIndexOf("}"));
					        	System.out.println("Selected: " + idLog );
						} else {
								tableAllSelectedData.remove(selectedData);
						        String idLog = selectedData.substring(selectedData.lastIndexOf("{")+1,selectedData.lastIndexOf("}"));
					        	System.out.println("Unselected: " + idLog );
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
				LocalDate nowDate = LocalDate.now();
				String jours = nowDate.toString().substring(8);
				String mois = nowDate.toString().substring(5,7);
				String annee = nowDate.toString().substring(0,4);
				String dateCourrante = ( jours + "/" + mois + "/" + annee );
				this.textFieldDateObtention.setText(""+dateCourrante);
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
			case "Ajouter":					
				String numRef = textFieldNumRef.getText();
				String nomDiag = textNomDiagnostic.getText() ;
				String dateObtention = textFieldDateObtention.getText() ; 
				String dateFinva = textFieldDateFin.getText();
				String numRapp = textFieldNumRapport.getText() ;
				String direction = textFieldRepPDF.getText() ; 
				String nomPdf = textFieldNomPDF.getText();
				int idLog = Integer.parseInt(selectedComboIDLogement);
				try {	
					int numSiren = RequeteInsertion.RequeteGetSirenEntrepriseCombo(comboEntNom);
					System.out.println(numSiren + " " + numRef + " " + nomDiag + " " + dateObtention + " " + dateFinva + " " + numRapp + " " + direction + " " + nomPdf + " " + idLog);
					RequeteInsertion.RequeteInsertBati(numSiren, numRef,nomDiag, dateObtention, dateFinva, numRapp, direction, nomPdf, idLog);
					JOptionPane.showMessageDialog(frame, "Diagnostic " + numRef + " sur le logement " + selectedComboLogement + " a été inséré.");
				} catch (SQLException e3) {
					e3.printStackTrace();
				}
				this.dispose();
				new Accueil().setVisible(true);
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
				
			case "Nouveaux loyers":
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