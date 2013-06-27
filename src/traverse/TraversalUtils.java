package traverse;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

import de.invation.code.toval.validate.ParameterException;
import de.invation.code.toval.validate.Validate;
import de.uni.freiburg.iig.telematik.jagal.graph.Vertex;
import de.uni.freiburg.iig.telematik.jagal.graph.abstr.AbstractGraph;
import de.uni.freiburg.iig.telematik.jagal.graph.exception.VertexNotFoundException;

import traverse.Traverser.TraversalMode;
import traverse.algorithms.SCCTarjan;

public class TraversalUtils {
	
	private static ArrayBlockingQueue<Object> queue = new ArrayBlockingQueue<Object>(10);
	private static Set<Object> visited = new HashSet<Object>();
	
	public static <V extends Object> boolean isStronglyConnected(Traversable<V> traversableStructure, V node){
		int visitedNodes = 0;
		Iterator<V> iter = new Traverser<V>(traversableStructure, node, TraversalMode.DEPTHFIRST);
		while(iter.hasNext()){
			iter.next();
			visitedNodes++;
		}
		return visitedNodes == traversableStructure.nodeCount();
	}
	
	
	public static <V extends Object> Set<Set<V>> getStronglyConnectedComponents(Traversable<V> traversableStructure) throws ParameterException{
		SCCTarjan<V> tarjan = new SCCTarjan<V>();
		return tarjan.execute(traversableStructure);
	}
	
	public static <V extends Object> Set<V> getSiblings(Traversable<V> traversableStructure, V node) throws VertexNotFoundException, ParameterException{
		Validate.notNull(traversableStructure);
		Set<V> result = new HashSet<V>();
		for(V parent: traversableStructure.getParents(node)){
			result.addAll(traversableStructure.getChildren(parent));
		}
		result.remove(node);
		return result;
	}
	
//	public static <V extends Vertex<U>, U> boolean isPredecessor(AbstractGraph<V, ?, U> graph, V queryVertex, V baseVertex) throws VertexNotFoundException
	
	/**
	 * Checks, if {@link queryNode} is a predecessor of {@link baseNode}.
	 * @param baseNode Basic Node for predecessor search
	 * @param queryNode Query Node for predecessor search
	 * @return <code>true</code> if {@link queryNode} is a predecessor of {@link baseNode};
     *		<code>false</code> otherwise.
	 * @throws ParameterException 
	 */
	@SuppressWarnings("unchecked")
	public static <V extends Object> boolean isPredecessor(Traversable<V> traversableStructure, V queryNode, V baseNode) throws VertexNotFoundException, ParameterException{
		Validate.notNull(traversableStructure);
		visited.clear();
		visited.add(baseNode);
		queue.clear();
		for(V parent: traversableStructure.getParents(baseNode)){
			queue.offer(parent);
		}
		while(!queue.isEmpty()){
			if(queryNode.equals(queue.peek())){
				return true;
			}
			//Cast is safe since only objects of type V were added to the queue before.
			for(V parent: traversableStructure.getParents((V) queue.peek())){
				if(!visited.contains(parent) && !queue.contains(parent)){
					queue.add(parent);
				}
			}
			visited.add(queue.poll());
		}
		return false;
	}
	
//	public static <V extends Vertex<U>, U> boolean isSuccessor(AbstractGraph<V, ?, U> graph, V queryVertex, V baseVertex) throws GraphException
	
	/**
	 * Checks, if {@link queryNode<T>} is a successor of {@link baseNode<T>}.
	 * @param baseNode<T> Basic Node<T> for successor search
	 * @param queryNode<T> Query Node<T> for successor search
	 * @return <code>true</code> if {@link queryNode<T>} is a successor of {@link baseNode<T>};
     *		<code>false</code> otherwise.
	 * @throws ParameterException 
	 */
	@SuppressWarnings("unchecked")
	public static <V extends Object> boolean isSuccessor(Traversable<V> traversableStructure, V queryNode, V baseNode) throws VertexNotFoundException, ParameterException{
		Validate.notNull(traversableStructure);
		visited.clear();
		visited.add(baseNode);
		queue.clear();
		for(V child: traversableStructure.getChildren(baseNode)){
			queue.offer(child);
		}
		while(!queue.isEmpty()){
			if(queryNode.equals(queue.peek())){
				return true;
			}
			//Cast is safe since only objects of type V were added to the queue before.
			for(V child: traversableStructure.getChildren((V) queue.peek())){
				if(!visited.contains(child) && !queue.contains(child)){
					queue.add(child);
				}
			}
			visited.add(queue.poll());
		}
		return false;
	}
	
//	public static <V extends Vertex<U>, U> Set<V> getPredecessorsFor(AbstractGraph<V, ?, U> graph, V vertex) throws VertexNotFoundException
	
	@SuppressWarnings("unchecked")
	public static <V extends Object> Set<V> getPredecessorsFor(Traversable<V> traversableStructure, V startNode) throws VertexNotFoundException, ParameterException{
		Validate.notNull(traversableStructure);
		Set<V> visited = new HashSet<V>();
		visited.add(startNode);
		queue.clear();
		for(V parent: traversableStructure.getParents(startNode)){
			queue.offer(parent);
		}
		while(!queue.isEmpty()){
			//Cast is safe since only objects of type V were added to the queue before.
			for(V parent: traversableStructure.getParents((V) queue.peek())){
				if(!visited.contains(parent) && !queue.contains(parent)){
					queue.add(parent);
				}
			}
			visited.add((V) queue.poll());
		}
		visited.remove(startNode);
		return visited;
	}

//	public static <V extends Vertex<U>, U> Set<V> getSuccessorsFor(AbstractGraph<V, ?, U> graph, V vertex) throws GraphException
	
	@SuppressWarnings("unchecked")
	public static <V extends Object> Set<V> getSuccessorsFor(Traversable<V> traversableStructure, V startNode) throws VertexNotFoundException, ParameterException{
		Validate.notNull(traversableStructure);
		Set<V> visited = new HashSet<V>();
		visited.add(startNode);
		queue.clear();
		for(V child: traversableStructure.getChildren(startNode)){
			queue.offer(child);
		}
		while(!queue.isEmpty()){
			//Cast is safe since only objects of type V were added to the queue before.
			for(V child: traversableStructure.getChildren((V) queue.peek())){
				if(!visited.contains(child) && !queue.contains(child)){
					queue.add(child);
				}
			}
			visited.add((V) queue.poll());
		}
		visited.remove(startNode);
		return visited;
	}
	
//	public static <V extends Vertex<U>, U> boolean isVertexInCycle(AbstractGraph<V, ?, U> graph, V vertex) throws VertexNotFoundException{
	
	/**
	 * Checks if a vertex is contained in a cycle.<br>
	 * In case the given vertex is in a cycle, it is a predecessor of itself.
	 * @param traversableStructure The graph that contains the vertex.
	 * @param node The vertex for which the property is checked.
	 * @return <code>true</code> if the given vertex is contained in a cycle;<br>
	 * <code>false</code>.
	 * @throws VertexNotFoundException If the graph does not contain the vertex.
	 * @throws ParameterException 
	 * @see {@link #isPredecessor(AbstractGraph, Vertex, Vertex)}
	 */
	public static <V extends Object> boolean isNodeInCycle(Traversable<V> traversableStructure, V node) throws VertexNotFoundException, ParameterException{
		Validate.notNull(traversableStructure);
		return isPredecessor(traversableStructure, node, node);
	}
	
	
//	public static <V extends Vertex<U>, U> ArrayBlockingQueue<ArrayList<V>> getDirectedPathsFor(AbstractGraph<V, ?, U> graph, V sourceVertex, V targetVertex) throws VertexNotFoundException{
	
	/**
	 * Returns a list of paths that lead from sourceVertex to targetVertex.
	 * @param sourceNode The source vertex for the desired paths.
	 * @param targetNode The target vertex for the desired paths.
	 * @return A list of all paths leading from sourceVertex to targetVertex.
	 * @throws VertexNotFoundException If the graph does not contain the given vertexes.
	 * @throws ParameterException 
	 */
	@SuppressWarnings("unchecked")
	public static <V extends Object> ArrayBlockingQueue<List<V>> getDirectedPathsFor(Traversable<V> traversableStructure, V sourceNode, V targetNode) throws VertexNotFoundException, ParameterException{
		Validate.notNull(traversableStructure);
		ArrayList<V> sourceVertexes = new ArrayList<V>();
		sourceVertexes.add(sourceNode);
		return getDirectedPathsFor(traversableStructure, Arrays.asList(sourceNode), targetNode);
	}
	
	
//	public static <V extends Vertex<U>, U> ArrayBlockingQueue<List<V>> getDirectedPathsFor(AbstractGraph<V, ?, U> graph, List<V> sourceVertexes, V targetVertex) throws VertexNotFoundException{
	
	/**
	 * Returns a list of paths leading from one source vertex to targetVertex.
	 * @param traversableStructure The graph that contains the vertexes.
	 * @param sourceVertex The source vertexes for the desired paths.
	 * @param targetNode The target vertex for the desired paths.
	 * @return A list of all paths leading from sourceVertex to targetVertex.
	 * @throws VertexNotFoundException If the graph does not contain the given vertexes.
	 * @throws ParameterException 
	 */
	public static <V extends Object> ArrayBlockingQueue<List<V>> getDirectedPathsFor(Traversable<V> traversableStructure, List<V> sourceNodes, V targetNode) throws VertexNotFoundException, ParameterException{
		Validate.notNull(traversableStructure);
		ArrayBlockingQueue<List<V>> finalPaths = new ArrayBlockingQueue<List<V>>(10);

		if(sourceNodes.contains(targetNode))
			return finalPaths;
		
		ArrayBlockingQueue<List<V>> tempPaths = new ArrayBlockingQueue<List<V>>(10);
		ArrayList<V> firstPath = new ArrayList<V>();
		firstPath.add(targetNode);
		tempPaths.offer(firstPath);
		
		while(!tempPaths.isEmpty()){
			Set<V> parents = traversableStructure.getParents(tempPaths.peek().get(tempPaths.peek().size()-1));
			for(V v: parents){
				ArrayList<V> newPath = new ArrayList<V>(tempPaths.peek());
				if(!tempPaths.peek().contains(v)){
					newPath.add(v);
					if(sourceNodes.contains(v)){
						finalPaths.add(newPath);
					} else {
						tempPaths.offer(newPath);
					}
				}
			}
			tempPaths.poll();
		}
		for(List<V> l: finalPaths){
			Collections.reverse(l);
		}
		return finalPaths;
	}

}
