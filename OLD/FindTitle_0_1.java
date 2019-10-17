import java.util.regex.*;
import java.util.ArrayList;

public class FindTitle_0_1
{
    static ArrayList<String> test;)//replace <test> with input ArrayList name

    public static ArrayList<String> main ( String [] args )
    {

	ArrayList<String> _test =  test;
	ArrayList<String> out = new ArrayList<String>();

	for ( String input : _test)//replace <test> with input ArrayList name
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
}
