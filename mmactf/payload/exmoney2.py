from pwn import *
from time import sleep

while 1 : 
	p = remote("pwn1.chal.mmactf.link",21345)
	s = remote("pwn1.chal.mmactf.link",21345)

	#p = remote("127.0.0.1",3000)
	#p = process("moneygame/moneygame")
	stock = [10000,10000,10000]
	stock2 = [10000,10000,10000]
	old = [100,100,100]
	last = [100,100,100]
	item = [0,0,0]
	print s.recv()
	s.sendline("R")
	history = []
	strategy = []
	#strategy += [0]
	ff = []
	gg = 0
	for i in range(51):
		quiz =  s.recvuntil("[Rest] :")
		money = int(quiz.replace(".","").split('\n')[-5].split()[2][1:-1])
		#print money
		quiz = quiz.replace(".","").split('\n')[-4:-1]
		new = [int(quiz[0].split()[2][1:]),int(quiz[1].split()[2][1:]) ,int(quiz[2].split()[2][1:]) ] 
		item = [int(quiz[0].split()[5][:-1]),int(quiz[1].split()[5][:-1]) ,int(quiz[2].split()[5][:-1]) ] 
		#print quiz
		#print new
		#print item
		a = new[0]>old[0]
		b = new[1]>old[1]
		c = new[2]>old[2]
		if a + b + c > 1:
			strategy += [0]	
		elif a:
			strategy += [1]	
		elif b:
			strategy += [2]
		elif c:
			strategy += [3]
		else :
			strategy += [4]
			gg += 1
		old = new
		history += [new]
		mybuy = 0
		mysell = 0

		s.sendline("R")
	if gg > 20:
		print "GGGGGGGGGGGGGGGGGGGGGGGG"
		sleep(1)
		continue
	print strategy
	print len(strategy)
	print history
	cmd = 0
	#raw_input()	
	for i in range(50):
		print "What I do?",strategy[i],cmd
		#raw_input()
		quiz =  p.recvuntil("[Rest] :")
		money = int(quiz.replace(".","").split('\n')[-5].split()[2][1:-1])
		quiz = quiz.replace(".","").split('\n')[-4:-1]
		new = [int(quiz[0].split()[2][1:]),int(quiz[1].split()[2][1:]) ,int(quiz[2].split()[2][1:]) ] 
		item = [int(quiz[0].split()[5][:-1]),int(quiz[1].split()[5][:-1]) ,int(quiz[2].split()[5][:-1]) ] 
		print quiz
		print new
		print item
		t = -1
		if strategy[i] == 0 :
			t = i
			while t < len(strategy)-1 and strategy[t] == 0 :
				t+=1
			if strategy[t] == 0:
				if cmd == 1 :
					p.sendline("S")
					print p.recvuntil(":")
					p.sendline("1")
					n = p.recvuntil(":")
					print n
				elif cmd == 2 :
					p.sendline("S")
					print p.recvuntil(":")
					p.sendline("2")
					n = p.recvuntil(":")
					print n
				elif cmd == 3 :
					p.sendline("S")
					print p.recvuntil(":")
					p.sendline("3")
					n = p.recvuntil(":")
					print n
				if cmd > 0 and cmd < 4:
					n = n.split("-")[1].split(")")[0]
					print n
					p.sendline(n)
				cmd = 5
				continue
			tt = i
			for tt in range(t-i):
				strategy[i+tt] = strategy[t]
			print strategy

		if strategy[i] != cmd and cmd != 0:
			if item[0] != 0 :
				cmd = 1
			if item[1] != 0 :
				cmd = 2
			if item[2] != 0 :
				cmd = 3
			if cmd == 1 :
				p.sendline("S")
				print p.recvuntil(":")
				p.sendline("1")
				n = p.recvuntil(":")
				print n
			elif cmd == 2 :
				p.sendline("S")
				print p.recvuntil(":")
				p.sendline("2")
				n = p.recvuntil(":")
				print n
			elif cmd == 3 :
				p.sendline("S")
				print p.recvuntil(":")
				p.sendline("3")
				n = p.recvuntil(":")
				print n
			if cmd > 0 and cmd < 4:
				n = n.split("-")[1].split(")")[0]
				print n
				p.sendline(n)
				cmd = strategy[t]
				continue
			cmd = 0
		if t != -1:
			cmd = strategy[t]
		
		if cmd == 1 :
			p.sendline("B")
			print "s",p.recvuntil(":")
			p.sendline("1")
			n = p.recvuntil(":")
			print "n",n
		if cmd == 2 :
			p.sendline("B")
			print "s",p.recvuntil(":")
			p.sendline("2")
			n = p.recvuntil(":")
			print "n",n
		if cmd == 3 :
			p.sendline("B")
			print "s",p.recvuntil(":")
			p.sendline("2")
			n = p.recvuntil(":")
			print "n",n
		if cmd < 4 and cmd > 0:
			n = n.split("-")[1].split(")")[0]
			print n
			p.sendline(n)
		ff += [money]
		print money
		if cmd == 0 or cmd > 3 :
			p.sendline("R")
	quiz =  p.recvuntil("[Rest] :",timeout=3)
	if item[0] != 0 :
		cmd = 1
	if item[1] != 0 :
		cmd = 2
	if item[2] != 0 :
		cmd = 3
	if cmd == 1 :
		p.sendline("S")
		print p.recvuntil(":")
		p.sendline("1")
		n = p.recvuntil(":")
		print n
	elif cmd == 2 :
		p.sendline("S")
		print p.recvuntil(":")
		p.sendline("2")
		n = p.recvuntil(":")
		print n
	elif cmd == 3 :
		p.sendline("S")
		print p.recvuntil(":")
		p.sendline("3")
		n = p.recvuntil(":")
		print n
	if cmd > 0 and cmd < 4:
		n = n.split("-")[1].split(")")[0]
		print n
		p.sendline(n)
	
	p.sendline("R")
	quiz =  p.recvuntil("[Rest] :")
	money = int(quiz.replace(".","").split('\n')[-5].split()[2][1:-1])
	quiz = quiz.replace(".","").split('\n')[-4:-1]
	new = [int(quiz[0].split()[2][1:]),int(quiz[1].split()[2][1:]) ,int(quiz[2].split()[2][1:]) ] 
	item = [int(quiz[0].split()[5][:-1]),int(quiz[1].split()[5][:-1]) ,int(quiz[2].split()[5][:-1]) ] 
	print ff
	print money
	print strategy
	if money > 10000000:
		p.interactive()
	sleep(4)
	p.close()
	s.close()
	continue
	
	p.sendline("R")

	#while raw_input("Continue?") != "N":
	for i in range(13):
		quiz =  s.recvuntil("[Rest] :")
		money = int(quiz.replace(".","").split('\n')[-5].split()[2][1:-1])
		#print money
		quiz = quiz.replace(".","").split('\n')[-4:-1]
		new = [int(quiz[0].split()[2][1:]),int(quiz[1].split()[2][1:]) ,int(quiz[2].split()[2][1:]) ] 
		#item = [int(quiz[0].split()[5][:-1]),int(quiz[1].split()[5][:-1]) ,int(quiz[2].split()[5][:-1]) ] 
		print quiz
		print "new",new,"old",old
		print item
		a = new[0]>old[0] and new[0]-old[0] > new[1]-old[1] and new[0]-old[0] > new[2] - old[2]
		b = new[1]>old[1] and new[1]-old[1] > new[2]-old[2] and new[1]-old[1] > new[2] - old[2]
		c = new[2]>old[2] and new[2]-old[2] > new[1]-old[1] and new[2]-old[2] > new[0] - old[0]
		#old = new
		s.sendline("R")
		
		quiz =  s.recvuntil("[Rest] :")
		#money = int(quiz.replace(".","").split('\n')[-5].split()[2][1:-1])
		print money
		quiz = quiz.replace(".","").split('\n')[-4:-1]
		new = [int(quiz[0].split()[2][1:]),int(quiz[1].split()[2][1:]) ,int(quiz[2].split()[2][1:]) ] 
		#item = [int(quiz[0].split()[5][:-1]),int(quiz[1].split()[5][:-1]) ,int(quiz[2].split()[5][:-1]) ] 
		print quiz
		print "new",new,"old",old
		print item
		a = new[0]>old[0] and new[0]-old[0] > new[1]-old[1] and new[0]-old[0] > new[2] - old[2]
		b = new[1]>old[1] and new[1]-old[1] > new[2]-old[2] and new[1]-old[1] > new[2] - old[2]
		c = new[2]>old[2] and new[2]-old[2] > new[1]-old[1] and new[2]-old[2] > new[0] - old[0]
		#old = new
		s.sendline("R")
		
		quiz =  p.recvuntil("[Rest] :")
		money = int(quiz.replace(".","").split('\n')[-5].split()[2][1:-1])
		print money
		quiz = quiz.replace(".","").split('\n')[-4:-1]
		comp = [int(quiz[0].split()[2][1:]),int(quiz[1].split()[2][1:]) ,int(quiz[2].split()[2][1:]) ] 
		item = [int(quiz[0].split()[5][:-1]),int(quiz[1].split()[5][:-1]) ,int(quiz[2].split()[5][:-1]) ] 
		print quiz
		print comp
		print item

		quiz =  s.recvuntil("[Rest] :")
		#money = int(quiz.replace(".","").split('\n')[-5].split()[2][1:-1])
		print money
		quiz = quiz.replace(".","").split('\n')[-4:-1]
		new = [int(quiz[0].split()[2][1:]),int(quiz[1].split()[2][1:]) ,int(quiz[2].split()[2][1:]) ] 
		#item = [int(quiz[0].split()[5][:-1]),int(quiz[1].split()[5][:-1]) ,int(quiz[2].split()[5][:-1]) ] 
		print quiz
		print "new",new,"old",old
		print item
		a = new[0]>old[0] and (new[0]-old[0])*(money/old[0]) > (new[1]-old[1])*(money/old[1]) and (new[0]-old[0])*(money/old[0]) > (new[2] - old[2])*(money/old[2])
		b = new[1]>old[1] and (new[1]-old[1])*(money/old[1]) > (new[0]-old[0])*(money/old[0]) and (new[1]-old[1])*(money/old[1]) > (new[2] - old[2])*(money/old[2])
		c = new[2]>old[2] and (new[2]-old[2])*(money/old[2]) > (new[0]-old[0])*(money/old[0]) and (new[2]-old[2])*(money/old[2]) > (new[1] - old[1])*(money/old[1])
#		b = new[1]>old[1] and new[1]-old[1] > new[2]-old[2] and new[1]-old[1] > new[2] - old[2]
#		c = new[2]>old[2] and new[2]-old[2] > new[1]-old[1] and new[2]-old[2] > new[0] - old[0]
		old = new
		s.sendline("R")
		
		#BUY
		#a = stock[0]-comp[0] > stock[1] - comp[1] & stock[0]-comp[0] > stock[2]-comp[2] & stock[0]-comp[0]>0
		#b = stock[1]-comp[1] > stock[0] - comp[0] & stock[1]-comp[1] > stock[2]-comp[2] & stock[1]-comp[1]>0
		#c = stock[2]-comp[2] > stock[1] - comp[1] & stock[2]-comp[2] > stock[0]-comp[0] & stock[2]-comp[2]>0
		
		if a or b or c: 
			if a :
				p.sendline("B")
				print "s",p.recvuntil(":")
				p.sendline("1")
				n = p.recvuntil(":")
				print "n",n
				stock2[0] = comp[0]
			elif b :
				p.sendline("B")
				print "s",p.recvuntil(":")
				p.sendline("2")
				n = p.recvuntil(":")
				print "n",n
				stock2[1] = comp[1]
			elif c :
				p.sendline("B")
				print "s",p.recvuntil(":")
				p.sendline("3")
				n = p.recvuntil(":")
				print "n",n
				stock2[2] = comp[2]
			n = n.split("-")[1].split(")")[0]
			print n
			p.sendline(n)
			mybuy+=1
			ff += [money]
		else:
			p.sendline("R")
		
		quiz =  p.recvuntil("[Rest] :")
		money = int(quiz.replace(".","").split('\n')[-5].split()[2][1:-1])
		print money
		quiz = quiz.replace(".","").split('\n')[-4:-1]
		comp = [int(quiz[0].split()[2][1:]),int(quiz[1].split()[2][1:]) ,int(quiz[2].split()[2][1:]) ] 
		item = [int(quiz[0].split()[5][:-1]),int(quiz[1].split()[5][:-1]) ,int(quiz[2].split()[5][:-1]) ] 
		print quiz
		print comp
		print item
		p.sendline("R")

		quiz =  p.recvuntil("[Rest] :")
		money = int(quiz.replace(".","").split('\n')[-5].split()[2][1:-1])
		print money
		quiz = quiz.replace(".","").split('\n')[-4:-1]
		comp = [int(quiz[0].split()[2][1:]),int(quiz[1].split()[2][1:]) ,int(quiz[2].split()[2][1:]) ] 
		item = [int(quiz[0].split()[5][:-1]),int(quiz[1].split()[5][:-1]) ,int(quiz[2].split()[5][:-1]) ] 
		print quiz
		print comp
		print item
		p.sendline("R")
		
		#a = item[0]!=0
		#b = item[1]!=0
		#c = item[2]!=0
		quiz =  s.recvuntil("[Rest] :")
		money = int(quiz.replace(".","").split('\n')[-5].split()[2][1:-1])
		#print money
		quiz = quiz.replace(".","").split('\n')[-4:-1]
		new = [int(quiz[0].split()[2][1:]),int(quiz[1].split()[2][1:]) ,int(quiz[2].split()[2][1:]) ] 
		#item = [int(quiz[0].split()[5][:-1]),int(quiz[1].split()[5][:-1]) ,int(quiz[2].split()[5][:-1]) ] 
		#print quiz
		print "new",new,"old",old
		#print item
		old = new
		s.sendline("R")
		
		quiz =  p.recvuntil("[Rest] :")
		money = int(quiz.replace(".","").split('\n')[-5].split()[2][1:-1])
		print money
		quiz = quiz.replace(".","").split('\n')[-4:-1]
		comp = [int(quiz[0].split()[2][1:]),int(quiz[1].split()[2][1:]) ,int(quiz[2].split()[2][1:]) ] 
		item = [int(quiz[0].split()[5][:-1]),int(quiz[1].split()[5][:-1]) ,int(quiz[2].split()[5][:-1]) ] 
		print quiz
		#print comp
		print item
		if a or b or c:	
			if a :
				p.sendline("S")
				print p.recvuntil(":")
				p.sendline("1")
				n = p.recvuntil(":")
				print n
			elif b :
				p.sendline("S")
				print p.recvuntil(":")
				p.sendline("2")
				n = p.recvuntil(":")
				print n
			elif c :
				p.sendline("S")
				print p.recvuntil(":")
				p.sendline("3")
				n = p.recvuntil(":")
				print n
			n = n.split("-")[1].split(")")[0]
			print n
			p.sendline(n)
			mysell+=1
		else:
			p.sendline("R")

	print mybuy			
	print mysell		
	print ff
	print ff[-1]
	sleep(2)	
	if ff < 10000000:
		break
	p.close()
	s.close()
p.interactive()


