import sys
#sys.setrecursionlimit(10000)

def ReadMaze(h,f):
	m = ""
	for i in range(h):
		m += f.readline().strip()
	return m

TC = 0

def BFS(m,w,s,NC):
	global TC
	(R,L,D,U) = ("X","X","X","X")
	(RC,LC,DC,UC) = (0,0,0,0)
	(RE,LE,DE,UE) = (0,0,0,0)
	if m[s+1] == "G" and NC == TC:
		return ("R",0,0)
	if m[s-1] == "G" and NC == TC:
		return ("L",0,0)
	if m[s+w] == "G" and NC == TC:
		return ("D",0,0)
	if m[s-w] == "G" and NC == TC:
		return ("U",0,0)
	if m[s+1] == "C":
		mm = m.replace("N","z").replace("Z","G")
		m = m[:s+1]+'N'+m[s+2:]
		mm = mm[:s+1]+'N'+mm[s+2:]
		(R,RC,RE) = BFS(mm,w,s+1,NC+1)
		RC += 1
	if m[s-1] == "C":
		mm = m.replace("N","z").replace("Z","G")
		m = m[:s-1]+'N'+m[s:]
		mm = mm[:s-1]+'N'+mm[s:]
		(L,LC,LE) = BFS(mm,w,s-1,NC+1)
		LC += 1
	if m[s+w] == "C":
		mm = m.replace("N","z").replace("Z","G")
		m = m[:s+w]+'N'+m[s+w+1:]
		mm = mm[:s+w]+'N'+mm[s+w+1:]
		(D,DC,DE) = BFS(mm,w,s+w,NC+1)
		DC += 1
	if m[s-w] == "C":
		mm = m.replace("N","z").replace("Z","G")
		m = m[:s-w]+'N'+m[s-w+1:]
		mm = mm[:s-w]+'N'+mm[s-w+1:]
		(U,UC,UE) = BFS(mm,w,s-w,NC+1)
		UC += 1
	if m[s+1] == "E":
		m = m[:s+1]+'N'+m[s+2:]
		(R,RC,RE) = BFS(m,w,s+1,NC)
		RE = RE+20
	if m[s-1] == "E":
		m = m[:s-1]+'N'+m[s:]
		(L,LC,LE) = BFS(m,w,s-1,NC)
		LE = LE+20
	if m[s+w] == "E":
		m = m[:s+w]+'N'+m[s+w+1:]
		(D,DC,DE) = BFS(m,w,s+w,NC)
		DE = DE+20
	if m[s-w] == "E":
		m = m[:s-w]+'N'+m[s-w+1:]
		(U,UC,UE) = BFS(m,w,s-w,NC)
		UE = UE+20
	if m[s+1] == "z":
		m = m[:s+1]+'N'+m[s+2:]
		(R,RC,RE) = BFS(m,w,s+1,NC)
		RE = RE-1
	if m[s-1] == "z":
		m = m[:s-1]+'N'+m[s:]
		(L,LC,LE) = BFS(m,w,s-1,NC)
		LE = LE-1
	if m[s+w] == "z":
		m = m[:s+w]+'N'+m[s+w+1:]
		(D,DC,DE) = BFS(m,w,s+w,NC)
		DE = DE-1
	if m[s-w] == "z":
		m = m[:s-w]+'N'+m[s-w+1:]
		(U,UC,UE) = BFS(m,w,s-w,NC)
		UE = UE-1
	if m[s+1] == ".":
		m = m[:s+1]+'N'+m[s+2:]
		(R,RC,RE) = BFS(m,w,s+1,NC)
	if m[s-1] == ".":
		m = m[:s-1]+'N'+m[s:]
		(L,LC,LE) = BFS(m,w,s-1,NC)
	if m[s+w] == ".":
		m = m[:s+w]+'N'+m[s+w+1:]
		(D,DC,DE) = BFS(m,w,s+w,NC)
	if m[s-w] == ".":
		m = m[:s-w]+'N'+m[s-w+1:]
		(U,UC,UE) = BFS(m,w,s-w,NC)
	if m[s+1] == "G":
		m = m[:s+1]+'Z'+m[s+2:]
		(R,RC,RE) = BFS(m,w,s+1,NC)
	if m[s-1] == "G":
		m = m[:s-1]+'Z'+m[s:]
		(L,LC,LE) = BFS(m,w,s-1,NC)
	if m[s+w] == "G":
		m = m[:s+w]+'Z'+m[s+w+1:]
		(D,DC,DE) = BFS(m,w,s+w,NC)
	if m[s-w] == "G":
		m = m[:s-w]+'Z'+m[s-w+1:]
		(U,UC,UE) = BFS(m,w,s-w,NC)
	NC = max(RC,LC,DC,UC)
	#(RE,LE,DE,UE) = (0,0,0,0)
	N = []
	if RC == NC and "X" not in R : N += [len(R)-RE]
	if LC == NC and "X" not in L : N += [len(L)-LE]
	if DC == NC and "X" not in D : N += [len(D)-DE]
	if UC == NC and "X" not in U : N += [len(U)-UE]
	if not N:
		return ("X",-1,0)
	MN = min(N)
	if len(R)-RE == MN and "X" not in R  : return ("R"+R,RC,RE)
	if len(L)-LE == MN and "X" not in L  : return ("L"+L,LC,LE)
	if len(D)-DE == MN and "X" not in D  : return ("D"+D,DC,DE)
	if len(U)-UE == MN and "X" not in U  : return ("U"+U,UC,UE)

def SolveMaze(w,h,c,f,o,i):
	m = ReadMaze(h,f)
	global TC, EN
	TC = c
	(A,B,C) = BFS(m.replace("S",".") ,w,m.find("S"),0)
	print i,A,B,C,EN
	EN += C
	if i == 850:
		print m
		raw_input()
		pass
	if A == "X":
		A = "R"
	o.write(A+"\n")
f = open('maze.txt','r')
o = open('answer.txt','w')

line = f.readline()

i = 0
EN = 0
while line :
	print line
	i = i+1
	(w,h,c) = line.split()
	SolveMaze(int(w),int(h),int(c),f,o,i)
	line = f.readline()
print EN
