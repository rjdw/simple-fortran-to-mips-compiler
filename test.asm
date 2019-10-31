.data
varx: .word 0

.text
.globl main
 main:  #QTSPIM will automatically look for main
# future code will go here

la $t0 varx
lw $a0 ($t0)
li $v0 1
syscall

li $t0 42
sw $t0 varx
la $t0 varx
lw $a0 ($t0)
li $v0 1
syscall

li $v0 10
syscall
