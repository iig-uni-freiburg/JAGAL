package de.uni.freiburg.iig.telematik.jagal.graph.visualization.flexible;

import de.invation.code.toval.graphic.component.DisplayFrame;
import de.uni.freiburg.iig.telematik.jagal.graph.Edge;
import de.uni.freiburg.iig.telematik.jagal.graph.Graph;
import de.uni.freiburg.iig.telematik.jagal.graph.Vertex;

public class GraphComponent<G extends Graph<T>, T extends Object> extends AbstractGraphComponent<G, Vertex<T>, Edge<Vertex<T>>, T> {

	private static final long serialVersionUID = 7114630729271184459L;

	public GraphComponent(G graph) throws Exception {
		super(graph);
	}

	public static void main(String[] args) throws Exception {
		Graph<Object> g = new Graph<Object>();
		g.addVertex("v1");
		g.addVertex("v2");
		g.addEdge("v1", "v2");
		new DisplayFrame(new GraphComponent(g), true);
	}

}
