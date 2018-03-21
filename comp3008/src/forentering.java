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
public class forentering extends JDialog implements ActionListener {
	Front f;
	//radiobutton part
	//password
	int[] res=new int[6];
	int current=0;
	boolean rb=false;
	//for tutorial
	//JPanel functionalb=new JPanel(new GridLayout(2,0));
	
  //  JPanel rt = new JPanel(new BorderLayout());//radio button part
  //  ButtonGroup bgr=new ButtonGroup();
 //   JRadioButton fr=new JRadioButton();
 //   JRadioButton sr=new JRadioButton();
    
    JPanel buttons=new JPanel( new FlowLayout(FlowLayout.LEFT));
    JButton pmc=new JButton("Password memorized - Continue");
    JButton help=new JButton("Help");
    
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

  public forentering(Front f, String title) {
    super(f, title, true);
    if (f != null) {
    	this.f=f;
    }
setup();
//add(functionalb,BorderLayout.NORTH);
add(img,BorderLayout.CENTER);
add(rPanel,BorderLayout.SOUTH);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setSize(new Dimension(800,600));
  }
  public void setupimg() {
	  File[] fs=new File("icon").listFiles();
	  for(int x=0;x<10;x++) {
		  num[x]=new ImageIcon(new ImageIcon(fs[x].getPath()).getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT));
		  fornum[x]=new JLabel(num[x]);
	  }
	  for(int x=0;x<16;x++) {
		  ii[x]=new ImageIcon(new ImageIcon(f.img[f.current][x]).getImage().getScaledInstance(199,149, Image.SCALE_DEFAULT));
		  jl[x]=new JLabel(ii[x]);
		  jl[x].setBounds(0, 0, 199, 149);
		  jlp[x]=new JLayeredPane();
		  jlp[x].setBorder(BorderFactory.createLineBorder(Color.BLACK));
		  jlp[x].add(jl[x], 1);
		  jlp[x].addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mouseClicked(MouseEvent e) {

				if(current<5) {
					int x=0;
					for(;x<16;x++) {
						if(jlp[x]==(JLayeredPane)e.getSource())break;
					}
					fornum[current+5].setBounds(current*20, 20,20,20);
					jlp[x].add(fornum[current+5], 0);
					res[current]=x;
					current++;
					repaint();
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
	/*  for(int x=0;x<5;x++) {
		  fornum[x].setBounds(x*20, 0, 20, 20);
		  jlp[f.result[f.current][x]].add(fornum[x], 0);
	  }*/
	 /* if(f.result[f.current][5]==0) {//radiobuttons
		  fb.setOpaque(true);
		  fb.setBackground(Color.YELLOW);
	  }
	  else {
		  sb.setOpaque(true);
		  sb.setBackground(Color.YELLOW);
	  }*/
	  img.setPreferredSize(new Dimension(600,600));
	  
  }
  public void check() {
	  if(current==5&&rb) {
		  boolean correct=true;
		  ArrayList<String> pa=new ArrayList<>();
		  for(int x=0;x<5;x++) {
			  pa.add(res[x]+"");
			  if(res[x]!=f.result[f.current][x])correct=false;
		  }
		  pa.add(res[5]+"");
		  if(res[5]!=f.result[f.current][5])correct=false;
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
		  }
		  else {
			  if(f.counter<3) {
				  result=result+"Sorry, incorrect.\n"
						  +"You can have "+(3-f.counter)+" chances.";
			  }
			  else {
				  result=result+"Sorry, incorrect.\n"
						  +"But you can close this window and go to next one.";
				  f.enterb[f.current][1].setEnabled(true);
			  }
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
  public void setup() {
	setupimg();
/*	  
  	fr.setText("Show Password");
    fr.setPreferredSize(new Dimension(400,30));
    fr.setSelected(true);
    fr.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
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
			repaint();
		}
    	
    });
    sr.setText("Hide Password");
    sr.setPreferredSize(new Dimension(400,30));
    sr.addActionListener(new ActionListener() {
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
			repaint();
		}
    	
    });
    bgr.add(fr);
    bgr.add(sr);
    rt.add(fr,BorderLayout.WEST);
    rt.add(sr,BorderLayout.EAST);
	  */
    pmc.setPreferredSize(new Dimension(300,30));
    pmc.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			f.createb[f.current][1].setEnabled(true);
			setVisible(false);
		}
    	
    });
    help.setPreferredSize(new Dimension(80,30));
    buttons.add(pmc);
    buttons.add(help);
    
 //   functionalb.add(rt);
 //   functionalb.add(buttons);
    

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