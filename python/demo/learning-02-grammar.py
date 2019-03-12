# coding=utf-8



# variable type:number,string,list,tuple(is array),dictionary
counter = 100 # integer
miles = 100.22 # float
name = "Elinx" # string
# a = b = c = 333
a, b, c = 3, 4, "name"
print counter,miles,name
print a,b,c

#delete reference.(quotes)
del a,b,c

# python number type:int,long,float,complex
"""
int	long	float	complex
10	51924361L	0.0	3.14j
100	-0x19323L	15.20	45.j
-786	0122L	-21.9	9.322e-36j
080	0xDEFABCECBDAECBFBAEl	32.3e+18	.876j
-0490	535633629843L	-90.	-.6545+0J
-0x260	-052318172735L	-32.54e100	3e+26J
0x69	-4721885298529L	70.2E-12	4.53e-7j
"""

#python help command
#> python -h

# Waiting user input.input enter key to exit
# raw_input("\n\nPress the enter key to exit.") # temporarily closed

# multi line sentence,use \ to connect
total = 1 + \
        2
print total

# Reserved characters
"""
and	exec	not
assert	finally	or
break	for	pass
class	from	print
continue	global	raise
def	if	return
del	import	try
elif	in	while
else	is	with
except	lambda	yield
"""

# Comment
print "Hello, World!";print 'Same line'
name = "Elinx" # This is a comment

"""
Multi line comment.
"""

# if grammar,True is py's bool grammar
if True:
    print "true"
else:
    print "false"

