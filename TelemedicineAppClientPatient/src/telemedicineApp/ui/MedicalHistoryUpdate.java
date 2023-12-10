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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.SwingConstants;
import java.awt.Font;

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
	public MedicalHistoryUpdate(JFrame patientMenu, ClientPatient client, Patient patient) {
		setTitle("Patient");
		patientMenu.setVisible(false);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 505, 340);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		drowsiness = new JCheckBox("Drowsiness");
		drowsiness.setBounds(82, 146, 97, 23);
		contentPane.add(drowsiness);

		dizziness = new JCheckBox("Dizziness");
		dizziness.setBounds(82, 182, 97, 23);
		contentPane.add(dizziness);

		JLabel lblNewLabel = new JLabel("SELECT YOU MEDICATION");
		lblNewLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 11));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(151, 30, 176, 16);
		contentPane.add(lblNewLabel);

		JLabel lblIndicateYourSymptoms = new JLabel("INDICATE YOUR SYMPTOMS");
		lblIndicateYourSymptoms.setFont(new Font("Bookman Old Style", Font.PLAIN, 11));
		lblIndicateYourSymptoms.setHorizontalAlignment(SwingConstants.CENTER);
		lblIndicateYourSymptoms.setBounds(145, 120, 195, 16);
		contentPane.add(lblIndicateYourSymptoms);

		nausea = new JCheckBox("Nausea");
		nausea.setBounds(189, 146, 97, 23);
		contentPane.add(nausea);

		alucinations = new JCheckBox("Alucinations");
		alucinations.setBounds(189, 182, 97, 23);
		contentPane.add(alucinations);

		swelling = new JCheckBox("Swelling");
		swelling.setBounds(304, 146, 97, 23);
		contentPane.add(swelling);

		lackOfAppetite = new JCheckBox("Lack of appetite");
		lackOfAppetite.setBounds(304, 182, 130, 23);
		contentPane.add(lackOfAppetite);

		JButton update = new JButton("Save changes");
		update.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (levodopa.isSelected() || pramipexol.isSelected()) {
					MedicalHistory medicalHistory = new MedicalHistory();
					medicalHistory.setSymptoms(getChosenSymptoms());
					medicalHistory.setDate_medhist(LocalDate.now());
					medicalHistory.setPatient_id(patient.getId());
					if (levodopa.isSelected()) {
						medicalHistory.setMedication(Medication.LEVODOPA);
					}
					if (pramipexol.isSelected()) {
						medicalHistory.setMedication(Medication.PRAMIPEXOL);
					}
					try {

						client.sendFunction("modifysymptoms"); // message "modifysymptoms" sent to server

						// To send to the server the MedicalHistory updated:
						if (client.newMedicalHistory(medicalHistory)) {
							JOptionPane.showMessageDialog(MedicalHistoryUpdate.this, "Medical history updated",
									"Message", JOptionPane.PLAIN_MESSAGE);
							patientMenu.setVisible(true);
							MedicalHistoryUpdate.this.setVisible(false);
						}
					} catch (IOException ex) {
						JOptionPane.showMessageDialog(MedicalHistoryUpdate.this, "Problems connecting with server",
								"Message", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(MedicalHistoryUpdate.this, "Choose your medication", "Message",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		update.setBounds(174, 251, 130, 39);
		contentPane.add(update);

		levodopa = new JRadioButton("Levodopa");
		levodopa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (levodopa.isSelected()) {
					pramipexol.setSelected(false);
				}
			}
		});
		levodopa.setBounds(95, 62, 109, 23);
		contentPane.add(levodopa);

		pramipexol = new JRadioButton("Pramipexol");
		pramipexol.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (pramipexol.isSelected()) {
					levodopa.setSelected(false);
				}
			}
		});
		pramipexol.setBounds(279, 62, 109, 23);
		contentPane.add(pramipexol);

		// CLOSING CONNECTION WHEN CLOSING FRAME
		WindowListener exitListener = (WindowListener) new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				try {
					client.sendFunction("logout");
					client.closeConnection();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(MedicalHistoryUpdate.this, "Problems closing connection", "Message",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		this.addWindowListener(exitListener);

	}

	private ArrayList<Symptom> getChosenSymptoms() {
		ArrayList<Symptom> symptoms = new ArrayList<Symptom>();
		if (drowsiness.isSelected()) {
			Symptom drowsiness = new Symptom("Drowsiness");
			symptoms.add(drowsiness);
		}
		if (dizziness.isSelected()) {
			Symptom dizziness = new Symptom("Dizziness");
			symptoms.add(dizziness);
		}
		if (nausea.isSelected()) {
			Symptom nausea = new Symptom("Nausea");
			symptoms.add(nausea);
		}
		if (alucinations.isSelected()) {
			Symptom alucinations = new Symptom("Alucinations");
			symptoms.add(alucinations);
		}
		if (swelling.isSelected()) {
			Symptom swelling = new Symptom("Swelling");
			symptoms.add(swelling);
		}
		if (lackOfAppetite.isSelected()) {
			Symptom lackOfAppetite = new Symptom("Lack of appetite");
			symptoms.add(lackOfAppetite);
		}
		return symptoms;
	}
}
