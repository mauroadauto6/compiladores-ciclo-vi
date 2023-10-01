import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.*;

// clase para los nodos del árbol
class SyntaxTreeNode {
    String value;
    List<SyntaxTreeNode> children;
    SyntaxTreeNode parent; // nodo padre

    public SyntaxTreeNode(String value) {
        this.value = value;
        this.children = new ArrayList<>();
        this.parent = null; 
    }
}

public class Testin {
    public static void main(String[] args) throws Exception {
        ANTLRInputStream input = new ANTLRInputStream(System.in);
        ExprLexer lexer = new ExprLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ExprParser parser = new ExprParser(tokens);

        // Inicia el análisis sintáctico desde 'program'
        ParseTree tree = parser.program();

        // Crea un walker para recorrer el árbol sintáctico
        ParseTreeWalker walker = new ParseTreeWalker();

        // Define un listener 
        SyntaxTreeNode rootNode = new SyntaxTreeNode("Program");

        ParseTreeListener listener = new ExprBaseListener() {
            SyntaxTreeNode currentNode = rootNode;

            @Override
            public void exitStmt(ExprParser.StmtContext ctx) {
                SyntaxTreeNode newNode = new SyntaxTreeNode(ctx.getText());
                newNode.parent = currentNode; // nodo padre
                currentNode.children.add(newNode);
                currentNode = newNode;
            }

            @Override
            public void exitEveryRule(ParserRuleContext ctx) {
                if (currentNode != rootNode) {
                    currentNode = currentNode.parent; // Actualizamos el nodo padre
                }
            }
        };

        walker.walk(listener, tree);

        // Genera la lista de adyacencia 
        Map<String, List<String>> adjacencyList = new HashMap<>();
        generateAdjacencyList(rootNode, adjacencyList);

        // Imprime la lista de adyacencia 
        for (Map.Entry<String, List<String>> entry : adjacencyList.entrySet()) {
            String node = entry.getKey();
            List<String> neighbors = entry.getValue();
            System.out.print(node + ": ");
            for (int i = 0; i < neighbors.size(); i++) {
                System.out.print(neighbors.get(i));
                if (i < neighbors.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }

    // Función para generar la lista de adyacencia
    private static void generateAdjacencyList(SyntaxTreeNode node, Map<String, List<String>> adjacencyList) {
        if (node.value.contains("->")) {
            String[] nodes = node.value.split("->");
            if (nodes.length == 2) {
                String sourceNode = nodes[0].trim();
                String targetNode = nodes[1].trim();
                adjacencyList.computeIfAbsent(sourceNode, k -> new ArrayList<>()).add(targetNode);
            }
        }

        // Recorre los hijos y construye la lista de adyacencia
        for (SyntaxTreeNode child : node.children) {
            generateAdjacencyList(child, adjacencyList);
        }
    }
}
