package vue.consultation;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import vue.Accueil;
import vue.IRL;
import vue.InformationsBailleur;
import vue.Quittances;
import vue.insertion.NouveauEntretien;
import vue.insertion.NouveauTravaux;
import vue.insertion.NouvelleChargeSupp;
import vue.insertion.NouvelleFactureEau;
import vue.insertion.NouvelleFactureElectricite;
import vue.insertion.NouvelleLocation;
import vue.insertion.NouvelleProtectionJuridique;
import vue.insertion.NouvelleTaxeFonciere;

public class LocationsChargesAnciennes extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LocationsChargesAnciennes frame = new LocationsChargesAnciennes();
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
	public LocationsChargesAnciennes() {
		setBackground(new Color(240, 240, 240));
		setTitle("Charges anciennes locations");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 960, 480);
		
		JMenuBar menuBarTop = new JMenuBar();
		menuBarTop.setMargin(new Insets(0, 5, 0, 5));
		setJMenuBar(menuBarTop);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 49, 914, 278);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
			},
			new String[] {
				"Libellé", "Ordure ménagère", "Taxe foncière", "Partie commune", "Facture électrique", "Prime", "Prime jurisprudence", "Année"
			}
		));
		scrollPane.setViewportView(table);
		
		JLabel TitreChargesAnciennesLocations = new JLabel("Charges anciennes locations");
		TitreChargesAnciennesLocations.setFont(new Font("Tahoma", Font.BOLD, 20));
		TitreChargesAnciennesLocations.setBounds(10, 10, 477, 29);
		contentPane.add(TitreChargesAnciennesLocations);
		
		JButton ButtonCharger = new JButton("Charger");
		ButtonCharger.addActionListener(this);
		ButtonCharger.setBounds(35, 376, 85, 21);
		contentPane.add(ButtonCharger);
		
		JButton ButtonInserer = new JButton("Insérer");
		ButtonInserer.addActionListener(this);
		ButtonInserer.setBounds(230, 376, 85, 21);
		contentPane.add(ButtonInserer);
		
		JButton ButtonMiseJour = new JButton("Mise à jour");
		ButtonMiseJour.addActionListener(this);
		ButtonMiseJour.setBounds(414, 376, 85, 21);
		contentPane.add(ButtonMiseJour);
		
		JButton ButtonSupprimer = new JButton("Supprimer");
		ButtonSupprimer.addActionListener(this);
		ButtonSupprimer.setBounds(611, 376, 85, 21);
		contentPane.add(ButtonSupprimer);
		
		JButton ButtonAnnuler = new JButton("Annuler");
		ButtonAnnuler.addActionListener(this);
		ButtonAnnuler.setBounds(816, 376, 85, 21);
		contentPane.add(ButtonAnnuler);
	}
	
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
			case "Accueil":
				this.dispose();
				new Accueil().setVisible(true);
				break;
				
			case "Anciennes location":
				this.dispose();
				new LocationsAnciennes().setVisible(true);
				break;
			
			case "Anciens travaux":
				this.dispose();
				new TravauxAnciens().setVisible(true);
				break;
				
			case "Electricite":
				this.dispose();
				new NouvelleFactureElectricite().setVisible(true);
				break;
				
			case "Entretien":
				this.dispose();
				new NouveauEntretien().setVisible(true);
				break;
				
			case "Facture d'eau":
				this.dispose();
				new NouvelleFactureEau().setVisible(true);
				break;
			
			case "Impositions":
				this.dispose();
				new Impositions().setVisible(true);
				break;
				
			case "Informations bailleur":
				this.dispose();
				new InformationsBailleur().setVisible(true);
				break;
				
			case "IRL":
				this.dispose();
				new IRL().setVisible(true);
				break;
				
			case "Locations en cours":
				this.dispose();
				new LocationsEnCours().setVisible(true);
				break;
			
			case "Nouveaux travaux":
				this.dispose();
				new NouveauTravaux().setVisible(true);
				break;
				
			case "Nouvelle location":
				this.dispose();
				new NouvelleLocation().setVisible(true);
				break;
				
			case "Protection juridique":
				this.dispose();
				new NouvelleProtectionJuridique().setVisible(true);
				break;
				
			case "Quittances":
				this.dispose();
				new Quittances().setVisible(true);
				break;
			
			case "Taxe fonciere":
				this.dispose();
				new NouvelleTaxeFonciere().setVisible(true);
				break;
			case "Autre":
				this.dispose();
				new NouvelleChargeSupp().setVisible(true);
				break;
			case "Travaux en cours":
				this.dispose();
				new TravauxEnCours().setVisible(true);
				break;
				
			case "Inserer" :
				this.dispose();
				new NouvelleLocation().setVisible(true);
				break;
			
			case "Annuler" :
				this.dispose();
				break;
       
			default:
				System.out.println("Choix incorrect");
				break;
		}
	}
}