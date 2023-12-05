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
import telemedicineApp.pojos.Patient;

import javax.bluetooth.RemoteDevice;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
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

public class SignalRecording extends JFrame {

	private JPanel contentPane;
	private JButton start;
	private JButton stop;
	private JTextField macAddress;
	
	private ArrayList<Integer> dataFromBitalino = new ArrayList<Integer>();
	//private BitalinoDemo bitalino = new BitalinoDemo();
	private Frame[] frame;
    private BITalino bitalino;
    private BitalinoDemo bitalinoThread;
	private LocalTime startTime;
	

	
	public SignalRecording(JFrame patientDisplay, Patient patient) {
		patientDisplay.setVisible(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 462, 407);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		start = new JButton("");
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
		start.setBounds(125, 88, 41, 33);
		contentPane.add(start);
		
		stop = new JButton("");
		stop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				BitalinoDemo.record = false;
				LocalTime currentTime = LocalTime.now();
				
				long minutes = ChronoUnit.MINUTES.between(startTime, currentTime);
				long seconds = ChronoUnit.SECONDS.between(startTime, currentTime);
				String timeRecording = minutes + " minutes" + seconds + " seconds";
				dataFromBitalino = bitalinoThread.getDataFromBitalino();
				//System.out.println(bitalinoThread.getDataFromBitalino());
				//System.out.println(timeRecording);
				
			}
		});
		stop.setEnabled(false);
		stop.setBackground(Color.WHITE);
		Image stopImg = new ImageIcon(this.getClass().getResource("/stop recording.PNG")).getImage();
		stop.setIcon(new ImageIcon(stopImg));
		stop.setBounds(250, 88, 41, 33);
		contentPane.add(stop);
		
		JLabel lblNewLabel = new JLabel("Start recording");
		lblNewLabel.setBounds(112, 74, 86, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblStopRecording = new JLabel("Stop recording");
		lblStopRecording.setBounds(231, 74, 86, 14);
		contentPane.add(lblStopRecording);
		
		macAddress = new JTextField();
		macAddress.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(checkMACaddress(macAddress.getText())) {
					start.setEnabled(true);
				}
			}
		});
		macAddress.setBounds(125, 31, 185, 20);
		contentPane.add(macAddress);
		macAddress.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("MAC Address : ");
		lblNewLabel_1.setBounds(27, 34, 96, 14);
		contentPane.add(lblNewLabel_1);
	}
	
	private boolean checkMACaddress(String macAddress) {
		String format = "\\d{2}:\\d{2}:\\d{2}:\\d{2}:\\d{2}:\\d{2}";
		Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(macAddress);
        return matcher.matches();
	}
	
	public boolean isRecording(boolean isRecording) {
		return isRecording;
	}
	
	/*private void startRecording() {
		isRecording = true;
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
			@Override
			protected Void doInBackground() throws Exception {
				while(isRecording) {
					
				}
				return null;
			}
			@Override
			protected void done() {
				isRecording = false;
			}
		};
		worker.execute();
	}
	
	private void stopRecording() {
		isRecording = false;
	}*/
}
