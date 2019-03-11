parser grammar ClientScriptParser;

@lexer::header {package com.neptune.jagintellij.parser;}

options { tokenVocab = ClientScriptLexer; }

file
    :   scriptDeclaration*
    ;

scriptDeclaration
    : SCRIPT_DECLARATION ('(' formal_args? ')')?
    ;

formal_args : formal_arg (',' formal_arg)* ;

formal_arg : type LOCAL_VAR ;

// TODO load types differently?
type:	'int'
	|	'float'
	|	'string'
	|	'boolean'
    ;

block
    : '{'  '}'
    ;
