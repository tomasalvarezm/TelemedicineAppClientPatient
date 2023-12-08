package telemedicineApp.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import connection.ClientPatient;
import telemedicineApp.pojos.Doctor;
import telemedicineApp.pojos.Patient;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AppDisplay extends JFrame {

	private JPanel contentPane;
	private JTextField userID;
	private JPasswordField password;
	private ClientPatient client;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppDisplay frame = new AppDisplay();
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
	public AppDisplay() {
		
		client = new ClientPatient("localhost", 9000);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 639, 386);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton logIn = new JButton("Log in");
		logIn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				try {
					client.sendFunction("login");
					Patient patient = client.checkPatient(userID.getText());
					if(patient != null) {
						JFrame patientDisplay = new PatientDisplay(AppDisplay.this, client, patient);
						patientDisplay.setVisible(true);	
					} else {
						JOptionPane.showMessageDialog(AppDisplay.this, "You need to register first!", "Message",
								JOptionPane.WARNING_MESSAGE);
					}
				} catch (ClassNotFoundException | IOException e1) {
					JOptionPane.showMessageDialog(AppDisplay.this, "Problems connecting with server", "Message",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		logIn.setBounds(183, 190, 230, 23);
		contentPane.add(logIn);
		
		userID = new JTextField();
		userID.setBounds(173, 125, 269, 20);
		userID.setBackground(new Color(240, 240, 240));
		userID.setBorder(null);
		contentPane.add(userID);
		userID.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("User ID :");
		lblNewLabel.setBounds(93, 128, 77, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setBounds(93, 159, 63, 14);
		contentPane.add(lblPassword);
		
		password = new JPasswordField();
		password.setBounds(173, 156, 269, 20);
		password.setBackground(new Color(240, 240, 240));
		password.setBorder(null);
		contentPane.add(password);
		
		JLabel imgLabel = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/signal.PNG")).getImage();
		imgLabel.setIcon(new ImageIcon(img));
		imgLabel.setBounds(220, 11, 205, 117);
		contentPane.add(imgLabel);
		
		JButton register = new JButton("Register");
		register.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				ArrayList<Doctor> doctors = new ArrayList<Doctor>();
				try {
					client.sendFunction("register");
					doctors = (ArrayList<Doctor>) client.getAllDoctors();
					
				} catch (IOException | ClassNotFoundException e1) {
					JOptionPane.showMessageDialog(AppDisplay.this, "Problems connecting with server", "Message",
							JOptionPane.ERROR_MESSAGE);
				}
				JFrame patientRegister = new PatientRegister(AppDisplay.this, client, doctors);
				patientRegister.setVisible(true);
			}
		});
		register.setBounds(183, 224, 230, 23);
		contentPane.add(register);
		
		JButton manual = new JButton("");
		Image manualImg = new ImageIcon(this.getClass().getResource("/manual.png")).getImage();
		manual.setIcon(new ImageIcon(manualImg));
		manual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		manual.setBounds(267, 269, 73, 54);
		contentPane.add(manual);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(173, 145, 269, 2);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(173, 176, 269, 2);
		contentPane.add(separator_1);
	}
}
