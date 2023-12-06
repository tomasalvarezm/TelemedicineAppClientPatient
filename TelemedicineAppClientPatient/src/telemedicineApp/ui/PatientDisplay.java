package telemedicineApp.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import telemedicineApp.pojos.Patient;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;

public class PatientDisplay extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public PatientDisplay(JFrame appDisplay, Patient patient) {
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
				JFrame signalRecording = new SignalRecording(PatientDisplay.this, patient);
				signalRecording.setVisible(true);
			}
		});
		uploadSignal.setBounds(169, 221, 178, 46);
		contentPane.add(uploadSignal);
		
		JButton changeSymptoms = new JButton("Modify my symptoms");
		changeSymptoms.setFont(new Font("Tahoma", Font.PLAIN, 13));
		changeSymptoms.setBounds(169, 143, 178, 46);
		contentPane.add(changeSymptoms);
		
		JLabel lblWelcome = new JLabel("");
		Image welcolmeImg = new ImageIcon(this.getClass().getResource("/welcome.png")).getImage();
		lblWelcome.setIcon(new ImageIcon(welcolmeImg));
		lblWelcome.setBounds(137, 37, 114, 46);
		contentPane.add(lblWelcome);
		
		JLabel username = new JLabel(", User !");
		username.setToolTipText("");
		username.setLabelFor(this);
		username.setForeground(Color.BLACK);
		username.setBackground(Color.WHITE);
		username.setFont(new Font("Arial", Font.BOLD, 20));
		username.setBounds(253, 37, 156, 46);
		contentPane.add(username);
		
	}
	
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PatientDisplay frame = new PatientDisplay();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/
}
