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
    :   type localVarReference
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
    :   defType localVarReference (EQUAL expr)? # NormalDeclarationStatement
    |   defType localVarReference '(' expr ')'  # ArrayDeclarationStatement
    ;

assignmentStatement
    :   assignableVar EQUAL expr            # SingleAssignmentStatement
    |   assignableVarList EQUAL exprList    # MultiAssignmentStatement
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
    :   (SWITCH | SWITCH_TYPE) parenthesis '{' caseClause* '}'
    ;

caseClause
    :   CASE (DEFAULT | caseClauseExpressionList) ':' statement*
    ;

caseClauseExpression
    :   constant
    |   identifier
    |   constantReference
    |   caseClauseCastExpression
    ;

caseClauseExpressionList
    :   caseClauseExpression (',' caseClauseExpression)*
    ;

caseClauseCastExpression
    :   type '(' caseClauseExpression ')'
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
    |   localVarReference                                           # VarExpression
    |   localArrayVarReference                                      # VarExpression
    |   gameVarReference                                            # VarExpression
    |   constantReference                                           # VarExpression
    |   constant                                                    # ScalarExpression
    |   interpolatedString                                          # InterpolatedStringExpression
    |   parenthesis                                                 # ParenthesisExpression
    |   type '(' expr ')'                                           # CastExpression
    |   callExpr                                                    # CallExpression
    |   {inCallExpr}? '&' hookExpression                            # HookRefExpression
    |   identifier                                                  # IdentiferExpression
    ;

callExpr
    :   {!inCalc}? name=CALC {inCalc=true;} '(' exprList ')' {inCalc=false;}                # SpecialWordExpression
    |   name=DEBUG '(' exprList ')'                                                         # SpecialWordExpression
    |   name=RUNELITE_CALLBACK '(' event=stringConstant (',' exprList)? ')' formalReturns?  # SpecialWordExpression
    |   identifier '(' {inCallExpr=true;} exprList? {inCallExpr=false;} ')'                 # PrimaryCallExpression
    |   '.' identifier ('(' {inCallExpr=true;} exprList? {inCallExpr=false;} ')')?          # SecondaryCallExpression
    |   '~' identifier ('(' {inCallExpr=true;} exprList? {inCallExpr=false;} ')')?          # ProcCallExpression
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

assignableVar
    :   localVarReference
    |   localArrayVarReference
    |   gameVarReference
    ;

localVarReference
    :   DOLLAR identifier
    ;

localArrayVarReference
    :   DOLLAR identifier '(' expr ')'
    ;

gameVarReference
    :   PERCENT identifier
    ;

constantReference
    :   CARET identifier
    ;

assignableVarList
    :   assignableVar (',' assignableVar)*
    ;

hookExpression
    :   identifier ('(' args=exprList? ')')? ('{' triggers=exprList '}')?
    |   NULL ('(' ')')?
    ;

identifier
    :   ID
    |   type
    |   RETURN
    |   SWITCH
    |   RUNELITE_CALLBACK
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
