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

public class ExcelHandle 
{
	public HashMap<String,ArrayList<AttendeeData>> ExcelParser() 
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
		
		ArrayList<AttendeeData> list = new ArrayList<AttendeeData>();
		AttendeeData aData = null;
		HashMap<String,ArrayList<AttendeeData>> mb = new HashMap();
		
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
			aData.setMoney(money); //금액
			aData.setMemo(temp[1]); //메모
			aData.setType(temp[2]); //분류
			aData.setIo(temp[3]); //형태
			
			if(mb.containsKey(temp[0]))
			{
				list= mb.get(temp[0]);
				list.add(aData);
				mb.put(temp[0],list);	
			}
			else
			{
				ArrayList<AttendeeData> newlist = new ArrayList<AttendeeData>();
				newlist.add(aData);
				mb.put(temp[0],newlist);
			}
		}
		return mb;
	}
}