package Vo;

public class AttendeeData {

   private Double money;
   private String memo;
   private String type;
   private String io;
   
   public Double getMoney() {
      return money;
   }
   public void setMoney(Double money) {
      this.money = money;
   }
   public String getMemo() {
      return memo;
   }
   public void setMemo(String memo) {
      this.memo = memo;
   }
   public String getType() {
	   return type;
   }
   public void setType(String type) {
	   this.type = type;
   }
   public int getIo() {
	   if(this.io.equals("¼öÀÔ")) return 1;
	   else return -1;
   }
   public void setIo(String io) {
	   this.io= io;
   }
}
