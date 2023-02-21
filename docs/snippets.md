---
title: ICPC Snippets
author: Jacobus Burger
date: Feb 20, 2023
documentclass: report
toc: true
---

# Modules we love

These are the modules we specifically care about
```python
import cmath  # maths but for complex numbers
import math  # maths
import functools  # reduce, partial, lru_cache...
import itertools  # product, chain, cycle, repeat...
import collections  # deque, Counter
import heapq  # min-heap priority queue
import string  # various string types (eg: ascii_letters, digits)
import operator  # treat python operators as functions
import random  # name says it all
import re  # just in case we need to use the forbidden tongue
```

# Graphs

## Data

```python
# adjacency list
G = {
  a: [b, c],
  b: [a, c, d],
  c: [b],
  d: [b]
}


# binary heap (complete/incomplete binary tree)
T = [a, b, c, d, e]  # nodes = 2**height - 1
def parent(i): return floor((i - 1) / 2)
def left(i): return 2*i + 1
def right(i): return 2*i + 2
```

## BFS

```python
from collections import deque

# Breadth First Search,
#   where G is an adjacency list
#         v is the start
def bfs(G, v):
    visited, queue = [v], deque([v])
    while queue:
        v = queue.popleft()
        for w in G[v]:
            if w not in visited:
                visited.append(w)
                queue.append(w)
    return visited
```

## DFS

```python

# G is graph, v is starting vertex
# most straigh-forward solution
def dfs(G, v):
    visited, stack = [], [v]
    while stack:
        v = stack.pop()
        visited.append(v)
        for w in reversed(G[v]):
            if w not in visited:
                stack.append(w)
    return visited
```

# Mathematics

## General
Use math module. GCD, LCM, Permuations, Combinations, and more are all already defined and ready to use.

## Fibbonacci

```python
def fib_iter(n):
    a, b = 1, 1
    for i in range(n):
        a, b = a + b, a
    return a

@lru_cache
def fib_rec(n, a = 1, b = 1):
    if n == 0:
        return a
    return fib(n - 1, a + b, a)
```

## Hamming Distance

```python
# find the hamming distance of any two strings
def hamming_distance(a, b):
    return sum(map(op.ne, a, b))
```
