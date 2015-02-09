import java.util.ArrayList;


public class UDP_Datagram {
int status;
int sequence_no;
ArrayList<Byte> data;
public int getStatus() {
	return status;
}
public void setStatus(int status) {
	this.status = status;
}
public int getSequence_no() {
	return sequence_no;
}
public void setSequence_no(int sequenceNo) {
	sequence_no = sequenceNo;
}
public ArrayList<Byte> getData() {
	return data;
}
public void setData(ArrayList<Byte> data) {
	this.data = data;
}

}
