package GUI;

import javax.swing.*;

import Vo.AttendeeData;

import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import Vo.typeData;
import Vo.AttendeeData;
import Action.ExcelHandle;
import Action.ExcelSave;

/*method 3
 *1 : menuOpen -> Excel file 선택
 *2 : menuSave -> Excel file 저장
 *3 : menuExit -> 프로그램 끝내기 
 */

public class MyFrame extends JFrame
{
	public pHistory history = null;
    public pCategory category = null;
    
    static HashMap<String,ArrayList<AttendeeData>> hm = new HashMap();
    static ArrayList<AttendeeData> data = new ArrayList<AttendeeData>();
    ArrayList<typeData> tlist = new ArrayList<typeData>();
    
    MyFrame()
    {
    	JMenuBar mb= new JMenuBar();
    	JMenu menu= new JMenu("File");
    	JMenuItem menuOpen= new JMenuItem("open");
    	JMenuItem menuSave= new JMenuItem("save");
    	JMenuItem menuExit= new JMenuItem("exit");
    	
		menu.add(menuOpen);
		menu.add(menuSave);
		menu.add(menuExit);
		
		menuOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				File f;
				JFileChooser fc= new JFileChooser();
				int answer= fc.showOpenDialog(null);
				if(answer==JFileChooser.APPROVE_OPTION)
				{
					f= fc.getSelectedFile();
					try {
						ExcelHandle eh= new ExcelHandle(f);
						hm = eh.getHashmap();
						tlist = eh.getTlist();
						history = new pHistory(hm);
						category = new pCategory(tlist);
					
						JTabbedPane jtab = new JTabbedPane();   //  JTabbedPane  객체 생성
				        
				        jtab.addTab("내역", history );
				        jtab.addTab("분류", category );
				        
				        add(jtab);
				        
				        setVisible(true);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}			
		});
		
		menuSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				File f;
				JFileChooser fc= new JFileChooser();
				int answer= fc.showSaveDialog(null);
				if(answer== JFileChooser.APPROVE_OPTION)
				{
					f= fc.getSelectedFile();
					try {
						new ExcelSave(f, history.sendHashMap());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			
		});
		
		menuExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{	
				System.exit(0);
			}
		});
		
		mb.add(menu);
		setJMenuBar(mb);
		
		setTitle("Money Book");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600,800);
        setVisible(true);
    }
    
    public static void main(String[] args) 
    {
        new MyFrame();
    }
}

