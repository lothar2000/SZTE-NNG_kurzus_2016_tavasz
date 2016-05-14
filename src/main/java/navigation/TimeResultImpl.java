package navigation;

import org.w3c.dom.Element;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Joci on 2016.05.12..
 */
public class TimeResultImpl implements TimeResult {

    private AlgorithmImpl ai;
    private GraphImpl gi;

    private int startNodeID;
    private int destNodeID;

    private Element startElement;
    private Element destElement;

    public TimeResultImpl(int s,int d) {
        gi=new GraphImpl();
        File f=new File("graph.xml");
        gi.initializeFromFile(f);

        this.startNodeID=s;
        this.destNodeID=d;

        startElement=gi.getNodeMap().get(s);
        destElement=gi.getNodeMap().get(d);

    }

    public List<Integer> getResultPath() {

        List <Integer> ResultPath=new LinkedList<Integer>();

        for (Element g:ai.aStar(startElement,destElement)) {
            ResultPath.add(Integer.parseInt(g.getAttribute("id")));
        }

        return ResultPath;
    }

    public double getTravelTimeOfResultPath() {

        double timeResult=0.0;

        Element actual;
        Element actual2;


        for(int i=0; i<=getResultPath().size()-2;i++) {
            actual=gi.getNodeMap().get(getResultPath().get(i));  //!!!!!!!!!!!!!!!!!! remélem jó az Integer objektum is

            actual2=gi.getNodeMap().get(getResultPath().get(i+1));
            double twoNodesAirDistance=ai.airDistance(actual, actual2);

            for(Element e:gi.getEdgeList()) {
                if(getResultPath().get(i).equals(Integer.parseInt(e.getFirstChild().getTextContent()))
                        && getResultPath().get(i+1).equals(Integer.parseInt(e.getChildNodes().item(1).getTextContent()))) {
                    timeResult+=twoNodesAirDistance/Double.parseDouble(e.getLastChild().getTextContent());
                }

            }

        }

        return timeResult;

    }
}
