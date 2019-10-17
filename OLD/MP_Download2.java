import java.net.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Desktop;
import javax.imageio.ImageIO;
import java.awt.image.*;
import java.lang.NullPointerException;
import java.applet.Applet;


class TabbedPane extends JFrame
{

    // code from chortle.ccsu.edu and stackoverflow.com
    public TabbedPane( ArrayList<String> urls, ArrayList<String> titles, ArrayList<Image> postPix )//CHANGE
 {
	setTitle( "Mountain Project For-Sale Forum Search Results" );
	setSize( 1200, 1200 );
	JTabbedPane jtp = new JTabbedPane();
	getContentPane().add(jtp);

	int count = 0;
	for (String line : urls)
	{	
		JPanel jP = new JPanel();
		JButton urlLink = new JButton();
		urlLink.setText( "Open Post" );
		urlLink.addActionListener(new ActionListener()
		{ 
			public void actionPerformed(ActionEvent e)  
			{
			    try
				{Desktop.getDesktop().browse(new URL( line ).toURI());}
			    catch ( MalformedURLException a )
				{System.exit(-1);}
			    catch ( IOException b )
				{System.exit(-1);}
			    catch ( URISyntaxException c )
				{System.exit(-1);}
			}
		    });
		
		try
		    {
			Image a_pic = postPix.get(count);
			ImageIcon gearImage = new ImageIcon( a_pic );
			JLabel label = new JLabel("", gearImage, JLabel.CENTER);
			jP.add(label);
		    }
		catch (NullPointerException nu)
		    {
			System.out.println("Null Image");
		    }
		catch (IndexOutOfBoundsException ind)
		    {
			System.out.println("Index out of Bounds");
		    }		
		jP.add(urlLink);
		jtp.addTab( titles.get(count), jP );
		count++;
	} 
 }
}	   

public class MP_Download extends JFrame
{   
    
    //a method that scans the url and adds each line to an arraylist
    public static ArrayList<String> website_sc()
    {
	ArrayList<String> RAW = new ArrayList<String> ();
	try
	    {
		for (int i=1; i <=20; i++)
		    {
			URL website = new URL("http://www.mountainproject.com/v/for-sale--wanted/103989416__" + i);
			
			Scanner myScanner = new Scanner(website.openStream());
			while (myScanner.hasNextLine()) 
			    {RAW.add(myScanner.nextLine());}
		    }
	    }
	catch ( MalformedURLException d )
	    {
		System.exit(-1);
	    }
	catch ( IOException e )
	    {
		System.exit(-1);
	    }
	return RAW;
    }
    
    //processing the arraylist of lines from the website to output an arraylist of post titles with urls
    public static String[][] getURL(ArrayList<String> _RAW, String user_Input)
    {
   	 ArrayList<String> out = new ArrayList<String>();
	 String[][] urls_titles = new String[2][200];
   	 Pattern p = Pattern.compile( "/v/(.*)</a></b><br/></td><td" );
   	 for(String input : _RAW)
   	 {
          Matcher m = p.matcher( input );
   		 if(input.length() > 12 && ( input.substring(0,12) ).equals( "<td width=23" ))
   		 {		
   		    while ( m.find() )
   			    {
   				 String rawOut = m.group();
   				 out.add( rawOut.substring( 3, rawOut.length() - 21 ) );
   			    }
   		 }		
   	 }
	 int i = 0;
	 for ( String OUT : out)
	     {
		 if (OUT.contains (user_Input))
		     {
			 String[]parts = OUT.split("\">");
			 //0th dim is urls, 1st dim is titles
			 urls_titles[0][i] = parts[0];
			 urls_titles[1][i] = parts[1];
			 i++;
		     }
	     }
	 return urls_titles;
    } 
    
    ArrayList<String> a_post;
    
    //this method scans the page that each post links
    public static ArrayList<String>  post_sc( ArrayList<String> urls)  throws MalformedURLException, IOException //CHANGE
    {
	
	ArrayList<String> a_post = new ArrayList<String>();
	
	for (String a_url : urls)
	    {
		URL website = new URL("http://www.mountainproject.com/v/" + a_url);	
		Scanner myScanner = new Scanner(website.openStream());
		boolean b = false;
		while (myScanner.hasNextLine() && b == false) 
		    {
			String lineS = myScanner.nextLine();
			if (lineS.contains("jpg") && lineS.contains("medium")  && lineS.contains("/images/"))
			    {	
				System.out.println("hi");			
				a_post.add(lineS);
				b = true;
			    }
			else 
			    {
				b = false;
			    }
		    }
		if (b == false)
		    {
			a_post.add(null);
		    }
	    }
	
	return a_post;
    }
    
    //this method downloads images from the post and adds them to an array
    public static ArrayList<Image> getImage(ArrayList<String> postText) throws MalformedURLException, IOException //CHANGE
    {
	Image pic1 = null;	
	
	ArrayList<Image> imageLinks = new ArrayList<Image>();
	
	Pattern z = Pattern.compile( "http://www.mountainproject.com/images/(.*).jpg");
		for(String txt : postText)
		    {
			System.out.println(txt);
			Matcher m = z.matcher( txt );
			while ( m.find() )
			    {
				String rOut = m.group();
				if (rOut.length() < 90 && rOut.contains("medium"))
				    {
					
					try 
					    {
						//System.out.println(rOut);
						URL website = new URL(rOut);
						pic1 = ImageIO.read(website);
					    } 
					catch (IOException e) 
					    {
						System.out.println("Error in Image Capture");
					    } 
					imageLinks.add(pic1);
				    }
				else if(rOut.equals(null))
				    {
				        //pic1 = getImage("No_Image.jpg");
					//HERE!
					imageLinks.add(pic1);
				    }	    
			    }			
		    }
		
		return imageLinks;
    } 
    
    
    //the main method, prompts the user for input on the command line and outputs a frame with the urls of the posts containing the input word
    public static void main(String[] args) throws MalformedURLException, IOException, UnknownHostException, NullPointerException
    {
      String temp1 = "";
      int kk = 0;
      
      Scanner in = new Scanner (System.in);
      System.out.println("Search for keyword?");
      String user_Input = in.nextLine();
      
      ArrayList<String> raw_website = website_sc();
      String[][] urlsTitles = getURL(raw_website, user_Input);

      ArrayList<String> urls = new ArrayList<String>();
      ArrayList<String> titles = new ArrayList<String>();
      ArrayList<String> urls_ = new ArrayList<String>();

      for (int a = 0; a < urlsTitles[0].length; a++)
	  {
	      if ( urlsTitles[0][a] != null )
		  { 
		      urls.add(urlsTitles[0][a]);
		  }
	  }

      for (int b = 0; b < urlsTitles[0].length; b++)
	  {
	      if ( urlsTitles[0][b] != null )
		  { 
		      titles.add(urlsTitles[1][b]);
		  }
	  }

      for( String url : urls )
	  {
	      urls_.add("http://www.mountainproject.com/v/" + url);
  	  }

      ArrayList<String> postText = post_sc(urls); 

      ArrayList<Image> postImages = getImage(postText); 

      TabbedPane tp = new TabbedPane( urls_, titles, postImages );
      tp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      tp.setVisible(true);
    }
}
