# Jacobus Burger (2022)
# polyhedra, 2014 ICPC Div 2

T = int(input())
for _ in range(T):
    V, E = [int(n) for n in input().split()]
    # V - E + F = 2, therefore F = 2 - V + E
    F = 2 - V + E
    print(F)
