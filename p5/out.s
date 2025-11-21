.data
.align 2
_string_newline:		.asciiz "\n"
.align 2
_bool_true:		.asciiz "true\n"
.align 2
_x:		.space 4
.align 2
_bool_false:		.asciiz "false\n"

.text
.globl main

main:
	# Preamble:
	sw $ra, 0($sp)
	subu $sp, $sp, 4
	sw $fp, 0($sp)
	subu $sp, $sp, 4
	addu $fp, $sp, 8
	subu $sp, $sp, 0
	# Body:
	li $t0, 5
	sw $t0, ($sp)
	subu $sp, $sp, 4
	lw $t0, 4($sp)
	addu $sp, $sp, 4
	sw $t0, _x
	lw $t0, _x
	sw $t0, ($sp)
	subu $sp, $sp, 4
	lw $a0, 4($sp)
	addu $sp, $sp, 4
	li $v0, 1
	syscall
	la $a0, _string_newline
	li $v0, 4
	syscall
return_main:
	# Exit:
	lw $ra, 0($fp)
	move $t0, $fp
	lw $fp, -4($fp)
	move $sp, $t0
	jr $ra

