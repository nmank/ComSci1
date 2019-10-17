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


class TabbedPane extends JComponent
{

    // code from chortle.ccsu.edu, stackoverflow.com, and java api
    //this method creates a tabbed pane with the post titles as the tab labels, a button that sends
    public TabbedPane( ArrayList<String> urls, ArrayList<String> titles, ArrayList<Image> pix )
    {
	setTitle( "Mountain Project For-Sale Forum Search Results" );
	setSize( 800, 800 );
	JTabbedPane jtp = new JTabbedPane();
	getContentPane().add(jtp);

	int count = 0;
	for (final String line : urls)
	    {	
		ImageIcon icon =  new ImageIcon(pix.get(count));
		JPanel jP = new JPanel();
		JButton urlLink = new JButton();
		urlLink.setText( "Open Post" );
		urlLink.addActionListener(new ActionListener()
		    { 
			public void actionPerformed(ActionEvent e)  
			{
			    try
				{
				    Desktop.getDesktop().browse(new URL( line ).toURI());				}
			    catch ( MalformedURLException a )
				{
				    System.exit(-1);
				}
			    catch ( IOException b )
				{
				    System.exit(-1);
				}
			    catch ( URISyntaxException c )
				{
				    System.exit(-1);
				}
			}
		    });
		jP.add(icon);
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
    public static String[][] getURL(ArrayList<String> RAW, String user_Input)
    {
   	 ArrayList<String> out = new ArrayList<String>();
	 String[][] urls_titles = new String[2][200];
   	 Pattern p = Pattern.compile( "/v/(.*)</a></b><br/></td><td" );
   	 for(String input : RAW)
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
    
    //this method scans the page that each post links
    public static String post_sc(String Post_Extension_Input)  throws MalformedURLException, IOException
    {
	   String POST = "";
	
		URL website = new URL(Post_Extension_Input );
		
		Scanner myScanner = new Scanner(website.openStream());
		while (myScanner.hasNextLine()) 
		    {POST = POST + (myScanner.nextLine()) + "\r\n";}
          
	   return POST;
    }

    //this method downloads images from the post and adds them to an array
    public static ArrayList<Image> getImage(ArrayList<String> postText) throws MalformedURLException, IOException
    {
	Image pic1 = null;
	
	ArrayList<Image> imageLinks = new ArrayList<Image>();
	ArrayList<String> out = new ArrayList<String>();
	Pattern p = Pattern.compile( "www.mountainproject.com/images(.*).jpeg");
	for(String input : postText)
	    {
		Matcher m = p.matcher( input );
		if(input.length() > 12)
		    {		
			while ( m.find() )
			    {
				String rawOut = m.group();
				out.add( rawOut );
			    }
		    }		
	    }
	for (String imageRAWURL : out)
	    {
		int j=0;
		
		URL website = new URL(imageRAWURL);
		Scanner myScanner = new Scanner(website.openStream());
		while (myScanner.hasNextLine())
		    {
			try 
			    {pic1 = ImageIO.read(website);} 
			catch (IOException e) 
			    {}
			j++;
		    }
		imageLinks.set(j, pic1);
	    }
	return imageLinks;
    } 
    
    
    //the main method, prompts the user for input on the command line and outputs a frame with the urls of the posts containing the input word
    public static void main(String[] args) throws MalformedURLException, IOException, UnknownHostException, NullPointerException
    {
      String temp1 = "";
      
      Scanner in = new Scanner (System.in);
      System.out.println("Search for keyword?");
      String user_Input = in.nextLine();
      
      ArrayList<String> raw_website = website_sc();
      String[][] urlsTitles = getURL(raw_website, user_Input);

      ArrayList<String> urls = new ArrayList<String>();
      ArrayList<String> titles = new ArrayList<String>();

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
      ArrayList<String> postText = new ArrayList<String> ();

      ArrayList<String> urls_ = new ArrayList<String>();
      for( String url : urls )
	  {
	      urls_.add("http://www.mountainproject.com/v/" + url);
	      postText.add(post_sc("http://www.mountainproject.com/v/" + url));
  	  }
      ArrayList<Image> images = getImage(postText);

      TabbedPane tp = new TabbedPane( urls_, titles, images);
      tp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      tp.setVisible(true);
    }
}
