	.data
	varx: .word 0
	vary: .word 0
	.text
	.globl main
	 main:  #QTSPIM will automatically look for main
	# future code will go here
	

	li $v0 3
	sw $v0 varx
	li $v0 2
	subu $sp $sp 4
	sw $v0 ($sp)
	la $t0 varx
	lw $v0 ($t0)
	lw $t0 ($sp)
	addu $sp $sp 4
	mult $t0 $v0
	mflo $v0
	sw $v0 vary
	la $t0 varx
	lw $v0 ($t0)
	subu $sp $sp 4
	sw $v0 ($sp)
	la $t0 vary
	lw $v0 ($t0)
	lw $t0 ($sp)
	addu $sp $sp 4
	add $v0 $t0 $v0
	move $a0 $v0
	li $v0 1
	syscall
	addi $a0, $0, 0xA
	addi $v0, $0, 0xB
	syscall
	

	li $v0 10
	syscall   # halt
