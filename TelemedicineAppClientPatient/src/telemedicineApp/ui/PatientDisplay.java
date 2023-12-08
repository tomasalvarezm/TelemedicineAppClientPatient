package telemedicineApp.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import connection.ClientPatient;
import telemedicineApp.pojos.Patient;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class PatientDisplay extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public PatientDisplay(JFrame appDisplay, ClientPatient client, Patient patient) {
		appDisplay.setVisible(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 562, 360);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.menu);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton uploadSignal = new JButton("Upload Signal");
		uploadSignal.setFont(new Font("Tahoma", Font.PLAIN, 13));
		uploadSignal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame signalRecording = new SignalRecording(PatientDisplay.this, client, patient);
				signalRecording.setVisible(true);
			}
		});
		uploadSignal.setBounds(169, 221, 178, 46);
		contentPane.add(uploadSignal);
		
		JButton changeSymptoms = new JButton("Modify my symptoms");
		changeSymptoms.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame medicalHistory = new MedicalHistoryUpdate(PatientDisplay.this, client, patient);
				medicalHistory.setVisible(true);
			}
		});
		changeSymptoms.setFont(new Font("Tahoma", Font.PLAIN, 13));
		changeSymptoms.setBounds(169, 143, 178, 46);
		contentPane.add(changeSymptoms);
		
		JLabel lblWelcome = new JLabel("");
		Image welcolmeImg = new ImageIcon(this.getClass().getResource("/welcome.png")).getImage();
		lblWelcome.setIcon(new ImageIcon(welcolmeImg));
		lblWelcome.setBounds(137, 37, 114, 46);
		contentPane.add(lblWelcome);
		
		JLabel username = new JLabel(", " + patient.getName());
		username.setToolTipText("");
		username.setLabelFor(this);
		username.setForeground(Color.BLACK);
		username.setBackground(Color.WHITE);
		username.setFont(new Font("Arial", Font.BOLD, 20));
		username.setBounds(253, 37, 202, 46);
		contentPane.add(username);
		
		JButton logout = new JButton("Log out");
		logout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				try {
					client.sendFunction("logout");
					client.closeConnection();
					System.exit(0);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(PatientDisplay.this, "Problems closing connection", "Message",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		logout.setBounds(10, 287, 89, 23);
		contentPane.add(logout);
		
	}
}
