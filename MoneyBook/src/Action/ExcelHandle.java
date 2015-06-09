package Action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Vo.AttendeeData;
import Vo.typeData;

public class ExcelHandle 
{
	HashMap<String,ArrayList<AttendeeData>> mba = new HashMap();
	ArrayList<typeData> tlist = new ArrayList<typeData>();
	
	AttendeeData makeaData(double money, String memo, String type, String io)
	{
		AttendeeData data = new AttendeeData();
		data.setMoney(money); //금액
		data.setMemo(memo); //메모
		data.setType(type); //분류
		data.setIo(io); //형태
		return data;
	}
	
	typeData maketData(double money, String memo, String type, String date)
	{
		typeData data = new typeData();
		data.setMoney(money); //금액
		data.setMemo(memo); //메모
		data.setType(type); //분류
		data.setDate(date); //날짜
		return data;
	}
	
	public HashMap<String,ArrayList<AttendeeData>> getHashmap()
	{
		return mba;
	}
	
	public ArrayList<typeData> getTlist()
	{
		return tlist;
	}
	
	public ExcelHandle() 
			//throws IOException
	{
		//File file = f;
		File file = new File("201503.xlsx");
		XSSFWorkbook wb = null;
		XSSFSheet sheet = null;
		try {
			wb = new XSSFWorkbook(new FileInputStream(file)); } 
		catch (Exception e) {
			e.printStackTrace(); }
		
		ArrayList<AttendeeData> alist = new ArrayList<AttendeeData>();
		typeData tData = null;
		AttendeeData aData = null;
		
		sheet = wb.getSheetAt(0); //첫번째 sheet 가져옴
		int rowCnt = sheet.getPhysicalNumberOfRows(); //row 갯수
		for(int r=1; r<rowCnt; r++) 
		{
			aData = new AttendeeData();
			String[] temp = new String[5];
			int count = 0;
			double money = 0;
			XSSFRow row = sheet.getRow(r);
			int cellCnt = row.getPhysicalNumberOfCells(); //cell 갯수
			count = 0;
			for(int c=0; c<cellCnt; c++)
			{
				XSSFCell cell = row.getCell(c);
				switch(cell.getCellType())
				{
					case XSSFCell.CELL_TYPE_NUMERIC:
			         money = cell.getNumericCellValue();
			         break;
			        case XSSFCell.CELL_TYPE_STRING:
			         temp[count++]= cell.getStringCellValue();
			         break;
			        case XSSFCell.CELL_TYPE_BLANK:
			         break;
				}
			}
			aData = makeaData(money,temp[1],temp[2],temp[3]);
			tData = maketData(money,temp[1],temp[2],temp[0]);
			tlist.add(tData);
			
			if(mba.containsKey(temp[0]))
			{
				alist= mba.get(temp[0]);
				alist.add(aData);
				mba.put(temp[0],alist);	
			}
			else
			{
				ArrayList<AttendeeData> newlist = new ArrayList<AttendeeData>();
				newlist.add(aData);
				mba.put(temp[0],newlist);
			}
		}
	}
}