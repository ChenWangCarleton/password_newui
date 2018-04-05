import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import comp3008.logger.LogStore;
/*
Author: Chen Wang
Purpose of this class: 
This class is for creating the page for entering

*/
public class forentering extends JDialog implements ActionListener {
	Front f;
	int WIDTH=1000;
	//radiobutton part
	//password
	int[] res=new int[6];
	int current=0;//which digit of password is in
	boolean rb=false;//is radiobutton chosen


    
    //images
    JPanel img=new JPanel(new GridLayout(4,4));
    JLayeredPane[] jlp=new JLayeredPane[16];
	ImageIcon[] ii=new ImageIcon[16];
	JLabel[] jl=new JLabel[16];
	//JPanel[] nums=new JPanel[16];//
    JLabel[] fornum=new JLabel[10];
    ImageIcon[] num=new ImageIcon[10];
	
	//for password
    JPanel rPanel = new JPanel(new BorderLayout());//radio button part
    ButtonGroup bgp=new ButtonGroup();
    JRadioButton fb=new JRadioButton();
    JRadioButton sb=new JRadioButton();
    
    String title = "";
  public forentering(Front f, String title) {
    super(f, title, true);
    if (f != null) {
    	this.f=f;
    }
    setup();
    this.title = title;
    add(img,BorderLayout.CENTER);
    add(rPanel,BorderLayout.SOUTH);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setSize(new Dimension(WIDTH,600));
    LogStore.getInstance().createLog(f.getUser(), "User logged into app", title);
	
    this.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
           //System.exit(0);
     	   //LogStore.writeLogs();
     	   LogStore.getInstance().createLog(f.getUser(), "User closed app", title);
        }
    });
  }
  
  public void setupimg() {//to prepare the images
	  File[] fs=new File("icon").listFiles();
	  for(int x=0;x<10;x++) {//this is for little number icon to indicate the order of images
		  num[x]=new ImageIcon(new ImageIcon(fs[x].getPath()).getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT));
		  fornum[x]=new JLabel(num[x]);
	  }
	  for(int x=0;x<16;x++) {
		  ii[x]=new ImageIcon(new ImageIcon(f.img[f.rt[f.current]][f.ra[f.rt[f.current]][x]]).getImage().getScaledInstance(WIDTH/4,149, Image.SCALE_DEFAULT));
		  jl[x]=new JLabel(ii[x]);
		  jl[x].setBounds(0, 0, WIDTH/4, 149);
		  jlp[x]=new JLayeredPane();
		  jlp[x].setBorder(BorderFactory.createLineBorder(Color.BLACK));
		  jlp[x].add(jl[x], 1);
		  jlp[x].addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mouseClicked(MouseEvent e) {

				if(current<5) {//show number icon for user to know the x th image for the password is chosen
					int x=0;
					for(;x<16;x++) {
						if(jlp[x]==(JLayeredPane)e.getSource())break;
					}
					fornum[current+5].setBounds(current*20, 20,20,20);
					jlp[x].add(fornum[current+5], 0);
					res[current]=x;
					current++;
					repaint();
					LogStore.getInstance().createLog(f.getUser(), "User selected image", title);
					if(current==5)check();
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {

			} 
		  });
		  img.add(jlp[x]);
	  }

	  img.setPreferredSize(new Dimension(WIDTH,600));
	  //LogStore.getInstance().createLog(f.getUser(), "Images displayed to user", f.type[f.current]);
  }
  public void check() {//to check if the user chosen all 5 images and the radio buttons. 
	  if(current==5&&rb) {
 
		  boolean correct=true;
		  ArrayList<String> pa=new ArrayList<>();
		  for(int x=0;x<5;x++) {
			  pa.add(f.ra[f.rt[f.current]][res[x]]+"");
			  if(f.ra[f.rt[f.current]][res[x]]!=f.result[f.rt[f.current]][x])correct=false;
		  }
		  pa.add(res[5]+"");
		  if(res[5]!=f.result[f.rt[f.current]][5])correct=false; 
		  if(f.current==0) {
			  f.first.add(pa);//transfer the user's input to main program
		  }
		  else if (f.current==1) {
			  f.second.add(pa);
		  }
		  else {
			  f.third.add(pa);
		  }
		  
		  
		  f.counter=f.counter+1;
		  
		  String result="";
		  if(correct) {
			  result=result+"Congratulations! Correct!!!\n"
					  +"You can now go to next one!";
			  f.enterb[f.current][1].setEnabled(true);
			  setVisible(false);
			  //log correct time here
			  LogStore.getInstance().createLog(f.getUser(), "Login Success", title);
		  }
		  else {
			  if(f.counter<3) {
				  result=result+"Sorry, incorrect.\n"
						  +"You can have "+(3-f.counter)+" chances.";
				  LogStore.getInstance().createLog(f.getUser(), "Login Attempt Fail", title);
			  }
			  else {
				  result=result+"Sorry, incorrect.\n"
						  +"But you can close this window and go to next one.";
				  LogStore.getInstance().createLog(f.getUser(), "Login Fail - No more attempts", title);
				  f.enterb[f.current][1].setEnabled(true);
			  }
			 //log incorrect time here
		  }
  		JOptionPane.showMessageDialog(null, result);
  		current=0;
  		rb=false;
  		for(int x=0;x<5;x++) {
  			jlp[res[x]].remove(fornum[x+5]);
  		}
  		bgp.clearSelection();
  		repaint();
	  }
	  }
  public void setup() {//to set up the components
	setupimg();


  	fb.setText("0");
    fb.setPreferredSize(new Dimension(400,30));
    fb.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			res[5]=Integer.parseInt(((JRadioButton)e.getSource()).getText());
			rb=true;
			check();
		}
    	
    });
    sb.setText("1");
    sb.setPreferredSize(new Dimension(400,30));
    sb.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			res[5]=Integer.parseInt(((JRadioButton)e.getSource()).getText());
			rb=true;
			check();
		}
    	
    });
    bgp.add(fb);
    bgp.add(sb);
    rPanel.add(fb,BorderLayout.WEST);
    rPanel.add(sb,BorderLayout.EAST);
      
      

  }
 
@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	
}



}