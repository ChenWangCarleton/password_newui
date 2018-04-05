import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import comp3008.logger.LogStore;
/*
Author: Chen Wang
Purpose of this class: 
This class is for creating the front page of the new password scheme.

*/
public class Front extends JFrame {
	Random rand=new Random();//for generating random int
	String user=randomIdentifier();//random user name is generated
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
    String[] type= {"Email","Banking","Shopping"};

	//ui component
	JLabel userl=new JLabel("User:"+user );


	JLabel textl=new JLabel();//in case system status is asked to show to user
	
	
	JPanel[] createp=new JPanel[3];//panel for each part (6 parts in total, 3 for displaying correct password, 3 for inputing password
	JPanel[] enterp=new JPanel[3];
	JLabel[] createl=new JLabel[3];//label for each part
	JLabel[] enterl=new JLabel[3];
	JPanel[] createbp=new JPanel[3];//panel for buttons
	JPanel[] enterbp=new JPanel[3];
	JButton[][] createb=new JButton[3][2];//buttons createb[x][0] for displaying correct password,createb[x][1] for go to next one
	JButton[][] enterb=new JButton[3][2];//enterb[x][0] for entering password, enterb[x][1] for go to next one
	
	
	//password related
	int[][] result=new int[3][6];// correct password
	ArrayList<ArrayList<ArrayList<String>>> userresult=new ArrayList<>();//for displaying to user
	ArrayList<ArrayList<String>> first=new ArrayList<>();
	ArrayList<ArrayList<String>> second=new ArrayList<>();
	ArrayList<ArrayList<String>> third=new ArrayList<>();
	int counter=0;//flags for how many attempts the user had for current password
	
	//for entering
	int[][] ra=new int[3][16];//for random order of images in entering
	int[]  rt= {0,1,2};//for random order of category of password
	//images
	String[][] img=new String[3][16];//images' path
    File[] source=new File("img").listFiles();
    int[] sour= {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};//use for random

	//flags
	int current=0;//0-2
	static boolean isReady=false;//if next button should be selectable
	boolean Entering=false;//if the user is entering the password
	
	
	public void setup() {//to prepare all component
		
		
    	for(int x=0;x<3;x++) {
    		for(int y=0;y<5;y++) {
    			result[x][y]=rand.nextInt(16);
    		}
    		result[x][5]=rand.nextInt(2);//generate result
    	}
		for(int y=0;y<3;y++) {
			shuffleArray(sour);
			for(int x=0;x<16;x++) {
				img[y][x]=source[sour[x]].listFiles()[rand.nextInt(source[sour[x]].listFiles().length)].getPath();
			}
		}//generate images order
		for(int y=0;y<3;y++) {
			shuffleArray(sour);
			for(int x=0;x<16;x++) {
				ra[y][x]=sour[x];
			}
		}	//random images order for entering
		userl.setPreferredSize(new Dimension(600,50));
		textl.setPreferredSize(new Dimension(600,50));

		for(int x=0;x<3;x++) {//initialize the buttons
			createp[x]=new JPanel();
			createp[x].setBackground(Color.LIGHT_GRAY);
			createp[x].setPreferredSize(new Dimension(600,100));
			createp[x].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
			enterp[x]=new JPanel();
			enterp[x].setBackground(Color.LIGHT_GRAY);
			enterp[x].setPreferredSize(new Dimension(600,100));
			enterp[x].setBorder(BorderFactory.createLineBorder(Color.BLACK));

			
			createl[x]=new JLabel();
			createl[x].setText("Create Password for: "+type[x]);
			createl[x].setPreferredSize(new Dimension(600,30));
			
			//need to change here if want the user to enter random type of password
			enterl[x]=new JLabel();
			enterl[x].setText("Enter Password for: "+type[rt[x]]+" (3 Attempts Allowed)");
			enterl[x].setPreferredSize(new Dimension(600,30));
			
			
			
			//buttons
			createbp[x]=new JPanel( new FlowLayout(FlowLayout.LEFT));
			createbp[x].setPreferredSize(new Dimension(600,40));
			createbp[x].setBackground(Color.LIGHT_GRAY);
			enterbp[x]=new JPanel(new FlowLayout(FlowLayout.LEFT));
			enterbp[x].setPreferredSize(new Dimension(600,40));
			enterbp[x].setBackground(Color.LIGHT_GRAY);

			createb[x][0]=new JButton("Create");
			createb[x][0].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					tutorial tut=new tutorial(Front.this, type[current]);//open up a page to show correct password
					tut.pack();
					tut.setVisible(true);
				}
				
			});
			createb[x][1]=new JButton("Next");
			createb[x][1].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {//go to next part
					// TODO Auto-generated method stub
					createb[current][0].setEnabled(false);
					createb[current][1].setEnabled(false);					
					if(current==2) {//check if its time for user to entering
						Entering=true;
						isReady=false;
						current=0;
						enterb[current][0].setEnabled(true);
					}
					else {
						current++;
						createb[current][0].setEnabled(true);
					}
				}
				
			});
			enterb[x][0]=new JButton("Enter");
			enterb[x][0].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {//go to entering page
					// TODO Auto-generated method stub
					forentering fe=new forentering(Front.this, type[rt[current]]);
					fe.pack();
					fe.setVisible(true);
					if(counter>3) {
						enterb[current][1].setEnabled(true);
					}
				}
				
			});
			enterb[x][1]=new JButton("Next");
			enterb[x][1].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {//go to next part, check if ends
					// TODO Auto-generated method stub
					enterb[current][0].setEnabled(false);
					enterb[current][1].setEnabled(false);		
					counter=0;
					if(current==2) {
						end();
					}
					else {
						current++;
						enterb[current][0].setEnabled(true);
					}
				}
				
			});
			
			createb[x][0].setPreferredSize(new Dimension(80,30));
			createb[x][1].setPreferredSize(new Dimension(80,30));
			enterb[x][0].setPreferredSize(new Dimension(80,30));
			enterb[x][1].setPreferredSize(new Dimension(80,30));
			
			createb[x][0].setEnabled(false);
			createb[x][1].setEnabled(false);
			enterb[x][0].setEnabled(false);
			enterb[x][1].setEnabled(false);
			
			createbp[x].add(createb[x][0]);
			createbp[x].add(createb[x][1]);
			enterbp[x].add(enterb[x][0]);
			enterbp[x].add(enterb[x][1]);
						
			createp[x].add(createl[x],BorderLayout.NORTH);
			createp[x].add(createbp[x],BorderLayout.SOUTH);
			enterp[x].add(enterl[x],BorderLayout.NORTH);
			enterp[x].add(enterbp[x],BorderLayout.SOUTH);			
			
			
		}
		createb[0][0].setEnabled(true);
	}
	public void end() {//if ends, show a page for correct passwords and user's inputs
    	JOptionPane.showMessageDialog(null, toString());
    	//LogStore.writeLogs();
	}
	public String toString() {//get user's input and correct answer
		String tor="";
		userresult.add(first);
		userresult.add(second);
		userresult.add(third);
		
		for(int x=0;x<3;x++) {
			tor=tor+"Correct password for "+type[rt[x]]+"->\n";
			for(int y=0;y<5;y++) {
				tor=tor+result[rt[x]][y]+",";
			}
			tor=tor+result[rt[x]][5]+"\nYour input->\n";
			for(int y=0;y<userresult.get(x).size();y++) {
				tor=tor+userresult.get(x).get(y)+"\n";
			}
		}
		return tor;
	}
    public Front() {
		shuffleArray(rt);
    	setup();
    	this.setLayout(new GridLayout(8,0));
    	
    	add(userl);
    	for(int x=0;x<3;x++) {
    		add(createp[x]);
    	}
    	for(int x=0;x<3;x++) {
    		add(enterp[x]);
    	}
       setResizable(false);
       setTitle("Password Tester");
       setSize(700, 700);
       setDefaultCloseOperation(EXIT_ON_CLOSE);
       this.addWindowListener(new WindowAdapter() {
           public void windowClosing(WindowEvent e) {
              LogStore.writeLogs();
           }
       });
    }
//used to generate random user name
    final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";

    public String randomIdentifier() {
        StringBuilder builder = new StringBuilder();
        while(builder.toString().length() == 0) {
            int length = rand.nextInt(5)+5;
            for(int i = 0; i < length; i++) {
                builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
            }
        }
        return builder.toString();
    }
    
    //used to shuffle array
    static void shuffleArray(int[] ar)
    {
      Random rnd = ThreadLocalRandom.current();
      for (int i = ar.length - 1; i > 0; i--)
      {
        int index = rnd.nextInt(i + 1);
        int a = ar[index];
        ar[index] = ar[i];
        ar[i] = a;
      }
    }
    public static void main(String[] args) {
    	Front ex = new Front();
       ex.setVisible(true);
    }
}