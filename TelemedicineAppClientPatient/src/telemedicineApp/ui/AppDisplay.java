package telemedicineApp.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.Font;
import javax.swing.SwingConstants;

public class AppDisplay extends JFrame {

	private JPanel contentPane;
	private JTextField userID;
	private ClientPatient client;

	/**
	 * Create the frame.
	 */
	public AppDisplay() {
		setTitle("Patient");

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
					client.sendFunction("login");// message "login" sent to server
					Patient patient = client.checkPatient(userID.getText());
					if (patient != null) {
						JFrame patientMenu = new PatientMenu(AppDisplay.this, client, patient);
						patientMenu.setVisible(true);
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

		JLabel lblNewLabel = new JLabel("ID :");
		lblNewLabel.setBounds(130, 128, 29, 14);
		contentPane.add(lblNewLabel);

		JLabel imgLabel = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/signal.PNG")).getImage();
		imgLabel.setIcon(new ImageIcon(img));
		imgLabel.setBounds(311, 11, 205, 117);
		contentPane.add(imgLabel);

		JButton register = new JButton("Register");
		register.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				ArrayList<Doctor> doctors = new ArrayList<Doctor>();
				try {
					client.sendFunction("register");// message "register" sent to server
					doctors = (ArrayList<Doctor>) client.getAllDoctors();
					JFrame patientRegister = new PatientRegister(AppDisplay.this, client, doctors);
					patientRegister.setVisible(true);
				} catch (IOException | ClassNotFoundException e1) {
					JOptionPane.showMessageDialog(AppDisplay.this, "Problems connecting with server", "Message",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		register.setBounds(183, 224, 230, 23);
		contentPane.add(register);

		JButton manual = new JButton("");
		Image manualImg = new ImageIcon(this.getClass().getResource("/manual.png")).getImage();
		manual.setIcon(new ImageIcon(manualImg));
		manual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame instructions = new Instructions(AppDisplay.this);
				instructions.setVisible(true);
			}
		});
		manual.setBounds(267, 269, 73, 54);
		contentPane.add(manual);

		JSeparator separator = new JSeparator();
		separator.setBounds(173, 145, 269, 2);
		contentPane.add(separator);
		
		JLabel lblNewLabel_1 = new JLabel("EMG MONITORING");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(Color.RED);
		lblNewLabel_1.setFont(new Font("Bookman Old Style", Font.BOLD, 17));
		lblNewLabel_1.setBounds(109, 43, 192, 59);
		contentPane.add(lblNewLabel_1);

		// CLOSING CONNECTION WHEN EXIT
		WindowListener exitListener = (WindowListener) new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				try {
					client.closeConnection();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(AppDisplay.this, "Problems closing connection", "Message",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		this.addWindowListener(exitListener);
	}
}
