.data

.text
.globl main
f:
# prologue f
addi $sp, $sp, -8
sw $ra, 4($sp)
sw $fp, 0($sp)
addi $fp, $sp, 8
lw $t0, 8($fp)
addi $sp, $sp, -4
sw $t0, 0($sp)
lw $t0, 12($fp)
lw $t1, 0($sp)
addi $sp, $sp, 4
add $t0, $t1, $t0
move $v0, $t0
# epilogue
lw $ra, 4($sp)
lw $fp, 0($sp)
addi $sp, $sp, 8
jr $ra
main:
# prologue main
addi $sp, $sp, -20
sw $ra, 16($sp)
sw $fp, 12($sp)
addi $fp, $sp, 20
li $t0, 4
sw $t0, -4($fp)
li $t0, 5
sw $t0, -8($fp)
lw $t0, -8($fp)
addi $sp, $sp, -4
sw $t0, 0($sp)
lw $t0, -4($fp)
addi $sp, $sp, -4
sw $t0, 0($sp)
jal f
addi $sp, $sp, 8
move $t0, $v0
sw $t0, -12($fp)
lw $t0, -12($fp)
move $a0, $t0
li $v0, 1
syscall
li $t0, 0
move $v0, $t0
# epilogue
lw $ra, 16($sp)
lw $fp, 12($sp)
addi $sp, $sp, 20
jr $ra
