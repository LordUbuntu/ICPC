# Jacobus Burger (2022)
# runes problem, 2014 ICPC D2

T = int(input())
for _ in range(T):
    equation = input().split('=')
    for i in range(10):
        lhs = eval(equation[0].replace('?', str(i)))
        rhs = eval(equation[1].replace('?', str(i)))
        if lhs == rhs:
            print(i)
            break
    else:
        print(-1)


# alternative approach
from functools import reduce
from operator import eq
for _ in range(T):
    equation = input().split('=')
    for i in range(10):
        calculation = map(lambda s: eval(s.replace('?', str(i))), equation)
        if reduce(eq, calculation):
            print(i)
            break
    else:
        print(-1)
