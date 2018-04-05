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
This class is for creating the page for displaying the correct password

*/
public class tutorial extends JDialog implements ActionListener {
	Front f;
	int WIDTH=1000;
	//radiobutton part
	//password
	int[] res=new int[6];
	int current=0;//which digit of password is in
	boolean rb=false;//is radiobutton chosen
	//for tutorial
	JPanel functionalb=new JPanel(new GridLayout(2,0));
	
    JPanel rt = new JPanel(new BorderLayout());//radio button part
    ButtonGroup bgr=new ButtonGroup();
    JRadioButton fr=new JRadioButton();
    JRadioButton sr=new JRadioButton();
    
    JPanel buttons=new JPanel( new FlowLayout(FlowLayout.LEFT));
    JButton pmc=new JButton("Password memorized - Continue");
    JButton help=new JButton("Help");
    
    //images
    JPanel img=new JPanel(new GridLayout(4,4));
    JLayeredPane[] jlp=new JLayeredPane[16];
	ImageIcon[] ii=new ImageIcon[16];
	JLabel[] jl=new JLabel[16];
    JLabel[] fornum=new JLabel[10];
    ImageIcon[] num=new ImageIcon[10];
	
	//for password
    JPanel rPanel = new JPanel(new BorderLayout());//radio button part
    ButtonGroup bgp=new ButtonGroup();
    JRadioButton fb=new JRadioButton();
    JRadioButton sb=new JRadioButton();

  public tutorial(Front f, String title) {
    super(f, title, true);
    if (f != null) {
    	this.f=f;
    }
    setup();
    add(functionalb,BorderLayout.NORTH);
    add(img,BorderLayout.CENTER);
    add(rPanel,BorderLayout.SOUTH);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setSize(new Dimension(WIDTH,800));
    this.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
	        LogStore.getInstance().createLog(f.getUser(), "Tutorial - User closed tutorial", f.type[f.current]);
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
		  ii[x]=new ImageIcon(new ImageIcon(f.img[f.current][x]).getImage().getScaledInstance(WIDTH/4,149, Image.SCALE_DEFAULT));//get image path from front
		  jl[x]=new JLabel(ii[x]);
		  jl[x].setBounds(0, 0, WIDTH/4, 149);
		//  jl[x].setPreferredSize(new Dimension(200,150));
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
					LogStore.getInstance().createLog(f.getUser(), "User selected image", f.type[f.current]);
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
	  for(int x=0;x<5;x++) {
		  fornum[x].setBounds(x*20, 0, 20, 20);
		  jlp[f.result[f.current][x]].add(fornum[x], 0);
	  }
	  if(f.result[f.current][5]==0) {//radiobuttons
		  fb.setOpaque(true);
		  fb.setBackground(Color.YELLOW);
	  }
	  else {
		  sb.setOpaque(true);
		  sb.setBackground(Color.YELLOW);
	  }
	  img.setPreferredSize(new Dimension(WIDTH,600));
	  
  }
  public void check() {//to check if the user chosen all 5 images and the radio buttons. 
	  if(current==5&&rb) {
		  boolean correct=true;
		  for(int x=0;x<6;x++) {
			  if(res[x]!=f.result[f.current][x])correct=false;
		  }
		  String result="";
		  if(correct){
			  LogStore.getInstance().createLog(f.getUser(), "Tutorial - User correctly memorized password", f.type[f.current]);
			  result=result+"Congratulations! Correct!!!";
		  }
		  else {
			  LogStore.getInstance().createLog(f.getUser(), "Tutorial - User incorrectly memorized password", f.type[f.current]);
			  result=result+"Sorry, incorrect. Please check the correct password again.";
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
	LogStore.getInstance().createLog(f.getUser(), "Tutorial - User entered tutorial", f.type[f.current]);
		
	setupimg();
	  
  	fr.setText("Show Password");
    fr.setPreferredSize(new Dimension(400,30));
    fr.setSelected(true);
    fr.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {//display the correct password by number icon on images and background color for radiobutton
			// TODO Auto-generated method stub
			for(int x=0;x<5;x++) {
				  jlp[f.result[f.current][x]].add(fornum[x], 0);
			}
			  if(f.result[f.current][5]==0) {//radiobuttons
				  fb.setOpaque(true);
				  fb.setBackground(Color.YELLOW);
			  }
			  else {
				  sb.setOpaque(true);
				  sb.setBackground(Color.YELLOW);
			  }
			LogStore.getInstance().createLog(f.getUser(), "Tutorial - User toggled on selected images view", f.type[f.current]);
			repaint();
		}
    	
    });
    sr.setText("Hide Password");
    sr.setPreferredSize(new Dimension(400,30));
    sr.addActionListener(new ActionListener() {//hide the correct password
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			for(int x=0;x<5;x++) {
				  jlp[f.result[f.current][x]].remove(fornum[x]);
			}
			  if(f.result[f.current][5]==0) {//radiobuttons
				  fb.setOpaque(false);
			  }
			  else {
				  sb.setOpaque(false);
			  }
			LogStore.getInstance().createLog(f.getUser(), "Tutorial - User toggled off selected images view", f.type[f.current]);
			repaint();
		}
    	
    });
    bgr.add(fr);
    bgr.add(sr);
    rt.add(fr,BorderLayout.WEST);
    rt.add(sr,BorderLayout.EAST);
	  
    pmc.setPreferredSize(new Dimension(300,30));
    pmc.addActionListener(new ActionListener() {//the user confirmed that the correct password is memorized, the user can go to next part in front

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			f.createb[f.current][1].setEnabled(true);
			LogStore.getInstance().createLog(f.getUser(), "Tutorial - User is confident they have memorized password. Exiting tutorial.", f.type[f.current]);
			setVisible(false);
		}
    	
    });
    help.setPreferredSize(new Dimension(80,30));
    buttons.add(pmc);
    buttons.add(help);
    
    functionalb.add(rt);
    functionalb.add(buttons);
    
//fb sb are radio button for 0 or 1
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
  
//pack()
  //setVisible(true);

@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	
}



}