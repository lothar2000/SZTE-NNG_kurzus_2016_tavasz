package navigation;

import org.w3c.dom.Element;

import java.io.File;

import java.util.*;

import static java.lang.Math.*;

/**
 * Implement your navigation algorithm here. This class will be instantiated
 * during the unit tests.
 */
public class AlgorithmImpl implements Algorithm {


    GraphImpl gi=new GraphImpl();

	HashSet<Element> closedSet=new HashSet<Element>(); //NEMTOM PONTOSAN MÉG HOGY MELYIK FAJTA MAP ÉS SET KELL

    HashSet<Element> openSett=new HashSet<Element>();

    HashMap<Element,Element>  cameFrom=new HashMap<Element,Element>();

    HashMap<Element, Double>  gScore=new HashMap<Element, Double>();



    HashMap<Element, Double>  fScore=new HashMap<Element, Double>();



    HashMap<Element, Double>  tentativeGScore=new HashMap<Element, Double>();


    private Element startElement;
    private Element destElement;






	public LinkedList<Element> aStar(Element start, Element goal) {

        Element s=start;

        LinkedList<Element> returnList = null;

        Element current;

        Comparator<Element> comparator=new FScoreComparator();

        PriorityQueue<Element> priorityQueueFScore = new PriorityQueue<Element>(100, comparator);  //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        priorityQueueFScore.add(s);

		while(!priorityQueueFScore.isEmpty()) {
            current=priorityQueueFScore.poll();
            System.out.println("current ID: " + current.getAttribute("id") + ", goal ID: " + goal.getAttribute("id") + "\n");
            if(current.getAttribute("id").equals(goal.getAttribute("id"))) {  //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!lehet hogy ==
                return reconstructPath(cameFrom,current);
            }

            priorityQueueFScore.remove(current);

            closedSet.add(current);

            if(current.getAttribute("id").equals(start.getAttribute("id"))) {  //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!lehet hogy ==
                cameFrom.put(current, null); //!!!!!!!!!!!!!nemtom már lehet e bele null-t rakni
                gScore.put(current,0.0);
                fScore.put(current,airDistance(current,goal));
            }

            for (Element neighbor:neighbors(current)) { //itt a neighbors eljárás sem biztos hogy jól működik
                if(closedSet.contains(neighbor)) {
                    continue;
                }

                // ideiglenes gScore:
                tentativeGScore.put(neighbor,gScore.get(current)+airDistance(current,neighbor));

                if (priorityQueueFScore.contains(neighbor)) {
                    if (tentativeGScore.get(neighbor)>=gScore.get(neighbor)) {
                        tentativeGScore.remove(neighbor);
                        continue;
                    }
                    priorityQueueFScore.remove(neighbor); //ez lehet hogy hülyeség
                }

                if(cameFrom.containsKey(neighbor)) {
                    cameFrom.remove(neighbor);
                }
                cameFrom.put(neighbor,current);


                if(gScore.containsKey(neighbor)) {
                    gScore.remove(neighbor);
                }
                gScore.put(neighbor,tentativeGScore.get(neighbor));  //LEHET MÉG KULCSDUPLIKÁLÓDÁS!!!!!!!


                tentativeGScore.remove(neighbor);

                if (fScore.containsKey(neighbor)) {
                    fScore.remove(neighbor);
                }
                fScore.put(neighbor,gScore.get(neighbor)+airDistance(neighbor,goal));

                priorityQueueFScore.add(neighbor);

            }

		}

        return null;

	}



    public double airDistance(Element A, Element B) {
        double xA = Double.parseDouble(A.getChildNodes().item(3).getTextContent()); //x coordinate of A element
        double yA = Double.parseDouble(A.getChildNodes().item(1).getTextContent()); //y coordinate of A element
        double xB = Double.parseDouble(B.getChildNodes().item(3).getTextContent()); //x coordinate of B element
        double yB = Double.parseDouble(B.getChildNodes().item(1).getTextContent()); //y coordinate of B// element
        return sqrt(pow(xB - xA, 2.0) + pow(yB - yA, 2.0));
    }



    public LinkedList<Element> neighbors(Element base) {
        LinkedList<Element> neighborList=new LinkedList<Element>();


        File f=new File("graph.xml");
        gi.initializeFromFile(f);

        LinkedList<Element> edgeList= gi.getEdgeList();

        for (Element e:edgeList) {
            Element g;
            Element firstChild=(Element)e.getChildNodes().item(1);
            System.out.println(base.getAttribute("id") +  " ? " + firstChild.getTextContent() + "\n");
            if (base.getAttribute("id").equals(firstChild.getTextContent())) { //!!!!!!!!!!!!!!!!!!ide lehet hogy == kell
                g=(Element)e.getChildNodes().item(3);

                neighborList.add(gi.getNodeMap().get(Integer.parseInt(g.getTextContent())));
            }
        }

        return neighborList;
    }

    public LinkedList<Element> reconstructPath(HashMap cameFrom, Element current) {

        Element actual=current;

        LinkedList<Element> path=new LinkedList<Element>();
        LinkedList<Element> path_normal=new LinkedList<Element>();


        path.add(actual);

        while (cameFrom.containsKey(actual)) {
            actual= (Element) cameFrom.get(actual);
            path.add(actual);
        }

        for(int i=path.size()-2;i>=0;i--) {  //megforditom a path-t, hogyha foreach-csel bejárom, akkor az elejétől a végéig menjek
            path_normal.add(path.get(i));
        }

        return path_normal;

    }


    public HashMap<Element, Double> getfScore() {
        return fScore;
    }



	@Override
	public void preprocess(Graph graph) {
		// TODO Auto-generated method stub

	}

	@Override
	public DistanceResult findShortestPath(int startNodeId,
			int destinationNodeId) {
		// TODO Auto-generated method stub

        DistanceResultImpl dri=new DistanceResultImpl(startNodeId,destinationNodeId);


		return dri;
	}

	@Override
	public TimeResult findFastestPath(int startNodeId, int destinationNodeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasPath(int startNodeId, int destinationNodeId) {
		// TODO Auto-generated method stub

		return false;
	}

}
