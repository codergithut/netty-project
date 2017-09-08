package abstracts.fileOperation;

import java.util.Comparator;

public class SentenceComparator implements Comparator<Sentence> {

	public int compare(Sentence st1, Sentence st2) {
//		return (int)(st2.getWeigh() - st1.getWeigh());
		double a1 = st2.getWeigh();
	    double a2 = st1.getWeigh();
	    if (a1 > a2) {
	        return 1;
	    }
	    if(a1 == a2){
	        return 0;
	    }
	    return -1;
	}
}
