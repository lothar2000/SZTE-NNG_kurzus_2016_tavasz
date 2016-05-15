package navigation;

import org.w3c.dom.Element;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Joci on 2016.05.10..
 */
public class DistanceResultImpl implements DistanceResult {

    private AlgorithmImpl ai;
    private GraphImpl gi;

    private int startNodeID;
    private int destNodeID;

    private Element startElement;
    private Element destElement;




    public DistanceResultImpl(int s,int d, Graph graph) {
        ai=new AlgorithmImpl();
        gi=(GraphImpl) graph;
        ai.preprocess(gi);

        this.startNodeID=s;
        this.destNodeID=d;

        startElement=gi.getNodeMap().get(s);
        destElement=gi.getNodeMap().get(d);

    }


    public List<Integer> getResultPath() {

        List <Integer> ResultPath=new LinkedList<Integer>();

        Integer integ=new Integer(0);



        for (Element g:ai.aStar(startElement,destElement)) {
            integ=Integer.valueOf(Integer.parseInt(g.getAttribute("id")));


            ResultPath.add(integ);
        }

        return ResultPath;
    }

    public double getTravelDistanceOfResultPath() {

        double sumDistance=0;

        Element actual;
        Element actual2;


        for(int i=0; i<=getResultPath().size()-2;i++) {
            actual=gi.getNodeMap().get(getResultPath().get(i));

            actual2=gi.getNodeMap().get(getResultPath().get(i+1));

            sumDistance+=ai.airDistance(actual,actual2);

        }

        return sumDistance;

    }
}
