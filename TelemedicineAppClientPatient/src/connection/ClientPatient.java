package connection;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import BITalino.BitalinoDemo;
import telemedicineApp.pojos.Patient;


public class ClientPatient implements Serializable{
	
	private static final long serialVersionUID = 7897757187683889057L;
	
	private Socket socket;
	private ObjectOutputStream objectOutput;
	//private OutputStream outputStream;
	
	
	
	//CONSTRUCTOR
	public ClientPatient(String serverIP, int port) {
		try {	
			
			this.socket= new Socket (serverIP, port);
			//this.outputStream = socket.getOutputStream();
			this.objectOutput = new ObjectOutputStream(socket.getOutputStream());
			
		} catch (IOException ex) {
            Logger.getLogger(ClientPatient.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}
	
	//funcion para registrar a un paciente y mandarlo // sera lo mismo qmandar todo el paciente (sendPatient)?
	/*public void registerPatient(Patient patient) {
        try {
            this.objectOutput.writeObject(patient);
            this.objectOutput.flush();
        } catch (IOException ex) {
            Logger.getLogger(ClientPatient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
	
	public void sendPhysiologicalParameters(ArrayList<Integer> values) throws IOException{
		
		/*DataOutputStream dataOutput = new DataOutputStream(this.socket.getOutputStream());
		dataOutput.writeInt(values.size());
		for(Integer value : values) {
			dataOutput.writeInt(value);
		}*/
		
		this.objectOutput.writeObject(values);
	}
	
	public void checkPatient(String id/*, String password*/) throws IOException {
		objectOutput.writeObject(id);
	}
	
	
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
	
	public static void main(String[] args) {
		
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
	}
	
}
