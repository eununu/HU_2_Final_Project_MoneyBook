package Vo;

public class AttendeeData extends typeData{
	String io;
	public int getIo() {
		if(this.io.equals("¼öÀÔ")) return 1;
		else return -1;
	}
	public void setIo(String io) {
		this.io= io;
	}
}
