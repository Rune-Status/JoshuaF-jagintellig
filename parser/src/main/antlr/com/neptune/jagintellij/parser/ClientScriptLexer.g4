lexer grammar ClientScriptLexer;

// Types
TYPEINT : 'int' ;
TYPECOORDGRID : 'coordgrid' ;
TYPESTRING : 'string' ;
TYPEBOOLEAN : 'boolean' ;

LPAREN : '(' ;
RPAREN : ')' ;
SEMICOLON : ';';
COLON : ':' ;
COMMA : ',' ;
LBRACK : '[' ;
RBRACK : ']' ;
LBRACE : '{' ;
RBRACE : '}' ;
IF : 'if' ;
ELSE : 'else' ;
WHILE : 'while' ;
CALC : 'calc' ;
ENUM : 'enum' ;
EQUAL : '=' ;
PERCENT : '%' ;
RETURN : 'return' ;
TRUE : 'true' ;
FALSE : 'false' ;
NULL : 'null' ;
INC: '++';
DEC: '--';
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
UNDERSCORE : '_' ;

LINE_COMMENT        : '//' .*? ('\n'|EOF)	-> channel(HIDDEN) ;
COMMENT             : '/*' .*? '*/' -> channel(HIDDEN) ;

SCRIPT_DECLARATION  : '[' SCRIPT_TYPE ',' ID ']' ;
SCRIPT_TYPE         : 'clientscript' | 'proc' ;

DEF_TYPE            : 'def_' (TYPEINT | TYPECOORDGRID | TYPESTRING | TYPEBOOLEAN) ;

LOCAL_VAR           : '$' ID ;
CONSTANT_VAR        : '^' ID ;
GAME_VAR            : '%' ID ;

ID                  : [a-zA-Z_] [a-zA-Z0-9_]* ;

INT                 : [0-9]+ ;
HEX                 : '0x' HexDigit+ ;
COORD_GRID          : INT '_' INT '_' INT '_' INT '_' INT ;
fragment HexDigit:             [a-fA-F0-9];

STRING              :  '"' (ESC | ~["\\])* '"' ;
fragment ESC        :   '\\' ["\bfnrt] ;

WS                  : [ \t\n\r]+ -> channel(HIDDEN) ;

/** "catch all" rule for any char not matche in a token rule of your
 *  grammar. Lexers in Intellij must return all tokens good and bad.
 *  There must be a token to cover all characters, which makes sense, for
 *  an IDE. The parser however should not see these bad tokens because
 *  it just confuses the issue. Hence, the hidden channel.
 */
ERRCHAR
	:	.	-> channel(HIDDEN)
	;
