package connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import telemedicineApp.pojos.BitalinoSignal;
import telemedicineApp.pojos.MedicalHistory;
import telemedicineApp.pojos.Patient;


public class ClientPatient implements Serializable{
	
	private static final long serialVersionUID = 7897757187683889057L;
	
	private Socket socket;
	private ObjectOutputStream objectOutput;
	private ObjectInputStream objectInput;
	
	
	
	//CONSTRUCTOR
	public ClientPatient(String serverIP, int port) {
		try {	
			
			this.socket= new Socket (serverIP, port);
			this.objectOutput = new ObjectOutputStream(socket.getOutputStream());
			this.objectInput = new ObjectInputStream(socket.getInputStream());
			
			String role = "patient";
			objectOutput.writeObject(role);
			
		} catch (IOException ex) {
            Logger.getLogger(ClientPatient.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}
	
	
	//METHODS
	public void sendFunction(String function) throws IOException {
		objectOutput.writeObject(function);
		objectOutput.flush();
	}
	
	public Patient checkPatient(String id/*, String password*/) throws IOException, ClassNotFoundException {
		objectOutput.writeObject(id);
		objectOutput.flush();
		Patient patient = (Patient) objectInput.readObject();
		return patient;
	}
	
	public boolean registerPatient(Patient patient) throws IOException {
		objectOutput.writeObject(patient);
		objectOutput.flush();
		boolean b = objectInput.readBoolean();
		System.out.println(b);
		return b;
	}
	
	public boolean newMedicalHistory(MedicalHistory medicalHistory) throws IOException {
		objectOutput.writeObject(medicalHistory);
		objectOutput.flush();
		return objectInput.readBoolean();
	}
	
	public boolean sendPhysiologicalParameters(BitalinoSignal bitalinoSignal) throws IOException{
		this.objectOutput.writeObject(bitalinoSignal);
		objectOutput.flush();
		return objectInput.readBoolean();
	}
	
	
	
	//CLOSING CONNECTION
	//Should be done when logout?
	private static void releaseResources(OutputStream outputStream, Socket socket) {
        try {
            try {
            	
                outputStream.close();
                
            } catch (IOException ex) {
                Logger.getLogger(ClientPatient.class.getName()).log(Level.SEVERE, null, ex);
            }

            socket.close();

        } catch (IOException ex) {
            Logger.getLogger(ClientPatient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
	
	/*public static void main(String[] args) {
		
		ClientPatient client = new ClientPatient("localhost", 9000);
		
		ArrayList<Integer> signal = new ArrayList<Integer>();
		for(int i = 500; i < 550; i++) {
			signal.add(i);
		}
		String signalToSend = "";
		for(Integer value : signal) {
			signalToSend += Integer.toString(value) + "/n";
		}
		try {
			
			client.sendPhysiologicalParameters(signal);
			
		} catch (IOException ex) {
            Logger.getLogger(ClientPatient.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		releaseResources(client.objectOutput, client.socket);
	}*/
	
}
