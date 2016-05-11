package navigation;

import org.w3c.dom.Element;

import java.util.Comparator;

/**
 * Created by Joci on 2016.04.23..
 */
public class FScoreComparator implements Comparator <Element> {

    private AlgorithmImpl ai;

    public int compare(Element x, Element y) {

        this.ai=new AlgorithmImpl();

        if(ai.getfScore().get(x)>ai.getfScore().get(y)) {
            return -1;
        } else if (ai.getfScore().get(x)<ai.getfScore().get(y)) {
            return 1;
        } else {
            return 0;
        }
    }
}
