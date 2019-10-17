import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.*;

public class FullTest_0_1
{   
    
    public static ArrayList<String> getTitle(ArrayList<String> RAW)
    {
	ArrayList<String> out = new ArrayList<String>();
	
	for ( String input : RAW)//replace <test> with input ArrayList name
	    {
		Pattern p = Pattern.compile( "/v/(.*)</a></b><br/></td><td" );
		Matcher m = p.matcher( input );
		while ( m.find() )
		    {
			String rawOut = m.group();
			out.add(rawOut.substring( 3,rawOut.length()-21) );
		    }
	    }
	return out;
    }
    
    public static void main(String[] args) throws MalformedURLException, IOException, IndexOutOfBoundsException
    {
	ArrayList<String> RAW = new ArrayList<String> ();
	File dir = new File("MP_Download_Folder");
	dir.mkdir();
	
	for (int i=1; i <=20; i++)
	    {
		URL website = new URL("http://www.mountainproject.com/v/for-sale--wanted/103989416__" + i);
		
		PrintWriter out = new PrintWriter("MP_Download_Folder/MP_forum" + i + ".txt");
		Scanner myScanner = new Scanner(website.openStream());
		while (myScanner.hasNextLine()) 		    
		    {
			out.println(myScanner.nextLine());
			RAW.set(i, RAW.get(i) + myScanner.nextLine());
		    }
	    }

        ArrayList<String> Out = getTitle(RAW);
	for ( String OUT : Out)
	    {
		System.out.println(OUT);
	    }
    }
}
