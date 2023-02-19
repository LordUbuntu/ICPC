# Problem G - Movie Night
from itertools import chain
from functools import reduce
from operator import mul

n = int(input())
graph = {i: int(input()) for i in range(1, n + 1)}
visited = {node: False for node in graph}

##### topologically sort graph nodes in reverse order (using DFS)
ordering = []  # topological ordering of graph (used for counting)
cycles = []  # cycle components in graph
for node in graph:
    # skip already visited nodes (start at next smallest available node)
    if visited[node]:
        continue
    # otherwise dfs available path
    path, stack = [], node
    while stack:
        curr = stack  # get current node
        next = graph[curr]  # get next node
        stack = next  # put next node on stack
        # add current node to path and mark as visited
        path.append(curr)
        visited[curr] = True
        # if next node in path
        #   we've discovered a cycle...
        #   to ordering, add cycle then rest of path in reverse
        #   end the dfs (break)
        # NOTE:
        #   for counting to work correctly, cycles must be treated as
        #   "root" nodes of each subgraph, and must come before paths
        #   that lead into them. Paths must be in reverse order and come
        #   after cycles.
        # eg: 1 -> 2, 3 -> 2, 2 -> 4 -> 5 -> 4
        #    [4, 5] [2, 3, 1] => [4, 5, 2, 3, 1]
        if next in path:
            cycle = path[path.index(next):]
            path = path[:path.index(next)]
            cycles.append(cycle)
            ordering.extend(cycle)
            ordering.extend(reversed(path))
            break
        # if next has been visited
        #   record path and end dfs (break)
        if visited[next]:
            ordering.extend(reversed(path))
            break


##### calculate the result
# NOTE:
# Counting works by taking the reverse topological ordering we've
#   determined in the topological ordering dfs step above, and then
#   traversing that ordering from right-to-left (back in order). From
#   there we initially set the count for all nodes to 1. Then, at each
#   step on our traversal, we add 1 to the count of our current node, and
#   then set the value of the next node to the product of the current
#   node with the value of the next node, we ignore nodes that are in
#   cycles in this first step (count phase 1).
# In the next step (count phase 2), for each cycle we take the product
#   of all its member nodes and add 1 to the result, then to finally
#   find the total (the answer to the problem) we take the resulting
#   product of all the cycles in the graph and subtract 1 from that.
# EX:
#   supposing graph = {1: 2, 5: 2, 2: 3, 3: 4, 4: 3}, then ordering would
#   be [3, 4, 2, 1, 5]. The process of counting in phase 1 would be:
# [1, 1, 1, 1, 1]
# [1, 1, 2, 1, 2]
# [1, 1, 4, 2, 2]
# [5, 1, 5, 2, 2]
#   and after that counting completes, the cycle product would be:
# [5, 1] = 5 * 1 = 5 + 1 = 6
#   then the total would be calculated:
# 6 = 6 - 1 = 5
#   and the answer would be 5 (correct answer)
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


##### output result
print(total % (10**9 + 7))
