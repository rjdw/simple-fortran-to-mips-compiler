	.text
	.globl main
	 main:  #QTSPIM will automatically look for main
	# future code will go here
	

	li $v0 -3
	subu $sp $sp 4
	sw $v0 ($sp)
	li $v0 11
	lw $t0 ($sp)
	addu $sp $sp 4
	add $v0 $t0 $v0
	move $a0 $v0
	li $v0 1
	syscall
	addi $a0, $0, 0xA
	addi $v0, $0, 0xB
	syscall
	li $v0 16
	subu $sp $sp 4
	sw $v0 ($sp)
	li $v0 2
	lw $t0 ($sp)
	addu $sp $sp 4
	div $t0 $v0
	mflo $v0
	move $a0 $v0
	li $v0 1
	syscall
	addi $a0, $0, 0xA
	addi $v0, $0, 0xB
	syscall
	

	li $v0 10
	syscall   # halt
