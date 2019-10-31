	.data
	varcount: .word 0
	.text
	.globl main
	 main:  #QTSPIM will automatically look for main
	# future code will go here
	

	li $v0 1
	sw $v0 varcount
startWhile1:
	la $t0 varcount
	lw $v0 ($t0)
	move $t0 $v0
	li $v0 15
	bgt $t0 $v0 endWhile2
	la $t0 varcount
	lw $v0 ($t0)
	move $a0 $v0
	li $v0 1
	syscall
	addi $a0, $0, 0xA
	addi $v0, $0, 0xB
	syscall
	la $t0 varcount
	lw $v0 ($t0)
	subu $sp $sp 4
	sw $v0 ($sp)
	li $v0 1
	lw $t0 ($sp)
	addu $sp $sp 4
	add $v0 $t0 $v0
	sw $v0 varcount
	j startWhile1
	endWhile2: 
	

	li $v0 10
	syscall   # halt
