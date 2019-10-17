public class SimpleLinkedList
{


    //a get method
    public int get( int index )
    {
	if ( first == null || index < 0 || index > length())
	    {
	        throw new IndexOutofBoundsException;
	    }
	LinkedListCell l = first;
	int i = 0;
	while ( i < index )
	    {
		l = l.next;
	        i++;
	    }
	return l.value;
    }

}
