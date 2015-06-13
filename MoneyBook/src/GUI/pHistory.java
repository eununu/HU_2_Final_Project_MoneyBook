package GUI;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Vo.AttendeeData;
import Vo.typeData;

import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/*method 9
 *1 : 현재상태가 담긴 HashMap return (key: date / value: 금액,메모,분류,형태)
 *2 : 수입, 지출, 잔고 금액 update
 *3 : list update
 *  - action Listener -  
 *4 : 수정 -> modifyDialog 
 *5 : 검색 -> searchDialog
 *6 : 삭제 -> deleteDialog
 *7 : 등록 / JRadiobutton, JTextField에서 값 얻어온 후 등록
 *8 : 간편등록 / JTextField 에서 카드내역문자 붙여넣기 해서 입력하면 정보 파싱해서 등록
 *9 : 분류 / JCombobox에서 값 얻어오기
*/

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
	String names[]= {"기타","식사","문화","모임","화장품","소셜","카페","인터넷","카드","용돈"};
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
	ArrayList<typeData> tlist = new ArrayList<typeData>();
	AttendeeData adata = new AttendeeData();
	
	static Double inmoney = 0.0;
	static Double outmoney = 0.0;
	static Double bud = 0.0;

	pHistory(){
	}
	
	pHistory(HashMap<String,ArrayList<AttendeeData>> hm, ArrayList<typeData> list)
	{
		this.mb = hm;
		this.tlist = list;
	//	pCategory pc = new pCategory(tlist);
		
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

		while(it.hasNext()) {
			String d = it.next();
			vec.add(d);
			ArrayList<AttendeeData> checkMoney = mb.get(d);
		
			for(int i=0; i<checkMoney.size(); i++)
			{
				if(checkMoney.get(i).getIo().equals("수입")) 
				{
					inmoney+= checkMoney.get(i).getMoney();
				}
				else 
				{
					outmoney+= checkMoney.get(i).getMoney();	
				}
			}
		}
		changeState(inmoney,outmoney);
		Collections.sort(vec);
		datelist = new JList(vec);
		JScrollPane spbd = new JScrollPane(datelist);
		ccp.add(spbd);
		datelist.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e){
				String k = (String) datelist.getSelectedValue();
				
				ArrayList<AttendeeData> data = mb.get(k);
				breakdown.setText("");
				if(data.size()> 0)
				{
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
	
	public HashMap <String,ArrayList<AttendeeData>> sendHashMap()
	{
		return mb;
	}
	
	public void changeState(Double im, Double om)
	{
		bud= im-om;
		income.setText(im.toString());
		expense.setText(om.toString());
		budget.setText(bud.toString());
	}

	public void listModify()
	{
		DefaultListModel defaultModel = new DefaultListModel();
		Set<String> key = mb.keySet();
		Iterator<String> iter = key.iterator();
		while(iter.hasNext())
		{
			defaultModel.addElement(iter.next());
		}
		
		//get a collection of the elements in the model
		List list = Collections.list(defaultModel.elements());
		Collections.sort(list); //sort
		defaultModel.clear(); //remove all elements
		for(Object o : list)
		{
			defaultModel.addElement(o);
		}
		datelist.setModel(defaultModel);	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int index = 0;
		Double omoney = 0.0;
		Double cmoney = 0.0;
		String cate;
		ArrayList<AttendeeData> list = new ArrayList<AttendeeData>();
		pCategory pc = new pCategory();
		
		if(e.getSource().equals(combo)) //분류선택
		{
			JComboBox cb = (JComboBox)e.getSource();
			index = cb.getSelectedIndex();
			adata.setType(names[index]);
		}
		
		if(e.getSource().equals(btnm)) //수정하면
		{
			mdialog = new modifyDialog(this);
			mb = mdialog.showDialog(mb);
			omoney = mdialog.getOriginalMoney();
			cmoney = mdialog.getChangedMoney();
			outmoney-= omoney;
			outmoney+= cmoney;
			
			cate = mdialog.getCategory();
			
			pc.changeData(cate,omoney,cmoney);
								
			changeState(inmoney,outmoney);
			listModify();
		}
		
		if(e.getSource().equals(btns)) //검색하면
		{
			sdialog = new searchDialog(this,mb);
			sdialog.setVisible(true);
		}
		
		if(e.getSource().equals(btnd)) //삭제하면
		{
			ddialog = new deleteDialog(this);
			mb= ddialog.showDialog(mb);
			omoney = ddialog.getOriginalMoney();
			outmoney-= omoney;
			
			cate = ddialog.getCategory();
		
			pc.changeData(cate,omoney,0.0);
			
			changeState(inmoney,outmoney);
			listModify();
		}
		
		if(e.getSource().equals(btne)) //등록하면
		{
			String k = date.getText();
			adata.setMoney(Double.parseDouble(money.getText()));
			adata.setMemo(memo.getText());
			
			if(adata.getType() == null) adata.setType("기타");
			
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
				listModify();
				list.add(adata);
				mb.put(k,list);
			}

			date.setText("");
			money.setText("");
			memo.setText("");
			
			pc.changeData(adata.getType(),0.0,adata.getMoney());
			
			changeState(inmoney,outmoney);
		}
				
		if(e.getSource().equals(cardtext)) //간편추가
		{
			String cardline = cardtext.getText();
			String clist[] = cardline.split(" ");
			String cardmemo = null;
			String dlist[] = clist[1].split("/");
			String k = "2015."+dlist[0]+"."+dlist[1];
			String commadon[] = clist[3].split("원");
			String don[]= commadon[0].split(",");
			
			Double mmoney = 0.0;
			for(int i=0;i<don.length;i++)
			{
				mmoney= mmoney*1000 + Double.parseDouble(don[i]);
			}
			
			adata.setType("카드");
			adata.setMoney(mmoney);
			adata.setIo("지출");
			
			for(int i=4; i<clist.length; i++)
			{
				if(clist[i].indexOf("잔액")!= -1) break;
				if(cardmemo == null) cardmemo = clist[i];
				else cardmemo += " "+clist[i];
			}
			adata.setMemo(cardmemo);
			outmoney+= adata.getMoney();

			if(mb.containsKey(k)) //원래 날짜가 있으면
			{
				list = mb.get(k);
				list.add(adata);
				mb.put(k,list);
			}
			
			else if(!mb.containsKey(k))
			{
				listModify();
				list.add(adata);
				mb.put(k,list);
			}
					
			changeState(inmoney,outmoney);
			cardtext.setText("");
		}
	}
}