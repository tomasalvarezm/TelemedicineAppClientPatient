package telemedicineApp.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class MedicalHistory extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MedicalHistory frame = new MedicalHistory();
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
	public MedicalHistory() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 505, 340);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JCheckBox drowsiness = new JCheckBox("Drowsiness");
		drowsiness.setBounds(41, 139, 97, 23);
		contentPane.add(drowsiness);
		
		JCheckBox dizziness = new JCheckBox("Dizziness");
		dizziness.setBounds(41, 175, 97, 23);
		contentPane.add(dizziness);
		
		JLabel lblNewLabel = new JLabel("Select your medication");
		lblNewLabel.setBounds(23, 24, 163, 14);
		contentPane.add(lblNewLabel);
		
		JComboBox medication = new JComboBox();
		medication.setModel(new DefaultComboBoxModel(new String[] {"Levodopa", "Pramipexol"}));
		medication.setBounds(23, 49, 200, 22);
		contentPane.add(medication);
		
		JLabel lblIndicateYourSymptoms = new JLabel("Indicate your symptoms");
		lblIndicateYourSymptoms.setBounds(23, 118, 163, 14);
		contentPane.add(lblIndicateYourSymptoms);
		
		JCheckBox nausea = new JCheckBox("Nausea");
		nausea.setBounds(140, 139, 97, 23);
		contentPane.add(nausea);
		
		JCheckBox alucinations = new JCheckBox("Alucinations");
		alucinations.setBounds(140, 175, 97, 23);
		contentPane.add(alucinations);
		
		JCheckBox swelling = new JCheckBox("Swelling");
		swelling.setBounds(255, 139, 97, 23);
		contentPane.add(swelling);
		
		JCheckBox lackOfAppetite = new JCheckBox("Lack of appetite");
		lackOfAppetite.setBounds(255, 175, 130, 23);
		contentPane.add(lackOfAppetite);
	}
}
