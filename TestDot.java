import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestDot {
    public static void main(String[] args) throws Exception {
        // Lee el archivo .DOT
        String dotContent = readDotFile("input.dot");

        // Crea un InputStream para el archivo .DOT
        ANTLRInputStream input = new ANTLRInputStream(dotContent);
        ExprLexer lexer = new ExprLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ExprParser parser = new ExprParser(tokens);

        // Inicia el análisis sintáctico desde la regla 'program'
        ParseTree tree = parser.program();

        // Crea un walker para recorrer el árbol sintáctico
        ParseTreeWalker walker = new ParseTreeWalker();

        
        SyntaxTreeNode rootNode = new SyntaxTreeNode("Program");
	
	// Crea un listener
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
                    currentNode = currentNode.parent; // actualiza el nodo padre
                }
            }
        };

        
        walker.walk(listener, tree);

        // Lista de adyacencia
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

        // Recorre los hijos y genera la lista de adyacencia
        for (SyntaxTreeNode child : node.children) {
            generateAdjacencyList(child, adjacencyList);
        }
    }

    private static String readDotFile(String filePath) throws IOException {
        // Lee el archivo .DOT
        byte[] encoded = Files.readAllBytes(Paths.get(filePath));
        return new String(encoded, StandardCharsets.UTF_8);
    }
}
