import java.net.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Desktop;


class TabbedPane extends JFrame
{

    // code from chortle.ccsu.edu and stackoverflow.com
    public TabbedPane( ArrayList<String> body )
    {
	setTitle( "Mountain Project For-Sale Forum Search Results" );
	setSize( 800, 800 );
	JTabbedPane jtp = new JTabbedPane();
	getContentPane().add(jtp);

	for (String line : body)
	    {	
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
		jP.add(urlLink);
		jtp.addTab( "+", jP );
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
    public static ArrayList<String> getURL(ArrayList<String> RAW, String user_Input)
    {
   	 ArrayList<String> out = new ArrayList<String>();
	 ArrayList<String> url_ext = new ArrayList<String>();
	 ArrayList<String> post_title = new ArrayList<String>();
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
	      for ( String OUT : out)
	      {
		  if (OUT.contains (user_Input))
		      {
			  String[]parts = OUT.split("\">");
			  url_ext.add(parts[0]);
			  post_title.add(parts[1]);
		      }
	      }


	      return ;
    } 

    //the main method, prompts the user for input on the command line and outputs a frame with the urls of the posts containing the input word
    public static void main(String[] args) throws MalformedURLException, IOException, UnknownHostException
    {

      Scanner in = new Scanner (System.in);
      System.out.println("Search for keyword?");
      String user_Input = in.nextLine();
      
      ArrayList<String> raw_website = website_sc();
      
      ArrayList<String> Out = getURL(raw_website, user_Input);

      ArrayList<String> urls = new ArrayList<String>();
      for( String url : Out )
	  {
	      urls.add("http://www.mountainproject.com/v/" + url);
	  }

      TabbedPane tp = new TabbedPane( urls );
      tp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      tp.setVisible(true);
    }
}
