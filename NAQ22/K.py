# Jacobus Burger (2023)
# My solution to this problem (very straight-forward)
# Poblem K - Smallest Caclulated Value
from itertools import product
from math import inf

a, b, c = map(int, input().split())
ops = ['+', '-', '*', '//']
min_val = inf

for op1, op2 in product(ops, ops):
    if op1 == '//' or op2 == '//':
        if a % b != 0:
            continue
        elif (a // b) % c != 0:
            continue
    val = eval(f"({a} {op1} {b}) {op2} {c}")
    if val < 0:
        continue
    if val < min_val:
        min_val = val
    if min_val == 0:
        break
print(min_val)
