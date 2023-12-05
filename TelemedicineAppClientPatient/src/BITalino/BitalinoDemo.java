//TODO Eliminate this class; would be handled from SignalRecording Class; already implemented and working

package BITalino;


import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Vector;

import javax.bluetooth.RemoteDevice;


import java.util.logging.Level;
import java.util.logging.Logger;

public class BitalinoDemo extends Thread{

    private Frame[] frame;
    private BITalino bitalino;
    private ArrayList<Integer> dataFromBitalino; //TODO comprobar
    private String macAddress;
    public static boolean record = true;
    private int i = 0;
    

    public BitalinoDemo(String macAddress) {
		this.macAddress = macAddress;
		this.dataFromBitalino = new ArrayList<Integer>();
	}

    @Override
	public void run() {

        try {
        	
            bitalino = new BITalino();
            
            // Code to find Devices
            Vector<RemoteDevice> devices = bitalino.findDevices();
            System.out.println(devices);

            //String macAddress = "20:17:11:20:52:32"; //-> this is our bitalino, just for testing
            
            int SamplingRate = 10;
            bitalino.open(this.macAddress, SamplingRate);

            int[] channelsToAcquire = {0};  //channel 0 as we are measuring EMG
            bitalino.start(channelsToAcquire);
            
            while(record) {
            	
            	//Each time read a block of 10 samples 
                int block_size=10;
                frame = bitalino.read(block_size);
                
                //Save the samples
                for (int i = 0; i < frame.length; i++) {
                    dataFromBitalino.add(frame[i].analog[0]);
                    System.out.println(dataFromBitalino.get(i));
                }
            }
            
            //stop acquisition
            bitalino.stop();
        } catch (BITalinoException ex) {
            Logger.getLogger(BitalinoDemo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable ex) {
            Logger.getLogger(BitalinoDemo.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                //close bluetooth connection
                if (bitalino != null) {
                    bitalino.close();
                }
            } catch (BITalinoException ex) {
                Logger.getLogger(BitalinoDemo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        /*while(record) {
        	this.dataFromBitalino.add(i);
        	//System.out.println(dataFromBitalino.get(i));
        	i++;
        	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }*/
    }

	public ArrayList<Integer> getDataFromBitalino() {
		return this.dataFromBitalino;
	}
    
    
}
