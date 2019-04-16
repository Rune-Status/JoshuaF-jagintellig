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
    |   instruction*
    ;

script
    :   '[' trigger=identifier ',' name=identifier ']' formalArgs? formalReturns? statement*
    ;

instruction
    :   '[' INSTRUCTION ',' id=INT ',' name=identifier ']' formalArgs? formalReturns?
    ;

formalArgs
    :   '(' formalArg (',' formalArg)* ')'
    ;

formalArg
    :   type var
    ;

formalReturns
    :   '(' type (',' type)* ')'
    ;

// Statements
statement
    :   blockStatement
    |   returnStatement
    |   ifStatement
    |   whileStatement
    |   forStatement
    |   switchStatement
    |   declarationStatement ';' // ';' because it's used in for loop which manually specified it
    |   assignmentStatement ';' // same as above
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
    :   defType var (EQUAL expr)?   # NormalDeclarationStatement
    |   defType var '(' expr ')'    # ArrayDeclarationStatement
    ;

assignmentStatement
    :   var EQUAL expr          # SingleAssignmentStatement
    |   varList EQUAL exprList  # MultiAssignmentStatement
    ;

ifStatement
    :   IF {inCondition=true;} parenthesis {inCondition=false;} statement (ELSE statement)?
    ;

whileStatement
    :   WHILE {inCondition=true;} parenthesis {inCondition=false;} statement
    ;

forStatement
    :   FOR '(' forControl ')' statement
    ;

forControl
    :   initializer=forInit ';'
        {inCondition=true;} condition=expr {inCondition=false;} ';'
        incrementer=assignmentStatement
    ;

forInit
    :   declarationStatement
    |   assignmentStatement
    ;

switchStatement
    :   (SWITCH | SWITCH_TYPE) parenthesis '{' switchBlock* '}'
    ;

switchBlock
    :   CASE switchCaseList ':' statement*
    ;

switchCase
    :   (constant | DEFAULT)
    ;

switchCaseList
    :   switchCase (',' switchCase)*
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
    |   expr {inCondition}? op=(LT | GT | GE | '<=') expr           # RelationalExpression
    |   expr {inCondition}? op=('==' | '!=') expr                   # EqualityExpression
    |   expr {!requireCalc || inCalc}? '&' expr                     # BitwiseAndExpression
    |   expr {!requireCalc || inCalc}? '|' expr                     # BitwiseOrExpression
    |   expr {inCondition}? '||' expr                               # OrExpression
    |   expr {inCondition}? '&&' expr                               # AndExpression
    |   {inCallExpr}? type                                          # TypeExpression
    |   var                                                         # VarExpression
    |   var '(' expr ')'                                            # ArrayVarExpression
    |   constant                                                    # ScalarExpression
    |   interpolatedString                                          # InterpolatedStringExpression
    |   parenthesis                                                 # ParenthesisExpression
    |   type '(' expr ')'                                           # CastExpression
    |   callExpr                                                    # CallExpression
    |   identifier                                                  # IdentiferExpression
    ;

callExpr
    :   {!inCalc}? name=CALC {inCalc=true;} '(' exprList ')' {inCalc=false;}        # SpecialWordExpression
    |   identifier '(' {inCallExpr=true;} exprList? {inCallExpr=false;} ')'         # PrimaryCallExpression
    |   '.' identifier ('(' {inCallExpr=true;} exprList? {inCallExpr=false;} ')')?  # SecondaryCallExpression
    |   '~' identifier ('(' {inCallExpr=true;} exprList? {inCallExpr=false;} ')')?  # ProcCallExpression
    ;

constant
    :   NULL
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
    :   COORD_GRID                  # CoordGridNumericConstant
    |   INT                         # NormalNumericConstant
    |   HEX                         # HexNumericConstant
    |   INT ':' INT                 # ComponentIntNumericConstant // TODO move somewhere else?
    |   identifier  ':' identifier  # ComponentIdNumericConstant  // TODO move somewhere else?
    ;

stringConstant
    :   QUOTE_OPEN stringContent* QUOTE_CLOSE
    ;

interpolatedString
    :   QUOTE_OPEN (stringContent | stringExpression)* QUOTE_CLOSE
    ;

stringContent
    :   STRING_TEXT
    |   STRING_ESCAPED_CHAR
    |   stringTag
    ;

stringTag
    :   STRING_EXPR_START tag (EQUAL ATTRIBUTE_VALUE)? STRING_EXPR_END
    |   STRING_EXPR_START '/' tag STRING_EXPR_END
    ;

stringExpression
    :   STRING_EXPR_START expr STRING_EXPR_END
    ;

var
    :   prefix=(DOLLAR | CARET | PERCENT) identifier
    ;

varList
    :   var (',' var)*
    ;

identifier
    :   ID
    |   type
    ;

defType
    :	DEF_INT
    |   DEF_STRING
    |   DEF_TYPE
    ;

type
    :	TYPEINT
    |   TYPESTRING
    |   TYPE
    ;

tag
    :   TAG_COL
    |   TAG_BR
    |   TAG_U
    |   TAG_STR
    |   TAG_SHAD
    |   TAG_IMG
    |   TAG_GT
    |   TAG_LT
    ;
