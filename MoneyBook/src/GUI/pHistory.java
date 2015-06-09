package GUI;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Vo.AttendeeData;

import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

class modifyDialog extends JDialog
{
	JTextField text = new JTextField(20);
	JButton btn = new JButton("수정");
	String item;
	HashMap<String,ArrayList<AttendeeData>> hm;
	String k;
	String klist[];
	
	JList mlist = new JList();
	JPanel sp = new JPanel(new BorderLayout(5,5));
	JPanel scp = new JPanel(new GridLayout(2,2));
	JLabel lamo = new JLabel("금액");
	JLabel lame = new JLabel("메모");
	JTextField money = new JTextField(20);
	JTextField memo = new JTextField(20);
	JPanel ssp = new JPanel();
	Double omoney = 0.0;
	Double cmoney = 0.0;
	
	ArrayList<AttendeeData> checkItem = new ArrayList<AttendeeData>();
	ArrayList<AttendeeData> modifyItem = new ArrayList<AttendeeData>();
	
	modifyDialog(pHistory pHistory)
	{
		setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
		setTitle("수정");
		setLayout(new BorderLayout(5,5));
		add(text,BorderLayout.NORTH);
		text.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				item = text.getText();
				Set<String> keys = hm.keySet();
				Iterator<String> it = keys.iterator();

				DefaultListModel mlistModel = new DefaultListModel();
				
				while(it.hasNext()) {
					String d = it.next();
					checkItem = hm.get(d);
					for(int i=0; i<checkItem.size(); i++)
					{
						if(item!=null && checkItem.get(i).getMemo().indexOf(item)!=-1) 
						{
							mlistModel.addElement(d + "/" + checkItem.get(i).getMemo() + "/" + checkItem.get(i).getMoney());
						}
					}
				}
				mlist.setModel(mlistModel);
			}
		});
		
		JScrollPane spb = new JScrollPane(mlist);

		add(spb,BorderLayout.CENTER);
	
		mlist.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e){
				k = (String) mlist.getSelectedValue();
				if(k!= null) klist = k.split("/");
			}
		});
		
		scp.add(lamo);
		scp.add(money);
		scp.add(lame);
		scp.add(memo);
		ssp.add(btn);
		
		sp.add(scp,BorderLayout.CENTER);
		sp.add(ssp,BorderLayout.SOUTH);
		
		add(sp,BorderLayout.SOUTH);
		setSize(400,300);
		
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(klist.length> 0 && item!= null) 
				{
					modifyItem = hm.get(klist[0]);
				
					for(int i=0; i<modifyItem.size();i++)
					{
						if(modifyItem.get(i).getMemo().equals(klist[1]) && modifyItem.get(i).getMoney().equals(Double.parseDouble(klist[2])))
						{
							omoney= Double.parseDouble(klist[2]);
							cmoney= Double.parseDouble(money.getText());
							modifyItem.get(i).setMoney(Double.parseDouble(money.getText()));
							modifyItem.get(i).setMemo(memo.getText());
							break;
						}
					}
					hm.put(klist[0],modifyItem);
				}
				setVisible(false);
				dispose();
			}
		});
	}
	
	HashMap<String,ArrayList<AttendeeData>> showDialog(HashMap<String,ArrayList<AttendeeData>> hm)
	{
		this.hm = hm;
		setVisible(true);
		return hm;
	}
	
	Double getOriginalMoney()
	{
		return omoney;
	}
	
	Double getChangedMoney()
	{
		return cmoney;
	}
}

class searchDialog extends JDialog
{
	JTextField text = new JTextField(20);
	JTextArea ta = new JTextArea(5,20);
	JButton btn = new JButton("종료");

	String item;
	HashMap<String,ArrayList<AttendeeData>> hm;
	String k;
	String klist[];
		
	ArrayList<AttendeeData> checkItem = new ArrayList<AttendeeData>();
	ArrayList<AttendeeData> deleteItem = new ArrayList<AttendeeData>();
	
	searchDialog(pHistory pHistory, HashMap<String,ArrayList<AttendeeData>> hm)
	{
		setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
		this.hm = hm;
		setTitle("검색");
		setLayout(new BorderLayout(5,5));
		add(text,BorderLayout.NORTH);
		text.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				item = text.getText();
				ta.setText("");
				
				Set<String> keys = hm.keySet();
				Iterator<String> it = keys.iterator();
				
				while(it.hasNext()) {
					String d = it.next();
					checkItem = hm.get(d);
					for(int i=0; i<checkItem.size(); i++)
					{
						if(item!=null && checkItem.get(i).getMemo().indexOf(item)!=-1) 
						{
							ta.append(d + "/" + checkItem.get(i).getMemo() + "/" + checkItem.get(i).getMoney()+"\n");
						}
					}
				}
				ta.setEditable(false);
			}
		});
		
		JScrollPane spb = new JScrollPane(ta);

		add(spb,BorderLayout.CENTER);
		add(btn,BorderLayout.SOUTH);
		setSize(400,300);

		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
	}	
}

class deleteDialog extends JDialog
{
	JTextField text = new JTextField(20);
	JButton btn = new JButton("삭제");
	String item;
	HashMap<String,ArrayList<AttendeeData>> hm;
	String k;
	String klist[];
	JList dlist= new JList();
	JPanel sp = new JPanel();
	Double omoney = 0.0;
	
	ArrayList<AttendeeData> checkItem = new ArrayList<AttendeeData>();
	ArrayList<AttendeeData> deleteItem = new ArrayList<AttendeeData>();
	
	deleteDialog(pHistory pHistory)
	{
		setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
		setTitle("삭제");
		setLayout(new BorderLayout(5,5));
		add(text,BorderLayout.NORTH);
		text.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				item = text.getText();
				Set<String> keys = hm.keySet();
				Iterator<String> it = keys.iterator();

				DefaultListModel dlistModel = new DefaultListModel();
				
				while(it.hasNext()) {
					String d = it.next();
					checkItem = hm.get(d);
					for(int i=0; i<checkItem.size(); i++)
					{
						if(item!=null && checkItem.get(i).getMemo().indexOf(item)!=-1) 
						{
							dlistModel.addElement(d + "/" + checkItem.get(i).getMemo() + "/" + checkItem.get(i).getMoney());
						}
					}
				}
				dlist.setModel(dlistModel);
			}
		});
		
		JScrollPane spb = new JScrollPane(dlist);

		add(spb,BorderLayout.CENTER);
	
		dlist.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e){
				k = (String) dlist.getSelectedValue();
				if(k!= null) klist = k.split("/");
			}
		});
		
		sp.add(btn);
	
		add(sp,BorderLayout.SOUTH);
		setSize(400,300);
		
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(klist.length> 0) 
				{
					deleteItem = hm.get(klist[0]);
					for(int i=0; i<deleteItem.size();i++)
					{
						if(deleteItem.get(i).getMemo().equals(klist[1]) && deleteItem.get(i).getMoney().equals(Double.parseDouble(klist[2])))
						{
							omoney= Double.parseDouble(klist[2]);
							deleteItem.remove(i);
							break;
						}
					}
					hm.put(klist[0],deleteItem);
				}
				setVisible(false);
				dispose();
			}
		});
	}
	
	HashMap<String,ArrayList<AttendeeData>> showDialog(HashMap<String,ArrayList<AttendeeData>> hm)
	{
		this.hm = hm;
		setVisible(true);
		return hm;
	}	
	
	Double getOriginalMoney()
	{
		return omoney;
	}
}

public class pHistory extends JPanel implements ActionListener
{
	JPanel northPanel = new JPanel(new GridLayout(1,3));
	ImageIcon searchIcon = new ImageIcon("images/search.jpg");
	ImageIcon deleteIcon = new ImageIcon("images/delete.jpg");
	ImageIcon modifyIcon = new ImageIcon("images/modify.jpg");
	JButton btns = new JButton(searchIcon);
	JButton btnd = new JButton(deleteIcon);
	JButton btnm = new JButton(modifyIcon);
	
	modifyDialog mdialog;
	searchDialog sdialog;
	deleteDialog ddialog;
	
	JList datelist = new JList();
	JPanel centerPanel = new JPanel(new BorderLayout(5,5));
	JPanel cnp = new JPanel(new GridLayout(3,2));
	JLabel lain = new JLabel("수입");
	JLabel laex = new JLabel("지출");
	JLabel labu = new JLabel("잔고");
	JTextField income = new JTextField(20);
	JTextField expense = new JTextField(20);
	JTextField budget = new JTextField(20);
	JTextArea breakdown = new JTextArea(21,20);
	JPanel ccp = new JPanel(new GridLayout(1,2));
	JLabel laca = new JLabel("간편등록");
	JTextField cardtext = new JTextField(20);
	JPanel ccep = new JPanel(new BorderLayout());
	
	JPanel southPanel = new JPanel(new BorderLayout());
	JPanel snp = new JPanel();
	JRadioButton btnin = new JRadioButton("수입");
	JRadioButton btnex = new JRadioButton("지출");
	String names[]= {"기타","식사","문화","모임","화장품","소셜","카페","인터넷","카드","용돈","과외"};
	JComboBox combo = new JComboBox();
	
	JPanel scp = new JPanel(new GridLayout(3,2));
	JLabel lada = new JLabel("날짜");
	JLabel lamo = new JLabel("금액");
	JLabel lame = new JLabel("메모");
	JTextField date = new JTextField(20);
	JTextField money = new JTextField(20);
	JTextField memo = new JTextField(20);
	
	JPanel ssp = new JPanel();
	JButton btne = new JButton("등록");

	HashMap<String,ArrayList<AttendeeData>> mb = new HashMap();
	
	static Double inmoney = 0.0;
	static Double outmoney = 0.0;
	static Double bud = 0.0;

	pHistory(HashMap<String,ArrayList<AttendeeData>> hm)
	{
		this.mb = hm;
		setLayout(new BorderLayout(5,5));
			
		northPanel.add(btnm);
		northPanel.add(btns);
		northPanel.add(btnd);
		btnm.addActionListener(this);
		btns.addActionListener(this);
		btnd.addActionListener(this);
		add(northPanel,BorderLayout.NORTH);
		
		cnp.add(lain);
		cnp.add(income);
		cnp.add(laex);
		cnp.add(expense);
		cnp.add(labu);
		cnp.add(budget);
		income.setEditable(false);
		expense.setEditable(false);
		budget.setEditable(false);
		
		Set<String> keys = mb.keySet();
		Iterator<String> it = keys.iterator();
		
		Vector<String> vec = new Vector();
		vec.add("날짜");

		while(it.hasNext()) {
			String d = it.next();
			vec.add(d);
			ArrayList<AttendeeData> checkMoney = mb.get(d);	
			for(int i=0; i<checkMoney.size(); i++)
			{
				if(checkMoney.get(i).getIo()== 1) 
				{
					inmoney+= checkMoney.get(i).getMoney();
				}
				else 
				{
					outmoney+= checkMoney.get(i).getMoney();	
				}
			}
		}
		bud= inmoney-outmoney;
		income.setText(inmoney.toString());
		expense.setText(outmoney.toString());
		budget.setText(bud.toString());
		
		datelist = new JList(vec);
		JScrollPane spbd = new JScrollPane(datelist);
		ccp.add(spbd);
		datelist.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e){
				String k = (String) datelist.getSelectedValue();
				if(!k.equals("날짜"))
				{
					ArrayList<AttendeeData> data = mb.get(k);
					breakdown.setText("");
					for(int i=0; i<data.size(); i++)
					{
						breakdown.append(data.get(i).getMemo() +" "+ data.get(i).getMoney() +" ("+ data.get(i).getType()+")\n");
					}
				}
			}
		});
		
		JScrollPane spbb = new JScrollPane(breakdown);
		breakdown.setEditable(false);
		ccep.add(spbb,BorderLayout.NORTH);
		ccep.add(laca,BorderLayout.CENTER);
		ccep.add(cardtext,BorderLayout.SOUTH);
		cardtext.addActionListener(this);
		ccp.add(ccep);
		
		centerPanel.add(cnp,BorderLayout.NORTH);
		centerPanel.add(ccp,BorderLayout.CENTER);
		add(centerPanel,BorderLayout.CENTER);
	
		ButtonGroup group = new ButtonGroup();
		
		group.add(btnin);
		group.add(btnex);
		snp.add(btnin);
		snp.add(btnex);
	
		for(int i=0;i<names.length;i++)
		{
			combo.addItem(names[i]);
		}
		combo.addActionListener(this);
		snp.add(combo);
		
		scp.add(lada);
		scp.add(date);
		scp.add(lamo);
		scp.add(money);
		scp.add(lame);
		scp.add(memo);
		
		ssp.add(btne);
		btne.addActionListener(this);
	
		southPanel.add(snp,BorderLayout.NORTH);
		southPanel.add(scp,BorderLayout.CENTER);
		southPanel.add(ssp,BorderLayout.SOUTH);
		add(southPanel,BorderLayout.SOUTH);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int index = 0;
		Double omoney = 0.0;
		Double cmoney = 0.0;
		ArrayList<AttendeeData> list = new ArrayList<AttendeeData>();
		AttendeeData adata = new AttendeeData();
		if(e.getSource().equals(btnm)) //수정하면
		{
			mdialog = new modifyDialog(this);
			mb = mdialog.showDialog(mb);
			omoney = mdialog.getOriginalMoney();
			cmoney = mdialog.getChangedMoney();
			outmoney-= omoney;
			outmoney+= cmoney;
		
			bud= inmoney-outmoney;
			income.setText(inmoney.toString());
			expense.setText(outmoney.toString());
			budget.setText(bud.toString());
		}
		
		else if(e.getSource().equals(btns)) //검색하면
		{
			sdialog = new searchDialog(this,mb);
			sdialog.setVisible(true);
		}
		
		else if(e.getSource().equals(btnd)) //삭제하면
		{
			ddialog = new deleteDialog(this);
			mb= mdialog.showDialog(mb);
			omoney = ddialog.getOriginalMoney();
			outmoney-= omoney;
		
			bud= inmoney-outmoney;
			income.setText(inmoney.toString());
			expense.setText(outmoney.toString());
			budget.setText(bud.toString());
		}
		
		else if(e.getSource().equals(btne)) //등록하면
		{
			String k = date.getText();
			adata.setMoney(Double.parseDouble(money.getText()));
			adata.setMemo(memo.getText());
			adata.setType(names[index]);
			
			if(btnin.isSelected()) 
			{
				adata.setIo("수입");
				inmoney+= adata.getMoney();
			}
			else if(btnex.isSelected())
			{
				adata.setIo("지출");
				outmoney+= adata.getMoney();
			}
			
			if(mb.containsKey(k)) //원래 날짜가 있으면
			{
				list = mb.get(k);
				list.add(adata);
				mb.put(k,list);
			}
			
			else if(!mb.containsKey(k))
			{
				// 기존 아이템을 가진 모델 객체를 얻는다
				ListModel listModel = datelist.getModel();
				
				// 기존 아이템을 새로운 모델 객체에 복사한다
				DefaultListModel defaultModel = new DefaultListModel();
				for(int i=0; i<listModel.getSize(); i++)
				{
					defaultModel.addElement(listModel.getElementAt(i));
				}
				// 기존 아이템 외에 새로운 아이템 하나 추가한다
				defaultModel.addElement(k);
				// 리스트에 모델을 적용한다
				datelist.setModel(defaultModel);
				list.add(adata);
				mb.put(k,list);
			}
				
			date.setText("");
			money.setText("");
			memo.setText("");
			
			bud= inmoney-outmoney;
			income.setText(inmoney.toString());
			expense.setText(outmoney.toString());
			budget.setText(bud.toString());
		}
		
		else if(e.getSource().equals(combo)) //분류선택
		{
			JComboBox cb = (JComboBox)e.getSource();
			index = cb.getSelectedIndex();
		}
		
		else if(e.getSource().equals(cardtext)) //간편추가
		{
			String cardline = cardtext.getText();
			String clist[] = cardline.split(" ");
			String cardmemo = null;
			String dlist[] = clist[1].split("/");
			String k = "2015."+dlist[0]+"."+dlist[1];
			String don[] = clist[3].split("원");
			
			
			adata.setType("카드");
			adata.setMoney(Double.parseDouble(don[0]));
			adata.setIo("지출");
			
			for(int i=4; i<clist.length; i++)
			{
				if(clist[i].indexOf("잔액")!= -1) break;
				if(cardmemo == null) cardmemo = clist[i];
				else cardmemo += " "+clist[i];
			}
			adata.setMemo(cardmemo);
			
			if(mb.containsKey(k)) //원래 날짜가 있으면
			{
				list = mb.get(k);
				list.add(adata);
				mb.put(k,list);
			}
			else
			{
				// 기존 아이템을 가진 모델 객체를 얻는다
				ListModel listModel = datelist.getModel();
				
				// 기존 아이템을 새로운 모델 객체에 복사한다
				DefaultListModel defaultModel = new DefaultListModel();
				for(int i=0; i<listModel.getSize(); i++)
				{
					defaultModel.addElement(listModel.getElementAt(i));
				}
				// 기존 아이템 외에 새로운 아이템 하나 추가한다
				defaultModel.addElement(k);
				// 리스트에 모델을 적용한다
				datelist.setModel(defaultModel);
				list.add(adata);
				mb.put(k,list);
			}
			
			outmoney+= adata.getMoney();
			bud= inmoney-outmoney;
			expense.setText(outmoney.toString());
			budget.setText(bud.toString());
			cardtext.setText("");
		}
	}
}