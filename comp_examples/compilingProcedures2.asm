	.data
	varignore: .word 0
	.text
	.globl main
	 main:  #QTSPIM will automatically look for main
	# future code will go here
	

	subu $sp, $sp, 4
	sw $ra, ($sp)
	jal procfoo
	lw $ra, ($sp)
	addu $sp, $sp, 4
	sw $v0 varignore
	

	li $v0 10
	syscall   # halt
	
procfoo:
	subu $sp, $sp, 4
	sw $ra, ($sp)
	jal procbar
	lw $ra, ($sp)
	addu $sp, $sp, 4
	sw $v0 varignore
	li $v0 -3
	move $a0 $v0
	li $v0 1
	syscall
	addi $a0, $0, 0xA
	addi $v0, $0, 0xB
	syscall
	jr $ra
	
	
procbar:
	li $v0 3
	move $a0 $v0
	li $v0 1
	syscall
	addi $a0, $0, 0xA
	addi $v0, $0, 0xB
	syscall
	jr $ra
	
