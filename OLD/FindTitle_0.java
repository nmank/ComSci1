import java.util.regex.*;
import java.util.ArrayList;

public class FindTitle_0
{
    /*
    String input;
    public void FindTitle_0(String input)
    {
	for (String test : input)
	    {
		String _input = input;
		Pattern p = Pattern.compile( "/v/(.*)</a></b><br/></td><td" );
		Matcher m = p.matcher( _input );
		while (m.find())
		    {
			System.out.println(m.group());
		    }
	    }
    }
    */

    public static void main( String [] args )
    {
	ArrayList<String> test = new ArrayList<String>();
	test.add("asdfifj/v/you</a></b><br/></td><tdjdsdfjfdjdfkfsjsfosjdflakjdsfladskjflakdsjfldskjf");
	test.add("asdfifj/v/suck</a></b><br/></td><tdjdsdfjfdjdfkfsjsfosjdflakjdsfladskjflakdsjfldskjf");
	ArrayList<String> out = new ArrayList<String>();
	for (String _input : test)
	    {
		Pattern p = Pattern.compile( "/v/(.*)</a></b><br/></td><td" );
		Matcher m = p.matcher( _input );
		while (m.find())
		    {
		        String rawOut = m.group();
			out.add(rawOut.substring(3,rawOut.length()-21));
		    }
	    }
	for (String foo : out)
	    {
		System.out.println(foo);
	    }
    }
}
