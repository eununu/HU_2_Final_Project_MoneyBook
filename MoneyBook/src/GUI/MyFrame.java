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

public class MyFrame extends JFrame
{
	public pHistory jpanel01 = null;
    public pMonth jpanel02 = null;
    public pCategory jpanel03 = null;
    static HashMap<String,ArrayList<AttendeeData>> hm = new HashMap();
    static ArrayList<AttendeeData> data = new ArrayList<AttendeeData>();
    ArrayList<typeData> tlist = new ArrayList<typeData>();
    
    MyFrame()
    {
    	ExcelHandle eh= new ExcelHandle();
    	hm = eh.getHashmap();
		tlist = eh.getTlist();
		
    	JMenuBar mb= new JMenuBar();
    	JMenu menu= new JMenu("File");
    	JMenuItem menuOpen= new JMenuItem("open");
    	JMenuItem menuSave= new JMenuItem("save");
    	JMenuItem menuExit= new JMenuItem("exit");
    	
		MyOpenActionListener listenerOpen= new MyOpenActionListener();
		menu.add(menuOpen);
		menu.add(menuSave);
		menu.add(menuExit);
		menuOpen.addActionListener(listenerOpen);
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
						new ExcelSave(f, eh.getHashmap());
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
		
		jpanel01 = new pHistory(hm);
		jpanel02 = new pMonth();
        jpanel03 = new pCategory(tlist);
        
        JTabbedPane jtab = new JTabbedPane();   //  JTabbedPane  ��ü ����
        
        jtab.addTab("����", jpanel01 );
        jtab.addTab("����", jpanel02 );
        jtab.addTab("�з�", jpanel03 );
        
        add(jtab);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600,800);
        setVisible(true);
    }
    
    class MyOpenActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			File f;
			ExcelHandle eh= new ExcelHandle();
			
			JFileChooser fc= new JFileChooser();
			int answer= fc.showOpenDialog(null);
			if(answer==JFileChooser.APPROVE_OPTION)
			{
				f= fc.getSelectedFile();
			/*	try {
					hm = eh.ExcelParser(f);
					new MyFrame();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
			}
		}
	}

    public static void main(String[] args) 
    {
        new MyFrame();
    }
}

