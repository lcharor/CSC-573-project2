import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.TimerTask;


public class AckInstance extends TimerTask{
DatagramSocket client;
int expected_seq_no;
InetAddress iNat_address;
int base;

	public AckInstance(DatagramSocket client, int expectedSeqNo,
		InetAddress iNatAddress,int base) {
	super();
	this.client = client;
	this.expected_seq_no = expectedSeqNo;
	this.iNat_address = iNatAddress;
	this.base=base;
}

	@Override
	public void run() {
		System.out.println("comming here for:"+expected_seq_no);
		int expected_ack=expected_seq_no;
		byte[] ack=new byte[4];
 	   DatagramPacket ACK_packet=new DatagramPacket(ack, ack.length, iNat_address, 9999);
 	  try {
		client.receive(ACK_packet);
		System.out.println("ack received for packet:"+ack[0]);
	    if(ack[0]==expected_ack)
 	     {
 	       System.out.println("ACK received:"+ack[0]);
         }
		  else{
	    		 //received ACK is not what is expected 
	    		   
	    	   }

	} catch
	(IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

}
