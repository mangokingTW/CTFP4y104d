from pwn import *

p = process('secure_mm')

p.interactive()
