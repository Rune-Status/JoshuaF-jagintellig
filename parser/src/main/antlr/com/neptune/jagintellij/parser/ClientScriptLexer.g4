lexer grammar ClientScriptLexer;

// Types
TYPEINT : 'int' ;
TYPEFLOAT : 'float' ;
TYPESTRING : 'string' ;
TYPEBOOLEAN : 'boolean' ;

LPAREN : '(' ;
RPAREN : ')' ;
COLON : ':' ;
COMMA : ',' ;
LBRACK : '[' ;
RBRACK : ']' ;
LBRACE : '{' ;
RBRACE : '}' ;
IF : 'if' ;
ELSE : 'else' ;
WHILE : 'while' ;
VAR : 'var' ;
EQUAL : '=' ;
RETURN : 'return' ;
PRINT : 'print' ;
FUNC : 'func' ;
TRUE : 'true' ;
FALSE : 'false' ;
SUB : '-' ;
BANG : '!' ;
MUL : '*' ;
DIV : '/' ;
ADD : '+' ;
LT : '<' ;
LE : '<=' ;
EQUAL_EQUAL : '==' ;
NOT_EQUAL : '!=' ;
GT : '>' ;
GE : '>=' ;
OR : '||' ;
AND : '&&' ;
DOT : ' . ' ;

LINE_COMMENT        : '//' .*? ('\n'|EOF)	-> channel(HIDDEN) ;
COMMENT             : '/*' .*? '*/' -> channel(HIDDEN) ;

SCRIPT_DECLARATION  : '[' SCRIPT_TYPE ',' ID ']' ;
SCRIPT_TYPE         : 'clientscript' | 'proc' ;

LOCAL_VAR           : '$' ID ;
CONSTANT_VAR        : '^' ID ;
GAME_VAR            : '%' ID ;

ID                  : [a-zA-Z_] [a-zA-Z0-9_]* ;
COORD_GRID          : INT '_' INT '_' INT '_' INT '_' INT ;
INT                 : [0-9]+ ;

STRING              :  '"' (ESC | ~["\\])* '"' ;
fragment ESC        :   '\\' ["\bfnrt] ;

WS                  : [ \t\n\r]+ -> channel(HIDDEN) ;

/** "catch all" rule for any char not matche in a token rule of your
 *  grammar. Lexers in Intellij must return all tokens good and bad.
 *  There must be a token to cover all characters, which makes sense, for
 *  an IDE. The parser however should not see these bad tokens because
 *  it just confuses the issue. Hence, the hidden channel.
 */
//ERRCHAR
//	:	.	-> channel(HIDDEN)
//	;
