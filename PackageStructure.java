

import java.util.ArrayList;
import java.util.List;

public class PackageStructure {
	private String packageName;
	private List<Node> nodes;
	private List<Edge> edges;
	
	public PackageStructure() {
		nodes = new ArrayList<Node>(0);
		edges = new ArrayList<Edge>(0);
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	
	public Node getNodeByName(String name) {
		for (Node node : nodes) {
			if(node.getTypeName().equals(name)){
				return node;
			}
		}
		return null;
	}
	
	
	public Edge getEdge(String source, String destination, EdgeType edgeType){
		for (Edge edge : edges) {
			if(edge.getSource().getTypeName().equals(source) &&
					(edge.getDestination().getTypeName().equals(destination)) &&
					edge.getEdgeType().equals(edgeType)){
				return edge;
			}
		}
		return null;
	}
}
