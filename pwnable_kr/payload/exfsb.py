from pwn import *

#p = remote("linux6.cs.nctu.edu.tw",11005)
p = remote("127.0.0.1",4123)
p.sendline("AAAAA")
p.sendline("AAAAA")
p.sendline("AAAAA")
p.sendline("AAAAA")
p.sendline("AAAAA")

p.interactive()


