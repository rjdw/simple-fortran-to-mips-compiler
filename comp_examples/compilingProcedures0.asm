	.data
	.text
	.globl main
	 main:  #QTSPIM will automatically look for main
	# future code will go here
	

	

	li $v0 10
	syscall   # halt
procfoo:
	li $v0 1
	move $a0 $v0
	li $v0 1
	syscall
	addi $a0, $0, 0xA
	addi $v0, $0, 0xB
	syscall
	jr $ra
