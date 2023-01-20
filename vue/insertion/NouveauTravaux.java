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
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

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

public class NouveauTravaux extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField textFieldNumeroFacture;
	private JTextField textFieldNumeroDevis;
	private JTextField textFieldLibelle;
	private JTextField textFieldDateDebut;
	private JTextField textFieldMontant;
	private JTextField textFieldMontantNonDeductible;
	private JTextField textFieldReduction;
	private JTextField textFieldDateFin;
	private JTextField textFieldRepPDF;
	private JTextField textFieldNomPDF;
	private JTextPane textPaneDescription;
	private String comboEntNom;
	private NouveauTravaux frame;
	private ArrayList<String> tableAllSelectedData = new ArrayList<String>();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NouveauTravaux frame = new NouveauTravaux();
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
	public NouveauTravaux() {
		setTitle("Nouveaux travaux");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(105, 100, 480, 480);
		
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
		
		JMenuItem MenuItemNouvelleLocation = new JMenuItem("Nouveaux loyers");
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
		textFieldNumeroFacture.setBounds(164, 59, 132, 20);
		contentPane.add(textFieldNumeroFacture);
		textFieldNumeroFacture.setColumns(10);
		
		textFieldNumeroDevis = new JTextField();
		textFieldNumeroDevis.setColumns(10);
		textFieldNumeroDevis.setBounds(164, 84, 132, 20);
		contentPane.add(textFieldNumeroDevis);
		
		JLabel LabelNumeroFacture = new JLabel("* NumÃ©ro de facture :");
		LabelNumeroFacture.setBounds(36, 59, 132, 14);
		contentPane.add(LabelNumeroFacture);
		
		JLabel LabelNumeroDevis = new JLabel("NumÃ©ro de devis :");
		LabelNumeroDevis.setBounds(36, 84, 132, 14);
		contentPane.add(LabelNumeroDevis);
		
		textFieldLibelle = new JTextField();
		textFieldLibelle.setColumns(10);
		textFieldLibelle.setBounds(164, 109, 132, 20);
		contentPane.add(textFieldLibelle);
		
		JLabel LabelLibelle = new JLabel("LibellÃ©  :");
		LabelLibelle.setBounds(36, 109, 132, 14);
		contentPane.add(LabelLibelle);
		
		textFieldDateDebut = new JTextField();
		textFieldDateDebut.setColumns(10);
		textFieldDateDebut.setBounds(164, 135, 68, 20);
		contentPane.add(textFieldDateDebut);
		
		JLabel LabelDateDebut = new JLabel("Date de dÃ©but  :");
		LabelDateDebut.setBounds(36, 135, 132, 14);
		contentPane.add(LabelDateDebut);
		
		JLabel LabelMontant = new JLabel("Montant  :");
		LabelMontant.setBounds(37, 207, 132, 14);
		contentPane.add(LabelMontant);
		
		textFieldMontant = new JTextField();
		textFieldMontant.setColumns(10);
		textFieldMontant.setBounds(165, 207, 132, 20);
		contentPane.add(textFieldMontant);
		
		JLabel LabelMontantNonDeductible = new JLabel("Montant  non dÃ©ductible :");
		LabelMontantNonDeductible.setBounds(36, 232, 132, 14);
		contentPane.add(LabelMontantNonDeductible);
		
		textFieldMontantNonDeductible = new JTextField();
		textFieldMontantNonDeductible.setColumns(10);
		textFieldMontantNonDeductible.setBounds(164, 232, 132, 20);
		contentPane.add(textFieldMontantNonDeductible);
		
		JLabel LabelReduction = new JLabel("RÃ©duction :");
		LabelReduction.setBounds(36, 262, 132, 14);
		contentPane.add(LabelReduction);
		
		textFieldReduction = new JTextField();
		textFieldReduction.setColumns(10);
		textFieldReduction.setBounds(164, 262, 132, 20);
		contentPane.add(textFieldReduction);
		
		JButton ButtonAjouter = new JButton("Ajouter");
		ButtonAjouter.setBounds(307, 384, 132, 23);
		ButtonAjouter.addActionListener(this);
		contentPane.add(ButtonAjouter);
		
		JButton ButtonAnnuler = new JButton("Annuler");
		ButtonAnnuler.setBounds(36, 384, 132, 23);
		ButtonAnnuler.addActionListener(this);
		contentPane.add(ButtonAnnuler);
		
		
		JLabel LabelNouveauTravaux = new JLabel("Nouveaux Travaux");
		LabelNouveauTravaux.setFont(new Font("Tahoma", Font.BOLD, 20));
		LabelNouveauTravaux.setBounds(37, 0, 202, 41);
		contentPane.add(LabelNouveauTravaux);
		
		JLabel LabelDateFin = new JLabel("Date de fin :");
		LabelDateFin.setBounds(242, 138, 93, 14);
		contentPane.add(LabelDateFin);
		
		textFieldDateFin = new JTextField();
		textFieldDateFin.setColumns(10);
		textFieldDateFin.setBounds(306, 135, 68, 20);
		contentPane.add(textFieldDateFin);
		
		JLabel LabelDescription = new JLabel("Description  :");
		LabelDescription.setBounds(37, 162, 132, 14);
		contentPane.add(LabelDescription);
		
		JLabel LabelEntreprise = new JLabel("* Entreprise :");
		LabelEntreprise.setBounds(37, 36, 132, 14);
		contentPane.add(LabelEntreprise);
		
		JComboBox<String> comboBoxEntreprise = new JComboBox<String>();
		comboBoxEntreprise.setBounds(165, 32, 132, 22);
		contentPane.add(comboBoxEntreprise);
		try {
			ResultSet rsEntNom = Requetes.RequeteInsertion.RequeteAfficheComboEntreprise();
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
		ButtonNouvelleEntreprise.setBounds(307, 32, 132, 23);
		ButtonNouvelleEntreprise.addActionListener(this);
		contentPane.add(ButtonNouvelleEntreprise);
		
		JButton ButtonPDF = new JButton("Choix fichier...");
		ButtonPDF.setBounds(164, 287, 132, 23);
		ButtonPDF.addActionListener(this);
		contentPane.add(ButtonPDF);
		
		textFieldRepPDF = new JTextField();
		textFieldRepPDF.setEditable(false);
		textFieldRepPDF.setColumns(10);
		textFieldRepPDF.setBounds(394, 257, 70, 20);
		contentPane.add(textFieldRepPDF);
		
		JLabel LabelCheminPDF = new JLabel("Chemin d'accÃ¨s  :");
		LabelCheminPDF.setBounds(301, 261, 119, 14);
		contentPane.add(LabelCheminPDF);
		
		JLabel LabelNomPDF = new JLabel("Nom fichier :");
		LabelNomPDF.setBounds(301, 293, 119, 14);
		contentPane.add(LabelNomPDF);
		
		textFieldNomPDF = new JTextField();
		textFieldNomPDF.setEditable(false);
		textFieldNomPDF.setColumns(10);
		textFieldNomPDF.setBounds(394, 290, 70, 20);
		contentPane.add(textFieldNomPDF);
		
		JLabel LabelPDF = new JLabel("* Fichier :");
		LabelPDF.setBounds(36, 291, 132, 14);
		contentPane.add(LabelPDF);		
		
		JButton btnDateDeMaintenant = new JButton("Date");
		btnDateDeMaintenant.setBounds(373, 134, 66, 23);
		btnDateDeMaintenant.addActionListener(this);
		contentPane.add(btnDateDeMaintenant);
		
		this.textPaneDescription = new JTextPane();
		textPaneDescription.setBounds(165, 160, 235, 46);
		textPaneDescription.setText(" ");
		contentPane.add(textPaneDescription);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(34, 313, 405, 70);
		contentPane.add(scrollPane);
		
		DefaultTableModel tableModel = new DefaultTableModel();
		JTable table = new JTable(tableModel);
		tableModel.addColumn("Logements");
		try {
			ResultSet rsTable = Requetes.RequeteInsertion.RequeteGetLogFromBati();
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
			case "Ajouter":				
				int numFacture = Integer.parseInt(textFieldNumeroFacture.getText());
				int numeroDevis= Integer.parseInt(textFieldNumeroDevis.getText());
				String lib = textFieldLibelle.getText();
				String dateD = textFieldDateDebut.getText();
				String dateF = textFieldDateFin.getText();
				String details = this.textPaneDescription.getText();
				float montantTrav = Float.parseFloat(textFieldMontant.getText()); 
				float montantNondeduc = Float.parseFloat(textFieldMontantNonDeductible.getText()); 
				float reduc = Float.parseFloat(textFieldReduction.getText());
				String direction = textFieldRepPDF.getText();
				String nomPdf = textFieldNomPDF.getText();
				try {
					System.out.println(textPaneDescription.getText());
					String numSiren = Requetes.RequeteInsertion.RequeteGetStringSirenEntrepriseCombo(comboEntNom);					
					Requetes.RequeteInsertion.RequeteInsertTravaux(numSiren, numFacture,numeroDevis, lib, dateD, dateF, details, montantTrav, montantNondeduc, reduc, direction, nomPdf);
					JOptionPane.showMessageDialog(frame, "Facture " + numFacture + " des travaux nÂ°" + numeroDevis +" insÃ©rÃ©e.");
				} catch (SQLException e3) {
					e3.printStackTrace();
				}				
				if(tableAllSelectedData.isEmpty()) { 

					this.dispose();
					new Accueil().setVisible(true);
					break;
				} else {
				      for (int counter = 0; counter < tableAllSelectedData.size(); counter++) { 		      
						// Pour les liaisons avec les batis
						String datePaiement = "01/01/2001";
						String modePaiement = " ";
						String numeroCheque = " ";
						float pourcentagepart = 0.0F;
				        String idlogement = (tableAllSelectedData.get(counter).substring(tableAllSelectedData.get(counter).lastIndexOf("{")+1,tableAllSelectedData.get(counter).lastIndexOf("}")));
				        try {									
				        	String siren = Requetes.RequeteInsertion.RequeteGetStringSirenEntrepriseCombo(comboEntNom);
				        	Requetes.RequeteInsertion.RequeteInsertConcerne(idlogement, siren, numFacture, pourcentagepart,  datePaiement, modePaiement, numeroCheque);
				        } catch (SQLException e1) {
								e1.printStackTrace();
							}
				      }   	
					this.dispose();
					new Accueil().setVisible(true);
					break;
				}
			case "Choix fichier...":
				JFileChooser pdfChooser = new JFileChooser();
				int reponse = pdfChooser.showOpenDialog(null);
				if (reponse == JFileChooser.APPROVE_OPTION) {
					File file = new File(pdfChooser.getSelectedFile().getAbsolutePath());
					textFieldNomPDF.setText(file.getName());
					textFieldRepPDF.setText(file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - textFieldNomPDF.getText().length() - 1));
				}
				break;    
			case "Date":
				LocalDate nowDate = LocalDate.now();
				String jours = nowDate.toString().substring(8);
				String mois = nowDate.toString().substring(5,7);
				String annee = nowDate.toString().substring(0,4);
				String dateCourrante = ( jours + "/" + mois + "/" + annee );
				this.textFieldDateFin.setText(""+dateCourrante);
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