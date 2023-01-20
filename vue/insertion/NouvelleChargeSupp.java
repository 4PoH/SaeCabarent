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

public class NouvelleChargeSupp extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField textFieldNumFac;
	private JTextField textFieldLibelle;
	private JTextField textFieldDateFac;
	private JTextField textFieldMontantTotal;
	private JTextField textFieldNomPDF;
	private JTextField textFieldRepPDF;
	private String selectedComboEntNom;
	private NouvelleChargeSupp frame;
	private ArrayList<String> tableAllSelectedData = new ArrayList<String>();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NouvelleChargeSupp frame = new NouvelleChargeSupp();
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
	public NouvelleChargeSupp() {
		setTitle("Nouvelle charge supplementaire");
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
				
		JLabel LabelNumeroFacture = new JLabel("* Numéro de facture :");
		LabelNumeroFacture.setBounds(24, 95, 132, 14);
		contentPane.add(LabelNumeroFacture);
		
		JLabel LabelDateFacture = new JLabel("* Date de la facture  :");
		LabelDateFacture.setBounds(24, 126, 132, 14);
		contentPane.add(LabelDateFacture);
		
		JLabel LabelMontantTotal = new JLabel("* Montant  total :");
		LabelMontantTotal.setBounds(24, 188, 132, 14);
		contentPane.add(LabelMontantTotal);
		
		JLabel LabelNomPDF = new JLabel("Nom fichier :");
		LabelNomPDF.setBounds(36, 285, 119, 14);
		contentPane.add(LabelNomPDF);

		
		JLabel LabelCheminPDF = new JLabel("Chemin d'accès  :");
		LabelCheminPDF.setBounds(36, 253, 119, 14);
		contentPane.add(LabelCheminPDF);
		
		JLabel LabelPDF = new JLabel("* Fichier :");
		LabelPDF.setBounds(24, 223, 132, 14);
		contentPane.add(LabelPDF);
		
		JLabel LabelNouvelEntretien = new JLabel("Nouvelle charge supplementaire");
		LabelNouvelEntretien.setFont(new Font("Tahoma", Font.BOLD, 20));
		LabelNouvelEntretien.setBounds(24, 11, 350, 41);
		contentPane.add(LabelNouvelEntretien);
		
		JButton ButtonAjouter = new JButton("Ajouter");
		ButtonAjouter.setBounds(294, 384, 132, 23);
		ButtonAjouter.addActionListener(this);
		contentPane.add(ButtonAjouter);
		
		JButton ButtonAnnuler = new JButton("Annuler");
		ButtonAnnuler.setBounds(36, 384, 132, 23);
		ButtonAnnuler.addActionListener(this);
		contentPane.add(ButtonAnnuler);
		
		textFieldMontantTotal = new JTextField();
		textFieldMontantTotal.setColumns(10);
		textFieldMontantTotal.setBounds(152, 188, 132, 20);
		contentPane.add(textFieldMontantTotal);
		
		textFieldNumFac = new JTextField();
		textFieldNumFac.setColumns(10);
		textFieldNumFac.setBounds(152, 95, 132, 20);
		contentPane.add(textFieldNumFac);
		
		textFieldDateFac = new JTextField();
		textFieldDateFac.setColumns(10);
		textFieldDateFac.setBounds(152, 126, 68, 20);
		contentPane.add(textFieldDateFac);
		
		textFieldNomPDF = new JTextField();
		textFieldNomPDF.setEditable(false);
		textFieldNomPDF.setColumns(10);
		textFieldNomPDF.setBounds(152, 282, 132, 20);
		contentPane.add(textFieldNomPDF);
		
		textFieldRepPDF = new JTextField();
		textFieldRepPDF.setEditable(false);
		textFieldRepPDF.setColumns(10);
		textFieldRepPDF.setBounds(152, 249, 274, 20);
		contentPane.add(textFieldRepPDF);
		
		textFieldLibelle = new JTextField();
		textFieldLibelle.setColumns(10);
		textFieldLibelle.setBounds(152, 157, 132, 22);
		contentPane.add(textFieldLibelle);
		
		JLabel LabelEntreprise = new JLabel("* Entreprise :");
		LabelEntreprise.setBounds(24, 66, 132, 14);
		contentPane.add(LabelEntreprise);
		
		JButton ButtonNouvelleEntreprise = new JButton("Nouvelle entreprise");
		ButtonNouvelleEntreprise.setBounds(294, 62, 132, 23);
		ButtonNouvelleEntreprise.addActionListener(this);
		contentPane.add(ButtonNouvelleEntreprise);
		
		JComboBox<String> comboBoxEntreprise = new JComboBox<String>();
		comboBoxEntreprise.setBounds(152, 62, 132, 22);
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
		comboBoxEntreprise.addActionListener(new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent e) {
		        JComboBox<?> jcmbType = (JComboBox<?>) e.getSource();
		        selectedComboEntNom = (String) jcmbType.getSelectedItem();
		      }
		    });
	
		JButton ButtonPDF = new JButton("Choix fichier...");
		ButtonPDF.setBounds(152, 219, 132, 23);
		ButtonPDF.addActionListener(this);
		contentPane.add(ButtonPDF);
		
		JButton btnDateDeMaintenant = new JButton("Date");
		btnDateDeMaintenant.setBounds(220, 124, 66, 23);
		btnDateDeMaintenant.addActionListener(this);
		contentPane.add(btnDateDeMaintenant);
		
		JLabel LabelLibelle = new JLabel("Libelle :");
		LabelLibelle.setBounds(28, 160, 132, 14);
		contentPane.add(LabelLibelle);
	

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(34, 313, 405, 70);
		contentPane.add(scrollPane);
		
		DefaultTableModel tableModel = new DefaultTableModel();
		JTable table = new JTable(tableModel);
		tableModel.addColumn("Logements");
		try {
			ResultSet rsTable = Requetes.RequeteInsertion.RequeteGetLogLocBati();
			int i = 0;
			rsTable.next();
			while ( i < rsTable.getRow()) {
				String jours = rsTable.getString("dateLoc").substring(8,10);
				String mois = rsTable.getString("dateLoc").substring(5,7);
				String annee = rsTable.getString("dateLoc").substring(0,4);
				String dateLocation = ( jours + "/" + mois + "/" + annee );
				String lib = rsTable.getString("lib");
				String nom = rsTable.getString("nom");
				String adr = rsTable.getString("adr");
				String cp = rsTable.getString("cp");
				String idlogement = rsTable.getString("idlogement");
				String idcontrat = rsTable.getString("idcontrat");
				tableModel.insertRow(0, new Object[] { "{" + dateLocation + "}"+" - "+ nom + " - " + lib  + " " + adr + " " + cp + " <" + idlogement + "> " + " [" + idcontrat + "] "});
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
					        String idLog = selectedData.substring(selectedData.lastIndexOf("<")+1,selectedData.lastIndexOf(">"));
					        String idCon = selectedData.substring(selectedData.lastIndexOf("[")+1,selectedData.lastIndexOf("]"));
					        String dateLoc = selectedData.substring(selectedData.lastIndexOf("{")+1,selectedData.lastIndexOf("}"));
					        System.out.println("Selected = Date de location: {" + dateLoc + "} Logement : <"+ idLog+"> Contrat : [" + idCon + "]" );
						} else {
								tableAllSelectedData.remove(selectedData);
								String idLog = selectedData.substring(selectedData.lastIndexOf("<")+1,selectedData.lastIndexOf(">"));
						        String idCon = selectedData.substring(selectedData.lastIndexOf("[")+1,selectedData.lastIndexOf("]"));
						        String dateLoc = selectedData.substring(selectedData.lastIndexOf("{")+1,selectedData.lastIndexOf("}"));
						        System.out.println("Unselected = Date de location: {" + dateLoc + "} Logement : <"+ idLog+"> Contrat : [" + idCon + "]" );
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
			this.textFieldDateFac.setText(dateCourrante);
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
		case "Nouvelle entreprise":
			new NouvelleEntreprise().setVisible(true);
			break;	
		case "Ajouter":	
			String datefact = textFieldDateFac.getText();				
			String chemin = textFieldRepPDF.getText() ;
			String pdf = textFieldNomPDF.getText() ;					
			int numfact = Integer.parseInt(textFieldNumFac.getText());		        
	        float total = Float.parseFloat(textFieldMontantTotal.getText());		        
	        String lib = textFieldLibelle.getText();		        
			try {
				int siren = Requetes.RequeteInsertion.RequeteGetSirenEntrepriseCombo(selectedComboEntNom);
				System.out.println(siren + " " + numfact + " " +lib + " " +total + " " +datefact + " " +chemin + " " + pdf);
				Requetes.RequeteInsertion.RequeteInsertChargeSupp(siren, numfact, lib, datefact, total, chemin, pdf);
				JOptionPane.showMessageDialog(frame, "Charge supplÃ©mentaire " + numfact + " insÃ©rÃ©e.");
			} catch (SQLException e3) {
				e3.printStackTrace();
			}			
			if(tableAllSelectedData.isEmpty()) { 

				this.dispose();
				new Accueil().setVisible(true);
				break;
			} else {
			      for (int counter = 0; counter < tableAllSelectedData.size(); counter++) { 		      
					// Pour les liaisons avec les loyers ( loue )
					String datePaiement = "01/01/2001";
					String modePaiement = " ";
					String numeroCheque = " ";
					float pourcentagepart = 0.0F;
			        String idlogement = (tableAllSelectedData.get(counter).substring(tableAllSelectedData.get(counter).lastIndexOf("<")+1,tableAllSelectedData.get(counter).lastIndexOf(">")));
				    String idcontrat = (tableAllSelectedData.get(counter).substring(tableAllSelectedData.get(counter).lastIndexOf("[")+1,tableAllSelectedData.get(counter).lastIndexOf("]")));
				 	String DATELOCATION = (tableAllSelectedData.get(counter).substring(tableAllSelectedData.get(counter).lastIndexOf("{")+1,tableAllSelectedData.get(counter).lastIndexOf("}")));
			        try {	

			        	int siren = Requetes.RequeteInsertion.RequeteGetSirenEntrepriseCombo(selectedComboEntNom);
			        	Requetes.RequeteInsertion.RequeteInsertSupplemente(idlogement, idcontrat, DATELOCATION, numfact, siren, pourcentagepart, modePaiement, datePaiement, numeroCheque);
			        } catch (SQLException e1) {
							e1.printStackTrace();
						}
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