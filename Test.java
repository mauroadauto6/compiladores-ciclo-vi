import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class Test {
    public static void main(String[] args) throws Exception {
        ANTLRInputStream input = new ANTLRInputStream(System.in);
        ExprLexer lexer = new ExprLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ExprParser parser = new ExprParser(tokens);

        // Inicia el análisis sintáctico desde la regla 'program'
        ParseTree tree = parser.program();

        // Crea un walker para recorrer el árbol sintáctico
        ParseTreeWalker walker = new ParseTreeWalker();

        // Crea un listener
        ParseTreeListener listener = new ExprBaseListener() {
            @Override
            public void exitStmt(ExprParser.StmtContext ctx) {
                System.out.println(ctx.getText());
            }
        };

        // Agrega el listener al walker
        walker.walk(listener, tree);
    }
}

