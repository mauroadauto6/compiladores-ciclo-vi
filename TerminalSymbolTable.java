import java.util.HashMap;
import java.util.Map;

public class TerminalSymbolTable {
    private Map<String, String> symbolTable;

    public TerminalSymbolTable() {
        symbolTable = new HashMap<>();
        initializeSymbolTable();
    }

    private void initializeSymbolTable() {
        // Tokens Terminales
        symbolTable.put("NODE", "Representa la palabra clave 'node'.");
        symbolTable.put("EDGE", "Representa la palabra clave 'edge'.");
        symbolTable.put("COMMA", "Representa la coma utilizada para separar elementos.");
        symbolTable.put("SUBGRAPH", "Representa la palabra clave 'subgraph'.");
        symbolTable.put("ARROW", "Representa la flecha '->' utilizada en declaraciones de aristas.");
        symbolTable.put("COMPASS_PT", "Representa los puntos cardinales utilizados para direcciones.");
        symbolTable.put("GRAPH", "Representa la palabra clave 'graph' o 'digraph'.");
        symbolTable.put("ID", "Representa un identificador (nombres de nodos, atributos, etc.).");
        symbolTable.put("WS", "Representa espacios en blanco (ignorados).");
        symbolTable.put("LBRACE", "Representa una llave de apertura.");
        symbolTable.put("RBRACE", "Representa una llave de cierre.");
        symbolTable.put("SEMICOLON", "Representa un punto y coma utilizado para separar declaraciones.");
        symbolTable.put("EQUALS", "Representa un signo igual utilizado en asignaciones.");
        symbolTable.put("COLON", "Representa dos puntos utilizados en declaraciones de puertos.");
        symbolTable.put("NEWLINE", "Representa un carácter de nueva línea (ignorado).");

        // Tokens No Terminales
        symbolTable.put("program", "Regla principal del programa.");
        symbolTable.put("graph", "Declaración de un grafo.");
        symbolTable.put("stmt_list", "Lista de declaraciones de stmt.");
        symbolTable.put("stmt", "Declaración de nodo, arista, atributo o subgrafo.");
        symbolTable.put("attr_stmt", "Declaración de atributos para grafo, nodo o arista.");
        symbolTable.put("attr_list", "Lista de atributos.");
        symbolTable.put("a_list", "Lista de asignaciones de atributos.");
        symbolTable.put("edge_stmt", "Declaración de arista.");
        symbolTable.put("edgeRHS", "Lado derecho de una declaración de arista.");
        symbolTable.put("node_stmt", "Declaración de nodo.");
        symbolTable.put("node_id", "Identificador de nodo.");
        symbolTable.put("port", "Puerto utilizado en nodos.");
        symbolTable.put("subgraph", "Declaración de subgrafo.");
        symbolTable.put("compass_pt", "Punto cardinal utilizado en puertos.");
    }

    public String getDescription(String token) {
        return symbolTable.get(token);
    }

    public void printSymbolTable() {
        System.out.println("Tabla de Símbolos de Tokens Terminales y No Terminales:");
        for (Map.Entry<String, String> entry : symbolTable.entrySet()) {
            String token = entry.getKey();
            String description = entry.getValue();
            System.out.printf("%-12s : %s%n", token, description);
        }
    }

    public static void main(String[] args) {
        TerminalSymbolTable terminalSymbolTable = new TerminalSymbolTable();
        terminalSymbolTable.printSymbolTable();
        String nonTerminalToken = "program";
        String description = terminalSymbolTable.getDescription(nonTerminalToken);
        System.out.printf("Descripción de '%s': %s%n", nonTerminalToken, description);
    }
}
