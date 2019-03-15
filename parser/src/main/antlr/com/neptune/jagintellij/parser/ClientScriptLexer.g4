lexer grammar ClientScriptLexer;

@members {
private java.util.Set<String> types = new java.util.HashSet<String>() {{
    add("int");
    add("string");
}};
private java.util.Set<String> defTypes = new java.util.HashSet<String>() {{
    add("def_int");
    add("def_string");
}};
private java.util.Set<String> switchTypes = new java.util.HashSet<String>() {{
    add("switch_int");
}};

public ClientScriptLexer(CharStream input, java.util.Set<String> types) {
    this(input);
    this.types = types;

    for (String type : types) {
        defTypes.add("def_" + type);

        if (!type.equals("string")) {
            switchTypes.add("switch_" + type);
        }
    }
}
}

tokens {
    TYPE,
    DEF_TYPE,
    SWITCH_TYPE
}

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
FOR : 'for' ;
SWITCH : 'switch' ;
CASE : 'case' ;
DEFAULT : 'default' ;
CALC : 'calc' ;
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
BITWISE_OR : '|' ;
OR : '||' ;
BITWISE_AND : '&' ;
AND : '&&' ;
UNDERSCORE : '_' ;
TILDE : '~';
PERIOD : '.' ;

LINE_COMMENT        : '//' .*? ('\n'|EOF)	-> channel(HIDDEN) ;
BLOCK_COMMENT       : '/*' .*? '*/' -> channel(HIDDEN) ;

SCRIPT_DECLARATION  : '[' SCRIPT_TYPE ',' ID ']' ;
SCRIPT_TYPE         : 'clientscript' | 'proc' ;
INSTRUCTION         : 'instruction' ;

LOCAL_VAR           : '$' ID ;
CONSTANT_VAR        : '^' ID ;
GAME_VAR            : '%' ID ;

// base var types
TYPEINT             : 'int' ;
DEF_INT             : 'def_int' ;
TYPESTRING          : 'string' ;
DEF_STRING          : 'def_string' ;

ID                  : [a-zA-Z_] [a-zA-Z0-9_]*
                    {
                        if(types.contains(getText())) setType(TYPE);
                        if(defTypes.contains(getText())) setType(DEF_TYPE);
                        if(switchTypes.contains(getText())) setType(SWITCH_TYPE);
                    }
                    ;

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
