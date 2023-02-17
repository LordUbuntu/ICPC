# Problem A - Beast Bullies
n = int(input())
animals = sorted([int(input()) for i in range(n)])
attackers = n-1
loss = 0

defenceStr = 0
attackStr = animals[-1]

for i in range(n-2, -1, -1):
    defenceStr += animals[i]
    
    if defenceStr >= attackStr:
        attackers = i
        loss = 0
        attackStr += defenceStr
        defenceStr = 0
    else:  
        loss += 1
print(n - loss)
