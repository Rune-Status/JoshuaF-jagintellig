parser grammar ClientScriptParser;

@members {
boolean inCalc = false;
boolean inCallExpr = false;

boolean inCondition = false;

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
    |   ifStatement
    |   switchStatement
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
    :   defType assignableIdentifier EQUAL expr ';'
    ;

// TODO multiple assignments
assignmentStatement
    :   assignableIdentifier EQUAL expr ';'         # SingleAssignmentStatement
    |   assignableIdentifierList EQUAL exprList ';' # MultiAssignmentStatement
    ;

ifStatement
    :   IF {inCondition=true;} '(' expr ')' {inCondition=false;} statement (ELSE statement)?
    ;

switchStatement
    :   SWITCH parenthesis '{' switchBlock* '}'
    ;

switchBlock
    :   (CASE (constant | DEFAULT)+ ':')+ statement?
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
    :   op=('+' | '-') expr                                         # UnaryExpression
    |   expr {!requireCalc || inCalc}? op=('*' | '/' | '%') expr    # MultiplicativeExpression
    |   expr {!requireCalc || inCalc}? op=('+' | '-') expr          # AdditiveExpression
    |   expr {inCondition}? op=('<' | '>' | '>=' | '<=') expr       # RelationalExpression
    |   expr {inCondition}? op=('==' | '!=') expr                   # EqualityExpression
    |   expr {!requireCalc || inCalc}? '&' expr                     # BitwiseAndExpression
    |   expr {!requireCalc || inCalc}? '|' expr                     # BitwiseOrExpression
    |   expr {inCondition}? '||' expr                               # OrExpression
    |   expr {inCondition}? '&&' expr                               # AndExpression
    |   {inCallExpr}? type                                          # TypeExpression
    |   assignableIdentifier                                       # AssignableExpression
    |   constant                                                    # ScalarExpression
    |   parenthesis                                                 # ParenthesisExpression
    |   callExpr                                                    # CallExpression
    ;

callExpr
    :   {!inCalc}? name=CALC {inCalc=true;} '(' exprList ')' {inCalc=false;}            # SpecialWordExpression
    |   identifier=(ID | TYPE) '(' {inCallExpr=true;} exprList {inCallExpr=false;} ')'  # NormalCallExpression
    |   '~' ID '(' {inCallExpr=true;} exprList {inCallExpr=false;} ')'                  # ProcCallExpression
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

assignableIdentifier
    :   LOCAL_VAR
    |   GAME_VAR
    ;

assignableIdentifierList
    :   assignableIdentifier (',' assignableIdentifier)*
    ;

// TODO load types differently?
defType
    :	DEF_TYPE
    ;

type
    :	TYPE
    ;
