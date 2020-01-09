import edu.uci.ics.jung.algorithms.cluster.WeakComponentClusterer;
import edu.uci.ics.jung.algorithms.generators.random.EppsteinPowerLawGenerator;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.io.PajekNetReader;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

import org.apache.commons.collections15.Factory;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Exercise {
	static Factory edgeFactory = new Factory<String>() {
		int i;

		public String create() {
			return new Integer(i++).toString();
		}
	};
	static Factory vertexFactory = new Factory<Integer>() {
		int i;

		public Integer create() {
			return new Integer(i++);
		}
	};
	static Factory<Graph> graphFactory = new Factory<Graph>() {
		public Graph create() {
			return new UndirectedSparseGraph();
		}
	};

	private static Graph Eppstain(int numVertices, int numEdges, int r) {
		EppsteinPowerLawGenerator gn = new EppsteinPowerLawGenerator(graphFactory, vertexFactory, edgeFactory,
				numVertices, numEdges, r);

		return gn.create();
	}

	public static void main(String[] argc) {

		final Graph<Integer, String> g = Eppstain(8, 6, 20);
		Layout<Integer, String> layout = new CircleLayout(g);
		layout.setSize(new Dimension(500, 500));
		BasicVisualizationServer<Integer, String> vv = new BasicVisualizationServer<Integer, String>(layout);
		vv.setPreferredSize(new Dimension(500, 500)); // Sets the viewing area size
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());

		JButton button = new JButton("Поиск подграфов");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				WeakComponentClusterer<Integer, String> wcc = new WeakComponentClusterer<>();
				Set<Set<Integer>> components = wcc.transform(g);
				JPanel panel = new JPanel();
				JLabel label = new JLabel("Связные подграфы - " + " " + components);
				String message = new String();
				message += "Связные подграфы - " + " " + components;
				JOptionPane.showMessageDialog(null, message, "Output", JOptionPane.PLAIN_MESSAGE);
			}

		});

		JPanel panel = new JPanel();
		panel.add(vv);
		panel.add(button);
		JFrame frame = new JFrame("Simple Graph View");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
	}
}
