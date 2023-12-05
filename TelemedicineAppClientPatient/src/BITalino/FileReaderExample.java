/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BITalino;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

 public class FileReaderExample {
    public static void main(String[] args) {
        // Direccion del archivo que deseas leer
        String filePathname = "C:\\Users\\User\\Documents\\OpenSignals (r)evolution\\files\\Practica telemedicina\\practiceRecordedSignal.txt";
        File file = new File(filePathname);
        
        try {
            // Abre el archivo para lectura
            FileReader fileReader = new FileReader(file);
            
            // Crea un objeto BufferedReader para leer el archivo de manera eficiente
            BufferedReader reader = new BufferedReader(fileReader);
            String linea;
            
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }
            
            // Cierra el archivo después de leer
            reader.close();
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }
} 
