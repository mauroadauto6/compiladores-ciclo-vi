grammar Expr;

// Lexer rules
NODE: 'node';
EDGE: 'edge';
COMMA: ',';
SUBGRAPH: 'subgraph';
ARROW: '->';
DOUBLE: '--';
COMPASS_PT: ('n' | 'ne' | 'e' | 'se' | 's' | 'sw' | 'w' | 'nw' | 'c' | '_');

GRAPH: ('strict' )? ('graph' | 'digraph');
ID: [a-zA-Z_][a-zA-Z_0-9]*;
WS: [ \t\n\r\f]+ -> skip ;
LBRACE: '{';
RBRACE: '}';
SEMICOLON: ';';
EQUALS: '=';
COLON: ':';
NEWLINE : [\r\n]+ ;

// Parser rules
program
    : (graph)* EOF
    ;

graph: GRAPH ID? LBRACE stmt_list RBRACE;

stmt_list: (stmt ((SEMICOLON) stmt)*)?;

stmt: node_stmt
    | edge_stmt
    | attr_stmt
    | ID EQUALS ID
    | subgraph
    ;

attr_stmt: (GRAPH | NODE | EDGE) attr_list;

attr_list: LBRACE (a_list)? RBRACE (attr_list)?;

a_list: ID EQUALS ID (SEMICOLON | COMMA)? (a_list)?;

edge_stmt: (node_id | subgraph) edgeRHS attr_list?;

edgeRHS: (ARROW | DOUBLE) (node_id | subgraph) (edgeRHS)?;

node_stmt: node_id attr_list?;

node_id: ID (port)?;

port: COLON ID (COLON compass_pt)?;

subgraph: SUBGRAPH ID? LBRACE stmt_list RBRACE;

compass_pt: COMPASS_PT;
