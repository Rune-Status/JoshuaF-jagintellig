parser grammar ClientScriptParser;

@members {
boolean inCalc = false;
}

@lexer::header {package com.neptune.jagintellij.parser;}

options { tokenVocab = ClientScriptLexer; }

file
    :   script*
    ;

script
    :   SCRIPT_DECLARATION formalArgs? formalReturns? statement*
    ;

formalArgs
    :   '(' formalArg (',' formalArg)* ')'
    ;

formalArg
    :   type LOCAL_VAR
    ;

formalReturns
    :   '(' type (',' type)* ')'
    ;

// Statements
statement
    :   blockStatement
    |   returnStatement
    |   assignmentStatement
    |   expressionStatement
    |   emptyStatement
    ;

blockStatement
    :   '{' statement* '}'
    ;

emptyStatement
    :   ';'
    ;

returnStatement
    :   RETURN ('(' exprList ')') ';'
    ;

assignmentStatement
    :   defType assignableIdentifiers EQUAL expr ';'
    ;

expressionStatement
    :   expr ';'
    ;

exprList
    :   expr (',' expr)*
    ;

parenthesis
    :   '(' expr ')'
    ;

// Expressions
// Followings Java's operator precedence: https://docs.oracle.com/javase/tutorial/java/nutsandbolts/operators.html
// TODO multi assignment
expr
    :   expr op=('++' | '--')           # PostfixExpression
    |   op=('+' | '-') expr             # UnaryExpression
    |   expr op=('*' | '/' | '%') expr  # MultiplicativeExpression
    |   constant                        # ScalarExpression
    |   parenthesis                     # ParenthesisExpression
    |   callExpr                        # CallExpression
    ;

callExpr
    :   {!inCalc}? name=CALC {inCalc=true;} '(' exprList ')' {inCalc=false;}    # SpecialWordExpression
    |   name=ENUM '(' inputType=type ',' outputType=type ',' expr ',' expr ')'  # SpecialWordExpression
    |   ID ('(' exprList ')')?                                                  # NormalCallExpression
    ;

constant
    :   NULL
    |   CONSTANT_VAR
    |   literalConstant
    ;

literalConstant
    :   booleanConstant
    |   numericConstant
    |   stringConstant
    ;

booleanConstant
    :   TRUE
    |   FALSE
    ;

numericConstant
    :   INT         # NormalNumericConstant
    |   HEX         # HexNumericConstant
    |   COORD_GRID  # CoordGridNumericConstant
    ;

stringConstant
    :   STRING
    ;

assignableIdentifiers
    :   LOCAL_VAR
    |   GAME_VAR
    ;

// TODO load types differently?
defType
    :	DEF_TYPE
    ;

type
    :	TYPEINT
    |   TYPECOORDGRID
    |   TYPESTRING
    |   TYPEBOOLEAN
    ;

