package connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import telemedicineApp.pojos.BitalinoSignal;
import telemedicineApp.pojos.Doctor;
import telemedicineApp.pojos.MedicalHistory;
import telemedicineApp.pojos.Patient;

public class ClientPatient implements Serializable {

	private static final long serialVersionUID = 7897757187683889057L;

	private Socket socket;
	private ObjectOutputStream objectOutput;
	private ObjectInputStream objectInput;

	// CONSTRUCTOR
	public ClientPatient(String serverIP, int port) {
		try {

			this.socket = new Socket(serverIP, port);
			this.objectOutput = new ObjectOutputStream(socket.getOutputStream());
			this.objectInput = new ObjectInputStream(socket.getInputStream());

			String role = "patient";
			objectOutput.writeObject(role);
			objectOutput.flush();

		} catch (IOException ex) {
			Logger.getLogger(ClientPatient.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	// CLOSING CONNECTION
	public void closeConnection() throws IOException {

		objectInput.close();
		objectOutput.close();
		socket.close();

	}

	// METHODS
	public void sendFunction(String function) throws IOException {
		objectOutput.writeObject(function);
		objectOutput.flush();
	}

	public Patient checkPatient(String id/* , String password */) throws IOException, ClassNotFoundException {
		objectOutput.writeObject(id);
		objectOutput.flush();
		Patient patient = (Patient) objectInput.readObject();
		return patient;
	}

	public boolean registerPatient(Patient patient) throws IOException {
		objectOutput.writeObject(patient);
		objectOutput.flush();
		return objectInput.readBoolean();
	}
	
	public Doctor getDoctorByName(String name) throws IOException, ClassNotFoundException {
		objectOutput.writeObject(name);
		objectOutput.flush();
		Doctor doctor = (Doctor) objectInput.readObject();
		return doctor;
	}

	public boolean newMedicalHistory(MedicalHistory medicalHistory) throws IOException {
		objectOutput.writeObject(medicalHistory);
		objectOutput.flush();
		return objectInput.readBoolean();
	}

	public boolean sendPhysiologicalParameters(BitalinoSignal bitalinoSignal) throws IOException {
		objectOutput.writeObject(bitalinoSignal);
		objectOutput.flush();
		return objectInput.readBoolean();
	}
	
	public ArrayList<Doctor> getAllDoctors() throws ClassNotFoundException, IOException{
		ArrayList<Doctor> doctors = (ArrayList<Doctor>) objectInput.readObject();
		return doctors;
	}

}
