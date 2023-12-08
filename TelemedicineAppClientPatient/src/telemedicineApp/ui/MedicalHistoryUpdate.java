package telemedicineApp.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import connection.ClientPatient;
import telemedicineApp.pojos.MedicalHistory;
import telemedicineApp.pojos.Medication;
import telemedicineApp.pojos.Patient;
import telemedicineApp.pojos.Symptom;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class MedicalHistoryUpdate extends JFrame {

	private JPanel contentPane;
	private JRadioButton levodopa;
	private JRadioButton pramipexol;
	private JCheckBox drowsiness;
	private JCheckBox dizziness;
	private JCheckBox nausea;
	private JCheckBox alucinations;
	private JCheckBox swelling;
	private JCheckBox lackOfAppetite;
	
	
	/**
	 * Create the frame.
	 */
	public MedicalHistoryUpdate(JFrame patientDisplay, ClientPatient client, Patient patient) {
		patientDisplay.setVisible(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 505, 340);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		drowsiness = new JCheckBox("Drowsiness");
		drowsiness.setBounds(76, 145, 97, 23);
		contentPane.add(drowsiness);
		
		dizziness = new JCheckBox("Dizziness");
		dizziness.setBounds(76, 181, 97, 23);
		contentPane.add(dizziness);
		
		JLabel lblNewLabel = new JLabel("Select your medication");
		lblNewLabel.setBounds(156, 30, 163, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblIndicateYourSymptoms = new JLabel("Indicate your symptoms");
		lblIndicateYourSymptoms.setBounds(156, 124, 163, 14);
		contentPane.add(lblIndicateYourSymptoms);
		
		nausea = new JCheckBox("Nausea");
		nausea.setBounds(175, 145, 97, 23);
		contentPane.add(nausea);
		
		alucinations = new JCheckBox("Alucinations");
		alucinations.setBounds(175, 181, 97, 23);
		contentPane.add(alucinations);
		
		swelling = new JCheckBox("Swelling");
		swelling.setBounds(290, 145, 97, 23);
		contentPane.add(swelling);
		
		lackOfAppetite = new JCheckBox("Lack of appetite");
		lackOfAppetite.setBounds(290, 181, 130, 23);
		contentPane.add(lackOfAppetite);
		
		JButton update = new JButton("Save changes");
		update.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(levodopa.isSelected() || pramipexol.isSelected()) {
					MedicalHistory medicalHistory = new MedicalHistory();
					medicalHistory.setSymptoms(getChosenSymptoms());
					medicalHistory.setDate_medhist(LocalDate.now());
					medicalHistory.setPatient_id(patient.getId());
					if(levodopa.isSelected()) {
						medicalHistory.setMedication(Medication.LEVODOPA);
					}
					if(pramipexol.isSelected()) {
						medicalHistory.setMedication(Medication.PRAMIPEXOL);
					}
					try {
						
						client.sendFunction("modifysymptoms");
						
						if(client.newMedicalHistory(medicalHistory)) {
							JOptionPane.showMessageDialog(MedicalHistoryUpdate.this, "Medical history updated", "Message",
									JOptionPane.PLAIN_MESSAGE);
							//regresar al frame anterior
						}
					}catch(IOException ex) {
						JOptionPane.showMessageDialog(MedicalHistoryUpdate.this, "Problems connecting with server", "Message",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(MedicalHistoryUpdate.this, "Choose your medication", "Message",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		update.setBounds(181, 251, 130, 39);
		contentPane.add(update);
		
		levodopa = new JRadioButton("Levodopa");
		levodopa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(levodopa.isSelected()) {
					pramipexol.setSelected(false);
				}
			}
		});
		levodopa.setBounds(88, 51, 109, 23);
		contentPane.add(levodopa);
		
		pramipexol = new JRadioButton("Pramipexol");
		pramipexol.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(pramipexol.isSelected()) {
					levodopa.setSelected(false);
				}
			}
		});
		pramipexol.setBounds(265, 51, 109, 23);
		contentPane.add(pramipexol);
	}
	
	private ArrayList<Symptom> getChosenSymptoms() {
		ArrayList<Symptom> symptoms = new ArrayList<Symptom>(); 
		if(drowsiness.isSelected()) {
			Symptom drowsiness = new Symptom("Drowsiness");
			symptoms.add(drowsiness);
		}
		if(dizziness.isSelected()) {
			Symptom dizziness = new Symptom("Dizziness");
			symptoms.add(dizziness);
		}
		if(nausea.isSelected()) {
			Symptom nausea = new Symptom("Nausea");
			symptoms.add(nausea);
		}
		if(alucinations.isSelected()) {
			Symptom alucinations = new Symptom("Alucinations");
			symptoms.add(alucinations);
		}
		if(swelling.isSelected()) {
			Symptom swelling = new Symptom("Swelling");
			symptoms.add(swelling);
		}
		if(lackOfAppetite.isSelected()) {
			Symptom lackOfAppetite = new Symptom("Lack of appetite");
			symptoms.add(lackOfAppetite);
		}
		return symptoms;
	}
}
