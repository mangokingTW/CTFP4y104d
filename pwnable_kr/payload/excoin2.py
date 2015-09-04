from pwn import *
from time import *
from sys import stdout

p = remote("pwnable.kr",9008)
sleep(2)
print p.recv()
def mys(s):
	p.send(s)
	stdout.write(s)

NC =  p.recvline()
N = int(NC.split()[0][2:])
C = int(NC.split()[1][2:])
print N,C
N=N
A = 0
for j in range(C):
	mys(str(A))
	A=A+1
	for i in range(int(N/C-0.5)):
		mys(" "+str(A))
		A=A+1
	if j!=C-1:
		mys("-")
	else:
		p.sendline("")
print ""
print p.recvline()
p.interactive()


