package navigation;

import org.w3c.dom.Element;

import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by Joci on 2016.04.23..
 */
public class FScoreComparator implements Comparator <Element> {

    private AlgorithmImpl ai;
    private GraphImpl gi;
    HashMap<Element,Double> fScoreMap;

    FScoreComparator(Graph graph) {
        ai=new AlgorithmImpl();
        gi=(GraphImpl) graph;
        ai.preprocess(gi);
    }

    public int compare(Element x, Element y) {

        //System.out.println("X id: " + x.getAttribute("id") + ", Y id: " +  y.getAttribute("id"));
        //System.out.println("fScoreMap.get(x) " + fScoreMap.get(x) + " ->  fScoreMap.get(y)" +  "  >  " + fScoreMap.get(y) + "\n");

        if(fScoreMap.get(x)>fScoreMap.get(y)) {
            return -1;
        } else if (fScoreMap.get(x)<fScoreMap.get(y)) {
            return 1;
        } else {
            return 0;
        }
    }

    public void pullFScoreMap(HashMap<Element, Double> m) {
        this.fScoreMap=m;
    }
}
