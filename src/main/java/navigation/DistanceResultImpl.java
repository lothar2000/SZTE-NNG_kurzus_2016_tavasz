package navigation;

import org.w3c.dom.Element;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Joci on 2016.05.10..
 */
public class DistanceResultImpl implements DistanceResult {

    private AlgorithmImpl ai;
    private int startNodeID;
    private int destNodeID;

    private Element startElement;
    private Element destElement;


    public DistanceResultImpl(int s,int d) {
        GraphImpl gi=new GraphImpl();

        this.startNodeID=s;
        this.destNodeID=d;

        for(Element e:gi.getNodeList()) {
            if(Integer.parseInt(e.getAttribute("id"))==s) {
                startElement=e;
            } else if(Integer.parseInt(e.getAttribute("id"))==d) {
                destElement=e;
            }
        }


    }


    List<Integer> getResultPath() {
        ai=new AlgorithmImpl();

        LinkedList<Integer>  result=new LinkedList<Integer>();

        Integer puffer;

        for(Element e:ai.aStar(startElement,destElement)) {
            puffer=new Integer
        }



    }

    double getTravelDistanceOfResultPath() {


        LinkedList<Element> path=ai.aStar();
    }
}
