package GUI;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import Vo.typeData;

class pCategory extends JPanel {
	
	double data[] = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
	int arcAngle[] = new int [8];
	Color color[] = {Color.RED, Color.BLUE, Color.MAGENTA, Color.ORANGE, Color.GREEN, Color.PINK, Color.YELLOW, Color.WHITE};
	String itemName[] = {"기타","식사","문화","모임","화장품","소셜","카페","인터넷"};
	ChartPanel chartPanel = new ChartPanel();
	HashMap<String,Double> chm = new HashMap();
	
	pCategory(ArrayList<typeData> tlist)
	{
		setLayout(new BorderLayout());
		if(!tlist.isEmpty()) calculateData(tlist);
		add(chartPanel, BorderLayout.CENTER);
	}
	
	void calculateData(ArrayList<typeData> datalist)
	{
		double num;
		String t;
		for(int i=0; i<datalist.size(); i++)
		{
			t= datalist.get(i).getType();
			if(t.equals("카드") || t.equals("용돈")) continue;
			if(chm.containsKey(t))
			{
				num = chm.get(t);
				num+= datalist.get(i).getMoney();
				chm.put(t, num);
			}
			else
			{
				chm.put(t, datalist.get(i).getMoney());
			}
		}
		for(int i=0; i<data.length; i++)
		{
			if(chm.containsKey(itemName[i])) data[i] = chm.get(itemName[i]);
		}
		drawChart();
	}
	
	void drawChart() 
	{
		double sum = 0.0;
		for(int i=0; i<data.length; i++) 
		{
			sum+= data[i];
		}
		if(sum == 0.0) return;

		for(int i=0; i<data.length; i++)
		{
			arcAngle[i]=(int)Math.round(data[i]/sum*360);
		}
		chartPanel.repaint();
	}

	class ChartPanel extends JPanel 
	{
		public void paintComponent(Graphics g) 
		{
			super.paintComponent(g);
			int startAngle = 0;
			
			for(int i=0; i<data.length; i++) 
			{
				g.setColor(color[i]);
				g.fillArc(100,50,400,400,startAngle, arcAngle[i]);
				startAngle = startAngle + arcAngle[i];
			}
			
			for(int i=0; i<data.length; i++) 
			{
				g.setColor(color[i]);
				g.drawString(itemName[i]+ " " + Math.round(arcAngle[i]*100./360.)+ "% " +data[i], 50 , 500+i*20);
			}
		}
	}	
}
