import java.lang.ref.PhantomReference; 
import java.lang.ref.Reference; 
import java.lang.ref.ReferenceQueue; 

public class Phantom { 
    static ReferenceQueue rq = new ReferenceQueue(); 
    public static void main(final String[] args) throws Exception { 
	PhantomReference ref = new PhantomReference(new Object(), rq); 
	System.out.println("ref: " + ref); 
	System.gc(); 

	Reference rmRef = rq.remove(1000); 
	if (rmRef == null) { 
	    System.out.println("Phantom reference not removed");
	} else {
	    System.out.println("Phantom reference removed");
	}
    } 
}
