---
title: ICPC Snippets
author: Jacobus Burger
date: Feb 3, 2023
documentclass: report
toc: true
---

# Modules we love

A brief highlight of some items from each module included, but not comprehensive. All modules of note are included however.
```python
import cmath
import math
import functools  # reduce, partial, lru_cache...
import itertools  # product, chain, cycle, repeat...
import collections  # deque, Counter
import string  # various string types (eg: ascii_letters, digits)
import operator  # treat python operators as functions
import random  # name says it all
import re  # just in case we need to use the forbidden tongue
```

# Graphs Algorithms

## Graph Data

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


# looks like
#     A
#   B   C
#  D E F G
#
# should return
# ABCDEFG
G = {
    'A': ['B', 'C'],
    'B': ['A', 'D', 'E'],
    'C': ['A', 'F', 'G'],
    'D': ['B'],
    'E': ['B'],
    'F': ['C'],
    'G': ['C']
}


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
# looks like
#     A
#    B I
#   C   J
#  DEF
#    GH
G = {
    'A': ['B', 'H'],
    'B': ['A', 'C', 'D', 'E'],
    'C': ['B'],
    'D': ['B'],
    'E': ['B', 'F', 'G'],
    'F': ['E'],
    'G': ['E'],
    'H': ['A', 'I'],
    'I': ['H'],
}

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

## Factorial

```python
from functools import lru_cache
# factorial function
@lru_cache
def fact(n):
    if n <= 1:
        return 1
    return n * fact(n - 1)
```

## GCD

```python 
def gcd_iter(a, b):
    while b != 0:
        a, b = b, a % b
    return a


@cache  # uses lru caching to drastically improve speed
def gcd_rec(a, b):
    if b == 0:
        return a
    return gcd(b, a % b)
```

## LCM

```python
# this lcm is a direct calculation that depends on the use of gcd
def lcm(a, b):
    return abs(a * b) // gcd(a, b)
```

## Combinations

```python
def C(n, k):
  return fact(n) // (fact(k) * fact(n - k))
```

## Permutations

```python
def P(n, k):
    return fact(n) // fact(n - k)
```

## Fibbonacci

```python
# fibbonacci sequence (iterative)
def fib_iter(n):
    a, b = 1, 1
    for i in range(n):
        a, b = a + b, a
    return a


# fibbonacci sequence
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

# Python Tricks

## Transpose a 2d list (matrix)

```python
list(map(list, zip(*matrix)))
```

## Limit x between an upper and lower bound (inclusive)

```python
lambda x: min(upper, max(x, lower))
```

# Other

## Annagram

```python
def anna(original, string):
    if sorted(original) == sorted(string):
        return True
    return False
```


