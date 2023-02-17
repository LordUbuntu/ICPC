# Problem K - Smallest Caclulated Value


# Jacobus Burger (2023)
# My solution to this problem. Brute force (16 cases)
from itertools import product

a, b, c = map(int, input().split())
ops = ['+', '-', '*', '/']
min_val = (1000**3)

for op1, op2 in product(ops, ops):
    if op1 == '/':
        if a % b != 0:
            continue
    val = eval(f"{a} {op1} {b}")
    if op2 == '/':
        if val % c != 0:
            continue
    val = eval(f"{val} {op2} {c}")
    if val < min_val:
        if val < 0:
            continue
        min_val = val
    if min_val == 0:
        break
print(int(min_val))





# An alternative but equivalent approach taken by Finian
def finian():
    a, b, c = map(int, input().split())
    answers = []
    answers.append(eval(f"({a}+{b})+{c}"))
    answers.append(eval(f"({a}+{b})-{c}"))
    answers.append(eval(f"({a}+{b})*{c}"))

    answers.append(eval(f"({a}-{b})+{c}"))
    answers.append(eval(f"({a}-{b})-{c}"))
    answers.append(eval(f"({a}-{b})*{c}"))

    answers.append(eval(f"({a}*{b})+{c}"))
    answers.append(eval(f"({a}*{b})-{c}"))
    answers.append(eval(f"({a}*{b})*{c}"))

    if a % b == 0:
        answers.append(eval(f"({a}/{b})+{c}"))
        answers.append(eval(f"({a}/{b})-{c}"))
        answers.append(eval(f"({a}/{b})*{c}"))

    if (a+b) % c == 0:
        answers.append(eval(f"({a}+{b})/{c}"))
    if (a-b) % c == 0:
        answers.append(eval(f"({a}-{b})/{c}"))
    if (a*b) % c == 0:
        answers.append(eval(f"({a}*{b})/{c}"))

    if (a % b == 0) and (a / b) % c == 0:
        answers.append(eval(f"({a}/{b})/{c}"))

    print(int(min(filter(lambda x: x >= 0, answers))))
