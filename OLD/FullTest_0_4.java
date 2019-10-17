import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.*;

public class FullTest_0_4 
{   
    public static ArrayList<String> getTitle(ArrayList<String> RAW)
    {
	ArrayList<String> out = new ArrayList<String>();
	Pattern p = Pattern.compile( "/v/(.*)</a></b><br/></td><td" );
	for ( String input : RAW)
	    {
		Matcher m = p.matcher( input );
		if ( input.length() >  12 && ( input.substring(0,12) ).equals( "<td width=23" ))
		    {		
			while ( m.find() )
			    {
				String rawOut = m.group();
				out.add( rawOut.substring( 3, rawOut.length() - 21 ) );
			    }
		    }		
	    }
	return out;
    }    
    
    public static void main(String[] args) throws MalformedURLException, IOException
   {
      ArrayList<String> RAW = new ArrayList<String> ();
      File dir = new File("MP_Download_Folder");
      dir.mkdir();

      for (int i=1; i <=20; i++)
      {
          URL website = new URL("http://www.mountainproject.com/v/for-sale--wanted/103989416__" + i);
          
          Scanner myScanner = new Scanner(website.openStream());
          while (myScanner.hasNextLine()) 
            {
		RAW.add(myScanner.nextLine());
	    }
       }
       
       //System.out.println(RAW.get(0));

        ArrayList<String> Out = getTitle(RAW);
	for ( String OUT : Out)
	    {
		System.out.println(OUT+ "\n");
	    }

    }
}
