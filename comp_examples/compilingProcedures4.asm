	.data
	varignore: .word 0
	.text
	.globl main
	 main:  #QTSPIM will automatically look for main
	# future code will go here
	

	subu $sp, $sp, 4
	sw $ra, ($sp)
	li $v0 9
	subu $sp $sp 4
	sw $v0 ($sp)
	jal procfoo
	lw $t0 ($sp)
	addu $sp $sp 4
	lw $ra, ($sp)
	addu $sp, $sp, 4
	sw $v0 varignore
	

	li $v0 10
	syscall   # halt
	
procfoo:
	li $v0 1
	subu $sp $sp 4
	sw $v0 ($sp)
	lw $v0 4($sp)
	lw $t0 ($sp)
	addu $sp $sp 4
	add $v0 $t0 $v0
	move $a0 $v0
	li $v0 1
	syscall
	addi $a0, $0, 0xA
	addi $v0, $0, 0xB
	syscall
	jr $ra
	
