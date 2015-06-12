package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Vo.AttendeeData;

class modifyDialog extends JDialog
{
	/*method 6
	 *1 : ������ keyword �Է� �� list�����
	 *2 : ������ list ���� �� ��������
	 *3 : ������ ���� ����, ����
	 *4 : ����� ��ü HashMap return
	 *5 : ������ �ݾ� return
	 *6 : ������ �ݾ� return
	*/
	
	JTextField text = new JTextField(20);
	JButton btn = new JButton("����");
	String item;
	HashMap<String,ArrayList<AttendeeData>> hm;
	String k;
	String klist[];
	
	JList mlist = new JList();
	JPanel sp = new JPanel(new BorderLayout(5,5));
	JPanel scp = new JPanel(new GridLayout(3,2));
	JLabel lada = new JLabel("��¥");
	JLabel lamo = new JLabel("�ݾ�");
	JLabel lame = new JLabel("�޸�");
	JTextField date = new JTextField(20);
	JTextField money = new JTextField(20);
	JTextField memo = new JTextField(20);
	JPanel ssp = new JPanel();
	Double omoney = 0.0;
	Double cmoney = 0.0;
	
	ArrayList<AttendeeData> changeItem = new ArrayList<AttendeeData>();
	ArrayList<AttendeeData> checkItem = new ArrayList<AttendeeData>();
	ArrayList<AttendeeData> originItem = new ArrayList<AttendeeData>();
	AttendeeData modifyItem = new AttendeeData();
	
	modifyDialog(pHistory pHistory)
	{
		setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
		
		setTitle("����");
		setLayout(new BorderLayout(5,5));
		add(text,BorderLayout.NORTH);
		
		//item �Է� �ް� �� item�� ���Ե� ����Ʈ �����
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
	
		//�����ϱ����� ���õ� ����Ʈ�� �� �����ϱ�
		mlist.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e){
				k = (String) mlist.getSelectedValue();
				if(k!= null) klist = k.split("/");
			}
		});
		
		scp.add(lada);
		scp.add(date);
		scp.add(lamo);
		scp.add(money);
		scp.add(lame);
		scp.add(memo);
		ssp.add(btn);
		
		sp.add(scp,BorderLayout.CENTER);
		sp.add(ssp,BorderLayout.SOUTH);
		
		add(sp,BorderLayout.SOUTH);
		setSize(400,300);
		
		//klist : ������ �������� ����
		//JTextField : ������ ������ ���� �Է� ����
		//��¥: klist[0] -> date
		//�޸�: klist[1] -> memo
		//�ݾ�: klist[2] -> money
		
		//����
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(klist.length> 0 && item!= null) 
				{
					//�׳� �� ������ list
					originItem = hm.get(klist[0]); 
					
					for(int i=0; i<originItem.size();i++)
					{
						if(originItem.get(i).getMemo().equals(klist[1]) && originItem.get(i).getMoney().equals(Double.parseDouble(klist[2])))
						{
							//�׳� �� �� �� i��° ������ ����
							//omoney: �����ݾ�, cmoney: �ٲ�ݾ� / ��ü ����ݾ� ������� �ʿ�
							
							omoney= Double.parseDouble(klist[2]);
							cmoney= Double.parseDouble(money.getText());
							
							//������ ������ ���� update
							modifyItem.setDate(date.getText());
							modifyItem.setMoney(Double.parseDouble(money.getText()));
							modifyItem.setMemo(memo.getText());
							modifyItem.setType(originItem.get(i).getType());
							modifyItem.setIo(originItem.get(i).getIo());
							
							//������ ������ �����
							originItem.remove(i);
							
							//����� �׳� �� �ٸ� �������� ������ ��ü ��¥ list���� ������
							if(originItem.size()> 0) hm.put(klist[0], originItem);
							else hm.remove(klist[0]);
							
							//������ ��¥�� �ٸ� �͵� ������ �� list�޾Ƽ� �ű⿡ �߰�
							//�ƴϸ� ���ο� list ����
							if(hm.containsKey(modifyItem.getDate()))
								changeItem = hm.get(modifyItem.getDate());
							
							changeItem.add(modifyItem);
							hm.put(date.getText(),changeItem);
							
							break;
						}
					}				
				}
				//Dialog ��
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