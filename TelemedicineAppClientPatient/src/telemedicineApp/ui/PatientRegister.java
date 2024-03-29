package telemedicineApp.ui;

import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JCalendar;

import connection.ClientPatient;
import telemedicineApp.pojos.Doctor;
import telemedicineApp.pojos.Patient;
import telemedicineApp.pojos.Sex;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import java.awt.Color;
import javax.swing.JSeparator;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class PatientRegister extends JFrame {

	private JPanel contentPane;
	private JTextField id;
	private JTextField name;
	private JTextField email;
	private JTextField phoneNumber;
	private JComboBox sex;
	private LocalDate dob;
	private JComboBox doctorBox;
	private JCalendar calendar;

	/**
	 * Create the frame.
	 */
	public PatientRegister(JFrame appDisplay, ClientPatient client, ArrayList<Doctor> doctors) {
		setTitle("Patient");
		appDisplay.setVisible(false);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 636, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		id = new JTextField();
		id.setBackground(new Color(240, 240, 240));
		id.setBounds(new Rectangle(112, 53, 218, 20));
		id.setBorder(null);
		contentPane.add(id);
		id.setColumns(10);

		JLabel lblNewLabel = new JLabel("ID :");
		lblNewLabel.setBounds(21, 62, 28, 14);
		contentPane.add(lblNewLabel);

		name = new JTextField();
		name.setBackground(new Color(240, 240, 240));
		name.setColumns(10);
		name.setBounds(new Rectangle(112, 84, 218, 20));
		name.setBorder(null);
		contentPane.add(name);

		email = new JTextField();
		email.setBackground(new Color(240, 240, 240));
		email.setColumns(10);
		email.setBounds(new Rectangle(112, 116, 218, 20));
		email.setBorder(null);
		contentPane.add(email);

		phoneNumber = new JTextField();
		phoneNumber.setBackground(new Color(240, 240, 240));
		phoneNumber.setColumns(10);
		phoneNumber.setBounds(new Rectangle(112, 147, 218, 20));
		phoneNumber.setBorder(null);
		contentPane.add(phoneNumber);

		JLabel lblFullName = new JLabel("Full name : ");
		lblFullName.setBounds(21, 93, 71, 14);
		contentPane.add(lblFullName);

		JLabel lblEmail = new JLabel("Email :");
		lblEmail.setBounds(21, 124, 57, 14);
		contentPane.add(lblEmail);

		JLabel lblPhoneNumber = new JLabel("Phone number:");
		lblPhoneNumber.setBounds(21, 155, 95, 14);
		contentPane.add(lblPhoneNumber);

		sex = new JComboBox();
		sex.setModel(new DefaultComboBoxModel(new String[] { "Male", "Female" }));
		sex.setBounds(112, 194, 95, 22);
		contentPane.add(sex);

		JLabel lblSex = new JLabel("Sex :");
		lblSex.setBounds(21, 198, 95, 14);
		contentPane.add(lblSex);

		JLabel lblAge = new JLabel("Date of birth :");
		lblAge.setBounds(455, 59, 95, 14);
		contentPane.add(lblAge);

		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		separator.setBounds(112, 73, 218, 2);
		contentPane.add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.BLACK);
		separator_1.setBounds(112, 104, 218, 2);
		contentPane.add(separator_1);

		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(Color.BLACK);
		separator_2.setBounds(112, 136, 218, 2);
		contentPane.add(separator_2);

		JSeparator separator_3 = new JSeparator();
		separator_3.setForeground(Color.BLACK);
		separator_3.setBounds(112, 167, 218, 2);
		contentPane.add(separator_3);

		JButton back = new JButton("");
		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				try {
					client.sendFunction("back");
					appDisplay.setVisible(true);
					PatientRegister.this.setVisible(false);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(PatientRegister.this, "Problems connecting with server",
							"Message", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		back.setBounds(21, 332, 77, 18);
		Image backImg = new ImageIcon(this.getClass().getResource("/back.png")).getImage();
		back.setIcon(new ImageIcon(backImg));
		contentPane.add(back);

		JButton register = new JButton("");
		register.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (id == null) {
					JOptionPane.showMessageDialog(PatientRegister.this, "Introduce your ID", "Message",
							JOptionPane.WARNING_MESSAGE);
				} else if (!validateEmail(email.getText())) {
					JOptionPane.showMessageDialog(PatientRegister.this, "Invalid email", "Message",
							JOptionPane.WARNING_MESSAGE);
				} else if (dob == null) {
					JOptionPane.showMessageDialog(PatientRegister.this, "Select your date of birth", "Message",
							JOptionPane.WARNING_MESSAGE);
				} else {
					try {
						Patient patient = new Patient();
						patient.setId(id.getText());
						patient.setName(name.getText());
						patient.setEmail(email.getText());
						patient.setPhoneNumber(Integer.parseInt(phoneNumber.getText()));
						patient.setDob(dob);
						patient.setAge(getAge(dob));
						if (sex.getSelectedItem().toString().equalsIgnoreCase("male")) {
							patient.setSex(Sex.MALE);
						} else {
							patient.setSex(Sex.FEMALE);
						}

						client.sendFunction("register");

						Doctor doctor = client.getDoctorByName(doctorBox.getSelectedItem().toString());
						patient.setDoctor(doctor);

						// check if already exists
						if (client.registerPatient(patient)) {
							JOptionPane.showMessageDialog(PatientRegister.this, "Successfully registered", "Message",
									JOptionPane.PLAIN_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(PatientRegister.this, "Existing account. Go to log in", "Message",
									JOptionPane.PLAIN_MESSAGE);
						}
						appDisplay.setVisible(true);
						PatientRegister.this.setVisible(false);

					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(PatientRegister.this, "Invalid phone number", "Message",
								JOptionPane.WARNING_MESSAGE);
					} catch (IOException | ClassNotFoundException e1) {
						JOptionPane.showMessageDialog(PatientRegister.this, "Problems connecting with server",
								"Message", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		register.setBounds(520, 330, 90, 20);
		Image registerImg = new ImageIcon(this.getClass().getResource("/register.png")).getImage();
		register.setIcon(new ImageIcon(registerImg));
		contentPane.add(register);

		JLabel lblDoctor = new JLabel("Doctor :");
		lblDoctor.setBounds(21, 242, 65, 16);
		contentPane.add(lblDoctor);

		doctorBox = new JComboBox();
		doctorBox.setBounds(112, 239, 139, 22);
		contentPane.add(doctorBox);
		
		calendar = new JCalendar();
		calendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getOldValue() != null) {
					int year = calendar.getYearChooser().getYear();
					int month = calendar.getMonthChooser().getMonth() + 1; //Don't know why it gives the month value minus one
					int day = calendar.getDayChooser().getDay();
					dob = LocalDate.of(year, month, day);
				}
			}
		});
		calendar.setBounds(392, 84, 198, 153);
		contentPane.add(calendar);
		for (Doctor d : doctors) {
			doctorBox.addItem(d.getName());
		}

		
		
		// CLOSING CONNECTION WHEN CLOSING FRAME
		WindowListener exitListener = (WindowListener) new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				try {
				    client.sendFunction("back");
				    appDisplay.setVisible(true);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(PatientRegister.this, "Problems connecting with server", "Message",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		this.addWindowListener(exitListener);

	}

	private boolean validateEmail(String email) {
		// String of available characters and pattern
		Pattern pattern = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@([a-z]+(\\.[a-z]+)+)"); 
		Matcher matcher = pattern.matcher(email);
		return matcher.find();
	}

	private int getAge(LocalDate birth) {
		int age = 0;
		if (LocalDate.now().getMonthValue() > birth.getMonthValue()) {
			age = LocalDate.now().getYear() - birth.getYear();
		} else if (LocalDate.now().getDayOfMonth() == birth.getMonthValue()) {
			if (LocalDate.now().getDayOfMonth() >= birth.getDayOfMonth()) {
				age = LocalDate.now().getYear() - birth.getYear();
			}
		} else {
			age = LocalDate.now().getYear() - birth.getYear() - 1;
		}
		return age;
	}
}
