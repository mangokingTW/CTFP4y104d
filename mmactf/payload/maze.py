from pwn import *

p = remote("cry1.chal.mmactf.link",26314)
while 1 :
	print p.recv()
	p.sendline("n")
	p.sendline("n")
	p.sendline("n")
	p.sendline("n")
	p.sendline("n")
