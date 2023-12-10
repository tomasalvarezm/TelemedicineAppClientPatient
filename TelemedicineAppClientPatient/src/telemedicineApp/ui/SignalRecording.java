package telemedicineApp.ui;

import java.awt.BorderLayout;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import BITalino.BITalino;
import BITalino.BITalinoException;
import BITalino.BitalinoDemo;
import BITalino.Frame;
import connection.ClientPatient;
import telemedicineApp.pojos.BitalinoSignal;
import telemedicineApp.pojos.Patient;

import javax.bluetooth.RemoteDevice;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import java.awt.Font;

public class SignalRecording extends JFrame {

	private JPanel contentPane;
	private JButton start;
	private JButton stop;
	private JButton send;
	private JTextField macAddress;

	private ArrayList<Integer> dataFromBitalino = new ArrayList<Integer>();
	private BitalinoDemo bitalinoThread; // To manage the start and stop of the bitalino recording we use threads
	private LocalTime startTime;
	private String timeRecording;
	private JSeparator separator;
	private JLabel lblPlaceTheElectrodes;
	private JLabel lblTheMuscularBelly;
	private JLabel lblOnTheTriceps;

	public SignalRecording(JFrame patientMenu, ClientPatient client, Patient patient) {
		patientMenu.setVisible(false);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 462, 407);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		start = new JButton("");
		start.setToolTipText("Start recording");
		start.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				startTime = LocalTime.now();
				bitalinoThread = new BitalinoDemo(macAddress.getText());
				bitalinoThread.setDaemon(true);
				bitalinoThread.start();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				stop.setEnabled(true);
			}
		});
		start.setEnabled(false);
		start.setBackground(Color.WHITE);
		Image startImg = new ImageIcon(this.getClass().getResource("/start recording.PNG")).getImage();
		start.setIcon(new ImageIcon(startImg));
		start.setBounds(144, 237, 41, 33);
		contentPane.add(start);

		stop = new JButton("");
		stop.setToolTipText("Stop recording");
		stop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				BitalinoDemo.record = false;
				LocalTime currentTime = LocalTime.now();

				long minutes = ChronoUnit.MINUTES.between(startTime, currentTime);
				long seconds = ChronoUnit.SECONDS.between(startTime, currentTime);
				timeRecording = minutes + " minutes" + seconds + " seconds";
				dataFromBitalino = bitalinoThread.getDataFromBitalino();

				send.setEnabled(true);
			}
		});
		stop.setEnabled(false);
		stop.setBackground(Color.WHITE);
		Image stopImg = new ImageIcon(this.getClass().getResource("/stop recording.PNG")).getImage();
		stop.setIcon(new ImageIcon(stopImg));
		stop.setBounds(269, 237, 41, 33);
		contentPane.add(stop);

		macAddress = new JTextField();
		macAddress.setHorizontalAlignment(SwingConstants.CENTER);
		macAddress.setBackground(new Color(240, 240, 240));
		macAddress.setBorder(null);
		macAddress.setToolTipText("Fromat XX:XX:XX:XX:XX:XX ");
		macAddress.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (checkMACaddress(macAddress.getText())) {
					start.setEnabled(true);
				}
			}
		});
		macAddress.setBounds(114, 80, 225, 20);
		contentPane.add(macAddress);
		macAddress.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel(" BITalino MAC Address");
		lblNewLabel_1.setFont(new Font("Bookman Old Style", Font.PLAIN, 15));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setToolTipText("Fromat XX:XX:XX:XX:XX:XX ");
		lblNewLabel_1.setBounds(123, 44, 201, 25);
		contentPane.add(lblNewLabel_1);

		send = new JButton("Send");
		send.setEnabled(false);
		send.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				try {
					client.sendFunction("uploadsignal");
					BitalinoSignal bitalinoSignal = new BitalinoSignal();
					bitalinoSignal.setPatient_id(patient.getId());
					bitalinoSignal.setSignal_duration(timeRecording);
					bitalinoSignal.setDateSignal(LocalDate.now());
					bitalinoSignal.setData(dataFromBitalino);

					client.sendPhysiologicalParameters(bitalinoSignal);
					patientMenu.setVisible(true);
					SignalRecording.this.setVisible(false);
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(SignalRecording.this, "Problems connecting with server", "Message",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		send.setBounds(181, 334, 89, 23);
		contentPane.add(send);
		
		separator = new JSeparator();
		separator.setBounds(114, 100, 225, 2);
		contentPane.add(separator);
		
		JLabel lblNewLabel = new JLabel("Before start: \r\n");
		lblNewLabel.setFont(new Font("Bookman Old Style", Font.PLAIN, 11));
		lblNewLabel.setBounds(76, 132, 293, 20);
		contentPane.add(lblNewLabel);
		
		lblPlaceTheElectrodes = new JLabel("Place the electrodes in the rigth position. Two on ");
		lblPlaceTheElectrodes.setFont(new Font("Bookman Old Style", Font.PLAIN, 11));
		lblPlaceTheElectrodes.setBounds(76, 160, 293, 20);
		contentPane.add(lblPlaceTheElectrodes);
		
		lblTheMuscularBelly = new JLabel("the muscular belly of your thumb and the black one");
		lblTheMuscularBelly.setFont(new Font("Bookman Old Style", Font.PLAIN, 11));
		lblTheMuscularBelly.setBounds(76, 181, 293, 20);
		contentPane.add(lblTheMuscularBelly);
		
		lblOnTheTriceps = new JLabel("on the triceps.");
		lblOnTheTriceps.setFont(new Font("Bookman Old Style", Font.PLAIN, 11));
		lblOnTheTriceps.setBounds(76, 202, 293, 20);
		contentPane.add(lblOnTheTriceps);

		// CLOSING CONNECTION WHEN CLOSING FRAME
		WindowListener exitListener = (WindowListener) new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				try {
					client.sendFunction("logout");
					client.closeConnection();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(SignalRecording.this, "Problems closing connection", "Message",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		this.addWindowListener(exitListener);
	}

	private boolean checkMACaddress(String macAddress) {
		String format = "\\d{2}:\\d{2}:\\d{2}:\\d{2}:\\d{2}:\\d{2}";
		Pattern pattern = Pattern.compile(format);
		Matcher matcher = pattern.matcher(macAddress);
		return matcher.matches();
	}
}
