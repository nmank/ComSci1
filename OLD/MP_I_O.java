import java.util.Scanner;
import java.util.ArrayList;

interface MP_I_O
{
   Scanner user_In = new Scanner (System.in);
   String user_Input = user_In.nextLine();
   ArrayList<String> title_Output = new ArrayList<String> ();
}