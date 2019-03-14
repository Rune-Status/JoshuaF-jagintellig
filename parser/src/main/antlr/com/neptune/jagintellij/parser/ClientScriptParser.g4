parser grammar ClientScriptParser;

@members {
boolean inCalc = false;
boolean inCallExpr = false;

boolean requireCalc = false;

public void setRequireCalc(boolean requireCalc) {
    this.requireCalc = requireCalc;
}
}

@lexer::header {package com.neptune.jagintellij.parser;}

options { tokenVocab = ClientScriptLexer; }

file
    :   script*
    ;

script
    :   scriptDeclaration statement*
    ;

scriptDeclaration
    :   SCRIPT_DECLARATION formalArgs? formalReturns?
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
    |   declarationStatement
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

declarationStatement
    :   defType assignableIdentifiers EQUAL expr ';'
    ;

// TODO multiple assignments
assignmentStatement
    :   assignableIdentifiers EQUAL expr ';'  # SingleAssignmentStatement
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
expr
    :   expr op=('++' | '--')                                       # PostfixExpression
    |   op=('+' | '-') expr                                         # UnaryExpression
    |   expr {!requireCalc || inCalc}? op=('*' | '/' | '%') expr    # MultiplicativeExpression
    |   {inCallExpr}? type                                          # TypeExpression
    |   constant                                                    # ScalarExpression
    |   parenthesis                                                 # ParenthesisExpression
    |   callExpr                                                    # CallExpression
    ;

callExpr
    :   {!inCalc}? name=CALC {inCalc=true;} '(' exprList ')' {inCalc=false;}            # SpecialWordExpression
    |   identifier=(ID | TYPE) '(' {inCallExpr=true;} exprList {inCallExpr=false;} ')'  # NormalCallExpression
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
    :   COORD_GRID      # CoordGridNumericConstant
    |   INT             # NormalNumericConstant
    |   HEX             # HexNumericConstant
    |   INT ':' INT     # ComponentIntNumericConstant // TODO move somewhere else?
    |   ID  ':' ID      # ComponentIdNumericConstant  // TODO move somewhere else?
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
    :	TYPE
    ;
