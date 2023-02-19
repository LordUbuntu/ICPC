# greedy scheduler problem
from heapq import heappop as pop
from heapq import heappush as push
from heapq import heapify

# setup
n, c = map(int, input().split())
customers = map(int, input().split())
# initialize first n customer times for the n cashiers
cashiers = []
for i in range(n):
    push(cashiers, (next(customers), i+1))
order = [i for i in range(1, n + 1)]  # initial order is cashier order

# simulate remaining customers
for customer in customers:
    time, cashier = pop(cashiers)
    order.append(cashier)
    push(cashiers, (time + customer, cashier))

# show results
print(' '.join(map(str, order)))
