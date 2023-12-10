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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

public class PatientMenu extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public PatientMenu(JFrame appDisplay, ClientPatient client, Patient patient) {
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
				JFrame signalRecording = new SignalRecording(PatientMenu.this, client, patient);
				signalRecording.setVisible(true);
			}
		});
		uploadSignal.setBounds(171, 202, 178, 46);
		contentPane.add(uploadSignal);

		JButton changeSymptoms = new JButton("Modify my symptoms");
		changeSymptoms.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame medicalHistory = new MedicalHistoryUpdate(PatientMenu.this, client, patient);
				medicalHistory.setVisible(true);
			}
		});
		changeSymptoms.setFont(new Font("Tahoma", Font.PLAIN, 13));
		changeSymptoms.setBounds(171, 122, 178, 46);
		contentPane.add(changeSymptoms);
		Image welcolmeImg = new ImageIcon(this.getClass().getResource("/welcome.png")).getImage();

		JLabel username = new JLabel(patient.getName());
		username.setFont(new Font("Bookman Old Style", Font.PLAIN, 11));
		username.setBounds(85, 32, 202, 16);
		contentPane.add(username);
		
		JLabel userLabel = new JLabel("");
		Image userImg = new ImageIcon(this.getClass().getResource("/user.png")).getImage();
		userLabel.setIcon(new ImageIcon(userImg));
		userLabel.setBounds(40, 22, 45, 39);
		contentPane.add(userLabel);

		JButton logout = new JButton("Log out");
		logout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				try {
					// when the user logs out we close the connection with the server
					client.sendFunction("logout");
					client.closeConnection();
					System.exit(0);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(PatientMenu.this, "Problems closing connection", "Message",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		Image logOutImg = new ImageIcon(this.getClass().getResource("/logOut.png")).getImage();
		logout.setIcon(new ImageIcon(logOutImg));
		logout.setBounds(417, 29, 98, 23);
		contentPane.add(logout);

		// CLOSING CONNECTION WHEN CLOSING FRAME
		WindowListener exitListener = (WindowListener) new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				try {
					client.sendFunction("logout");
					client.closeConnection();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(PatientMenu.this, "Problems closing connection", "Message",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		this.addWindowListener(exitListener);
	}

}
