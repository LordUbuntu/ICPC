# Problem G - Movie Night
from collections import deque
from itertools import chain
from functools import reduce
from operator import mul

n = int(input())
graph = {i: int(input()) for i in range(1, n + 1)}
unvisited = deque(graph)

# dfs components across graph, pay special attention to cycles
ordering = deque()  # global ordering (outside-in dfs), used to count later
cycles = []  # cycle components in graph
while unvisited:
    # dfs
    path, stack = [], unvisited.popleft()  # start at smallest first
    while stack:
        # get current node
        curr = stack
        # get next node and mark to be visited
        next = graph[curr]
        stack = next
        # mark current node as visited and remove from unvisited
        path.append(curr)
        if curr in unvisited:
            unvisited.remove(curr)
        # if next in path
        #   note path and cycle, then break
        #   notice: cycle must come before rest of path
        #           and rest of path must be reversed
        if next in path:
            cycle = path[path.index(next):]
            path = path[:path.index(next)]
            cycles.append(cycle)
            ordering.extend(cycle)
            ordering.extend(reversed(path))
            break
        # if next not in unvisited
        #   note path, then break
        if next not in unvisited:
            ordering.extend(reversed(path))
            break


# calculate the result
count = {node: 1 for node in ordering}

#   first calculate all non-cycle nodes by dependency
cycle_members = list(chain.from_iterable(cycles))
for node in reversed(ordering):
    if node in cycle_members: continue
    count[node] += 1
    count[graph[node]] *= count[node]

#   next count totals of all cycles
# product of each cycle component ("root" of each subgraph) plus 1
# total = (product + 1) foreach cycle, then -1 to get final result
products = [
    reduce(mul, [count[node] for node in cycle]) + 1
    for cycle in cycles
]
total = reduce(mul, products) - 1


# output result
print(total % (10**9 + 7))
