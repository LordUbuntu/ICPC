# Problem G - Movie Night
from collections import deque
n = int(input())
graph = {i: int(input()) for i in range(1, n + 1)}
unvisited = deque(graph)

# dfs components across graph, pay special attention to cycles
ordering = []  # global ordering (outside-in dfs), used to count later
cycles = []
while unvisited:
    # dfs
    # TODO - stack be replaced with a temp variable method instead
    # TODO - name visited path instead
    visited, stack = [], [unvisited.popleft()]  # start at smallest first
    while stack:
        # get current node
        curr = stack.pop()
        # get next node and mark to be visited
        next = graph[curr]
        stack.append(next)
        # mark current node as visited and remove from unvisited
        visited.append(curr)
        if curr in unvisited:
            unvisited.remove(curr)
        # if next node in stack
        #   return slice (cycle)
        if next in visited:
            cycles.append(visited[visited.index(next):])
            # TODO - is a break needed here???
        # if next node in ordering, extend ordering ("reverse the dive")
        # TODO - needs more thinking. two sides of same coin???
        if next in ordering:
            ordering.extend(reversed(visited))
            break
        # if next node has been visited on a different path
        #   stop, we've already been there
        #   add to ordering
        if next not in unvisited:
            ordering.extend(visited)
            break


# calculate the result
