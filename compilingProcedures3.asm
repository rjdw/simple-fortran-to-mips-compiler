	.data
	vara: .word 0
	varb: .word 0
	.text
	.globl main
	 main:  #QTSPIM will automatically look for main
	# future code will go here
	

	li $v0 9
	sw $v0 vara
	li $v0 10
	sw $v0 varb
	subu $sp, $sp, 4
	sw $ra, ($sp)
	li $v0 11
	subu $sp, $sp, 4
	sw $v0, ($sp)
	la $t0 vara
	lw $v0 ($t0)
	subu $sp $sp 4
	sw $v0 ($sp)
	li $v0 3
	lw $t0 ($sp)
	addu $sp $sp 4
	add $v0 $t0 $v0
	subu $sp, $sp, 4
	sw $v0, ($sp)
	jal procfoo
	lw $t0, ($sp)
	addu $sp, $sp, 4
	lw $t0, ($sp)
	addu $sp, $sp, 4
	lw $ra, ($sp)
	addu $sp, $sp, 4
	sw $v0 vara
	

	li $v0 10
	syscall   # halt
	
procfoo:
	la $t0 vara
	lw $v0 ($t0)
	move $a0 $v0
	li $v0 1
	syscall
	addi $a0, $0, 0xA
	addi $v0, $0, 0xB
	syscall
	lw $v0 4($sp)
	move $a0 $v0
	li $v0 1
	syscall
	addi $a0, $0, 0xA
	addi $v0, $0, 0xB
	syscall
	lw $v0 0($sp)
	move $a0 $v0
	li $v0 1
	syscall
	addi $a0, $0, 0xA
	addi $v0, $0, 0xB
	syscall
	jr $ra
	
