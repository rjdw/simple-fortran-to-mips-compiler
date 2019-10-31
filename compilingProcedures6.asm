	.data
	.text
	.globl main
	 main:  #QTSPIM will automatically look for main
	# future code will go here
	

	subu $sp, $sp, 4
	sw $ra, ($sp)
	li $v0 5
	subu $sp $sp 4
	sw $v0 ($sp)
	li $v0 12
	subu $sp $sp 4
	sw $v0 ($sp)
	jal procmax
	lw $t0 ($sp)
	addu $sp $sp 4
	lw $t0 ($sp)
	addu $sp $sp 4
	lw $ra, ($sp)
	addu $sp, $sp, 4
	move $a0 $v0
	li $v0 1
	syscall
	addi $a0, $0, 0xA
	addi $v0, $0, 0xB
	syscall
	subu $sp, $sp, 4
	sw $ra, ($sp)
	li $v0 13
	subu $sp $sp 4
	sw $v0 ($sp)
	li $v0 7
	subu $sp $sp 4
	sw $v0 ($sp)
	jal procmax
	lw $t0 ($sp)
	addu $sp $sp 4
	lw $t0 ($sp)
	addu $sp $sp 4
	lw $ra, ($sp)
	addu $sp, $sp, 4
	move $a0 $v0
	li $v0 1
	syscall
	addi $a0, $0, 0xA
	addi $v0, $0, 0xB
	syscall
	

	li $v0 10
	syscall   # halt
	
procmax:
	li $v0 0
	subu $sp $sp 4
	sw $v0 ($sp)
	lw $v0 8($sp)
	sw $v0 0($sp)
	lw $v0 4($sp)
	move $t0 $v0
	lw $v0 8($sp)
	ble $t0 $v0 endif1
	lw $v0 4($sp)
	sw $v0 0($sp)
	endif1: 
	lw $t0 ($sp)
	addu $sp $sp 4
	move $v0 $t0
	jr $ra
	
