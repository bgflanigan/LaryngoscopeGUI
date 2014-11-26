package edu.bflan.laryngoscopegui;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

public class DeviceSensorUpdater extends AbstractSensorUpdater{

	SerialPort port;
	InputStream input;
	@Override
	public void setup() {
		CommPortIdentifier ident = getArduinoPort();
		try {
			port = (SerialPort)ident.open("LaryngoscopeGUI",2000);
			port.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			input = port.getInputStream();
		} catch (PortInUseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedCommOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void update() {
		 try {  
	            int b;  
	            while(true) {  
	                  
	                // if stream is not bound in.read() method returns -1  
	                while((b = input.read()) != -1) {  
	                    onReceive((byte) b);  
	                }  
	                onStreamClosed();  
	                  
	                // wait 10ms when stream is broken and check again  
	                Thread.sleep(10);  
	            }  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } catch (InterruptedException e) {  
	            e.printStackTrace();  
	        } 
	}

	public static void main(String[] args) {
		
	}
	
	byte[] buffer = new byte[1024];  
    int tail = 0;  
      
    public void onReceive(byte b) {  
        // simple protocol: each message ends with new line  
        if (b=='\n') {  
            onMessage();  
        } else {  
            buffer[tail] = b;  
            tail++;  
        }  
    }  
   
    public void onStreamClosed() {  
        onMessage();  
    }  
      
    /* 
     * When message is recognized onMessage is invoked  
     */  
    private void onMessage() {  
        if (tail!=0) {  
            // constructing message  
            String message = getMessage(buffer, tail);  
            System.out.println("RECEIVED MESSAGE: " + message);  
            char identifier = message.charAt(0);
            try {
            	float force = Float.parseFloat(message.substring(1));
            	this.updateSensor(identifier, force);
            	
            }
            catch(NumberFormatException e) {
            	System.out.println("poorly formatted message: "+message);
            }
            
            tail = 0;  
        }  
    }  
      
    // helper methods   
    public byte[] getMessage(String message) {  
        return message.getBytes();  
    }  
      
    public String getMessage(byte[] buffer, int len) {  
        return new String(buffer, 0, tail);  
    }  
	
	public CommPortIdentifier getArduinoPort() {
		Enumeration ports = CommPortIdentifier.getPortIdentifiers();
		while (ports.hasMoreElements()) {
			CommPortIdentifier port = (CommPortIdentifier)ports.nextElement();
			if (!port.isCurrentlyOwned())
				return port;
		}
		return null;
	}

}




