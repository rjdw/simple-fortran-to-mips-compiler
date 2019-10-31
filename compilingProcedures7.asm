	.data
	varcount: .word 0
	varignore: .word 0
	vartimes: .word 0
	.text
	.globl main
	 main:  #QTSPIM will automatically look for main
	# future code will go here
	

	li $v0 196
	sw $v0 varcount
	li $v0 0
	sw $v0 vartimes
	subu $sp, $sp, 4
	sw $ra, ($sp)
	li $v0 10
	subu $sp $sp 4
	sw $v0 ($sp)
	li $v0 13
	subu $sp $sp 4
	sw $v0 ($sp)
	jal procprintSquares
	lw $t0 ($sp)
	addu $sp $sp 4
	lw $t0 ($sp)
	addu $sp $sp 4
	lw $ra, ($sp)
	addu $sp, $sp, 4
	sw $v0 varignore
	la $t0 varcount
	lw $v0 ($t0)
	move $a0 $v0
	li $v0 1
	syscall
	addi $a0, $0, 0xA
	addi $v0, $0, 0xB
	syscall
	la $t0 vartimes
	lw $v0 ($t0)
	move $a0 $v0
	li $v0 1
	syscall
	addi $a0, $0, 0xA
	addi $v0, $0, 0xB
	syscall
	

	li $v0 10
	syscall   # halt
	
procprintSquares:
	li $v0 0
	subu $sp $sp 4
	sw $v0 ($sp)
	li $v0 0
	subu $sp $sp 4
	sw $v0 ($sp)
	li $v0 0
	subu $sp $sp 4
	sw $v0 ($sp)
	lw $v0 16($sp)
	sw $v0 0($sp)
startWhile1:
	lw $v0 0($sp)
	move $t0 $v0
	lw $v0 12($sp)
	bgt $t0 $v0 endWhile2
	lw $v0 0($sp)
	subu $sp $sp 4
	sw $v0 ($sp)
	lw $v0 0($sp)
	lw $t0 ($sp)
	addu $sp $sp 4
	mult $t0 $v0
	mflo $v0
	sw $v0 4($sp)
	lw $v0 4($sp)
	move $a0 $v0
	li $v0 1
	syscall
	addi $a0, $0, 0xA
	addi $v0, $0, 0xB
	syscall
	lw $v0 0($sp)
	subu $sp $sp 4
	sw $v0 ($sp)
	li $v0 1
	lw $t0 ($sp)
	addu $sp $sp 4
	add $v0 $t0 $v0
	sw $v0 0($sp)
	la $t0 vartimes
	lw $v0 ($t0)
	subu $sp $sp 4
	sw $v0 ($sp)
	li $v0 1
	lw $t0 ($sp)
	addu $sp $sp 4
	add $v0 $t0 $v0
	sw $v0 vartimes
	j startWhile1
	endWhile2: 
	lw $t0 ($sp)
	addu $sp $sp 4
	lw $t0 ($sp)
	addu $sp $sp 4
	lw $t0 ($sp)
	addu $sp $sp 4
	move $v0 $t0
	jr $ra
	
