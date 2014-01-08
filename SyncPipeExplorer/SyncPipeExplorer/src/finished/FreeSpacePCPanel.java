package finished;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
 
public class FreeSpacePCPanel extends JPanel{
	private int rootCounter=2;//internal and external storage on mobile, is overwritten in constructor
	private JLabel[] labels;
	private long[] freeSpace;
	private long[] totalSpace;
	private Font font;
	
	//constructor that automatically uses free and total space to create everything from root drives
    public FreeSpacePCPanel(Font font){	
    	this.font=font;
    	freeSpace = new long[File.listRoots().length];
    	totalSpace = new long[File.listRoots().length];
    	rootCounter=0;
    	
    	for(File root:File.listRoots()){
    		if(root.listFiles()!=null){
    			freeSpace[rootCounter]=root.getFreeSpace();
    			totalSpace[rootCounter]=root.getTotalSpace();
    			rootCounter++;
    		}
    	}
    	this.createPanel();
    	this.repaint();
    }
    //constructor that uses free and total space from parameters
    public FreeSpacePCPanel(Font font, long[] freeSpace, long[] totalSpace){
    	this.font=font;
    	this.freeSpace=freeSpace;
    	this.totalSpace=totalSpace;
    	this.rootCounter=freeSpace.length;
    	createPanel();
    	this.repaint();
    }
    private void createPanel(){
    	JLabel pcLabel;
    	this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    	labels = new JLabel[File.listRoots().length];
    	for(int i=0; i<rootCounter; i++){
    		pcLabel=new JLabel(File.listRoots()[i].getAbsolutePath());
    		pcLabel.setBorder(new EmptyBorder(10,10,10,10));
    		pcLabel.setAlignmentX(LEFT_ALIGNMENT);
    		this.add(pcLabel);
    		labels[i]=pcLabel;
    	}
    }
    public void paint(Graphics graphics){
    	super.paint(graphics);
    	String text;
    	for(int i=0; i<rootCounter; i++){
	    	graphics.setColor(new Color(0,210,250));
	    	graphics.drawRect((int)labels[i].getBounds().getMaxX(), (int) (labels[i].getBounds().getCenterY()+labels[i].getBounds().getMinY())/2, this.getWidth()-50, 20);
	    	graphics.setColor(Color.WHITE);
	    	double fs = freeSpace[i];
	    	double ts = totalSpace[i];
	    	
	    	System.out.println("fs: "+fs);
	    	System.out.println("ts: "+ts);
	    	
	    	graphics.fillRect((int)labels[i].getBounds().getMaxX(), (int)(labels[i].getBounds().getCenterY()+labels[i].getBounds().getMinY())/2,
	    			(int)(this.getWidth()-50-(fs/(ts/100)*(this.getWidth()-50)/100)), 20);
	    	text = freeSpace[i]/1024/1024/1024+" GB frei";
	    	graphics.setColor(new Color(0,210,250));
	    	graphics.setFont(font);
	    	FontMetrics fm = graphics.getFontMetrics();
	        int totalWidth = fm.stringWidth(text);
	    	graphics.drawString(text, (int)labels[i].getBounds().getMaxX()+(this.getWidth()-50 - totalWidth)/2, (int)(labels[i].getBounds().getCenterY()+labels[i].getBounds().getMinY())/2+font.getSize());
    	}
    }
    public void setFreeSpace(long[] freeSpace){
    	this.freeSpace=freeSpace;
    }
    public void setTotalSpace(long[] totalSpace){
    	this.totalSpace=totalSpace;
    }
}
