package Action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import Vo.AttendeeData;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelSave {
	
	public ExcelSave(File file, HashMap<String,ArrayList<AttendeeData>> hm) throws IOException
	{
	//	File file = new File("201506.xlsx");
		XSSFWorkbook wb = null;
		XSSFSheet sheet = null;
		
		wb = new XSSFWorkbook();
		sheet = wb.createSheet("new sheet");
		ArrayList<AttendeeData> list = new ArrayList<AttendeeData>();
		
		Set<String> keys = hm.keySet();
		Iterator<String> it = keys.iterator();
		
		String index[] = {"날짜","금액","메모","분류","형태"};
		
		Row row = sheet.createRow(0);
		for(int j=0; j<5; j++)
		{
			Cell cell = row.createCell(j);
			cell.setCellValue(index[j]);
		}
		
		int cnt = 1;
		while(it.hasNext()) {
			String d = it.next();
			list = hm.get(d);
			for(int j=0; j<list.size(); j++)
			{
				row = sheet.createRow(cnt++);
				Cell cell = row.createCell(0);
				cell.setCellValue(d);
				cell = row.createCell(1);
				cell.setCellValue(list.get(j).getMoney());
				cell = row.createCell(2);
				cell.setCellValue(list.get(j).getMemo());
				cell = row.createCell(3);
				cell.setCellValue(list.get(j).getType());
				cell = row.createCell(4);
				cell.setCellValue(list.get(j).getIo());
			}	
		}
		
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.close();
	}
}
