import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;


public class FTP_Server {
public static void main(String[] args) throws IOException{
	 Scanner input = new Scanner(System.in);
	 System.out.println("Enter Request to server:Simple_ftp_server port# file-name p");
	 String Request=input.nextLine();
	 String[] Server_REQUEST=Request.split(" ");
	 int server_port=Integer.parseInt(Server_REQUEST[1]);
	 String Filename=Server_REQUEST[2];
	 float probability=Float.parseFloat(Server_REQUEST[3]);
	 Random rand=new Random();
	 
     DatagramSocket listener = new DatagramSocket(server_port);
     System.out.println("Simple_ftp_server Started.");
     InetAddress iNat_address=InetAddress.getByName("LOCALHOST");
     int expected_seq_no=0;
  
	 BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(Filename));
     try {
         while (true) {
         	byte[] mybytearray = new byte[listener.getReceiveBufferSize()];
         	byte[] seq_no=new byte[4];
         	byte[] data=new byte[100];
         	DatagramPacket rp=new DatagramPacket(mybytearray, mybytearray.length,iNat_address,server_port);
         	listener.receive(rp);
         	float num = rand.nextFloat();
        	seq_no[0]=mybytearray[0];
        	
        	if((seq_no[0]==expected_seq_no) && (num < probability))
        	{
        	System.out.print("packet received:"+seq_no[0]+",");
		    bos.write(mybytearray);
		    DatagramPacket ACK = new DatagramPacket(seq_no, seq_no.length,iNat_address,9999);
		    
		    listener.send(ACK);
		    System.out.println("ACK SENT:"+ACK.getData()[0]);
		    expected_seq_no++;
        	}
        	else
        		System.out.println("Dropping packet"+seq_no[0]);
         	}
         	
         
     } finally {
    	 bos.close();
         listener.close();
     }
}
}
