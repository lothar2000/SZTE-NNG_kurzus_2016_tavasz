package navigation;

import org.w3c.dom.Element;



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









	public LinkedList<Element> aStar(Element start, Element goal) {

        Element current;

        Comparator<Element> comparator=new FScoreComparator();

        PriorityQueue<Element> priorityQueueFScore = new PriorityQueue<Element>(100, comparator);  //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

		while(!priorityQueueFScore.isEmpty()) {
            current=priorityQueueFScore.poll();

            if(current.equals(goal)) {  //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!lehet hogy ==
                return reconstructPath(cameFrom,current);
            }

            priorityQueueFScore.remove(current);

            closedSet.add(current);

            if(current.equals(start)) {  //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!lehet hogy ==
                cameFrom.put(current, null); //nemtom már lehet e bele null-t rakni
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
                gScore.put(neighbor,tentativeGScore.get(neighbor));


                tentativeGScore.remove(neighbor);

                if (fScore.containsKey(neighbor)) {
                    fScore.remove(neighbor);
                }
                fScore.put(neighbor,airDistance(neighbor,goal));

                priorityQueueFScore.add(neighbor);

            }

		}

        return null;

	}


    /*public double heuristic_cost_estimate(Element A, Element B) {

    }*/


    public double airDistance(Element A, Element B) {
        double xA = Double.parseDouble(A.getLastChild().getTextContent()); //x coordinate of A element
        double yA = Double.parseDouble(A.getFirstChild().getTextContent()); //y coordinate of A element
        double xB = Double.parseDouble(B.getLastChild().getTextContent()); //x coordinate of B element
        double yB = Double.parseDouble(B.getFirstChild().getTextContent()); //y coordinate of B// element
        return sqrt(pow(xB - xA, 2.0) + pow(yB - yA, 2.0));
    }

	public int gCalculator(Element current) {
		//TODO
		return 0;
	}

	public int fCalculator(Element current) {
		//TODO
		return 0;
	}

    public LinkedList<Element> neighbors(Element base) { //az olyan élek endNode-jait tárolom el, amelyek startNode-ja a base
        //tehát ahová közvetlenük el lehet jutni a base-ből, ez a List TELJESEN RENDEZETLEN!!!!!
        LinkedList<Element> neighborList=new LinkedList<Element>();
        LinkedList<Element> edgeList= gi.getEdgeList();
        LinkedList<Element> nodeList= gi.getNodeList();

        for (Element e:edgeList) {
            Element firstChild = (Element) e.getFirstChild();
            if (base.getAttribute("id").equals(firstChild.getTextContent())) { //!!!!!!!!!!!!!!!!!!ide lehet hogy == kell
                ((LinkedList<Element>) e.getChildNodes()).stream().filter(f -> f.getAttribute("name").equals("endNode")).forEachOrdered(f -> { // !!!!!!!!!!!!!!!!!!!!!!!!!!!
                    for (Element g : nodeList) {
                        if (f.getTextContent().equals(g.getAttribute("id"))) {
                            neighborList.add(g);
                        }
                    }
                });
            }

        }

        return neighborList;
    }

    public LinkedList<Element> reconstructPath(HashMap cameFrom, Element current) {

        Element actual=current;

        LinkedList<Element> path=new LinkedList<Element>();

        path.add(current);

        while (cameFrom.containsKey(actual)) {
            actual= (Element) cameFrom.get(actual);
            path.add(actual);
        }

        return path;

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
