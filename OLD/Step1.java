
import java.net.*;
import java.io.*;
import java.util.Scanner;
public class Step1
{
    public static void main(String[] args) throws MalformedURLException, IOException 
    {
	URL website = new URL("http://mountainproject.com/v/for-sale--wanted/103989416");
	Scanner myScanner = new Scanner(website.openStream());
	PrintWriter out = new PrintWriter ("forumForSale1");
	while (myScanner.hasNextLine()) 
	    {
		out.println(myScanner.nextLine());
	    }
    }
}
